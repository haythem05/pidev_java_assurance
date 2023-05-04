/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.assurance.services;
import tn.assurance.models.Habitation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tn.assurance.models.Categorie;
import tn.assurance.utils.MyConnection;

/**
 *
 * @author haythem
 */


public class habitationS {
    
            Statement ste;
    Connection conn = MyConnection.getInstance().getConn();
    
    
    
    
 
    public void ajouterHabitation(Habitation h,Categorie  cat) {
        try {
            String query = "INSERT INTO  assurance_habitation  ( id_client,  type_id,  nb_piece_immobilier,  capital_immobilier,  capital_mobilier,devis) VALUES (?, ?, ?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,h.getIdclient());
            preparedStatement.setInt(2,cat.getId());
            preparedStatement.setInt(3,h.getNbpieceimmobilier());
            preparedStatement.setFloat(4,h.getCapitalimmobilier());
            preparedStatement.setFloat(5,h.getCapitalmobilier());
            
     float devis = h.getNbpieceimmobilier() * h.getCapitalimmobilier() * 0.1f + h.getCapitalmobilier() * 0.05f;
        preparedStatement.setFloat(6, devis);

        preparedStatement.executeUpdate();
        ResultSet rs = preparedStatement.getGeneratedKeys();
        if (rs.next()) {
            h.setId(rs.getInt(1));
            System.out.println("Habitation ajouté avec succès.");
        } else {
            System.out.println("ECHEC");
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
    
    
     public void modifierHabitation(Habitation h, int id) {
        try {
            String req = "UPDATE `assurance_habitation` SET `id_client` = '"  + h.getIdclient()+ "', `nb_piece_immobilier` = '" + h.getNbpieceimmobilier() +  "', `capital_immobilier` = '" + h.getCapitalimmobilier() + "', `capital_mobilier` = '" + h.getCapitalmobilier() 
                    +"' WHERE `assurance_habitation`.`id` = " + id;
            Statement st = conn.createStatement();
            st.executeUpdate(req);
            System.out.println("habitation  updated  !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
     
     public void supprimerHabitation(int id) {
        try {
            String req = "DELETE FROM `assurance_habitation` WHERE id = " + id;
            Statement st = conn.createStatement();
            st.executeUpdate(req);
            System.out.println(" habitation  deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    

     
public List<Habitation> afficherHabitation() {
    List<Habitation> list = new ArrayList<>();

    try {
        String req = "SELECT  c.* ,t.nom  FROM assurance_habitation c inner join categorie t on c.type_id =t.id";
        Statement st = conn.createStatement();
        ResultSet CA = st.executeQuery(req);
                 Categorie ca =new Categorie();
        
        while (CA.next()) {
    
            Habitation h = new Habitation();
              
            h.setId(CA.getInt("id"));
            h.setNbpieceimmobilier(CA.getInt("nb_piece_immobilier"));
            h.setCapitalimmobilier(CA.getFloat("capital_immobilier"));
            h.setCapitalmobilier(CA.getFloat("capital_mobilier"));
            h.setIdclient(CA.getInt("id_client"));
             ca.setNom(CA.getString("nom"));
            ca.setId(CA.getInt("type_id"));
            h.setType_Id(ca);
            h.setDevis(CA.getFloat("devis"));
            list.add(h);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return list;
}

public List<Habitation> rechHab(int id) {
        List<Habitation> list = new ArrayList<>();
        try {
            String req = "SELECT c.* ,t.nom  FROM assurance_habitation c inner join  categorie t on c.type_id = t.id WHERE c.id=" + id;
            Statement st = conn.createStatement();
            ResultSet RS = st.executeQuery(req);
            while (RS.next()) {
                Habitation c = new Habitation();
                c.setId(RS.getInt(1));
                Categorie ca = new Categorie();
                ca.setId(RS.getInt(6));
                ca.setNom(RS.getString("nom"));
                c.setType_Id(ca);
                c.setIdclient(RS.getInt(5));
                c.setNbpieceimmobilier(RS.getInt(2));
                c.setCapitalimmobilier(RS.getFloat(3));
                c.setCapitalmobilier(RS.getFloat(4));
                c.setDevis(RS.getFloat(7));
                list.add(c);
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return list;
    }



     
  
      
      
      

      
      
      

    
    
}




