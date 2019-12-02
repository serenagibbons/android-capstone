package com.example.androidcapstone.Model;

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
    //int m_Id; //Unique ID MAY NOT NEED
    private String m_Creator; //Email of Creator
    private String [] m_AssignedTo; //Email of people assigned to, can have multiple
    private String m_TaskName; //Name of task
    private String m_TaskDescription; //Description of task
    private String m_Importance; //[F - Low, Medium, High]
    private String m_DueDate; //The day to be completed on [F - Mon, Tue, Wed, Thur, Fri, Sat, Sun]
    private String m_Frequency; //[F - Once, Weekly, BiWeekly]
    private Date m_CreatedOnDate; //[F - mm/dd/yy]
    private String m_Status; //[F Complete || Incomplete]
    private String m_PostedTime; //[F - hh:mm:ss]
    //double m_Location; //Geolocation (API Possibly) that gives coordinates

    //Default Dummy Constructor for testing purposes
    public Task ()
    {
        this.m_Creator = "Dummy Data";
        this.m_AssignedTo = new String[]{"@Dummy"};
        this.m_TaskName = "Dummy Task";
        this.m_TaskDescription = "Dummy Description";
        this.m_Importance = "HIGH";
        this.m_DueDate = "13/13/13";
        this.m_Frequency = "Once";
        this.m_CreatedOnDate = new Date(11/11/11);
        this.m_Status = "COMPLETE";
        //Get current time
        Date d = new Date();
        String dateFormat = "hh:mm:ss a";
        DateFormat df = new SimpleDateFormat(dateFormat);
        this.m_PostedTime = df.format(d);
    }

    public String getM_PostedTime() {
        return m_PostedTime;
    }

    public void setM_PostedTime(String m_PostedTime) {
        this.m_PostedTime = m_PostedTime;
    }

    //Parameterized Constructor
    public Task(String m_Creator, String[] m_AssignedTo, String m_TaskName, String m_TaskDescription, String m_Importance, String m_DueDate, String m_Frequency, Date m_CreatedOnDate, String m_Status)
    {
        this.m_Creator = m_Creator;
        this.m_AssignedTo = m_AssignedTo;
        this.m_TaskName = m_TaskName;
        this.m_TaskDescription = m_TaskDescription;
        this.m_Importance = m_Importance;
        this.m_DueDate = m_DueDate;
        this.m_Frequency = m_Frequency;
        this.m_CreatedOnDate = m_CreatedOnDate;
        this.m_Status = m_Status;
        //Get current time
        Date d = new Date();
        String dateFormat = "hh:mm:ss a";
        DateFormat df = new SimpleDateFormat(dateFormat);
        this.m_PostedTime = df.format(d);
    }

    public String getM_Creator() {
        return m_Creator;
    }

    public void setM_Creator(String m_Creator) {
        this.m_Creator = m_Creator;
    }

    public String[] getM_AssignedTo() {
        return m_AssignedTo;
    }

    public void setM_AssignedTo(String[] m_AssignedTo) {
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

    public String getM_DueDate() {
        return m_DueDate;
    }

    public void setM_DueDate(String m_DueDate) {
        this.m_DueDate = m_DueDate;
    }

    public String getM_Frequency() {
        return m_Frequency;
    }

    public void setM_Frequency(String m_Frequency) {
        this.m_Frequency = m_Frequency;
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
