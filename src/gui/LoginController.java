/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import entities.Utilisateur;
import java.sql.PreparedStatement;
import static java.time.Clock.system;

import java.io.IOException;

import services.UtilisateurService;

/**
 * FXML Controller class
 *
 * @author Ranim
 */
public class LoginController implements Initializable {

    @FXML
    private TextField loginmailtf;
    @FXML
    private TextField loginmdptf;
    @FXML
    private Button validloginbtn;
    @FXML
    private Label loginemaill;
    @FXML
    private Label loginmdpl;
     

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws IOException {

    String email = loginmailtf.getText();
    String password = loginmdptf.getText();

    // Vérifier si les champs d'identifiant et de mot de passe sont remplis
    if (email.isEmpty() || password.isEmpty()) {
        // Afficher un message d'erreur
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs!");
        alert.showAndWait();
        return;
    }

    // Vérifier si les identifiants sont valides en appelant la méthode checkCredentials de UtilisateurService
    boolean isValidCredentials = new UtilisateurService().checkCredentials(email, password);

    if (isValidCredentials) {
        // Afficher un message de succès
        Parent root = FXMLLoader.load(getClass().getResource("homeusers.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } else {
        // Afficher un message d'erreur
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Identifiants invalides!");
        alert.showAndWait();
    }
}

    @FXML
    void toRegister(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("signup.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    
    
    /*@FXML
    private void handleLoginButtonAction(ActionEvent event) {
        
                
    String email = loginmailtf.getText();
    String password = loginmdptf.getText();
    
    // Vérifier si les champs d'identifiant et de mot de passe sont remplis
    if (email.isEmpty() || password.isEmpty()) {
        // Afficher un message d'erreur
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs!");
        alert.showAndWait();
        return;
    }
    
    // Vérifier si les identifiants sont valides (par exemple, en les vérifiant avec une base de données)
    boolean isValidCredentials = checkCredentials(email, password);
    
    if (isValidCredentials) {
        // Si les identifiants sont valides, ouvrir la fenêtre principale de l'application
        System.out.println("Connexion réussie!");
        // Code pour ouvrir la fenêtre principale ici
    } else {
        // Si les identifiants ne sont pas valides, afficher un message d'erreur
        System.out.println("Identifiants incorrects!");
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Identifiants incorrects!");
        alert.showAndWait();
    }*/
}

 
    

