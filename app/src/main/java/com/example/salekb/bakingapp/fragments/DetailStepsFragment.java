package com.example.salekb.bakingapp.fragments;


import android.app.Dialog;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salekb.bakingapp.R;
import com.example.salekb.bakingapp.steps.Steps;
import com.example.salekb.bakingapp.steps.StepsAdapter;
import com.example.salekb.bakingapp.steps.StepsLoader;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailStepsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Steps>> {

    private static final int DETAIL_STEP_LOADER_ID = 4845;
    private ProgressBar mProgressbar;
    private ImageView mNoVideoImageView;
    private TextView mDetailSteps_textView;
    private ImageButton mArrowBackImageButton;
    private ImageButton mArrowForwardImageButton;
    private StepsAdapter mStepsAdapter;
    private FrameLayout mExoMediaFrame;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mSimpleExoPlayerView;
    private FrameLayout mExoFullScreenButton;
    private ImageView mExoFullScreenIcon;
    private Dialog mFullScreenDialog;

    private boolean mIsExoPlayerFullscreen;
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
        mNoVideoImageView = (ImageView)view.findViewById(R.id.image_no_video_imageView);
        mExoMediaFrame = (FrameLayout)view.findViewById(R.id.main_media_frame);
        mSimpleExoPlayerView = (SimpleExoPlayerView)view.findViewById(R.id.steps_playerView);
        mExoFullScreenButton = (FrameLayout)view.findViewById(R.id.exo_fullscreen_button);
        mExoFullScreenIcon = (ImageView)view.findViewById(R.id.exo_fullscreen_icon);
        mDetailSteps_textView = (TextView)view.findViewById(R.id.detail_steps_textView);
        mArrowBackImageButton = (ImageButton)view.findViewById(R.id.arrow_back_imageButton);
        mArrowForwardImageButton = (ImageButton)view.findViewById(R.id.arrow_forward_imageButton);

        mFullScreenDialog = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        mExoFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mIsExoPlayerFullscreen) {
                    openFullscreenDialog();
                } else {
                    closeFullscreenDialog();
                }
            }
        });

        mArrowBackImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stepsPosition == 0) {
                    return;
                } else {
                    stepsPosition = stepsPosition - 1;

                    String previousDescription = mStepsAdapter.steps.get(stepsPosition).getDescription();
                    mDetailSteps_textView.setText(previousDescription);

                    String previousVideoURL = mStepsAdapter.steps.get(stepsPosition).getVideoURL();
                    releasePlayer();
                    initializePlayer(Uri.parse(previousVideoURL));
                    if (previousVideoURL.isEmpty()) {
                        mSimpleExoPlayerView.hideController();
                        mSimpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.image_no_video));
                    }
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

                    String nextVideoURL = mStepsAdapter.steps.get(stepsPosition).getVideoURL();
                    releasePlayer();
                    initializePlayer(Uri.parse(nextVideoURL));
                    if (nextVideoURL.isEmpty()) {
                        mSimpleExoPlayerView.hideController();
                        mSimpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.image_no_video));
                    }
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
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!mStepsAdapter.steps.get(stepsPosition).getVideoURL().isEmpty()) {
            releasePlayer();
        }
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
            if (videoURL.isEmpty()) {
                mSimpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.image_no_video));
                mSimpleExoPlayerView.hideController();
            }
            initializePlayer(Uri.parse(videoURL));
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Steps>> loader) {
        loader.reset();
        Log.v(LOG_TAG, "onLoaderReset is initiated");
    }

    /**
     * Initialize ExoPlayer
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mSimpleExoPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource
            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    /**
     * Release ExoPlayer
     */
    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    private void closeFullscreenDialog() {
        ((ViewGroup) mSimpleExoPlayerView.getParent()).removeView(mSimpleExoPlayerView);
        mExoMediaFrame.addView(mSimpleExoPlayerView);
        mIsExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
        mExoFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_fullscreen_expand));
    }

    private void openFullscreenDialog() {
        ((ViewGroup)mSimpleExoPlayerView.getParent()).removeView(mSimpleExoPlayerView);
        mFullScreenDialog.addContentView(mSimpleExoPlayerView,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mExoFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_fullscreen_shrink));
        mIsExoPlayerFullscreen = true;
        mFullScreenDialog.show();
    }
}
