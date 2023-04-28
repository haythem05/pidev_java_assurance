package gui;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import entities.Utilisateur;

public class ProfileController implements Initializable {

    @FXML
    private Label fullNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label cinLabel;

    @FXML
    private Label birthDateLabel;

    @FXML
    private ImageView profileImage;

    @FXML
    private Button uploadButton;

    @FXML
    private Button modifyButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set initial values for profile details labels
        String fullName = getNom() + " " + getPrenom();
        fullNameLabel.setText(fullName);
        lastNameLabel.setText(getNom());
        firstNameLabel.setText(getPrenom());
        emailLabel.setText(getEmail());
        phoneLabel.setText(getNumtel());
        cinLabel.setText(getCin());
        birthDateLabel.setText(getDatedenaissance());
    }

    @FXML
    private void handleUpload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload Profile Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(uploadButton.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            profileImage.setImage(image);
        }
    }

    @FXML
    private void handleModify(ActionEvent event) {
        // TODO: handle profile modification
    }

}