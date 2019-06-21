package com.example.hatansaad.baking_app;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.google.gson.Gson;

import java.util.List;

import static com.example.hatansaad.baking_app.RecipeDetailFragment.SHARED_PREF;
import static com.example.hatansaad.baking_app.RecipeDetailFragment.WIDGET_TEXT;

public class BakingAppWidgetProvider extends AppWidgetProvider {

    SharedPreferences sharedPreferences;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        sharedPreferences = context.getSharedPreferences("prefs",
                Context.MODE_PRIVATE);
        String result = sharedPreferences.getString("WIDGET_RESULT", null);
        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(result, Recipe.class);
        String recipeName = recipe.getRecipeName();
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipeName, recipe.getIngredients());
        }
    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId, String recipeName, List<Ingredient> ingredientList) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        views.setTextViewText(R.id.recipe_name_text_view, recipeName);
        views.removeAllViews(R.id.widget_ingredients_container);
        for (Ingredient ingredient : ingredientList) {
            RemoteViews ingredientView = new RemoteViews(context.getPackageName(),
                    R.layout.recipe_item);
            ingredientView.setTextViewText(R.id.ingredient_name_text_view,
                    String.valueOf(ingredient.getIngredientsName()) + " " +
                            String.valueOf(ingredient.getIngredientsMeasureType()));
            views.addView(R.id.widget_ingredients_container, ingredientView);
        }
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
