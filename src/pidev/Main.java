/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev;

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
 * @author HD
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            //Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/Afficher.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/AfficherBack.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/AfficherT.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/AffichageFront.fxml"));
            Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/AffichageBack.fxml"));

            Scene scene = new Scene(root,800,550);

            /*String css = this.getClass().getResource("/edu/webuild/gui/style.css").toExternalForm();
            scene.getStylesheets().add(css);*/
            primaryStage.setTitle("SecurAssur");
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
