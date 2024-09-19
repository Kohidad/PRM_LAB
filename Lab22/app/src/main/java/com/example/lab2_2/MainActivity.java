package com.example.lab2_2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView username;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.main_username);
        //Retrieve the passed info.
        Intent intent = getIntent();
        if (username != null){
            username.setText(intent.getStringExtra("USERNAME"));
            Log.i("SUCCESS","User: " + intent.getStringExtra("USERNAME") + " logged into the system!");
        } else {
            Log.i("ERROR", "Something went wrong at " + this);
        }
    }
}
