package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import java.io.IOException;

public class AppointmentsController {
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

    public void modifyAppointment(ActionEvent actionEvent) throws IOException {
        Parent addPartModal = FXMLLoader.load(getClass().getResource("/view/ModifyAppointment.fxml"));
        //set new scene mod appt modal
        Scene scene = new Scene(addPartModal);
        //set stage of the modal
        Stage modal = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        //put add part modal inside
        modal.setScene(scene);
        //show the modal
        modal.show();
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

    public void deleteAppointment(ActionEvent actionEvent) {
    }

    public void viewAllAppointments(ActionEvent actionEvent) {
    }

    public void viewByMonth(ActionEvent actionEvent) {
    }

    public void viewByWeek(ActionEvent actionEvent) {
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
}
