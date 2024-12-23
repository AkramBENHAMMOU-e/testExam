package ma.proj.testexam.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import ma.proj.testexam.dao.CabinetMetierImpl;
import ma.proj.testexam.dao.ICabinetMetier;
import ma.proj.testexam.model.Patient;

import java.net.URL;
import java.util.ResourceBundle;

public class PatientController implements Initializable {
    private final ICabinetMetier metier = new CabinetMetierImpl();

    @FXML private TextField searchField;
    @FXML private TableView<Patient> patientTable;
    @FXML private TableColumn<Patient, Integer> idColumn;
    @FXML private TableColumn<Patient, String> nomColumn;
    @FXML private TableColumn<Patient, String> prenomColumn;
    @FXML private TableColumn<Patient, String> cinColumn;
    @FXML private TableColumn<Patient, String> telephoneColumn;
    @FXML private TableColumn<Patient, String> emailColumn;
    @FXML private TableColumn<Patient, String> dateNaissanceColumn;
    @FXML private TableColumn<Patient, Void> actionsColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTable();
        loadPatients();
    }

    private void setupTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idPatient"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        cinColumn.setCellValueFactory(new PropertyValueFactory<>("cin"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        dateNaissanceColumn.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));

        setupActionsColumn();
    }

    private void setupActionsColumn() {
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteBtn = new Button("Supprimer");

            {
                deleteBtn.setOnAction(event -> {
                    Patient patient = getTableView().getItems().get(getIndex());
                    if (patient != null) {
                        handleDelete(patient);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableView().getItems().get(getIndex()) == null) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(5, deleteBtn);
                    setGraphic(buttons);
                }
            }
        });
    }

    private void loadPatients() {
        patientTable.setItems(FXCollections.observableArrayList(metier.afficherPatients()));
        patientTable.refresh();
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText();
        if (keyword != null && !keyword.isEmpty()) {
            patientTable.setItems(FXCollections.observableArrayList(metier.rechercherPatient(keyword)));
        } else {
            loadPatients();
        }
        patientTable.refresh();
    }

    @FXML
    private void handleAdd() {
        Dialog<Patient> dialog = new Dialog<>();
        dialog.setTitle("Ajouter un patient");
        dialog.setHeaderText("Saisir les informations du patient");

        ButtonType saveButtonType = new ButtonType("Sauvegarder", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nom = new TextField();
        TextField prenom = new TextField();
        TextField cin = new TextField();
        TextField telephone = new TextField();
        TextField email = new TextField();
        DatePicker dateNaissance = new DatePicker();

        grid.add(new Label("Nom:"), 0, 0);
        grid.add(nom, 1, 0);
        grid.add(new Label("Prénom:"), 0, 1);
        grid.add(prenom, 1, 1);
        grid.add(new Label("CIN:"), 0, 2);
        grid.add(cin, 1, 2);
        grid.add(new Label("Téléphone:"), 0, 3);
        grid.add(telephone, 1, 3);
        grid.add(new Label("Email:"), 0, 4);
        grid.add(email, 1, 4);
        grid.add(new Label("Date de naissance:"), 0, 5);
        grid.add(dateNaissance, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                if (nom.getText().isEmpty() || prenom.getText().isEmpty() || cin.getText().isEmpty()) {
                    showAlert("Erreur", "Tous les champs obligatoires doivent être remplis", Alert.AlertType.ERROR);
                    return null;
                }
                Patient patient = new Patient();
                patient.setNom(nom.getText());
                patient.setPrenom(prenom.getText());
                patient.setCin(cin.getText());
                patient.setTelephone(telephone.getText());
                patient.setEmail(email.getText());
                if (dateNaissance.getValue() != null) {
                    patient.setDateNaissance(java.sql.Date.valueOf(dateNaissance.getValue()));
                }
                return patient;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(patient -> {
            metier.ajouterPatient(patient);
            loadPatients();
        });
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleDelete(Patient patient) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Supprimer le patient");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer ce patient ?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                metier.supprimerPatient(patient.getIdPatient());
                loadPatients();
            }
        });
    }
}
