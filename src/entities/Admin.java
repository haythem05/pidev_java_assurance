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
public class Admin extends Utilisateur {
    public Admin(int id, int numtel, int cin, String email, String password, String nom, String prenom, String datedenaissance) {
        super(id, numtel, cin, email, password, nom, prenom, datedenaissance);
    }
    
}
