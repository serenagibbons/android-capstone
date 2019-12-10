package com.example.androidcapstone.Model;

import java.util.List;

public class User {

    // member variables
    private String m_UserID;
    private String m_Avatar;
    private String m_Email;
    private String m_FirstName;
    private String m_LastName;
    private List<String> Contacts;

    // constructors
    public User(){}

    public User(String m_UserID, String m_Avatar, String m_Email, String m_FirstName, String m_LastName) {
        this.m_UserID = m_UserID;
        this.m_Avatar = m_Avatar;
        this.m_Email = m_Email;
        this.m_FirstName = m_FirstName;
        this.m_LastName = m_LastName;
    }

    // getters and setters
    public List<String> getContacts() {
        return Contacts;
    }

    public void setContacts(List<String> contacts) {
        Contacts = contacts;
    }

    public String getM_FirstName() {
        return m_FirstName;
    }

    public void setM_FirstName(String m_FirstName) {
        this.m_FirstName = m_FirstName;
    }

    public String getM_LastName() {
        return m_LastName;
    }

    public void setM_LastName(String m_LastName) {
        this.m_LastName = m_LastName;
    }

    public String getM_Email() {
        return m_Email;
    }

    public void setM_Email(String m_Email) {
        this.m_Email = m_Email;
    }

    public String getM_UserID() {
        return m_UserID;
    }

    public void setM_UserID(String m_UserID) {
        this.m_UserID = m_UserID;
    }

    public String getM_Avatar() {
        return m_Avatar;
    }

    public void setM_Avatar(String m_Avatar) {
        this.m_Avatar = m_Avatar;
    }


}
