package com.beakon.platty;

import android.icu.util.Calendar;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beakon on 3/10/2017.
 */

public class Event {

    private int hour, minute, year, month, day;
    private LatLng mLatLng;
    private String mEventName, mPlaceName, mHostId, mEventId;
    // TODO: 5/24/2017 Set up invites 
    private ArrayList<String> mInviteList;

    private boolean mPublic;

    // TODO: 4/5/2017 Set up invited guests and add inviting through contacts
    private List<String> mInvitedGuests;
    public Event() {
        mHostId = ParseUser.getCurrentUser().getObjectId();
        // TODO: 4/17/2017 change mEventId to use a real ID or remove if not necessary
        mEventId = "placeholderid";

        // Use the current time as default
        final Calendar c = Calendar.getInstance();

        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        c.get(Calendar.AM_PM);

        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DAY_OF_MONTH);

        mEventName = "defaultEventName";
        mPlaceName = "defaultPlaceName";
        mLatLng = new LatLng(-33.8523341, 151.2106085);
    }

    public Event(ParseObject event) {
        if (event.getClassName() == "Event") {
            mHostId = event.getString("hostId");
            mEventId = event.getString("eventId");
            mEventName = event.getString("eventName");
            mPlaceName = event.getString("placeName");

            ParseGeoPoint point = event.getParseGeoPoint("geoPoint");
            mLatLng = new LatLng(point.getLatitude(), point.getLongitude());

            hour = event.getInt("hour");
            minute = event.getInt("minute");
            year = event.getInt("year");
            month = event.getInt("month");
            day = event.getInt("day");
        } else {
            // Throw error is this is not of event class
            throw new IllegalArgumentException("ParseObject not of Event class");
        }
    }

    /**
     * Creates a new ParseObject for this class and saves it to the server.
     */
    public void saveAsNew() {
        ParseObject event = new ParseObject("Event");
        event.put("hostId", mHostId);
        event.put("eventId", mEventId);
        event.put("eventName", mEventName);
        event.put("placeName", mPlaceName);

        ParseGeoPoint point = new ParseGeoPoint(mLatLng.latitude, mLatLng.longitude);
        event.put("geoPoint", point);

        event.put("hour", hour);
        event.put("minute", minute);
        event.put("year", year);
        event.put("month", month);
        event.put("day", day);

        event.saveInBackground();
    }

    public boolean isPublic() {
        return mPublic;
    }

    public void setPublic(boolean mPublic) {
        this.mPublic = mPublic;
    }

    public String getHostId() {
        return mHostId;
    }

    public void setHostId(String hostId) {
        mHostId = hostId;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public LatLng getLatLng() {
        return mLatLng;
    }

    public void setLatLng(LatLng mLatLng) {
        this.mLatLng = mLatLng;
    }

    public String getEventName() {
        return mEventName;
    }

    public void setEventName(String mEventName) {
        this.mEventName = mEventName;
    }

    public String getPlaceName() {
        return mPlaceName;
    }

    public void setPlaceName(String mPlaceName) {
        this.mPlaceName = mPlaceName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month + 1;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}