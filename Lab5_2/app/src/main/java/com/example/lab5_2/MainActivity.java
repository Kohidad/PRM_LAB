package com.example.lab5_2;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface{

    private ArrayList<Fruit> fruitList;
    private RecyclerView recyclerView;

    int[] fruitImages = {R.drawable.apple,R.drawable.banana,R.drawable.c4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fruitList = new ArrayList<>();
        recyclerView = findViewById(R.id.fruitRecyclerView);
        setUpFruitList();
        setUpAdapter();
    }

    private void setUpAdapter() {
        FruitRecyclerAdapater adapter = new FruitRecyclerAdapater(this, fruitList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void setUpFruitList(){
        String[] fruitTitle = getResources().getStringArray(R.array.fruit_title);
        String[] fruitDescription = getResources().getStringArray(R.array.fruit_description);

        for (int i = 0; i < fruitTitle.length; i++) {
            fruitList.add(new Fruit(fruitTitle[i],
                    fruitDescription[i],
                    fruitImages[i]));
        }
    }

    // It's recommended to use Parcelable for this one.
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, FruitDescriptionFullView.class);
        intent.putExtra("TITLE", fruitList.get(position).getTitle());
        intent.putExtra("DESCRIPTION", fruitList.get(position).getDescription());
        intent.putExtra("IMAGE", fruitList.get(position).getImage());
        startActivity(intent);
    }
}