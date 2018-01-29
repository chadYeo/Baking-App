package com.example.salekb.bakingapp.steps;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.salekb.bakingapp.DetailActivity;
import com.example.salekb.bakingapp.R;
import com.example.salekb.bakingapp.fragments.DetailStepsFragment;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private static final String LOG_TAG = StepsAdapter.class.getSimpleName();

    public ArrayList<Steps> steps;
    private boolean twoPane;
    private RecyclerView.ViewHolder lastModifiedHolded = null;

    public StepsAdapter (ArrayList<Steps> stepsArrayList) {
        this.steps = stepsArrayList;
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_item, parent, false);

        return new StepsViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(final StepsViewHolder holder, final int position) {

        final String step = steps.get(position).getShortDescription();
        holder.mStepsTextView.setText(step);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailActivity detailActivity = (DetailActivity)view.getContext();
                DetailStepsFragment detailStepsFragment = new DetailStepsFragment();
                twoPane = detailActivity.isTwoPane();
                if (twoPane) {
                    // Reset last modified
                    if (lastModifiedHolded != null) {
                        int lastPosition = lastModifiedHolded.getAdapterPosition();
                        lastModifiedHolded.itemView.setSelected(false);
                        notifyItemChanged(lastPosition);
                        Log.v(LOG_TAG, "Last Item Position: " + lastPosition);
                    }
                    view.setSelected(true);
                    notifyItemChanged(position);
                    lastModifiedHolded = holder;

                    Log.v(LOG_TAG, "Current Item background: " + view.getBackground().toString());

                    detailActivity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.detail_largeScreen_fragment_container, detailStepsFragment)
                            .addToBackStack("Steps_Detail_TAG")
                            .commit();
                } else {
                    detailActivity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.detail_fragment_container, detailStepsFragment)
                            .addToBackStack("Steps_Detail_TAG")
                            .commit();
                }

                Bundle args = new Bundle();
                args.putInt("stepsPosition", position);
                args.putBoolean("twoPane", twoPane);
                detailStepsFragment.setArguments(args);
                Log.v(StepsAdapter.class.getSimpleName(), "onClicked in OnBindViewHolder with isTwoPane: " + twoPane);
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