/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.controller;

import tn.esprit.controller.AjouterReclamationController;
import pidevj3a40.PidevJ3A40;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.tools.MaConnexion;


/**
 * FXML Controller class
 *
 * @author MSI
 */
public class StatistiquesController implements Initializable {
    @FXML
    private PieChart piechart;
    private ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

    @FXML
    private Button retour;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Générer les statistiques de réclamation
            Map<String, Integer> stats = generateClaimStatistics();

            // Afficher les statistiques dans le graphique circulaire
            displayStatistics(stats);
        } catch (SQLException ex) {
            Logger.getLogger(StatistiquesController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur SQL");
            alert.setHeaderText("Une erreur s'est produite lors de la génération des statistiques");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
        retour.setOnAction(event -> redirectToList());
    }

    public Map<String, Integer> generateClaimStatistics() throws SQLException {
        Map<String, Integer> stats = new HashMap<>();

        try {
            // Compter les réclamations en cours
            String queryEnCours = "SELECT COUNT(*) as nbr FROM reclamation WHERE statut = 'En cours'";
            Statement stEnCours = MaConnexion.getInstance().getCnx().createStatement();
            ResultSet rsEnCours = stEnCours.executeQuery(queryEnCours);
            rsEnCours.next();
            int enCours = rsEnCours.getInt("nbr");
            stats.put("En cours", enCours);

            // Compter les réclamations approuvées
            String queryApprouvees = "SELECT COUNT(*) as nbr FROM reclamation WHERE statut = 'Approuvée'";
            Statement stApprouvees = MaConnexion.getInstance().getCnx().createStatement();
            ResultSet rsApprouvees = stApprouvees.executeQuery(queryApprouvees);
            rsApprouvees.next();
            int approuvees = rsApprouvees.getInt("nbr");
            stats.put("Approuvée", approuvees);

            // Compter les réclamations refusées
            String queryRefusees = "SELECT COUNT(*) as nbr FROM reclamation WHERE statut = 'Refusée'";
            Statement stRefusees = MaConnexion.getInstance().getCnx().createStatement();
            ResultSet rsRefusees = stRefusees.executeQuery(queryRefusees);
            rsRefusees.next();
            int refusees = rsRefusees.getInt("nbr");
            stats.put("Refusée", refusees);

        } catch (SQLException ex) {
            Logger.getLogger(StatistiquesController.class.getName()).log(Level.SEVERE, null, ex);
            throw ex; // renvoie l'exception SQLException pour que le bloc catch dans initialize() puisse la capturer
        }

        return stats;
    }

    public void displayStatistics(Map<String, Integer> stats) {
        // Effacer les anciennes données du graphique
        data.clear();
// Ajouter les nouvelles données au graphique
        stats.forEach((statut, count) -> {
            PieChart.Data slice = new PieChart.Data(statut, count);
data.add(slice);
});
      // Afficher les données dans le graphique
    piechart.setData(data);
}



  public void redirectToList() {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/Reclamations.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) retour.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        Logger.getLogger(AjouterReclamationController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    @FXML
    private void retour(MouseEvent event) {
        this.redirectToList();
    }
  
    
}