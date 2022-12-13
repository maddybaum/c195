package controller;

import Model.Appointments;
import Model.User;
import helper.AppointmentsQuery;
import helper.UserQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.util.ResourceBundle;
import java.io.*;

//todo contact not customer, not my schedule - today's appts
//number of appts by country or division
public class ReportsController implements Initializable {
    public ComboBox reportCombo;
    public Button generateReportBtn;
    public ComboBox<String> totalByType;
    public static ComboBox<String> totalByMonth;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> months = FXCollections.observableArrayList();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");

        totalByMonth.setItems(months);

    }


    public static int getTotalByMonthType() {
        Month month = Month.valueOf(totalByMonth.getSelectionModel().getSelectedItem());
        System.out.println(month);
        return 0;
    }

    public void getTotalByMonthType(ActionEvent actionEvent) {
        String month = totalByMonth.getSelectionModel().getSelectedItem();
        System.out.println(month);
    }
}

