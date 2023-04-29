package tn.assurance.gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
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
    private ChoiceBox<Categorie> type_Idh;

    private int idclient, nbpieceimmobilier;

    private float capitalimmobilier, capitalmobilier, devis;

    private Categorie type_id;

    private List<Categorie> categories;
    @FXML
    private ImageView imageV;

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
        type_id = type_Idh.getValue();

        // create new Habitation object with user input and selected category
        Habitation habitation = new Habitation(idclient, nbpieceimmobilier, capitalimmobilier, capitalmobilier, type_id);
habitationS hS= new  habitationS();
hS.ajouterHabitation(habitation, type_id);
  Image img = new Image("file:///C:/xampp/htdocs/logo.png");
               Notifications n = Notifications.create()
        .title("DevSquad")
        .text("Habitation  ajouteé !")
        .graphic(new ImageView(img))
        .position(Pos.BOTTOM_RIGHT);
    
    n.darkStyle();
    n.showInformation();


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
