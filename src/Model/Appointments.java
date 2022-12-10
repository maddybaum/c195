package Model;

import java.sql.Timestamp;

public class Appointments {

    private int appointmentID;
    private String appointmentTitle;
    private String appointmentDescription;
    private String appointmentLocation;
    private String appointmentType;
    private Timestamp appointmentStart;
    private Timestamp  appointmentEnd;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdateTime;
    private String lastUpdateUser;
    private int customerId;
    private int userId;
    private int contactId;

    public Appointments(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation,
                        String appointmentType, Timestamp appointmentStart, Timestamp appointmentEnd, Timestamp createDate,
                        String createdBy, Timestamp lastUpdateTime, String lastUpdateUser, int customerId, int userId, int contactId) {
        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.appointmentStart = appointmentStart;
        this.appointmentEnd = appointmentEnd;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdateTime = lastUpdateTime;
        this.lastUpdateUser = lastUpdateUser;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }

    public String getAppointmentLocation() {
        return appointmentLocation;
    }

    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public Timestamp getAppointmentStart() {
        return appointmentStart;
    }

    public void setAppointmentStart(Timestamp appointmentStart) {
        this.appointmentStart = appointmentStart;
    }

    public Timestamp getAppointmentEnd() {
        return appointmentEnd;
    }

    public void setAppointmentEnd(Timestamp appointmentEnd) {
        this.appointmentEnd = appointmentEnd;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
