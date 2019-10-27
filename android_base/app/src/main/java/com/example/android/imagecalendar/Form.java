package com.example.android.imagecalendar;

import android.content.Intent;
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

        Intent intent = getIntent();
        String message = intent.getStringExtra("photo_file");

        m_title=(EditText) findViewById(R.id.title);
        m_date=(EditText) findViewById(R.id.date);
        m_startTime=(EditText) findViewById(R.id.startTime);
        m_endTime=(EditText) findViewById(R.id.endTime);
        m_description=(EditText) findViewById(R.id.description);

        m_title.setText(message);
    }

    public void addEvent(View view){

    }
}
