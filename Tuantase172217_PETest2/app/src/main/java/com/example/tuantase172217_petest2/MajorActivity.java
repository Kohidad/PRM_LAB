package com.example.tuantase172217_petest2;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuantase172217_petest2.Adapter.MajorAdapter;
import com.example.tuantase172217_petest2.Database.MajorDB;
import com.example.tuantase172217_petest2.Model.Major;
import com.example.tuantase172217_petest2.Model.Student;

import java.util.ArrayList;

public class MajorActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Major> majorList;
    private int selectedPosition = -1;
    MajorAdapter majorAdapter;
    int selectedMajorId = -1;

    Button major_addBtn, major_updateBtn, major_deleteBtn;
    EditText majorNameInput;

    MajorDB majorDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.major_recycler_view);
        major_addBtn = findViewById(R.id.major_addBtn);
        major_updateBtn = findViewById(R.id.major_updateBtn);
        major_deleteBtn = findViewById(R.id.major_deleteBtn);
        majorNameInput = findViewById(R.id.majorNameInput);

        majorDB = new MajorDB(this);

        majorList = new ArrayList<>();
        majorAdapter = new MajorAdapter(this, this, majorList);
        recyclerView.setAdapter(majorAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        major_addBtn.setOnClickListener(view -> {
            addMajor();
        });

        major_updateBtn.setOnClickListener(view -> {
            updateMajor();
        });

        major_deleteBtn.setOnClickListener(view -> {
            deleteMajor();
        });


        if (majorDB.readAllMajor().getCount() == 0){
            addExampleData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllMajorData();
    }

    // Example data
    void addExampleData(){
        majorDB.addMajor("Computer Science");
        majorDB.addMajor("Electrical Engineering");
        majorDB.addMajor("Mechanical Engineering");
    }

    public void getAllMajorData(){
        majorList.clear();
        Cursor cursor = majorDB.readAllMajor();
        if (cursor.getCount() != 0){
            while(cursor.moveToNext()){
                int ID = cursor.getInt(0);
                String major_name = cursor.getString(1);
                majorList.add(new Major(ID,major_name));
            }
            majorAdapter.notifyDataSetChanged();
            cursor.close();
        }
    }

    // Set the name whenever the user click on which Major.
    public void setMajorData(Major major) {
        selectedMajorId = major.getIdMajor();
        majorNameInput.setText(major.getNameMajor());
    }

    private void addMajor() {
        String majorName = majorNameInput.getText().toString().trim();

        if (majorName.isEmpty()) {
            Toast.makeText(this, "Please enter a major name", Toast.LENGTH_SHORT).show();
            return;
        }
        majorDB.addMajor(majorName);
        Toast.makeText(this, "Major added successfully", Toast.LENGTH_SHORT).show();
        majorNameInput.setText("");
        getAllMajorData();
    }

    private void updateMajor() {
        String majorName = majorNameInput.getText().toString().trim();

        if (selectedMajorId == -1) {
            Toast.makeText(this, "No major selected for update", Toast.LENGTH_SHORT).show();
            return;
        }
        if (majorName.isEmpty()) {
            Toast.makeText(this, "Please enter a major name", Toast.LENGTH_SHORT).show();
            return;
        }
        majorDB.updateMajor(selectedMajorId, majorName);
        Toast.makeText(this, "Major updated successfully", Toast.LENGTH_SHORT).show();
        majorNameInput.setText("");
        selectedMajorId = -1;
        getAllMajorData();
    }

    private void deleteMajor() {
        if (selectedMajorId == -1) {
            Toast.makeText(this, "No major selected for deletion", Toast.LENGTH_SHORT).show();
            return;
        }
        majorDB.deleteMajor(selectedMajorId);
        Toast.makeText(this, "Major deleted successfully", Toast.LENGTH_SHORT).show();
        majorNameInput.setText("");
        selectedMajorId = -1;
        getAllMajorData();
    }
}