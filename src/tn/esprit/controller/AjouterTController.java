/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
    @FXML
    private AnchorPane rootPane;

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
        
        if (nom.isEmpty()) {
            // Afficher un message d'erreur si un champ obligatoire est vide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs obligatoires.");
            alert.showAndWait();
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ajout réussi");
        alert.setHeaderText(null);
        alert.setContentText("Le type a été ajouté avec succès à la base de données.");
        alert.showAndWait();
        
        Type t = new Type(nom);
        TypeService ts = new TypeService();
        ts.ajouterType(t);
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/TypeBack.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
}
