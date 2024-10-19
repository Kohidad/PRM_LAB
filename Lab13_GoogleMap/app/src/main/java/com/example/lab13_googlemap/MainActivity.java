package com.example.lab13_googlemap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnOpenMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOpenMap = findViewById(R.id.btnOpenMap);
        btnOpenMap.setOnClickListener(view -> {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        });
    }
}
