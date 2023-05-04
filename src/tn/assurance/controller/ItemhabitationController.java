/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.assurance.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import tn.assurance.models.Categorie;
import tn.assurance.models.Habitation;
import tn.assurance.services.habitationS;

/**
 * FXML Controller class
 *
 * @author haythem
 */
public class ItemhabitationController implements Initializable {

    @FXML
    private Label npilabel;
    @FXML
    private Label devislabel;
    private ItemhabitationController.MyListener myListener;
    private Habitation h;
    private int id;
    static Habitation s = new Habitation();
    static public String nomCat,nbp,ci,cm,d;

    /**
     * Initializes the controller class.
     */
    public void setData(int id, int idclient, int nbpieceimmobilier, float capitalimmobilier, float capitalmobilier, float devis, Categorie type_Id, MyListener myListener) {
        this.id = id;
        this.myListener = myListener;
        npilabel.setText(String.valueOf(nbpieceimmobilier));
        devislabel.setText(String.valueOf(devis));
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void onClick() {
        myListener.onClick(h);
    }
    
    public void setMyListener(ItemhabitationController.MyListener myListener) {
        this.myListener = myListener;
    }
    
    @FXML
    private void Click(MouseEvent event) {
        habitationS hs = new habitationS();
        List<Habitation> L = new ArrayList<>();
        myListener.onClick(h);
        L = hs.rechHab(id);
        System.out.println("l id =" + id);
        System.out.println("L =" + L);
        s.setId(L.get(0).getId());
        s.setIdclient(L.get(0).getIdclient());
        s.setNbpieceimmobilier(L.get(0).getNbpieceimmobilier());
        s.setCapitalmobilier(L.get(0).getCapitalmobilier());
        s.setCapitalimmobilier(L.get(0).getCapitalimmobilier());
        s.setDevis(L.get(0).getDevis());
        s.setType_Id(L.get(0).getType_Id());
        nomCat=s.getType_Id().getNom();
        //nbp=s.getNbplace();
        nbp=(String.valueOf(s.getNbpieceimmobilier()));
        ci=(String.valueOf(s.getCapitalimmobilier()));
        cm=(String.valueOf(s.getCapitalmobilier()));
        d=(String.valueOf(s.getDevis()));
    }
    
    public interface MyListener {

        void onClick(Habitation ha);
    }
    
}
