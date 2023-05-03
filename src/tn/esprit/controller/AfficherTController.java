/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import tn.esprit.entities.Type;
import tn.esprit.services.TypeService;


/**
 * FXML Controller class
 *
 * @author HD
 */
public class AfficherTController implements Initializable {

    @FXML
    private ListView<?> listT;
    static public String nom;
    static public int id;
    @FXML
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ListView list2 = listT;
        Type r = new Type();
        TypeService ss = new TypeService();
        List<Type> liste = ss.afficher();
        for (int i = 0; i < liste.size(); i++) {
            Type s = liste.get(i);
            list2.getItems().add(s);
        }
    }    

    @FXML
    private void ajouterT(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/AjouterT.fxml"));
        rootPane.getChildren().setAll(pane);
        
    }

    @FXML
    private void modifierT(ActionEvent event) {
        ListView<Type> liste = (ListView<Type>) listT; // assuming listView is a ListView<CoVoiturage>
        Type t = liste.getSelectionModel().getSelectedItem(); // use getSelectedItem() to get the selected item, not getSelectedItems()*

        id = t.getId();
        nom = t.getNom();
        
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/ModifierT.fxml"));
            rootPane.getChildren().setAll(pane);

        } catch (IOException ex) {
            System.err.println(ex);

        }
    }

    @FXML
    private void supprimerT(ActionEvent event) {
        ListView<Type> list_supp = (ListView<Type>) listT; // assuming listView is a ListView<CoVoiturage>
        TypeService ts = new TypeService();
        int selectedID = list_supp.getSelectionModel().getSelectedIndex();
        Type t = list_supp.getSelectionModel().getSelectedItem(); // use getSelectedItem() to get the selected item, not getSelectedItems()

        ts.supprimer(t.getId()); // assuming CoVoiturage has a method getId() to retrieve the unique ID of the object
        list_supp.getItems().remove(selectedID);
    }
    
}
