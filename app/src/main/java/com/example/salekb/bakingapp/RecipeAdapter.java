package com.example.salekb.bakingapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private static final String LOG_TAG = RecipeAdapter.class.getSimpleName();

    private List<Recipe> recipeList;

    public RecipeAdapter(List<Recipe> recipes) {
        this.recipeList = recipes;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false);
        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(itemView);

        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        final  Recipe recipe = recipeList.get(position);
        holder.mNameTextView.setText(recipe.getName());
        holder.mServingTextView.setText(recipe.getServings());

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO: Create Recipe Detail and set onClickListener
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {

        public TextView mNameTextView;
        public TextView mServingTextView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            this.mNameTextView = (TextView) itemView.findViewById(R.id.name);
            this.mServingTextView = (TextView) itemView.findViewById(R.id.serving);
        }
    }
}
