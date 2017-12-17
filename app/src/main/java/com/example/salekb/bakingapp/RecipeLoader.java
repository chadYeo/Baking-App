package com.example.salekb.bakingapp;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class RecipeLoader extends AsyncTaskLoader<List<Recipe>> {

    private static final String LOG_TAG = RecipeLoader.class.getSimpleName();

    private String mUrl;

    public RecipeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Recipe> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<Recipe> recipes = QueryUtils.fetchRecipeData(mUrl);

        Log.v(LOG_TAG, "loadInbackground is initiated");

        return recipes;
    }
}
