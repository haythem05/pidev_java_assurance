/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

//import java.util.Date;

/**
 *
 * @author Ranim
 */
public class Client extends Utilisateur{
    private int adressepostale;
    private String adresse;
    
    public Client(int id, int numtel, int cin, String email, String password, String nom, String prenom, String datedenaissance, int adressepostale, String adresse) {
        super(id, numtel, cin, email, password, nom, prenom, datedenaissance);
        this.adresse = adresse;
        this.adressepostale = adressepostale;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String asresse) {
        this.adresse = adresse;
    }
    
    public int getAdressepostale() {
        return adressepostale;
    }

    public void setAdressepostale(int adressepostale) {
        this.adressepostale = adressepostale;
    }

}
