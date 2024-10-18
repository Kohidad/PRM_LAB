package com.example.lab10;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PersonActivity extends AppCompatActivity {

    private FloatingActionButton fabAdd;
    private RecyclerView mRecyclerView;
    private PersonAdapter mAdapter;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        fabAdd = findViewById(R.id.fabAdd);

        fabAdd.setOnClickListener(view -> {
            startActivity(new Intent(this, EditPersonActivity.class));
        });

        mRecyclerView = findViewById(R.id.rvPerson);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new PersonAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mDb = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app-database").build();

        // Swipe
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<Person> tasks = mAdapter.getTasks();
                        mDb.personDAO().delete(tasks.get(position));
                        retrieveTasks();
                    }
                });
            }
        }).attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveTasks();
    }

    // Refresh data
    private void retrieveTasks() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<Person> personList = mDb.personDAO().getAll();
                // Once data is fetched, update the adapter with new list.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setTasks(personList);
                    }
                });
            }
        });
    }
}