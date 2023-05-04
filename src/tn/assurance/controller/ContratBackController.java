/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.assurance.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import java.sql.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.assurance.models.Contrat;
import tn.assurance.services.contratS;

/**
 * FXML Controller class
 *
 * @author haythem
 */
public class ContratBackController implements Initializable {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<Contrat> table;
    @FXML
    private TableColumn<Contrat, Date> datedcol;
    @FXML
    private TableColumn<Contrat, Date> datefcol;
    @FXML
    private TableColumn<Contrat, String> avantagecol;
    @FXML
    private TableColumn<Contrat, String> marquecol;
    @FXML
    private TableColumn<Contrat, String> modelecol;
    @FXML
    private TableColumn<Contrat, Integer> nbpcol;
    @FXML
    private TableColumn<Contrat, Date> dateccol;
    @FXML
    private TableColumn<Contrat, Float> vcatacol;
    @FXML
    private TableColumn<Contrat, Float> prixcol;
    @FXML
    private Button btQR;
    @FXML
    private TextField minPrixField;
    @FXML
    private TextField maxPrixField;
    private contratS cs;
    private Contrat s;
    @FXML
    private ImageView imageqr;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cs = new contratS();
        List<Contrat> liste = cs.afficherContrat();

        datedcol.setCellValueFactory(new PropertyValueFactory<>("datedebut"));
        datefcol.setCellValueFactory(new PropertyValueFactory<>("datefin"));
        avantagecol.setCellValueFactory(new PropertyValueFactory<>("avantages"));
        marquecol.setCellValueFactory(new PropertyValueFactory<>("marque"));
        modelecol.setCellValueFactory(new PropertyValueFactory<>("modele"));
        nbpcol.setCellValueFactory(new PropertyValueFactory<>("nbplace"));
        dateccol.setCellValueFactory(new PropertyValueFactory<>("datecirculation"));
        vcatacol.setCellValueFactory(new PropertyValueFactory<>("valeurcatalogue"));
        prixcol.setCellValueFactory(new PropertyValueFactory<>("prix"));

        table.setItems(FXCollections.observableArrayList(liste));

        minPrixField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateListViewF();
        });
        maxPrixField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateListViewF();
        });

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateTableView(newValue);
        });
    }

    public void updateTableView() {
        List<Contrat> liste = cs.getAllContrats();
        table.getItems().setAll(liste);
    }

    public void updateTableView(String search) {
        List<Contrat> liste = cs.searchContrats(search);
        table.getItems().setAll(liste);
    }

    private void updateListViewF() {
        List<Contrat> contrats = cs.getAllContrats();
        float minPrice = 0;
        float maxPrice = Float.MAX_VALUE;

        // Check if minPrixField and maxPrixField have values, then update minPrice and maxPrice
        if (!minPrixField.getText().isEmpty()) {
            minPrice = Float.parseFloat(minPrixField.getText());
        }
        if (!maxPrixField.getText().isEmpty()) {
            maxPrice = Float.parseFloat(maxPrixField.getText());
        }

        // Filter the contracts based on price
        ObservableList<Contrat> filteredList = FXCollections.observableArrayList();
        for (Contrat contrat : contrats) {
            if (contrat.getPrix() >= minPrice && contrat.getPrix() <= maxPrice) {
                filteredList.add(contrat);
            }
        }
        table.setItems(filteredList);
    }

    @FXML
    private void QrCode(ActionEvent event) throws WriterException, IOException {
        Contrat selectedPiece = table.getSelectionModel().getSelectedItem();
        if (selectedPiece != null) {
            String qrText = "  fiche technique   " + selectedPiece.getMarque() + selectedPiece.getModele();
            createQR(qrText);
        } else {
            alert("Please select a row.");
        }
    }

    private void createQR(String qrText) throws WriterException, IOException {
        try {
            String path = System.getProperty("user.home") + File.separatorChar + "Desktop" + File.separatorChar + "qr_code.png";
            BitMatrix matrix = new MultiFormatWriter().encode(qrText, BarcodeFormat.QR_CODE, 200, 200);
            MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
            alert("QR Code Created");
            setQRImage(path);
            //hl.setVisible(false);
        } catch (IOException | WriterException e) {
            e.printStackTrace();
        }
    }

    private void setQRImage(String path) {
        try {
            FileInputStream stream = new FileInputStream(path);
            javafx.scene.image.Image image = new javafx.scene.image.Image(stream) {
            };
            imageqr.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void alert(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    private void generatePDF(ActionEvent event) throws BadElementException, DocumentException, IOException {
        // Get the selected contrat
        TableView<Contrat> tab = table;
        Contrat c = tab.getSelectionModel().getSelectedItem();

// Create the PDF document
        Document document = new Document();

// Set the location path for the generated PDF
        String locationPath = "C:/xampp/htdocs/PDFContrat/" + c.getId() + ".pdf";
        FileOutputStream outputStream = new FileOutputStream(locationPath);
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);

// Add content to the PDF document
        document.open();
        PdfContentByte cb = writer.getDirectContent();
        cb.rectangle(30, 30, 540, 800); // (36, 36) est la position du coin supérieur gauche du cadre, 540 est la largeur et 756 est la hauteur
        cb.stroke();

// Add logo to the first page
// Add the logo
        Image logo = Image.getInstance("C:/xampp/htdocs/logo.png");
        logo.scaleToFit(120, 120);
        logo.setAbsolutePosition(document.getPageSize().getWidth() - 120, document.getPageSize().getHeight() - 120);
        document.add(logo);

// Add content to the first page
        BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        Font titleFont = new Font(bf, 16, Font.BOLD);
        Font nameFont = new Font(bf, 12, Font.BOLD, BaseColor.RED);
        Font valueFont = new Font(bf, 12);

        Paragraph title = new Paragraph("Contrat details:", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph("Nombre de place: ", nameFont));
        document.add(new Paragraph(String.valueOf(c.getNbplace()), valueFont));
        document.add(new Paragraph("Valeur catalogue: ", nameFont));
        document.add(new Paragraph(String.valueOf(c.getValeurcatalogue()), valueFont));
        document.add(new Paragraph("Prix: ", nameFont));
        document.add(new Paragraph(String.valueOf(c.getPrix()), valueFont));
        document.add(new Paragraph("Date debut: ", nameFont));
        document.add(new Paragraph(c.getDatedebut().toString(), valueFont));
        document.add(new Paragraph("Date fin: ", nameFont));
        document.add(new Paragraph(c.getDatefin().toString(), valueFont));
        document.add(new Paragraph("Date circulation: ", nameFont));
        document.add(new Paragraph(c.getDatecirculation().toString(), valueFont));
        document.add(new Paragraph("Avantages: ", nameFont));
        document.add(new Paragraph(c.getAvantages(), valueFont));
        document.add(new Paragraph("Marque: ", nameFont));
        document.add(new Paragraph(c.getMarque(), valueFont));
        document.add(new Paragraph("Modele: ", nameFont));
        document.add(new Paragraph(c.getModele(), valueFont));

// Close the PDF document
        document.close();

// Show success message to user
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("PDF generated successfully at " + locationPath);
        alert.showAndWait();
    }

    public List<Contrat> getContratsByPrix(float minPrix, float maxPrix) {
        contratS cS = new contratS();
        List<Contrat> allContrats = cS.getAllContrats();
        List<Contrat> filteredContrats = new ArrayList<>();

        for (Contrat contrat : allContrats) {
            if (contrat.getPrix() >= minPrix && contrat.getPrix() <= maxPrix) {
                filteredContrats.add(contrat);
            }
        }

        return filteredContrats;
    }

    @FXML
    private void habitation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/assurance/gui/HabitationBack.fxml"));
        Parent root = loader.load();

        // Get the current stage and set the new scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void automobile(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/assurance/gui/ContratBack.fxml"));
        Parent root = loader.load();

        // Get the current stage and set the new scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
