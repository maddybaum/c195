package controller;

import Model.Contact;
import Model.Countries;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {
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
    public ComboBox contactBox;

    public void saveAppointment(ActionEvent actionEvent) {
        String appointmentTitle = appointmentTitleInput.getText();
        String appointmentDescription = descriptionInput.getText();
        String appointmentLocation = locationInput.getText();
        String appointmentType = typeInput.getText();
        Timestamp appointmentStart = Timestamp.valueOf((String) addStartTimeBox.getSelectionModel().getSelectedItem());
        Timestamp appointmentEnd = Timestamp.valueOf((String) addEndTimeBox.getSelectionModel().getSelectedItem());
        int customerId = (int) addApptCustomerBox.getSelectionModel().getSelectedItem();
        Timestamp lastUpdated = Timestamp();
        String createdBy =


        AppointmentsQuery.insert(appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, appointmentStart, appointmentEnd, customerId, lastUpdated, )
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
            ObservableList<Customer> allCustomerList = CustomerQuery.select();
            ObservableList allCustomerNames = FXCollections.observableArrayList();

            for(Customer customer : allCustomerList){
                allCustomerNames.add(customer.getCustomerName());
            }

            ObservableList<Contact> allContactList = ContactQuery.select();
            ObservableList allContactNames = FXCollections.observableArrayList();
            for(Contact contact : allContactList){
                allContactNames.add(contact.getContactName());
            }

            ObservableList<User> allUserList = UserQuery.select();
            ObservableList allUserNames = FXCollections.observableArrayList();
            for(User user : allUserList){
                allUserNames.add(user.getUsername());
            }

            addApptCustomerBox.setItems(allCustomerNames);
            contactBox.setItems(allContactNames);
            addApptUserBox.setItems(allUserNames);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
