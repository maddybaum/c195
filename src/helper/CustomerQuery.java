package helper;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public abstract class CustomerQuery {
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

    public static ObservableList select() throws SQLException {
        ObservableList<Customer> allCustomerList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postal = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionId = rs.getInt("Division_ID");

            Customer customer = new Customer(customerId, customerName, address, postal, phone,
                    createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId);
            allCustomerList.add(customer);
        }
        return allCustomerList;
    }

    public static int getCustomerIDByName(String customerName) throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS WHERE CUSTOMER_NAME = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ResultSet rs = ps.executeQuery();
        int customerIdSet = 0;

        while(rs.next()){
            int customerId = rs.getInt("Customer_ID");
            customerIdSet = customerId;

        }
        return customerIdSet;
    }
}
