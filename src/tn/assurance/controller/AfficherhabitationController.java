/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.assurance.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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
    @FXML
    private ImageView imageV;



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          imageV.setImage(new javafx.scene.image.Image("file:C:\\Users\\haythem\\Documents\\NetBeansProjects\\Assurance\\build\\classes\\images\\logo.png"));
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
    private void modifier(ActionEvent event)  throws IOException {
        
        ListView<Habitation> list = listView;
        
        Habitation h = list.getSelectionModel().getSelectedItem(); // use getSelectedItem() to get the selected item, not getSelectedItems()*
        
        
        id = h.getId();
        idclient=h.getIdclient();
                nbpieceimmobilier=h.getNbpieceimmobilier();
                capitalimmobilier=h.getCapitalimmobilier();
                        capitalmobilier=h.getCapitalmobilier();
                        devis=h.getDevis();

        
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/assurance/gui/modifierHabitation.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    private void ajouter(ActionEvent event) throws IOException {
          AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/assurance/gui/ajouterHabitationFront.fxml"));
        rootPane.getChildren().setAll(pane);
    }
public List<Habitation> getHabitationsSortedAscDevis() {
      habitationS hs = new habitationS();
    List<Habitation> habitationList = hs.afficherHabitation();
    habitationList.sort(Comparator.comparing(Habitation::getDevis));
    return habitationList;
}

public List<Habitation> getHabitationsSortedDescDevis() {
       habitationS hs = new habitationS();
    List<Habitation> habitationList = hs.afficherHabitation();
    habitationList.sort(Comparator.comparing(Habitation::getDevis).reversed());
    return habitationList;
}

    
    @FXML
    private void sortAsc(ActionEvent event) {
       
    listView.getItems().clear();
    listView.getItems().addAll(getHabitationsSortedAscDevis());
    }

    @FXML
    private void sortDesc(ActionEvent event) {
         listView.getItems().clear();
    listView.getItems().addAll(getHabitationsSortedDescDevis());
    }

    @FXML
    private void home(ActionEvent event) throws IOException {
    // Load the affichercategorie.fxml file
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/assurance/gui/Home.fxml"));
    Parent root = loader.load();

    // Get the current stage and set the new scene
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
}
    
    
    
}
