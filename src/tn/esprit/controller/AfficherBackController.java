/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import tn.esprit.entities.Sinistre;
import tn.esprit.services.SinistreService;

/**
 * FXML Controller class
 *
 * @author HD
 */
public class AfficherBackController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private ListView<Sinistre> list;
    @FXML
    private Button traiter;
    @FXML
    private Button stat;
    @FXML
    private Button tri_d;
    @FXML
    private Button tri_a;
    @FXML
    private TextField tf_recherche;

    static public String statut;
    static public int id;
    static public Sinistre sin;
    private SinistreService ss;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ListView list2 = list;
        Sinistre r = new Sinistre();
        ss = new SinistreService();
        List<Sinistre> liste = ss.afficher();
        for (int i = 0; i < liste.size(); i++) {
            Sinistre s = liste.get(i);
            list2.getItems().add(s);
        }
        updateListView();
        tf_recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            updateListView(newValue);
        });
    }

    public void updateListView() {
        List<Sinistre> liste = ss.getAllSinistres();
        list.getItems().setAll(liste);
    }
    
    public void updateListView(String search){
        List<Sinistre> liste = ss.searchSinistres(search);
        list.getItems().setAll(liste);
    }

    @FXML
    private void traiter(ActionEvent event) {
        ListView<Sinistre> liste = (ListView<Sinistre>) list;
        sin = liste.getSelectionModel().getSelectedItem();
        id = sin.getId();

        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/Traiter.fxml"));
            rootPane.getChildren().setAll(pane);

        } catch (IOException ex) {
            System.err.println(ex);

        }
    }

    @FXML
    private void stat(ActionEvent event) {
    }

}
