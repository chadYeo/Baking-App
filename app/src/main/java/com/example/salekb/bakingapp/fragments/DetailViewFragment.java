package com.example.salekb.bakingapp.fragments;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.salekb.bakingapp.R;
import com.example.salekb.bakingapp.steps.Steps;
import com.example.salekb.bakingapp.steps.StepsAdapter;
import com.example.salekb.bakingapp.steps.StepsLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailViewFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<Steps>> {

    private static final int DETAILVIEW_LOADER_ID = 3835;
    private Button mRecipeIngredientsButton;
    private ProgressBar mProgressbar;
    private TextView mEmptyTextView;
    private RecyclerView mStepsRecyclerView;
    private StepsAdapter mStepsAdapter;

    private static final String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static final String LOG_TAG = DetailViewFragment.class.getSimpleName();

    public DetailViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_view, container, false);

        mProgressbar = (ProgressBar) view.findViewById(R.id.detail_progressBar);
        mEmptyTextView = (TextView) view.findViewById(R.id.detail_empty_textView);

        mStepsRecyclerView = (RecyclerView) view.findViewById(R.id.detailView_steps_recyclerView);
        mStepsRecyclerView.setHasFixedSize(true);

        mStepsAdapter = new StepsAdapter(new ArrayList<Steps>());
        mStepsRecyclerView.setAdapter(mStepsAdapter);

        mRecipeIngredientsButton = (Button) view.findViewById(R.id.recipe_ingredients_button);

        mRecipeIngredientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DetailIngredientsFragment detailIngredientsFragment = new DetailIngredientsFragment();
                fragmentTransaction.replace(R.id.detail_fragment_container, detailIngredientsFragment);
                fragmentTransaction.addToBackStack("Ingredient_Detail_TAG");
                fragmentTransaction.commit();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mStepsRecyclerView.setLayoutManager(layoutManager);

        ConnectivityManager cm = (ConnectivityManager)view.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getActivity().getLoaderManager();
            loaderManager.initLoader(DETAILVIEW_LOADER_ID, null, this);

            Log.v(LOG_TAG, "loaderManager.initLoader is initiated");
        } else {
            mProgressbar.setVisibility(View.GONE);
            mEmptyTextView.setText(R.string.no_internet_connection);
        }
        return view;
    }

    @Override
    public Loader<List<Steps>> onCreateLoader(int i, Bundle bundle) {
        Log.v(LOG_TAG, "onCreateLoader is initiated");
        Bundle extras = getActivity().getIntent().getExtras();
        int position = 0;
        if (extras != null) {
            position = extras.getInt("position");
            Log.v(LOG_TAG, "onCreateLoader with item position: " + position);
        }
        return new StepsLoader(getContext(), url, position);
    }

    @Override
    public void onLoadFinished(Loader<List<Steps>> loader, List<Steps> data) {
        mProgressbar.setVisibility(View.GONE);

        if (data != null && !data.isEmpty()) {
            mEmptyTextView.setVisibility(View.GONE);
            mStepsAdapter.steps.addAll(data);
            mStepsRecyclerView.setAdapter(mStepsAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Steps>> loader) {
        loader.reset();
        Log.v(LOG_TAG, "onLoaderReset is initiated");
    }
}
