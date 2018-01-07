package com.example.salekb.bakingapp.ingredient;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.salekb.bakingapp.R;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>{

    public ArrayList<Ingredient> ingredients;

    public IngredientAdapter(ArrayList<Ingredient> ingredientArrayList) {
        this.ingredients = ingredientArrayList;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, parent, false);

        return new IngredientViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {

        final String ingredient = ingredients.get(position).getIngredient();
        final int quantity = ingredients.get(position).getQuantity();
        final String measure = ingredients.get(position).getMeasure();

        holder.mIngredientNameTextView.setText(ingredient);
        holder.mQuantityTextView.setText(String.valueOf(quantity));
        holder.mMeasureTextView.setText(measure);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView mIngredientNameTextView;
        TextView mQuantityTextView;
        TextView mMeasureTextView;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            mIngredientNameTextView = (TextView)itemView.findViewById(R.id.ingredient_name);
            mQuantityTextView = (TextView)itemView.findViewById(R.id.quantity);
            mMeasureTextView = (TextView)itemView.findViewById(R.id.measure);
        }
    }
}
