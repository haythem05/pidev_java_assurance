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
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import tn.esprit.entities.Sinistre;
import tn.esprit.entities.Type;
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
    private String lieu,statut,degats,description;
    public String url_image;
    private Timestamp date_heure;
    @FXML
    private DatePicker date;
    
    private Type type;
    @FXML
    private ChoiceBox<Type> list_types;
    private List<Type> types;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        types = new TypeService().afficher();
        list_types.getItems().addAll(types);
        
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
            // url_image = "C:\\xampp\\htdocs\\image_trippie_cov\\" + file.getName();
            url_image = selectedFile.getName();

            try {
                // Copy the selected file to the destination file
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.err.println(e);
            }

        }
    }

    @FXML
    private void ajouter(ActionEvent event) {
        lieu = tf_lieu.getText();
        degats = tf_degats.getText();
        description = tf_description.getText();
        LocalDate d = date.getValue();
        date_heure = Timestamp.valueOf(d.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime());
        type = list_types.getValue();
        
        Sinistre s = new Sinistre(date_heure, lieu, degats, statut, description, url_image, type);
        SinistreService ss = new SinistreService();
        ss.ajouterSinistre(s,type);
    }

}
