/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

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
import javafx.scene.image.ImageView;


/**
 * FXML Controller class
 *
 * @author MSI
 */
public class ReclamationsController implements Initializable {
     
    @FXML
    private Button Créer;
    @FXML
    private Button ModifierR;
    @FXML
    private Button Supprimer;
      @FXML
    private Button reponse;
    @FXML
    private  ListView<Reclamation> lvReclamation;
    @FXML
    private Pane paneReclamations;
     @FXML
    private TextField recherche;
    
    public Connection cnx;
    public Statement stm;
    String sql = "";
    public int idSelected= -1;
    @FXML
    private Button refresh;
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
        
        reponse.setOnAction((ActionEvent event) -> {
            reponse();
        });

        // Appel de la méthode ShowListe() pour afficher la liste des réclamations
        ShowListe();
    }

    private void GoToCréer() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("AjouterReclamation.fxml"));
            Scene c = new Scene(root);
            Stage stage = (Stage) Créer.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            Logger.getLogger(AjouterReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void GoToModifierR() {
                this.idSelected = lvReclamation.getSelectionModel().getSelectedItem().getId();

        Pane view;

        try {

            
            
            
            
            
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierReclamation.fxml"));
            Parent root = loader.load();


            ModifierReclamationController HomeScene = loader.getController();
            HomeScene.initializeFxml(this.idSelected);
             Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(AjouterReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

public ObservableList<Reclamation> getReclamationList() {
    cnx = MaConnexion.getInstance().getCnx();
    ObservableList<Reclamation> ReclamationList = FXCollections.observableArrayList();
    try {
        String query2 = "SELECT * from reclamation ";
        PreparedStatement smt = cnx.prepareStatement(query2);
        ResultSet rs = smt.executeQuery();
        while (rs.next()) {
            Reclamation r = new Reclamation(rs.getInt("id"), rs.getString("reference"), rs.getString("nom_d"), rs.getString("prenom_d"), rs.getInt("cin"), rs.getString("email"), rs.getString("commentaire"), rs.getDate("created_at"), rs.getString("statut"), rs.getString("file"), rs.getString("tel"));
            ReclamationList.add(r);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }

    return ReclamationList;
}

public void ShowListe() {
    ObservableList<Reclamation> list = getReclamationList();
    lvReclamation.setItems(list);
    
    // Définir la cellFactory pour le ListView
    lvReclamation.setCellFactory(new Callback<ListView<Reclamation>, ListCell<Reclamation>>() {
        @Override
        public ListCell<Reclamation> call(ListView<Reclamation> param) {
            return new ListCell<Reclamation>() {
                @Override
                protected void updateItem(Reclamation item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        // Définir le texte à afficher pour chaque attribut de l'objet Reclamation
                        setText("ID: " + item.getId() + "\n"
                                + "Référence: " + item.getReference() + "\n"
                                + "Nom: " + item.getNom_d() + "\n"
                                + "Prénom: " + item.getPrenom_d() + "\n"
                                + "CIN: " + item.getCin() + "\n"
                                + "Email: " + item.getEmail() + "\n"
                                + "Commentaire: " + item.getCommentaire() + "\n"
                                + "Créé le: " + item.getCreated_at() + "\n"
                                + "Statut: " + item.getStatut() + "\n"
                                + "Fichier: " + item.getFile() + "\n"
                                + "Téléphone: " + item.getTel());
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
        this.idSelected = lvReclamation.getSelectionModel().getSelectedItem().getId();
        
        ReclamationService reclamationService = new ReclamationService();
            Reclamation reclamation =reclamationService.recup(this.idSelected);

        
        
        String imagePath = "file:\\" + reclamation.getFile(); // Replace with the actual file path

        Image image = new Image(imagePath); 
        
        this.image.setImage(image);




        
                
    }

    @FXML
    private void refresh(ActionEvent event) {
        ShowListe();
    }

    @FXML
    private void supprimer(ActionEvent event) {
                this.idSelected = lvReclamation.getSelectionModel().getSelectedItem().getId();
  ReclamationService reclamationService = new ReclamationService();
           reclamationService.supprimer(this.idSelected);
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("La réclamation a été supprimée avec succès.");
        alert.showAndWait();
        ShowListe();
    }

    @FXML
  private void reponse() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("AjouterReponse.fxml"));
            Scene c = new Scene(root);
            Stage stage = (Stage) reponse.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            Logger.getLogger(AjouterReponseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}



