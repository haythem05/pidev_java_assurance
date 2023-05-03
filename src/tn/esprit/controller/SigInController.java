/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.controller;

import tn.esprit.entities.User;
import tn.esprit.entities.Session;
import tn.esprit.services.JavaMailUtil;
import tn.esprit.services.UserService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.mail.MessagingException;
import static javax.swing.JOptionPane.showMessageDialog;
import org.apache.commons.lang3.RandomStringUtils;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author iHoussem
 */
public class SigInController implements Initializable {

    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfPassword;
    public static User cl;
    public static int test=0;
    public static String HomeMsg;
    @FXML
    private HBox hboxEmail;
    @FXML
    private HBox hboxReset;
    @FXML
    private TextField tfReset;
    @FXML
    private HBox hboxPassword;
    @FXML
    private Button btnSignIn;
    @FXML
    private Button btnForgetPassword;
    @FXML
    private Button btnReset;
        

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        hboxEmail.setVisible(true);
        hboxPassword.setVisible(true);
        hboxReset.setVisible(false);
        btnForgetPassword.setVisible(true);
        btnSignIn.setVisible(true);
        btnReset.setVisible(false);
    }    

    @FXML
    private void fnSignIn(ActionEvent event) throws IOException {
        User cl1=new User();
        cl1.setEmail(tfEmail.getText());
        UserService serU=new UserService();
        String password=serU.crypter_password(tfPassword.getText());
        cl1.setPassword(password);
        UserService serC=new UserService();
        if(serC.Login(cl1).iconnected==0){
            Stage window = new Stage();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("");
        window.setWidth(400);
        Label label= new Label();
        label.setText("mot de passe n'est pas conforme");
        Button close=new Button("OK!");
        close.setOnAction(e->window.close());
        VBox layout=new VBox(10);
        layout.getChildren().addAll(label,close);
        layout.setAlignment(Pos.CENTER);
        
        Scene scene=new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
            
        }else{
            
            cl=serC.Login(cl1);
            test=1;
            HomeMsg="Welcome Connected succesfully";
            Session session = new Session();
            session.connectedUser = cl1;
            if(!serC.Login(cl1).isRole()){
                Parent etab = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/Home.fxml"));      
        Scene scene = new Scene(etab);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
            }else{
                Parent etab = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/HomeAdmin.fxml"));      
        Scene scene = new Scene(etab);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
            }
            
        }
    }

    @FXML
    private void fnForgetPassword(ActionEvent event) throws MessagingException {
         hboxEmail.setVisible(false);
        hboxPassword.setVisible(false);
        hboxReset.setVisible(true);
        btnForgetPassword.setVisible(false);
        btnSignIn.setVisible(false);
        String result = RandomStringUtils.random(6, false, true);
        JavaMailUtil.sendMail(tfEmail.getText(), "Code Reset ", result);
        Notifications.create()
              .title("Mail envoyer avec succées")
              .text("Veuillez vérifier votre boite de reception pour un mot de passe temporelle")
              .showWarning();
        UserService serC=new UserService();
        String password=serC.crypter_password(result);
        serC.modifierClientReset(tfEmail.getText(), password);
        btnReset.setVisible(true);
    }

    @FXML
    private void fnResetGo(ActionEvent event) throws IOException {
        UserService serC=new UserService();
        User user=serC.getUserByEmail(tfEmail.getText());
        String password=serC.crypter_password(tfReset.getText());
        user.setPassword(password);
        if(serC.Login(user).iconnected==0){
            Stage window = new Stage();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("");
        window.setWidth(400);
        Label label= new Label();
        label.setText("Code Non valide");
        Button close=new Button("OK!");
        close.setOnAction(e->window.close());
        VBox layout=new VBox(10);
        layout.getChildren().addAll(label,close);
        layout.setAlignment(Pos.CENTER);
        
        Scene scene=new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
            
        }else{
            cl=serC.Login(user);
            test=1;
            HomeMsg="Welcome Connected succesfully";
            if(!serC.Login(user).isRole()){
                Parent etab = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/Home.fxml"));      
        Scene scene = new Scene(etab);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        
            }else{
                Parent etab = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/HomeAdmin.fxml"));      
        Scene scene = new Scene(etab);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
            
            }}
    }
    
}
