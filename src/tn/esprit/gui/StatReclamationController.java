/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import pidevj3a40.PidevJ3A40;
import java.io.IOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.stage.Stage;
import tn.esprit.tools.MaConnexion;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class StatReclamationController implements Initializable {
 @FXML
    private PieChart piechart;
    ObservableList<Data> data=FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stat();
    }
  public void stat(){
    try {
        String queryEnCours = "SELECT COUNT(*) as nbr FROM reclamation WHERE statut = 'En cours'";
        String queryApprouvee = "SELECT COUNT(*) as nbr FROM reclamation WHERE statut = 'Approuvée'";
        String queryRefusee = "SELECT COUNT(*) as nbr FROM reclamation WHERE statut = 'Refusée'";
        Statement st=MaConnexion.getInstance().getCnx().createStatement();
        ResultSet rsEnCours = st.executeQuery(queryEnCours);
        ResultSet rsApprouvee = st.executeQuery(queryApprouvee);
        ResultSet rsRefusee = st.executeQuery(queryRefusee);
        int nbrEnCours = rsEnCours.getInt("nbr");
        int nbrApprouvee = rsApprouvee.getInt("nbr");
        int nbrRefusee = rsRefusee.getInt("nbr");
        data.add(new Data("En cours", nbrEnCours));
        data.add(new Data("Approuvée", nbrApprouvee));
        data.add(new Data("Refusée", nbrRefusee));
        piechart.setData(data);
    } catch (SQLException ex) {
        Logger.getLogger(StatReclamationController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    @FXML
    private void retour(ActionEvent event) {
        Stage stageclose=(Stage)((Node)event.getSource()).getScene().getWindow();
        stageclose.close();
        try {
            Parent root=FXMLLoader.load(getClass().getResource("/tn.esprit.gui/Reclamations.fxml"));

            Scene scene = new Scene(root);
            Stage primaryStage=new Stage();
            primaryStage.setTitle("Gestion recalamation");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(PidevJ3A40.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}