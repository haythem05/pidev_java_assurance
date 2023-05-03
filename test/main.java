
import tn.esprit.entities.Type;
import tn.esprit.services.SinistreService;
import tn.esprit.services.TypeService;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author HD
 */
public class main {
    public static void main(String[] args) {
        SinistreService ss = new SinistreService();
        //System.out.println(ss.afficher());
        
        TypeService ts = new TypeService();
        Type t1 = new Type();
        t1.setNom("Risques spéciauxx");
        //ts.ajouterType(t1);
        //System.out.println(ts.afficher());
       
        //Modification
        Type t2 = new Type();
        t2.setNom("Risques spéciaux");
        //ts.modifier(t2, 56);
        //System.out.println(ts.afficher());
    }
}
