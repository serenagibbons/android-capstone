package com.example.androidcapstone.Model;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class UserLocation {

    private String m_UserID;
    private @ServerTimestamp Date m_TimeStamp;
    private GeoPoint m_GeoLocation; //Used for tracking the users location

    public UserLocation(String m_UserID, Date m_TimeStamp, GeoPoint m_GeoLocation) {
        this.m_UserID = m_UserID;
        this.m_TimeStamp = m_TimeStamp;
        this.m_GeoLocation = m_GeoLocation;
    }

    public UserLocation() {

    }

    public String getM_UserID() {
        return m_UserID;
    }

    public void setM_UserID(String m_UserID) {
        this.m_UserID = m_UserID;
    }

    public Date getM_TimeStamp() {
        return m_TimeStamp;
    }

    public void setM_TimeStamp(Date m_TimeStamp) {
        this.m_TimeStamp = m_TimeStamp;
    }

    public GeoPoint getM_GeoLocation() {
        return m_GeoLocation;
    }

    public void setM_GeoLocation(GeoPoint m_GeoLocation) {
        this.m_GeoLocation = m_GeoLocation;
    }

    @Override
    public String toString() {
        return "UserLocation{" +
                "m_UserID='" + m_UserID + '\'' +
                ", m_TimeStamp=" + m_TimeStamp +
                ", m_GeoLocation=" + m_GeoLocation +
                '}';
    }
}
