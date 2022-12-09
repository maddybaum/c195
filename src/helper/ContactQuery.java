package helper;

import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactQuery {

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

    public static int getContactIDByName(String contactName) throws SQLException {
        String sql = "SELECT * FROM contacts WHERE Contact_Name = ? ";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, contactName);
        ResultSet rs = ps.executeQuery();
        int contactIdStart = 0;

        while(rs.next()){
            int contactId = rs.getInt("Contact_ID");
            contactIdStart = contactId;
        }
        return contactIdStart;
    }
}
