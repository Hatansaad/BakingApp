package com.example.hatansaad.baking_app;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable{

    private String recipeName;
    private int recipeId;
    private int servings;
    private String imagePath;
    private List<Ingredient> ingredients = null;
    private List<Step> steps = null;

    public Recipe() { }

    public Recipe(String recipeName , int recipeId , int servings){
        this.recipeName=recipeName;
        this.recipeId=recipeId;
        this.servings=servings;
    }

    public Recipe(String recipeName , int recipeId , int servings,List<Ingredient> ingredients , List<Step> steps){
        this.recipeName=recipeName;
        this.recipeId=recipeId;
        this.servings=servings;
        this.ingredients=ingredients;
        this.steps=steps;
    }

    protected Recipe(Parcel in) {
        if (in.readByte() == 0x01) {
            ingredients = new ArrayList<>();
            in.readList(ingredients, Ingredient.class.getClassLoader());
        } else {
            ingredients = null;
        }
        if (in.readByte() == 0x01) {
            steps = new ArrayList<> ();
            in.readList(steps, Step.class.getClassLoader());
        } else {
            steps = null;
        }
        recipeId = in.readInt ();
        recipeName = in.readString ();
        servings = in.readInt ();
        imagePath = in.readString ();
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) { return new Recipe (in); }

        @Override
        public Recipe[] newArray(int size) { return new Recipe[size]; }
    };


    public void setRecipeName(String recipeName) { this.recipeName = recipeName; }
    public void setRecipeId(int recipeId) { this.recipeId = recipeId; }
    public void setServings(int servings) { this.servings = servings; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public void setIngredients(List<Ingredient> ingredients) { this.ingredients = ingredients; }
    public void setSteps(List<Step> steps) { this.steps = steps; }


    public String getRecipeName() { return recipeName; }
    public int getRecipeId() { return recipeId; }
    public int getServings() { return servings; }
    public String getImagePath() { return imagePath; }
    public List<Ingredient> getIngredients() { return ingredients; }
    public List<Step> getSteps() { return steps; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(recipeName);
        dest.writeInt (recipeId);
        dest.writeInt (servings);
        dest.writeString (imagePath);

        if (ingredients == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ingredients);
        }

        if (steps == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(steps);
        }
    }

}
