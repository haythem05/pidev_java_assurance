/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.controller;

import tn.esprit.controller.ReclamationsController;
import tn.esprit.controller.ModifierReponseController;
import tn.esprit.controller.AjouterReponseController;
import tn.esprit.entities.Reclamation;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import tn.esprit.services.ReclamationService;
import tn.esprit.tools.MaConnexion;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
  
import java.awt.Dimension;
import java.awt.GridLayout;
import javafx.scene.image.Image;
import java.util.Collections;
import java.util.Comparator;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TextArea;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Predicate;
import javafx.scene.image.ImageView;
import tn.esprit.services.ReponseService;
import tn.esprit.entities.Reponse;
import javafx.scene.control.Alert.AlertType;



/**
 * FXML Controller class
 *
 * @author MSI
 */
public class ReponseController implements Initializable {

   @FXML
    private Button Créer;
    @FXML
    private Button ModifierR;
    @FXML
    private Button Supprimer;
    @FXML
    private  ListView<Reponse> lvReponse;
    @FXML
    private Pane paneReponses;
     @FXML
    private Button reclamation;
    @FXML
    private TextField tfrecherche;
    
    public Connection cnx;
    public Statement stm;
    String sql = "";
    public int idSelected= -1;
    @FXML
    private Button refresh;
    @FXML
    private Pane paneReclamations;
    @FXML
    private ImageView image;
  
   
    
  @Override
public void initialize(URL url, ResourceBundle rb) {
    try {
        // Initialize your database connection here
        cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/assurancepidev", "root", "");
    } catch (SQLException ex) {
        System.out.println("Failed to connect to database: " + ex.getMessage());
    }
    Créer.setOnAction((ActionEvent event) -> {
        GoToCréer();
    });

    ModifierR.setOnAction((ActionEvent event) -> {
        GoToModifierR();
    });
    reclamation.setOnAction((ActionEvent event) -> {
            reclamation();
        });
     

 //recherche
             tfrecherche.textProperty().addListener((observable, oldValue, newValue) -> {
        rechercherReponses(newValue);
    });
    // Appel de la méthode ShowListe() pour afficher la liste des réclamations
    ShowListe();
}

private void GoToCréer() {
    Parent root;
    try {
        root = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/AjouterReponse.fxml"));
        Scene c = new Scene(root);
        Stage stage = (Stage) Créer.getScene().getWindow();
        stage.setScene(c);
    } catch (IOException ex) {
        Logger.getLogger(AjouterReponseController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

private void GoToModifierR() {
    Reponse selectedReponse = lvReponse.getSelectionModel().getSelectedItem();
    if (selectedReponse == null) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aucune réponse sélectionnée");
        alert.setHeaderText("Sélectionnez une réponse à modifier");
        alert.showAndWait();
        return;
    }
    this.idSelected = selectedReponse.getId();

    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/gui/ModifierReponse.fxml"));
        Parent root = loader.load();

        ModifierReponseController controller = loader.getController();
        controller.initializeFxml(this.idSelected);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    } catch (IOException ex) {
        Logger.getLogger(AjouterReponseController.class.getName()).log(Level.SEVERE, null, ex);
    }
}





public ObservableList<Reponse> getReponseList() {
    cnx = MaConnexion.getInstance().getCnx();
    ObservableList<Reponse> ReponseList = FXCollections.observableArrayList();
    try {
        String query2 = "SELECT * from reponse LEFT JOIN reclamation ON reponse.reclamation_id = reclamation.id";
        PreparedStatement smt = cnx.prepareStatement(query2);
        ResultSet rs = smt.executeQuery();
        while (rs.next()) {
            // Create the Reponse object and fetch the Reclamation object
            Reclamation rec = new Reclamation(rs.getInt("reclamation.id"), rs.getString("reference"), rs.getString("nom_d"), rs.getString("prenom_d"), rs.getInt("cin"), rs.getString("email"), rs.getString("commentaire"), rs.getDate("created_at"), rs.getString("statut"), rs.getString("file"), rs.getString("tel"));
            Reponse rep = new Reponse(rs.getInt("id"), rs.getString("id_user"), rs.getString("note"), rs.getDate("created_at"), rec);
            
            ReponseList.add(rep);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }

    return ReponseList;
}


public void ShowListe() {
    ObservableList<Reponse> list = getReponseList();
    lvReponse.setItems(list);

    // Définir la cellFactory pour le ListView
    lvReponse.setCellFactory(new Callback<ListView<Reponse>, ListCell<Reponse>>() {
        @Override
        public ListCell<Reponse> call(ListView<Reponse> param) {
            return new ListCell<Reponse>() {
                @Override
                protected void updateItem(Reponse item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        // Définir le texte à afficher pour chaque attribut de l'objet Reponse et Reclamation
                        setText(
                                 "Id_User: " + item.getId_user() + "\n"
                                + "Note: " + item.getNote() + "\n"
                                + "Créé le: " + item.getCreated_at() + "\n"
                                + "Réclamation: " + item.getReclamation().getReference());

                    } else {
                        setText(null);
                    }
                }
            };
        }
    });
}


    @FXML
    private void functionTest(MouseEvent event) {
        this.idSelected = lvReponse.getSelectionModel().getSelectedItem().getId();
        
        ReponseService reponseService = new ReponseService();
            Reponse reponse =reponseService.recup(this.idSelected);}

    @FXML
    private void refresh(ActionEvent event) {
        ShowListe();
    }

    @FXML
private void supprimer(ActionEvent event) {
Reponse selectedReponse = lvReponse.getSelectionModel().getSelectedItem();
if (selectedReponse == null) {
Alert alert = new Alert(Alert.AlertType.ERROR);
alert.setTitle("Erreur");
alert.setHeaderText(null);
alert.setContentText("Veuillez sélectionner une réponse à supprimer.");
alert.showAndWait();
return;
}
int selectedId = selectedReponse.getId();
ReponseService reponseService = new ReponseService();
reponseService.supprimer(selectedId);

Alert alert = new Alert(Alert.AlertType.INFORMATION);
alert.setTitle("Information");
alert.setHeaderText(null);
alert.setContentText("La réponse a été supprimée avec succès.");
alert.showAndWait();

ShowListe();

}
    @FXML
  private void reclamation() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/Reclamations.fxml"));
            Scene c = new Scene(root);
            Stage stage = (Stage) reclamation.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            Logger.getLogger(ReclamationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  public void rechercherReponses(String recherche) {
    ObservableList<Reponse> list = getReponseList();

 if (recherche != null && !recherche.isEmpty()) {
    Predicate<Reponse> predicate = reponse -> {
        Reclamation reclamation = reponse.getReclamation();
        return reclamation != null && reclamation.getReference().contains(recherche);
    };

    list = list.filtered(predicate);
}

lvReponse.setItems(list);




 
}
}



