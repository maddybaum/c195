package helper;

import Model.Appointments;
import com.mysql.cj.jdbc.JdbcConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import javax.xml.transform.Result;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class AppointmentsQuery {

    public static TextField appointmentId;
    public static TextField appointmentTitleInput;
    public static TextField descriptionInput;
    public static TextField locationInput;
    public static TextField typeInput;
    public static DatePicker addCustomerStart;
    public static ComboBox addApptCustomerBox;
    public static ComboBox addApptUserBox;
    public static ComboBox addStartTimeBox;
    public static ComboBox addEndTimeBox;


    public static int insert(String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType,
                             LocalDateTime appointmentStart, LocalDateTime appointmentEnd,
                             int customerId, int userId, int contactId)
            throws SQLException {


        String sql = "INSERT INTO APPOINTMENTS (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES ( ?, ?, ?, ?, ?, ?, now(), ?, now(), ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, appointmentTitle);
        ps.setString(2, appointmentDescription);
        ps.setString(3, appointmentLocation);
        //date and time are in the same column however have 2 different inputs in my fxml
        ps.setString(4, appointmentType);
        ps.setTimestamp(5, Timestamp.valueOf(appointmentStart));
        ps.setTimestamp(6, Timestamp.valueOf(appointmentEnd));
//        ps.setTimestamp(8, Timestamp.valueOf(appointmentCreateDate));
        ps.setString(7, UserLogin.getUsername());
//        ps.setTimestamp(10, Timestamp.valueOf(lastUpdated));
        ps.setString(8, "NA");
        ps.setInt(9, customerId);
        ps.setInt(10, userId);
        ps.setInt(11, contactId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int update(String appointmentTitle, String appointmentDescription, String appointmentLocation, Timestamp appointmentStart, Timestamp appointmentEnd, Timestamp appointmentCreateDate, String createdBy, Timestamp lastUpdated, int customerId, int userId, int contactId) throws SQLException {
        String sql = "UPDATE APPOINTMENTS SET appointmentTitle = ? WHERE Appointment_ID = ?";

        //need to finish this
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    public static int delete (int appointmentId) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static ObservableList select() throws SQLException {
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int appointmentId = rs.getInt("Appointment_ID");
            String appointmentTitle = rs.getString("Title");
            String appointmentDescription = rs.getString("Description");
            String appointmentLocation = rs.getString("Location");
            String appointmentType = rs.getString("Type");
            Timestamp appointmentStart = rs.getTimestamp("Start");
            Timestamp appointmentEnd = rs.getTimestamp("End");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdated = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");

            Appointments appointments = new Appointments(appointmentId, appointmentTitle, appointmentDescription,
                    appointmentLocation, appointmentType, appointmentStart, appointmentEnd, createDate, createdBy, lastUpdated, lastUpdatedBy,
                    customerId, userId, contactId);
            appointmentsList.add(appointments);
            System.out.print(appointmentId + "      ");
            System.out.print(appointmentTitle + "     ");
            System.out.println(appointmentDescription);

        }
        System.out.println(appointmentsList);
        return appointmentsList;

    }
}
