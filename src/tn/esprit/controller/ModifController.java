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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import tn.esprit.entities.Sinistre;
import tn.esprit.entities.Type;
import tn.esprit.services.SinistreService;

/**
 * FXML Controller class
 *
 * @author HD
 */
public class ModifController implements Initializable {

    @FXML
    private ImageView imageV;
    @FXML
    private TextField tf_lieu;
    @FXML
    private TextField tf_degats;
    @FXML
    private TextField tf_description;
    @FXML
    private DatePicker date;
    
    private String url_image;
    File selectedFile;
    @FXML
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tf_lieu.setText(String.valueOf(AfficherController.lieu));
        tf_degats.setText(String.valueOf(AfficherController.degats));
        tf_description.setText(String.valueOf(AfficherController.description));
        date.setPromptText(AfficherController.date_heure.toString());
        //imageV.setImage(new Image("file:C:\\Users\\HD\\Desktop\\Installations\\XAMPP\\htdocs\\imagePi\\" + AfficherController.url_image));
    }    

    @FXML
    private void modifier(ActionEvent event) {
        try {
            int id = AfficherController.id;
            String lieu = tf_lieu.getText();
            String degats = tf_degats.getText();
            String description = tf_description.getText();
            LocalDate d = date.getValue();
            Timestamp date_heure = Timestamp.valueOf(d.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime());
            Type type = new Type();

            Sinistre s = new Sinistre(date_heure, lieu, degats, description, type);

            SinistreService ss = new SinistreService();

            ss.modifier(s, id);
            
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/Afficher.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    @FXML
    private void addimage(MouseEvent event) {
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
    
}
