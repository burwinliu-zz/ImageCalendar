package com.example.android.imagecalendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Form extends AppCompatActivity {
    EditText m_title, m_date, m_startTime, m_endTime, m_description;
    CalendarEvent ce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        Intent intent = getIntent();
        String photo_file = intent.getStringExtra("photo_file");
        /*Process data from photo file*/

        m_title=(EditText) findViewById(R.id.title);
        m_date=(EditText) findViewById(R.id.date);
        m_startTime=(EditText) findViewById(R.id.startTime);
        m_endTime=(EditText) findViewById(R.id.endTime);
        m_description=(EditText) findViewById(R.id.description);

        JSONObject res = null;
        try {
            res = new JSONObject("");
            JSONArray arr = null;
            try {
                arr = res.getJSONArray("description");
            } catch (Exception e) {
            }
            String desc="";
            for(int i=0; i<arr.length(); i++){
                try {
                    desc+=(arr.getString(i)+"\n");
                } catch (Exception e) {
                }
            }
            ce.setDescription(desc);

            try {
                ce.setTitle(res.getString("title"));
            } catch (Exception e) {
            }
            try {
                ce.setStartTime(res.getJSONArray("times").getString(0));
            } catch (Exception e) {
            }
            //TODO: Parse string into date, start time
            try {
                ce.setEndTime(res.getJSONArray("times").getString(1));
            } catch (Exception e) {
            }
            //TODO: Parse string into date, end time

        } catch (Exception e) {
        }


    }

    public void addEvent(View view){

    }
}
