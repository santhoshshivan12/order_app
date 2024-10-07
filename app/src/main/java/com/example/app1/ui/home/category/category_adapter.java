package com.example.app1.ui.home.category;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app1.R;

import java.util.List;

public class category_adapter extends RecyclerView.Adapter<category_adapter.ViewHolder> {

    private List<category> data;
    private OnCategoryClickListener listener;
    private int selectedPosition = -1; // to track selected position


    public category_adapter(List<category> data, OnCategoryClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        category Category = data.get(position);
        holder.textView.setText(Category.getText());
        holder.img.setImageResource(Category.getImageResId());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When category item is clicked, pass the category to the listener


                if (listener != null) {
                    Log.d("category_adapter", "Category clicked: " + Category.getText());
                    listener.onCategoryClick(Category);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        private ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.item_image);
            textView = itemView.findViewById(R.id.item_text);
        }
    }

    public interface OnCategoryClickListener {
        void onCategoryClick(category category); // Callback for category click
    }
}
