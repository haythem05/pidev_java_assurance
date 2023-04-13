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
    Connection cnx;
    String sql="";

    public UtilisateurService() {
        cnx = MaConnexion.getInstance().getCnx();
    }

    @Override
    public void ajouter(Utilisateur u) {
        sql = "INSERT INTO user(nom, prenom, email, password, datedenaissance,cin,numtel) VALUES (?, ?, ?, ?, ?,?,?)";
        try {
            PreparedStatement statement = cnx.prepareStatement(sql);
            statement.setString(1, u.getNom());
            statement.setString(2, u.getPrenom());
            statement.setString(3, u.getEmail());
            statement.setString(4, u.getPassword());
            statement.setString(5, u.getDatedenaissance());
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
                list.add(new Utilisateur(rs.getInt(1),rs.getInt(2),rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return list;
    }

    @Override
    public void supprimer(Utilisateur u) {
        String sql = "DELETE FROM user WHERE id=?";
        try {
            PreparedStatement statement = cnx.prepareStatement(sql);
            statement.setInt(1, u.getId());
            statement.executeUpdate();
            System.out.println("Utilisateur supprimé avec succès !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Utilisateur t) {
        try {
            String req = "UPDATE user SET numtel=?,cin=?,password=?,email=?,datedenaissance=?,nom=?,prenom=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, t.getNumtel());
            pst.setInt(2, t.getCin());
            pst.setString(3, t.getPassword());
            pst.setString(4, t.getEmail());
            pst.setString(5, t.getDatedenaissance());
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
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
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
