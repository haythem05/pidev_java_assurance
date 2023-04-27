package tn.esprit.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.stream.Collectors;
import com.twilio.type.PhoneNumber;




import tn.esprit.entities.Reclamation;
import tn.esprit.entities.Reponse;
import tn.esprit.tools.MaConnexion;

public class ReclamationService implements NewInterface<Reclamation> {
    Connection cnx;
    String sql = "";

    public ReclamationService() {
        cnx = MaConnexion.getInstance().getCnx();
    }

@Override
public void ajouter(Reclamation r) {
    // Générer la référence aléatoire
    String reference = Reclamation.genererReference();
    // Ajouter la réclamation avec la référence générée, la date de création et le statut "En cours"
    sql = "insert into reclamation(reference, nom_d, prenom_d, cin, email, commentaire, tel, created_at, statut, file) values (?,?,?,?,?,?,?,?,?,?)";
    try {
        PreparedStatement ste = cnx.prepareStatement(sql);
        ste.setString(1, reference);
        ste.setString(2, r.getNom_d());
        ste.setString(3, r.getPrenom_d());
        ste.setInt(4, r.getCin());
        ste.setString(5, r.getEmail());
        ste.setString(6, r.getCommentaire());
        ste.setString(7, r.getTel());
        ste.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
        ste.setString(9, "En cours");
        ste.setString(10, r.getFile());
        ste.executeUpdate();
        // Définir la référence générée pour la nouvelle réclamation
        r.setReference(reference);
        System.out.println("Réclamation ajoutée !");
        
        // Envoyer un SMS avec Twilio
        String ACCOUNT_SID = "AC8d0ef4234781bddf96867d3ec05586cb";
        String AUTH_TOKEN = "4f05938ff4b3f5376ec2276918e0d119";
        String TWILIO_NUMBER = "+21652983903";
        String message = "Votre réclamation a été ajoutée avec la référence : " + reference;
        PhoneNumber toNumber = new PhoneNumber(r.getTel());
        PhoneNumber fromNumber = new PhoneNumber(TWILIO_NUMBER);

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message twilioMessage = Message.creator(toNumber, fromNumber, message).create();
        System.out.println("SMS envoyé avec succès !");
        
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}





public Reclamation recup(int id){
        Reclamation A = new Reclamation();
        
          try {
            Statement st = cnx.createStatement();
            String query = "select * FROM reclamation WHERE id='" + id + "'";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {   
             
             
                             
                A.setId(rs.getInt(1));

                  A.setReference(rs.getString(2));
        A.setNom_d(rs.getString(3));
        A.setPrenom_d(rs.getString(4));
        A.setCin(rs.getInt(5));
        A.setEmail(rs.getString(6));
        A.setCommentaire(rs.getString(7));
        A.setFile(rs.getString(10));
        A.setTel(rs.getString(11));
      
                
                
                
                System.out.println("!!!");
               
              
                /*ch.setId(("id"));
                ch.setTitre(rst.getString(2));
                ch.setType(rst.getString(3));
                ch.setDescription(rst.getString(4));
                ch.setImg(rst.getString(5));
                ch.setDate_debut(rst.getDate(6));
                ch.setDate_fin(rst.getDate(7));
                ch.setNb_participants(rst.getInt(8));
                ch.setEtat(rst.getString(9));
                ch.setNiveau(rst.getInt(10));*/
            }
            
        } catch (SQLException ex) {
            System.out.println("erreur get IdOBJ pour suivi");
            System.out.println(ex);
        }
        
            return A;
        
    }



   @Override
public List<Reclamation> afficher() {
    List<Reclamation> reclamations = new ArrayList<>();
    sql = "SELECT r.*, re.note FROM reclamation r LEFT JOIN reponse re ON r.id = re.reclamation_id";
    try {
        Statement ste = cnx.createStatement();
        ResultSet rs = ste.executeQuery(sql);
        while (rs.next()) {
            Reclamation r = new Reclamation(
                    rs.getInt("id"),
                    rs.getString("reference"),
                    rs.getString("nom_d"),
                    rs.getString("prenom_d"),
                    rs.getInt("cin"),
                    rs.getString("email"),
                    rs.getString("commentaire"),
                    rs.getDate("created_at"),
                    rs.getString("statut"),
                    rs.getString("file"),
                    rs.getString("tel")
            );
            // Ajouter la note de la réponse si elle existe
            if (rs.getString("note") != null) {
                Reponse rep = new Reponse();
                rep.setNote(rs.getString("note"));
                r.setReponse(rep);
            }
            reclamations.add(r);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return reclamations;
}



@Override
public void supprimer(int id) {
    try {
        String req = "DELETE FROM `reclamation` WHERE id = ?";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, id);
        st.executeUpdate();
        System.out.println("Réclamation supprimée !");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
public void modifier(int id, Reclamation nouvelleReclamation) {
    try {
        String req = "UPDATE reclamation SET nom_d = ?, prenom_d = ?, cin = ?, email = ?, commentaire = ?, statut = ?, file = ?, tel = ? WHERE id = ?";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setString(1, nouvelleReclamation.getNom_d());
        st.setString(2, nouvelleReclamation.getPrenom_d());
        st.setInt(3, nouvelleReclamation.getCin());
        st.setString(4, nouvelleReclamation.getEmail());
        st.setString(5, nouvelleReclamation.getCommentaire());
        st.setString(6, nouvelleReclamation.getStatut());
        st.setString(7, nouvelleReclamation.getFile());
        st.setString(8, nouvelleReclamation.getTel());
      
        st.setInt(9, id);
        st.executeUpdate();
        System.out.println("Réclamation modifiée avec succès.");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
 public List<Reclamation> triParDate(){
        return afficher().stream()
                .sorted(Comparator.comparing((reclamation) -> reclamation.getCreated_at()))
                .collect(Collectors.toList());
    }

    

}

