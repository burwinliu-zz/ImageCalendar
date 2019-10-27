package com.example.android.imagecalendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Form extends AppCompatActivity {
    EditText m_title, m_date, m_startTime, m_endTime, m_description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        m_title=(EditText) findViewById(R.id.title);
        m_date=(EditText) findViewById(R.id.date);
        m_startTime=(EditText) findViewById(R.id.startTime);
        m_endTime=(EditText) findViewById(R.id.endTime);
        m_description=(EditText) findViewById(R.id.description);
    }

    public void addEvent(View view){

    }
}
