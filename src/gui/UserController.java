/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import entities.Utilisateur;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;

import static java.time.Clock.system;

import java.io.IOException;

import services.UtilisateurService;

/**
 * FXML Controller class
 *
 * @author Ranim
 */
public class UserController implements Initializable {

    @FXML
    private TextField nomInput;
    @FXML
    private TextField prenomInput;
    @FXML
    private TextField emailInput;
    @FXML
    private TextField cinInput;
    @FXML
    private TextField numtelInput;
    @FXML
    private TextField mdpInput;
    @FXML
    private DatePicker dateInput;
    @FXML
    private Button modifierBtn;
    @FXML
    private Button supprimerBtn;
    @FXML
    private TableView<Utilisateur> tvtype;
    @FXML
    private TableColumn<Utilisateur, Integer> cinTable;
    @FXML
    private TableColumn<Utilisateur, String> nomTable;
    @FXML
    private TableColumn<Utilisateur, String> prenomTable;
    @FXML
    private TableColumn<Utilisateur, String> emailTable;
    @FXML
    private TableColumn<Utilisateur, Date> datedenaissanceTable;
    @FXML
    private TableColumn<Utilisateur, String> numtelTable;

    UtilisateurService utilisateurService = new UtilisateurService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayData();
    }

    @FXML
    private void displayData() {
        ObservableList<Utilisateur> dataList = FXCollections.observableArrayList(utilisateurService.afficher());
        cinTable.setCellValueFactory(new PropertyValueFactory<>("cin"));
        nomTable.setCellValueFactory(new PropertyValueFactory<>("nom"));
        datedenaissanceTable.setCellValueFactory(new PropertyValueFactory<>("datedenaissance"));
        emailTable.setCellValueFactory(new PropertyValueFactory<>("Email"));
        prenomTable.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        numtelTable.setCellValueFactory(new PropertyValueFactory<>("numtel"));
        tvtype.setItems(dataList);
    }

    @FXML
    private void modifier(ActionEvent event) {
        Utilisateur utilisateur = tvtype.getSelectionModel().getSelectedItem();
        if (utilisateur == null) {
            // show error message
            return;
        }
        String nom = nomInput.getText();
        String prenom = prenomInput.getText();
        String cinText = cinInput.getText();
        int cin = Integer.parseInt(cinText);
        String numtelText = numtelInput.getText();
        int numTel = Integer.parseInt(numtelText);
        String email = emailInput.getText();
        String password = mdpInput.getText();
        LocalDate localDate = dateInput.getValue();
        Date datedenaissance = Date.valueOf(localDate);
        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setCin(cin);
        utilisateur.setDatedenaissance(datedenaissance);
        utilisateur.setPassword(password);
        utilisateur.setNumtel(numTel);
        utilisateur.setEmail(email);
        UtilisateurService utilisateurService = new UtilisateurService();
        utilisateurService.modifier(utilisateur);
        // update the table view with the new data
        displayData();
    }

    @FXML
    void handleTableClick(MouseEvent event) {
        Utilisateur user = tvtype.getSelectionModel().getSelectedItem();
        if (user != null) {
            nomInput.setText(user.getNom());
            prenomInput.setText(user.getPrenom());
            emailInput.setText(user.getEmail());
            cinInput.setText(Integer.toString(user.getCin()));
            numtelInput.setText(Integer.toString(user.getNumtel()));
            mdpInput.setText(user.getPassword());
            LocalDate date = user.getDatedenaissance().toLocalDate();
            dateInput.setValue(date);
        }
    }

    @FXML
    private void supprimer(ActionEvent event) throws Exception {
        if (tvtype.getSelectionModel().getSelectedItem() != null) {
            int id = tvtype.getSelectionModel().getSelectedItem().getId();
            utilisateurService.supprimer(id);
            displayData();
        }
    }
}
