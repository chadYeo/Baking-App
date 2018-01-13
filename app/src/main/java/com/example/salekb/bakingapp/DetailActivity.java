package com.example.salekb.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.salekb.bakingapp.fragments.DetailViewFragment;

public class DetailActivity extends AppCompatActivity {

    private int itemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            itemPosition = extras.getInt("position");
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DetailViewFragment detailViewFragment = new DetailViewFragment();
        fragmentTransaction.add(R.id.detail_fragment_container, detailViewFragment);
        fragmentTransaction.commit();
    }
}