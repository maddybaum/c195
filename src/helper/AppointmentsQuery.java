package helper;

import Model.Appointments;
import Model.Customer;
import com.mysql.cj.jdbc.JdbcConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import javax.xml.transform.Result;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

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
        ps.setString(7, UserQuery.getLoggedInUser());
//        ps.setTimestamp(10, Timestamp.valueOf(lastUpdated));
        ps.setString(8, "NA");
        ps.setInt(9, customerId);
        ps.setInt(10, userId);
        ps.setInt(11, contactId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static ObservableList<Appointments> checkForApptIn15() throws SQLException {
        ObservableList<Appointments> appointmentsIn15List = FXCollections.observableArrayList();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime in15mins = LocalDateTime.now().plusMinutes(15);

        String sql = "SELECT * FROM Appointments WHERE Start BETWEEN ? AND ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setTimestamp(1, Timestamp.valueOf(currentDateTime));
        ps.setTimestamp(2, Timestamp.valueOf(in15mins));

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
            appointmentsIn15List.add(appointments);
        }
        return appointmentsIn15List;
    }

    public static int update(int appointmentId, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd,
             String createdBy, LocalDateTime lastUpdated, String updatedBy, int customerId, int userId, int contactId) throws SQLException {
        String sql = "UPDATE APPOINTMENTS SET Title = ?,  Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
                "Last_Update = now(), Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";


        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, appointmentTitle);
        ps.setString(2, appointmentDescription);
        ps.setString(3, appointmentLocation);
        ps.setString(4, appointmentType);
        ps.setTimestamp(5, Timestamp.valueOf(appointmentStart));
        ps.setTimestamp(6, Timestamp.valueOf(appointmentEnd));
//        ps.setTimestamp(7, Timestamp.valueOf(appointmentCreateDate));
//        ps.setString(8, createdBy);
        ps.setString(7, UserQuery.getLoggedInUser());
        ps.setInt(8, customerId);
        ps.setInt(9, userId);
        ps.setInt(10, contactId);
        ps.setInt(11, appointmentId);
        System.out.println(ps);

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

    public static ObservableList<Appointments> select() throws SQLException {
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

    public static ObservableList selectAllByMonth(int month) throws SQLException {
        ObservableList<Appointments> appointmentsByMonth = FXCollections.observableArrayList();
//        try {
        String sql = "SELECT * FROM Appointments WHERE MONTH(Start) = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, month);
        ResultSet rs = ps.executeQuery();
        System.out.println(ps);
        while (rs.next()) {
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
            appointmentsByMonth.add(appointments);
        }
        return appointmentsByMonth;

    }

    public static ObservableList<Appointments> getApptsByCustomer(int customerId) throws SQLException {
        ObservableList<Appointments> appointmentsByCustomer = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        ResultSet rs = ps.executeQuery();
        System.out.println(ps);
        while (rs.next()) {
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
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");

            Appointments appointments = new Appointments(appointmentId, appointmentTitle, appointmentDescription,
                    appointmentLocation, appointmentType, appointmentStart, appointmentEnd, createDate, createdBy, lastUpdated, lastUpdatedBy,
                    customerId, userId, contactId);
            appointmentsByCustomer.add(appointments);
        }
        return appointmentsByCustomer;
    }
    public static ObservableList<Appointments> getAllApptsByCurrentWeek() throws SQLException {
        ObservableList<Appointments> appointmentsByCurrentWeek = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Appointments WHERE week(Start) = week(now())";
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
            appointmentsByCurrentWeek.add(appointments);
        }
        return appointmentsByCurrentWeek;
        }

    public static ObservableList<Appointments> getApptsByUser(int userID) throws SQLException {
        ObservableList<Appointments> appointmentsByUser = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Appointments WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, userID);
        ResultSet rs = ps.executeQuery();
        System.out.println(ps);
        while (rs.next()) {
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
            int customerId = rs.getInt("Customer_ID");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");

            Appointments appointments = new Appointments(appointmentId, appointmentTitle, appointmentDescription,
                    appointmentLocation, appointmentType, appointmentStart, appointmentEnd, createDate, createdBy, lastUpdated, lastUpdatedBy,
                    customerId, userID, contactId);
            appointmentsByUser.add(appointments);
        }
        return appointmentsByUser;
    }


    public static ObservableList<Appointments> getAppointmentByMonthAndType(int month, String type) throws SQLException {
        ObservableList<Appointments> appointmentsByMonthType = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Appointments WHERE MONTH(Start) = ? AND Type = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, month);
        ps.setString(2, type);
        ResultSet rs = ps.executeQuery();
        System.out.println(ps);
        while (rs.next()) {
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
            int customerId = rs.getInt("Customer_ID");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");

            Appointments appointments = new Appointments(appointmentId, appointmentTitle, appointmentDescription,
                    appointmentLocation, appointmentType, appointmentStart, appointmentEnd, createDate, createdBy, lastUpdated, lastUpdatedBy,
                    customerId, userId, contactId);
            appointmentsByMonthType.add(appointments);
        }
        return appointmentsByMonthType;
    }
    }


