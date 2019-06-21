package com.example.hatansaad.baking_app;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientListAdapter  extends RecyclerView.Adapter<IngredientListAdapter.ViewHolder>{
    private Context context;
    private static List<Ingredient> ingredients;

    public IngredientListAdapter(List<Ingredient> ingredients, Context context) {
        this.context = context;
        this.ingredients = ingredients;
    }
    @NonNull
    @Override
    public IngredientListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ConstraintLayout layout = (ConstraintLayout) LayoutInflater.from(viewGroup.getContext ())
                .inflate (R.layout.ingredient_list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder (layout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientListAdapter.ViewHolder viewHolder, int i) {

        int quantity = (int) Math.round(ingredients.get (i).getIngredientsQuantity ());
        String measureType = ingredients.get(i).getIngredientsMeasureType ();
        String ingredientName = ingredients.get(i).getIngredientsName ();

        String ingredientItem = "• " + String.valueOf (quantity) + " " + measureType + " " + ingredientName;

        viewHolder.ingredientItemTV.setText (ingredientItem);

    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredientItemTextView)
        TextView ingredientItemTV;

        public ViewHolder(ConstraintLayout itemView) {
            super (itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
