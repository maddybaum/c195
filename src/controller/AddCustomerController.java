package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCustomerController {
    public TextField addCustomerID;
    public TextField addCustomerNameInput;
    public TextField addCustomerPhoneInput;
    public TextField addCustomerAddressInput;
    public TextField addCustomerZipInput;
    public ChoiceBox countryBox;
    public Button addCustomerSaveBtn;
    public Button addCustomerCancelBtn;

    public void addCustomerSave(ActionEvent actionEvent) {
    }

    public void addCustomerCancel(ActionEvent actionEvent) throws IOException {
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
