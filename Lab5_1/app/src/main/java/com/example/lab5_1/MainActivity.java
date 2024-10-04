package com.example.lab5_1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Student> studentList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        studentList = new ArrayList<>();
        recyclerView = findViewById(R.id.studentRecyclerView);
        setUpStudentList();
        setUpAdapter();
    }

    // Make this because clean and reduce the complexity.
    private void setUpAdapter() {
        StudentInfo_RecyclerViewAdapter studentAdapter = new StudentInfo_RecyclerViewAdapter(this, studentList);
        // We arrange how the layout goes (vertical, horizontal, etc...) default is vertical.
        // Also responsible for measuring and positioning item within RecyclerView.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        // After picking your desired layout, set it up yo.
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // After configuration, you gotta initialize it yo.
        recyclerView.setAdapter(studentAdapter);
    }

    public void setUpStudentList(){
        String[] student_username = getResources().getStringArray(R.array.student_username);
        String[] student_fullname = getResources().getStringArray(R.array.student_fullname);
        String[] student_email = getResources().getStringArray(R.array.student_email);

        // Each student has name & email each respective to username.
        for (int i = 0; i < student_username.length; i++) {
            studentList.add(new Student(student_username[i],
                    student_fullname[i],
                    student_email[i]));
        }
    }
}