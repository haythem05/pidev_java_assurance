/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.assurance.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import tn.assurance.models.Categorie;
import tn.assurance.models.Habitation;
import tn.assurance.services.categorieS;
import tn.assurance.services.habitationS;

/**
 * FXML Controller class
 *
 * @author haythem
 */
public class AfficherhabitationController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private ListView<Habitation> listView;
    
static public int id,idclient,nbpieceimmobilier;
static public float     capitalmobilier,capitalimmobilier,devis;
 Categorie  type_Id;



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ListView list2 = listView;
           Habitation h= new Habitation();
           habitationS hs = new habitationS();
           List<Habitation>liste = hs.afficherHabitation();
           for(int i=0; i<liste.size();i++)
           {
               Habitation h1 = liste.get(i);
               list2.getItems().add(h1);
    }    
    }
    @FXML
    private void supprimer(ActionEvent event) {
         ListView<Habitation> list_supp = listView;
        habitationS hs = new habitationS();
        int selectedID = list_supp.getSelectionModel().getSelectedIndex();
        Habitation h = list_supp.getSelectionModel().getSelectedItem(); 
        
        hs.supprimerHabitation(h.getId());       
        list_supp.getItems().remove(selectedID);
        
    }

    @FXML
    private void modifier(ActionEvent event) {
    }

    @FXML
    private void ajouter(ActionEvent event) throws IOException {
          AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/assurance/gui/ajouterHabitationFront.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
}
