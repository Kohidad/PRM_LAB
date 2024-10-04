package com.example.recyclerviewtutorial;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<User> user_list;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        user_list = new ArrayList<>();
        setUserInfo();
        setAdapter();

        Log.i("INFO", "Amount of user:" + user_list.size());
    }

    private void setAdapter(){
        recyclerAdapter adapter = new recyclerAdapter(user_list);
        // We arrange how the layout goes (vertical, horizontal, etc...) default is vertical.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // Render the list.
        recyclerView.setAdapter(adapter);
    }

    private void setUserInfo() {
        user_list.add(new User("John Pork"));
        user_list.add(new User("Baller"));
        user_list.add(new User("Freak Bob"));
    }
}