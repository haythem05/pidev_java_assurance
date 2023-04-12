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
public class Habitation {
    
    private int id,idclient;
    private int nbpieceimmobilier;
    private float capitalimmobilier;
    private float capitalmobilier;
    private float devis;
    Categorie  type_Id;
    
    public Habitation() {
    }

    public Habitation(int idclient, int nbpieceimmobilier, float capitalimmobilier, float capitalmobilier, float devis, Categorie type_Id) {
        this.idclient = idclient;
        this.nbpieceimmobilier = nbpieceimmobilier;
        this.capitalimmobilier = capitalimmobilier;
        this.capitalmobilier = capitalmobilier;
        this.devis = devis;
        this.type_Id = type_Id;
    }

    public Habitation(int idclient, int nbpieceimmobilier, float capitalimmobilier, float capitalmobilier, float devis) {
        this.idclient = idclient;
        this.nbpieceimmobilier = nbpieceimmobilier;
        this.capitalimmobilier = capitalimmobilier;
        this.capitalmobilier = capitalmobilier;
        this.devis = devis;
    }

    
    @Override
    public String toString() {
        return "Habitation{" + "id=" + id + ", idclient=" + idclient + ", nbpieceimmobilier=" + nbpieceimmobilier + ", capitalimmobilier=" + capitalimmobilier + ", capitalmobilier=" + capitalmobilier + ", devis=" + devis + ", type_Id=" + type_Id.getId() + ", nom=" + type_Id.getNom() +  "}\n";
    }

    public Categorie getType_Id() {
        return type_Id;
    }

    public void setType_Id(Categorie type_Id) {
        this.type_Id = type_Id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdclient() {
        return idclient;
    }

    public void setIdclient(int idclient) {
        this.idclient = idclient;
    }


    public int getNbpieceimmobilier() {
        return nbpieceimmobilier;
    }

    public void setNbpieceimmobilier(int nbpieceimmobilier) {
        this.nbpieceimmobilier = nbpieceimmobilier;
    }

    public float getCapitalimmobilier() {
        return capitalimmobilier;
    }

    public void setCapitalimmobilier(float capitalimmobilier) {
        this.capitalimmobilier = capitalimmobilier;
    }

    public float getCapitalmobilier() {
        return capitalmobilier;
    }

    public void setCapitalmobilier(float capitalmobilier) {
        this.capitalmobilier = capitalmobilier;
    }

    public float getDevis() {
        return devis;
    }

    public void setDevis(float devis) {
        this.devis = devis;
    }

 
    
}
