package com.example.android.imagecalendar;

import java.sql.Date;
import java.sql.Time;

public class CalendarEvent {
    String title = "";
    String description = "";
    String startTime = "";
    String endTime = "";
    String date = "";

    public static void main(String[] args) {
        CalendarEvent event = new CalendarEvent();
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
        Time time = new Time(00,00, 00);
        return time;
    }

    public void setStartTime(String s) {
        //TODO
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
    }
}
