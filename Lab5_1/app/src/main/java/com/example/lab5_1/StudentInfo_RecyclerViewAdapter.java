package com.example.lab5_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentInfo_RecyclerViewAdapter extends RecyclerView.Adapter<StudentInfo_RecyclerViewAdapter.MyViewHolder> {

    Context context;
    private ArrayList<Student> student_list;

    public StudentInfo_RecyclerViewAdapter(Context context, ArrayList<Student> student_list) {
        this.context = context;
        this.student_list = student_list;
    }

    // This is our ViewHolder, to hold our views, duh.
    // We initialize our variables here.
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView username;
        private TextView fullname;
        private TextView email;

        // itemView represent the card.
        // From here, after creating a card (VH) we set the IDs to each card created.
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.student_username);
            fullname = itemView.findViewById(R.id.student_fullname);
            email = itemView.findViewById(R.id.student_email);
        }
    }

    @NonNull
    @Override
    // This is just where we inflate our layout.
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Giving out the layout by inflating it.
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_rows,null);
        // Passing the view (inflated object)
        return new MyViewHolder(view);
        }

    @Override
    // Assign the values, when it's called (or scrolled on screen)
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.username.setText(student_list.get(position).getUsername());
        holder.fullname.setText(student_list.get(position).getFullname());
        holder.email.setText(student_list.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return student_list.size();
    }
}
