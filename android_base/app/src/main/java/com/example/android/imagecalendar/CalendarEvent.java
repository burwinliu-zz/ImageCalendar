package com.example.android.imagecalendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Time;

public class CalendarEvent {
    String title = "";
    String description = "";
    //String startTime = "";
    Time startTime = new Time(00, 00, 00);
    //String endTime = "";
    Time endTime = new Time(00, 00, 00);
    //String date = "";
    Date date = new Date(19, 10, 00);

    public CalendarEvent(JSONObject json) {

    }

    public void setTitle(String tt) {
        this.title = tt;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public Time getStartTime() {
        //TODO
        //Time time = new Time(00,00, 00);
        return this.startTime;
    }

    public void setStartTime(String s) {
        //TODO
        if (s.matches("\\d\\d:\\d\\d:\\d\\d")) {
            this.startTime = Time.valueOf(s);
        }
    }

    public void setStartTime(Time s) {
        this.startTime = s;
    }

    public Time getEndTime() {
        //TODO
        // if nothing, then set automatically to 1 hr after getStartTime IF getStartTime != 00:00:00
        Time time = new Time(00,00, 00);
        return time;
    }

    public void setEndTime(String s) {
        //TODO
    }

    public Date getDate() {
        //TODO
        Date date = new Date(00, 00, 00);
        return date;
    }

    public void setDate(String s) {
        //TODO
        if (s.contains("Jan") || s.contains("January")) {

        } else if (s.contains("Feb")) {

        } else if (s.contains("Mar")) {

        } else if (s.contains("Apr")) {

        } else if (s.contains("May")) {

        } else if (s.contains("Jun")) {

        } else if (s.contains ("Jul")) {

        } else if (s.contains("Aug")) {

        } else if (s.contains("Sep")) {

        } else if (s.contains("Oct")) {

        } else if (s.contains("Nov")) {

        } else if (s.contains("Dec")) {

        }

    }
}
