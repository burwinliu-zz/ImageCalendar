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

    public CalendarEvent() {}

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

    public Time getStartTime() {  return this.startTime;   }

    public void setStartTime(String s) {
        //TODO
        String sarr[] = s.split(" ");
        for (int i = 0; i < sarr.length; i++) {
            if (sarr[i].matches("\\d\\d:\\d\\d:\\d\\d")) {
                this.startTime = Time.valueOf(sarr[i]);
            }
        }
    }

    public void setStartTime(Time s) {
        this.startTime = s;
    }

    public Time getEndTime() { return this.endTime;  }

    public void setEndTime(String s) {
        String sarr[] = s.split(" ");
        for (int i = 0; i < sarr.length; i++) {
            if (sarr[i].matches("\\d\\d:\\d\\d:\\d\\d")) {
                this.endTime = Time.valueOf(sarr[i]);
            }
        }
    }
    public void setEndTime(Time s) { this.endTime = s; }

    public Date getDate() { return this.date;  }

    public void setDate(String s) {
        //TODO
        s.replaceAll("\\d\\d:\\d\\d:\\d\\d", "");
        String[] sarr = s.split(" ");
        for (int i = 0; i < sarr.length; i++) {
            try {
                int j = Integer.valueOf(sarr[i]);

                if(j < 32) {
                    this.date.setDate(j); // date. worst case scenario it's a month
                } else {
                    this.date.setYear(j); // def a year. maybe an error lmao.
                }
            } catch(Exception e) {

            }
        }
        if (s.contains("Jan")) {
            this.date.setMonth(1);
        } else if (s.contains("Feb")) {
            this.date.setMonth(2);
        } else if (s.contains("Mar")) {
            this.date.setMonth(3);
        } else if (s.contains("Apr")) {
            this.date.setMonth(4);
        } else if (s.contains("May")) {
            this.date.setMonth(5);
        } else if (s.contains("Jun")) {
            this.date.setMonth(6);
        } else if (s.contains ("Jul")) {
            this.date.setMonth(7);
        } else if (s.contains("Aug")) {
            this.date.setMonth(8);
        } else if (s.contains("Sep")) {
            this.date.setMonth(9);
        } else if (s.contains("Oct")) {
            this.date.setMonth(10);
        } else if (s.contains("Nov")) {
            this.date.setMonth(11);
        } else if (s.contains("Dec")) {
            this.date.setMonth(12);
        }

    }
}
