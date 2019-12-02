package com.example.androidcapstone.Model;

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
    String m_Creator; //Email of Creator
    String [] m_AssignedTo; //Email of people assigned to, can have multiple
    String m_TaskName; //Name of task
    String m_TaskDescription; //Description of task
    String m_Importance; //[F - Low, Medium, High]
    String m_DueDate; //The day to be completed on [F - Mon, Tue, Wed, Thur, Fri, Sat, Sun]
    String m_Frequency; //[F - Once, Weekly, BiWeekly]
    Date m_CreatedOnDate; //[F - mm/dd/yy]
    Date m_Status; //[F Complete || Incomplete]
    double m_Location; //Geolocation (API Possibly) that gives coordinates
}
