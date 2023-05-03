/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import tn.esprit.entities.Sinistre;
import tn.esprit.entities.Type;
import tn.esprit.services.SinistreService;
import tn.esprit.services.TypeService;

/**
 * FXML Controller class
 *
 * @author HD
 */
public class ModifierTController implements Initializable {

    @FXML
    private TextField tf_nom;
    @FXML
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tf_nom.setText(String.valueOf(TypeBackController.typ.getNom()));
    }    

    @FXML
    private void modifierT(ActionEvent event) {
        try {
            int id = TypeBackController.id;
            String nom = tf_nom.getText();
            
            Type t = new Type(nom);
            TypeService ts = new TypeService();
            ts.modifier(t, id);
            
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/TypeBack.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/TypeBack.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
}
