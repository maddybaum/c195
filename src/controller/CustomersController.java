package controller;

import Model.Countries;
import Model.Customer;
import Model.Divisions;
import helper.CountryQuery;
import helper.CustomerQuery;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomersController implements Initializable {
    public TableColumn customerId;
    public TableColumn customerName;
    public TableColumn customerPhoneNumber;
    public TableColumn customerAddress;
    public TableColumn customerZip;
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
    public TableView CustomerTable;
    public TableColumn<Divisions, String> divisionId;
    public TableColumn<Countries, String> country;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
//todo after division add the country
            ObservableList<Customer> allCustomers = CustomerQuery.select();
            CustomerTable.setItems(allCustomers);

            customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            customerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            customerZip.setCellValueFactory(new PropertyValueFactory<>("postal"));
            customerPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone"));
            divisionId.setCellValueFactory(new PropertyValueFactory<>("divisionId"));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public void modifyCustomer(ActionEvent actionEvent) throws IOException, SQLException {
        Customer customerToModify = (Customer) CustomerTable.getSelectionModel().getSelectedItem();
        int customerId = customerToModify.getCustomerId();
        System.out.println("==========CustomerToModify" + customerToModify);
        System.out.println("========== customer ID" + customerId);
        if(customerToModify == null){
            Alert noValue = new Alert(Alert.AlertType.ERROR);
            noValue.setContentText("No customer selected");
            Optional<ButtonType> response = noValue.showAndWait();
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyCustomer.fxml"));
        loader.load();
        ModifyCustomerController mcc = loader.getController();
        mcc.setInputs(customerToModify);

       Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
       Parent scene = loader.getRoot();
       stage.setScene(new Scene(scene));
       stage.show();
    }

    public void deleteCustomer(ActionEvent actionEvent) throws SQLException {
        Customer customerToDelete = (Customer) CustomerTable.getSelectionModel().getSelectedItem();

        if(customerToDelete == null){
            Alert noValue = new Alert(Alert.AlertType.ERROR);
            noValue.setContentText("There is no customer to delete");
            Optional<ButtonType> response = noValue.showAndWait();
        }

        int customerId = customerToDelete.getCustomerId();
        String customerName = customerToDelete.getCustomerName();
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setContentText("Are you sure you want to delete " + customerName + "?");
        Optional<ButtonType> response = confirmation.showAndWait();

        if(response.get() == ButtonType.OK){
            CustomerQuery.delete(customerId);
            Alert customerDeleted = new Alert(Alert.AlertType.INFORMATION);
            customerDeleted.setContentText(customerName + "successfully deleted");
            ObservableList customerList = CustomerQuery.select();
            Optional<ButtonType> responseDelete = customerDeleted.showAndWait();
            CustomerTable.setItems(customerList);

        }
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


    public void generateReport(ActionEvent actionEvent) {
    }
}
