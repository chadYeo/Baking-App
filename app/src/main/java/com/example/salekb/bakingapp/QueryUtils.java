package com.example.salekb.bakingapp;


import android.text.TextUtils;
import android.util.Log;

import com.example.salekb.bakingapp.ingredient.Ingredient;
import com.example.salekb.bakingapp.recipe.Recipe;
import com.example.salekb.bakingapp.steps.Steps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public QueryUtils() {
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Error with creating URL", e);
        }
        Log.v(LOG_TAG, "Url is created");
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(100000);
            urlConnection.setReadTimeout(100000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
                Log.i(LOG_TAG, "HttpURLConnection is Good: 200");
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error with making HTTP Request", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        Log.v(LOG_TAG, "makeHttpRequest is initiated");
        Log.v(LOG_TAG, "jsonResponse is the following: " + jsonResponse);
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        Log.i(LOG_TAG, "readFromStream is initiated");

        return output.toString();
    }

    public static List<Recipe> fetchRecipeData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException", e);
        }

        ArrayList<Recipe> recipeArrayList = extractRecipeFromJson(jsonResponse);

        Log.i(LOG_TAG, "fetching data: " + url);

        return recipeArrayList;
    }

    public static List<Ingredient> fetchIngredientData(String requestUrl, int position) {
        URL url = createUrl(requestUrl);
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Ingredient> ingredientArrayList = extractIngredientsFromJson(jsonResponse, position);

        Log.i(LOG_TAG, "fetching ingredient data: " + url);

        return ingredientArrayList;
    }

    public static List<Steps> fetchStepsData(String requestUrl, int position) {
        URL url = createUrl(requestUrl);
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Steps> stepsArrayList = extractStepsFromJson(jsonResponse, position);

        Log.i(LOG_TAG, "fetching steps data: " + url);

        return stepsArrayList;
    }

    private static ArrayList<Recipe> extractRecipeFromJson(String recipeJSON) {
        if (TextUtils.isEmpty(recipeJSON)) {
            return null;
        }

        ArrayList<Recipe> recipeArrayList = new ArrayList<>();

        Log.i(LOG_TAG, "extractRecipeFromJson is initiated");

        try {
            JSONArray baseJsonResponses = new JSONArray(recipeJSON);

            for (int i=0; i<baseJsonResponses.length(); i++) {
                JSONObject currentItem = baseJsonResponses.getJSONObject(i);

                String name = currentItem.getString("name");;
                int servings = currentItem.getInt("servings");

                Log.v(LOG_TAG, "recipe arraylist: " + name + " - " + servings);
                recipeArrayList.add(new Recipe(name, servings));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipeArrayList;
    }

    private static ArrayList<Ingredient> extractIngredientsFromJson(String ingredientJSON, int position) {
        if (TextUtils.isEmpty(ingredientJSON)) {
            return null;
        }

        ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();

        Log.i(LOG_TAG, "extractIngredientsFromJson is initiated");

        try {
            JSONArray baseJsonResponses = new JSONArray(ingredientJSON);
            JSONObject currentItem = baseJsonResponses.getJSONObject(position);
            JSONArray ingredients = currentItem.getJSONArray("ingredients");
            for (int i=0; i<ingredients.length(); i++) {
                JSONObject ingredientStep = ingredients.getJSONObject(i);
                int quantity = ingredientStep.getInt("quantity");
                String measure = ingredientStep.getString("measure");
                String ingredient = ingredientStep.getString("ingredient");
                ingredientArrayList.add(new Ingredient(quantity, measure, ingredient));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ingredientArrayList;
    }

    private static ArrayList<Steps> extractStepsFromJson(String stepsJSON, int position) {
        if (TextUtils.isEmpty(stepsJSON)) {
            return null;
        }

        ArrayList<Steps> stepsArrayList = new ArrayList<>();

        Log.i(LOG_TAG, "extractStepsFromJson is initiated");

        try {
            JSONArray baseJsonResponses = new JSONArray(stepsJSON);
            JSONObject currentItem = baseJsonResponses.getJSONObject(position);
            JSONArray steps = currentItem.getJSONArray("steps");
            for (int i=0; i<steps.length(); i++) {
                JSONObject recipeStep = steps.getJSONObject(i);
                String shortDescription = recipeStep.getString("shortDescription");
                String description = recipeStep.getString("description");
                String videoURL = recipeStep.getString("videoURL");
                String thumbnailURL = recipeStep.getString("thumbnailURL");
                stepsArrayList.add(new Steps(shortDescription, description, videoURL, thumbnailURL));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stepsArrayList;
    }
}
