/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.controller;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
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
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.util.Base64;
import java.util.Optional;
import java.util.zip.GZIPOutputStream;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Element;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.net.MalformedURLException;
import tn.esprit.services.Emailsender;



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
} catch (SQLException ex) {
    System.out.println(ex.getMessage());
}

// Demander si l'utilisateur souhaite générer et télécharger le PDF
Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
alert.setTitle("Génération de PDF");
alert.setHeaderText(null);
alert.setContentText("Voulez-vous générer et télécharger un PDF pour cette réclamation ?");

Optional<ButtonType> result = alert.showAndWait();

if (result.isPresent() && result.get() == ButtonType.OK) {
    // Générer et télécharger le PDF
    try {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("reclamation_" + reference + ".pdf"));
        document.open();

       // Ajouter un logo en haut à gauche de la page
String logoPath = "C:\\Users\\MSI\\Desktop\\PidevJ3A40\\src\\tn\\esprit\\images\\logo_java.png"; // Remplacez par le chemin d'accès réel du fichier de logo
com.itextpdf.text.Image logo = null;
try {
    logo = com.itextpdf.text.Image.getInstance(logoPath);
    logo.setAbsolutePosition(30, 730); // Modifier la position absolue du logo pour le faire monter plus haut à gauche
    logo.scaleAbsolute(100, 100); // Réduire la taille du logo à 50x50 pixels
    document.add(logo);
} catch (IOException | BadElementException e) {
    e.printStackTrace();
}
        
        document.add(new Paragraph(" ")); // Ajouter un espacement après le logo

        // Ajouter un message de confirmation centré avec un espacement avant
        String confirmationMessage = "Bonjour cher(e) client, votre réclamation a bien été reçue. Nous la traiterons dans les meilleurs délais.";
        Paragraph confirmation = new Paragraph(confirmationMessage);
        confirmation.setAlignment(Element.ALIGN_CENTER);
        confirmation.setSpacingBefore(50); // Ajouter un espacement avant le message
        document.add(confirmation);

        // Ajouter un espacement entre le message de confirmation et le tableau
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100); // Définir la largeur du tableau à 100%
        table.setSpacingBefore(10); // Ajouter un espacement avant le tableau

        // Ajouter un fond mauve clair pour les cellules de la première colonne
        PdfPCell cellAttribut = new PdfPCell(new Phrase("Attribut"));
        cellAttribut.setBackgroundColor(new BaseColor(204, 153, 255)); // Mauve clair
        table.addCell(cellAttribut);

        // Ajouter un fond mauve clair pour les cellules de la deuxième colonne
        PdfPCell cellValeur = new PdfPCell(new Phrase("Valeur"));
        cellValeur.setBackgroundColor(new BaseColor(204, 153, 255)); // Mauve clair
        table.addCell(cellValeur);

        // Créer une instance de BaseColor pour la couleur bleu ciel
        BaseColor bleuCiel = new BaseColor(135, 206, 235); // bleu ciel

        PdfPCell cellReference = new PdfPCell(new Phrase("Référence"));
        cellReference.setBackgroundColor(bleuCiel);
        table.addCell(cellReference);

        PdfPCell cellReferenceValue = new PdfPCell(new Phrase(reference));
        cellReferenceValue.setBackgroundColor(bleuCiel);
        table.addCell(cellReferenceValue);
            PdfPCell cellNom = new PdfPCell(new Phrase("Nom"));
    cellNom.setBackgroundColor(bleuCiel);
    table.addCell(cellNom);

    PdfPCell cellNomValue = new PdfPCell(new Phrase(r.getNom_d()));
    cellNomValue.setBackgroundColor(bleuCiel);
    table.addCell(cellNomValue);

    PdfPCell cellPrenom = new PdfPCell(new Phrase("Prénom"));
    cellPrenom.setBackgroundColor(bleuCiel);
    table.addCell(cellPrenom);

    PdfPCell cellPrenomValue = new PdfPCell(new Phrase(r.getPrenom_d()));
    cellPrenomValue.setBackgroundColor(bleuCiel);
    table.addCell(cellPrenomValue);

    PdfPCell cellCIN = new PdfPCell(new Phrase("CIN"));
    cellCIN.setBackgroundColor(bleuCiel);
    table.addCell(cellCIN);

    PdfPCell cellCINValue = new PdfPCell(new Phrase(String.valueOf(r.getCin())));
    cellCINValue.setBackgroundColor(bleuCiel);
    table.addCell(cellCINValue);

    PdfPCell cellEmail = new PdfPCell(new Phrase("Email"));
    cellEmail.setBackgroundColor(bleuCiel);
    table.addCell(cellEmail);

    PdfPCell cellEmailValue = new PdfPCell(new Phrase(r.getEmail()));
    cellEmailValue.setBackgroundColor(bleuCiel);
    table.addCell(cellEmailValue);

    PdfPCell cellCommentaire = new PdfPCell(new Phrase("Commentaire"));
    cellCommentaire.setBackgroundColor(bleuCiel);
    table.addCell(cellCommentaire);

    PdfPCell cellCommentaireValue = new PdfPCell(new Phrase(r.getCommentaire()));
    cellCommentaireValue.setBackgroundColor(bleuCiel);
    table.addCell(cellCommentaireValue);

    PdfPCell cellTel = new PdfPCell(new Phrase("Téléphone"));
    cellTel.setBackgroundColor(bleuCiel);
    table.addCell(cellTel);

    PdfPCell cellTelValue = new PdfPCell(new Phrase(r.getTel()));
    cellTelValue.setBackgroundColor(bleuCiel);
    table.addCell(cellTelValue);

    PdfPCell cellDateCreation = new PdfPCell(new Phrase("Date de création"));
    cellDateCreation.setBackgroundColor(bleuCiel);
    table.addCell(cellDateCreation);

    PdfPCell cellDateCreationValue = new PdfPCell(new Phrase(new Timestamp(System.currentTimeMillis()).toString()));
    cellDateCreationValue.setBackgroundColor(bleuCiel);
    table.addCell(cellDateCreationValue);

    PdfPCell cellStatut = new PdfPCell(new Phrase("Statut"));
    cellStatut.setBackgroundColor(bleuCiel);
    table.addCell(cellStatut);

    PdfPCell cellStatutValue = new PdfPCell(new Phrase("En cours"));
    cellStatutValue.setBackgroundColor(bleuCiel);
    table.addCell(cellStatutValue);

    document.add(table);

    // Ajouter une phrase à droite en bas de la page
    Paragraph cordialement = new Paragraph("Cordialement");
    cordialement.setAlignment(Element.ALIGN_RIGHT);
    cordialement.setSpacingBefore(50); // Ajouter un espacement avant la phrase
    cordialement.setFont(FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)); // Minimiser l'écriture et rendre la phrase en gras
    document.add(cordialement);

    // Ajouter une photo de signature avec son path en dessous de la phrase "Cordialement"
    String signaturePath = "C:\\Users\\MSI\\Desktop\\PidevJ3A40\\src\\tn\\esprit\\images\\signature_ines_besaad.jpg"; // Remplacez par le chemin d'accès réel du fichier de signature
    com.itextpdf.text.Image signature = null;
    try {
        signature = com.itextpdf.text.Image.getInstance(signaturePath);
        signature.setAlignment(Element.ALIGN_RIGHT); // Aligner la signature à droite de la page
        signature.scaleAbsolute(100, 50); // Réduire la taille de la signature à 100x50 pixels
        document.add(signature);
} catch (IOException | BadElementException e) {
e.printStackTrace();
}
// Ajouter le nom et poste de la directrice en dessous de la photo de signature
Paragraph directeur = new Paragraph("Ines Bessaad");
directeur.setAlignment(Element.ALIGN_RIGHT);
directeur.setSpacingBefore(10); // Ajouter un espacement avant le nom du directeur
directeur.setFont(FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)); // Minimiser l'écriture et rendre le nom du directeur en gras
document.add(directeur);

Paragraph posteDirecteur = new Paragraph("PDG SecurAssur");
posteDirecteur.setAlignment(Element.ALIGN_RIGHT);
posteDirecteur.setFont(FontFactory.getFont(FontFactory.HELVETICA, 10)); // Changer la police d'écriture pour le poste de la directrice
document.add(posteDirecteur);


        document.close();

    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
    successAlert.setTitle("Information");
    successAlert.setHeaderText(null);
    successAlert.setContentText("Le PDF a été généré et téléchargé avec succès.");
    successAlert.showAndWait();
} catch (FileNotFoundException | DocumentException ex) {
    System.out.println(ex.getMessage());
}
} else {
Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
successAlert.setTitle("Information");
successAlert.setHeaderText(null);
successAlert.setContentText("La réclamation a été ajoutée avec succès.");
successAlert.showAndWait();
}


//// Envoyer un SMS avec Twilio
//String ACCOUNT_SID = "AC8d0ef4234781bddf96867d3ec05586cb";
//String AUTH_TOKEN = "4a232110ce4b0b70b05f7b05d9470d79";
//String TWILIO_NUMBER = "+16813346926";
//String message = "Votre réclamation sous le nom de " + r.getNom_d() + " " + r.getPrenom_d() + " a été ajoutée avec la référence : " + reference;
//PhoneNumber toNumber = new PhoneNumber(r.getTel());
//PhoneNumber fromNumber = new PhoneNumber(TWILIO_NUMBER);
//
//Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//Message twilioMessage = Message.creator(toNumber, fromNumber, message).create();
//System.out.println("SMS envoyé avec succès !");
//Emailsender.sendEmail_add("adam.messaoudi@esprit.tn", "Merci pour votre confiance.\nNous avons bien reçu votre réclamation.\nPatientez quelques instants, elle est en cours de traitement.\n\nÀ bientôt.");

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
    

    


    

