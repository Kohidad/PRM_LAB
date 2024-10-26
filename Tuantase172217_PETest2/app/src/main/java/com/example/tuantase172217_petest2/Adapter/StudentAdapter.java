package com.example.tuantase172217_petest2.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuantase172217_petest2.Database.MajorDB;
import com.example.tuantase172217_petest2.Model.Major;
import com.example.tuantase172217_petest2.Model.Student;
import com.example.tuantase172217_petest2.R;
import com.example.tuantase172217_petest2.UpdateStudentActivity;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {

    private ArrayList<Student> studentList;
    private Activity activity;
    private Context context;

    MajorDB majorDB;

    public StudentAdapter(ArrayList<Student> studentList, Activity activity, Context context) {
        this.studentList = studentList;
        this.activity = activity;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.student_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.MyViewHolder holder, int position) {
        holder.studentID.setText(String.valueOf(studentList.get(position).getID()));
        holder.student_name.setText(studentList.get(position).getName());
        holder.date.setText(studentList.get(position).getDate());
        holder.email.setText(studentList.get(position).getEmail());
        holder.gender.setText(studentList.get(position).getGender());
        holder.address.setText(studentList.get(position).getAddress());

        Major major = getCurrentMajor(studentList.get(position).getIdMajor());
        holder.majorID.setText(major != null ? major.getNameMajor() : "No Major");

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateStudentActivity.class);
            intent.putExtra("STUDENT_ID", studentList.get(position).getID());
            intent.putExtra("STUDENT_NAME", studentList.get(position).getName());
            intent.putExtra("DATE", studentList.get(position).getDate());
            intent.putExtra("EMAIL", studentList.get(position).getEmail());
            intent.putExtra("GENDER", studentList.get(position).getGender());
            intent.putExtra("ADDRESS", studentList.get(position).getAddress());
            intent.putExtra("MAJOR_ID", studentList.get(position).getIdMajor());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView studentID, student_name, date, email, gender, address, majorID;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            studentID = itemView.findViewById(R.id.student_id_txt);
            student_name = itemView.findViewById(R.id.student_name_txt);
            date = itemView.findViewById(R.id.date_txt);
            email = itemView.findViewById(R.id.email_txt);
            gender = itemView.findViewById(R.id.gender_txt);
            address = itemView.findViewById(R.id.address_txt);
            majorID = itemView.findViewById(R.id.idMajor_txt);
        }
    }

    private Major getCurrentMajor(int id) {
        Major major = null;
        Cursor cursor = MajorDB.getData(context, "SELECT * FROM Major WHERE idMajor = " + id);
        if (cursor.moveToFirst()) {
            int idMajor = cursor.getInt(0);
            String nameMajor = cursor.getString(1);
            major = new Major(idMajor, nameMajor);
        }
        cursor.close();
        return major;
    }
}
