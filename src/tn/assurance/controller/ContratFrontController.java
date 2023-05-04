/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.assurance.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import tn.assurance.controller.ItemcontratController.MyListener;
import tn.assurance.models.Categorie;
import tn.assurance.models.Contrat;
import tn.assurance.services.contratS;

/**
 * FXML Controller class
 *
 * @author haythem
 */
public class ContratFrontController implements Initializable {

    private MyListener myListener;
    private contratS cs = new contratS();
    private List<Contrat> sinDataList = FXCollections.observableArrayList();
    @FXML
    private TextField tf_recherche;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
    @FXML
    private Label catlabel;
    @FXML
    private Label dateDlabel;
    @FXML
    private Label dateFlabel;
    @FXML
    private Label avlabel;
    @FXML
    private Label marquelabel;
    @FXML
    private Label modelelabel;
    @FXML
    private Label nbplabel;
    @FXML
    private Label dateClabel;
    @FXML
    private Label valcatlabel;
    @FXML
    private Label prixlabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sinDataList.addAll(cs.afficherContrat());

        if (sinDataList.size() > 0) {
            setChosenSin(sinDataList.get(0));
            myListener = new MyListener() {

                @Override
                public void onClick(Contrat c) {
                    System.out.println("mouse clicked");
                    setChosenSin(c);
                }
            };

            int column = 0;
            int row = 3;
            for (int i = 0; i < sinDataList.size(); i++) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/assurance/gui/itemcontrat.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    ItemcontratController item = fxmlLoader.getController();
                    //statutlabel.setText(ItemController.statut);
                    item.setData(sinDataList.get(i).getId(), sinDataList.get(i).getIdclient(), sinDataList.get(i).getNbplace(), sinDataList.get(i).getValeurcatalogue(), sinDataList.get(i).getDatedebut(), sinDataList.get(i).getDatefin(), sinDataList.get(i).getDatecirculation(), sinDataList.get(i).getAvantages(), sinDataList.get(i).getMarque(), sinDataList.get(i).getModele(), sinDataList.get(i).getType_Id(), myListener);

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
    }

    private void setChosenSin(Contrat se) {
        catlabel.setText(ItemcontratController.nomCat);
        dateDlabel.setText(ItemcontratController.dated);
        dateFlabel.setText(ItemcontratController.datef);
        dateClabel.setText(ItemcontratController.datec);
        //nbplabel.setText(String.valueOf(ItemcontratController.s.getNbplace()));
        nbplabel.setText(ItemcontratController.nbp);
        avlabel.setText(ItemcontratController.s.getAvantages());
        marquelabel.setText(ItemcontratController.s.getMarque());
        modelelabel.setText(ItemcontratController.s.getModele());
        valcatlabel.setText(ItemcontratController.vc);
        prixlabel.setText(ItemcontratController.p);
    }

    @FXML
    private void ajouter(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/assurance/gui/AjouterContratBack.fxml"));
        Parent root = loader.load();

        // Get the current stage and set the new scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void modifier(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/assurance/gui/modifierContrat.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void supprimer(ActionEvent event) throws IOException {
        cs.supprimerContrat(ItemcontratController.s.getId());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Suppression réussie");
        alert.setHeaderText(null);
        alert.setContentText("Le contrat a été supprimé avec succès de la base de données.");
        alert.showAndWait();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/assurance/gui/ContratFront.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
