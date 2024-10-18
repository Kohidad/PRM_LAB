package com.example.feedbackmanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedbackmanagementsystem.Constants.Constants;
import com.example.feedbackmanagementsystem.model.Trainee;

import java.util.ArrayList;
import java.util.List;

public class TraineeAdapter extends RecyclerView.Adapter<TraineeAdapter.MyViewHolder> {

    private Context context;
    private List<Trainee> traineeList = new ArrayList<>();

    public TraineeAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TraineeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.trainee_information, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TraineeAdapter.MyViewHolder holder, int position) {
        Log.i("BINDING_VIEW", "Binding trainee at position: " + position);
        holder.name.setText(traineeList.get(position).getName());
        holder.email.setText(traineeList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return traineeList.size();
    }

    public void getAllTraineesIntoList(List<Trainee> traineeList) {
        this.traineeList.clear();
        this.traineeList.addAll(traineeList);
        Log.i("ADAPTER_UPDATE", "Number of trainees: " + traineeList.size());
        notifyDataSetChanged(); // Notify the adapter of the data change
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, email;
        ImageView editImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvName);
            email = itemView.findViewById(R.id.tvEmail);
            editImage = itemView.findViewById(R.id.ivEdit);

            editImage.setOnClickListener(view -> {
                int elementId = (int) traineeList.get(getAdapterPosition()).getId();
                Intent intent = new Intent(context, TraineeEdit.class);
                intent.putExtra(Constants.UPDATE_Trainee_Id, elementId);
                context.startActivity(intent);
            });
        }
    }
}
