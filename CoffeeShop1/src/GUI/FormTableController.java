/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import DB.Coffee;
import DB.CoffeeDao;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class FormTableController implements Initializable {

    @FXML
    private TableView<Coffee> tablecoffee;
    @FXML
    private TableColumn<Coffee, String> columnkodepbl;
    @FXML
    private TableColumn<Coffee, String> columnnamapbl;
    @FXML
    private TableColumn<Coffee, String> columnnamabrg;
    @FXML
    private TableColumn<Coffee, String> columnhargabrg;
    @FXML
    private TextField txtfilter;
    @FXML
    private Button btndelete;
    @FXML
    private Button btnfilter;

    @FXML
    private Button btnInput;

    private CoffeeDao dao;
    private ListProperty<Coffee> coffee;

    String filter;
    // primary stage, untuk dikirim ke form input sebagai parent window
    private Stage stage;

    public FormTableController(CoffeeDao dao, Stage stage) {
        this.dao = dao;
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // load semua data mahasiswa
        try {
            coffee.addAll(dao.all());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        coffee = new SimpleListProperty<>(FXCollections.observableArrayList());
        tablecoffee.itemsProperty().bind(coffee);
        columnkodepbl.setCellValueFactory(new PropertyValueFactory<>("kodepbl"));
        columnnamapbl.setCellValueFactory(new PropertyValueFactory<>("namapbl"));
        columnnamabrg.setCellValueFactory(new PropertyValueFactory<>("namabrg"));
        columnhargabrg.setCellValueFactory(new PropertyValueFactory<>("hargabrg"));

        btnfilter.setOnAction(event -> {
            filter = txtfilter.getText();
            coffee.clear();
            try {
                // cari by nim
                Integer.parseInt(filter);
                runSQL(() -> coffee.addAll(dao.findBykodepbl(filter)));
            } catch (NumberFormatException e) {
                runSQL(() -> coffee.addAll(dao.findBynamapbl(filter)));
            }
        });

        tablecoffee.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DELETE) {
                runSQL(() -> dao.delete(tablecoffee.getSelectionModel().getSelectedItem()));
                coffee.clear();

            }
        });

        btndelete.setOnAction(event -> {
            // buat dialog
            Coffee m = inputDataCoffee(new Coffee("", "", "", ""), false).orElse(null);

        });

        btnInput.setOnAction(event -> {
            this.stage.close();
        });

        tablecoffee.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Coffee m = inputDataCoffee(tablecoffee.getSelectionModel().getSelectedItem(), true).orElse(null);

            }
        });

    }

    private Optional<Coffee> inputDataCoffee(Coffee coffee, boolean edit) {
        Dialog<Coffee> dialog = new Dialog<>();
        dialog.setTitle("Input Data Coffee");

        // Set the button types.
        ButtonType okButtonType = new ButtonType("Simpan", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField kodepbl = new TextField();
        kodepbl.setPromptText("kodepbl");
        kodepbl.setText(coffee.getKodepbl());
        if (edit) {
            kodepbl.setDisable(true);
        }
        TextField namapbl = new TextField();
        namapbl.setPromptText("namapbl");
        namapbl.setText(coffee.getNamapbl());
        TextField namabrg = new TextField();
        namabrg.setPromptText("namabrg");
        namabrg.setText(coffee.getNamabrg());
        TextField hargabrg = new TextField();
        hargabrg.setPromptText("hargabrg");
        hargabrg.setText(coffee.getHargabrg());

        grid.add(new Label("Kode Pembelian:"), 0, 0);
        grid.add(kodepbl, 1, 0);
        grid.add(new Label("Nama Pembeli:"), 0, 1);
        grid.add(namapbl, 1, 1);
        grid.add(new Label("Nama Barang:"), 0, 1);
        grid.add(namabrg, 1, 1);
        grid.add(new Label("Harga Barang:"), 0, 1);
        grid.add(hargabrg, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(kodepbl::requestFocus);

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new Coffee(kodepbl.getText(), namapbl.getText(), namabrg.getText(), hargabrg.getText());
            }
            return null;
        });

        Optional<Coffee> result = dialog.showAndWait();
        return result;
    }

    private void runSQL(SQLAction action) {
        try {
            action.run();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}

@FunctionalInterface
interface SQLAction {

    void run() throws SQLException;
}
