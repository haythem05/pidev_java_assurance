/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataSource;
import tn.esprit.entities.Sinistre;
import tn.esprit.entities.Type;
import tn.esprit.tools.MaConnexion;

/**
 *
 * @author HD
 */
public class SinistreService implements Fonctions<Sinistre> {

    Connection cnx;
    String sql = "";

    public SinistreService() {
        cnx = MaConnexion.getInstance().getCnx();
    }

    /*public void ajouter(Sinistre t) {
        sql = "insert into sinistre(date_heure, lieu, statut, degats, description, file) values (?,?,?,?,?,?)";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setTimestamp(1, t.getDate_heure());
            ste.setString(2, t.getLieu());
            ste.setString(3, "En attente de traitement");
            ste.setString(4, t.getDegats());
            ste.setString(5, t.getDescription());
            ste.setString(6, t.getFile());
            ste.executeUpdate();
            System.out.println("Sinistre ajouté avec succès.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }*/
    public void ajouterSinistre(Sinistre t, Type type) {
        sql = "insert into sinistre(date_heure, lieu, statut, degats, description, file, type_id) values (?,?,?,?,?,?,?)";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ste.setTimestamp(1, t.getDate_heure());
            ste.setString(2, t.getLieu());
            ste.setString(3, "En attente de traitement");
            ste.setString(4, t.getDegats());
            ste.setString(5, t.getDescription());
            ste.setString(6, t.getFile());
            ste.setInt(7, type.getId());
            ste.executeUpdate();
            ResultSet rs = ste.getGeneratedKeys();
            if (rs.next()) {
                t.setId(rs.getInt(1));
            }
            System.out.println("Sinistre ajouté avec succès.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Sinistre> afficher() {
        List<Sinistre> sinistres = new ArrayList<>();
        sql = "select s.*, t.nom from sinistre s inner join type t on s.type_id = t.id";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Sinistre s = new Sinistre();
                s.setId(rs.getInt(1));
                Type t = new Type();
                t.setId(rs.getInt(2));
                t.setNom(rs.getString("nom"));
                s.setType(t);
                s.setDate_heure(rs.getTimestamp(3));
                s.setLieu(rs.getString(4));
                s.setStatut(rs.getString(5));
                s.setDegats(rs.getString(6));
                s.setDescription(rs.getString(7));
                s.setFile(rs.getString(8));

                sinistres.add(s);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return sinistres;
    }

    @Override
    public void supprimer(int id) {
        sql = "delete from sinistre where id= " + id;
        try {
            Statement ste = cnx.createStatement();
            ste.executeUpdate(sql);
            System.out.println("Sinistre supprimé avec succès.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Sinistre t, int id) {
        sql = "update sinistre set date_heure = ' " + t.getDate_heure() + " ', lieu = ' " + t.getLieu() + " ', statut = ' " + t.getStatut() + " ', degats = ' " + t.getDegats() + " ', description = ' " + t.getDescription() + " ', type_id = '" + t.getType().getId() + " ' where id= " + id;
        try {
            Statement ste = cnx.createStatement();
            ste.executeUpdate(sql);
            System.out.println("Sinistre modifié avec succès.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifiersanst(Sinistre t, int id) {
        sql = "update sinistre set date_heure = ' " + t.getDate_heure() + " ', lieu = ' " + t.getLieu() + " ', statut = ' " + t.getStatut() + " ', degats = ' " + t.getDegats() + " ', description = ' " + t.getDescription() + " ' where id= " + id;
        try {
            Statement ste = cnx.createStatement();
            ste.executeUpdate(sql);
            System.out.println("Sinistre modifié avec succès.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void traiter(Sinistre t, int id, String statu) {
        sql = "update sinistre set statut = ' " + statu + " ' where id= " + id;
        try {
            Statement ste = cnx.createStatement();
            ste.executeUpdate(sql);
            System.out.println("Sinistre traité avec succès.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Sinistre> searchSinistres(String search) {
        List<Sinistre> sinistres = getAllSinistres();
        List<Sinistre> results = new ArrayList<>();
        for (Sinistre s : sinistres) {
            if (s.getType().getNom().toLowerCase().contains(search.toLowerCase())) {
                results.add(s);
            }
        }
        return results;
    }

    public List<Sinistre> getAllSinistres() {
        Connection conn = MaConnexion.getInstance().getCnx();
        List<Sinistre> sinistres = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("select s.*, t.nom from sinistre s inner join type t on s.type_id = t.id");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Sinistre s = new Sinistre();
                s.setId(rs.getInt(1));
                Type t = new Type();
                t.setId(rs.getInt(2));
                t.setNom(rs.getString("nom"));
                s.setType(t);
                s.setDate_heure(rs.getTimestamp(3));
                s.setLieu(rs.getString(4));
                s.setStatut(rs.getString(5));
                s.setDegats(rs.getString(6));
                s.setDescription(rs.getString(7));
                s.setFile(rs.getString(8));

                sinistres.add(s);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return sinistres;
    }

    public int countSinistresEnAttente() throws SQLException {
        sql = "SELECT COUNT(*) FROM sinistre WHERE statut = 'En attente de traitement'";
        PreparedStatement ste = cnx.prepareStatement(sql);
        ResultSet rs = ste.executeQuery();
        int count = 0;
        if (rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }
    
    public List<Sinistre> rechSin(int id) {
        List<Sinistre> list = new ArrayList<>();
        try {
            String req = "select s.*, t.nom from sinistre s inner join type t on s.type_id = t.id WHERE s.id= " + id;
            Statement st = cnx.createStatement();
            ResultSet RS = st.executeQuery(req);
            while (RS.next()) {
                Sinistre r = new Sinistre();
                r.setId(RS.getInt("id"));
                Type t = new Type();
                t.setId(RS.getInt(2));
                t.setNom(RS.getString("nom"));
                r.setType(t);
                r.setDescription(RS.getString("description"));
                r.setLieu(RS.getString("lieu"));
                r.setFile(RS.getString("file"));
                r.setDate_heure(RS.getTimestamp("date_heure"));
                r.setStatut(RS.getString("statut"));
                r.setDegats(RS.getString("degats"));

                list.add(r);
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return list;
    }

}
