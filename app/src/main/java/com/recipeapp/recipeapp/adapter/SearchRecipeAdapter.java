package com.recipeapp.recipeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.recipeapp.recipeapp.R;
import com.recipeapp.recipeapp.models.RootObjectModel;

import java.util.ArrayList;

public class SearchRecipeAdapter extends RecyclerView.Adapter<SearchRecipeAdapter.FoodViewHolder> {
    private Context context;
    private ArrayList<RootObjectModel> recipes;
    private RecyclerViewClickListener listener;

    public SearchRecipeAdapter(Context context, ArrayList<RootObjectModel> recipes, RecyclerViewClickListener listener) {
        this.context = context;
        this.recipes = recipes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_entries, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        holder.label.setText(recipes.get(position).getRecipeModel().getLabel());
        Glide.with(holder.itemView.getContext()).load(recipes.get(position).getRecipeModel().getImage()).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
        holder.url.setText(recipes.get(position).getRecipeModel().getUrl());
        holder.source.setText(recipes.get(position).getRecipeModel().getSource());
        holder.yield.setText(recipes.get(position).getRecipeModel().getYield());
        holder.calories.setText(recipes.get(position).getRecipeModel().getCalories());
        holder.totalWeight.setText(recipes.get(position).getRecipeModel().getTotalWeight());

    }

    @Override
    public int getItemCount() {
        if(recipes != null){
            return recipes.size();
        }
        return 0;
    }

    class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView label, url, source, yield, calories, totalWeight;
        ImageView imageView;

        public FoodViewHolder(@NonNull View itemView){
            super(itemView);
            label = itemView.findViewById(R.id.text_label1);
            imageView = itemView.findViewById(R.id.sliderImg);
            url = itemView.findViewById(R.id.instructions1);
            source = itemView.findViewById(R.id.ingr1);
            yield = itemView.findViewById(R.id.ingr2);
            calories = itemView.findViewById(R.id.ingr3);
            totalWeight = itemView.findViewById(R.id.ingr4);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getBindingAdapterPosition());
        }
    }
    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

}
