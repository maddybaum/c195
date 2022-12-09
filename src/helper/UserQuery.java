package helper;

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UserQuery {

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
        return alluserList;
    }

    public static String getNameByID(int userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE User_Id = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        String userName = "";
        while(rs.next()){
            userName = rs.getString("User_Name");
        }
        return userName;
    }
}
