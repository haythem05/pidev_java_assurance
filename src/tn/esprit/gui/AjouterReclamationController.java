/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.util.UUID;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.entities.Reclamation;
import tn.esprit.services.ReclamationService;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.logging.Logger;
import java.util.logging.Level;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.zip.GZIPOutputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;



/**
 * FXML Controller class
 *
 * @author MSI
 */
public class AjouterReclamationController implements Initializable {

    @FXML
    private Label fxreference; // Changer le type de l'attribut à Label
    @FXML
    private TextArea fxnom_d;
    @FXML
    private TextArea fxprenom_d;
    @FXML
    private TextArea fxcin;
    @FXML
    private TextArea fxemail;
    @FXML
    private TextArea fxcommentaire;
    @FXML
    private TextArea fxtel;
    File  selectedFile;
    
    @FXML
    private ImageView imageV;
    public String file;
 
    @FXML
    private Button ajout;
    @FXML
    private Button retour;
   

    public Connection cnx;
    public Statement stm;
    String sql = "";

@Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Initialize your database connection here
            cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/assurancepidev", "root", "");
        } catch (SQLException ex) {
            System.out.println("Failed to connect to database: " + ex.getMessage());
        } 
        Reclamation r = new Reclamation();


        ajout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Reclamation r = new Reclamation();
                r.setNom_d(fxnom_d.getText());
                r.setPrenom_d(fxprenom_d.getText());
                int cin = 0;
                if (!fxcin.getText().isEmpty() && fxcin.getText().matches("\\d+")) {
                    cin = Integer.parseInt(fxcin.getText());
                }
                r.setCin(cin);
                r.setEmail(fxemail.getText());
                r.setCommentaire(fxcommentaire.getText());
                r.setTel(fxtel.getText());

                ajouter(r);
            }
        });


        retour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                redirectToList();
            }
        });

  imageV.setOnDragDropped(new EventHandler<DragEvent>() {
    public void handle(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;
            String path = null;
            for (File file : db.getFiles()) {
                path = file.getName();
                System.out.println("Drag and drop file done and path=" + file.getAbsolutePath());
                imageV.setImage(new Image("file:" + file.getAbsolutePath()));

                // Vérifier la taille du fichier
                long fileSize = file.length();
                if (fileSize > 1048576) { // Limite de 1 Mo
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Avertissement");
                    alert.setHeaderText(null);
                    alert.setContentText("Le fichier ne doit pas dépasser 1 Mo.");
                    alert.showAndWait();
                    return;
                }

                // Ajouter le nom de l'image à l'objet r
                r.setFile(file.getName());
            }
        }
        event.setDropCompleted(success);
        event.consume();
    }
});



imageV.setImage(new Image("file:C:/Users/MSI/Desktop/PidevJ3A40/src/tn/esprit/images/drag-drop.gif"));
    }



    

     
   public void ajouter(Reclamation r) {
   // Générer la référence aléatoire
 String reference = Reclamation.genererReference();
 fxreference.setText(reference);
   String nom_d = r.getNom_d();
   String prenom_d = r.getPrenom_d();
   int cin = r.getCin();
   String email = r.getEmail();
   String commentaire = r.getCommentaire();
   String tel = r.getTel();
   
   
   // Vérifier si tous les champs sont vides
if (nom_d.isEmpty() && prenom_d.isEmpty() && cin == 0
        && email.isEmpty() && commentaire.isEmpty() && tel.isEmpty()) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Tous les champs sont obligatoires!");
    alert.showAndWait();
    return;
}


    // Validate input fields 
if (reference == null || !reference.matches("^[a-zA-Z0-9]+$")) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("La référence doit contenir uniquement des lettres et des chiffres!");
    alert.showAndWait();
    return;
}

if (nom_d == null || nom_d.trim().isEmpty()) {
Alert alert = new Alert(Alert.AlertType.ERROR);
alert.setHeaderText(null);
alert.setContentText("Le champ nom est obligatoire!");
alert.showAndWait();
return;

} else if (!nom_d.matches("^[a-zA-Z ]+$")) {
Alert alert = new Alert(Alert.AlertType.ERROR);
alert.setHeaderText(null);
alert.setContentText("Le champ nom ne doit contenir que des lettres et des espaces.");
alert.showAndWait();
return;
}

if (prenom_d == null || prenom_d.trim().isEmpty()) {
Alert alert = new Alert(Alert.AlertType.ERROR);
alert.setHeaderText(null);
alert.setContentText("Le champ prénom est obligatoire!");
alert.showAndWait();
return;

} else if (!prenom_d.matches("^[a-zA-Z ]+$")) {
Alert alert = new Alert(Alert.AlertType.ERROR);
alert.setHeaderText(null);
alert.setContentText("Le champ prénom ne doit contenir que des lettres et des espaces.");
alert.showAndWait();
return;
}

if (cin == 0) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Le champ CIN est obligatoire!");
    alert.showAndWait();
    return;
} else if (String.valueOf(cin).length() != 8) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Le champ CIN doit contenir exactement 8 chiffres sans caractères.");
    alert.showAndWait();
    return;
}


if (email == null || email.isEmpty()) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Le champ email est obligatoire!");
    alert.showAndWait();
    return;


} else if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Le champ email n'est pas valide.");
    alert.showAndWait();
    return;
}

if (commentaire == null || commentaire.isEmpty()) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Le champ commentaire est obligatoire!");
    alert.showAndWait();
    return;
} else if (commentaire.length() < 10) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Le champ commentaire doit contenir au moins 10 caractères.");
    alert.showAndWait();
    return;
}

if (tel == null || tel.isEmpty()) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Le champ téléphone est obligatoire!");
    alert.showAndWait();
    return;
}


 
 // Ajouter la réclamation avec la référence générée, la date de création et le statut "En cours"
sql = "insert into reclamation(reference, nom_d, prenom_d, cin, email, commentaire, tel, created_at, statut, file) values (?,?,?,?,?,?,?,?,?,?)";
try {
    PreparedStatement ste = cnx.prepareStatement(sql);
    ste.setString(1, reference);
    ste.setString(2, r.getNom_d());
    ste.setString(3, r.getPrenom_d());
    ste.setInt(4, r.getCin());
    ste.setString(5, r.getEmail());
    ste.setString(6, r.getCommentaire());
    ste.setString(7, r.getTel());
    ste.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
    ste.setString(9, "En cours");
    ste.setString(10, this.file); // Ajouter le nom de fichier à la base de données

    ste.executeUpdate();
    // Définir la référence générée pour la nouvelle réclamation
    r.setReference(reference);
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Information");
    alert.setHeaderText(null);
    alert.setContentText("La réclamation a été ajoutée avec succès.");
    alert.showAndWait();

    // Envoyer un SMS avec Twilio
    String ACCOUNT_SID = "AC8d0ef4234781bddf96867d3ec05586cb";
    String AUTH_TOKEN = "4f05938ff4b3f5376ec2276918e0d119";
    String TWILIO_NUMBER = "+16813346926";
    String message = "Votre réclamation sous le nom de " + nom_d + " " + prenom_d + " a été ajoutée avec la référence : " + reference;
    PhoneNumber toNumber = new PhoneNumber(r.getTel());
    PhoneNumber fromNumber = new PhoneNumber(TWILIO_NUMBER);

    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message twilioMessage = Message.creator(toNumber, fromNumber, message).create();
    System.out.println("SMS envoyé avec succès !");
} catch (SQLException ex) {
    System.out.println(ex.getMessage());
}
   }





     public void redirectToList() {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("AfficherReclamation.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) retour.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        Logger.getLogger(AjouterReclamationController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    private void retour(MouseEvent event) {
        this.redirectToList();
    }

  @FXML
    private void addImage(MouseEvent event) {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.home") + "\\Desktop"));
        fc.setTitle("Veuillez choisir l'image");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image", "*.jpg", "*.png"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg")
        );
        selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {

            // Load the selected image into the image view
            Image image1 = new Image(selectedFile.toURI().toString());

            System.out.println(selectedFile.toURI().toString());
            imageV.setImage(image1);

            // Create a new file in the destination directory
            File destinationFile = new File("C:\\xampp\\htdocs\\imagesAssurance\\" + selectedFile.getName());

            this.file = selectedFile.getName();

            try {
                // Copy the selected file to the destination file
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.err.println(e);
            }

        }
    }

 

   

}
    

    


    

