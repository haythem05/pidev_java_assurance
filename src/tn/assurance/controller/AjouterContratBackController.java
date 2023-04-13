/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.assurance.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tn.assurance.models.Categorie;
import tn.assurance.models.Contrat;
import tn.assurance.services.categorieS;
import tn.assurance.services.contratS;

/**
 * FXML Controller class
 *
 * @author haythem
 */
public class AjouterContratBackController implements Initializable {

    @FXML
    private ChoiceBox<Categorie> typeidc;
    @FXML
    private TextField idclientc;
    @FXML
    private TextField nbdeplacec;
    @FXML
    private TextField valeurcataloguec;
    @FXML
    private TextField prixc;
    @FXML
    private TextField avantagesc;
    @FXML
    private TextField modelc;
    @FXML
    private DatePicker datedebutc;
    @FXML
    private DatePicker datefinc;
    @FXML
    private DatePicker datecirculationc;
    @FXML
    private TextField marquec;

    private int id, idclient, nbplace;
    private float valeurcatalogue, prix;
    private Date datedebut, datefin, datecirculation;
    private String avantages, marque, modele;
    Categorie type_Id;
    private Categorie type_id;

    private List<Categorie> categories;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categories = new categorieS().afficherCategorie();
        typeidc.getItems().addAll(categories);
        
    }

    @FXML
    private void valider(ActionEvent event) {
        idclient = Integer.parseInt(idclientc.getText());
        nbplace = Integer.parseInt(nbdeplacec.getText());
        valeurcatalogue = Float.parseFloat(valeurcataloguec.getText());
        prix = Float.parseFloat(prixc.getText());

LocalDate d = datedebutc.getValue();
datedebut = java.sql.Date.valueOf(d);

LocalDate d3= datecirculationc.getValue();
datecirculation = java.sql.Date.valueOf(d3);

LocalDate d2 = datefinc.getValue();
datefin = java.sql.Date.valueOf(d2);
type_Id=typeidc.getValue();

                            avantages = avantagesc.getText();
                            marque = marquec.getText();
                            modele = modelc.getText();
                        
                            


 
    
    
                Contrat c = new Contrat( idclient,  nbplace,  valeurcatalogue,  prix,  datedebut,  datefin,  datecirculation,  avantages,  marque,  modele,  type_Id);
                contratS cs=new contratS();
                cs.ajouterContrat(c, type_Id);
                
       

    }

}
