package com.example.salekb.bakingapp.fragments;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.salekb.bakingapp.QueryUtils;
import com.example.salekb.bakingapp.R;
import com.example.salekb.bakingapp.steps.Steps;
import com.example.salekb.bakingapp.steps.StepsAdapter;
import com.example.salekb.bakingapp.steps.StepsLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailStepsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Steps>> {

    private static final int DETAIL_STEP_LOADER_ID = 4845;
    private ProgressBar mProgressbar;
    private TextView mDetailSteps_textView;
    private ImageButton mArrowBackImageButton;
    private ImageButton mArrowForwardImageButton;
    private StepsAdapter mStepsAdapter;

    private static final String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static final String LOG_TAG = DetailStepsFragment.class.getSimpleName();

    public DetailStepsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_steps, container, false);

        mProgressbar = (ProgressBar)view.findViewById(R.id.detail_steps_progressBar);
        mDetailSteps_textView = (TextView)view.findViewById(R.id.detail_steps_textView);
        mArrowBackImageButton = (ImageButton)view.findViewById(R.id.arrow_back_imageButton);
        mArrowForwardImageButton = (ImageButton)view.findViewById(R.id.arrow_forward_imageButton);

        mStepsAdapter = new StepsAdapter(new ArrayList<Steps>());

        ConnectivityManager cm = (ConnectivityManager)view.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getActivity().getLoaderManager();
            loaderManager.initLoader(DETAIL_STEP_LOADER_ID, null, this);

            Log.v(LOG_TAG, "loaderManager.initLoader is initiated");
        } else {
            mProgressbar.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public Loader<List<Steps>> onCreateLoader(int i, Bundle bundle) {
        Bundle extras_recipe = getActivity().getIntent().getExtras();
        int recipePosition = extras_recipe.getInt("position");

        return new StepsLoader(getContext(), url, recipePosition);
    }

    @Override
    public void onLoadFinished(Loader<List<Steps>> loader, List<Steps> data) {
        mProgressbar.setVisibility(View.GONE);

        if (data != null && !data.isEmpty()) {
            mStepsAdapter.steps.addAll(data);

            Bundle extras_stepsPosition = this.getArguments();
            int stepsPosition = extras_stepsPosition.getInt("stepsPosition");

            String description = data.get(stepsPosition).getDescription();
            mDetailSteps_textView.setText(description);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Steps>> loader) {
        loader.reset();
        Log.v(LOG_TAG, "onLoaderReset is initiated");
    }
}
