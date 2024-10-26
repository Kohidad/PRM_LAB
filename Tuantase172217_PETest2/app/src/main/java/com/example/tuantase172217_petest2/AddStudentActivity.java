package com.example.tuantase172217_petest2;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tuantase172217_petest2.Database.MajorDB;
import com.example.tuantase172217_petest2.Database.StudentDB;
import com.example.tuantase172217_petest2.Model.Major;

import java.util.ArrayList;

public class AddStudentActivity extends AppCompatActivity {

    private EditText studentNameInput, dateInput, emailInput, genderInput, addressInput;
    private Spinner majorSpinner;
    private Button addButton, deleteButton;
    private StudentDB studentDB;
    private MajorDB majorDB;
    private ArrayList<Major> majorList;
    private ArrayList<String> majorNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        studentNameInput = findViewById(R.id.student_name_input);
        dateInput = findViewById(R.id.date_input);
        emailInput = findViewById(R.id.email_input);
        genderInput = findViewById(R.id.gender_input);
        addressInput = findViewById(R.id.address_input);
        majorSpinner = findViewById(R.id.major_spinner);
        addButton = findViewById(R.id.add_button);
        deleteButton = findViewById(R.id.delete_button);

        studentDB = new StudentDB(this);
        majorDB = new MajorDB(this);
        majorList = new ArrayList<>();
        majorNames = new ArrayList<>();

        deleteButton.setVisibility(View.GONE);

        loadMajors();

        addButton.setOnClickListener(v -> addStudent());
    }

    private void loadMajors() {
        Cursor cursor = majorDB.readAllMajor();
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                majorList.add(new Major(id, name));
                majorNames.add(name);
            }
            cursor.close();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, majorNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        majorSpinner.setAdapter(adapter);
    }

    private void addStudent() {
        String name = studentNameInput.getText().toString().trim();
        String date = dateInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String gender = genderInput.getText().toString().trim();
        String address = addressInput.getText().toString().trim();
        int selectedMajorPosition = majorSpinner.getSelectedItemPosition();

        if (name.isEmpty() || date.isEmpty() || email.isEmpty() || gender.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int majorId = majorList.get(selectedMajorPosition).getIdMajor();
        studentDB.addStudent(name, date, email, gender, address, majorId);
        Toast.makeText(this, "Student added successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}
