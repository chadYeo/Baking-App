package com.example.salekb.bakingapp.recipe;


public class Recipe {
    private String mName;
    private int mServings;
    //private String mShortDescription;
    //private String mDescription;
    //private String mVideoURL;
    //private String mThumbnailURL;

    public Recipe (String name, int servings) {
        mName = name;
        mServings = servings;
    }

    public String getName() {
        return mName;
    }

    public int getServings() {
        return mServings;
    }
}
