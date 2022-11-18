package controller;

import Model.Customer;
import helper.CustomerQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    public TableColumn divisionId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            ObservableList<Customer> allCustomers = CustomerQuery.select();
            CustomerTable.setItems(allCustomers);

            customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            customerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            customerZip.setCellValueFactory(new PropertyValueFactory<>("postal"));
            customerPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone"));
            dateCreated.setCellValueFactory(new PropertyValueFactory<>("createDate"));
            createdBy.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
            updated.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
            updatedBy.setCellValueFactory(new PropertyValueFactory<>("updatedBy"));
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
