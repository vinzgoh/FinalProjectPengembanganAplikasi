/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import DB.Coffee;
import DB.CoffeeDao;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class FormInputController implements Initializable {

    private Stage stage;
    @FXML
    private TextField txtkodepbl;
    @FXML
    private TextField txtnamapbl;
    @FXML
    private TextField txtnamabrg;
    @FXML
    private TextField txthargabrg;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnView;
    @FXML
    private AnchorPane Pane;
    @FXML
    private Label lblJudul;

    private boolean editMode = false;
    private int editIndex = -1;
    private Coffee coffee = null;

    private void setup() throws IOException {
        stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("FormInput.fxml"));
        loader.setController(this);

        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            txtkodepbl.requestFocus();
        });

    }

    public void onBtnSimpanClick(ActionEvent event) {
        String kodepbl = "";
        try {
            kodepbl = getKodepblFromInput();
        } catch (IllegalArgumentException e) {
            messageBox("Contoh Data", e.getMessage()).showAndWait();
            txtkodepbl.requestFocus();
        }
        String namapbl = "";
        try {
            namapbl = getNamapblFromInput();
        } catch (IllegalArgumentException e) {
            messageBox("Contoh Data", e.getMessage()).showAndWait();
            txtkodepbl.requestFocus();
        }
        String namabrg = "";
        try {
            namabrg = getNamabrgFromInput();
        } catch (IllegalArgumentException e) {
            messageBox("Contoh Data", e.getMessage()).showAndWait();
            txtkodepbl.requestFocus();
        }
        String hargabrg = "";
        try {
            hargabrg = getHargabrgFromInput();
        } catch (IllegalArgumentException e) {
            messageBox("Contoh Data", e.getMessage()).showAndWait();
            txtkodepbl.requestFocus();
        }

        txtkodepbl.clear();
        txtnamapbl.clear();
        txtnamabrg.clear();
        txthargabrg.clear();
        this.coffee = new Coffee(txtnamapbl.getText(), txtnamabrg.getText(), txtkodepbl.getText(), txthargabrg.getText());
        txtkodepbl.requestFocus();
    }

    private String getKodepblFromInput() throws IllegalArgumentException {
        String kodepbl = txtkodepbl.getText().trim();
        if (kodepbl.equals("")) {
            throw new IllegalArgumentException("Kode pembelian tidak boleh kosong");
        }
        return kodepbl;
    }

    private String getNamapblFromInput() throws IllegalArgumentException {
        String namapbl = txtnamapbl.getText().trim();
        if (namapbl.equals("")) {
            throw new IllegalArgumentException("Nama pembeli tidak boleh kosong");
        }
        return namapbl;
    }

    private String getNamabrgFromInput() throws IllegalArgumentException {
        String namabrg = txtnamabrg.getText().trim();
        if (namabrg.equals("")) {
            throw new IllegalArgumentException("Nama produk boleh kosong");
        }
        return namabrg;
    }

    private String getHargabrgFromInput() throws IllegalArgumentException {
        String hargabrg = txthargabrg.getText().trim();
        if (hargabrg.equals("")) {
            throw new IllegalArgumentException("Harga produk boleh kosong");
        }
        return hargabrg;

    }

    private void onKeyEscapePressed(KeyEvent event) {
        // cancel edit jika user menekan tombol esc dan aplikasi dalam mode editMode
        if (event.getCode().equals(KeyCode.ESCAPE) && editMode) {
            cancelEdit();
        }
    }

    private void cancelEdit() {
        if (editMode) {
            disableEditMode();
            txtkodepbl.clear();
            txtnamapbl.clear();
            txtnamabrg.clear();
            txthargabrg.clear();
            txtkodepbl.requestFocus();
            messageBox("Edit", "Edit canceled");
        }
    }

    private void enableEditMode(int index) {
        this.editMode = true;
        this.editIndex = index;
    }

    /**
     * mengnonaktifkan mode edit
     */
    private void disableEditMode() {
        this.editMode = false;
        this.editIndex = -1;
    }

    public void onBtnCancelClick(ActionEvent event) {
        txtkodepbl.clear();
        txtnamapbl.clear();
        txtnamabrg.clear();
        txthargabrg.clear();
        txtkodepbl.requestFocus();
        messageBox("Message", "Canceled");
    }

    public void onBtnView(ActionEvent event) throws Exception {
        table();
    }

    public void table() throws SQLException {
        Connection conn = connectAndCreateTable("jdbc:sqlite:Coffee.sqlite");
        CoffeeDao dao = new CoffeeDao(conn);
        try {

            Stage tabelStage = new Stage();
            FXMLLoader Loader = new FXMLLoader();
            Pane root = (Pane)Loader.load(Paths.get("src/GUI/FormTable.fxml").toUri().toURL());
            Initializable controller = new FormTableController(dao, stage);
            // set controller gui
            Loader.setController(controller);
            Scene scene = new Scene(root);
            tabelStage.setScene(scene);
            tabelStage.setTitle("Table DataBase Transaksi");
            tabelStage.setResizable(true);
            tabelStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection connectAndCreateTable(String url) throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement();
        String sql = "create table if not exists Coffee ("
                + "kodepbl varchar(11) PRIMARY KEY, "
                + "namapbl varchar(255) not null,"
                + "namabrg varchar(255) not null,"
                + "hargabrg varchar(255) not null"
                + ")";
        stmt.execute(sql);

        ResultSet resultSet = stmt.executeQuery("select * from Coffee");

        // jika belum ada data tambahkan data contoh
        if (!resultSet.next()) {
            stmt.execute("insert into Coffee values (\"014201800001\", \"Budi\", \"Java Chips\", \"24000\")");
            stmt.execute("insert into Coffee values (\"084201800002\", \"Susi\", \"Coffee Latte\", \"30000\")");
            stmt.execute("insert into Coffee values (\"084201800003\", \"Daniel\", \"Exspresso\", \"34000\")");
            stmt.execute("insert into Coffee values (\"114201800004\", \"Vincent\", \"Caramel Latte\", \"32000\")");
            stmt.execute("insert into Coffee values (\"134201800005\", \"Susi\", \"Coffee Latte\", \"30000\")");
        }
        return conn;
    }

    public Optional<Coffee> showAndWait(Stage stage) throws IOException {
        this.setup();

        // stage sebagai window induk
        this.stage.initOwner(stage);
        // set window modal (tidak bisa berinteraksi dengan form lain sebelum form ini ditutup)
        this.stage.initModality(Modality.APPLICATION_MODAL);
        // tampilkan
        this.stage.showAndWait();

        // kembalikan optional mahasiswa, jika ditutup melalui tombol btnSimpanMhs,
        // atribut mahasiswa dari form ini seharusnya sudah diisikan dengan object mahasiswa
        // apabila belum hasil dari form ini adalah empty
        return Optional.ofNullable(this.coffee);
    }

    private Alert messageBox(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert;
    }
}
