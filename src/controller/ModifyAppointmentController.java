package controller;

import Model.Appointments;
import Model.User;
import helper.CustomerQuery;
import helper.UserLogin;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ModifyAppointmentController {
    public TextField appointmentId;
    public TextField appointmentTitleInput;
    public TextField modifyDescriptionInput;
    public TextField modifyLocationInput;
    public TextField modifyTypeInput;
    public DatePicker modifyCustomerStart;
    public DatePicker modifyCustomerEnd;
    public ComboBox modifyApptCustomerBox;
    public ComboBox modifyApptUserBox;
    public ComboBox modifyStartTimeBox;
    public ComboBox modifyEndTimeBox;
    public Button saveButton;
    public Button cancelButton;
    public ComboBox contactBox;
    public ComboBox addApptCustomerBox;
    public ComboBox addApptUserBox;

    public void setInputs(Appointments appointments){
        appointmentId.setText(Integer.toString(appointments.getAppointmentID()));
        appointmentTitleInput.setText(appointments.getAppointmentTitle());
        modifyDescriptionInput.setText(appointments.getAppointmentDescription());
        modifyLocationInput.setText(appointments.getAppointmentLocation());
        modifyTypeInput.setText(appointments.getAppointmentType());

    }
    public void saveAppointment(ActionEvent actionEvent) {
        try{
        Appointments apptToModify = AppointmentsController.getAppointmentToModify();
        int appointmentId = apptToModify.getAppointmentID();
        String newAppointmentTitle = appointmentTitleInput.getText();
        String newAppointmentDesc = modifyDescriptionInput.getText();
        String newAppointmentLocation = modifyLocationInput.getText();
        String newAppointmentType = modifyTypeInput.getText();

        String customerName = (String) modifyApptCustomerBox.getSelectionModel().getSelectedItem();
        int customerId = CustomerQuery.getCustomerIDByName(customerName);

            LocalDate startDate = modifyCustomerStart.getValue();
            LocalDate endDate = modifyCustomerEnd.getValue();

            LocalTime startTime = (LocalTime) modifyStartTimeBox.getValue();
            LocalTime endTime = (LocalTime) modifyEndTimeBox.getValue();

            LocalDateTime localDateTimeStart = LocalDateTime.of(startDate, startTime);
            LocalDateTime localDateTimeEnd = LocalDateTime.of(endDate, endTime);
            
            //Need to get the previously created by value
            String createdBy = UserLogin.getUsername();
            
            int userId = User.getUserId();
            String contactName = (String) 
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
}
