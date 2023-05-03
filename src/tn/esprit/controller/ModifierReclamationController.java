/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.controller;

import java.io.File;
import tn.esprit.entities.Reclamation;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
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
   File  selectedFile;
    
    @FXML
    private ImageView imageV;
    public String file;
    
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
                    this.file = reclamation.getFile();
                     
        
        
        
        
        
         

}


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
    nouvelleReclamation.setFile(this.file );
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
    if (verifierSaisie()) {
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
    nouvelleReclamation.setFile(this.file);
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
}
private boolean verifierSaisie() {
String reference = fxreferenceM.getText();
String nom_d = fxnom_dM.getText();
String prenom_d = fxprenom_dM.getText();
String cinString = fxcinM.getText();
String email = fxemailM.getText();
String commentaire = fxcommentaireM.getText();
String tel = fxtelM.getText();
if (reference.isEmpty() || nom_d.isEmpty() || prenom_d.isEmpty()
        || cinString.isEmpty() || email.isEmpty() || commentaire.isEmpty()
        || tel.isEmpty()) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Tous les champs sont obligatoires!");
    alert.showAndWait();
    return false;
}

// Validate input fields
if (!reference.matches("^[a-zA-Z0-9]+$")) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("La référence doit contenir uniquement des lettres et des chiffres!");
    alert.showAndWait();
    return false;
}

if (nom_d == null || nom_d.isEmpty()) {
Alert alert = new Alert(Alert.AlertType.ERROR);
alert.setHeaderText(null);
alert.setContentText("Le champ nom est obligatoire!");
alert.showAndWait();
return false;
} else if (!nom_d.matches("^[a-zA-Z ]+$")) {
Alert alert = new Alert(Alert.AlertType.ERROR);
alert.setHeaderText(null);
alert.setContentText("Le champ nom ne doit contenir que des lettres et des espaces.");
alert.showAndWait();
return false;
}

if (prenom_d == null || prenom_d.isEmpty()) {
Alert alert = new Alert(Alert.AlertType.ERROR);
alert.setHeaderText(null);
alert.setContentText("Le champ prénom est obligatoire!");
alert.showAndWait();
return false;
} else if (!prenom_d.matches("^[a-zA-Z ]+$")) {
Alert alert = new Alert(Alert.AlertType.ERROR);
alert.setHeaderText(null);
alert.setContentText("Le champ prénom ne doit contenir que des lettres et des espaces.");
alert.showAndWait();
return false;
}


int cin = 0;
try {
    cin = Integer.parseInt(cinString);
} catch (NumberFormatException e) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Le champ CIN doit contenir uniquement des chiffres.");
    alert.showAndWait();
    return false;
}
if (cin == 0) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Le champ CIN est obligatoire!");
    alert.showAndWait();
    return false;
} else if (String.valueOf(cin).length() != 8) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Le champ CIN doit contenir exactement 8 chiffres sans caractères.");
    alert.showAndWait();
    return false;
}

if (email == null || email.isEmpty()) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Le champ email est obligatoire!");
    alert.showAndWait();
    return false;
} else if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")) {
   
// Check if email is valid
Alert alert = new Alert(Alert.AlertType.ERROR);
alert.setHeaderText(null);
alert.setContentText("L'adresse email n'est pas valide.");
alert.showAndWait();
return false;
}

if (commentaire == null || commentaire.isEmpty()) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Le champ commentaire est obligatoire!");
    alert.showAndWait();
    return false;
} else if (commentaire.length() < 10) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Le champ commentaire doit contenir au moins 10 caractères.");
    alert.showAndWait();
    return false;
}

if (tel == null || tel.isEmpty()) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Le champ téléphone est obligatoire!");
    alert.showAndWait();
    return false;
}

// if everything is valid, return true
return true;
}



    @FXML
    private void retourM(ActionEvent event) {
Stage stage = (Stage) retourM.getScene().getWindow();

// Close the stage
stage.close();
    }
    
    @FXML
    private void addImage(MouseEvent event) {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.home") + "\\Desktop"));
        fc.setTitle("Veuillez choisir l'image");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image", "*.jpg", "*.png"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg")
        );
        selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {

            // Load the selected image into the image view
            Image image1 = new Image(selectedFile.toURI().toString());

            System.out.println(selectedFile.toURI().toString());
            imageV.setImage(image1);

            // Create a new file in the destination directory
            File destinationFile = new File("C:\\xampp\\htdocs\\imagesAssurance\\" + selectedFile.getName());

            this.file = selectedFile.getName();

            try {
                // Copy the selected file to the destination file
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.err.println(e);
            }

        }
    }

 
    
    
}




  
