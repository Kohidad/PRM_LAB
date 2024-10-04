package com.example.recyclerviewtutorial;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Adapters provide a binding from an app-specific data set to views that are displayed within a RecyclerView.
public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {

    private ArrayList<User> user_list;

    public recyclerAdapter(ArrayList<User> user_list) {
        this.user_list = user_list;
    }

    // Each individual element in the list is defined by a view holder object.
    // After the view holder is created, the RecyclerView binds it to its data.
    // You define the view holder by extending RecyclerView.ViewHolder.
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView username;

        // Constructor to bind view to specific items.
        // We pass the inflated item (user_username) here, then initialized VH held the data.
        public MyViewHolder (final View view){
            super(view);
            username = view.findViewById(R.id.user_username);
        }
    }

    @NonNull
    @Override
    // New VH: Inflate view -> Create new ViewHolder
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    // Display VH: Bind the VH with the data -> Display VH.
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {
        String name = user_list.get(position).getUsername();
        holder.username.setText(name);
    }

    @Override
    public int getItemCount() {
        return user_list.size();
    }
}
