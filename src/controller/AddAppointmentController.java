package controller;

import Model.*;
import helper.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class AddAppointmentController implements Initializable {
    public TextField appointmentId;
    public TextField appointmentTitleInput;
    public TextField descriptionInput;
    public TextField locationInput;
    public TextField typeInput;
    public TextField startInput;
    public DatePicker addCustomerStart;
    public DatePicker addCustomerEnd;
    public ComboBox<Customer> addApptCustomerBox;
    public ComboBox<User> addApptUserBox;
    public ComboBox addStartTimeBox;
    public ComboBox addEndTimeBox;
    public ComboBox<Contact> contactBox;

    /**
     * Upon click to save an appointment, this function is invoked. It will grab the inputs from each input area and validate them
     * by checking if the date makes sense - on a weekend, or if the start and end times do not logically align. If the timing is okay, it will check whether the customer or
     * the user attached to the appointment have other appointments that overlap. If they do, the user will receive an error to fix that. If all data is okay and meets the
     * requirements, then this function will call the addAppointment method in AppointmentsQuery*/
    public void saveAppointment(ActionEvent actionEvent) throws SQLException, IOException {
        //appointment ID
        try {
            String appointmentTitle = appointmentTitleInput.getText();
            String appointmentDescription = descriptionInput.getText();
            String appointmentLocation = locationInput.getText();
            String appointmentType = typeInput.getText();

            Customer customer = addApptCustomerBox.getSelectionModel().getSelectedItem();
            int customerId = addApptCustomerBox.getValue().getCustomerId();

            LocalDate startDate = addCustomerStart.getValue();
            LocalDate endDate = addCustomerStart.getValue();
            if (startDate.getDayOfWeek() == DayOfWeek.SATURDAY || startDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                Alert weekend = new Alert(Alert.AlertType.ERROR);
                weekend.setContentText("Your chosen date is on a weekend. Our business is closed on weekends.");
                weekend.showAndWait();
            }

            LocalTime startTime = (LocalTime) addStartTimeBox.getSelectionModel().getSelectedItem();
            LocalTime endTime = (LocalTime) addEndTimeBox.getSelectionModel().getSelectedItem();

            LocalDateTime localDateTimeStart = LocalDateTime.of(startDate, startTime);
            LocalDateTime localDateTimeEnd = LocalDateTime.of(endDate, endTime);

            System.out.println(localDateTimeEnd);

            String createdBy = UserQuery.getLoggedInUser();

            LocalDateTime createdOn = LocalDateTime.now();

            LocalDateTime updatedOn = LocalDateTime.now();
            String updatedBy = UserQuery.getLoggedInUser();

            String currentUser = UserQuery.getLoggedInUser();
            int userId = UserQuery.getUserUser(currentUser).getUserId();
            Contact contact = contactBox.getSelectionModel().getSelectedItem();
            String contactName = contact.getContactName();
            int contactId = contact.getContactId();

            if (endTime.isBefore(startTime)) {
                Alert badEndTime = new Alert(Alert.AlertType.ERROR);
                badEndTime.setContentText("The start time selected is after the end time. Please fix timing of appointment");
                badEndTime.showAndWait();

            } else if (endTime.equals(startTime)) {
                Alert badEnd = new Alert((Alert.AlertType.ERROR));
                badEnd.setContentText("You selected the same start and end time for the appointment");
                badEnd.showAndWait();
            } else {

                ObservableList<Appointments> customerAppts = AppointmentsQuery.getApptsByCustomer(customerId);
                System.out.println(customerAppts);
                for (Appointments appointments : customerAppts) {
                    //case 3

                    //works
                    if (appointments.getAppointmentStart().toLocalDateTime().isBefore(localDateTimeStart) && appointments.getAppointmentEnd().toLocalDateTime().isAfter(localDateTimeEnd)) {
                        Alert overlapAlert = new Alert(Alert.AlertType.ERROR);
                        overlapAlert.setContentText(CustomerQuery.getCustomerById(customerId) + " has a conflict with this proposed time. They have another appointment at " + appointments.getAppointmentStart());
                        overlapAlert.showAndWait();
                        return;
                        //works
                    } else if (appointments.getAppointmentStart().toLocalDateTime().isAfter(localDateTimeStart) && appointments.getAppointmentEnd().toLocalDateTime().isBefore(localDateTimeEnd)) {
                        Alert overlapAlert = new Alert(Alert.AlertType.ERROR);
                        overlapAlert.setContentText(CustomerQuery.getCustomerById(customerId) + " has a conflict with this proposed time. They have another appointment at " + appointments.getAppointmentStart());
                        overlapAlert.showAndWait();
                        return;

                    } else if (localDateTimeStart.isAfter(appointments.getAppointmentStart().toLocalDateTime()) && localDateTimeEnd.isBefore(appointments.getAppointmentEnd().toLocalDateTime())) {
                        Alert overlapAlert = new Alert(Alert.AlertType.ERROR);
                        overlapAlert.setContentText(CustomerQuery.getCustomerById(customerId) + " has a conflict with this proposed time. They have another appointment at " + appointments.getAppointmentStart());
                        overlapAlert.showAndWait();
                        return;
                    } else if (appointments.getAppointmentStart().equals(localDateTimeStart)){
                            Alert overlapAlert = new Alert(Alert.AlertType.ERROR);
                            overlapAlert.setContentText(CustomerQuery.getCustomerById(customerId) + " has a conflict with this proposed time. They have another appointment at " + appointments.getAppointmentStart());
                            overlapAlert.showAndWait();
                            return;
                        } else if (appointments.getAppointmentEnd().equals(localDateTimeEnd)) {
                        Alert overlapAlert = new Alert(Alert.AlertType.ERROR);
                        overlapAlert.setContentText(CustomerQuery.getCustomerById(customerId) + " has a conflict with this proposed time. They have another appointment at " + appointments.getAppointmentStart());
                        overlapAlert.showAndWait();
                        return;
                    }
                }
            ObservableList<Appointments> userAppointments = AppointmentsQuery.getApptsByUser(userId);
                for(Appointments appointments : userAppointments){
                    if (appointments.getAppointmentStart().toLocalDateTime().isBefore(localDateTimeStart) && appointments.getAppointmentEnd().toLocalDateTime().isAfter(localDateTimeEnd)) {
                        Alert overlapAlert = new Alert(Alert.AlertType.ERROR);
                        overlapAlert.setContentText(UserQuery.getUserByID(userId).getUsername() + " has a conflict with this proposed time. They have another appointment at " + appointments.getAppointmentStart());
                        overlapAlert.showAndWait();
                        return;
                        //works
                    } else if (appointments.getAppointmentStart().toLocalDateTime().isAfter(localDateTimeStart) && appointments.getAppointmentEnd().toLocalDateTime().isBefore(localDateTimeEnd)) {
                        Alert overlapAlert = new Alert(Alert.AlertType.ERROR);
                        overlapAlert.setContentText(UserQuery.getUserByID(userId).getUsername() + " has a conflict with this proposed time. They have another appointment at " + appointments.getAppointmentStart());
                        overlapAlert.showAndWait();
                        return;

                    } else if (localDateTimeStart.isAfter(appointments.getAppointmentStart().toLocalDateTime()) && localDateTimeEnd.isBefore(appointments.getAppointmentEnd().toLocalDateTime())) {
                        Alert overlapAlert = new Alert(Alert.AlertType.ERROR);
                        overlapAlert.setContentText(UserQuery.getUserByID(userId).getUsername() + " has a conflict with this proposed time. They have another appointment at " + appointments.getAppointmentStart());
                        overlapAlert.showAndWait();
                        return;
                    } else if (appointments.getAppointmentStart().equals(localDateTimeStart)){
                        Alert overlapAlert = new Alert(Alert.AlertType.ERROR);
                        overlapAlert.setContentText(UserQuery.getUserByID(userId).getUsername() + " has a conflict with this proposed time. They have another appointment at " + appointments.getAppointmentStart());
                        overlapAlert.showAndWait();
                        return;
                    } else if (appointments.getAppointmentEnd().equals(localDateTimeEnd)) {
                        Alert overlapAlert = new Alert(Alert.AlertType.ERROR);
                        overlapAlert.setContentText(UserQuery.getUserByID(userId).getUsername() + " has a conflict with this proposed time. They have another appointment at " + appointments.getAppointmentStart());
                        overlapAlert.showAndWait();
                        return;
                    }
                }

//
                System.out.println(customerId);
                System.out.println(userId);
                System.out.println(contactId);

                AppointmentsQuery.insert(appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, localDateTimeStart, localDateTimeEnd,
                        customerId, userId, contactId);

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

    } catch(RuntimeException e) {
            Alert validValues = new Alert(Alert.AlertType.ERROR);
            validValues.setContentText("Please make sure all values are entered before saving appointment");
            validValues.showAndWait();
    }
    }
    public void cancelClicked(ActionEvent actionEvent) throws IOException {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            addApptCustomerBox.setItems(CustomerQuery.select());
            contactBox.setItems(ContactQuery.select());
            addApptUserBox.setItems(UserQuery.select());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<LocalTime> allTimesList = FXCollections.observableArrayList();


        addStartTimeBox.setItems(TimeManager.getTimes(8));
        addEndTimeBox.setItems(TimeManager.getTimes(9));

    }

}
