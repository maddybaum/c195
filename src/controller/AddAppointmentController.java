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
    public ComboBox addApptCustomerBox;
    public ComboBox addApptUserBox;
    public ComboBox addStartTimeBox;
    public ComboBox addEndTimeBox;
    public ComboBox contactBox;

    public void saveAppointment(ActionEvent actionEvent) throws SQLException, IOException {
        //appointment ID

        int appointmentId = (int) (Math.random()*100);
        String appointmentTitle = appointmentTitleInput.getText();
        String appointmentDescription = descriptionInput.getText();
        String appointmentLocation = locationInput.getText();
        String appointmentType = typeInput.getText();


        String customerName = (String) addApptCustomerBox.getSelectionModel().getSelectedItem();
        int customerId = CustomerQuery.getCustomerIDByName(customerName);

        LocalDate startDate = addCustomerStart.getValue();
        LocalDate endDate = addCustomerStart.getValue();

        LocalTime startTime = (LocalTime) addStartTimeBox.getSelectionModel().getSelectedItem();
        LocalTime endTime = (LocalTime) addEndTimeBox.getSelectionModel().getSelectedItem();

        LocalDateTime localDateTimeStart = LocalDateTime.of(startDate, startTime);
        LocalDateTime localDateTimeEnd = LocalDateTime.of(endDate, endTime);



        System.out.println(localDateTimeEnd);

        String createdBy = UserLogin.getUsername();

        LocalDateTime createdOn = LocalDateTime.now();

        LocalDateTime updatedOn = LocalDateTime.now();
        String updatedBy = User.getUsername();

        int userId = User.getUserId();

        String contactName = (String) contactBox.getSelectionModel().getSelectedItem();
        int contactId = ContactQuery.getContactIDByName(contactName);

        if(endTime.isBefore(startTime)){
            Alert badEndTime = new Alert(Alert.AlertType.ERROR);
            badEndTime.setContentText("The start time selected is after the end time. Please fix timing of appointment");
            badEndTime.showAndWait();

        } else {


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
    }}

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
        ObservableList<LocalTime> allTimesList = FXCollections.observableArrayList();


        addStartTimeBox.setItems(TimeManager.getTimes(8));
        addEndTimeBox.setItems(TimeManager.getTimes(9));

    }
}
