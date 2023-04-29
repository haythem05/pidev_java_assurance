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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author haythem
 */
public class HomeController implements Initializable {

    @FXML
    private ImageView imageV;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         imageV.setImage(new javafx.scene.image.Image("file:C:\\Users\\haythem\\Documents\\NetBeansProjects\\Assurance\\build\\classes\\images\\logo.png"));
    }    

    @FXML
    private void gotohabitation(ActionEvent event) throws IOException {
    // Load the affichercategorie.fxml file
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/assurance/gui/afficherhabitation.fxml"));
    Parent root = loader.load();

    // Get the current stage and set the new scene
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
}
    

    @FXML
    private void gotoCategorie(ActionEvent event) throws IOException {
    // Load the affichercategorie.fxml file
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/assurance/gui/affichercategorie.fxml"));
    Parent root = loader.load();

    // Get the current stage and set the new scene
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    }
    

    @FXML
    private void gotoautomobile(ActionEvent event) throws IOException {
    // Load the affichercategorie.fxml file
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/assurance/gui/afficherContrat.fxml"));
    Parent root = loader.load();

    // Get the current stage and set the new scene
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    }
    
}
