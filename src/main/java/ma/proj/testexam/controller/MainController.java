package ma.proj.testexam.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private BorderPane mainPane;

    @FXML
    private void home(ActionEvent event) throws IOException {
        loadPage("home");
    }

    @FXML
    private void Patients(ActionEvent event) throws IOException {
        loadPage("patientSection");
    }

    @FXML
    private void Medecins(ActionEvent event) throws IOException {
        loadPage("medecinSection");
    }

    private void loadPage(String page) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(page + ".fxml"));
        mainPane.setCenter(root);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Main Controller Initialized");
    }
}
