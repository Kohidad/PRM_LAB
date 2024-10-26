package com.example.tuantase172217_petest2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tuantase172217_petest2.Database.StudentDB;
import com.example.tuantase172217_petest2.Database.MajorDB;
import com.example.tuantase172217_petest2.Model.Major;

import java.util.ArrayList;

public class UpdateStudentActivity extends AppCompatActivity {

    private EditText studentNameInput, dateInput, emailInput, genderInput, addressInput;
    private Spinner majorSpinner;
    private Button updateButton, deleteButton;
    private StudentDB studentDB;
    private MajorDB majorDB;
    private int studentId;
    private ArrayList<Major> majorList;

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
        updateButton = findViewById(R.id.add_button);
        deleteButton = findViewById(R.id.delete_button);

        deleteButton.setVisibility(View.VISIBLE);

        studentDB = new StudentDB(this);
        majorDB = new MajorDB(this);
        majorList = new ArrayList<>();

        loadMajors();

        Intent intent = getIntent();
        studentId = intent.getIntExtra("STUDENT_ID", -1);
        studentNameInput.setText(intent.getStringExtra("STUDENT_NAME"));
        dateInput.setText(intent.getStringExtra("DATE"));
        emailInput.setText(intent.getStringExtra("EMAIL"));
        genderInput.setText(intent.getStringExtra("GENDER"));
        addressInput.setText(intent.getStringExtra("ADDRESS"));

        int majorId = intent.getIntExtra("MAJOR_ID", -1);
        setMajorSpinnerSelection(majorId);

        updateButton.setText("Update");
        updateButton.setOnClickListener(v -> updateStudent());
        deleteButton.setOnClickListener(v -> deleteStudent());
    }

    private void loadMajors() {
        Cursor cursor = majorDB.readAllMajor();
        ArrayList<String> majorNames = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            majorList.add(new Major(id, name));
            majorNames.add(name);
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, majorNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        majorSpinner.setAdapter(adapter);
    }

    private void setMajorSpinnerSelection(int majorId) {
        for (int i = 0; i < majorList.size(); i++) {
            if (majorList.get(i).getIdMajor() == majorId) {
                majorSpinner.setSelection(i);
                break;
            }
        }
    }

    private void updateStudent() {
        String name = studentNameInput.getText().toString().trim();
        String date = dateInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String gender = genderInput.getText().toString().trim();
        String address = addressInput.getText().toString().trim();
        int selectedMajorPosition = majorSpinner.getSelectedItemPosition();
        int majorId = majorList.get(selectedMajorPosition).getIdMajor();

        if (name.isEmpty() || date.isEmpty() || email.isEmpty() || gender.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        studentDB.updateStudent(studentId, name, date, email, gender, address, majorId);
        Toast.makeText(this, "Student updated successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void deleteStudent() {
        studentDB.deleteStudent(studentId);
        Toast.makeText(this, "Student deleted successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}
