package controller;

import Model.Countries;
import Model.Customer;
import Model.Divisions;
import Model.User;
import helper.CountryQuery;
import helper.CustomerQuery;
import helper.UserQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable {

    int customerId;
    public TextField modifyCustomerID;
    public TextField modifyCustomerNameInput;
    public TextField modifyCustomerPhoneInput;
    public TextField modifyCustomerAddressInput;
    public TextField modifyCustomerZipInput;
    public ComboBox<Divisions> divisionBox;
    public Button modifyCustomerSaveBtn;
    public Button modifyCustomerCancelBtn;
    public ComboBox<Countries> countryBox;

    int customerDivisionId;
/**Takes in a cutomer to modify from the customer controller and sets the corresponding inputs for that customer*/
    public void setInputs(Customer customerToModify) throws SQLException {
        countryBox.setItems(CountryQuery.select());
        System.out.println("line 43 customer to modify" + customerToModify);
        customerId = customerToModify.getCustomerId();
        System.out.println("modifycustomercontroller ID" + customerId);
        modifyCustomerID.setText(String.valueOf(customerId));
        modifyCustomerNameInput.setText(customerToModify.getCustomerName());
        System.out.println("customer to modify name " + customerToModify.getCustomerName());
        modifyCustomerPhoneInput.setText(customerToModify.getPhone());
        System.out.println("customer to modify phone " + customerToModify.getPhone());
        modifyCustomerAddressInput.setText(customerToModify.getAddress());
        modifyCustomerZipInput.setText(customerToModify.getPostal());
        int divisionId = customerToModify.getDivisionId();
        Countries customerCountry = CountryQuery.getCountryByDivision(divisionId);
        countryBox.setValue(CountryQuery.getCountryByDivision(divisionId));
        System.out.println("COUNTRY BY DIVISION " + CountryQuery.getCountryByDivision(divisionId));
        System.out.println(customerToModify.getDivisionId() + "GET DIVISION ID METHOD");
        customerDivisionId = customerToModify.getDivisionId();
        System.out.println("CUSTOMERDIVISIONID VARIABLE IS HERE" + customerDivisionId);
        divisionBox.setItems(CountryQuery.getCountryDivisions(String.valueOf(customerCountry)));
        divisionBox.setValue(CountryQuery.getDivisionByID(divisionId));

        System.out.println("division result" + CountryQuery.getDivisionByID(divisionId));
    }

    /**This takes in all of the inputs and updates the customer calling the updateCustomer method in CustomerQuery.
     * Based on the required formatting for address following the street number + division, I Had to create a string in order to get that to work properly*/

    public void modifyCustomerSave(ActionEvent actionEvent) throws SQLException, IOException {
        try {
            int customerId = Integer.parseInt(modifyCustomerID.getText());

            String customerName = modifyCustomerNameInput.getText();
            String customerPhone = modifyCustomerPhoneInput.getText();
            String customerAddressNum = modifyCustomerAddressInput.getText();
            String customerZip = modifyCustomerZipInput.getText();
            Divisions customerDivision = divisionBox.getSelectionModel().getSelectedItem();
            int divisionId = CountryQuery.getDivisionByName(customerDivision.getDivision());
            System.out.println("DIVISION ID " + customerDivisionId);
            String createdBy = UserQuery.getLoggedInUser();
            LocalDateTime createdOn = LocalDateTime.now();
            LocalDateTime updatedOn = LocalDateTime.now();
            String updatedBy = UserQuery.getLoggedInUser();

            Countries customerCountry = CountryQuery.getCountryByDivision(divisionId);


            String customerAddress = customerAddressNum + ", " + customerDivision;

            if (customerName.isEmpty() || customerPhone.isEmpty() || customerAddress.isEmpty() || customerAddressNum.isEmpty() || customerZip.isEmpty()) {
                Alert noValues = new Alert(Alert.AlertType.ERROR);
                noValues.setContentText("Please make sure all values are entered before saving customer");
                noValues.showAndWait();
            } else {
                CustomerQuery.updateCustomer(customerId, customerName, customerAddress, customerZip, customerPhone,
                        createdOn, createdBy, updatedOn, updatedBy, divisionId);


                Parent addPartModal = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
                //set new scene with main modal
                Scene scene = new Scene(addPartModal);
                //set stage of the modal
                Stage modal = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                //put add part modal inside
                modal.setScene(scene);
                //show the modal
                modal.show();
            }
        } catch (NumberFormatException e) {
            Alert invalidData = new Alert(Alert.AlertType.ERROR);
            invalidData.setContentText("Please make sure all values are entered before saving appointment");
            invalidData.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            countryBox.setItems(CountryQuery.select());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void getDivisionsByCountry() throws SQLException {
//        Countries country = (Countries) countryBox.getSelectionModel().getSelectedItem();
        String country = countryBox.getSelectionModel().getSelectedItem().toString();
        System.out.println("country here" + country);
//        int countryId = country.getCountryId();
        System.out.println(country);
        divisionBox.setItems(CountryQuery.getCountryDivisions(country));
        
    }



}
