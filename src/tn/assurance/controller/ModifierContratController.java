/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.assurance.controller;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import tn.assurance.models.Categorie;
import tn.assurance.models.Contrat;
import tn.assurance.services.categorieS;
import tn.assurance.services.contratS;


/**
 * FXML Controller class
 *
 * @author haythem
 */
public class ModifierContratController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField idclientc;
    @FXML
    private TextField nbplacec;
    @FXML
    private TextField valeurCataloguec;
    @FXML
    private TextField prixc;
    @FXML
    private TextArea avantagesc;
    @FXML
    private DatePicker datedebutc;
    @FXML
    private DatePicker dateFinc;
    @FXML
    private DatePicker datecirculationC;
    @FXML
    private TextField marquec;
    @FXML
    private TextField modelc;

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  
        idclientc.setText(Integer.toString(AfficherContratController.idclient));
        nbplacec.setText(Integer.toString(AfficherContratController.nbplace));
        valeurCataloguec.setText(Float.toString(AfficherContratController.valeurcatalogue));

        avantagesc.setText(AfficherContratController.avantages);
        prixc.setText(Float.toString(AfficherContratController.prix));
        marquec.setText(AfficherContratController.marque);
        modelc.setText(AfficherContratController.modele);
        
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    datedebutc.setValue(LocalDate.parse(dateFormat.format(AfficherContratController.datedebut)));
    dateFinc.setValue(LocalDate.parse(dateFormat.format(AfficherContratController.datefin)));
    datecirculationC.setValue(LocalDate.parse(dateFormat.format(AfficherContratController.datecirculation)));
}    
        

    @FXML
    private void modifier(ActionEvent event){
        try {
            int id=AfficherContratController.id;
            
            int idclient =Integer.parseInt(idclientc.getText());
            int nbplace=Integer.parseInt(nbplacec.getText());
            
            float valeurcatalogue=Integer.parseInt(nbplacec.getText());
          
                        
            String avantages=avantagesc.getText();
            float prix=Float.parseFloat(prixc.getText());
            String modele=modelc.getText();
                    String marque=marquec.getText();
         LocalDate d = datedebutc.getValue();
         
        Date datedebut = java.sql.Date.valueOf(d);
 Date datecirculation = java.sql.Date.valueOf(d);
 Date datefin = java.sql.Date.valueOf(d);


            
            
                 Contrat c = new Contrat( idclient,  nbplace,  valeurcatalogue,  prix,  datedebut,  datefin,  datecirculation,  avantages,  marque,  modele);
                contratS cs=new contratS();
  
            
            
            

  
         cs.modifierContrat(c, id);
         
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/assurance/gui/afficherContrat.fxml"));  
        
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}

