/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.assurance.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import tn.assurance.models.Categorie;
import tn.assurance.services.categorieS;

/**
 * FXML Controller class
 *
 * @author haythem
 */
public class ModifiercategorieController implements Initializable {

    @FXML
    private TextField nomc;
    @FXML
    private TextField descriptionc;
    @FXML
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        nomc.setText(String.valueOf(affichercategorieController.nom));
        descriptionc.setText(String.valueOf(affichercategorieController.description));
        
        
        // TODO
    }    

    @FXML
    private void modifier(ActionEvent event) {
        try {
            int id=affichercategorieController.id;
            String nom=nomc.getText();
            String description=descriptionc.getText();
            
            Categorie ca = new Categorie(nom, description);
            categorieS cS = new categorieS();

         cS.modifierCategorie(ca, id);
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/assurance/gui/affichercategorieB.fxml"));  
        
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
