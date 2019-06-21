package com.example.hatansaad.baking_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeLisAdapter extends RecyclerView.Adapter<RecipeLisAdapter.ViewHolder>{

    private static List<Recipe> recipes;
    private Context context;
    private SharedPreferences sharedPreferences;

    public RecipeLisAdapter(List<Recipe> recipes , Context context){
        this.recipes=recipes;
        this.context=context;
        sharedPreferences = context.getSharedPreferences("prefs",
                Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public RecipeLisAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ConstraintLayout layout = (ConstraintLayout) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recipe_name_list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(layout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeLisAdapter.ViewHolder viewHolder, int i) {
        viewHolder.recipeNameTV.setText (String.valueOf(recipes.get(i).getRecipeName ()));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipeName)
        TextView recipeNameTV;
        @BindView(R.id.recipeNameCardView)
        CardView cardView;

        public ViewHolder(ConstraintLayout itemView) {
            super (itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<Ingredient> ingredientList = new ArrayList<>();
                    List<Step> stepList = new ArrayList<>();

                    ingredientList = recipes.get(getAdapterPosition()).getIngredients();
                    stepList = recipes.get(getAdapterPosition()).getSteps();

                    Gson gson = new Gson();
                    String ingredientJson = gson.toJson(ingredientList);
                    String stepJson = gson.toJson(stepList);

                    String resultJson = gson.toJson(recipes.get(getAdapterPosition()));
                    sharedPreferences.edit().putString("WIDGET_RESULT", resultJson).apply();

                   //start replacing fragment
                    Bundle bundle = new Bundle();
                    bundle.putParcelable ("recipe",recipes.get(getAdapterPosition()));
                    RecipeDetailFragment fragment = new RecipeDetailFragment ();
                    fragment.setArguments (bundle);

                    //((FragmentActivity)context).getSupportFragmentManager().addOnBackStackChangedListener (null);
                    ((FragmentActivity)context).getSupportFragmentManager().beginTransaction ()
                            .replace (R.id.frame_container, fragment)
                            .addToBackStack (null)
                            .commit ();
                }
            });

        }
    }
}
