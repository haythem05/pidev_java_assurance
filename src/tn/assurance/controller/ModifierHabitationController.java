package tn.assurance.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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

     @FXML
private void retour(ActionEvent event) throws IOException {
    // Load the affichercategorie.fxml file
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/assurance/gui/afficherhabitation.fxml"));
    Parent root = loader.load();

    // Get the current stage and set the new scene
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
}
}
