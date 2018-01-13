package com.example.salekb.bakingapp.steps;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.salekb.bakingapp.QueryUtils;

import java.util.List;

public class StepsLoader extends AsyncTaskLoader<List<Steps>> {

    private static final String LOG_TAG = StepsLoader.class.getSimpleName();

    private String mUrl;
    private int mItemPosition;

    public StepsLoader(Context context, String url, int itemPosition) {
        super(context);
        mUrl = url;
        mItemPosition = itemPosition;
    }

    @Override
    public List<Steps> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<Steps> steps = QueryUtils.fetchStepsData(mUrl, mItemPosition);

        Log.v(LOG_TAG, "loadInbackground is initiated");

        return steps;
    }
}