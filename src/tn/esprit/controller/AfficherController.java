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
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import tn.esprit.entities.Sinistre;
import tn.esprit.services.SinistreService;

/**
 * FXML Controller class
 *
 * @author HD
 */
public class AfficherController implements Initializable {

    @FXML
    private ListView<Sinistre> list;
    @FXML
    private AnchorPane rootPane;

    File selectedFile;
    static public String lieu, degats, description, url_image;
    static public Timestamp date_heure;
    static public int id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ListView list2 = list;
        Sinistre r = new Sinistre();
        SinistreService ss = new SinistreService();
        List<Sinistre> liste = ss.affichersanst();
        for (int i = 0; i < liste.size(); i++) {
            Sinistre s = liste.get(i);
            list2.getItems().add(s);
        }
    }

    @FXML
    private void modifier(ActionEvent event) {
        ListView<Sinistre> liste = list; // assuming listView is a ListView<CoVoiturage>

        SinistreService ss = new SinistreService();

        int selectedID = liste.getSelectionModel().getSelectedIndex();

        Sinistre s = liste.getSelectionModel().getSelectedItem(); // use getSelectedItem() to get the selected item, not getSelectedItems()*

        id = s.getId();
        lieu = s.getLieu();
        degats = s.getDegats();
        description = s.getDescription();
        url_image = s.getFile();
        date_heure = s.getDate_heure();
        
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/Modif.fxml"));
            rootPane.getChildren().setAll(pane);

        } catch (IOException ex) {
            System.err.println(ex);

        }
    }

    @FXML
    private void supprimer(ActionEvent event) {
        ListView<Sinistre> list_supp = list; // assuming listView is a ListView<CoVoiturage>
        SinistreService ss = new SinistreService();
        int selectedID = list_supp.getSelectionModel().getSelectedIndex();
        Sinistre s = list_supp.getSelectionModel().getSelectedItem(); // use getSelectedItem() to get the selected item, not getSelectedItems()

        ss.supprimer(s.getId()); // assuming CoVoiturage has a method getId() to retrieve the unique ID of the object
        list_supp.getItems().remove(selectedID);
    }

    @FXML
    private void ajouter(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/Ajouter.fxml"));
        rootPane.getChildren().setAll(pane);
    }

}
    