/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.assurance.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.assurance.models.Contrat;
import tn.assurance.models.Habitation;
import tn.assurance.services.habitationS;

/**
 * FXML Controller class
 *
 * @author haythem
 */
public class HabitationBackController implements Initializable {

    @FXML
    private TableView<Habitation> table;
    @FXML
    private TableColumn<Habitation, Integer> piercecol;
    @FXML
    private TableColumn<Habitation, Float> capitalMcol;
    @FXML
    private TableColumn<Habitation, Float> capitalIcol;
    @FXML
    private TableColumn<Habitation, Float> deviscol;
    private habitationS hs;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hs = new habitationS();
        List<Habitation> liste = hs.afficherHabitation();

        piercecol.setCellValueFactory(new PropertyValueFactory<>("nbpieceimmobilier"));
        capitalMcol.setCellValueFactory(new PropertyValueFactory<>("capitalmobilier"));
        capitalIcol.setCellValueFactory(new PropertyValueFactory<>("capitalimmobilier"));
        deviscol.setCellValueFactory(new PropertyValueFactory<>("devis"));

        table.setItems(FXCollections.observableArrayList(liste));
    }

    public List<Habitation> getHabitationsSortedDescDevis() {
        habitationS hs = new habitationS();
        List<Habitation> habitationList = hs.afficherHabitation();
        habitationList.sort(Comparator.comparing(Habitation::getDevis).reversed());
        return habitationList;
    }

    @FXML
    private void sortAsc(ActionEvent event) {
        table.getItems().clear();
        table.getItems().addAll(getHabitationsSortedAscDevis());
    }

    @FXML
    private void sortDesc(ActionEvent event) {
        table.getItems().clear();
        table.getItems().addAll(getHabitationsSortedDescDevis());
    }

    private List<Habitation> getHabitationsSortedAscDevis() {
        habitationS hs = new habitationS();
        List<Habitation> habitationList = hs.afficherHabitation();
        habitationList.sort(Comparator.comparing(Habitation::getDevis).reversed());
        return habitationList;
    }

    @FXML
    private void habitation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/assurance/gui/HabitationBack.fxml"));
        Parent root = loader.load();

        // Get the current stage and set the new scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void automobile(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/assurance/gui/ContratBack.fxml"));
        Parent root = loader.load();

        // Get the current stage and set the new scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
