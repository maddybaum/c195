package helper;

import Model.User;
import helper.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class UserLogin extends User {


    public UserLogin(int userId, String username, String userPassword, Timestamp createDate, String createdBy, Timestamp lastUpdateDate, String lastUpdatedBy) {
        super(userId, username, userPassword, createDate, createdBy, lastUpdateDate, lastUpdatedBy);
    }


    public static boolean checkCredentials(String usernameInput, String passwordInput) throws SQLException {
        String checkLogin = "SELECT * FROM users WHERE User_Name = '" + usernameInput +"' AND Password = '" + passwordInput + "'";

        try {
            Statement statement = JDBC.getConnection().prepareStatement(checkLogin);
            ResultSet rs = statement.executeQuery(checkLogin);

            if(rs.next()){
                return true;

            } else {
                return false;
            }
        } catch(SQLException e) {
        e.printStackTrace();
    }
        return false;
}}