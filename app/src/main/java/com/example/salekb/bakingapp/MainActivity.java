package com.example.salekb.bakingapp;

import android.app.LoaderManager;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Recipe>> {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ProgressBar mProgressBar;
    private RecipeAdapter mAdapter;
    private TextView mEmptyTextView;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private static final int RECIPE_LOADER_ID = 1;

    private static final String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.isIndeterminate();

        mEmptyTextView = (TextView) findViewById(R.id.empty_textView);

        mRecyclerView = (RecyclerView) findViewById(R.id.recipe_recyclerView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecipeAdapter(new ArrayList<Recipe>());
        mRecyclerView.setAdapter(mAdapter);

        ConnectivityManager cm = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(RECIPE_LOADER_ID, null, this);
            Log.v(LOG_TAG, "loadermanager.initLoader is initiated");
        } else {
            mProgressBar.setVisibility(View.GONE);
            mEmptyTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<Recipe>> onCreateLoader(int i, Bundle bundle) {

        Log.v(LOG_TAG, "onCreateLoader is initiated");

        return new RecipeLoader(MainActivity.this, url);
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> recipes) {
        mProgressBar.setVisibility(View.GONE);

        if (recipes != null && !recipes.isEmpty()) {
            mEmptyTextView.setVisibility(View.GONE);
        }
        Log.v(LOG_TAG, "onLoadFinished is initiated");
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {
        Log.v(LOG_TAG, "onLoaderReset is initiated");
    }
}
