package com.example.hatansaad.baking_app;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListFragment extends Fragment {

    @BindView(R.id.recipeRecyclerView)
    RecyclerView mRecipeRecyclerView;
    RecyclerView.Adapter mRecipeAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        ButterKnife.bind(this, view);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle (Constant.MAIN_TITLE);


        mLayoutManager = new LinearLayoutManager(getActivity ().getApplicationContext ());
        mRecipeRecyclerView.setLayoutManager(mLayoutManager);

        new RecipeAsyncTask().execute ();

        return view;
    }

    private class RecipeAsyncTask extends AsyncTask<String, Void, Recipe[]> {
        @Override
        protected Recipe[] doInBackground(String... strings) {
            try {

                URL url = Json.buildBakingUrl ();
                String bakingResults = Json.getResponseFromHttpUrl(url);
                Recipe[] list=null;
                try {
                    list = Json.setRecipesArray(bakingResults);
                }catch (Exception e){
                    e.printStackTrace();
                }

                //System.out.println("RESUALTS IS LIST SIZE "+list.size());
                return list;

            } catch (IOException e) {
                e.printStackTrace ();
            }
            return null;
        }
        protected void onPostExecute(Recipe[] recipes) {
            List<Recipe> recipeList = new ArrayList<>();
            for(int i=0;i<recipes.length;i++){
               recipeList.add(recipes[i]);
            }
            mRecipeAdapter = new RecipeLisAdapter (recipeList, getContext ());
            mRecipeRecyclerView.setAdapter(mRecipeAdapter);

        }
    }
}
