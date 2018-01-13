package com.example.salekb.bakingapp.steps;


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

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    public ArrayList<Steps> steps;

    public StepsAdapter (ArrayList<Steps> stepsArrayList) {
        this.steps = stepsArrayList;
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_item, parent, false);

        return new StepsViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, final int position) {
        final String step = steps.get(position).getShortDescription();

        holder.mStepsTextView.setText(step);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(view.getContext(), DetailActivity.class);
                detailIntent.putExtra("position", position);
                view.getContext().startActivity(detailIntent);
                Log.v(StepsAdapter.class.getSimpleName(), "onClicked in OnBindViewHolder");
            }
        });
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public static class StepsViewHolder extends RecyclerView.ViewHolder {

        private TextView mStepsTextView;

        public StepsViewHolder(View itemView) {
            super(itemView);
            mStepsTextView = (TextView) itemView.findViewById(R.id.steps_item_textView);
        }
    }
}