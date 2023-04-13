/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tests;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import tn.esprit.entities.Sinistre;
import tn.esprit.entities.Type;
import tn.esprit.services.SinistreService;
import tn.esprit.services.TypeService;
import tn.esprit.tools.MaConnexion;
/**
 *
 * @author HD
 */
public class main {
    public static void main(String[] args) throws ParseException {
        //Test Sinistre
        SinistreService ss = new SinistreService();
        
        //Ajout
        Sinistre s1 = new Sinistre();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Timestamp date1 = Timestamp.valueOf("2023-03-29 12:00:00");
        s1.setDate_heure(date1);
        s1.setLieu("Tunis");
        s1.setStatut("En attente de traitement");
        s1.setDegats("aaa");
        s1.setDescription("aaa");
        //ss.ajouter(s1);
        
        //Affichage
        //System.out.println(ss.afficher());
        
        //Suppression
        //ss.supprimer(51);
        //System.out.println(ss.afficher());
        
        //Modification
        Sinistre s2 = new Sinistre();
        Timestamp date2 = Timestamp.valueOf("2023-03-30 12:00:00");
        s2.setDate_heure(date2);
        s2.setLieu("Tunis");
        s2.setStatut("En attente de traitement");
        s2.setDegats("bbb");
        s2.setDescription("bbb");
        //ss.modifier(s2, 52);
        //System.out.println(ss.afficher());
        
        //Test Type
        TypeService ts = new TypeService();
        
        //Suppression
        //ts.supprimer(8);
        //System.out.println(ts.afficher());
        
        //Ajout
        Type t1 = new Type();
        t1.setNom("Risques spéciauxx");
        //ts.ajouter(t1);
        //System.out.println(ts.afficher());
        
        //Modification
        Type t2 = new Type();
        t2.setNom("Risques spéciaux");
        //ts.modifier(t2, 18);
        //System.out.println(ts.afficher());
        
        //Ajout sans statut
        Sinistre s3 = new Sinistre();
        Timestamp date3 = Timestamp.valueOf("2023-03-31 12:00:00");
        s3.setDate_heure(date3);
        s3.setLieu("Tunis");
        s3.setDegats("ccc");
        s3.setDescription("ccc");
        //ss.ajouter(s3);
        //System.out.println(ss.afficher());
        
        //Ajout Sinistre avec type (jointure)
        Type t3 = new Type();
        t3.setNom("aaa");
        //ts.ajouterType(t3);
        Sinistre s4 = new Sinistre();
        s4.setType(t3);
        Timestamp date4 = Timestamp.valueOf("2023-03-31 12:00:00");
        s4.setDate_heure(date4);
        s4.setLieu("Ben Arous");
        s4.setDegats("ddd");
        s4.setDescription("ddd");
        //ss.ajouterSinistre(s4,t3);
        //System.out.println(ss.afficher()); 
        
        Type t4 = new Type();
        t4.setNom("bb");
        //ts.ajouterType(t4);
        Sinistre s5 = new Sinistre();
        s5.setType(t4);
        Timestamp date5 = Timestamp.valueOf("2023-03-31 12:00:00");
        s5.setDate_heure(date5);
        s5.setLieu("Tunis");
        s5.setDegats("eee");
        s5.setDescription("eee");
        //ss.ajouterSinistre(s5,t4);
        //System.out.println(ss.afficher());
        
        //Modification
        Type t5 = new Type();
        t5.setNom("fff");
        //ts.ajouterType(t5);
        Sinistre s6 = new Sinistre();
        s6.setType(t5);
        Timestamp date6 = Timestamp.valueOf("2023-03-30 12:00:00");
        s6.setDate_heure(date6);
        s6.setLieu("Tunis");
        s6.setStatut("En cours de traitement");
        s6.setDegats("fff");
        s6.setDescription("fff");
        //ss.modifier(s6, 85);
        //ss.supprimer(85);
        
        Type t6 = new Type("ggg");
        //ts.ajouterType(t6);
        Sinistre s7 = new Sinistre(date6, "Boumhal", "ggg", "En attente de traitement", "ggg", t6);
        //ss.ajouterSinistre(s7, t6);
        System.out.println(ss.afficher());
        
    }
}
