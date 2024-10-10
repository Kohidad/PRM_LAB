package com.example.lab5_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface{

    // Reference to MyApplication
    MyApplication myApplication = (MyApplication) this.getApplication();

    private ArrayList<Fruit> fruitList;
    private RecyclerView recyclerView;
    FruitRecyclerAdapater adapter;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.fruitRecyclerView);
        addBtn = findViewById(R.id.addBtn);

        fruitList = myApplication.getFruitList();
        setUpAdapter();

        addBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddFruit.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getBooleanExtra("NEW_FRUIT_ADDED", false)) {
            Toast.makeText(this, "New fruit just got added", Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
        }
    }

    private void setUpAdapter() {
        adapter = new FruitRecyclerAdapater(this, fruitList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    // It's recommended to use Parcelable for this one.
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, FruitDescriptionFullView.class);
        intent.putExtra("TITLE", fruitList.get(position).getTitle());
        intent.putExtra("DESCRIPTION", fruitList.get(position).getDescription());
        intent.putExtra("IMAGEURI", fruitList.get(position).getImage());
        intent.putExtra("POSITION", position);
        startActivity(intent);
    }
}