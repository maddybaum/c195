package controller;

import Model.Appointments;
import helper.AppointmentsQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {
    private static Appointments appointmentToModify;
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
    public TableColumn appointmentCreateDate;

    public TableView allTable;

    public void modifyAppointment(ActionEvent actionEvent) throws IOException {

        Appointments appointmentToModify = (Appointments) allTable.getSelectionModel().getSelectedItem();
        if(appointmentToModify == null){
            Alert noValue = new Alert(Alert.AlertType.ERROR);
            noValue.setContentText("No appointment selected");
            Optional<ButtonType> response = noValue.showAndWait();
        }
       FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyAppointment.fxml"));
        loader.load();
        ModifyAppointmentController mac = loader.getController();
        mac.setInputs(appointmentToModify);

        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    public void setAppointmentToModify(){
        appointmentToModify = appointmentToModify;
    }
    public static Appointments getAppointmentToModify(){
        return appointmentToModify;
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



    public void viewAllAppointments() throws SQLException {
        ObservableList<Appointments> allAppointments = AppointmentsQuery.select();

//        for (Appointments appointments : allAppointments) {
//            appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
//            appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
//            appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
//            appointmentContact.setCellValueFactory(new PropertyValueFactory<>(""));
//            appointmentLocation.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
//            appointmentType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
//            appointmentStart.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
//            appointmentEnd.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
//            userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
//        }

    }
    public void viewByMonth(ActionEvent actionEvent) {
//        try {
//            ObservableList<Appointments> appointmentsByMonthList = FXCollections.observableArrayList();
//
//        }

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

    public void deleteAppointment(ActionEvent actionEvent) throws SQLException {
        Appointments appointmentToDelete = (Appointments) allTable.getSelectionModel().getSelectedItem();

        if(appointmentToDelete == null) {
            Alert noValue = new Alert(Alert.AlertType.ERROR);
            noValue.setContentText("There is no appointment to delete");
            Optional<ButtonType> response = noValue.showAndWait();
        }
        int appointmentId = appointmentToDelete.getAppointmentID();
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setContentText("Are you sure you want to delete?");
        Optional<ButtonType> response = confirmation.showAndWait();

        if(response.get() == ButtonType.OK) {
            AppointmentsQuery.delete(appointmentId);
            ObservableList appointmentList = AppointmentsQuery.select();
            System.out.println("Deleted " + appointmentId);
            allTable.setItems(appointmentList);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Appointments> allAppointments = AppointmentsQuery.select();
            allTable.setItems(allAppointments);
            appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
            appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
            appointmentContact.setCellValueFactory(new PropertyValueFactory<>("contactId"));
            appointmentLocation.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
            appointmentType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            appointmentStart.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
            appointmentEnd.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
            userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
            customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
