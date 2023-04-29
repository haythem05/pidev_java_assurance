
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.entities.Sinistre;
import tn.esprit.services.SinistreService;

/**
 * FXML Controller class
 *
 * @author HD
 */
public class AffichageFrontController implements Initializable {

    private ListView<Sinistre> list;
    File selectedFile;
    static public String lieu, degats, description, url_image;
    static public Timestamp date_heure;
    static public int id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ListView list2 = list;
        Sinistre r = new Sinistre();
        SinistreService ss = new SinistreService();
        List<Sinistre> liste = ss.afficher();
        for (int i = 0; i < liste.size(); i++) {
            Sinistre s = liste.get(i);
            list2.getItems().add(s);
        }
    }

    private void ajouter(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/gui/Ajout.fxml"));
        Parent root = loader.load();

        // Get the current stage and set the new scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void modifier(ActionEvent event) {
        ListView<Sinistre> liste = list; // assuming listView is a ListView<CoVoiturage>

        Sinistre s = liste.getSelectionModel().getSelectedItem(); // use getSelectedItem() to get the selected item, not getSelectedItems()*

        id = s.getId();
        lieu = s.getLieu();
        degats = s.getDegats();
        description = s.getDescription();
        url_image = s.getFile();
        date_heure = s.getDate_heure();

        try {
            if (s.getStatut().equals("En cours de traitement") || s.getStatut().equals(" En cours de traitement") || s.getStatut().equals("  En cours de traitement")) {
                Alert alerte = new Alert(Alert.AlertType.ERROR);
                alerte.setTitle("Erreur de modification");
                alerte.setHeaderText(null);
                alerte.setContentText("Impossible de modifier ce sinistre car son traitement a déjà commencé.");
                alerte.showAndWait();
                return;
            } else if (s.getStatut().equals("Traité") || s.getStatut().equals(" Traité") || s.getStatut().equals("  Traité")) {
                Alert alerte = new Alert(Alert.AlertType.ERROR);
                alerte.setTitle("Erreur de modification");
                alerte.setHeaderText(null);
                alerte.setContentText("Impossible de modifier ce sinistre car il a déjà été traité.");
                alerte.showAndWait();
                return;
            } else if (s.getStatut().equals("Refusé") || s.getStatut().equals(" Refusé") || s.getStatut().equals("  Refusé")) {
                Alert alerte = new Alert(Alert.AlertType.ERROR);
                alerte.setTitle("Erreur de modification");
                alerte.setHeaderText(null);
                alerte.setContentText("Impossible de modifier ce sinistre car il a été refusé.");
                alerte.showAndWait();
                return;
            } else if (s.getStatut().equals("En attente de traitement") || s.getStatut().equals(" En attente de traitement") || s.getStatut().equals("  En attente de traitement")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/gui/Modification.fxml"));
                Parent root = loader.load();

                // Get the current stage and set the new scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException ex) {
            System.err.println(ex);

        }
    }

    private void supprimer(ActionEvent event) {
        ListView<Sinistre> list_supp = list;
        SinistreService ss = new SinistreService();
        int selectedID = list_supp.getSelectionModel().getSelectedIndex();
        Sinistre s = list_supp.getSelectionModel().getSelectedItem();

        ss.supprimer(s.getId());
        list_supp.getItems().remove(selectedID);
    }

}