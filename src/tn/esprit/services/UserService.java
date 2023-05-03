/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;

import tn.esprit.entities.User;
import tn.esprit.entities.Session;
import tn.esprit.tools.Datasource;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author iHoussem
 */
public class UserService implements IService<User>{

    Connection cnx;

    public UserService() {
        cnx=Datasource.getInstance().getCnx();
    }
    
    @Override
    public void ajouter(User entity) {
        if(!entity.isRole()){
            entity.setRoles("[\"ROLE_USER\"]");
        }else{
            entity.setRoles("[\"ROLE_ADMIN\"]");
        }
        Session session = new Session();
        String sql="INSERT INTO `user`(prenom,roles,email,password,numtel,username,cin,datedenaissance,idclient) VALUES ('"+entity.getPrenom()+"','"
                +entity.getRoles()+"','"+entity.getEmail().toLowerCase()+"','"+entity.getPassword()+"',"+entity.getNumero()
                +",'"+entity.getUsername()+"',"+entity.getCin()+",'"+entity.getDatenaissance()+"', '"+session.connectedUser.getId()+"')";
        try {
            Statement ste = cnx.createStatement();
            ste.executeUpdate(sql);
            System.out.println("User Ajoutee");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(User a) {
        String sql="update User set datedenaissance=?,prenom=?,email=?,password=?,numtel=?,username=?,cin=? where id=?;";
        try {
            PreparedStatement ste= cnx.prepareStatement(sql);
           ste.setDate(1,a.getDatenaissance());
           ste.setString(2,a.getPrenom());
           ste.setString(3, a.getEmail().toLowerCase());
           ste.setString(4, a.getPassword());
           ste.setInt(5, a.getNumero());
           ste.setString(6, a.getUsername());
           ste.setInt(7, a.getCin());
           ste.setInt(8, a.getId());
            ste.executeUpdate();
            System.out.println("User modifié");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(User entity) {
         String request = "DELETE FROM User  where id=?";
        try {
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setInt(1, entity.getId());
            pst.executeUpdate();
            System.out.println("User  Canceled");
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    @Override
    public List<User> afficher() {
        
        // Session session = new Session();
        // String query="select * from contract where idclient ='"+session.connectedUser.getId()+"'";

        List<User> users = new ArrayList<>();
        String query="select * from user";
        try {
            PreparedStatement ste = cnx.prepareStatement(query);
            ResultSet rs= ste.executeQuery();
            while(rs.next()){
                User a = new User();
                a.setId(rs.getInt("id"));
                a.setPrenom(rs.getString("prenom"));
                a.setRoles(rs.getString("roles"));
                a.setEmail(rs.getString("email"));
                a.setPassword(rs.getString("password"));
                a.setNumero(rs.getInt("numtel"));
                a.setUsername(rs.getString("username"));
                a.setCin(rs.getInt("cin"));
                a.setDatenaissance(rs.getDate("datedenaissance"));
                users.add(a);
               
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return users;
    }
    
    public User getUserById(int id) {
        User a = new User();
        String query="select * from user where id="+id;
        try {
            PreparedStatement ste = cnx.prepareStatement(query);
            ResultSet rs= ste.executeQuery();
            while(rs.next()){
                
                a.setId(rs.getInt("id"));
                a.setPrenom(rs.getString("prenom"));
                a.setRoles(rs.getString("roles"));
                a.setEmail(rs.getString("email"));
                a.setPassword(rs.getString("password"));
                a.setNumero(rs.getInt("numtel"));
                a.setUsername(rs.getString("username"));
                a.setCin(rs.getInt("cin"));
                a.setDatenaissance(rs.getDate("datedenaissance"));
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return a;
    }

    public String crypter_password(String password) {
        String hashValue = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());
            byte[] digestedBytes = messageDigest.digest();
            hashValue = DatatypeConverter.printHexBinary(digestedBytes).toLowerCase();

        } catch (Exception e) {
        }

     //   return hashValue;
     return hashValue;
    }


    public User Login(User c){
        Statement stm = null;
        User a = new User();
        int i=0;
        a.iconnected=i;

        try {
            stm = cnx.createStatement();
            String ClientQ = "select * from user where (email='"+c.getEmail().toLowerCase()+"' and password='"+c.getPassword()+"')";
            ResultSet rs = stm.executeQuery(ClientQ);
            System.out.println(ClientQ);
            
            while (rs.next()) {
                a.setId(rs.getInt("id"));
                a.setPrenom(rs.getString("prenom"));
                a.setRoles(rs.getString("roles")); 
                a.setEmail(rs.getString("email"));
                a.setPassword(rs.getString("password"));
                a.setNumero(rs.getInt("numtel"));
                a.setUsername(rs.getString("username"));
                a.setCin(rs.getInt("cin"));
                a.setDatenaissance(rs.getDate("datedenaissance"));
                i++;
                if(a.getRoles().equals("[\"ROLE_USER\"]")){
                    a.setRole(false);
                }else{
                   a.setRole(true); 
                }
                
                a.iconnected=i;
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        System.out.println(a.toString());
        return a;
    }

    public void promote(int id) {
       String request = "UPDATE user SET roles=? where id=?";
        try {
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setString(1,"[\"ROLE_ADMIN\"]");
            pst.setInt(2, id);
            pst.executeUpdate();
            System.out.println("utilisateur  promoted");
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void modifierClientReset(String email,String code) {

        Statement stm;
        try {
            stm = cnx.createStatement();

            String query = "UPDATE user SET password='" + code +"' WHERE email='" + email + "'";
            System.out.println(query);
            stm.executeUpdate(query);
            System.out.println(query);

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public User getUserByEmail(String email) {
        User a = new User();
        String query="select * from user where email='"+email+"'";
        try {
            PreparedStatement ste = cnx.prepareStatement(query);
            ResultSet rs= ste.executeQuery();
            while(rs.next()){
                
                a.setId(rs.getInt("id"));
                a.setPrenom(rs.getString("prenom"));
                a.setRoles(rs.getString("roles"));
                a.setEmail(rs.getString("email"));
                a.setPassword(rs.getString("password"));
                a.setNumero(rs.getInt("numtel"));
                a.setUsername(rs.getString("username"));
                a.setCin(rs.getInt("cin"));
                a.setDatenaissance(rs.getDate("datedenaissance"));
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return a;
    }
    
    
}
