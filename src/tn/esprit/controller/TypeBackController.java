/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.controller;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.entities.Sinistre;
import tn.esprit.entities.Type;
import tn.esprit.services.TypeService;

/**
 * FXML Controller class
 *
 * @author HD
 */
public class TypeBackController implements Initializable {

    @FXML
    private TextField tf_recherche;
    @FXML
    private TableView<Type> tableT;
    @FXML
    private TableColumn<Type, Integer> idcol;
    @FXML
    private TableColumn<Type, String> nomcol;
    private TypeService st;
    static public Type typ;
    static public int id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        st = new TypeService();
        List<Type> liste = st.afficher();

        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomcol.setCellValueFactory(new PropertyValueFactory<>("nom"));

        tableT.setItems(FXCollections.observableArrayList(liste));

        tf_recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            updateTableView(newValue);
        });
    }

    public void updateTableView() {
        List<Type> liste = st.getAllTypes();
        tableT.getItems().setAll(liste);
    }

    public void updateTableView(String search) {
        List<Type> liste = st.searchTypes(search);
        tableT.getItems().setAll(liste);
    }

    @FXML
    private void ajouter(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/gui/AjouterT.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void modifier(ActionEvent event) {
        /*TableView<Sinistre> tab = (TableView<Sinistre>) table;
        sin = tab.getSelectionModel().getSelectedItem();
        id = sin.getId();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/gui/Traitement.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.err.println(ex);
        }*/
        /*ListView<Type> liste = (ListView<Type>) listT; // assuming listView is a ListView<CoVoiturage>
        Type t = liste.getSelectionModel().getSelectedItem(); // use getSelectedItem() to get the selected item, not getSelectedItems()*

        id = t.getId();
        nom = t.getNom();
        
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/ModifierT.fxml"));
            rootPane.getChildren().setAll(pane);

        } catch (IOException ex) {
            System.err.println(ex);

        }*/
        TableView<Type> tab = (TableView<Type>) tableT;
        typ = tab.getSelectionModel().getSelectedItem();
        id = typ.getId();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/gui/ModifierT.fxml"));
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
    private void supprimer(ActionEvent event) {
        TableView<Type> list_supp = (TableView<Type>) tableT; // assuming listView is a ListView<CoVoiturage>
        TypeService ts = new TypeService();
        int selectedID = list_supp.getSelectionModel().getSelectedIndex();
        Type t = list_supp.getSelectionModel().getSelectedItem(); // use getSelectedItem() to get the selected item, not getSelectedItems()

        ts.supprimer(t.getId()); // assuming CoVoiturage has a method getId() to retrieve the unique ID of the object
        list_supp.getItems().remove(selectedID);
    }

}
