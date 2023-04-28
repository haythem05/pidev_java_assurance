/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.assurance.models;

/**
 *
 * @author haythem
 */
public class Categorie {
    
    private int id;
    private String nom;
    private String description;
    private String categorieimage;

    public Categorie() {
    }

    public Categorie(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }



    
    public Categorie(String nom, String description, String categorieimage) {
        this.nom = nom;
        this.description = description;
        this.categorieimage = categorieimage;
    }

    public String getCategorieimage() {
        return categorieimage;
    }

    public void setCategorieimage(String categorieimage) {
        this.categorieimage = categorieimage;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return  " Nom=" + nom + "  |  "+ "Description=" + description + "  |  "+ " Categorie Image=" + categorieimage +  "}\n";
    }
    
}

