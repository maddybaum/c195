package controller;

import Model.Appointments;
import helper.AppointmentsQuery;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {
    private static Appointments appointmentToModify;
    public TableColumn appointmentId;
    public TableColumn appointmentTitle;
    public TableColumn appointmentDescription;
    public TableColumn appointmentLocation;
    public TableColumn appointmentContact;
    public TableColumn appointmentType;
    public TableColumn appointmentStart;
    public TableColumn appointmentEnd;
    public TableColumn customerId;
    public TableColumn userId;
    public Button addAppt;
    public Button deleteAppointment;
    public RadioButton viewAllRadio;
    public RadioButton viewByMonthRadio;
    public RadioButton viewByWeekRadio;
    public RadioButton viewByCustomerRadio;
    public Button viewCustomersButton;
    public TableColumn appointmentCreateDate;

    public TableView allTable;

    public void modifyAppointment(ActionEvent actionEvent) throws IOException, SQLException {

        Appointments appointmentToModify = (Appointments) allTable.getSelectionModel().getSelectedItem();
        if(appointmentToModify == null){
            Alert noValue = new Alert(Alert.AlertType.ERROR);
            noValue.setContentText("No appointment selected");
            Optional<ButtonType> response = noValue.showAndWait();
        }
       FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyAppointment.fxml"));
        loader.load();
        ModifyAppointmentController mac = loader.getController();
        mac.setInputs(appointmentToModify);

        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    public void setAppointmentToModify(){
        appointmentToModify = appointmentToModify;
    }
    public static Appointments getAppointmentToModify(){
        return appointmentToModify;
    }

    public void addAppointment(ActionEvent actionEvent) throws IOException {
        Parent addPartModal = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        //set new scene with add appt modal
        Scene scene = new Scene(addPartModal);
        //set stage of the modal
        Stage modal = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        //put add part modal inside
        modal.setScene(scene);
        //show the modal
        modal.show();
    }



    public void viewAllAppointments() throws SQLException {
        ObservableList<Appointments> allAppointments = AppointmentsQuery.select();
        allTable.setItems(allAppointments);
        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        appointmentContact.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        appointmentLocation.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appointmentStart.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        appointmentEnd.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));

    }
    public void viewByMonth(ActionEvent actionEvent) {
        try {
            Month currentMonth = LocalDate.now().getMonth();
            System.out.println(currentMonth.getValue() + "CURRENT MONTH VALUE");
            ObservableList<Appointments> appointmentsMonthList = AppointmentsQuery.selectAllByMonth(currentMonth.getValue());
            allTable.setItems(appointmentsMonthList);
            appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
            appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
            appointmentContact.setCellValueFactory(new PropertyValueFactory<>("contactId"));
            appointmentLocation.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
            appointmentType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            appointmentStart.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
            appointmentEnd.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
            userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
            customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            AppointmentsQuery.selectAllByMonth(currentMonth.getValue());

        } catch (SQLException e) {
            Alert noResults = new Alert(Alert.AlertType.WARNING);
            noResults.setContentText("There are no appointments for this month");
            noResults.showAndWait();
        }

    }

    public void viewByWeek(ActionEvent actionEvent) {
        try{
            ObservableList<Appointments> appointmentsByWeek = AppointmentsQuery.getAllApptsByCurrentWeek();
            allTable.setItems(appointmentsByWeek);
            appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
            appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
            appointmentContact.setCellValueFactory(new PropertyValueFactory<>("contactId"));
            appointmentLocation.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
            appointmentType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            appointmentStart.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
            appointmentEnd.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
            userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
            customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        } catch (SQLException e) {
            Alert noResults = new Alert(Alert.AlertType.WARNING);
            noResults.setContentText("There are no appointments for this week");
            noResults.showAndWait();        }
    }

    public void viewByCustomer(ActionEvent actionEvent) {
    }

    public void viewCustomers(ActionEvent actionEvent) throws IOException {
        Parent addPartModal = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        //set new scene with customer modal
        Scene scene = new Scene(addPartModal);
        //set stage of the modal
        Stage modal = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        //put add part modal inside
        modal.setScene(scene);
        //show the modal
        modal.show();
    }

    /**Lambda one is below to deal with the alert for confirming delete*/
    public void deleteAppointment(ActionEvent actionEvent) throws SQLException {
        Appointments appointmentToDelete = (Appointments) allTable.getSelectionModel().getSelectedItem();

        if(appointmentToDelete == null) {
            Alert noValue = new Alert(Alert.AlertType.ERROR);
            noValue.setContentText("There is no appointment to delete");
            Optional<ButtonType> response = noValue.showAndWait();
        }
        int appointmentId = appointmentToDelete.getAppointmentID();
        String appointmentType = appointmentToDelete.getAppointmentType();
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setContentText("Are you sure you want to delete Appointment " + appointmentId + "?");
       confirmation.showAndWait()
            .filter(res -> res == ButtonType.OK)
                .ifPresent(res -> {
                    try {
                        deleteAlert(appointmentToDelete);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

    }

    public void deleteAlert(Appointments appointmentToDelete) throws SQLException {
        AppointmentsQuery.delete(appointmentToDelete.getAppointmentID());
        int appointmentId = appointmentToDelete.getAppointmentID();
        String appointmentType = appointmentToDelete.getAppointmentType();
        ObservableList appointmentList = AppointmentsQuery.select();
        System.out.println("Deleted " + appointmentId + "appointment type of " + appointmentType);
        Alert appointmentDeleted = new Alert((Alert.AlertType.INFORMATION));
        appointmentDeleted.setContentText("Appointment " + appointmentId + " of type " + appointmentType + " was deleted.");
        Optional<ButtonType> responseDelete = appointmentDeleted.showAndWait();
        allTable.setItems(appointmentList);

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Appointments> allAppointments = AppointmentsQuery.select();
            allTable.setItems(allAppointments);
            appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
            appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
            appointmentContact.setCellValueFactory(new PropertyValueFactory<>("contactId"));
            appointmentLocation.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
            appointmentType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            appointmentStart.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
            appointmentEnd.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
            userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
            customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));


            LocalDateTime now = LocalDateTime.now();
            alertUpcomingAppt();
            for(Appointments appointment : allAppointments){
                if(appointment.getAppointmentStart().toLocalDateTime().isAfter(now) && appointment.getAppointmentStart().toLocalDateTime().isBefore(now.plusMinutes(60))){
                    Alert upcomingAppt = new Alert(Alert.AlertType.INFORMATION);
                    upcomingAppt.setContentText("You have an appointment in the next 15 minutes " + appointment.getAppointmentTitle() + " at " + appointment.getAppointmentStart());
                    upcomingAppt.showAndWait();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void alertUpcomingAppt() throws SQLException {
        ObservableList<Appointments> upcomingList = AppointmentsQuery.checkForApptIn15();

        if(upcomingList.size() != 0){
            Alert upcomingAlert = new Alert(Alert.AlertType.INFORMATION);
            String alertContent = "";
            for(Appointments appointments : upcomingList){
                alertContent += (
                        "Appointment Title: " + appointments.getAppointmentTitle() +
                                "Appointment Start: " + appointments.getAppointmentStart() +
                                "with " + appointments.getCustomerId()

                        );
            }
        }
        else {
            Alert noAppointments = new Alert(Alert.AlertType.INFORMATION);
            noAppointments.setContentText("You have no appointments in the next 15 minutes");
            noAppointments.showAndWait();
        }
    }

    public void openReports(ActionEvent actionEvent) throws IOException {
        Parent addPartModal = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        //set new scene with customer modal
        Scene scene = new Scene(addPartModal);
        //set stage of the modal
        Stage modal = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        //put add part modal inside
        modal.setScene(scene);
        //show the modal
        modal.show();
    }

}

