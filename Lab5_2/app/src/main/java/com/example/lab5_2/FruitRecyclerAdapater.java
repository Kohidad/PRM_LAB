package com.example.lab5_2;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class FruitRecyclerAdapater extends RecyclerView.Adapter<FruitRecyclerAdapater.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    private ArrayList<Fruit> fruitList;
    private String imageUri;

    public FruitRecyclerAdapater(Context context, ArrayList<Fruit> fruitList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.fruitList = fruitList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView fruitTitle, fruitDesciption;
        private ImageView fruitImages;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            fruitTitle = itemView.findViewById(R.id.fruitTitle);
            fruitDesciption = itemView.findViewById(R.id.fruitDescription);
            fruitImages = itemView.findViewById(R.id.fruitImage);

            itemView.setOnClickListener(view -> {
                if (recyclerViewInterface != null){
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION){
                        recyclerViewInterface.onItemClick(position);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public FruitRecyclerAdapater.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_rows,null);
        return new MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull FruitRecyclerAdapater.MyViewHolder holder, int position) {
        holder.fruitTitle.setText(fruitList.get(position).getTitle());
        holder.fruitDesciption.setText(fruitList.get(position).getDescription());

        imageUri = fruitList.get(position).getImage();
        holder.fruitImages.setImageURI(Uri.parse(imageUri));
    }

    @Override
    public int getItemCount() {
        return fruitList.size();
    }

}
