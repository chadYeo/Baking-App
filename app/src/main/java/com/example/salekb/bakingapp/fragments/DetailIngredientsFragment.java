package com.example.salekb.bakingapp.fragments;


import android.app.LoaderManager;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.salekb.bakingapp.R;
import com.example.salekb.bakingapp.ingredient.Ingredient;
import com.example.salekb.bakingapp.ingredient.IngredientAdapter;
import com.example.salekb.bakingapp.ingredient.IngredientLoader;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailIngredientsFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<Ingredient>> {

    private static final int INGREDIENTS_LOADER_ID = 3;
    private static final String LOG_TAG = DetailIngredientsFragment.class.getSimpleName();
    private static final String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private ProgressBar mProgressBar;
    private IngredientAdapter mAdapter;
    private TextView mEmptyTextView;
    private RecyclerView mRecyclerView;

    public DetailIngredientsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_ingredients, container, false);

        mProgressBar = (ProgressBar) view.findViewById(R.id.detail_ingredients_progressBar);
        mEmptyTextView = (TextView) view.findViewById(R.id.detail_ingredients_empty_textView);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.ingredients_recyclerView);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new IngredientAdapter(new ArrayList<Ingredient>());
        mRecyclerView.setAdapter(mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        ConnectivityManager cm = (ConnectivityManager)view.getContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getActivity().getLoaderManager();
            loaderManager.initLoader(INGREDIENTS_LOADER_ID, null, this);

            //LoaderManager loaderManager = getLoaderManager();
            //loaderManager.initLoader(RECIPE_LOADER_ID, null, this);

            //getActivity().getSupportLoaderManager().initLoader(INGREDIENTS_LOADER_ID, null, this);
            Log.v(LOG_TAG, "loadermanager.initLoader is initiated");
        } else {
            mProgressBar.setVisibility(View.GONE);
            mEmptyTextView.setText(R.string.no_internet_connection);
        }
        return view;
    }

    @Override
    public Loader<List<Ingredient>> onCreateLoader(int i, Bundle bundle) {
        Log.v(LOG_TAG, "onCreateLoader is initiated");

        return new IngredientLoader(getContext(), url);
    }

    @Override
    public void onLoadFinished(Loader<List<Ingredient>> loader, List<Ingredient> data) {
        mProgressBar.setVisibility(View.GONE);

        if (data != null && !data.isEmpty()) {
            mEmptyTextView.setVisibility(View.GONE);
            mAdapter.ingredients.addAll(data);
            mRecyclerView.setAdapter(mAdapter);
        }
        Log.v(LOG_TAG, "onLoadFinished is initiated: " + data.get(0).getIngredient().toString());
    }

    @Override
    public void onLoaderReset(Loader<List<Ingredient>> loader) {
        loader.reset();
        Log.v(LOG_TAG, "onLoaderReset is initiated");
    }
}
