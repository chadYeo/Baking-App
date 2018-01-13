package com.example.salekb.bakingapp.recipe;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.salekb.bakingapp.DetailActivity;
import com.example.salekb.bakingapp.R;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    public ArrayList<Recipe> recipes;

    public RecipeAdapter (ArrayList<Recipe> recipeArrayList) {
        this.recipes = recipeArrayList;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);

        return new RecipeViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, final int position) {

        final String name = recipes.get(position).getName();
        String serving = String.valueOf(recipes.get(position).getServings());

        holder.mNameTextView.setText(name);
        holder.mServingTextView.setText(serving);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(view.getContext(), DetailActivity.class);
                detailIntent.putExtra("position", position);
                view.getContext().startActivity(detailIntent);
                Log.v(RecipeAdapter.class.getSimpleName(), "onClicked in OnBindViewHolder");
            }
        });
    }


    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {

        private TextView mNameTextView;
        private TextView mServingTextView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            mNameTextView = (TextView)itemView.findViewById(R.id.name);
            mServingTextView = (TextView)itemView.findViewById(R.id.serving);
        }
    }

}
