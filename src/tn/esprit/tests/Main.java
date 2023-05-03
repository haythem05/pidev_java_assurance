package tn.esprit.tests;

import tn.esprit.entities.Reclamation;
import tn.esprit.services.ReclamationService;
import tn.esprit.tools.MaConnexion;

import java.util.Date;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;





public class Main {
    public static void main(String[] args) {
        ReclamationService rs = new ReclamationService();
        // Chemin d'accès du fichier
        String filePath = "C:/Users/MSI/Desktop/français.png";
        int MAX_FILE_SIZE = 1200* 610; // 1 Mo

        // Lecture du contenu du fichier
        File file = new File(filePath);
        if (!file.exists() || !file.canRead()) {
            System.err.println("Impossible de lire le fichier : " + filePath);
            System.exit(0);
        }
        byte[] fileContent = new byte[(int) file.length()];
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            inputStream.read(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Convertir le tableau de bytes en chaîne de caractères
        String fileContentStr = new String(fileContent, StandardCharsets.UTF_8);

        // Ajout manuel d'une réclamation avec l'attribut "file"
        Reclamation r1 = new Reclamation();
        r1.setNom_d("Messaoudi");
        r1.setPrenom_d("Adam");
        r1.setCin(12345678);
        r1.setEmail("email1@example.com");
        r1.setCommentaire("Commentaire1");
        r1.setTel("12345678");
        r1.setCreated_at(new Date()); // Date et heure actuelles
        r1.setFile(fileContentStr);
        rs.ajouter(r1);

      

        // Affichage de toutes les réclamations
        for (Reclamation r : rs.afficher()) {
            System.out.println(r);
        }

        // Suppression d'une réclamation
        rs.supprimer(147);

        // Affichage de toutes les réclamations après la suppression
        for (Reclamation r : rs.afficher()) {
            System.out.println(r);
        }

        // Modification d'une réclamation avec l'attribut "file"
        Reclamation r5 = new Reclamation();
        r5.setNom_d("Ben Hamida");
        r5.setPrenom_d("Sami");
        r5.setCin(12345682);
        r5.setEmail("email5@example.com");
        r5.setCommentaire("Commentaire5");
        r5.setTel("87654324");
        r5.setFile(fileContentStr);
        rs.modifier(145, r5);

        // Affichage de toutes les réclamations après la modification
        for (Reclamation r : rs.afficher()) {
            System.out.println(r);
        }
    }
}

     
        
 

