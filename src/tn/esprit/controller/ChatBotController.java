/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.controller;

import tn.esprit.services.ChatBotConstants;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tn.esprit.services.ChatBotConstants;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;


/**
 * FXML Controller class
 *
 * @author MSI
 */
public class ChatBotController implements Initializable {

    /**
     * Initializes the controller class.
     */
    // Couleur des messages de l'utilisateur
    private static final String USER_MESSAGE_STYLE = "-fx-background-color: #87CEEB ; -fx-padding: 5px; -fx-border-radius: 10px;";
    // Couleur des messages du chatbot
    private static final String CHATBOT_MESSAGE_STYLE = "-fx-background-color: #D3D3D3; -fx-padding: 5px; -fx-border-radius: 10px;";

    @FXML
    private Label lblTitle;
    @FXML
    private VBox messageContainer;
    @FXML
    private TextField txtInput;
    @FXML
    private Button btnSend;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set button action
        btnSend.setOnAction(event -> {
            String input = txtInput.getText();
            String response = getResponse(input);
            appendUserMessage(input);
            appendChatbotMessage(response);
            txtInput.clear();
        });
    }

private String getResponse(String input) {
        // Return specific responses for certain questions
        if (input.toLowerCase().contains("salut") || input.toLowerCase().contains("bonjour") || input.toLowerCase().contains("bonsoir")) {
            return "Salut, comment puis-je vous aider ?";
        } else if (input.toLowerCase().contains("questions")) {
            return "Je ferai de mon mieux pour vous aider.";
} else if (input.toLowerCase().contains("différence") && input.toLowerCase().contains("une assurance automobile tous risques") && input.toLowerCase().contains("assurance au tiers")) {
return "Une assurance automobile tous risques couvre généralement les dommages causés à votre propre véhicule, ainsi que les dommages que vous pourriez causer à d'autres personnes ou à leurs biens. Elle peut également inclure des garanties supplémentaires comme une assistance dépannage ou une assurance du conducteur. En revanche, une assurance au tiers ne couvre que les dommages que vous pourriez causer à d'autres personnes ou à leurs biens, sans couvrir les dommages subis par votre propre véhicule.";
} else if (input.toLowerCase().contains("remboursement") && input.toLowerCase().contains("frais médicaux")) {
return "Le remboursement des frais médicaux en cas d'accident dépendra de votre contrat d'assurance et de vos garanties. Si vous avez souscrit une assurance responsabilité civile automobile, celle-ci pourra couvrir les frais médicaux des tiers impliqués dans l'accident, mais pas vos propres frais. Si vous avez souscrit une assurance corporelle conducteur, celle-ci pourra couvrir vos propres frais médicaux en cas d'accident responsable ou non responsable. Il est important de bien vérifier les conditions de votre contrat pour savoir quelle prise en charge est prévue.";
} else {
// Return a generic response for all other inputs
return "Je suis désolé, je ne suis pas sûr de comprendre votre question. Pourriez-vous reformuler votre demande, s'il vous plaît ?";
}
}
        

private void appendUserMessage(String message) {
    // Create a new label for the user's message
    Label userLabel = new Label(message);
    // Set the style for the user's message
    userLabel.setStyle(USER_MESSAGE_STYLE);
    // Set the width of the label to prevent truncation
    userLabel.setWrapText(true);
    userLabel.setMaxWidth(messageContainer.getWidth() * 0.5);
    // Add the label to the message container with a margin
    VBox.setMargin(userLabel, new Insets(5, 50, 5, 5));
    messageContainer.getChildren().add(userLabel);
}

private void appendChatbotMessage(String message) {
    // Create a new label for the chatbot's message
    Label chatbotLabel = new Label(message);
    // Set the style for the chatbot's message
    chatbotLabel.setStyle(CHATBOT_MESSAGE_STYLE);
    // Set the width of the label to prevent truncation
    chatbotLabel.setWrapText(true);
    chatbotLabel.setMaxWidth(messageContainer.getWidth() * 0.7);
    // Add the label to the message container with a margin
    VBox.setMargin(chatbotLabel, new Insets(5, 5, 5, 50));
    messageContainer.getChildren().add(chatbotLabel);
}


}
    


























