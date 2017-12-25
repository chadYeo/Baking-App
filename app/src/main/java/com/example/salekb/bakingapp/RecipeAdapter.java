package com.example.salekb.bakingapp;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    public void onBindViewHolder(RecipeViewHolder holder, int position) {

        final String name = recipes.get(position).getName();
        String serving = String.valueOf(recipes.get(position).getServings());

        holder.mNameTextView.setText(name);
        holder.mServingTextView.setText(serving);
        holder.itemView.setClickable(true);
        holder.itemView.setFocusable(true);
        holder.itemView.setFocusableInTouchMode(true);
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

    /**
    public RecipeAdapter(Context context, List<Recipe> objects) {
        super(context, 0, objects);
    }

    private static final String LOG_TAG = RecipeAdapter.class.getSimpleName();

    private List<Recipe> recipeList = new ArrayList<Recipe>();

    static class ViewHolder {
        TextView mNameTextView;
        TextView mServingTextView;
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
    **/
}
