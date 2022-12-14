package controller;

import Model.Appointments;
import Model.User;
import helper.AppointmentsQuery;
import helper.ContactQuery;
import helper.UserQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//todo contact not customer, not my schedule - today's appts
//number of appts by country or division
public class ReportsController implements Initializable {
    public ComboBox reportCombo;
    public Button generateReportBtn;
    public ComboBox<String> totalByType;
    public ComboBox<String> totalByMonth;
    public TextField totalBox;
    public ComboBox contactOptions;
    public Button contactSchedule;

    /**
 * The lambda in this method was created per a webinar. I used this to notate that for each item that exists in tye allAppts list I would like to get its appt type*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> months = FXCollections.observableArrayList(Arrays.asList("January","February", "March", "April", "May", "June", "July", "August", "September", "October",
                "November", "December"));

        try {
            ObservableList<Appointments> allAppts = AppointmentsQuery.select();
            ObservableList<String> typesList = FXCollections.observableArrayList();
            List<String> uniqueApptTypes = allAppts.stream().map(appt -> appt.getAppointmentType()).distinct().toList();

            for(String appt : uniqueApptTypes){
                typesList.add(appt);
            }
            totalByType.setItems(typesList);

            System.out.println(uniqueApptTypes);
            System.out.println(typesList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        totalByMonth.setItems(months);
        try {
            contactOptions.setItems(ContactQuery.select());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(months);
    }


    public void getTotalByMonthType(ActionEvent actionEvent) throws SQLException {
        String type = totalByType.getSelectionModel().getSelectedItem();
        String month = totalByMonth.getSelectionModel().getSelectedItem();
        int monthNum = 0;
        if(month.equals("January")){
            monthNum = 1;
        } else if(month.equals("February")){
            monthNum = 2;
        } else if (month.equals("March")){
            monthNum = 3;
        } else if (month.equals("April")){
            monthNum = 4;
        } else if (month.equals("May")){
            monthNum = 5;
        } else if (month.equals("June")){
            monthNum = 6;
        } else if (month.equals("July")){
            monthNum = 7;
        } else if (month.equals("August")){
            monthNum = 8;
        } else if (month.equals("September")){
            monthNum = 9;
        } else if (month.equals("October")){
            monthNum = 10;
        } else if (month.equals("November")){
            monthNum = 11;
        } else if (month.equals("December")){
            monthNum = 12;
        }

        ObservableList<Appointments> allList = AppointmentsQuery.getAppointmentByMonthAndType(monthNum, type);
        int totalNum = allList.size();
        totalBox.setText(String.valueOf(totalNum));
        System.out.println(allList);
    }
    
    public void getContactSchedule(){
        
    }
}

