package com.example.tuantase172217_petest2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuantase172217_petest2.Adapter.StudentAdapter;
import com.example.tuantase172217_petest2.Database.MajorDB;
import com.example.tuantase172217_petest2.Database.StudentDB;
import com.example.tuantase172217_petest2.Model.Student;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    StudentAdapter studentAdapter;
    Button addStudent_btn, addMajor_btn;

    StudentDB studentDB;
    MajorDB majorDB;

    ArrayList<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.student_recycler_view);

        studentDB = new StudentDB(this);

        studentList = new ArrayList<>();
        studentAdapter = new StudentAdapter(studentList, this, this);
        recyclerView.setAdapter(studentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addStudent_btn = findViewById(R.id.addStudentBtn);
        addMajor_btn = findViewById(R.id.addMajorBtn);

        addStudent_btn.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddStudentActivity.class);
            startActivity(intent);
        });

        addMajor_btn.setOnClickListener(view -> {
            Intent intent = new Intent(this, MajorActivity.class);
            startActivity(intent);
        });

//        addExampleData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllStudentData();
    }


    // Example datas
//    private void addExampleData(){
//        studentDB.addStudent("David Lee", "05-12-2021", "david.lee@example.com", "Male", "321 Elm St", 1);
//        studentDB.addStudent("Bob Smith", "15-08-2023", "bobsmith@example.com", "Male", "456 Oak St", 2);
//        studentDB.addStudent("Alice Johnson", "25-10-2024", "alice@example.com", "Female", "123 Maple St", 1);
//    }

    public void getAllStudentData(){
        studentList.clear();
        Cursor cursor = studentDB.readAllStudent();
        if (cursor.getCount() != 0){
            while(cursor.moveToNext()){
                int ID = cursor.getInt(0);
                String student_name = cursor.getString(1);
                String date = cursor.getString(2);
                String email = cursor.getString(3);
                String gender = cursor.getString(4);
                String address = cursor.getString(5);
                int majorID = cursor.getInt(6);
                studentList.add(new Student(ID,student_name,date,email,gender,address,majorID));
            }
            studentAdapter.notifyDataSetChanged();
            cursor.close();
        }
    }
}