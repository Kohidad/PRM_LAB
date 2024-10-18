package com.example.feedbackmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.feedbackmanagementsystem.Constants.Constants;
import com.example.feedbackmanagementsystem.api.TraineeRepository;
import com.example.feedbackmanagementsystem.api.TraineeService;
import com.example.feedbackmanagementsystem.model.Trainee;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TraineeEdit extends AppCompatActivity implements View.OnClickListener {

    private TraineeService traineeService;
    private int traineeId;
    private EditText etName, etEmail, etPhone, etGender;
    private Button btnSave,btnOverview;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainee_edit);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etName = findViewById(R.id.etTraineeName);
        etEmail = findViewById(R.id.etTraineeEmail);
        etPhone = findViewById(R.id.etTraineePhoneNumber);
        etGender = findViewById(R.id.etTraineeGender);
        btnSave = findViewById(R.id.btnSave);
        btnOverview = findViewById(R.id.btnOverview);

        btnSave.setOnClickListener(view -> {
            create();
            Intent intent = new Intent(this,TraineeOverview.class);
            startActivity(intent);
        });

        btnOverview.setOnClickListener(view -> {
            Intent intent = new Intent(this,TraineeOverview.class);
            startActivity(intent);
        });

        traineeService = TraineeRepository.getTraineeService();

        intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.UPDATE_Trainee_Id)){
            traineeId = intent.getIntExtra(Constants.UPDATE_Trainee_Id, -1);
            btnSave.setText("Update Data");
            getTraineeInfo(traineeId);
            if (traineeId != -1){
                // In progress.
                btnSave.setOnClickListener(view -> {
                    update(traineeId);
                    Intent intent = new Intent(this, TraineeOverview.class);
                    startActivity(intent);
                });
            }
        }
    }

    private void update(int id) {
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etPhone.getText().toString();
        String gender = etGender.getText().toString();

        Trainee trainee = new Trainee(name, email, phone, gender);

        try {
            Call<Trainee> call = traineeService.updateTrainees(id, trainee);
            call.enqueue(new Callback<Trainee>() {
                @Override
                public void onResponse(Call<Trainee> call, Response<Trainee> response) {
                    Toast.makeText(TraineeEdit.this, "Update Successful", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Trainee> call, Throwable throwable) {
                    Toast.makeText(TraineeEdit.this, "Update Failure", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e){
            Log.d("ERROR", e.getMessage());
        }

    }

    private void create() {
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
                        Toast.makeText(TraineeEdit.this, "Save successfully", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Trainee> call, Throwable throwable) {
                    Toast.makeText(TraineeEdit.this, "Failure occurred", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e){
            Log.d("ERROR", e.getMessage());
        }
    }

    private void getTraineeInfo(int id){
        Call<Trainee> call = traineeService.getAllTrainees(id);
        call.enqueue(new Callback<Trainee>() {
            @Override
            public void onResponse(Call<Trainee> call, Response<Trainee> response) {
                Trainee trainee = response.body();
                etName.setText(trainee.getName());
                etEmail.setText(trainee.getEmail());
                etPhone.setText(trainee.getPhone());
                etGender.setText(trainee.getGender());
            }

            @Override
            public void onFailure(Call<Trainee> call, Throwable throwable) {
                Toast.makeText(TraineeEdit.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        create();
    }
}