package tn.assurance.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import tn.assurance.models.Habitation;
import tn.assurance.services.habitationS;

public class ModifierHabitationController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField idclienth;
    @FXML
    private TextField nbpieceh;
    @FXML
    private TextField capitalimmobilierh;
    @FXML
    private TextField capitalmobilierh;
    private TextField devish;
    @FXML
    private ImageView imageV;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idclienth.setText(Integer.toString(AfficherhabitationController.idclient));
        nbpieceh.setText(Integer.toString(AfficherhabitationController.nbpieceimmobilier));
        capitalmobilierh.setText(Float.toString(AfficherhabitationController.capitalmobilier));
        capitalimmobilierh.setText(Float.toString(AfficherhabitationController.capitalimmobilier));
        devish.setText(Float.toString(AfficherhabitationController.devis));
    }

   @FXML
private void modifier(ActionEvent event) throws IOException {
    int id = AfficherhabitationController.id;
    int idclient = Integer.parseInt(idclienth.getText());
    int nbpieceimmobilier = Integer.parseInt(nbpieceh.getText());
    float capitalmobilier = Float.parseFloat(capitalmobilierh.getText());
    float capitalimmobilier = Float.parseFloat(capitalimmobilierh.getText());

    
    Habitation habitation = new Habitation(idclient, nbpieceimmobilier, capitalimmobilier, capitalmobilier);
    habitationS hS = new habitationS();
    hS.modifierHabitation(habitation, id);
    
    AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/assurance/gui/afficherhabitation.fxml"));
    rootPane.getChildren().setAll(pane);
}
}
