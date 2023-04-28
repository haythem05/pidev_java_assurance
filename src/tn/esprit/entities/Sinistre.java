/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.entities;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 *
 * @author HD
 */
public class Sinistre {
    private int id; 
    private Timestamp date_heure;
    private String lieu, degats, statut="En attente de traitement", description, file;
    Type type;

    public Sinistre() {
    }
    
    public Sinistre(int id, Timestamp date_heure, String lieu, String degats, String statut, String description, String file) {
        this.id = id;
        this.date_heure = date_heure;
        this.lieu = lieu;
        this.degats = degats;
        this.statut = statut;
        this.description = description;
        this.file = file;
    }

    public Sinistre(Timestamp date_heure, String lieu, String degats, String statut, String description, String file) {
        this.date_heure = date_heure;
        this.lieu = lieu;
        this.degats = degats;
        this.statut = statut;
        this.description = description;
        this.file = file;
    }

    /*public Sinistre(Timestamp date_heure, String lieu, String degats, String statut, String description) {
        this.date_heure =  date_heure;
        this.lieu = lieu;
        this.degats = degats;
        this.statut = statut;
        this.description = description;
    }*/

    public Sinistre(Timestamp date_heure, String lieu, String degats, String statut, String description, Type type) {
        this.date_heure = date_heure;
        this.lieu = lieu;
        this.degats = degats;
        this.statut = statut;
        this.description = description;
        this.type = type;
    }

    public Sinistre(Timestamp date_heure, String lieu, String degats, String statut, String description, String file, Type type) {
        this.date_heure = date_heure;
        this.lieu = lieu;
        this.degats = degats;
        this.statut = statut;
        this.description = description;
        this.file = file;
        this.type = type;
    }

    public Sinistre(Timestamp date_heure, String lieu, String degats, String description, String file) {
        this.date_heure = date_heure;
        this.lieu = lieu;
        this.degats = degats;
        this.description = description;
        this.file = file;
    }

    public Sinistre(Timestamp date_heure, String lieu, String degats, String description) {
        this.date_heure = date_heure;
        this.lieu = lieu;
        this.degats = degats;
        this.description = description;
    }

    public Sinistre(Timestamp date_heure, String lieu, String degats, String description, Type type) {
        this.date_heure = date_heure;
        this.lieu = lieu;
        this.degats = degats;
        this.description = description;
        this.type = type;
    }

    public Sinistre(String statut) {
        this.statut = statut;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate_heure() {
        return date_heure;
    }

    public void setDate_heure(Timestamp date_heure) {
        this.date_heure =  date_heure;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDegats() {
        return degats;
    }

    public void setDegats(String degats) {
        this.degats = degats;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Sinistre : Type : " +type.getNom()+ " | Date : " +date_heure+ " | Lieu : " +lieu+ " | Degats : " +degats+ " | Statut : " +statut+ " | Description : " +description+ " | Image : " +file+ " \n\n";
    }

    
    
}
