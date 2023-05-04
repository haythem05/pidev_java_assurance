/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.assurance.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import tn.assurance.models.Categorie;
import tn.assurance.models.Contrat;
import tn.assurance.services.contratS;

/**
 * FXML Controller class
 *
 * @author haythem
 */
public class ItemcontratController implements Initializable {

    @FXML
    private Label datedLabel;
    @FXML
    private Label datefLabel;
    @FXML
    private Label avantageLabel;
    private int id;
    private ItemcontratController.MyListener myListener;
    //private Contrat c;
    static Contrat s = new Contrat();
    //static public String nom, description, url_image;
    private Contrat cat;
    static public String dated, datef, datec;
    static public String nomCat;
    static public String nbp, vc, p;
    static public String url_image;
    /**
     * Initializes the controller class.
     */
    
    public void setData(int id, int idclient, int nbplace, float valeurcatalogue, Date datedebut, Date datefin, Date datecirculation, String avantages, String marque, String modele, Categorie type_Id, MyListener myListener) {
        this.id = id;
        this.myListener = myListener;
        datedLabel.setText(datedebut.toString());
        datefLabel.setText(datefin.toString());
        avantageLabel.setText(avantages);
    }
    
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
        contratS cs = new contratS();
        List<Contrat> L = new ArrayList<>();
        myListener.onClick(cat);
        L = cs.rechContrat(id);
        System.out.println("l id =" + id);
        System.out.println("L =" + L);
        s.setId(L.get(0).getId());
        s.setIdclient(L.get(0).getIdclient());
        s.setNbplace(L.get(0).getNbplace());
        s.setValeurcatalogue(L.get(0).getValeurcatalogue());
        s.setDatedebut(L.get(0).getDatedebut());
        s.setDatefin(L.get(0).getDatefin());
        s.setDatecirculation(L.get(0).getDatecirculation());
        s.setAvantages(L.get(0).getAvantages());
        s.setMarque(L.get(0).getMarque());
        s.setModele(L.get(0).getModele());
        s.setType_Id(L.get(0).getType_Id());
        s.setPrix(L.get(0).getPrix());
        dated=s.getDatedebut().toString();
        datef=s.getDatefin().toString();
        datec=s.getDatecirculation().toString();
        nomCat=s.getType_Id().getNom();
        //nbp=s.getNbplace();
        nbp=(String.valueOf(s.getNbplace()));
        vc=(String.valueOf(s.getValeurcatalogue()));
        p=(String.valueOf(s.getPrix()));
    }
    
    public interface MyListener {

        void onClick(Contrat c);
    }
}
