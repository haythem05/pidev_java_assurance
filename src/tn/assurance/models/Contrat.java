/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.assurance.models;
import java.util.Date;

/**
 *
 * @author haythem
 */
public class Contrat {
    
    private int id,idclient,nbplace;
    private float valeurcatalogue,prix;
    private Date datedebut,datefin,datecirculation;
    private String avantages,marque,modele;
    Categorie  type_Id;

    public Contrat(int id, int idclient, int nbplace, float valeurcatalogue, Date datedebut, Date datefin, Date datecirculation, String avantages, String marque, String modele, Categorie type_Id) {
        this.id = id;
        this.idclient = idclient;
        this.nbplace = nbplace;
        this.valeurcatalogue = valeurcatalogue;

        this.datedebut = datedebut;
        this.datefin = datefin;
        this.datecirculation = datecirculation;
        this.avantages = avantages;
        this.marque = marque;
        this.modele = modele;
        this.type_Id = type_Id;
    }

    public Contrat(int idclient, int nbplace, float valeurcatalogue, Date datedebut, Date datefin, Date datecirculation, String avantages, String marque, String modele) {
        this.idclient = idclient;
        this.nbplace = nbplace;
        this.valeurcatalogue = valeurcatalogue;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.datecirculation = datecirculation;
        this.avantages = avantages;
        this.marque = marque;
        this.modele = modele;
    }


    public Contrat(int idclient, int nbplace, float valeurcatalogue, Date datedebut, Date datefin, Date datecirculation, String avantages, String marque, String modele, Categorie type_Id) {
        this.idclient = idclient;
        this.nbplace = nbplace;
        this.valeurcatalogue = valeurcatalogue;

        this.datedebut = datedebut;
        this.datefin = datefin;
        this.datecirculation = datecirculation;
        this.avantages = avantages;
        this.marque = marque;
        this.modele = modele;
        this.type_Id = type_Id;
    }

     
    public Categorie getType_Id() {
        return type_Id;
    }

    public void setType_Id(Categorie type_Id) {
        this.type_Id = type_Id;
    }

    
@Override
public String toString() {
    return " Nombre De Place =" + nbplace +"  |  "+ " Valeur Catalogue=" + valeurcatalogue +"  |  "+ " Prix=" + prix +"  |  "+ " Date Debut=" + datedebut +"  |  "+ " Date Fin=" + datefin +"  |  "+ "Date Circulation=" + datecirculation +"  |  "+ " Avantages=" + avantages +"  |  "+ " Marque=" + marque +"  |  "+ " Modele=" +
            modele +  "\n";
}



    public Contrat(int id, int idclient, int nbplace, float valeurcatalogue, float prix, Date datedebut, Date datefin, Date datecirculation, String avantages, String marque, String modele) {
        this.id = id;
        this.idclient = idclient;
        this.nbplace = nbplace;
        this.valeurcatalogue = valeurcatalogue;
        this.prix = prix;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.datecirculation = datecirculation;
        this.avantages = avantages;
        this.marque = marque;
        this.modele = modele;
    }


    public Contrat(int idclient, int nbplace, float valeurcatalogue, float prix, Date datedebut, Date datefin, Date datecirculation, String avantages, String marque, String modele) {
        this.idclient = idclient;
        this.nbplace = nbplace;
        this.valeurcatalogue = valeurcatalogue;
        this.prix = prix;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.datecirculation = datecirculation;
        this.avantages = avantages;
        this.marque = marque;
        this.modele = modele;
    }


    public Contrat() {
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

    public int getNbplace() {
        return nbplace;
    }

    public void setNbplace(int nbplace) {
        this.nbplace = nbplace;
    }

    public float getValeurcatalogue() {
        return valeurcatalogue;
    }

    public void setValeurcatalogue(float valeurcatalogue) {
        this.valeurcatalogue = valeurcatalogue;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public Date getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(Date datedebut) {
        this.datedebut = datedebut;
    }

    public Date getDatefin() {
        return datefin;
    }

    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }

    public Date getDatecirculation() {
        return datecirculation;
    }

    public void setDatecirculation(Date datecirculation) {
        this.datecirculation = datecirculation;
    }

    public String getAvantages() {
        return avantages;
    }

    public void setAvantages(String avantages) {
        this.avantages = avantages;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

 
}
