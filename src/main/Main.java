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

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        stage.setTitle("Login Screen");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
//        LocalDateTime businessLDT = LocalDateTime.of(LocalDate.now(), LocalTime.of(8,0));
//        ZoneId estZID = ZoneId.of("America/New_York");
//        ZonedDateTime estZDT = ZonedDateTime.of(businessLDT, estZID);
//        ZoneId localZID = ZoneId.systemDefault();
//        ZonedDateTime localZDT = ZonedDateTime.ofInstant(estZDT.toInstant(), localZID);
////        System.out.println(localZDT);
//        for(int i = localZDT.getHour(); i < localZDT.getHour() + 14; i++){
//            System.out.println(LocalTime.of(i, 0));
//        }

        JDBC.openConnection();

        AppointmentsQuery.select();
        JDBC.getConnection();
        launch(args);
        ZoneId.getAvailableZoneIds().stream();
        //DBConnection.closeConnection();
    }
}
