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
            res = new JSONObject("{\"description\":[\"A sensory- sory-friendly movie night for\",\"people on the spectrum\",\"April 2, 2019 6PM\",\"San Dias Community Hall\",\"Community Hall is wheelchair-accessible.\",\"SAN DIAS COMMUNLTY CENTER\"],\"times\":[\"Tue, 02 Apr 2019 18:00:00 GMT\",\"Tue, 02 Apr 2019 18:00:00 GMT\"],\"title\":\"Lights Up, Sound Down \"}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ce = new CalendarEvent(res);
    }

    public void addEvent(View view){

    }
}
