/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.controller;

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
import tn.esprit.entities.Reponse;
import tn.esprit.services.ReponseService;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
/**
 * FXML Controller class
 *
 * @author MSI
 */
public class AjouterReponseController implements Initializable {

    @FXML
    private TextArea fxid_user;
    @FXML
    private TextArea fxnote;
    @FXML
    private ComboBox<Reclamation> fxcb;
    @FXML
    private Reclamation reclamation_id;
    @FXML
    private List<Reclamation> Reclamations;
    @FXML
    private Button ajout;
    @FXML
    private Button retour;

    public Connection cnx;
    public Statement stm;
    String sql = "";
    
    public void setReclamation(Reclamation reclamation) {
        fxcb.setValue(reclamation);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Reclamations = new ReclamationService().afficher();
        fxcb.getItems().addAll(Reclamations);

        Reponse rep = new Reponse();

        try {
            // Initialize your database connection here
            cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/assurancepidev", "root", "");
        } catch (SQLException ex) {
            System.out.println("Failed to connect to database: " + ex.getMessage());
        }

        ajout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                rep.setId_user(fxid_user.getText());
                rep.setNote(fxnote.getText());

                ajouter(rep);
            }
        });

        retour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                redirectToList();
            }
        });
    }

    public void ajouter(Reponse rep) {
        String id_user = fxid_user.getText();
        String note = fxnote.getText();
        reclamation_id = fxcb.getValue();
        
        // Vérifier si tous les champs sont vides
if (id_user.isEmpty() && note.isEmpty()) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Tous les champs sont obligatoires!");
    alert.showAndWait();
    return;
}

        // Validate input fields
        if (id_user == null || id_user.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Le champ id_user est obligatoire!");
            alert.showAndWait();
            return;
        }

        if (note == null || note.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Le champ note est obligatoire!");
            alert.showAndWait();
            return;
        } else if (note.length() < 10) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Le champ note doit contenir au moins 10 caractères.");
            alert.showAndWait();
            return;
        }

        // Ajouter la réponse avec la date de création
        sql = "insert into reponse(reclamation_id,id_user,note, created_at) values (?,?,?,?)";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, reclamation_id.getId());
            ste.setString(2, rep.getId_user());
            ste.setString(3, rep.getNote());
            ste.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

            ste.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("La réponse a été ajoutée avec succès.");
        alert.showAndWait();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    }

    public void redirectToList() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/Reponse.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) retour.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AjouterReponseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void retour(MouseEvent event) {
        this.redirectToList();
    }
}



