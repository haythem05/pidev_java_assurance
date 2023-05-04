/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.assurance.controller;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.Notifications;
import tn.assurance.models.Categorie;
import tn.assurance.models.Contrat;
import tn.assurance.services.categorieS;
import tn.assurance.services.contratS;
import java.time.LocalDate;
import java.sql.Date;
import java.time.LocalDate;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


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
    // Get input values
    try {
        idclient = Integer.parseInt(idclientc.getText());
        nbplace = Integer.parseInt(nbdeplacec.getText());
        valeurcatalogue = Float.parseFloat(valeurcataloguec.getText());
    } catch (NumberFormatException e) {
        showAlert("Erreur:   les champs sont vides.");
        return;
    }

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

    // Check if input values meet the required criteria
    if (nbplace < 2 || nbplace > 8) {
        showAlert("Nombre de places doit être entre 2 et 8");
        return;
    }
    if (valeurcatalogue < 15000 || valeurcatalogue > 2500000) {
        showAlert("Valeur catalogue doit être entre 15000 et 2500000");
        return;
    }
if (datedebut.toLocalDate().isBefore(LocalDate.now())) {
    showAlert("Date de début doit être égale à aujourd'hui ou ultérieure");
    return;
}
if (datecirculation.toLocalDate().isAfter(LocalDate.now())) {
    showAlert("Date de circulation doit être antérieure ou égale à aujourd'hui");
    return;
}
if (datefin.toLocalDate().isBefore(datedebut.toLocalDate().plusDays(1))) {
    showAlert("Date de fin doit être supérieure à la date de début d'au moins un jour");
    return;
}
if (datefin.toLocalDate().isAfter(datedebut.toLocalDate().plusYears(1))) {
    showAlert("Date de fin doit être inférieure à la date de début d'un an au maximum");
    return;
}
    if (type_Id == null) {
        showAlert("Type de contrat ne doit pas être vide");
        return;
    }
    if (avantages.isEmpty()) {
        showAlert("Avantages ne doivent pas être vides");
        return;
    }
    if (marque.isEmpty()) {
        showAlert("Marque ne doit pas être vide");
        return;
    }
    if (modele.isEmpty()) {
        showAlert("Modèle ne doit pas être vide");
        return;
    }

    // Create and add the contract to the database
    Contrat c = new Contrat(idclient, nbplace, valeurcatalogue, datedebut, datefin, datecirculation, avantages, marque, modele, type_Id);
    contratS cs = new contratS();
    cs.ajouterContrat(c, type_Id);

    // Show success notification
    Image img = new Image("file:///C:/xampp/htdocs/logo.png");
    Notifications n = Notifications.create()
        .title("DevSquad")
        .text("Contrat automobile ajouté !")
        .graphic(new ImageView(img))
        .position(Pos.BOTTOM_RIGHT);
    n.darkStyle();
    n.showInformation();
}


private void showAlert(String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Erreur de saisie");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}

    @FXML
private void retour(ActionEvent event) throws IOException {
    // Load the affichercategorie.fxml file
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/assurance/gui/ContratFront.fxml"));
    Parent root = loader.load();

    // Get the current stage and set the new scene
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
}



}
