/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

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

/**
 * FXML Controller class
 *
 * @author msi
 */
public class ChatBotController implements Initializable {

    @FXML
    private Label lblTitle;
    @FXML
    private TextField txtInput;
    @FXML
    private Button btnSend;
    @FXML
    private Label lblOutput;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set button action
        btnSend.setOnAction(event -> {
            String input = txtInput.getText();
            System.out.println("input" + input);
            String response = getResponse(input);
            System.out.println(response);
            lblOutput.setText(response);
            lblOutput.setWrapText(true); // Activate automatic line wrapping
            txtInput.clear();
        });
    }

    private String getResponse(String input) {
        // Return specific responses for certain questions
        if (input.toLowerCase().contains("différence") && input.toLowerCase().contains("assurance")) {
            return "Une assurance automobile tous risques couvre généralement les dommages causés à votre propre véhicule, ainsi que les dommages que vous pourriez causer à d'autres personnes ou à leurs biens. Elle peut également inclure des garanties supplémentaires comme une assistance dépannage ou une assurance du conducteur. En revanche, une assurance au tiers ne couvre que les dommages que vous pourriez causer à d'autres personnes ou à leurs biens, sans couvrir les dommages subis par votre propre véhicule.";
        } else if (input.toLowerCase().contains("remboursement") && input.toLowerCase().contains("frais médicaux")) {
            return "Le remboursement des frais médicaux en cas d'accident dépendra de votre contrat d'assurance et de vos garanties. Si vous avez souscrit une assurance responsabilité civile automobile, celle-ci pourra couvrir les frais médicaux des tiers impliqués dans l'accident, mais pas vos propres frais. Si vous avez souscrit une assurance corporelle conducteur, celle-ci pourra couvrir vos propres frais médicaux en cas d'accident responsable ou non responsable. Il est important de bien vérifier les conditions de votre contrat pour savoir quelle prise en charge est prévue.";
        } else if (input.toLowerCase().contains("assurance") && input.toLowerCase().contains("événement spécial")) {
            return "Pour obtenir une assurance pour un événement spécial, vous pouvez contacter une compagnie d'assurance qui propose ce type de contrat. Vous devrez généralement fournir des informations sur l'événement en question, comme sa date, son lieu, le nombre de participants, etc.";
        } else if (input.toLowerCase().contains("salut")) {
            return "Bonjour, comment puis-je vous aider ?";
        } else if (input.toLowerCase().contains("I have some questions")) {
            return "Je ferai de mon mieux pour vous aider.";
        }

        // Return default response if no specific response is matched
        return ChatBotConstants.DEFAULT_RESPONSE;
    }
}

























/*        
  private String getResponse(String input) {
    if (input.contains("hello") || input.contains("bonjour")) {
        return "Bonjour! Comment puis-je vous aider aujourd'hui?";
    } else if (input.contains("Je veux savoir l'heure quand mon plat sera pret")) {
        return "EnergyBox Bot :Vous trouvez tous les informations qui concerne la réservation dans le QrcCode \n qui apparait lors de réserver ou bien le SMS qui sera envoyé à vous";
    } else if (input.contains("Merci")) {
        return "Je suis désolé pour cela. Je vais demander au coach de vous contacter directement.";
    } else if (input.contains("je veux changer la date du cours avec ce coach")) {
        return "D'accord, nous allons vous contacter pour fixer une nouvelle date.";
    } else if (input.contains("merci")) {
        return "Merci pour votre fidélité avec Sportifly !";
    } else {
        return "Je suis désolé, je ne comprends pas. Pouvez-vous reformuler votre question s'il vous plaît ?";
    }
}
}  
    
*/