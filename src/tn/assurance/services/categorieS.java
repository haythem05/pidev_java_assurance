/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.assurance.services;
import tn.assurance.models.Categorie;
import tn.assurance.utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author haythem
 */
public class categorieS {
    
    
        Statement ste;
    Connection conn = MyConnection.getInstance().getConn();
    
    
    
    
 
    public void ajouterCategorie(Categorie c) {
        try {
            String query = "INSERT INTO categorie (nom,description,categorie_image) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,c.getNom());
            preparedStatement.setString(2,c.getDescription());
            preparedStatement.setString(3,c.getCategorieimage());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                c.setId(rs.getInt(1));
            
            System.out.println("Catégorie ajouté avec succès.");
        }
            else   System.out.println("ECHEC");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
     public void modifierCategorie(Categorie c, int id) {
        try {
            String req = "UPDATE `categorie` SET `nom` = '" + c.getNom() + "', `description` = '" + c.getDescription() + "', `categorie_image` = '" + c.getCategorieimage() +"' WHERE `categorie`.`id` = " + id;
            Statement st = conn.createStatement();
            st.executeUpdate(req);
            System.out.println("categorie  updated  !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
     public void supprimerCategorie(int id) {
        try {
            String req = "DELETE FROM `categorie` WHERE id = " + id;
            Statement st = conn.createStatement();
            st.executeUpdate(req);
            System.out.println("categorie deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
     
     
     
    public List<Categorie> afficherCategorie() {
        List<Categorie> list = new ArrayList<>();
        try {
            String req = "Select * from categorie";
            Statement st = conn.createStatement();

            ResultSet CA = st.executeQuery(req);
            while (CA.next()) {
                Categorie c = new Categorie();
                c.setId(CA.getInt(1));
                c.setNom(CA.getString(2));
                c.setDescription(CA.getString(4));
                c.setCategorieimage(CA.getString(3));
              

                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    
     
   
      public Categorie  getById(int id) {
        Categorie c  = new Categorie();
        try {
            String req = "Select * from categorie  WHERE `id` = " + id;
            Statement st = conn.createStatement();

            ResultSet CA = st.executeQuery(req);

            while (CA.next()) {
                 c.setNom(CA.getString(1));
                c.setDescription(CA.getString(2));
                c.setCategorieimage(CA.getString(3));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return c;
    }

    
    
}


