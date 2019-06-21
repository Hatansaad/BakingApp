package com.example.hatansaad.baking_app;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

public class Ingredient implements Parcelable {

    private double ingredientsQuantity;
    private String ingredientsMeasureType;
    private String ingredientsName;

    public Ingredient() { }

    public Ingredient(double ingredientsQuantity,String ingredientsMeasureType , String ingredientsName){
        this.ingredientsQuantity=ingredientsQuantity;
        this.ingredientsMeasureType=ingredientsMeasureType;
        this.ingredientsName=ingredientsName;
    }

    protected Ingredient(Parcel in) {
        ingredientsQuantity = in.readDouble ();
        ingredientsMeasureType = in.readString ();
        ingredientsName = in.readString ();
    }


    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient> () {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient (in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public void setIngredientsQuantity(double ingredientsQuantity) { this.ingredientsQuantity = ingredientsQuantity; }
    public void setIngredientsMeasureType(String ingredientsMeasureType) { this.ingredientsMeasureType = ingredientsMeasureType; }
    public void setIngredientsName(String ingredientsName) { this.ingredientsName = ingredientsName; }

    public double getIngredientsQuantity() { return ingredientsQuantity; }
    public String getIngredientsMeasureType() { return ingredientsMeasureType; }
    public String getIngredientsName() { return ingredientsName; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble (ingredientsQuantity);
        dest.writeString (ingredientsMeasureType);
        dest.writeString (ingredientsName);
    }

    @NonNull
    @Override
    public String toString() {
        return ingredientsQuantity+" "+ingredientsMeasureType+" "+ingredientsName;
    }
}
