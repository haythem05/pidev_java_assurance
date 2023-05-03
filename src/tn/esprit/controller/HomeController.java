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
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * FXML Controller class
 *
 * @author iHoussem
 */
public class HomeController implements Initializable {

    @FXML
    private Label lbHome;
    @FXML
    private ImageView ImgView;
    @FXML
    private Button btnLogOut;
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
    private Button btnUpdate;
    @FXML
    private Label lbId;
    @FXML
    private TextField tfCIN;
    @FXML
    private DatePicker tfDateNaissance;
    @FXML
    private PasswordField tfPasswordCon;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        show();
        
    }    

    @FXML
    private void fnLogOut(ActionEvent event) throws IOException {
        Parent etab = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/Main.fxml"));      
        Scene scene = new Scene(etab);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void show(){
        if(SigInController.test==1){
        tfNom.setText(SigInController.cl.getUsername());
        tfPrenom.setText(SigInController.cl.getPrenom());
        tfEmail.setText(SigInController.cl.getEmail());
        tfTel.setText(SigInController.cl.getNumero()+"");
        lbId.setText(SigInController.cl.getId()+"");
        tfCIN.setText(""+SigInController.cl.getCin());
        tfDateNaissance.setValue(SigInController.cl.getDatenaissance().toLocalDate());
        }else{
        tfNom.setText(SignUpController.clHome.getUsername());
        tfPrenom.setText(SignUpController.clHome.getPrenom());
        tfEmail.setText(SignUpController.clHome.getEmail());
        tfTel.setText(SignUpController.clHome.getNumero()+"");
        lbId.setText(SignUpController.clHome.getId()+"");
        
        tfCIN.setText(""+SignUpController.clHome.getCin());
        tfDateNaissance.setValue(SignUpController.clHome.getDatenaissance().toLocalDate());
        }
        
    }

    @FXML
    private void fnUpdate(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Modification");
		alert.setHeaderText("Action:");
		alert.setContentText("Vous voulez continuer ?");
		alert.showAndWait();
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(tfEmail.getText());
        if(mat.matches()){
         UserService serC=new UserService();
        User cl1=new User();
        cl1.setId(Integer.parseInt(lbId.getText()));
        cl1.setUsername(tfNom.getText());
        cl1.setPrenom(tfPrenom.getText());
        cl1.setEmail(tfEmail.getText());
        cl1.setDatenaissance(Date.valueOf(tfDateNaissance.getValue()));
        cl1.setCin(Integer.parseInt(tfCIN.getText()));
        UserService serU=new UserService();
        
        String password=serU.crypter_password(tfPassword.getText());
        cl1.setPassword(password);
                try{
                if((Integer.parseInt(tfTel.getText())>=20000000 && Integer.parseInt(tfTel.getText())<30000000) || (Integer.parseInt(tfTel.getText())>=50000000 && Integer.parseInt(tfTel.getText())<=59999999) 
                        || (Integer.parseInt(tfTel.getText()) >=90000000 && Integer.parseInt(tfTel.getText())<100000000)){
        cl1.setNumero(Integer.parseInt(tfTel.getText()));
        serC.modifier(cl1);   
        alert.close();
       }else{
                    showMessageDialog(null, "enter Valid number");
                }
                
            } catch (NumberFormatException e) {
                showMessageDialog(null, "enter Valid number");
            }
                
               
                }else{
            showMessageDialog(null, "enter Valid Email");
        }
        if(!tfPassword.getText().equals(tfPasswordCon.getText())){
                   showMessageDialog(null, "Mots de passe doit étre conforme");
               }
        } else if (option.get() == ButtonType.CANCEL) {
         alert.close();
        }
        
           
    }
    
}
