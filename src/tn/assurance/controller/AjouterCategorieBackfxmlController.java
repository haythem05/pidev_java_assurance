/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.assurance.controller;

import java.io.File;
import java.io.IOException;
import tn.assurance.models.Categorie;
import tn.assurance.services.categorieS;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
//
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author haythem
 */
public class AjouterCategorieBackfxmlController implements Initializable {

    @FXML
    private TextField nomF;
    @FXML
    private TextField descriptionF;
    private String nom, description,path;
    File  selectedFile;
    
    @FXML
    private ImageView imageV;
    public String url_image;
    @FXML
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
                        System.out.println("Drag and drop file done and path=" + file.getAbsolutePath());
                        imageV.setImage(new Image("file:" + file.getAbsolutePath()));

                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });

        imageV.setImage(new Image("file:C:\\Users\\haythem\\Documents\\NetBeansProjects\\Assurance\\build\\classes\\images\\drag-drop.gif"));

    }

    @FXML
    private void validerC(ActionEvent event) throws IOException {

        nom = nomF.getText();
        description = descriptionF.getText();
        
         if (nom.isEmpty() || description.isEmpty() ) {
  Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs obligatoires.");
            alert.showAndWait();
        return;
    }

    if (nom.matches(".*\\d.*")) {
  Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("pas de nombes dans le champ nom !!");
            alert.showAndWait();
        return;
    }
     if (description.length() < 20) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Minumuim 20 caracteres !  ");
            alert.showAndWait();
        return; 
    }

        Categorie ca = new Categorie(nom, description, url_image);
        categorieS cS = new categorieS();

        cS.ajouterCategorie(ca);
        
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/assurance/gui/affichercategorie.fxml"));  
        
            rootPane.getChildren().setAll(pane);

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

            //url_image = file.toURI().toString();
            System.out.println(selectedFile.toURI().toString());
            imageV.setImage(image1);

            // Create a new file in the destination directory
            File destinationFile = new File("C:\\xampp\\htdocs\\imagesAssurance\\" + selectedFile.getName());
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
