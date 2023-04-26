/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tn.assurance.controller;

import java.io.File;
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
import tn.assurance.services.categorieS;

/**
 * FXML Controller class
 *
 * @author haythem
 */
public class affichercategorieController implements Initializable {

    @FXML
    private AnchorPane rootPane;
      File selectedFile;
      static public String nom,description,url_image;
      static public int id;
    @FXML
    private ListView<Categorie> listView;
      
      

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    
       ListView list2 = listView;
           Categorie c= new Categorie();
           categorieS cc = new categorieS();
           List<Categorie>liste =cc.afficherCategorie();
           for(int i=0; i<liste.size();i++)
           {
               Categorie c1 = liste.get(i);
               list2.getItems().add(c1);
    }    
    }
    


    @FXML
    private void ajouter(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/assurance/gui/ajouterCategorieBackfxml.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    private void modifier(ActionEvent event) throws IOException {
        
        ListView<Categorie> list = listView;
        
        Categorie c = list.getSelectionModel().getSelectedItem(); // use getSelectedItem() to get the selected item, not getSelectedItems()*
        
        id = c.getId();
        nom = c.getNom();
        description = c.getDescription();
        url_image = c.getCategorieimage();
        
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/assurance/gui/modifiercategorie.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    private void supprimer(ActionEvent event) {
        ListView<Categorie> list_supp = listView;
        categorieS cs = new categorieS();
        int selectedID = list_supp.getSelectionModel().getSelectedIndex();
        Categorie c = list_supp.getSelectionModel().getSelectedItem(); 
        
        cs.supprimerCategorie(c.getId()); 
        list_supp.getItems().remove(selectedID);
    }
    
}
