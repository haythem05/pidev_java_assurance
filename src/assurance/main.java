/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assurance;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author haythem
 */
public class main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
             //Parent root = FXMLLoader.load(getClass().getResource("/tn/assurance/gui/ajouterCategorieBackfxml.fxml")); 
     //Parent root = FXMLLoader.load(getClass().getResource("/tn/assurance/gui/affichercategorie.fxml"));  
           // Parent root = FXMLLoader.load(getClass().getResource("/tn/assurance/gui/ajouterHabitationFront.fxml"));  
         
      //   Parent root = FXMLLoader.load(getClass().getResource("/tn/assurance/gui/afficherhabitation.fxml"));  
  Parent root = FXMLLoader.load(getClass().getResource("/tn/assurance/gui/afficherContrat.fxml"));  
  
           
        
            Scene scene = new Scene(root);

            primaryStage.setTitle("DevSquad");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}