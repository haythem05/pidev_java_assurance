/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.entities;
import java.util.List;
import java.util.Date;
import java.util.Random;
/**
 *
 * @author MSI
 */
public class Reclamation {
    private Integer id;
private String reference;
private String nom_d;
private String prenom_d;
private Integer cin;
private String email;
private String commentaire;
private Date created_at;
private String statut;
private String file;
private String tel;
 Reponse rep;
 String note;

public Reclamation() {
}

public Reclamation(String nom_d, String prenom_d, Integer cin, String email, String commentaire, String tel) {
    this.nom_d = nom_d;
    this.prenom_d = prenom_d;
    this.cin = cin;
    this.email = email;
    this.commentaire = commentaire;
    this.tel = tel;
}

public Reclamation(Integer id, String reference, String nom_d, String prenom_d, Integer cin, String email, String commentaire, Date created_at, String statut, String file, String tel) {
    this.id = id;
    this.reference = reference;
    this.nom_d = nom_d;
    this.prenom_d = prenom_d;
    this.cin = cin;
    this.email = email;
    this.commentaire = commentaire;
    this.created_at = created_at;
    this.statut = statut;
    this.file = file;
    this.tel = tel;
}

    public Reclamation(Integer id, String reference, String nom_d, String prenom_d, Integer cin, String email, String commentaire, Date created_at, String statut, String file, String tel, String note) {
        this.id = id;
        this.reference = reference;
        this.nom_d = nom_d;
        this.prenom_d = prenom_d;
        this.cin = cin;
        this.email = email;
        this.commentaire = commentaire;
        this.created_at = created_at;
        this.statut = statut;
        this.file = file;
        this.tel = tel;
        this.note = note;
    }

    public Reclamation( String reference, String nom_d, String prenom_d, Integer cin, String email, String commentaire, String file, String tel) {
       
        this.reference = reference;
        this.nom_d = nom_d;
        this.prenom_d = prenom_d;
        this.cin = cin;
        this.email = email;
        this.commentaire = commentaire;
        this.file = file;
        this.tel = tel;
    }

    public Reclamation(String nom_d, String prenom_d, Integer cin, String email, String commentaire, Date created_at, String tel) {
        this.nom_d = nom_d;
        this.prenom_d = prenom_d;
        this.cin = cin;
        this.email = email;
        this.commentaire = commentaire;
        this.created_at = created_at;
        this.tel = tel;
    }
    


public Integer getId() {
    return id;
}

public void setId(Integer id) {
    this.id = id;
}

public String getReference() {
    return reference;
}

public void setReference(String reference) {
    this.reference = reference;
}

public String getNom_d() {
    return nom_d;
}

public void setNom_d(String nom_d) {
    this.nom_d = nom_d;
}

public String getPrenom_d() {
    return prenom_d;
}

public void setPrenom_d(String prenom_d) {
    this.prenom_d = prenom_d;
}

public Integer getCin() {
    return cin;
}

public void setCin(Integer cin) {
    this.cin = cin;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

public String getCommentaire() {
    return commentaire;
}

public void setCommentaire(String commentaire) {
    this.commentaire = commentaire;
}

public Date getCreated_at() {
    return created_at;
}

public void setCreated_at(Date created_at) {
    this.created_at = created_at;
}

public String getStatut() {
    return statut;
}

public void setStatut(String statut) {
    this.statut = statut;
}

public String getFile() {
    return file;
}

public void setFile(String file) {
    this.file = file;
}

public String getTel() {
    return tel;
}

public void setTel(String tel) {
    this.tel = tel;
}

public Reponse getReponse() {
        return rep;
    }

    public void setReponse(Reponse rep) {
        this.rep = rep;
    }

    @Override
    public String toString() {
        return   reference ;
    }


 
 // Méthode statique pour générer une référence aléatoire de 8 caractères
    public static String genererReference() {
        final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(index));
        }

        return builder.toString();
    }
}





