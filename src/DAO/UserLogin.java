package DAO;

import Model.User;
import helper.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserLogin extends User {


    public UserLogin(int userId, String username, String userPassword) {
        super(userId, username, userPassword);
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