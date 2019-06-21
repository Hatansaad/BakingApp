package com.example.hatansaad.baking_app;

import androidx.lifecycle.LiveData;

import android.net.Uri;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Json {

    private static final String RESULTS_KEY = "results";
    private final static String NAME_KEY = "name";
    private final static String ID_KEY = "id";
    private final static String SERVINGS_KEY = "servings";
    private final static String INGREDIENTS_KEY = "ingredients";
    private final static String STEPS_KEY = "steps";
    private final static String INGREDIENTS_QUANTITY_KEY = "quantity";
    private final static String INGREDIENTS_MEASURE_KEY = "measure";
    private final static String INGREDIENT_NAME_KEY = "ingredient";
    private final static String STEP_SHORT_DESC_KEY = "shortDescription";
    private final static String STEP_LONG_DESC_KEY = "description";
    private final static String STEP_VIDEO_URL = "videoURL";


    public static URL buildBakingUrl() {
        Uri builtUri = Uri.parse (Constant.BAKING_JSON_URL);

        URL url = null;
        try {
            url = new URL (builtUri.toString ());
        } catch (MalformedURLException e) {
            e.printStackTrace ();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection ();
        try {
            InputStream in = urlConnection.getInputStream ();

            Scanner scanner = new Scanner (in);
            scanner.useDelimiter ("\\A");

            boolean hasInput = scanner.hasNext ();
            if (hasInput) {
                return scanner.next ();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect ();
        }
    }

    public static List<Recipe> extractFeatureFromJson(String recipeJSONRes) {

        if (TextUtils.isEmpty(recipeJSONRes)) {
            return null;
        }
        List<Recipe> recipes = new ArrayList<>();
        List<Ingredient> ingredients = new ArrayList<>();
        List<Step> steps =new ArrayList<>();
        Recipe recipe;

        try {

            JSONArray recipesArray = new JSONArray(recipeJSONRes);
            System.out.println("SIZZEEEE11 "+recipesArray.length());
                for (int i = 0; i < recipesArray.length(); i++) {

                    ingredients.clear();
                    steps.clear();

                    JSONObject currentRecipe = recipesArray.getJSONObject(i);


                    String recipeName = currentRecipe.getString(NAME_KEY);
                    int recipeID = currentRecipe.getInt(ID_KEY);
                    int recipeServings = currentRecipe.getInt(SERVINGS_KEY);

                    //recipe = new Recipe(recipeName,recipeID,recipeServings);

                    // INGREDIENTS
                    JSONArray ingredientsArray = currentRecipe.getJSONArray(INGREDIENTS_KEY);
                    // STEPS
                    JSONArray stepsArray = currentRecipe.getJSONArray (STEPS_KEY);

                    // For loop to get ingredients
                    System.out.println("SIZZEEEE222 "+ingredientsArray.length());
                    //ingredients.clear();
                    for (int j = 0; j < ingredientsArray.length(); j++) {

                        JSONObject ingredient = ingredientsArray.getJSONObject(j);

                        double quantity = ingredient.getDouble(INGREDIENTS_QUANTITY_KEY);
                        String measure = ingredient.getString(INGREDIENTS_MEASURE_KEY);
                        String name = ingredient.getString(INGREDIENT_NAME_KEY);
                        Ingredient currentIngredient = new Ingredient(quantity,measure,name);
                        ingredients.add(currentIngredient);
                    }

                    // For loop to get steps
                    //steps.clear();
                    System.out.println("SIZZEEEE333 "+stepsArray.length());
                    for (int k = 0; k < stepsArray.length (); k++) {
                        JSONObject step = stepsArray.getJSONObject (k);

                        int id = step.getInt(ID_KEY);
                        String shortDesc = step.getString(STEP_SHORT_DESC_KEY);
                        String longDesc = step.getString(STEP_LONG_DESC_KEY);
                        String video;
                        if (step.getString (STEP_VIDEO_URL) == "") {
                            video=null;
                        } else {
                            video=step.getString(STEP_VIDEO_URL);
                        }

                        Step currentStep = new Step(id,shortDesc,longDesc,video);

                        steps.add(currentStep);

                    }

                    //System.out.println("recipe "+recipe.getRecipeName() +" ing size "+ingredients.size());
                    /*recipe.setIngredients(ingredients);
                    recipe.setSteps(steps);*/

                    //System.out.println("recipe "+recipe.getRecipeName() +" ing size "+recipe.getIngredients().size());
                    System.out.println("hello "+ingredients.size());
                    recipe=new Recipe(recipeName,recipeID,recipeServings,ingredients,steps);
                    recipes.add(recipe);
                    //System.out.println("123recipe "+recipes.get(i).getRecipeName() +" ing size "+recipes.get(i).getIngredients().size());
                }

                for(int i=0;i<recipes.size();i++){
                    System.out.println("recipe "+recipes.get(i).getRecipeName() +" ing size "+recipes.get(i).getIngredients().size());
                }

        } catch (JSONException e) {
            System.out.println("Error while parsing the json");
        }

        if (recipes == null){ System.out.println("recipes are null"); }
        else { System.out.println("recipe not null"); }
        return recipes;
    }

    public static Recipe[] setRecipesArray(String jsonResults) throws JSONException {

        Recipe[] recipes;


        JSONArray resultsArray = new JSONArray(jsonResults);
        recipes = new Recipe[resultsArray.length ()];

        for (int i = 0; i < resultsArray.length(); i++) {
            recipes[i] = new Recipe ();
             List<Ingredient> ingredientsList;
             Ingredient[] ingredients;
             List<Step> stepsList;
             Step[] steps;
            JSONObject recipe = resultsArray.getJSONObject(i);

            recipes[i].setRecipeName (recipe.getString(NAME_KEY));
            recipes[i].setRecipeId (recipe.getInt (ID_KEY));
            recipes[i].setServings (recipe.getInt (SERVINGS_KEY));

            // INGREDIENTS
            JSONArray ingredientsArray = recipe.getJSONArray(INGREDIENTS_KEY);
            ingredients = new Ingredient[ingredientsArray.length ()];
            ingredientsList = new ArrayList();

            // STEPS
            JSONArray stepsArray = recipe.getJSONArray (STEPS_KEY);
            steps = new Step[stepsArray.length ()];
            stepsList = new ArrayList();

            // For loop for getting/setting ingredients
            for (int j = 0; j < ingredientsArray.length(); j++) {
                JSONObject ingredient = ingredientsArray.getJSONObject(j);

                ingredients[j] = new Ingredient ();

                ingredients[j].setIngredientsQuantity (ingredient.getDouble (INGREDIENTS_QUANTITY_KEY));
                ingredients[j].setIngredientsMeasureType (ingredient.getString(INGREDIENTS_MEASURE_KEY).toLowerCase ());
                ingredients[j].setIngredientsName (ingredient.getString (INGREDIENT_NAME_KEY));

                ingredientsList.add(ingredients[j]);
            }

            // For loop for getting/setting steps
            for (int k = 0; k < stepsArray.length (); k++) {
                JSONObject step = stepsArray.getJSONObject (k);

                steps[k] = new Step();

                steps[k].setStepId (step.getInt (ID_KEY));
                steps[k].setStepLongDescription (step.getString (STEP_LONG_DESC_KEY));
                steps[k].setStepShortDescription (step.getString (STEP_SHORT_DESC_KEY));

                if (step.getString (STEP_VIDEO_URL) == "") {
                    steps[k].setStepVideoUrl(null);
                } else {
                    steps[k].setStepVideoUrl (step.getString (STEP_VIDEO_URL));
                }
                stepsList.add(steps[k]);
            }

            recipes[i].setIngredients (ingredientsList);
            recipes[i].setSteps (stepsList);

        }
        return recipes;
    }


}
