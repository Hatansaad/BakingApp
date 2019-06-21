package com.example.hatansaad.baking_app;

import android.os.Parcelable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  RecipeDetailFragment.OnStepSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = new Bundle();
        RecipeListFragment listFragment = new RecipeListFragment ();
        listFragment.setArguments (bundle);

        getSupportFragmentManager ().beginTransaction ()
                .replace (R.id.frame_container, listFragment)
                .commit ();

    }

    @Override
    public void onStepSelected(List<Step> steps, int position) {

            boolean tabletSize = getResources().getBoolean(R.bool.isTablet);

            if (tabletSize) {
                //openFragment(R.id.recipe_detail_container, steps, position);
            } else {
                openFragment(R.id.frame_container, steps, position);
            }

        shouldDisplayHomeUp ();
    }

    public void openFragment(int container, List<Step> steps, int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList ("steps",(ArrayList<? extends Parcelable>) steps);
        bundle.putParcelable ("step", steps.get(position));

        StepVideoFragment fragment = new StepVideoFragment ();
        fragment.setArguments (bundle);
        getSupportFragmentManager ().popBackStack("BACK_STACK_ROOT_TAG", getSupportFragmentManager ().POP_BACK_STACK_INCLUSIVE);

        getSupportFragmentManager ().beginTransaction ()
                .replace (container, fragment)
                .addToBackStack ("BACK_STACK_ROOT_TAG")
                .commit ();
    }

    public void shouldDisplayHomeUp(){
        boolean canBack = getSupportFragmentManager().getBackStackEntryCount()>0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canBack);
    }
}
