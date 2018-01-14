package com.example.salekb.bakingapp.ingredient;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.salekb.bakingapp.QueryUtils;

import java.util.List;

public class IngredientLoader extends AsyncTaskLoader<List<Ingredient>> {

    private static final String LOG_TAG = IngredientLoader.class.getSimpleName();

    private String mUrl;
    private int mItemPosition;

    public IngredientLoader(Context context, String url, int itemPosition) {
        super(context);
        mUrl = url;
        mItemPosition = itemPosition;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Ingredient> loadInBackground() {

        Log.v(LOG_TAG, "loadInbackground is initiated");

        if (mUrl == null) {
            return null;
        }

        List<Ingredient> ingredients = QueryUtils.fetchIngredientData(mUrl, mItemPosition);

        return ingredients;
    }
}
