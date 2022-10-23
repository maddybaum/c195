package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyCustomerController {
    public TextField modifyCustomerID;
    public TextField modifyCustomerNameInput;
    public TextField modifyCustomerPhoneInput;
    public TextField modifyCustomerAddressInput;
    public TextField modifyCustomerZipInput;
    public ComboBox divisionBox;
    public Button modifyCustomerSaveBtn;
    public Button modifyCustomerCancelBtn;

    public void modifyCustomerSave(ActionEvent actionEvent) {
    }

    public void modifyCustomerCancel(ActionEvent actionEvent) throws IOException {
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
