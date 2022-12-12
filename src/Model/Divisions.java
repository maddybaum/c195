package Model;

import javafx.beans.value.ObservableValue;

import java.sql.Timestamp;

public class Divisions {
    private int divisionId;

    private String division;
    private ObservableValue<String> divisionValue;


    private int countryId;


    public Divisions(int divisionId, String division, ObservableValue divisionValue, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.divisionValue = divisionValue;
        this.countryId = countryId;
    }

    public Divisions(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

    public ObservableValue<String> getDivisionValue() {
        return divisionValue;
    }

    public ObservableValue<String> divisionValueProperty() {
        return divisionValue;
    }

    public Divisions(String division){
        this.division = division;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }


    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    @Override
    public String toString(){
        return division;
    }
}
