/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;
import javafx.scene.Parent;

public class MainGUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // inisialisasi database

        Parent root = FXMLLoader.load(getClass().getResource("FormInput.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Input Data Transaksi");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
