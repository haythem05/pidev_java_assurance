/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.assurance.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import static java.lang.Math.sin;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import tn.assurance.models.Categorie;
import tn.assurance.services.categorieS;

/**
 * FXML Controller class
 *
 * @author haythem
 */
public class ItemcategorieController implements Initializable {

    @FXML
    private Label nomC;
    @FXML
    private ImageView img_sin;
    private int id;
    private MyListener myListener;
    private Categorie cat;
    static Categorie s = new Categorie();
    static public String nom, description, url_image;

    public void setData(int id, String nom, String description, String categorieimage, MyListener myListener) {
        this.id = id;
        this.myListener = myListener;
        nomC.setText(nom);

        String fullurl = "C:\\xampp\\htdocs\\PiDev44\\public\\uploads\\images\\categorie\\" + categorieimage;

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
        myListener.onClick(cat);
    }

    public void setMyListener(MyListener myListener) {
        this.myListener = myListener;
    }

    @FXML
    private void Click(MouseEvent event) {
        categorieS sc = new categorieS();
        List<Categorie> L = new ArrayList<>();
        myListener.onClick(cat);
        L = sc.rechCat(id);
        System.out.println("l id =" + id);
        System.out.println("L =" + L);
        s.setId(L.get(0).getId());
        s.setNom(L.get(0).getNom());
        s.setDescription(L.get(0).getDescription());
        s.setCategorieimage(L.get(0).getCategorieimage());
        nom=s.getNom();
        description=s.getDescription();
        url_image=s.getCategorieimage();
        
    }

    public interface MyListener {

        void onClick(Categorie cat);
    }

}
