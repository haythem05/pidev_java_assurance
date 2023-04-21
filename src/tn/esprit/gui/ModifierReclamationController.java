/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

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

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class ModifierReclamationController implements Initializable {

   @FXML
    private Label fxreferenceM; 
    @FXML
    private TextArea fxnom_dM;
    @FXML
    private TextArea fxprenom_dM;
    @FXML
    private TextArea fxcinM;
    @FXML
    private TextArea fxemailM;
    @FXML
    private TextArea fxcommentaireM;
    @FXML
    private TextArea fxtelM;
    @FXML
    private Button fxfileM;
    @FXML
    private Button modifierRec;
    @FXML
    private Button retourM;
     private Reclamation reclamation;
     public int idSelected= -1;
   
    
@Override
public void initialize(URL url, ResourceBundle rb) {
         System.out.println("xx");

};
public void initializeFxml(int id ) {
    System.out.println(id);
    idSelected = id ;
            ReclamationService reclamationService = new ReclamationService();
            Reclamation reclamation =reclamationService.recup(id);

    
    
        fxreferenceM.setText(reclamation.getReference());
          fxnom_dM.setText(reclamation.getNom_d());
            fxprenom_dM.setText(reclamation.getPrenom_d());
            int cin = reclamation.getCin();
            fxcinM.setText(Integer.toString(cin));
                fxemailM.setText(reclamation.getEmail());
                  fxcommentaireM.setText(reclamation.getCommentaire());
                    fxtelM.setText(reclamation.getTel());
                      fxfileM.setText(reclamation.getFile());
        
        
        
        
        
         

}

// Méthode pour modifier une réclamation
// Méthode pour modifier une réclamation
public void modifier() {
    // Créer une nouvelle réclamation avec les valeurs modifiées
    Reclamation nouvelleReclamation = new Reclamation();
    ReclamationService reclamationService = new ReclamationService();

    nouvelleReclamation.setReference(fxreferenceM.getText());
    nouvelleReclamation.setNom_d(fxnom_dM.getText());
    nouvelleReclamation.setPrenom_d(fxprenom_dM.getText());
    nouvelleReclamation.setCin(Integer.parseInt(fxcinM.getText()));
    nouvelleReclamation.setEmail(fxemailM.getText());
    nouvelleReclamation.setCommentaire(fxcommentaireM.getText());
    nouvelleReclamation.setTel(fxtelM.getText());
    nouvelleReclamation.setFile(fxfileM.getText());
    nouvelleReclamation.setStatut("En cours");

    // Appeler la méthode de modification de la classe Reclamation
    reclamationService.modifier(this.idSelected, nouvelleReclamation);
    try {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("La réclamation a été modifiée avec succès.");
        alert.showAndWait();

        // Fermer la fenêtre de modification
        Stage stage = (Stage) modifierRec.getScene().getWindow();
        stage.close();

    } catch (Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Une erreur s'est produite lors de la modification de la réclamation.");
        alert.showAndWait();
    }
}

@FXML
private void modifierRec(ActionEvent event) {
    System.out.println("heey");
    Reclamation nouvelleReclamation = new Reclamation();
    ReclamationService reclamationService = new ReclamationService();

    nouvelleReclamation.setReference(fxreferenceM.getText());
    nouvelleReclamation.setNom_d(fxnom_dM.getText());
    nouvelleReclamation.setPrenom_d(fxprenom_dM.getText());
    nouvelleReclamation.setCin(Integer.parseInt(fxcinM.getText()));
    nouvelleReclamation.setEmail(fxemailM.getText());
    nouvelleReclamation.setCommentaire(fxcommentaireM.getText());
    nouvelleReclamation.setTel(fxtelM.getText());
    nouvelleReclamation.setFile(fxfileM.getText());
    nouvelleReclamation.setStatut("En cours");

    // Appeler la méthode de modification de la classe Reclamation
    reclamationService.modifier(this.idSelected, nouvelleReclamation);
    try {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("La réclamation a été modifiée avec succès.");
        alert.showAndWait();

       


        // Fermer la fenêtre de modification
        Stage stage = (Stage) modifierRec.getScene().getWindow();
        stage.close();

    } catch (Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Une erreur s'est produite lors de la modification de la réclamation.");
        alert.showAndWait();
    }
    }



    @FXML
    private void retourM(ActionEvent event) {
Stage stage = (Stage) retourM.getScene().getWindow();

// Close the stage
stage.close();
    }
}




  
