/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.assurance.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDate;
import java.time.ZoneId;
//import java.util.Date;
//import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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
    private String url_im;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        datedebutc.setPromptText(ItemcontratController.dated);
        dateFinc.setPromptText(ItemcontratController.datef);
        datecirculationC.setPromptText(ItemcontratController.datec);
        idclientc.setText(Integer.toString(ItemcontratController.s.getIdclient()));
        nbplacec.setText(Integer.toString(ItemcontratController.s.getNbplace()));
        valeurCataloguec.setText(Float.toString(ItemcontratController.s.getValeurcatalogue()));
        avantagesc.setText(ItemcontratController.s.getAvantages());
        prixc.setText(Float.toString(ItemcontratController.s.getPrix()));
        marquec.setText(ItemcontratController.s.getMarque());
        modelc.setText(ItemcontratController.s.getModele());
    }

    @FXML
    private void modifier(ActionEvent event) throws ParseException {
        try {
            /*int id = ItemController.s.getId();
            LocalDate d = date.getValue();
            Timestamp date_heure = Timestamp.valueOf(d.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime());
            String lieu = tf_lieu.getText();
            String degats = tf_degats.getText();
            String description = tf_description.getText();
            Sinistre s = new Sinistre(date_heure, lieu, degats, description, url_im);
            SinistreService ss = new SinistreService();
            ss.modifiersanst(s, id);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/gui/Front.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();*/
            int id = ItemcontratController.s.getId();
            int idclient = Integer.parseInt(idclientc.getText());
            int nbplace = Integer.parseInt(nbplacec.getText());
            float valeurcatalogue = Integer.parseInt(nbplacec.getText());
            String avantages = avantagesc.getText();
            float prix = Float.parseFloat(prixc.getText());
            String modele = modelc.getText();
            String marque = marquec.getText();
            //LocalDate d = datedebutc.getValue();
            //Date datedebut = (Date) Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant());
            LocalDate d = datedebutc.getValue();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date datedebut = null;
            try {
                datedebut = (Date) format.parse(d.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //Date datedebut = java.sql.Date.valueOf(d);
            Date datecirculation = java.sql.Date.valueOf(d);
            Date datefin = java.sql.Date.valueOf(d);

            Contrat c = new Contrat(idclient, nbplace, valeurcatalogue, datedebut, datefin, datecirculation, avantages, marque, modele);
            contratS cs = new contratS();
            cs.modifierContrat(c, id);

            AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/assurance/gui/ContratFront.fxml"));

            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            System.err.println(ex);
        }
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
