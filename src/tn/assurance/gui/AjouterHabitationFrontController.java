package tn.assurance.gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import tn.assurance.models.Categorie;
import tn.assurance.models.Habitation;
import tn.assurance.services.categorieS;
import tn.assurance.services.habitationS;

public class AjouterHabitationFrontController implements Initializable {

    @FXML
    private TextField idclienth;

    @FXML
    private TextField nbpieceimmobilierh;

    @FXML
    private TextField capitalimmobilierh;

    @FXML
    private TextField capitalmobilierh;

    @FXML
    private TextField devish;

    @FXML
    private ChoiceBox<Categorie> type_Idh;

    private int idclient, nbpieceimmobilier;

    private float capitalimmobilier, capitalmobilier, devis;

    private Categorie type_id;

    private List<Categorie> categories;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // populate choice box with available categories
        categories = new categorieS().afficherCategorie();
        type_Idh.getItems().addAll(categories);
    }

    @FXML
    private void validerh(ActionEvent event) {

        // retrieve user input and selected category
        idclient = Integer.parseInt(idclienth.getText());
        nbpieceimmobilier = Integer.parseInt(nbpieceimmobilierh.getText());
        capitalimmobilier = Float.parseFloat(capitalimmobilierh.getText());
        capitalmobilier = Float.parseFloat(capitalmobilierh.getText());
        devis = Float.parseFloat(devish.getText());
        type_id = type_Idh.getValue();

        // create new Habitation object with user input and selected category
        Habitation habitation = new Habitation(idclient, nbpieceimmobilier, capitalimmobilier, capitalmobilier, devis, type_id);
habitationS hS= new  habitationS();
hS.ajouterHabitation(habitation, type_id);


    }

}