/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.esprit.entities.Reclamation;
import tn.esprit.entities.Reponse;
import tn.esprit.tools.MaConnexion;
/**
 *
 * @author MSI
 */
public class ReponseService implements InterfaceReponse<Reponse> {
    Connection cnx;
    String sql = "";

    public ReponseService() {
        cnx = MaConnexion.getInstance().getCnx();
    }

    @Override
    public void ajouter(Reponse rep) {
        // Ajouter la réponse avec la date de création
        sql = "insert into Reponse(id_user, note, created_at, reclamation_id) values (?, ?, ?, ?)";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, rep.getId_user());
            ste.setString(2, rep.getNote());
            ste.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            ste.setInt(4, rep.getReclamation().getId()); // Récupérer l'identifiant de la réclamation depuis l'objet Reclamation associé à la réponse

            ste.executeUpdate();
            System.out.println("Reponse ajoutée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Reponse recup(int id) {
    Reponse B = new Reponse();

    try {
        Statement st = cnx.createStatement();
        String query = "select * FROM reponse WHERE id='" + id + "'";
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            B.setId(rs.getInt(1));
            Reclamation r = new Reclamation();
            r.setReference(rs.getString(2)); // Assuming the reference is stored in column 2
            B.setReclamation(r);
            B.setId_user(rs.getString(3));
            B.setNote(rs.getString(4));

            System.out.println("!!!");
        }

    } catch (SQLException ex) {
        System.out.println("erreur get IdOBJ pour suivi");
        System.out.println(ex);
    }

    return B;
}


    @Override
    public List<Reponse> afficher() {
        List<Reponse> li = new ArrayList<>();
        try {
            String req = "SELECT * FROM Reponse";
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                Reponse rep = new Reponse();
                rep.setId(res.getInt(1));
                rep.setId_user(res.getString(2));
                rep.setNote(res.getString(3));
                Reclamation r = new Reclamation();
                r.setReference(res.getString(4)); // Récupérer la référence de la réclamation
                rep.setReclamation(r);
                li.add(rep);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return li;
    }

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM Reponse WHERE id = ?";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, id);
            st.executeUpdate();
            System.out.println("Réponse supprimée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(int id, Reponse nouvelleReponse) {
        try {
            String req = "UPDATE Reponse SET id_user = ?, note = ? WHERE id = ?";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setString(1, nouvelleReponse.getId_user());
            st.setString(2, nouvelleReponse.getNote());
            st.setInt(3, id);
            st.executeUpdate();
            System.out.println("Réponse modifiée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   
   
}
