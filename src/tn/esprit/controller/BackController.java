/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.entities.Sinistre;
import tn.esprit.entities.Type;
import static tn.esprit.gui.AffichageBackController.id;
import static tn.esprit.gui.AffichageBackController.sin;
import tn.esprit.services.SinistreService;

/**
 * FXML Controller class
 *
 * @author HD
 */
public class BackController implements Initializable {

    @FXML
    private TextField tf_recherche;
    @FXML
    private TableView<Sinistre> table;
    @FXML
    private TableColumn<Sinistre, Type> typecol;
    @FXML
    private TableColumn<Sinistre, Timestamp> datecol;
    @FXML
    private TableColumn<Sinistre, String> lieucol;
    @FXML
    private TableColumn<Sinistre, String> desccol;
    @FXML
    private TableColumn<Sinistre, String> degatscol;
    @FXML
    private TableColumn<Sinistre, String> imagecol;
    @FXML
    private TableColumn<Sinistre, String> statutcol;
    private SinistreService ss;
    private Sinistre s;
    //static public String nomt;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*ListView list2 = list;
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
        });*/
        ss = new SinistreService();
        List<Sinistre> liste = ss.afficher();

        lieucol.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        desccol.setCellValueFactory(new PropertyValueFactory<>("description"));
        degatscol.setCellValueFactory(new PropertyValueFactory<>("degats"));
        statutcol.setCellValueFactory(new PropertyValueFactory<>("statut"));
        datecol.setCellValueFactory(new PropertyValueFactory<>("date_heure"));
        imagecol.setCellValueFactory(new PropertyValueFactory<>("file"));
        typecol.setCellValueFactory(new PropertyValueFactory<>("type"));
        typecol.setCellFactory(column -> {
            return new TableCell<Sinistre, Type>() {
                @Override
                protected void updateItem(Type type, boolean empty) {
                    super.updateItem(type, empty);
                    if (type == null || empty) {
                        setText(null);
                    } else {
                        setText(type.getNom());
                    }
                }
            };
        });

        table.setItems(FXCollections.observableArrayList(liste));

        tf_recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            updateTableView(newValue);
        });
    }

    public void updateTableView() {
        List<Sinistre> liste = ss.getAllSinistres();
        table.getItems().setAll(liste);
    }

    public void updateTableView(String search) {
        List<Sinistre> liste = ss.searchSinistres(search);
        table.getItems().setAll(liste);
    }

    @FXML
    private void traiter(ActionEvent event) {
        TableView<Sinistre> tab = (TableView<Sinistre>) table;
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
        ObservableList<Sinistre> sinistres = table.getItems();
        sinistres.sort(Comparator.comparing(Sinistre::getDate_heure));
    }

    @FXML
    private void sortButtonDESC(ActionEvent event) {
        ObservableList<Sinistre> sinistres = table.getItems();
        sinistres.sort(Comparator.comparing(Sinistre::getDate_heure).reversed());
    }

}
