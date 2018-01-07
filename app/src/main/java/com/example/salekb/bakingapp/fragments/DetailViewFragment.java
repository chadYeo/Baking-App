package com.example.salekb.bakingapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.example.salekb.bakingapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailViewFragment extends Fragment {


    public DetailViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_detail_view, container, false);

        return v;
    }

}
