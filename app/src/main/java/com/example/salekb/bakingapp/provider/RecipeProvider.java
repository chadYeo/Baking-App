package com.example.salekb.bakingapp.provider;


import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.tjeannin.provigen.ProviGenBaseContract;
import com.tjeannin.provigen.ProviGenOpenHelper;
import com.tjeannin.provigen.ProviGenProvider;
import com.tjeannin.provigen.annotation.Column;
import com.tjeannin.provigen.annotation.ContentUri;

public class RecipeProvider extends ProviGenProvider {

    public static final String COLUMN_RECIPE_ID = "recipe_id";

    private static final String AUTHORITY = "content://com.example.salekb.bakingapp/";

    private static Class[] contracts = new Class[] {
            RecipeContract.class, IngredientsContract.class, StepsContract.class};

    @Override
    public SQLiteOpenHelper openHelper(Context context) {
        return new ProviGenOpenHelper(getContext(), "RecipeDatabase", null, 1, contracts);
    }

    @Override
    public Class[] contractClasses() {
        return contracts;
    }

    // Recipe data model contract.
    public interface RecipeContract extends ProviGenBaseContract {

        @Column(Column.Type.TEXT)
        String NAME = "name";

        @Column(Column.Type.INTEGER)
        String SERVINGS = "servings";

        @ContentUri
        Uri CONTENT_URI = Uri.parse(AUTHORITY + "recipe");
    }

    // Ingredients data model contract.
    public interface IngredientsContract extends ProviGenBaseContract {

        @Column(Column.Type.REAL)
        String QUANTITY = "quantity";

        @Column(Column.Type.TEXT)
        String MEASURE = "measure";

        @Column(Column.Type.TEXT)
        String INGREDIENT = "ingredient";

        @ContentUri
        Uri CONTENT_URI = Uri.parse(AUTHORITY + "ingredients");
    }

    // Steps data model contract.
    public interface StepsContract extends ProviGenBaseContract {

        @Column(Column.Type.TEXT)
        String SHORTDESCRIPTION = "shortDescription";

        @Column(Column.Type.TEXT)
        String DESCRIPTION = "description";

        @Column(Column.Type.TEXT)
        String VIDEOURL = "videoURL";

        @ContentUri
        Uri CONTENT_URI = Uri.parse(AUTHORITY + "steps");
    }
}
