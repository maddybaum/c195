package controller;

import Model.Appointments;
import Model.Contact;
import Model.Customer;
import Model.User;
import helper.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ModifyAppointmentController implements Initializable {
    public TextField appointmentId;
    public TextField appointmentTitleInput;
    public TextField modifyDescriptionInput;
    public TextField modifyLocationInput;
    public TextField modifyTypeInput;

    public ComboBox modifyStartTimeBox;
    public ComboBox modifyEndTimeBox;
    public Button saveButton;
    public Button cancelButton;
    public ComboBox<Contact> contactBox;

    public ComboBox<User> modifyUser;
    public ComboBox<Customer> modifyCustomer;
    public DatePicker modifyStart;
    public TextField modifyStartInput;

    public void setInputs(Appointments appointments) throws SQLException {
        System.out.println("APPOINTMENTS " + appointments);
        appointmentId.setText(Integer.toString(appointments.getAppointmentID()));
        appointmentTitleInput.setText(appointments.getAppointmentTitle());
        modifyDescriptionInput.setText(appointments.getAppointmentDescription());
        modifyLocationInput.setText(appointments.getAppointmentLocation());
        modifyTypeInput.setText(appointments.getAppointmentType());
        int contactId = appointments.getContactId();
        contactBox.setValue(ContactQuery.getContactById(contactId));
        int customerId = appointments.getCustomerId();
        modifyCustomer.setValue(CustomerQuery.getCustomerById(customerId));
        modifyStartTimeBox.setValue(appointments.getAppointmentStart().toLocalDateTime().toLocalTime());
        modifyEndTimeBox.setValue(appointments.getAppointmentEnd().toLocalDateTime().toLocalTime());
        modifyUser.setValue(UserQuery.getUserByID(appointments.getUserId()));
        System.out.println("APPOINTMENTSTART" + appointments.getAppointmentStart());
        int userId = appointments.getUserId();
        modifyStart.setValue(appointments.getAppointmentStart().toLocalDateTime().toLocalDate());

    }
//look at documentation for timestamp to extract only date or only time
    public void saveAppointment(ActionEvent actionEvent) throws SQLException {

        try {
            Appointments apptToModify = AppointmentsController.getAppointmentToModify();
            System.out.println("APPOINTMENT TO MODIFY" + apptToModify);
            int appointmentIdValue = Integer.parseInt(appointmentId.getText());

            String newAppointmentTitle = appointmentTitleInput.getText();
            String newAppointmentDesc = modifyDescriptionInput.getText();
            String newAppointmentLocation = modifyLocationInput.getText();
            String newAppointmentType = modifyTypeInput.getText();

            Customer customer = (Customer) modifyCustomer.getSelectionModel().getSelectedItem();


            int customerId = customer.getCustomerId();

            LocalDate startDate = modifyStart.getValue();

            LocalTime startTime = (LocalTime) modifyStartTimeBox.getValue();
            LocalTime endTime = (LocalTime) modifyEndTimeBox.getValue();

            LocalDateTime localDateTimeStart = LocalDateTime.of(startDate, startTime);
            LocalDateTime localDateTimeEnd = LocalDateTime.of(startDate, endTime);

            //Need to get the previously created by value
            String createdBy = "original";

            int user = modifyUser.getSelectionModel().getSelectedItem().getUserId();

            int contact = contactBox.getSelectionModel().getSelectedItem().getContactId();

            String currentUser = UserQuery.getLoggedInUser();
            LocalDateTime updatedOn = LocalDateTime.now();


            if (startDate.getDayOfWeek() == DayOfWeek.SATURDAY || startDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                Alert weekend = new Alert(Alert.AlertType.ERROR);
                weekend.setContentText("Your chosen date is on a weekend. Our business is closed on weekends.");
                weekend.showAndWait();
            }
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
                    } else if (appointments.getAppointmentStart().equals(localDateTimeStart)) {
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
                    AppointmentsQuery.update(appointmentIdValue, newAppointmentTitle, newAppointmentDesc, newAppointmentLocation, newAppointmentType, localDateTimeStart, localDateTimeEnd, createdBy, updatedOn, currentUser,
                            customerId, user, contact);

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

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
        public void cancelClicked (ActionEvent actionEvent) throws IOException {
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
            public void initialize (URL url, ResourceBundle resourceBundle){
                try {

                    modifyCustomer.setItems(CustomerQuery.select());
                    contactBox.setItems(ContactQuery.select());
                    modifyUser.setItems(UserQuery.select());


                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                ObservableList<LocalTime> allTimesList = FXCollections.observableArrayList();


                modifyStartTimeBox.setItems(TimeManager.getTimes(8));
                modifyEndTimeBox.setItems(TimeManager.getTimes(9));


            }
    }
