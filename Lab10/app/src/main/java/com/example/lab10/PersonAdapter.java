package com.example.lab10;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.MyViewHolder> {

    private Context context;
    private List<Person> mPersonList;

    public PersonAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PersonAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.person_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonAdapter.MyViewHolder holder, int position) {
        holder.firstName.setText(mPersonList.get(position).getFirstName());
        holder.lastName.setText(mPersonList.get(position).getLastName());
    }

    @Override
    public int getItemCount() {

        if (mPersonList == null){
            return 0;
        }

        return mPersonList.size();
    }

    public void setTasks(List<Person> personList){
        mPersonList = personList;
        notifyDataSetChanged();
    }

    public List<Person> getTasks(){
        return mPersonList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView firstName, lastName;
        ImageView editImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.tvFirstName);
            lastName = itemView.findViewById(R.id.tvLastName);
            editImage = itemView.findViewById(R.id.ivEdit);

            editImage.setOnClickListener(view -> {
                int elementId = mPersonList.get(getAdapterPosition()).getUid();
                Intent intent = new Intent(context, EditPersonActivity.class);
                intent.putExtra(Constants.UPDATE_Person_Id, elementId);
                context.startActivity(intent);
            });
        }
    }
}
