package com.example.salekb.bakingapp.fragments;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.salekb.bakingapp.R;
import com.example.salekb.bakingapp.steps.Steps;
import com.example.salekb.bakingapp.steps.StepsAdapter;
import com.example.salekb.bakingapp.steps.StepsLoader;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.ArrayList;
import java.util.HashMap;
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
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;

    private int stepsPosition;
    private int numberOfSteps;

    private static final String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static final String LOG_TAG = DetailStepsFragment.class.getSimpleName();

    public DetailStepsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_steps, container, false);

        mStepsAdapter = new StepsAdapter(new ArrayList<Steps>());

        Bundle extras_stepsPosition = this.getArguments();
        stepsPosition = extras_stepsPosition.getInt("stepsPosition");

        mProgressbar = (ProgressBar)view.findViewById(R.id.detail_steps_progressBar);
        mPlayerView = (SimpleExoPlayerView)view.findViewById(R.id.steps_playerView);
        mDetailSteps_textView = (TextView)view.findViewById(R.id.detail_steps_textView);
        mArrowBackImageButton = (ImageButton)view.findViewById(R.id.arrow_back_imageButton);
        mArrowForwardImageButton = (ImageButton)view.findViewById(R.id.arrow_forward_imageButton);

        mArrowBackImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stepsPosition == 0) {
                    return;
                } else {
                    stepsPosition = stepsPosition - 1;
                    String previousDescription = mStepsAdapter.steps.get(stepsPosition).getDescription();
                    mDetailSteps_textView.setText(previousDescription);
                }
            }
        });

        mArrowForwardImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stepsPosition == numberOfSteps - 1) {
                    return;
                } else {
                    stepsPosition = stepsPosition + 1;
                    String nextDescription = mStepsAdapter.steps.get(stepsPosition).getDescription();
                    mDetailSteps_textView.setText(nextDescription);
                }
            }
        });

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
            numberOfSteps = data.size();

            String description = data.get(stepsPosition).getDescription();
            mDetailSteps_textView.setText(description);

            String videoURL = data.get(stepsPosition).getVideoURL();
            mPlayerView.setDefaultArtwork(retrieveVideoFrameFromVideo(videoURL));
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Steps>> loader) {
        loader.reset();
        Log.v(LOG_TAG, "onLoaderReset is initiated");
    }

    public static Bitmap retrieveVideoFrameFromVideo(String videoPath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14) {
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            } else {
                mediaMetadataRetriever.setDataSource(videoPath);
            }
            bitmap = mediaMetadataRetriever.getFrameAtTime(1000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }
}
