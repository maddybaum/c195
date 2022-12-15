package helper;

import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactQuery {

    /**Query to select all the contacts within the DB and return them in a list*/
    public static ObservableList select() throws SQLException {
        ObservableList<Contact> allContactList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM CONTACTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");

            Contact contact = new Contact(contactId, contactName, contactEmail);
            allContactList.add(contact);
        }
        return allContactList;
    }
    /**@param contactID
     * Used to get the whole contact from their ID*/
    public static Contact getContactById(int contactID) throws SQLException {
        String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactID);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            Contact contact = new Contact(contactID, contactName, contactEmail);
            return contact;
        }
        return null;
    }
}
