package helper;

import Model.Countries;
import Model.Divisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CountryQuery {

    /**method to select all of the countries within the Countries table and return them in a list. This is used to populate the country options for when a user is creating a customer*/
    public static ObservableList select() throws SQLException {
        ObservableList<Countries> allCountryList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int countryId = rs.getInt("Country_ID");
            String country = rs.getString("Country");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");

            Countries countries = new Countries(countryId, country, createDate, createdBy, lastUpdate, lastUpdatedBy);
            allCountryList.add(countries);
        }
        return allCountryList;
    }
/**@param country
 * this takes in a country as a string and then matches that country to its ID in the countries table
 * it then finds that country id from the first_level_divisions table to return the division
 * I got help creating the method during a LIS session*/
    public static ObservableList getCountryDivisions(String country) throws SQLException {

        ObservableList<Divisions> divisionList = FXCollections.observableArrayList();
        String sql = "SELECT Division FROM first_level_divisions where Country_ID = (SELECT Country_ID from Countries WHERE Country = ?)";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, country);
        System.out.println(ps.toString());
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
//            int divisionID = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
//            int divisionCountryID = rs.getInt("Country_ID");
            Divisions division = new Divisions(divisionName);
            divisionList.add(division);
        }
        System.out.println(divisionList);

        return divisionList;
    }
    /**
     * @param division
     * takes in a divisions name and matches it to the division, because when user is selecting a division they are selecting it using its name*/
    public static int getDivisionByName(String division) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Division = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, division);
        ResultSet rs = ps.executeQuery();
        int divisionId = 0;

        while(rs.next()){
            divisionId = rs.getInt("Division_ID");
        }
        return divisionId;
    }
/**
 * @param divisionID
 * takes in a division ID and then returns an instance of a division*/
    public static Divisions getDivisionByID(int divisionID) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionID);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String divisionName = rs.getString("Division");
            int countryID = rs.getInt("Country_ID");
            Divisions division = new Divisions(divisionID, divisionName, countryID);
            return division;
        }
       return null;
    }
/**@param divisionID
 * takes in a division ID and then finds the country with that division. I followed the same format as the method to find the country's divisions for this one*/
    public static Countries getCountryByDivision(int divisionID) throws SQLException {
        String sql = "SELECT Country FROM Countries WHERE Country_ID = (SELECT Country_ID from First_Level_Divisions WHERE Division_ID = ?)";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionID);
        System.out.println(ps.toString());
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            String countryName = rs.getString("Country");
            Countries country = new Countries(countryName);
            System.out.println(country);

            return country;
        }

        return null;
    }
}
