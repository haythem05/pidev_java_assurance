/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tests;
import java.util.Date;
import tn.esprit.entities.Reponse;
import tn.esprit.services.ReponseService;
import tn.esprit.tools.MaConnexion;
import tn.esprit.entities.Reclamation;

/**
 *
 * @author MSI
 */
public class MainRep {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
ReponseService reps = new ReponseService();

       // Ajout manuel d'une réponse
        Reponse rep1 = new Reponse();
        rep1.setId_user("1919");
        rep1.setNote("Non");
        Reclamation rec1 = new Reclamation();
        rec1.setId(184); // ID de la réclamation à laquelle la réponse est liée
        rep1.setReclamation(rec1);
        rep1.setCreated_at(new Date()); // Date et heure actuelles
        reps.ajouter(rep1);
        
        // Affichage de toutes les reponses
        for (Reponse rep : reps.afficher()) {
            System.out.println(rep);
        }

        // Suppression d'une réponse
        reps.supprimer(17);

        // Affichage de toutes les reponses après la suppression
        for (Reponse rep  : reps.afficher()) {
            System.out.println(rep);
        }
        

       // Modification d'une réponse
Reponse rep2 = new Reponse();
 rep2.setId_user("2008");
 rep2.setNote("OK");
reps.modifier(18, rep2);

// Affichage de toutes les reponses après la modification
for (Reponse rep : reps.afficher()) {
    System.out.println(rep);
}

        
    }
}
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    
    

