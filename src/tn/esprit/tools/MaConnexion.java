/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author HD
 */
public class MaConnexion {
    private Connection cnx;
    public final String url = "jdbc:mysql://localhost:3306/projetpi";
    public final String user="root";
    public final String pwd ="";
    public static MaConnexion ct;
    
    public MaConnexion() {
        try {
            cnx=DriverManager.getConnection(url, user, pwd);
            System.out.println("Connexion etablie !!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
    }
    
    public static MaConnexion getInstance(){
        if(ct==null){
            ct = new MaConnexion();
        }
        return ct;
    }

    public Connection getCnx() {
        return cnx;
    }
    
}
