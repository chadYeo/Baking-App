package com.example.salekb.bakingapp;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends ArrayAdapter<Recipe> {

    public RecipeAdapter(Context context, List<Recipe> objects) {
        super(context, 0, objects);
    }

    private static final String LOG_TAG = RecipeAdapter.class.getSimpleName();

    private List<Recipe> recipeList = new ArrayList<Recipe>();

    static class ViewHolder {
        TextView mNameTextView;
        TextView mServingTetView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.recipe_item, parent, false);
        }

        Recipe currentPosition = getItem(position);

        ViewHolder viewHolder = new ViewHolder();

        viewHolder.mNameTextView = (TextView) convertView.findViewById(R.id.name);
        String name = currentPosition.getName();
        viewHolder.mNameTextView.setText(name);

        viewHolder.mServingTetView = (TextView) convertView.findViewById(R.id.serving);
        String serving = String.valueOf(currentPosition.getServings());
        viewHolder.mServingTetView.setText(serving);

        return convertView;
    }

    public void setRecipe(List<Recipe> data) {
        recipeList.addAll(data);
        notifyDataSetChanged();
    }
}
