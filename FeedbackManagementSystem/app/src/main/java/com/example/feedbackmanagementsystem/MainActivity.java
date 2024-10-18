package com.example.feedbackmanagementsystem;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.feedbackmanagementsystem.api.TraineeRepository;
import com.example.feedbackmanagementsystem.api.TraineeService;
import com.example.feedbackmanagementsystem.model.Trainee;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TraineeService traineeService;
    EditText etName, etEmail, etPhone, etGender;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etTraineeName);
        etEmail = findViewById(R.id.etTraineeEmail);
        etPhone = findViewById(R.id.etTraineePhoneNumber);
        etGender = findViewById(R.id.etTraineeGender);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        traineeService = TraineeRepository.getTraineeService();
    }

    private void save() {
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etPhone.getText().toString();
        String gender = etGender.getText().toString();

        Trainee trainee = new Trainee(name, email, phone, gender);
        try {
            Call<Trainee> call = traineeService.createTrainees(trainee);
            call.enqueue(new Callback<Trainee>() {
                @Override
                public void onResponse(Call<Trainee> call, Response<Trainee> response) {
                    if (response.body() != null){
                        Toast.makeText(MainActivity.this, "Save successfully", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Trainee> call, Throwable throwable) {
                    Toast.makeText(MainActivity.this, "Failure occurred", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e){
            Log.d("ERROR", e.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        save();
    }
}