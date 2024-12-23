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
import ma.proj.testexam.model.Medecin;

import java.net.URL;
import java.util.ResourceBundle;

public class MedecinController implements Initializable {
    private final ICabinetMetier metier = new CabinetMetierImpl();

    @FXML private TextField searchField;
    @FXML private TableView<Medecin> medecinTable;
    @FXML private TableColumn<Medecin, Integer> idColumn;
    @FXML private TableColumn<Medecin, String> nomColumn;
    @FXML private TableColumn<Medecin, String> prenomColumn;
    @FXML private TableColumn<Medecin, String> emailColumn;
    @FXML private TableColumn<Medecin, String> telephoneColumn;
    @FXML private TableColumn<Medecin, Void> actionsColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTable();
        loadMedecins();
    }

    private void setupTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idMedecin"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        setupActionsColumn();
    }

    private void setupActionsColumn() {
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editBtn = new Button("Modifier");
            private final Button deleteBtn = new Button("Supprimer");

            {
                deleteBtn.setOnAction(event -> {
                    Medecin medecin = getTableView().getItems().get(getIndex());
                    if (medecin != null) {
                        handleDelete(medecin);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableView().getItems().get(getIndex()) == null) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(5, editBtn, deleteBtn);
                    setGraphic(buttons);
                }
            }
        });
    }

    private void loadMedecins() {
        medecinTable.setItems(FXCollections.observableArrayList(metier.afficherMedecins()));
        medecinTable.refresh();
    }

    @FXML
    private void handleAdd() {
        Dialog<Medecin> dialog = new Dialog<>();
        dialog.setTitle("Ajouter un médecin");
        dialog.setHeaderText("Saisir les informations du médecin");

        ButtonType saveButtonType = new ButtonType("Sauvegarder", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nom = new TextField();
        TextField prenom = new TextField();
        TextField telephone = new TextField();
        TextField email = new TextField();

        grid.add(new Label("Nom:"), 0, 0);
        grid.add(nom, 1, 0);
        grid.add(new Label("Prénom:"), 0, 1);
        grid.add(prenom, 1, 1);
        grid.add(new Label("Téléphone:"), 0, 2);
        grid.add(telephone, 1, 2);
        grid.add(new Label("Email:"), 0, 3);
        grid.add(email, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                Medecin medecin = new Medecin();
                medecin.setNom(nom.getText());
                medecin.setPrenom(prenom.getText());
                medecin.setTel(telephone.getText());
                medecin.setEmail(email.getText());
                return medecin;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(medecin -> {
            metier.ajouterMedecin(medecin);
            loadMedecins();
        });
    }

    @FXML
    private void handleDelete(Medecin medecin) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Supprimer médecin");
        alert.setContentText("Êtes-vous sûr ?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                metier.supprimerMedecin(medecin.getIdMedecin());
                loadMedecins();
            }
        });
    }
}
