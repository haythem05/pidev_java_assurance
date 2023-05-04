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
import tn.assurance.models.Categorie;
import tn.assurance.services.categorieS;
import tn.assurance.controller.ItemcategorieController.MyListener;

/**
 * FXML Controller class
 *
 * @author haythem
 */
public class CategorieFrontController implements Initializable {

    private List<Categorie> sinDataList = FXCollections.observableArrayList();
    private categorieS ss = new categorieS();
    private MyListener myListener;

    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
    @FXML
    private Label nomL;
    @FXML
    private Label desclabel;
    @FXML
    private ImageView imaged;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sinDataList.addAll(ss.afficherCategorie());

        if (sinDataList.size() > 0) {
            setChosenSin(sinDataList.get(0));
            myListener = new MyListener() {

                @Override
                public void onClick(Categorie cat) {
                    System.out.println("mouse clicked");
                    setChosenSin(cat);
                }
            };
        }

        int column = 0;
        int row = 3;
        for (int i = 0; i < sinDataList.size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/assurance/gui/itemcategorie.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemcategorieController item = fxmlLoader.getController();
                //statutlabel.setText(ItemController.statut);
                item.setData(sinDataList.get(i).getId(), sinDataList.get(i).getNom(), sinDataList.get(i).getDescription(), sinDataList.get(i).getCategorieimage(), myListener);

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
            /*updateList();
            tf_recherche.textProperty().addListener((observable, oldValue, newValue) -> {
                updateList(newValue);
            });*/
        }
    }

    private void setChosenSin(Categorie se) {
        nomL.setText(ItemcategorieController.nom);
        desclabel.setText(ItemcategorieController.description);
        String imagePath = "C:\\xampp\\htdocs\\PiDev44\\public\\uploads\\images\\categorie\\" + ItemcategorieController.url_image;
        try {
            imaged.setImage(new Image(new FileInputStream(imagePath)));
        } catch (FileNotFoundException e) {
            System.err.println("Error loading image: " + e.getMessage());
            System.out.println("hellooooo");
        }
    }

    @FXML
    private void ajouterC(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/assurance/gui/AjouterContratBack.fxml"));
        Parent root = loader.load();

        // Get the current stage and set the new scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void ajouterH(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/assurance/gui/AjouterHabitationFront.fxml"));
        Parent root = loader.load();

        // Get the current stage and set the new scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
