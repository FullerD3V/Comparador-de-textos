/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparadortextos.AdminWindow;

import comparadortextos.Comparador_Textos;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author alumno
 */
public class AdminController implements Initializable {

    @FXML
    private Button btnAdmin, btnUser;
        
    @FXML
    private Pane pane;

    @FXML
    private TilePane tpane;

    @FXML
    private TextField txt1, txt2;

    @FXML
    private Label user, admin, error;

    @FXML
    private Button btn;

    List<BorderPane> lista = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @FXML
    public void btnUserOnAction() throws IOException {
        Stage stage = (Stage) btnAdmin.getScene().getWindow();
        Parent root = FXMLLoader.load(Comparador_Textos.class.getResource("Main/MainFXML.fxml"));
        Stage newStage = new Stage();
        Scene scene = new Scene(root);
        newStage.setTitle("User");
        newStage.setScene(scene);
        newStage.show();
        stage.close();
    }

    @FXML
    public void btnAdminOnAction() throws IOException {

    }

    @FXML
    public void btnOnAction() {
        String s = txt1.getText();
        String x = txt2.getText();
        int n;
        String p;
        if (s.equals("admin") || x.equals("pass")) {
            tpane.getChildren().clear();

            File f = new File("FILES");
            File[] files = f.listFiles();
            for (int i = 0; i < files.length; i++) {
                //porcentaje
                n = (int) (Math.random() * 100);
                p = n + "%";
                Label l = new Label(p);

                l.setFont(new Font(30));

                if (n > 60) {
                    l.setTextFill(Color.color(1, 0, 0));
                }
                //nombre del archivo
                Text fileName = new Text();
                fileName.setText(files[i].getName());
                Label m = new Label();
                m.setText(fileName.getText());
                m.setFont(new Font(20));
                //introducir elementos en el nombre en un borderpane
                BorderPane borderpane = new BorderPane();
                borderpane.setCenter(l);
                BorderPane.setAlignment(l, Pos.CENTER);
                borderpane.setBottom(m);
                BorderPane.setAlignment(m, Pos.CENTER);
                borderpane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override //Eventos al ahcer click sobre el elemento de la lista 
                    public void handle(MouseEvent event) {
                        if (event.getClickCount() == 2) {
                            final Stage dialog = new Stage();
                            dialog.initModality(Modality.APPLICATION_MODAL);
                            VBox dialogVbox = new VBox(20);
                            dialogVbox.getChildren().add(new Text("This is a Dialog"));
                            Scene dialogScene = new Scene(dialogVbox, 300, 200);
                            dialog.setScene(dialogScene);
                            dialog.show();
                            
                        }
                    }                
                });
                //insertar el borderpane en una lista
                lista.add(borderpane);
            }
            tpane.getChildren().addAll(lista);
        } else {
            error.setText("Usuario o contraseña incorrectos");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tpane.setVgap(
                100);
        tpane.setHgap(
                100);
    }

}
