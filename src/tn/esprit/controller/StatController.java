/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.tools.MaConnexion;

/**
 * FXML Controller class
 *
 * @author HD
 */
public class StatController implements Initializable {

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private PieChart piechart;
    @FXML
    private Button retour;
    private ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Générer les statistiques des sinistres
            Map<String, Integer> stats = generateClaimStatistics();

            // Afficher les statistiques dans le graphique circulaire
            displayStatistics(stats);
        } catch (SQLException ex) {
            Logger.getLogger(StatController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur SQL");
            alert.setHeaderText("Une erreur s'est produite lors de la génération des statistiques");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
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
        piechart.setData(data);
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/AfficherBack.fxml"));
        AnchorPane.getChildren().setAll(pane);
    }

}
