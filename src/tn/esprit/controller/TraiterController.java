/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import tn.esprit.entities.Sinistre;
import tn.esprit.entities.Type;
import tn.esprit.services.SinistreService;


/**
 * FXML Controller class
 *
 * @author HD
 */
public class TraiterController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private ChoiceBox<String> choixstatut;
    
    private String lieu, statut, degats, description;
    public String url_image;
    private Timestamp date_heure;
    private Type type;
    
    private Sinistre sinistre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        choixstatut.setValue(AfficherBackController.sin.getStatut());
        choixstatut.getItems().addAll("En attente de traitement", "En cours de traitement", "Traité", "Refusé");

    }

    @FXML
    private void traiter(ActionEvent event) {
        try {
            int id = AfficherBackController.id;

            SinistreService ss = new SinistreService();
            String nouveauStatut = choixstatut.getValue();
            ss.traiter(AfficherBackController.sin, id, nouveauStatut);
            

            AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/AfficherBack.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/AfficherBack.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
