/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javapiuser;

import tn.esprit.entities.User;
import tn.esprit.services.UserService;

/**
 *
 * @author iHoussem
 */
public class JavaPiUser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        User  user1= new User();
        UserService serU=new UserService();
        
                user1.setNom("test");
                user1.setPrenom("test");
                user1.setRole(true);
                user1.setEmail("test@esprit.tn");
                user1.setPassword("passwordtest");
                user1.setNumero(25361425);
                user1.setUsername("testUsername");
        serU.ajouter(user1);
        
        user1.setId(25);
        user1.setUsername("UserNameTestModified");
        serU.modifier(user1);
        
        serU.supprimer(user1);
        
        for(int i=0;i<serU.afficher().size();i++){
            System.out.println(serU.afficher().get(i).toString());
        }
        
        System.out.println("++++++++++++++++"+serU.getUserById(27).toString());
        
        
        
    }
    
}
