/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import entities.Utilisateur;
import tools.MaConnexion;



/**
 *
 * @author Ranim
 */
public class UtilisateurService implements IService<Utilisateur>{
    public Connection cnx;
    public Statement sql;

    public UtilisateurService() {
        cnx = MaConnexion.getInstance().getCnx();
    }

    @Override
    public void ajouter(Utilisateur u) {
        String sql = "INSERT INTO user(UserName, prenom, Email, Password, datedenaissance,CIN,numtel) VALUES (?, ?, ?, ?, ?,?,?)";
        try {
            PreparedStatement statement = cnx.prepareStatement(sql);
            statement.setString(1, u.getNom());
            statement.setString(2, u.getPrenom());
            statement.setString(3, u.getEmail());
            statement.setString(4, u.getPassword());
            statement.setDate(5, u.getDatedenaissance());
            statement.setInt(6,u.getCin());
            statement.setInt(7,u.getNumtel());
            statement.executeUpdate();
            System.out.println("Utilisateur ajouté avec succès !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Utilisateur> afficher() {
        List<Utilisateur> list = new ArrayList<>();
        
        try {
            String req = "SELECT * FROM user";
            PreparedStatement pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                Utilisateur utilisateur = new Utilisateur();

                utilisateur.setId(rs.getInt("id"));
                utilisateur.setNom(rs.getString("UserName"));
                utilisateur.setPrenom(rs.getString("prenom"));
                utilisateur.setDatedenaissance(rs.getDate("datedenaissance"));
                utilisateur.setEmail(rs.getString("Email"));
                utilisateur.setPassword(rs.getString("Password"));
                utilisateur.setNumtel(rs.getInt("numtel"));
                utilisateur.setCin(rs.getInt("CIN"));

                list.add(utilisateur);

                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return list;
    }

    @Override
    public void supprimer(int id) {
        String sql = "DELETE FROM user WHERE id=?";
        try {
            PreparedStatement statement = cnx.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Utilisateur supprimé avec succès !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Utilisateur t) {
        try {
            String req = "UPDATE user SET numtel=?,CIN=?,Password=?,Email=?,datedenaissance=?,UserName=?,prenom=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, t.getNumtel());
            pst.setInt(2, t.getCin());
            pst.setString(3, t.getPassword());
            pst.setString(4, t.getEmail());
            pst.setDate(5, t.getDatedenaissance());
            pst.setString(6, t.getNom());
            pst.setString(7, t.getPrenom());
            pst.setInt(8, t.getId());
            pst.executeUpdate();
            System.out.println("Users modifiée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public boolean checkCredentials(String email, String password) {
    boolean result = false;
    try {
        String sql = "SELECT * FROM user WHERE Email = ? AND Password = ?";
        PreparedStatement statement = cnx.prepareStatement(sql);
        statement.setString(1, email);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        result = resultSet.next();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return result;
}
}
