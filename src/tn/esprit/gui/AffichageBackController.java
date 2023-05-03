/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static tn.esprit.controller.AfficherBackController.id;
import static tn.esprit.controller.AfficherBackController.sin;
import tn.esprit.controller.StatistiquesSController;
import tn.esprit.entities.Sinistre;
import tn.esprit.services.SinistreService;

/**
 * FXML Controller class
 *
 * @author HD
 */
public class AffichageBackController implements Initializable {

    @FXML
    private VBox vboxutill;
    @FXML
    private TextField tf_recherche;
    @FXML
    private Label labelNbSinistresEnAttente;
    @FXML
    private ListView<Sinistre> list;
    @FXML
    private Button traiter;
    @FXML
    private Button stat;
    @FXML
    private Button tri_a;
    @FXML
    private Button tri_d;
    static public String statut;
    static public int id;
    static public Sinistre sin;
    private SinistreService ss;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ListView list2 = list;
        Sinistre r = new Sinistre();
        ss = new SinistreService();
        List<Sinistre> liste = ss.afficher();
        for (int i = 0; i < liste.size(); i++) {
            Sinistre s = liste.get(i);
            list2.getItems().add(s);
        }
        updateListView();
        tf_recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            updateListView(newValue);
        });
    }

    public void updateListView() {
        List<Sinistre> liste = ss.getAllSinistres();
        list.getItems().setAll(liste);
    }
    
    public void updateListView(String search) {
        List<Sinistre> liste = ss.searchSinistres(search);
        list.getItems().setAll(liste);
    }

    @FXML
    private void shownb(MouseEvent event) throws SQLException {
        int nbSinistresEnAttente = ss.countSinistresEnAttente();
        labelNbSinistresEnAttente.setText(Integer.toString(nbSinistresEnAttente));
    }

    @FXML
    private void traiter(ActionEvent event) {
        ListView<Sinistre> liste = (ListView<Sinistre>) list;
        sin = liste.getSelectionModel().getSelectedItem();
        id = sin.getId();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/gui/Traitement.fxml"));
        Parent root = loader.load();

        // Get the current stage and set the new scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        } catch (IOException ex) {
            System.err.println(ex);

        }
    }

    @FXML
    private void stat(ActionEvent event) throws IOException, SQLException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/gui/Statistiques.fxml"));
        Parent root = loader.load();
        StatistiquesSController controller = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
        if (controller.tabType.isSelected()) {
            Map<String, Integer> statst = controller.generateTypeStatistics();
        } else if (controller.tabStatut.isSelected()) {
            Map<String, Integer> statss = controller.generateClaimStatistics();
        }
    }

    @FXML
    private void sortButtonASC(ActionEvent event) {
        List<Sinistre> sinistres = list.getItems();
        Collections.sort(sinistres, Comparator.comparing(Sinistre::getDate_heure));
        list.setItems(FXCollections.observableArrayList(sinistres));
    }

    @FXML
    private void sortButtonDESC(ActionEvent event) {
        List<Sinistre> sinistres = list.getItems();
        Collections.sort(sinistres, Comparator.comparing(Sinistre::getDate_heure).reversed());
        list.setItems(FXCollections.observableArrayList(sinistres));
    }

}
