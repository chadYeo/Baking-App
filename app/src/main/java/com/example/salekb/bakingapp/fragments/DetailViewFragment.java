package com.example.salekb.bakingapp.fragments;


import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.salekb.bakingapp.R;
import com.example.salekb.bakingapp.recipe.Recipe;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailViewFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Recipe>> {

    private static final int DETAILVIEW_LOADER_ID = 1;
    private Button mRecipeIngredientsButton;

    public DetailViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_view, container, false);
        mRecipeIngredientsButton = (Button) v.findViewById(R.id.recipe_ingredients_button);

        mRecipeIngredientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DetailIngredientsFragment detailIngredientsFragment = new DetailIngredientsFragment();
                fragmentTransaction.replace(R.id.detail_fragment_container, detailIngredientsFragment);
                fragmentTransaction.commit();
            }
        });

        return v;
    }

    @Override
    public Loader<List<Recipe>> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> recipeList) {

    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {

    }
}
