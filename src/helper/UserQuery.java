package helper;

import Model.User;
import com.mysql.cj.protocol.Resultset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UserQuery {

    public static String loggedInUser;

    public static String getLoggedInUser() {
        return loggedInUser;
    }
/**
 * @param username
 * takes in a username and then returns the whole user */
    public static User getUserUser(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE USER_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int userId = rs.getInt("User_ID");
            String currentUsername = rs.getString("User_Name");
            String currentPassword = rs.getString("Password");

            User user = new User(userId, username, currentPassword);
            return user;
        }
        return null;
    }
    public static void setLoggedInUser(String loggedInUser) {
        UserQuery.loggedInUser = loggedInUser;
    }
/**
 * queries the database for all of its users
 * this is used to populate the combo boxes for user when adding or modifying an appointment*/
    public static ObservableList select() throws SQLException {
        ObservableList<User> alluserList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM USERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int userId = rs.getInt("User_ID");
            String username = rs.getString("User_Name");
            String password = rs.getString("Password");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");

            User user = new User(userId, username, password, createDate, createdBy, lastUpdate, lastUpdatedBy);

            alluserList.add(user);
        }
        System.out.println("USER LIST" + alluserList);
        return alluserList;
    }

/**@param userID
 * takes in a userID and then returns the whole user*/
    public static User getUserByID(int userID) throws SQLException {
        String sql = "SELECT * FROM users WHERE User_Id = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, userID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String username = rs.getString("User_Name");
            String password = rs.getString("Password");

            User user = new User(userID, username, password);
            return user;

        }
        return null;
    }
/**
 * @param username
 * @param password
 * takes in a username and password and looks to see whether there is a matching username and password within the database. If there is it will return true, if not it returns false*/
    public static boolean checkLogin(String username, String password){
        try{
            String sql = "SELECT * FROM users WHERE USER_Name = ? AND Password = ?;";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int userId = rs.getInt("User_ID");
                String currentUsername = rs.getString("User_Name");
                String currentPassword = rs.getString("Password");

                setLoggedInUser(username);
                return true;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
