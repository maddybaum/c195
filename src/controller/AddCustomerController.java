package controller;

import Model.Countries;
import Model.Divisions;
import Model.User;
import helper.CountryQuery;
import helper.CustomerQuery;
import helper.UserLogin;
import javafx.beans.binding.ObjectExpression;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {
    public TextField addCustomerID;
    public TextField addCustomerNameInput;
    public TextField addCustomerPhoneInput;
    public TextField addCustomerAddressInput;
    public TextField addCustomerZipInput;
    public ChoiceBox countryBox;
    public Button addCustomerSaveBtn;
    public Button addCustomerCancelBtn;
    public ComboBox divisionBox;

    public void addCustomerSave(ActionEvent actionEvent) throws SQLException, IOException {

        String customerName = addCustomerNameInput.getText();
        String customerPhone = addCustomerPhoneInput.getText();
        String customerAddressNum = addCustomerAddressInput.getText();
        String customerZip = addCustomerZipInput.getText();
        String customerDivision = divisionBox.getSelectionModel().getSelectedItem().toString();
        int customerDivisionId = CountryQuery.getDivisionByName(customerDivision);
        String createdBy = UserLogin.getUsername();
        LocalDateTime createdOn = LocalDateTime.now();
        LocalDateTime updatedOn = LocalDateTime.now();
        String updatedBy = User.getUsername();

        Countries customerCountry = CountryQuery.getCountryByDivision(customerDivisionId);

        String customerAddress = customerAddressNum + ", " + customerDivision + " ";
        System.out.println(customerAddress);

        CustomerQuery.addCustomer(customerName, customerAddress, customerZip, customerPhone,
                createdOn, createdBy, updatedOn, updatedBy, customerDivisionId);


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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Countries> allCountries = CountryQuery.select();
            ObservableList countryOptions = FXCollections.observableArrayList();
            for(Countries country : allCountries){
                countryOptions.add(country.getCountry());
            }
            countryBox.setItems(countryOptions);
//            if(countryBox.getSelectionModel().getSelectedItem() != null){
//                getDivisionsByCountry();
//            }
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
