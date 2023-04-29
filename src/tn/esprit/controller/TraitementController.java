/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.entities.Sinistre;
import tn.esprit.entities.Type;
import tn.esprit.gui.AffichageBackController;
import tn.esprit.services.Emailsender;
import tn.esprit.services.SinistreService;

/**
 * FXML Controller class
 *
 * @author HD
 */
public class TraitementController implements Initializable {

    @FXML
    private ChoiceBox<String> choixstatut;
    private String lieu, statut, degats, description;
    public String url_image;
    private Timestamp date_heure;
    private Type type;

    private Sinistre sinistre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        choixstatut.setValue(AffichageBackController.sin.getStatut());
        choixstatut.getItems().addAll("En attente de traitement", "En cours de traitement", "Traité", "Refusé");
    }    

    @FXML
    private void traiter(ActionEvent event) {
        try {
            int id = AffichageBackController.id;

            SinistreService ss = new SinistreService();
            String nouveauStatut = choixstatut.getValue().trim();
            ss.traiter(AffichageBackController.sin, id, nouveauStatut);
            switch (nouveauStatut) {
                case "En cours de traitement":
                    Emailsender.sendEmail_add("ines.bessaad@esprit.tn", "Merci pour votre confiance.\nVotre sinistre est en cours de traitement. \nOn vous notifira en cas de mise à jour.\n\nÀ bientôt.");
                    break;
                case "Traité":
                    Emailsender.sendEmail_add("ines.bessaad@esprit.tn", "Félicitations!\nVotre sinistre a bien été traité. \nMerci pour votre confiance et restez prudents.\n\nÀ bientôt.");
                    break;
                case "Refusé":
                    Emailsender.sendEmail_add("ines.bessaad@esprit.tn", "Malheureusement, nous ne pouvons pas traiter votre sinistre.\nVeuillez nous contacter sur le +216 58 343 230 pour avoir plus de détails.\n\nÀ bientôt.");
                    break;
                default:
                    break;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/gui/AffichageBack.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/gui/AffichageFront.fxml"));
        Parent root = loader.load();

        // Get the current stage and set the new scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
