package com.example.lab5_2;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.fruitRecyclerView);
        fruitList = myApplication.getFruitList();
        setUpAdapter();
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
        Intent intent = new Intent(MainActivity.this, FruitDescriptionFullView.class);
        intent.putExtra("TITLE", fruitList.get(position).getTitle());
        intent.putExtra("DESCRIPTION", fruitList.get(position).getDescription());
        intent.putExtra("IMAGE", fruitList.get(position).getImage());
        intent.putExtra("POSITION", position);
        startActivity(intent);
    }
}