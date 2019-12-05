package com.example.androidcapstone.Model;

import com.google.firebase.firestore.ServerTimestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
    Created By: Kevin Wang
    Date: 11/30/19
    Purpose: Used as an object of Tasks, each task has given properties. If a variable
    has a comment, the user inputs should be consistent with the comment in order for the
    database to match

 */
public class Task {
    //Variables - 'F' means to follow this format
    private String m_Creator; //Email of Creator
    private String m_AssignedTo; //Email of people assigned to, can have multiple
    private String m_TaskName; //Name of task
    private String m_TaskDescription; //Description of task
    private String m_Importance; //[F - Low, Medium, High]
    private Date m_DueDate; //The day to be completed on [F - Mon, Tue, Wed, Thur, Fri, Sat, Sun]
    private @ServerTimestamp Date m_CreatedOnDate; //[F - mm/dd/yy] AutoImplemented if null
    private String m_Status; //[F Complete || Incomplete]
    private String m_Location; //Geolocation (API Possibly) that gives coordinates
    private String m_Privacy; //Public or Private

    //Default Dummy Constructor for testing purposes
    public Task ()
    {

    }

    //Parameterized Constructor
    public Task(String m_Creator, String m_AssignedTo, String m_TaskName, String m_TaskDescription,
                String m_Importance, String m_Location, Date m_DueDate, String m_Status, String m_Privacy)
    {
        this.m_Creator = m_Creator;
        this.m_AssignedTo = m_AssignedTo;
        this.m_TaskName = m_TaskName;
        this.m_TaskDescription = m_TaskDescription;
        this.m_Importance = m_Importance;
        this.m_DueDate = m_DueDate;
        this.m_Status = m_Status;
        this.m_Location = m_Location;
        this.m_Privacy = m_Privacy;
    }

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

    //public String getM_Frequency() {
   //     return m_Frequency;
//    }
//
//    public void setM_Frequency(String m_Frequency) {
//        this.m_Frequency = m_Frequency;
//    }

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
