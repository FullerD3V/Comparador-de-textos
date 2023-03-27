/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparadortextos.Main;

import comparadortextos.Comparador_Textos;
import comparadortextos.cosas.cosasPlus;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 *
 * @author alumno
 */
public class MainController implements Initializable {

    @FXML
    private Button reset, btnAdmin, btnUser, check;

    @FXML
    private TextArea txtArea1, txtArea2;

    @FXML
    private Label resultado;

    @FXML
    private ComboBox<String> archivos1, archivos2;
    
    @FXML 
    private String texto1, texto2;

    @FXML
    public void resetOnAction() {
        txtArea1.clear();
        txtArea2.clear();
        resultado.setText("0%");
    }

    @FXML
    public void checkOnAction() {
        String s = txtArea1.getText();
        String t = txtArea2.getText();
        resultado.setText(String.format("%.3f ", similarity(s, t) * 100, s, t) + "%");
    }

    @FXML
    public void archivos1OnAction() {
        texto1 = "";
        //Mostrar contenido del archivo        
        txtArea1.clear();
        try {
            Scanner input = new Scanner(new File("FILES/" + archivos1.getValue()));
            while (input.hasNextLine()) {
                String line = input.nextLine();
                texto1 += line;
                texto1 += "\n";
            }
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        txtArea1.setText(texto1);
    }

    @FXML
    public void archivos2OnAction() {
        texto2 = "";
        try {
            Scanner input2 = new Scanner(new File("FILES/" + archivos2.getValue()));
            while (input2.hasNextLine()) {
                String line2 = input2.nextLine();
                texto2 += line2;
                texto2 += "\n";
            }
            input2.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        txtArea2.setText(texto2);
    }

    @FXML
    public void btnUserOnAction() {

    }

    @FXML
    public void btnAdminOnAction() throws IOException {
        Stage stage = (Stage) btnAdmin.getScene().getWindow();
        Parent root = FXMLLoader.load(Comparador_Textos.class.getResource("AdminWindow/AdminFXML.fxml"));
        Stage newStage = new Stage();
        Scene scene = new Scene(root);
        newStage.setTitle("Admin");
        newStage.setScene(scene);
        newStage.show();
        stage.close();
    }
    
    public static double similarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) {
            longer = s2;
            shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) {
            return 1.0;
            /* both strings have zero length */
        }
        return (longerLength - getLevenshteinDistance(longer, shorter)) / (double) longerLength;
    }

    /**
     * Créditos
     * https://commons.apache.org/proper/commons-lang/javadocs/api-2.5/src-html/org/apache/commons/lang/StringUtils.html#line.6162
     */
    public static int getLevenshteinDistance(String s, String t) {
        if (s == null || t == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }

        int n = s.length(); // length of s
        int m = t.length(); // length of t

        if (n == 0) {
            return m;
        } else if (m == 0) {
            return n;
        }

        if (n > m) {
            // swap the input strings to consume less memory
            String tmp = s;
            s = t;
            t = tmp;
            n = m;
            m = t.length();
        }

        int p[] = new int[n + 1]; //'previous' cost array, horizontally
        int d[] = new int[n + 1]; // cost array, horizontally
        int _d[]; //placeholder to assist in swapping p and d

        // indexes into strings s and t
        int i; // iterates through s
        int j; // iterates through t

        char t_j; // jth character of t

        int cost; // cost

        for (i = 0; i <= n; i++) {
            p[i] = i;
        }

        for (j = 1; j <= m; j++) {
            t_j = t.charAt(j - 1);
            d[0] = j;

            for (i = 1; i <= n; i++) {
                cost = s.charAt(i - 1) == t_j ? 0 : 1;
                // minimum of cell to the left+1, to the top+1, diagonally left and up +cost
                d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
            }

            // copy current distance counts to 'previous row' distance counts
            _d = p;
            p = d;
            d = _d;
        }

        // our last action in the above loop was to switch d and p, so p now
        // actually has the most recent cost counts
        return p[n];        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        // TODO
        File f = new File("FILES");
        File[] files = f.listFiles();
        for (int i = 0; i < files.length; i++) {
            archivos1.getItems().add(files[i].getName());
            archivos2.getItems().add(files[i].getName());

        }   
    }
}
