/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;
//import java.util.Date;

import java.sql.Date;

/**
 *
 * @author Ranim
 */
public class Utilisateur {
    private int id,numtel,cin;
    private String email,password,nom,prenom;
    private Date datedenaissance;
    
    public Utilisateur(){    
    }
    
    public Utilisateur(String email, String password){
        this.email = email;
        this.password=password;
    }
    
    public Utilisateur(int id, int numtel, int cin, String password, String email, Date datedenaissance, String nom, String prenom) {
        this.id = id;
        this.numtel = numtel;
        this.cin = cin;
        this.password = password;
        this.email = email;
        this.datedenaissance = datedenaissance;
        this.nom = nom;
        this.prenom = prenom;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
     public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }
    
    public int getNumtel() {
        return numtel;
    }

    public void setNumtel(int numtel) {
        this.numtel = numtel;
    }
    
     public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
     public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
     public Date getDatedenaissance() {
        return datedenaissance;
    }

    public void setDatedenaissance(Date datedenaissance) {
        this.datedenaissance = datedenaissance;
    }
    
}
