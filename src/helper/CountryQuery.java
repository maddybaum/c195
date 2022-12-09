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

    public static ObservableList getCountryDivisions(String country) throws SQLException {

        ObservableList<String> divisionList = FXCollections.observableArrayList();
        String sql = "SELECT Division FROM first_level_divisions where Country_ID = (SELECT Country_ID from Countries WHERE Country = ?)";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, country);
        System.out.println(ps.toString());
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
//            int divisionID = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
//            int divisionCountryID = rs.getInt("Country_ID");
//            Divisions division = new Divisions(divisionID, divisionName, divisionCountryID);
            divisionList.add(divisionName);
        }
        System.out.println(divisionList);

        return divisionList;
    }
}
