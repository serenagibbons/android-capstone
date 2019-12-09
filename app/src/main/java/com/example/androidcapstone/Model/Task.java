package com.example.androidcapstone.Model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Task {
    //Variables - 'F' means to follow this format
    private String m_Creator; //Email of Creator
    private Boolean m_Accepted; //Email of Creator
    private String m_AssignedTo; //Email of people assigned to, can have multiple
    private String m_TaskName; //Name of task
    private String m_TaskDescription; //Description of task
    private String m_Importance; //[F - 1, 2, 3]
    private @ServerTimestamp Date m_DueDate; //The day to be completed on [F - Mon, Tue, Wed, Thur, Fri, Sat, Sun]
    private @ServerTimestamp Date m_CreatedOnDate; //[F - mm/dd/yy] AutoImplemented if null
    private String m_Status; //[F Complete || Incomplete]
    private String m_Location; //Geolocation (API Possibly) that gives coordinates
    private String m_Privacy; //Public or Private

    // default constructor
    public Task ()
    {}

    // parameterized constructor
    public Task(String m_Creator, Boolean m_Accepted, String m_AssignedTo, String m_TaskName, String m_TaskDescription,
                String m_Importance, String m_Location, Date m_DueDate, String m_Status, String m_Privacy)
    {
        this.m_Creator = m_Creator;
        this.m_Accepted = m_Accepted;
        this.m_AssignedTo = m_AssignedTo;
        this.m_TaskName = m_TaskName;
        this.m_TaskDescription = m_TaskDescription;
        this.m_Importance = m_Importance;
        this.m_DueDate = m_DueDate;
        this.m_Status = m_Status;
        this.m_Location = m_Location;
        this.m_Privacy = m_Privacy;
    }


    public Boolean getM_Accepted() { return m_Accepted; }
    public void setM_Accepted(Boolean m_Accepted) { this.m_Accepted = m_Accepted; }

    public String getM_Location() {
        return m_Location;
    }

    public void setM_Location(String m_Location) {
        this.m_Location = m_Location;
    }

    public String getM_Privacy() {
        return m_Privacy;
    }

    public void setM_Privacy(String m_Privacy) {
        this.m_Privacy = m_Privacy;
    }

    public String getM_Creator() {
        return m_Creator;
    }

    public void setM_Creator(String m_Creator) {
        this.m_Creator = m_Creator;
    }

    public String getM_AssignedTo() {
        return m_AssignedTo;
    }

    public void setM_AssignedTo(String m_AssignedTo) {
        this.m_AssignedTo = m_AssignedTo;
    }

    public String getM_TaskName() {
        return m_TaskName;
    }

    public void setM_TaskName(String m_TaskName) {
        this.m_TaskName = m_TaskName;
    }

    public String getM_TaskDescription() {
        return m_TaskDescription;
    }

    public void setM_TaskDescription(String m_TaskDescription) {
        this.m_TaskDescription = m_TaskDescription;
    }

    public String getM_Importance() {
        return m_Importance;
    }

    public void setM_Importance(String m_Importance) {
        this.m_Importance = m_Importance;
    }

    public Date getM_DueDate() {
        return m_DueDate;
    }
    public void setM_DueDate(Date m_DueDate) {
        this.m_DueDate = m_DueDate;
    }

    public Date getM_CreatedOnDate() {
        return m_CreatedOnDate;
    }
    public void setM_CreatedOnDate(Date m_CreatedOnDate) {
        this.m_CreatedOnDate = m_CreatedOnDate;
    }

    public String getM_Status() {
        return m_Status;
    }

    public void setM_Status(String m_Status) {
        this.m_Status = m_Status;
    }
}