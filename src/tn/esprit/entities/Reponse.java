/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.entities;

import java.util.Date;


public class Reponse {
    private int id;
    private String id_user;
    private String note;
    private Date created_at;
    private Reclamation reclamation_id;
    
    public Reponse() {
    }

    public Reponse(String id_user, String note) {
        this.id_user = id_user;
        this.note = note;
    }

    public Reponse(String id_user, String note, Reclamation reclamation_id) {
        this.id_user = id_user;
        this.note = note;
        this.reclamation_id = reclamation_id;
    }

    public Reponse(int id, String id_user, String note, Date created_at, Reclamation reclamation_id) {
        this.id = id;
        this.id_user = id_user;
        this.note = note;
        this.created_at = created_at;
        this.reclamation_id = reclamation_id;
    }
    
    public Reclamation getReclamation() {
        return reclamation_id;
    }

    public void setReclamation(Reclamation reclamation_id) {
        this.reclamation_id = reclamation_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Reponse{" + "id=" + id + ", id_user=" + id_user + ", note=" + note + ", created_at=" + created_at + ", reclamation_id=" + reclamation_id + '}';
    }
}



    

