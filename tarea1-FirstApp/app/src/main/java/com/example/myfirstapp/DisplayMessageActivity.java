package com.example.myfirstapp;

import android.content.Intent;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String message2 = intent.getStringExtra("com.example.myfirstapp.MESSAGE2");
        String message3 = intent.getStringExtra("com.example.myfirstapp.MESSAGE3");

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
        TextView textView2 = findViewById(R.id.textView2);
        textView2.setText(message2);
        TextView textView3 = findViewById(R.id.textView3);
        textView3.setText(message3);
    }

}