/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.assurance.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import tn.assurance.models.Categorie;
import tn.assurance.services.categorieS;

/**
 * FXML Controller class
 *
 * @author haythem
 */
public class ModifiercategorieController implements Initializable {

    @FXML
    private TextField nomc;
    @FXML
    private TextField descriptionc;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView imagec;
    
    File  selectedFile;
    String url_im;
    @FXML
    private ImageView imageV;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          imageV.setImage(new javafx.scene.image.Image("file:C:\\Users\\haythem\\Documents\\NetBeansProjects\\Assurance\\build\\classes\\images\\logo.png"));
        nomc.setText(String.valueOf(affichercategorieController.nom));
        descriptionc.setText(String.valueOf(affichercategorieController.description));
        
        String imagePath = "C:\\xampp\\htdocs\\imagesAssurance\\" + affichercategorieController.url_image;
        try {
            imagec.setImage(new Image(new FileInputStream(imagePath)));
        } catch (FileNotFoundException e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
        
        url_im=imagePath;
        
        // TODO
    }    

    @FXML
    private void modifier(ActionEvent event) {
        try {
            int id=affichercategorieController.id;
            String nom=nomc.getText();
            String description=descriptionc.getText();
            
            Categorie ca = new Categorie(nom, description, url_im);
            categorieS cS = new categorieS();

         cS.modifierCategorie(ca, id);
         
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/tn/assurance/gui/affichercategorie.fxml"));  
        
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    @FXML
    private void setimage(MouseEvent event) {
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
            imagec.setImage(image1);

            // Create a new file in the destination directory
            File destinationFile = new File("C:\\xampp\\htdocs\\imagesAssurance\\" + selectedFile.getName());
            // url_image = "C:\\xampp\\htdocs\\image_trippie_cov\\" + file.getName();
            url_im = selectedFile.getName();

            try {
                // Copy the selected file to the destination file
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.err.println(e);
            }
    }
}
}
