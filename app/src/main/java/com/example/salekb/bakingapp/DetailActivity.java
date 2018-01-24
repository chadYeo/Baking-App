package com.example.salekb.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.salekb.bakingapp.fragments.DetailIngredientsFragment;
import com.example.salekb.bakingapp.fragments.DetailStepsFragment;
import com.example.salekb.bakingapp.fragments.DetailViewFragment;

public class DetailActivity extends AppCompatActivity {

    private boolean mTwoPane;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            selectPopBackStackDetailTag();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DetailViewFragment detailViewFragment = new DetailViewFragment();
        DetailIngredientsFragment detailIngredientsFragment = new DetailIngredientsFragment();

        Bundle bundle = new Bundle();

        if (findViewById(R.id.detail_largeScreen_fragment_container) != null) {
            mTwoPane = true;
            bundle.putBoolean("mTwoPane", true);
            fragmentTransaction.add(R.id.detail_fragment_container, detailViewFragment);
            fragmentTransaction.add(R.id.detail_largeScreen_fragment_container, detailIngredientsFragment);
            fragmentTransaction.commit();
        } else {
            mTwoPane = false;
            bundle.putBoolean("mTwoPane", false);
            fragmentTransaction.add(R.id.detail_fragment_container, detailViewFragment);
            fragmentTransaction.commit();
        }
        detailViewFragment.setArguments(bundle);
    }

    @Override
    public void onBackPressed() {
        selectPopBackStackDetailTag();
    }

    private void selectPopBackStackDetailTag() {
        if (getSupportFragmentManager().findFragmentByTag("Ingredient_Detail_TAG") != null) {
            getSupportFragmentManager().popBackStack("Ingredient_Detail_TAG", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else if (getSupportFragmentManager().findFragmentByTag("Steps_Detail_TAG") != null){
            getSupportFragmentManager().popBackStack("Steps_Detail_TAG", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            super.onBackPressed();
        }
    }
}
