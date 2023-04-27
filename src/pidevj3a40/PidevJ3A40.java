/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevj3a40;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class PidevJ3A40 extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try {
           // FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/gui/Reclamations.fxml")); //admin
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/gui/AfficherReclamation.fxml")); //user
           
      
 // Correction du chemin du fichier FXML
            AnchorPane root = loader.load(); // Correction : chargement du fichier FXML dans un AnchorPane
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier FXML : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}

