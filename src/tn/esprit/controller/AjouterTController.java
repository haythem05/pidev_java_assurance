/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import tn.esprit.entities.Type;
import tn.esprit.services.TypeService;

/**
 * FXML Controller class
 *
 * @author HD
 */
public class AjouterTController implements Initializable {

    @FXML
    private TextField tf_nom;
    private String nom;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ajouterType(ActionEvent event) {
        nom = tf_nom.getText();
        
        Type t = new Type(nom);
        TypeService ts = new TypeService();
        ts.ajouterType(t);
    }
    
}
