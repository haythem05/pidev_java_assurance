/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import tn.esprit.entities.Type;
import tn.esprit.entities.Sinistre;
import tn.esprit.services.SinistreService;

/**
 * FXML Controller class
 *
 * @author HD
 */
public class ItemController implements Initializable {

    @FXML
    private Label type_sin;
    @FXML
    private Label date_sin;
    @FXML
    private Label lieu_sin;
    @FXML
    private ImageView img_sin;
    @FXML
    private Label statut_sin;
    
    private int id;
    private MyListener myListener;
    private Sinistre sin;
    private Type typ;
    static Sinistre s = new Sinistre();
    static public String nomt;
    static public String date;
    static public String lieu, degats, description, url_image;
    static public Timestamp date_heure;
    //static public int id;
    
    
    public void setData(int id, Timestamp date_heure, String lieu, String statut, String description, String degats, String file, Type type, MyListener myListener) {
        this.id = id;
        this.myListener = myListener;
        type_sin.setText(type.getNom());
        lieu_sin.setText(lieu);
        statut_sin.setText(statut);
        date_sin.setText(date_heure.toString());
        String fullurl = "C:\\Users\\HD\\Desktop\\Installations\\XAMPP\\htdocs\\imagePi\\" + file;

        try {
            img_sin.setImage(new Image(new FileInputStream(fullurl)));
        } catch (FileNotFoundException e) {
            System.err.println("Error loading image: " + e);
        }
    }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    public void onClick() {
        myListener.onClick(sin);
    }
    
    public void setMyListener(MyListener myListener) {
        this.myListener = myListener;
    }

    @FXML
    private void Click(MouseEvent event) {
        SinistreService sc = new SinistreService();
        List<Sinistre> L = new ArrayList<>();
        myListener.onClick(sin);
        L = sc.rechSin(id);
        s.setId(L.get(0).getId());
        s.setDate_heure(L.get(0).getDate_heure());
        s.setDegats(L.get(0).getDegats());
        s.setDescription(L.get(0).getDescription());
        s.setFile(L.get(0).getFile());
        s.setLieu(L.get(0).getLieu());
        s.setStatut(L.get(0).getStatut());
        s.setType(L.get(0).getType());
        nomt= s.getType().getNom();
        date=s.getDate_heure().toString();
        lieu=s.getLieu();
        degats=s.getDegats();
        description=s.getDescription();
        url_image=s.getFile();
    }
    
    public interface MyListener {

        void onClick(Sinistre sin);
    }
    
    
    
}
