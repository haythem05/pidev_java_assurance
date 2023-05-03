/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import java.util.Optional;
import java.util.function.Predicate;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import tn.esprit.services.ReponseService;


/**
 * FXML Controller class
 *
 * @author MSI
 */
public class AfficherReclamationController implements Initializable {

  
     @FXML
    private Button Créer;
    @FXML
    private Button ModifierR;
    @FXML
    private Button Supprimer;
      @FXML
    private Button generatePdfButton;
     
    @FXML
    private  ListView<Reclamation> lvReclamation;
    @FXML
    private Pane paneReclamations;
    
    
    public Connection cnx;
    public Statement stm;
    String sql = "";
    public int idSelected= -1;
    @FXML
    private Button refresh;
    @FXML
    private ImageView image;
      @FXML
    private TextField tfrecherche;
          ReclamationService rs=new ReclamationService();
    ReponseService reps=new ReponseService();
    
    
    
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
        

        
       

        // Appel de la méthode ShowListe() pour afficher la liste des réclamations
        ShowListe();
        
        tfrecherche.textProperty().addListener((observable, oldValue, newValue) -> {
        rechercherReclamations(newValue);
    });
       
    }

    private void GoToCréer() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/AjouterReclamation.fxml"));
            Scene c = new Scene(root);
            Stage stage = (Stage) Créer.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            Logger.getLogger(AjouterReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

private void GoToModifierR() {
Reclamation selectedReclamation = lvReclamation.getSelectionModel().getSelectedItem();
if (selectedReclamation == null) {
Alert alert = new Alert(Alert.AlertType.WARNING);
alert.setTitle("Aucune réclamation sélectionnée");
alert.setHeaderText("Sélectionnez une réclamation à modifier");
alert.showAndWait();
return;
}
if (selectedReclamation.getStatut().equals("En cours")) {
Alert alert = new Alert(Alert.AlertType.WARNING);
alert.setTitle("Avertissement");
alert.setHeaderText(null);
alert.setContentText("Vous ne pouvez pas modifier une réclamation en cours de traitement.");
alert.showAndWait();
return;
}
this.idSelected = selectedReclamation.getId();
try {
FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/gui/ModifierReclamation.fxml"));
Parent root = loader.load();
    ModifierReclamationController modifierReclamationController = loader.getController();
    modifierReclamationController.initializeFxml(this.idSelected);

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

        
        String imagePath = "file:\\C:\\xampp\\htdocs\\imagesAssurance\\" + reclamation.getFile(); // Replace with the actual file path
                System.out.println(imagePath);


        Image image = new Image(imagePath); 
        
        this.image.setImage(image);




        
                
    }

    @FXML
    private void refresh(ActionEvent event) {
        ShowListe();
    }

  @FXML
private void supprimer(ActionEvent event) {
Reclamation selectedReclamation = lvReclamation.getSelectionModel().getSelectedItem();
if (selectedReclamation == null) {
Alert alert = new Alert(Alert.AlertType.WARNING);
alert.setTitle("Avertissement");
alert.setHeaderText(null);
alert.setContentText("Veuillez sélectionner une réclamation à supprimer.");
alert.showAndWait();
return;
}
if (selectedReclamation.getReponse() != null && selectedReclamation.getReponse().getNote() != null) {
Alert alert = new Alert(Alert.AlertType.WARNING);
alert.setTitle("Avertissement");
alert.setHeaderText(null);
alert.setContentText("Vous ne pouvez pas supprimer une réclamation qui possède une réponse.");
alert.showAndWait();
return;
}
int selectedReclamationId = selectedReclamation.getId();
ReclamationService reclamationService = new ReclamationService();
Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
confirmation.setTitle("Confirmation");
confirmation.setHeaderText(null);
confirmation.setContentText("Voulez-vous vraiment supprimer cette réclamation ?");
Optional<ButtonType> result = confirmation.showAndWait();
if (result.get() == ButtonType.OK) {
reclamationService.supprimer(selectedReclamationId);
Alert alert = new Alert(Alert.AlertType.INFORMATION);
alert.setTitle("Information");
alert.setHeaderText(null);
alert.setContentText("La réclamation a été supprimée avec succès.");
alert.showAndWait();
ShowListe();
}
}

public void rechercherReclamations(String recherche) {
    ObservableList<Reclamation> list = getReclamationList();

    if (recherche != null && !recherche.isEmpty()) {
        Predicate<Reclamation> predicate = reclamation -> {
            String reference = reclamation.getReference();
            String nom = reclamation.getNom_d();
            String prenom = reclamation.getPrenom_d();
            String statut = reclamation.getStatut();

            return reference.contains(recherche)
                    || nom.contains(recherche)
                    || prenom.contains(recherche)
                    || statut.contains(recherche);
        };

        list = list.filtered(predicate);
    }

    lvReclamation.setItems(list);
}
@FXML
private void openChatbotWindow() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/gui/ChatBot.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

 
}



    























