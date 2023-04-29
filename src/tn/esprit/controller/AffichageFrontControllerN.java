/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import static tn.esprit.controller.AfficherController.date_heure;
import static tn.esprit.controller.AfficherController.degats;
import static tn.esprit.controller.AfficherController.description;
import static tn.esprit.controller.AfficherController.id;
import static tn.esprit.controller.AfficherController.lieu;
import static tn.esprit.controller.AfficherController.url_image;
import tn.esprit.entities.Sinistre;
import tn.esprit.services.SinistreService;

/**
 * FXML Controller class
 *
 * @author HD
 */
public class AffichageFrontControllerN implements Initializable {

    @FXML
    private ListView<Sinistre> list;
    @FXML
    private BorderPane rootPane;
    @FXML
    private VBox vboxutil;
    File selectedFile;
     public static String lieu, degats, description, url_image;
    static public Timestamp date_heure;
    static public int id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    
}
