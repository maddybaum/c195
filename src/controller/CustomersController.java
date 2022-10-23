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

public class CustomersController {
    public TableColumn customerId;
    public TableColumn customerName;
    public TableColumn customerPhoneNumber;
    public TableColumn customerAddress;
    public TableColumn customerZip;
    public TableColumn customerState;
    public TableColumn dateCreated;
    public TableColumn createdBy;
    public TableColumn updated;
    public TableColumn updatedBy;
    public RadioButton viewAllRadio;
    public RadioButton viewByMonthRadio;
    public RadioButton viewByWeekRadio;
    public RadioButton viewByCustomerRadio;
    public Button addCustomerBtn;
    public Button modifyCustomerBtn;
    public Button deleteCustomerButton;
    public Button closeButton;

    public void viewAllCustomers(ActionEvent actionEvent) {

    }

    public void viewByCountry(ActionEvent actionEvent) {
    }

    public void viewByWeek(ActionEvent actionEvent) {
    }

    public void viewByCustomer(ActionEvent actionEvent) {
    }

    public void addCustomer(ActionEvent actionEvent) throws IOException {
        Parent addPartModal = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        //set new scene with add customer modal
        Scene scene = new Scene(addPartModal);
        //set stage of the modal
        Stage modal = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        //put add part modal inside
        modal.setScene(scene);
        //show the modal
        modal.show();
    }

    public void modifyCustomer(ActionEvent actionEvent) throws IOException {
        Parent addPartModal = FXMLLoader.load(getClass().getResource("/view/ModifyCustomer.fxml"));
        //set new scene with modify customer modal
        Scene scene = new Scene(addPartModal);
        //set stage of the modal
        Stage modal = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        //put add part modal inside
        modal.setScene(scene);
        //show the modal
        modal.show();
    }

    public void deleteCustomer(ActionEvent actionEvent) {
    }

    public void closeWindow(ActionEvent actionEvent) throws IOException {
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
