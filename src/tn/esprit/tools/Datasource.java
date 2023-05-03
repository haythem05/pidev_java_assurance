/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tools;
import java.sql.*;

/**
 *
 * @author Gharbia
 */
public class Datasource {
    private String url="jdbc:mysql://localhost:3306/ranim";
    private String username = "root";
    private String password ="";
    private Connection cnx;
   static Datasource instance ;
    
    private Datasource() {
        
        try {
            
           cnx= DriverManager.getConnection(url, username, password);
           System.out.println ("Database Connected ");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage()); 
        }
    }
    
    public Connection getCnx(){
        return cnx;
    }
    
    public static Datasource getInstance() {
        if (instance==null){
            instance = new Datasource();
        }
        return instance ; 
    }
    
}
