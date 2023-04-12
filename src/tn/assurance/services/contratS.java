/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.assurance.services;
import tn.assurance.models.Categorie;
import tn.assurance.models.Contrat;
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
public class contratS {
    
    
        Statement ste;
    Connection conn = MyConnection.getInstance().getConn();
    
    
    
    
 
    public void ajouterContrat(Contrat C,Categorie  cat) {
        try {
            String query = "INSERT INTO contrat(type_id,id_client,nb_place,valeur_catalogue,prix,date_debut,date_fin,date_circulation,avantages,marque,modele) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            
            preparedStatement.setInt(1,cat.getId());
            preparedStatement.setInt(2,C.getIdclient());
            preparedStatement.setInt(3,C.getNbplace());
            preparedStatement.setFloat(4,C.getValeurcatalogue());
            preparedStatement.setFloat(5,C.getPrix());
            preparedStatement.setString(6,C.getDatedebut().toString());
            preparedStatement.setString(7,C.getDatefin().toString());
            preparedStatement.setString(8,C.getDatecirculation().toString());
            preparedStatement.setString(9,C.getAvantages());
            preparedStatement.setString(10,C.getMarque());
            preparedStatement.setString(11,C.getModele());
            
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                C.setId(rs.getInt(1));
        
            }
        
            preparedStatement.executeUpdate();
               System.out.print("succes!!!");
        } catch (SQLException e) {
            
            System.err.println(e.getMessage());
        }
    }
    
    
    
     public void modifierContrat(Contrat c, int id) {
        try {
          String req = "UPDATE `contrat` SET `id_client` = '" + c.getIdclient() + "', `nb_place` = '" + c.getNbplace()   + "', `valeur_catalogue` = '" + c.getValeurcatalogue() +  "', `nb_place` = '" + c.getNbplace() + "', `prix` = '" + c.getPrix() + "', `date_debut` = '" + c.getDatedebut() + "', `date_fin` = '" + c.getDatefin() + "', `date_circulation` = '" + c.getDatecirculation() + "', `avantages` = '" + c.getAvantages() + "', `marque` = '" + c.getMarque() + "', `modele` = '" + c.getModele() + "' WHERE `contrat`.`id` = " + id;

            Statement st = conn.createStatement();
            st.executeUpdate(req);
            System.out.println("contrat  updated  !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
     public void supprimerContrat(int id) {
        try {
            String req = "DELETE FROM `contrat` WHERE id = " + id;
            Statement st = conn.createStatement();
            st.executeUpdate(req);
            System.out.println("contrat deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
     
     
     
 public List<Contrat> afficherContrat() {
    List<Contrat> list = new ArrayList<>();
    try {
        String req = "SELECT c.* ,t.nom  FROM contrat c inner join  categorie t on c.type_id = t.id";
        Statement st = conn.createStatement();
        ResultSet CA = st.executeQuery(req);
        while (CA.next()) {
            Contrat c = new Contrat();
            Categorie ca =new Categorie();
            
            c.setId(CA.getInt("id"));
            ca.setNom(CA.getString("nom"));
            ca.setId(CA.getInt("type_id"));
            c.setType_Id(ca);
            c.setIdclient(CA.getInt("id_client"));
            c.setDatedebut(CA.getDate("date_debut"));
            c.setDatefin(CA.getDate("date_fin"));
            c.setAvantages(CA.getString("avantages"));
            c.setMarque(CA.getString("marque"));
            c.setModele(CA.getString("modele"));
            c.setNbplace(CA.getInt("nb_place"));
            c.setDatecirculation(CA.getDate("date_circulation"));
            c.setValeurcatalogue((float) CA.getDouble("valeur_catalogue"));
            c.setPrix((float) CA.getDouble("prix"));
            list.add(c);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return list;
}

    
     
   /*
     public Contrat getById(int id) {
    Contrat c = new Contrat();
     Categorie ca =new Categorie();
    try {
        String req = "SELECT * FROM contrat WHERE `id` = " + id;
        Statement st = conn.createStatement();

        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            c.setId(rs.getInt(1));
             ca.getId(rs.getInt(2));
            c.setIdclient(rs.getInt(3));
            c.setDatedebut(java.sql.Date.valueOf(rs.getDate(4).toLocalDate()));
            c.setDatefin(java.sql.Date.valueOf(rs.getDate(5).toLocalDate()));
            c.setDatecirculation(java.sql.Date.valueOf(rs.getDate(10).toLocalDate()));
            c.setAvantages(rs.getString(6));
            c.setMarque(rs.getString(7));
            c.setModele(rs.getString(8));
            c.setNbplace(rs.getInt(9));
            c.setValeurcatalogue((float) rs.getDouble(11));
            c.setPrix((float) rs.getDouble(12));
        }

    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }

    return c;
}
*/

    
    
}


