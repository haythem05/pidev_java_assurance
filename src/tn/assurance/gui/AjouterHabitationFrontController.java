package tn.assurance.gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
    if (idclienth.getText().isEmpty() || nbpieceimmobilierh.getText().isEmpty() || capitalimmobilierh.getText().isEmpty() || capitalmobilierh.getText().isEmpty() || devish.getText().isEmpty()) {
        // show an error message and return if any field is empty
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("All fields are required.");
        alert.showAndWait();
        return;
    }

    // validate input for positive values
    try {
        idclient = Integer.parseInt(idclienth.getText());
        nbpieceimmobilier = Integer.parseInt(nbpieceimmobilierh.getText());
        capitalimmobilier = Float.parseFloat(capitalimmobilierh.getText());
        capitalmobilier = Float.parseFloat(capitalmobilierh.getText());
        devis = Float.parseFloat(devish.getText());

        if (idclient <= 0 || nbpieceimmobilier <= 0 || capitalimmobilier <= 0 || capitalmobilier <= 0 || devis <= 0) {
            // show an error message and return if any field has a non-positive value
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("All fields must have positive values.");
            alert.showAndWait();
            return;
        }
    } catch (NumberFormatException e) {
   
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("All fields must have numeric values.");
        alert.showAndWait();
        return;
    }

    // create new Habitation object with user input and selected category
    Habitation habitation = new Habitation(idclient, nbpieceimmobilier, capitalimmobilier, capitalmobilier, devis, type_id);
    habitationS hS = new habitationS();
    hS.ajouterHabitation(habitation, type_id);
}


}
