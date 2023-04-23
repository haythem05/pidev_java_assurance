/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import tn.esprit.entities.Reclamation;
import tn.esprit.entities.Reponse;
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
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import pidevj3a40.PidevJ3A40;

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
    private Button approuver;
          @FXML
    private Button refuser;
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
   @FXML
    private Button tric;
    @FXML
    private Button trid;
    
       ObservableList<Reclamation> data=FXCollections.observableArrayList();
  
   
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Initialize your database connection here
            cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/assurancepidev", "root", "");
        } catch (SQLException ex) {
            System.out.println("Failed to connect to database: " + ex.getMessage());
        }
       
        
        reponse.setOnAction((ActionEvent event) -> {
            reponse();
        });
         approuver.setOnAction((ActionEvent event) -> {
    approuver(event);
});

refuser.setOnAction((ActionEvent event) -> {
    refuser(event);
});
tric.setOnAction(event -> {
    trierReclamationsParDate(true); // Tri par date croissante
});
trid.setOnAction(event -> {
    trierReclamationsParDate(false); // Tri par date decroissante
});


        // Appel de la méthode ShowListe() pour afficher la liste des réclamations
        ShowListe();
        
    }

   

public ObservableList<Reclamation> getReclamationList() {
cnx = MaConnexion.getInstance().getCnx();
ObservableList<Reclamation> ReclamationList = FXCollections.observableArrayList();
try {
String query2 = "SELECT * from reclamation LEFT JOIN reponse ON reclamation.id = reponse.reclamation_id";
PreparedStatement smt = cnx.prepareStatement(query2);
ResultSet rs = smt.executeQuery();
while (rs.next()) {
Reclamation r = new Reclamation(rs.getInt("id"), rs.getString("reference"), rs.getString("nom_d"), rs.getString("prenom_d"), rs.getInt("cin"), rs.getString("email"), rs.getString("commentaire"), rs.getDate("created_at"), rs.getString("statut"), rs.getString("file"), rs.getString("tel"));
if (rs.getInt("reponse.id") != 0) {
Reponse rep = new Reponse(rs.getInt("reponse.id"), rs.getString("reponse.id_user"), rs.getString("reponse.note"), rs.getDate("reponse.created_at"), r);
    r.setReponse(rep);
}
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
                        setText( "Référence: " + item.getReference() + "\n"
                                + "Nom: " + item.getNom_d() + "\n"
                                + "Prénom: " + item.getPrenom_d() + "\n"
                                + "CIN: " + item.getCin() + "\n"
                                + "Email: " + item.getEmail() + "\n"
                                + "Commentaire: " + item.getCommentaire() + "\n"
                                + "Créé le: " + item.getCreated_at() + "\n"
                                + "Statut: " + item.getStatut() + "\n"
                                + "Fichier: " + item.getFile() + "\n"
                                + "Téléphone: " + item.getTel() + "\n");

                        // Si l'objet Reclamation a une réponse associée, ajouter le texte de la réponse
 if (item.getReponse() != null) {
    Reponse rep = item.getReponse();
    setText(getText() + "Note de la réponse: " + rep.getNote() + "\n");
} else {
    setText(getText() + "Note de la réponse: N/A\n");
}
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
private void reponse() {
try {
// Récupérer la réclamation sélectionnée dans le ListView
Reclamation selectedReclamation = lvReclamation.getSelectionModel().getSelectedItem();
if (selectedReclamation == null) {
// Si aucune réclamation n'est sélectionnée, afficher un message d'erreur
Alert alert = new Alert(Alert.AlertType.ERROR);
 alert.setTitle("Erreur");
alert.setHeaderText(null);
alert.setContentText("Veuillez sélectionner une réclamation avant de répondre.");
alert.showAndWait();
return;
}
    // Charger la page AjouterReponse.fxml
    FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterReponse.fxml"));
    Parent root = loader.load();

    // Passer la référence de la réclamation sélectionnée au contrôleur de la page AjouterReponse.fxml
    AjouterReponseController ajouterReponseController = loader.getController();
    ajouterReponseController.setReclamation(selectedReclamation);

    // Créer une nouvelle scène avec la page AjouterReponse.fxml et l'afficher
    Scene c = new Scene(root);
    Stage stage = (Stage) reponse.getScene().getWindow();
    stage.setScene(c);
} catch (IOException ex) {
    Logger.getLogger(AjouterReponseController.class.getName()).log(Level.SEVERE, null, ex);
}

}
@FXML
private void approuver(ActionEvent event) {
    Reclamation selectedReclamation = lvReclamation.getSelectionModel().getSelectedItem();
    if (selectedReclamation == null) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Avertissement");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner une réclamation à approuver.");
        alert.showAndWait();
        return;
    }
    int selectedReclamationId = selectedReclamation.getId();
    ReclamationService reclamationService = new ReclamationService();
    selectedReclamation.setStatut("Approuvée");
    try {
        reclamationService.modifier(selectedReclamationId, selectedReclamation); // Appel à la méthode modifier
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("La réclamation a été approuvée avec succès.");
        alert.showAndWait();
        ShowListe();
    } catch (Exception e) {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Une erreur est survenue lors de la modification de la réclamation.");
        alert.showAndWait();
    }
}


@FXML
private void refuser(ActionEvent event) {
    Reclamation selectedReclamation = lvReclamation.getSelectionModel().getSelectedItem();
    if (selectedReclamation == null) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Avertissement");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner une réclamation à refuser.");
        alert.showAndWait();
        return;
    }
    int selectedReclamationId = selectedReclamation.getId();
    ReclamationService reclamationService = new ReclamationService();
    selectedReclamation.setStatut("Refusée");
    try {
        reclamationService.modifier(selectedReclamationId, selectedReclamation); // Appel à la méthode modifier
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("La réclamation a été refusée avec succès.");
        alert.showAndWait();
        ShowListe();
    } catch (Exception e) {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Une erreur est survenue lors de la modification de la réclamation.");
        alert.showAndWait();
    }
}
public void trierReclamationsParDate(boolean croissant) {
    ObservableList<Reclamation> reclamations = lvReclamation.getItems();

    Comparator<Reclamation> comparator = (r1, r2) -> {
        if (croissant) {
            return r1.getCreated_at().compareTo(r2.getCreated_at());
        } else {
            return r2.getCreated_at().compareTo(r1.getCreated_at());
        }
    };

    FXCollections.sort(reclamations, comparator);
    lvReclamation.setItems(reclamations);
}

   @FXML
    private void stat(ActionEvent event) {
        Stage stageclose=(Stage)((Node)event.getSource()).getScene().getWindow();
        stageclose.close();
        try {
            Parent root=FXMLLoader.load(getClass().getResource("/tn.esprit.gui/StatReclamation.fxml"));

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



