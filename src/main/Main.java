package main;

import controller.AppointmentsController;
import helper.AppointmentsQuery;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.time.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        stage.setTitle("Login Screen");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        Locale.setDefault(new Locale("fr", "FR"));
        ResourceBundle rb = ResourceBundle.getBundle("main/language", Locale.getDefault());
//
//        if(Locale.getDefault().getLanguage().equals("fr")){
//
//        }
        JDBC.openConnection();

        AppointmentsQuery.select();
        JDBC.getConnection();
        launch(args);
        ZoneId.getAvailableZoneIds().stream();
        //DBConnection.closeConnection();
    }
}
