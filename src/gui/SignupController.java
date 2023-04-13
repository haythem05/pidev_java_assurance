/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javax.mail.MessagingException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import entities.Utilisateur;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import static java.time.Clock.system;

import java.io.IOException;

import services.UtilisateurService;

/**
 * FXML Controller class
 *
 * @author Ranim
 */
public class SignupController implements Initializable {

    @FXML
    private TextField nomInput;
    @FXML
    private TextField prenomInput;
    @FXML
    private TextField emailInput;
    @FXML
    private TextField cinInput;
    @FXML
    private TextField numtelInput;
    @FXML
    private TextField mdpInput;
    @FXML
    private DatePicker dateInput;
    @FXML
    private Button validerButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleRegisterButtonAction(ActionEvent event) throws IOException {

        String nom = nomInput.getText();
        String prenom = prenomInput.getText();
        String cinText = cinInput.getText();
        int cin = Integer.parseInt(cinText);
        String numtelText = numtelInput.getText();
        int numTel = Integer.parseInt(numtelText);
        String email = emailInput.getText();
        String password = mdpInput.getText();
        LocalDate localDate = dateInput.getValue();
        Date datedenaissance = Date.valueOf(localDate);

        // Vérifier si les champs d'identifiant et de mot de passe sont remplis
        if (email.isEmpty() || password.isEmpty() || cinText.isEmpty() || numtelText.isEmpty() || nom.isEmpty()
                || prenom.isEmpty() || localDate == null) {
            // Afficher un message d'erreur
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs!");
            alert.showAndWait();
            return;
        }

        UtilisateurService utilisateurService = new UtilisateurService();

        Utilisateur user = new Utilisateur();

        user.setNom(nom);
        user.setPrenom(prenom);
        user.setCin(cin);
        user.setDatedenaissance(datedenaissance);
        user.setPassword(password);
        user.setNumtel(numTel);
        user.setEmail(email);

        utilisateurService.ajouter(user);
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
}
