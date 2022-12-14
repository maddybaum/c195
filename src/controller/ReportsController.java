package controller;

import Model.Appointments;
import Model.Contact;
import Model.User;
import helper.AppointmentsQuery;
import helper.ContactQuery;
import helper.UserQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
    public ComboBox<Contact> contactOptions;
    public Button contactSchedule;
    public TableView contactScheduleTable;
    public TableColumn contactApptID;
    public TableColumn contactTitle;
    public TableColumn contactType;
    public TableColumn contactDescription;
    public TableColumn contactStart;
    public TableColumn contactEnd;
    public TableColumn contactCustomer;
    public Button backButton;
    public TableView todaysScheduleTable;
    public TableColumn apptID;
    public TableColumn apptTitle;
    public TableColumn apptLocation;
    public TableColumn apptStart;
    public TableColumn apptEnd;
    public TableColumn apptCustomer;

    /**
 * The lambda in this method was created per a webinar. I used this to notate that for each item that exists in tye allAppts list I would like to get its appt type*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> months = FXCollections.observableArrayList(Arrays.asList("January","February", "March", "April", "May", "June", "July", "August", "September", "October",
                "November", "December"));
        try {
            getTodaysSchedule();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            ObservableList<Appointments> allAppts = AppointmentsQuery.select();
            ObservableList<String> typesList = FXCollections.observableArrayList();
            List<String> uniqueApptTypes = allAppts.stream().map(appt -> appt.getAppointmentType()).distinct().toList();
            ObservableList<Contact> allContacts = ContactQuery.select();
            for(String appt : uniqueApptTypes){
                typesList.add(appt);
            }
            totalByType.setItems(typesList);
            contactOptions.setItems(allContacts);
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
    
    public void getContactSchedule() throws SQLException {
        Contact selectedContact = contactOptions.getSelectionModel().getSelectedItem();
        int selectedContactID = selectedContact.getContactId();
        try {
            ObservableList<Appointments> contactSchedule = AppointmentsQuery.getAppointmentsByContact(selectedContactID);
            contactScheduleTable.setItems(contactSchedule);
            contactApptID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            contactTitle.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
            contactType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            contactDescription.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
            contactStart.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
            contactEnd.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
            contactCustomer.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        } catch (SQLException e) {
            Alert noResults = new Alert(Alert.AlertType.INFORMATION);
            noResults.setContentText(selectedContact + "has no upcoming appointments");
            noResults.showAndWait();
        }
    }

    public void getTodaysSchedule() throws SQLException{
        ObservableList<Appointments> todaysAppointments = AppointmentsQuery.getApptsForToday();
        todaysScheduleTable.setItems(todaysAppointments);

        apptID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptTitle.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        apptLocation.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        apptStart.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        apptEnd.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
        apptCustomer.setCellValueFactory(new PropertyValueFactory<>("contactId"));
    }

    public void returnToAppts(ActionEvent actionEvent) throws IOException {
        Parent addPartModal = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        //set new scene with main modal
        Scene scene = new Scene(addPartModal);
        //set stage of the modal
        Stage modal = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        //put add part modal inside
        modal.setScene(scene);
        //show the modal
        modal.show();
    }
}

