package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddAppointmentController {
    public TextField appointmentId;
    public TextField appointmentTitleInput;
    public TextField descriptionInput;
    public TextField locationInput;
    public TextField typeInput;
    public TextField startInput;
    public DatePicker addCustomerStart;
    public DatePicker addCustomerEnd;
    public ComboBox addApptCustomerBox;
    public ComboBox addApptUserBox;
    public ComboBox addStartTimeBox;
    public ComboBox addEndTimeBox;

    public void saveAppointment(ActionEvent actionEvent) {
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
