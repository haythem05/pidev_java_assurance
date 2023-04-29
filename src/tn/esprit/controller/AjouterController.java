/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javax.mail.MessagingException;
import org.controlsfx.control.Notifications;
import tn.esprit.entities.Sinistre;
import tn.esprit.entities.Type;
import tn.esprit.services.Email;
import tn.esprit.services.Emailsender;
import static tn.esprit.services.Sendmail.sendMail;
import tn.esprit.services.SinistreService;
import tn.esprit.services.TypeService;

/**
 * FXML Controller class
 *
 * @author HD
 */
public class AjouterController implements Initializable {

    @FXML
    private TextField tf_lieu;
    private TextField tf_statut;
    @FXML
    private TextField tf_degats;
    @FXML
    private TextField tf_description;
    @FXML
    private ImageView imageV;

    private String path;
    File selectedFile;
    private String lieu, statut, degats, description;
    public String url_image;
    private Timestamp date_heure;
    @FXML
    private DatePicker date;

    private Type type;
    @FXML
    private ChoiceBox<Type> list_types;
    private List<Type> types;
    @FXML
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        types = new TypeService().afficher();
        //list_types.getItems().addAll(types);

        List<String> typeNames = new ArrayList<>();
        types.forEach(type -> typeNames.add(type.getNom()));
        list_types.getItems().addAll(types);

        list_types.setConverter(new StringConverter<Type>() {
            @Override
            public String toString(Type type) {
                return type.getNom();
            }

            @Override
            public Type fromString(String nom) {
                return null;
            }
        });

        imageV.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else {
                    event.consume();
                }
            }
        });

        // Dropping over surface
        imageV.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    path = null;
                    for (File file : db.getFiles()) {
                        path = file.getName();
                        selectedFile = new File(file.getAbsolutePath());
                        System.out.println("Drag and drop file done and path=" + file.getAbsolutePath());//file.getAbsolutePath()="C:\Users\X\Desktop\ScreenShot.6.png"
                        imageV.setImage(new Image("file:" + file.getAbsolutePath()));
                        url_image = path;
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
        imageV.setImage(new Image("file:C:\\Users\\HD\\Desktop\\Esprit\\PiDev\\src\\tn\\esprit\\images\\drag-drop.gif"));
    }

    @FXML
    private void add_image(MouseEvent event) {
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

            //url_image = file.toURI().toString();
            System.out.println(selectedFile.toURI().toString());
            imageV.setImage(image1);

            // Create a new file in the destination directory
            File destinationFile = new File("C:\\Users\\HD\\Desktop\\Installations\\XAMPP\\htdocs\\imagePi\\" + selectedFile.getName());
            url_image = selectedFile.getName();
            System.out.println("image ajoutée");

            try {
                // Copy the selected file to the destination file
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.err.println(e);
            }

        }
    }

    @FXML
    private void ajouter(ActionEvent event) throws MessagingException {
        lieu = tf_lieu.getText();
        degats = tf_degats.getText();
        description = tf_description.getText();
        LocalDate d = date.getValue();
        date_heure = Timestamp.valueOf(d.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime());
        type = list_types.getValue();

        if (lieu.isEmpty() || degats.isEmpty() || description.isEmpty() || date_heure == null || type == null) {
            // Afficher un message d'erreur si un champ obligatoire est vide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs obligatoires.");
            alert.showAndWait();
            return;
        }

        LocalDate now = LocalDate.now();
        if (d.isAfter(now)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur de saisie");
            alert.setContentText("La date sélectionnée ne peut pas dépasser la date actuelle.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ajout réussi");
        alert.setHeaderText(null);
        alert.setContentText("Le sinistre a été ajouté avec succès à la base de données.");
        alert.showAndWait();

        Sinistre s = new Sinistre(date_heure, lieu, degats, statut, description, url_image, type);
        SinistreService ss = new SinistreService();
        ss.ajouterSinistre(s, type);

        Emailsender.sendEmail_add("ines.bessaad@esprit.tn", "Merci pour votre confiance.\nNous avons bien reçu votre déclaration.\nPatientez quelques instants, votre sinistre est en attente de traitement.\n\nÀ bientôt.");
        //Email e = new Email("freeelanci@gmail.com", "jjrnaazzdfwhwfar", "ines.bessaad@esprit.tn", "Etat réclamation", " bonjour, Votre réclamation a été traitée");
        //e.sendEmail();
        //sendMail("ines.bessaad02@gmail.com","Category","Nouvelle category ajouté.");

        //Image img = new Image("file:///C:/xampp/htdocs/logo.png");
        Notifications n = Notifications.create()
                .title("SecurAssur")
                .text("Nouveau sinisitre ajouté !")
                .position(Pos.BOTTOM_RIGHT);

        n.darkStyle();
        n.showInformation();
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/Afficher.fxml"));
        rootPane.getChildren().setAll(pane);
    }

}
