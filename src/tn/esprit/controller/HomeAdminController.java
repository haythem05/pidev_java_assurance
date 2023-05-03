/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.controller;

import tn.esprit.entities.User;
import tn.esprit.entities.Session;
import tn.esprit.services.UserService;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author iHoussem
 */
public class HomeAdminController implements Initializable {

    @FXML
    private Button btnLogOut;
    @FXML
    private Button btnSettings;
    @FXML
    private TableView<User> tvUsers;
    @FXML
    private TableColumn<User, String> colNom;
    @FXML
    private TableColumn<User, String> colPrenom;
    @FXML
    private TableColumn<User, String> colEmail;
    @FXML
    private TableColumn<User, Boolean> colRoles;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button btnPromote;
    @FXML
    private Label lbid;
    @FXML
    private Label lbUser;
    @FXML
    private TextField tfSearchUsers;

    UserService serC=new UserService();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       fnShow(); // TODO
       Session session = new Session();
       lbUser.setText(session.connectedUser.getEmail());
    }    

    @FXML
    private void fnLogOut(ActionEvent event) throws IOException {
         Parent etab = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/Main.fxml"));      
        Scene scene = new Scene(etab);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void fnSettings(ActionEvent event) throws IOException {
        Parent etab = FXMLLoader.load(getClass().getResource("/tn/esprit/gui/Home.fxml"));      
        Scene scene = new Scene(etab);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
       
    }

    @FXML
    private void fnSelected(MouseEvent event) {
        User client= tvUsers.getSelectionModel().getSelectedItem();
       lbid.setText(String.valueOf(client.getId()));
    }

    @FXML
    private void fnSupprimer(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Supprimer");
		alert.setHeaderText("Action:");
		alert.setContentText("Vous voulez continuer ?");
		alert.showAndWait();
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
         User u=   serC.getUserById(Integer.parseInt(lbid.getText()));
         serC.supprimer(u);
        fnShow();
        alert.close();
        } else if (option.get() == ButtonType.CANCEL) {
         alert.close();
        }
        
    }

    @FXML
    private void fnPromote(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Promote");
		alert.setHeaderText("Action:");
		alert.setContentText("Vous voulez continuer ?");
		alert.showAndWait();
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
         serC.promote(Integer.parseInt(lbid.getText()));
        fnShow();
        alert.close();
        } else if (option.get() == ButtonType.CANCEL) {
         alert.close();
        }
    }
    
    
    public void fnShow(){
     ObservableList<User> list = FXCollections.observableArrayList(serC.afficher());;
    
     colNom.setCellValueFactory(new PropertyValueFactory<>("username"));       
     colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));        
     colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));   
     colRoles.setCellValueFactory(new PropertyValueFactory<>("roles"));       

        
     tvUsers.setItems(list);
     tvUsers.setRowFactory(tv -> new TableRow<User>() {
    @Override
    protected void updateItem(User item, boolean empty) {
        super.updateItem(item, empty);
        
    }
});
      
    FilteredList<User> filteredData = new FilteredList<>(list, b -> true);
		
		tfSearchUsers.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(Staff -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (Staff.getUsername().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} else if (Staff.getPrenom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				} else if (String.valueOf(Staff.getEmail()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
				     else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<User> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tvUsers.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tvUsers.setItems(sortedData);
}   
    
    
}
