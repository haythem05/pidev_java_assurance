/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;

import java.util.List;
import tn.esprit.entities.Reponse;

/**
 *
 * @author MSI
 */
public interface InterfaceReponse<T> {
     public void ajouter(T rep);
    public List<T> afficher();
   public void supprimer(int id) ;
   public void modifier(int id , Reponse nouvelleReponse);
    
}
