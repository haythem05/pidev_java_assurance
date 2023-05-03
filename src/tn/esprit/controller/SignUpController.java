/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.controller;

import tn.esprit.entities.User;
import tn.esprit.services.UserService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * FXML Controller class
 *
 * @author iHoussem
 */
public class SignUpController implements Initializable {

    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfPrenom;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfTel;
    @FXML
    private Label lbPath;
    
    public static String HomeMsg;
    public static User clHome;
    @FXML
    private TextField tfCin;
    @FXML
    private DatePicker tfDate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    

    @FXML
    private void fnSignUp(ActionEvent event) throws IOException {   
        UserService serC=new UserService();
        User cl1=new User();
        cl1.setUsername(tfNom.getText());
        cl1.setPrenom(tfPrenom.getText());
        cl1.setCin(Integer.parseInt(tfCin.getText()));
        cl1.setDatenaissance(java.sql.Date.valueOf(tfDate.getValue()));
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(tfEmail.getText());
        if(mat.matches()){
            cl1.setEmail(tfEmail.getText());
        UserService serU=new UserService();
        String password=serU.crypter_password(tfPassword.getText());
        cl1.setPassword(password);
        
            try {
                
                if((Integer.parseInt(tfTel.getText())>=20000000 && Integer.parseInt(tfTel.getText())<30000000) || (Integer.parseInt(tfTel.getText())>=50000000 && Integer.parseInt(tfTel.getText())<=59999999) || (Integer.parseInt(tfTel.getText()) >=90000000 && Integer.parseInt(tfTel.getText())<100000000)){
                    cl1.setNumero(Integer.parseInt(tfTel.getText()));
        cl1.setRole(false);
        
        serC.ajouter(cl1);
        HomeMsg="SignUp avec succes";
        clHome=cl1;
        Parent etab = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/Home.fxml"));      
        Scene scene = new Scene(etab);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
                }else{
                    showMessageDialog(null, "enter Valid number");
                }
                
            } catch (NumberFormatException e) {
                showMessageDialog(null, "enter Valid number");
            }
        
        }else{
            showMessageDialog(null, "enter Valid Email");
        }
        
        
    }
    
}
