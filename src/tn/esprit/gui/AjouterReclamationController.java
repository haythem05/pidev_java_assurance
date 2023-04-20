/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import java.util.UUID;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.entities.Reclamation;
import tn.esprit.services.ReclamationService;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.logging.Logger;
import java.util.logging.Level;



/**
 * FXML Controller class
 *
 * @author MSI
 */
public class AjouterReclamationController implements Initializable {

    @FXML
    private Label fxreference; // Changer le type de l'attribut à Label
    @FXML
    private TextArea fxnom_d;
    @FXML
    private TextArea fxprenom_d;
    @FXML
    private TextArea fxcin;
    @FXML
    private TextArea fxemail;
    @FXML
    private TextArea fxcommentaire;
    @FXML
    private TextArea fxtel;
    @FXML
    private Button fxfile;
    @FXML
    private Button ajout;
    @FXML
    private Button retour;

    public Connection cnx;
    public Statement stm;
    String sql = "";

 @Override
public void initialize(URL url, ResourceBundle rb) {
    try {
        // Initialize your database connection here
        cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/assurancepidev", "root", "");
    } catch (SQLException ex) {
        System.out.println("Failed to connect to database: " + ex.getMessage());
    } 
    Reclamation r = new Reclamation();


ajout.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent event) {
        Reclamation r = new Reclamation();
        r.setNom_d(fxnom_d.getText());
        r.setPrenom_d(fxprenom_d.getText());
        int cin = 0;
        if (!fxcin.getText().isEmpty() && fxcin.getText().matches("\\d+")) {
            cin = Integer.parseInt(fxcin.getText());
        }
        r.setCin(cin);
        r.setEmail(fxemail.getText());
        r.setCommentaire(fxcommentaire.getText());
        r.setTel(fxtel.getText());

        ajouter(r);
    }
});



       fxfile.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent event) {
        // Appel à la méthode pour récupérer le chemin du fichier
        String file = selectFile();

        // Enregistrer le chemin absolu du fichier dans l'objet Reclamation
        r.setFile(file);

        // Mettre à jour le texte du bouton fxfile avec le nom du fichier sélectionné
        fxfile.setText(file != null ? file : "Aucun fichier sélectionné");
    }
});

        retour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                redirectToList();
            }
        });
    }


    private String selectFile() {
        FileChooser fileChooser = new FileChooser();
        // Configurer le file chooser
        fileChooser.setTitle("Importer");
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            return selectedFile.getAbsolutePath();
        }
        return null;
    }



    

     
   public void ajouter(Reclamation r) {
   // Générer la référence aléatoire
 String reference = Reclamation.genererReference();
 fxreference.setText(reference);
   String nom_d = r.getNom_d();
   String prenom_d = r.getPrenom_d();
   int cin = r.getCin();
   String email = r.getEmail();
   String commentaire = r.getCommentaire();
   String tel = r.getTel();
   
   
   // Vérifier si tous les champs sont vides
if (nom_d.isEmpty() && prenom_d.isEmpty() && cin == 0
        && email.isEmpty() && commentaire.isEmpty() && tel.isEmpty()) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Tous les champs sont obligatoires!");
    alert.showAndWait();
    return;
}


    // Validate input fields 
if (reference == null || !reference.matches("^[a-zA-Z0-9]+$")) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("La référence doit contenir uniquement des lettres et des chiffres!");
    alert.showAndWait();
    return;
}

if (nom_d == null || nom_d.isEmpty()) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Le champ nom est obligatoire!");
    alert.showAndWait();
    return;

} else if (!nom_d.matches("^[a-zA-Z]+$")) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Le champ nom ne doit contenir que des lettres.");
    alert.showAndWait();
    return;
}

if (prenom_d == null || prenom_d.isEmpty()) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Le champ prénom est obligatoire!");
    alert.showAndWait();
    return;

} else if (!prenom_d.matches("^[a-zA-Z]+$")) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Le champ prénom ne doit contenir que des lettres.");
    alert.showAndWait();
    return;
}

if (cin == 0) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Le champ CIN est obligatoire!");
    alert.showAndWait();
    return;
} else if (String.valueOf(cin).length() != 8) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Le champ CIN doit contenir exactement 8 chiffres sans caractères.");
    alert.showAndWait();
    return;
}


if (email == null || email.isEmpty()) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Le champ email est obligatoire!");
    alert.showAndWait();
    return;


} else if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Le champ email n'est pas valide.");
    alert.showAndWait();
    return;
}

if (commentaire == null || commentaire.isEmpty()) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Le champ commentaire est obligatoire!");
    alert.showAndWait();
    return;
} else if (commentaire.length() < 10) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Le champ commentaire doit contenir au moins 10 caractères.");
    alert.showAndWait();
    return;
}

if (tel == null || tel.isEmpty()) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Le champ téléphone est obligatoire!");
    alert.showAndWait();
    return;
}


 
    // Ajouter la réclamation avec la référence générée, la date de création et le statut "En cours"
    sql = "insert into reclamation(reference, nom_d, prenom_d, cin, email, commentaire, tel, created_at, statut, file) values (?,?,?,?,?,?,?,?,?,?)";
    try {
        PreparedStatement ste = cnx.prepareStatement(sql);
        ste.setString(1, reference);
        ste.setString(2, r.getNom_d());
        ste.setString(3, r.getPrenom_d());
        ste.setInt(4, r.getCin());
        ste.setString(5, r.getEmail());
        ste.setString(6, r.getCommentaire());
        ste.setString(7, r.getTel());
        ste.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
        ste.setString(9, "En cours");
        ste.setString(10, r.getFile());
        ste.executeUpdate();
        // Définir la référence générée pour la nouvelle réclamation
        r.setReference(reference);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("La réclamation a été ajoutée avec succès.");
        alert.showAndWait();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}





     public void redirectToList() {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("Reclamations.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) retour.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        Logger.getLogger(AjouterReclamationController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    @FXML
    private void retour(MouseEvent event) {
        this.redirectToList();
    }


    
}
