/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.assurance.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import tn.assurance.models.Categorie;
import tn.assurance.models.Contrat;
import tn.assurance.models.Habitation;
import tn.assurance.services.contratS;
import tn.assurance.services.habitationS;

/**
 * FXML Controller class
 *
 * @author haythem
 */
public class AfficherContratController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private ListView<Contrat> listView;

      private int id,idclient,nbplace;
    private float valeurcatalogue,prix;
    private Date datedebut,datefin,datecirculation;
    private String avantages,marque,modele;
    Categorie  type_Id;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
           ListView list2 = listView;
           Contrat c= new Contrat();
           contratS cs = new contratS();
           List<Contrat>liste = cs.afficherContrat();
           for(int i=0; i<liste.size();i++)
           {
               Contrat  c1 = liste.get(i);
               list2.getItems().add(c1);

    }    
    
}

    @FXML
    private void modifier(ActionEvent event) {
    }

    @FXML
    private void ajouter(ActionEvent event)throws IOException {
          AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/assurance/gui/ajouterContratBack.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    private void supprimer(ActionEvent event) {
           ListView<Contrat> list_supp = listView;
         contratS cs = new contratS();
        int selectedID = list_supp.getSelectionModel().getSelectedIndex();
         Contrat  c = list_supp.getSelectionModel().getSelectedItem(); 
        
        cs.supprimerContrat(c.getId());    
        list_supp.getItems().remove(selectedID);
        
    }
}
    
