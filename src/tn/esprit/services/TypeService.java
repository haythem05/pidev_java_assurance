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
    
}
