/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.entities.Sinistre;
import tn.esprit.services.SinistreService;

/**
 * FXML Controller class
 *
 * @author HD
 */
public class FrontModifierController implements Initializable {

    @FXML
    private DatePicker date;
    @FXML
    private TextField tf_lieu;
    @FXML
    private TextField tf_description;
    @FXML
    private TextField tf_degats;
    @FXML
    private ImageView imageV;
    private String url_im;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        date.setPromptText(ItemController.date);
        tf_lieu.setText(String.valueOf(ItemController.lieu));
        tf_description.setText(String.valueOf(ItemController.description));
        tf_degats.setText(String.valueOf(ItemController.degats));
        String imagePath = "C:\\Users\\HD\\Desktop\\Installations\\XAMPP\\htdocs\\imagePi\\" + ItemController.url_image;
        try {
            imageV.setImage(new Image(new FileInputStream(imagePath)));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FrontModifierController.class.getName()).log(Level.SEVERE, null, ex);
        }
        url_im = imagePath;
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/gui/Front.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void modifier(ActionEvent event) {
        try {
            int id = ItemController.s.getId();
            LocalDate d = date.getValue();
            Timestamp date_heure = Timestamp.valueOf(d.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime());
            String lieu = tf_lieu.getText();
            String degats = tf_degats.getText();
            String description = tf_description.getText();
            //Sinistre si = ss.rechS(id);
            //String statut = si.getStatut();

            if (lieu.isEmpty() || degats.isEmpty() || description.isEmpty() || date_heure == null) {
                // Afficher un message d'erreur si un champ obligatoire est vide
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs obligatoires.");
                alert.showAndWait();
                return;
            }

            LocalDate now = LocalDate.now();
            if (d.isAfter(now)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Erreur de saisie");
                alert.setContentText("La date sélectionnée ne peut pas dépasser la date actuelle.");
                alert.showAndWait();
                return;
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modification réussie");
            alert.setHeaderText(null);
            alert.setContentText("Le sinistre a été modifié avec succès dans la base de données.");
            alert.showAndWait();

            Sinistre s = new Sinistre(date_heure, lieu, degats, description, url_im);
            SinistreService ss = new SinistreService();
            ss.modifiersanst(s, id);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/gui/Front.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

}
