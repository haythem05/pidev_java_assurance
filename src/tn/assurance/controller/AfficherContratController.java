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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import tn.assurance.models.Categorie;
import tn.assurance.models.Contrat;
import tn.assurance.services.contratS;


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
       static public  int id;

  static public  int idclient, nbplace;
    static public  float valeurcatalogue, prix;
 static  public  Date datedebut, datefin, datecirculation;
 static  public  String avantages, marque, modele;
    Categorie type_Id;
    @FXML
    private TextField searchField;
private contratS contratService;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ListView list2 = listView;
        Contrat c = new Contrat();
        contratS cs = new contratS();
        List<Contrat> liste = cs.afficherContrat();
        for (int i = 0; i < liste.size(); i++) {
            Contrat c1 = liste.get(i);
            list2.getItems().add(c1);

        }
          contratService = new contratS();
        updateListView();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateListView(newValue);
        });

    }

    @FXML
    private void modifier(ActionEvent event) throws IOException {

        ListView<Contrat> list = listView;

        Contrat c = list.getSelectionModel().getSelectedItem(); // use getSelectedItem() to get the selected item, not getSelectedItems()*

        id = c.getId();
        idclient = c.getIdclient();
        nbplace = c.getNbplace();
        valeurcatalogue = c.getValeurcatalogue();
        prix = c.getPrix();
        datedebut = c.getDatedebut();
        datefin = c.getDatefin();
        datecirculation = c.getDatecirculation();
        avantages = c.getAvantages();
        marque = c.getMarque();
        modele = c.getModele();

        AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/assurance/gui/modifierContrat.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    private void ajouter(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/assurance/gui/ajouterContratBack.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    private void supprimer(ActionEvent event) {
        ListView<Contrat> list_supp = listView;
        contratS cs = new contratS();
        int selectedID = list_supp.getSelectionModel().getSelectedIndex();
        Contrat c = list_supp.getSelectionModel().getSelectedItem();

        cs.supprimerContrat(c.getId());
        list_supp.getItems().remove(selectedID);

    }
    
    
    


    private void updateListView() {
        List<Contrat> contrats = contratService.getAllContrats();
        listView.getItems().setAll(contrats);
    }


    private void updateListView(String search) {
        List<Contrat> contrats = contratService.searchContrats(search);
        listView.getItems().setAll(contrats);
    }

}
