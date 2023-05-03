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
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import tn.esprit.controller.ItemController.MyListener;
import tn.esprit.entities.Sinistre;
import static tn.esprit.controller.ItemController.date_heure;
import static tn.esprit.controller.ItemController.degats;
import static tn.esprit.controller.ItemController.description;
//import static tn.esprit.controller.ItemController.id;
import static tn.esprit.controller.ItemController.lieu;
import static tn.esprit.controller.ItemController.url_image;
import tn.esprit.services.SinistreService;

/**
 * FXML Controller class
 *
 * @author HD
 */
public class FrontController implements Initializable {

    private List<Sinistre> sinDataList = FXCollections.observableArrayList();
    private SinistreService ss = new SinistreService();
    private MyListener myListener;

    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
    @FXML
    private TextField tf_recherche;
    @FXML
    private Label typelabe;
    @FXML
    private Label datelabel;
    @FXML
    private Label lieulabel;
    @FXML
    private Label desclabel;
    @FXML
    private Label degatslabel;
    @FXML
    private Label statutlabel;
    @FXML
    private ImageView imaged;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sinDataList.addAll(ss.afficher());
        System.out.println("Statut initial: " + ItemController.s.getStatut());
        if (sinDataList.size() > 0) {
            setChosenSin(sinDataList.get(0));
            myListener = new MyListener() {
                @Override
                public void onClick(Sinistre sin) {
                    System.out.println("mouse clicked");
                    setChosenSin(sin);
                }
            };
        }

        int column = 0;
        int row = 3;
        for (int i = 0; i < sinDataList.size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/gui/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController item = fxmlLoader.getController();
                item.setData(sinDataList.get(i).getId(), sinDataList.get(i).getDate_heure(), sinDataList.get(i).getLieu(), sinDataList.get(i).getStatut(), sinDataList.get(i).getDescription(), sinDataList.get(i).getDegats(), sinDataList.get(i).getFile(), sinDataList.get(i).getType(), myListener);

                if (column == 2) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            } catch (IOException e) {
                System.out.println("problem");
            }
        }
    }

    private void setChosenSin(Sinistre se) {
        typelabe.setText(ItemController.nomt);
        datelabel.setText(ItemController.date);
        lieulabel.setText(ItemController.s.getLieu());
        desclabel.setText(ItemController.s.getDescription());
        degatslabel.setText(ItemController.s.getDegats());
        statutlabel.setText(ItemController.s.getStatut());
        String imagePath = "C:\\Users\\HD\\Desktop\\Installations\\XAMPP\\htdocs\\imagePi\\" + ItemController.s.getFile();
        try {
            imaged.setImage(new Image(new FileInputStream(imagePath)));
        } catch (FileNotFoundException e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
    }

    @FXML
    private void ajouter(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/gui/Ajout.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void modifier(ActionEvent event) throws IOException {
        if (ItemController.s.getStatut().equals("En cours de traitement") || ItemController.s.getStatut().equals(" En cours de traitement") || ItemController.s.getStatut().equals("  En cours de traitement")) {
            Alert alerte = new Alert(Alert.AlertType.ERROR);
            alerte.setTitle("Erreur de modification");
            alerte.setHeaderText(null);
            alerte.setContentText("Impossible de modifier ce sinistre car son traitement a déjà commencé.");
            alerte.showAndWait();
            return;
        } else if (ItemController.s.getStatut().equals("Traité") || ItemController.s.getStatut().equals(" Traité") || ItemController.s.getStatut().equals("  Traité")) {
            Alert alerte = new Alert(Alert.AlertType.ERROR);
            alerte.setTitle("Erreur de modification");
            alerte.setHeaderText(null);
            alerte.setContentText("Impossible de modifier ce sinistre car il a déjà été traité.");
            alerte.showAndWait();
            return;
        } else if (ItemController.s.getStatut().equals("Refusé") || ItemController.s.getStatut().equals(" Refusé") || ItemController.s.getStatut().equals("  Refusé")) {
            Alert alerte = new Alert(Alert.AlertType.ERROR);
            alerte.setTitle("Erreur de modification");
            alerte.setHeaderText(null);
            alerte.setContentText("Impossible de modifier ce sinistre car il a été refusé.");
            alerte.showAndWait();
            return;
        } else if (ItemController.s.getStatut().equals("En attente de traitement") || ItemController.s.getStatut().equals(" En attente de traitement") || ItemController.s.getStatut().equals("  En attente de traitement")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/gui/FrontModifier.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    private void supprimer(ActionEvent event) {
    }

}
