package helper;

import Model.Customer;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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
/**
 * Method to select all of the customers in the table, however this was tricky due to the fact that I had to deal with getting the customer's country as well, when the
 * table only provided their divisionID and the first_level_divisions table only provides a country ID not an actual country.
 * I used left join because it would return all of the rows of the table on the left and then what matches on the right.  Since Customers is the first left table
 * all of that will be returned, and then the matching first_level_division, and similarly the same thing will happen with divisions and countries. However I just don't use the rest
 * of the division data when getting the results of the query*/
    public static ObservableList select() throws SQLException {
        ObservableList<Customer> allCustomerList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Customers " +
                "LEFT JOIN first_level_divisions on customers.division_ID = first_level_divisions.Division_ID " +
                "LEFT JOIN countries on first_level_divisions.Country_ID = countries.Country_ID";
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
            String country = rs.getString("country");

            Customer customer = new Customer(customerId, customerName, address, postal, phone,
                    createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId, country);
            allCustomerList.add(customer);
        }
        return allCustomerList;
    }

/**
 * @param customerName
 * @param address
 * @param postal
 * @param phone
 * @param createdOn
 * @param createdBy
 * @param lastUpdate
 * @param updatedBy
 * @param divisionId
 *
 * This method allows the user to create new customers and add them to the database*/

    public static int addCustomer(String customerName, String address, String postal,
                                   String phone, LocalDateTime createdOn, String createdBy,
                                   LocalDateTime lastUpdate, String updatedBy, int divisionId) throws SQLException {
        String sql = "INSERT INTO Customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, " +
                "Last_Update, Last_Updated_By, Division_ID) VALUES (?, ?, ?, ?, now(), ?, now(), ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postal);
        ps.setString(4, phone);
        ps.setString(5, UserQuery.getLoggedInUser());
        ps.setString(6, "NA");
        ps.setInt(7, divisionId);

        int rowsAffected= ps.executeUpdate();
        return rowsAffected;
    }
/**
 * @param customerID
 * takes in a customer ID which comes from the customers page, and then deletes the customer with that ID*/
    public static int delete(int customerID) throws SQLException {
        String sql = "DELETE FROM Customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
/**
 * @param customerId
 *  * @param customerName
 *  * @param address
 *  * @param postal
 *  * @param phone
 *  * @param createdOn
 *  * @param createdBy
 *  * @param lastUpdate
 *  * @param updatedBy
 *  * @param divisionId
 *  * takes in all of the above parameters and updates the customer with the corresponding ID*/
    public static int updateCustomer(int customerId, String customerName, String customerAddress, String customerPostal,
                                     String customerPhone,
                                     LocalDateTime createdOn, String createdBy,
                                     LocalDateTime lastUpdate, String updatedBy, int divisionId) throws SQLException {
        String sql = "UPDATE Customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, " +
                "Last_Update = now(), Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, customerAddress);
        ps.setString(3, customerPostal);
        ps.setString(4, customerPhone);
        ps.setString(5, UserQuery.getLoggedInUser());
        ps.setInt(6, divisionId);
        ps.setInt(7, customerId);
        System.out.println(ps);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
/**
 * @param customerID
 * Takes in a customers ID and then returns the Customer and all of their data*/
    public static Customer getCustomerById(int customerID) throws SQLException {
        String sql = "SELECT * FROM Customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String customerName = rs.getString("Customer_Name");
            String customerAddress = rs.getString("Address");
            String postal = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionId = rs.getInt("Division_ID");

            Customer customer = new Customer(customerID, customerName, customerAddress, postal, phone,
                    createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId);
            return customer;
        }
        return null;
    }
}
