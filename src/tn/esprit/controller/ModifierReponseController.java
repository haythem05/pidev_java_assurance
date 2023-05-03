/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.controller;

import tn.esprit.entities.Reclamation;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.util.StringConverter;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.services.ReclamationService;
import tn.esprit.services.ReponseService;
import tn.esprit.entities.Reponse;


/**
 * FXML Controller class
 *
 * @author MSI
 */
public class ModifierReponseController implements Initializable {

     @FXML
    private TextArea fxid_userM;
    @FXML
    private TextArea fxnoteM;
    @FXML
    private ComboBox<String> fxcb;
     @FXML
    private Button modifierRep;
    @FXML
    private Button retourRep;
     private Reponse reponse;
     public int idSelected= -1;
   

  @Override
public void initialize(URL url, ResourceBundle rb) {
         System.out.println("xx");

};
public void initializeFxml(int id ) {
    System.out.println(id);
    idSelected = id ;
            ReponseService reponseService = new ReponseService();
            Reponse reponse =reponseService.recup(id);

    
    
   
          fxid_userM.setText(reponse.getId_user());
          fxnoteM.setText(reponse.getNote());
               
        
        
        
        
        
         

}

// Méthode pour modifier une réponse
public void modifier() {

        // Créer une nouvelle réclamation avec les valeurs modifiées
        Reponse nouvelleReponse = new Reponse();
            ReponseService reponseService = new ReponseService();

        nouvelleReponse.setId_user( fxid_userM.getText());
        nouvelleReponse.setNote(fxnoteM.getText());
        

        // Appeler la méthode de modification de la classe Reponse
        reponseService.modifier(this.idSelected, nouvelleReponse);
    try {
        // Appeler la méthode de modification de la classe Reponse
        // en utilisant l'ID et la nouvelle réclamation

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("La Reponse a été modifiée avec succès.");
        alert.showAndWait();

        // Fermer la fenêtre de modification
        Stage stage = (Stage) modifierRep.getScene().getWindow();
        stage.close();

    } catch (Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Une erreur s'est produite lors de la modification de la Reponse.");
        alert.showAndWait();
    }
}

    @FXML
    private void modifierRep(ActionEvent event) {
        System.out.println("heey");
         Reponse nouvelleReponse = new Reponse();
            ReponseService reponseService = new ReponseService();

        nouvelleReponse.setId_user( fxid_userM.getText());
        nouvelleReponse.setNote(fxnoteM.getText());
       

        // Appeler la méthode de modification de la classe Reponse
        reponseService.modifier(this.idSelected, nouvelleReponse);
    try {
        // Appeler la méthode de modification de la classe Reponse
        // en utilisant l'ID et la nouvelle réclamation

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("La Reponse a été modifiée avec succès.");
        alert.showAndWait();

        // Fermer la fenêtre de modification
        Stage stage = (Stage) modifierRep.getScene().getWindow();
        stage.close();

    } catch (Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Une erreur s'est produite lors de la modification de la Reponse.");
        alert.showAndWait();
    }
    }



    @FXML
    private void retourRep(ActionEvent event) {
Stage stage = (Stage) retourRep.getScene().getWindow();

// Close the stage
stage.close();
    }
}
