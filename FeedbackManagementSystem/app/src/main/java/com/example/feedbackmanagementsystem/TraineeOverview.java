package com.example.feedbackmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
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

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Trainee trainee = traineeAdapter.getTraineeAtPosition(position);
                if (position > -1){
                    deleteTrainee((int) trainee.getId());
                }
            }
        }).attachToRecyclerView(traineeRecyclerView);
    }

    private void deleteTrainee(int traineeId) {
        Call<Trainee> call = traineeService.deleteTraineese(traineeId);
        call.enqueue(new Callback<Trainee>() {
            @Override
            public void onResponse(Call<Trainee> call, Response<Trainee> response) {
                if (response.body() == null) {
                    return;
                }
                if (response.isSuccessful()){
                    traineeAdapter.removeTraineeAtId(traineeId);
                    Toast.makeText(TraineeOverview.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Trainee> call, Throwable throwable) {
                Toast.makeText(TraineeOverview.this, "Delete Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllTrainees() {
        // Since each trainees data are grouped and stored as each array, we can utilize Arrays.asList
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
