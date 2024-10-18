package com.example.feedbackmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedbackmanagementsystem.api.TraineeRepository;
import com.example.feedbackmanagementsystem.api.TraineeService;
import com.example.feedbackmanagementsystem.model.Trainee;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TraineeOverview extends AppCompatActivity {

    private FloatingActionButton fabAdd;
    private RecyclerView traineeRecyclerView;
    private TraineeAdapter traineeAdapter;
    private TraineeService traineeService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainee_overview);

        fabAdd = findViewById(R.id.fab_add);
        traineeRecyclerView = findViewById(R.id.trainee_recycler_view);

        fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(this, TraineeEdit.class);
            startActivity(intent);
        });

        traineeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        traineeAdapter = new TraineeAdapter(this);
        traineeRecyclerView.setAdapter(traineeAdapter);


        traineeService = TraineeRepository.getTraineeService();
        getAllTrainees();
    }

    private void getAllTrainees() {
        Call<Trainee[]> call = traineeService.getAllTrainees();
        call.enqueue(new Callback<Trainee[]>() {
            @Override
            public void onResponse(Call<Trainee[]> call, Response<Trainee[]> response) {
                if (response.body() == null) {
                    return;
                }

                if (response.isSuccessful()) {
                    Log.i("API_RESPONSE","Arrays:" + Arrays.toString(response.body()));
                    List<Trainee> traineeList = Arrays.asList(response.body());
                    traineeAdapter.getAllTraineesIntoList(traineeList);
                } else {
                    Log.e("ERROR", "Error fetching trainees: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Trainee[]> call, Throwable throwable) {
                Log.e("ERROR", "Network request failed: " + throwable.getMessage());
            }
        });
    }
}
