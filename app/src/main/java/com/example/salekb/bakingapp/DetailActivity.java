package com.example.salekb.bakingapp;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.salekb.bakingapp.fragments.DetailViewFragment;

public class DetailActivity extends AppCompatActivity {

    private Dialog mFullScreenDialog;
    private int itemPosition;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (getSupportFragmentManager().findFragmentByTag("Ingredient_Detail_TAG") != null) {
                getSupportFragmentManager().popBackStack("Ingredient_Detail_TAG", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                return true;
            } else if (getSupportFragmentManager().findFragmentByTag("Steps_Detail_TAG") != null){
                getSupportFragmentManager().popBackStack("Steps_Detail_TAG", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } else {
                super.onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

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

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentByTag("Ingredient_Detail_TAG") != null) {
            getSupportFragmentManager().popBackStack("Ingredient_Detail_TAG", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else if (getSupportFragmentManager().findFragmentByTag("Steps_Detail_TAG") != null){
            getSupportFragmentManager().popBackStack("Steps_Detail_TAG", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            super.onBackPressed();
        }
    }
}
