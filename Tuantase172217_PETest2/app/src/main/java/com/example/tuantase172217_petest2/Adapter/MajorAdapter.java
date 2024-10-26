package com.example.tuantase172217_petest2.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuantase172217_petest2.Database.MajorDB;
import com.example.tuantase172217_petest2.MajorActivity;
import com.example.tuantase172217_petest2.Model.Major;
import com.example.tuantase172217_petest2.R;

import java.util.ArrayList;

public class MajorAdapter extends RecyclerView.Adapter<MajorAdapter.MyViewHolder> {

    Context context;
    Activity activity;
    ArrayList<Major> majorList;
    private int selectedPosition = -1;

    public MajorAdapter(Context context, Activity activity, ArrayList<Major> majorList) {
        this.context = context;
        this.activity = activity;
        this.majorList = majorList;
    }

    @NonNull
    @Override
    public MajorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.major_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MajorAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.majorID.setText(String.valueOf(majorList.get(position).getIdMajor()));
        holder.majorName.setText(majorList.get(position).getNameMajor());

        holder.itemView.setOnClickListener(v -> {
            selectedPosition = position;
            notifyDataSetChanged();
            ((MajorActivity) activity).setMajorData(majorList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return majorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView majorID, majorName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            majorID = itemView.findViewById(R.id.major_row_id_txt);
            majorName = itemView.findViewById(R.id.major_row_title_txt);
        }
    }
}
