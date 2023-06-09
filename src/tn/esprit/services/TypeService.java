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
import tn.esprit.entities.Type;
import tn.esprit.tools.MaConnexion;

/**
 *
 * @author HD
 */
public class TypeService implements Fonctions<Type>{
    Connection cnx;
    String sql="";
    
    public TypeService(){
        cnx = MaConnexion.getInstance().getCnx();
    }

    public void ajouterType(Type t) {
        sql = "insert into type(nom) values (?)";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ste.setString(1, t.getNom());
            ste.executeUpdate();
            ResultSet rs = ste.getGeneratedKeys();
            if (rs.next()) {
                t.setId(rs.getInt(1));
            }
            System.out.println("Type ajouté avec succès.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Type> afficher() {
        List<Type> types = new ArrayList<>();
        sql="select * from type";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()){
                Type t = new Type();
                t.setId(rs.getInt(1));
                t.setNom(rs.getString(2));
                types.add(t);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return types;
    }

    @Override
    public void supprimer(int id) {
        sql="delete from type where id= " + id;
        try {
            Statement ste = cnx.createStatement();
            ste.executeUpdate(sql);
            System.out.println("Type supprimé avec succès.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Type t, int id) {
        sql="update type set nom = ' " + t.getNom() + " ' where id= " + id;
        try {
            Statement ste = cnx.createStatement();
            ste.executeUpdate(sql);
            System.out.println("Type modifié avec succès.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public List<Type> rechType(int id) {
        List<Type> list = new ArrayList<>();
        try {
            String req = "select * from type where id= " + id;
            Statement st = cnx.createStatement();
            ResultSet RS = st.executeQuery(req);
            while (RS.next()) {
                Type t = new Type();
                t.setId(RS.getInt(2));
                t.setNom(RS.getString("nom"));

                list.add(t);
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return list;
    }
    
    public List<Type> getAllTypes() {
        Connection conn = MaConnexion.getInstance().getCnx();
        List<Type> types = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("select * from type where id = id");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Type t = new Type();
                t.setId(rs.getInt(2));
                t.setNom(rs.getString("nom"));

                types.add(t);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return types;
    }
    
    public List<Type> searchTypes(String search) {
        List<Type> types = getAllTypes();
        List<Type> results = new ArrayList<>();
        for (Type t : types) {
            if (t.getNom().toLowerCase().contains(search.toLowerCase())) {
                results.add(t);
            }
        }
        return results;
    }
}
