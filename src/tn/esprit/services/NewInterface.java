/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;

import java.util.List;
import tn.esprit.entities.Reclamation;

/**
 *
 * @author MSI
 */
public interface NewInterface<T> {
    public void ajouter(T r);
    public List<T> afficher();
    public void supprimer(int id) ;
   public void modifier(int id , Reclamation nouvelleReclamation);
    
}
