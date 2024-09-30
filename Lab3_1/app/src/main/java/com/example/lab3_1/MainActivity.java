package com.example.lab3_1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> myItemsList;
    ListView listView;
    MyCustomAdapter customerAdapter;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.myListView);
        myItemsList = new ArrayList<>(Arrays.asList("Android", "ASP.NET", "Unity", "Java", "C#"));
        // Changed the getApplicationContext() to this
        customerAdapter = new MyCustomAdapter(this,myItemsList);
        listView.setAdapter(customerAdapter);

        // Add button
        addButton = findViewById(R.id.addItem);

        // Anonymous inner class
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myItemsList.add("Something");
            }
        });

    }
}