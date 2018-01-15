package com.example.salekb.bakingapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.salekb.bakingapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailStepsFragment extends Fragment {

    public DetailStepsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_steps, container, false);
    }

}
