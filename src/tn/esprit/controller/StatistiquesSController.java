/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.tools.MaConnexion;

/**
 * FXML Controller class
 *
 * @author HD
 */
public class StatistiquesSController implements Initializable {

    @FXML
    private AnchorPane paneType;
    @FXML
    private PieChart chartType;
    @FXML
    private AnchorPane paneStatut;
    @FXML
    private PieChart chartStatut;
    @FXML
    public Tab tabType;
    @FXML
    public Tab tabStatut;
    private ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> dataT = FXCollections.observableArrayList();
    @FXML
    private Button retour;
    @FXML
    private TabPane tabPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Générer les statistiques des sinistres
            Map<String, Integer> stats = generateClaimStatistics();
            Map<String, Integer> statsT = generateTypeStatistics();

            // Afficher les statistiques dans le graphique circulaire
            displayStatistics(stats);
            displayStatisticsType(statsT);
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur SQL");
            alert.setHeaderText("Une erreur s'est produite lors de la génération des statistiques");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
        chartType.setData(dataT);

        chartStatut.setData(data);

        // Ajouter un listener pour détecter le changement d'onglet
        tabType.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // L'utilisateur a sélectionné l'onglet Type
                chartType.setData(dataT);
            }
        });

        tabStatut.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // L'utilisateur a sélectionné l'onglet Statut
                chartStatut.setData(data);
            }
        });
    }

    public Map<String, Integer> generateClaimStatistics() throws SQLException {
        Map<String, Integer> stats = new HashMap<>();

        try {
            String queryEnCours = "SELECT COUNT(*) as nbr FROM sinistre WHERE statut = 'En cours de traitement'";
            Statement stEnCours = MaConnexion.getInstance().getCnx().createStatement();
            ResultSet rsEnCours = stEnCours.executeQuery(queryEnCours);
            rsEnCours.next();
            int enCours = rsEnCours.getInt("nbr");
            stats.put("En cours de traitement", enCours);

            String queryEnAttente = "SELECT COUNT(*) as nbr FROM sinistre WHERE statut = 'En attente de traitement'";
            Statement stEnAttente = MaConnexion.getInstance().getCnx().createStatement();
            ResultSet rsEnAttente = stEnAttente.executeQuery(queryEnAttente);
            rsEnAttente.next();
            int attente = rsEnAttente.getInt("nbr");
            stats.put("En attente de traitement", attente);

            String queryTraite = "SELECT COUNT(*) as nbr FROM sinistre WHERE statut = 'Traité'";
            Statement stTraite = MaConnexion.getInstance().getCnx().createStatement();
            ResultSet rsTraite = stTraite.executeQuery(queryTraite);
            rsTraite.next();
            int traite = rsTraite.getInt("nbr");
            stats.put("Traités", traite);

            String queryRefuse = "SELECT COUNT(*) as nbr FROM sinistre WHERE statut = 'Refusé'";
            Statement stRefuse = MaConnexion.getInstance().getCnx().createStatement();
            ResultSet rsRefuse = stRefuse.executeQuery(queryRefuse);
            rsRefuse.next();
            int refuse = rsRefuse.getInt("nbr");
            stats.put("Refusés", refuse);

        } catch (SQLException ex) {
            Logger.getLogger(StatController.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }

        return stats;
    }

    public void displayStatistics(Map<String, Integer> stats) {
        data.clear();
        stats.forEach((statut, count) -> {
            PieChart.Data slice = new PieChart.Data(statut, count);
            data.add(slice);
        });
        chartStatut.setData(data);
    }

    public Map<String, Integer> generateTypeStatistics() throws SQLException {
        Map<String, Integer> statsT = new HashMap<>();

        try {
            String query = "SELECT t.nom, COUNT(*) as nbr "
                    + "FROM sinistre s "
                    + "JOIN type t ON s.type_id = t.id "
                    + "GROUP BY t.nom";
            Statement st = MaConnexion.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String type = rs.getString("nom");
                int count = rs.getInt("nbr");
                statsT.put(type, count);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatController.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }

        return statsT;
    }

    public void displayStatisticsType(Map<String, Integer> statsT) {
        dataT.clear();
        statsT.forEach((type, count) -> {
            PieChart.Data slice = new PieChart.Data(type, count);
            dataT.add(slice);
        });
        chartType.setData(dataT);
    }

    @FXML
    private void stat_type(Event event) {

    }

    @FXML
    private void stat_statut(Event event) {

    }

    @FXML
    private void retourT(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/AffichageBack.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Masquer tous les onglets de la TabPane
        tabPane.getTabs().clear();

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void retourS(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/AffichageBack.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Masquer tous les onglets de la TabPane
        tabPane.getTabs().clear();

        stage.setScene(scene);
        stage.show();
    }
}
