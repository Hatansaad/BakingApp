package com.example.hatansaad.baking_app;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailFragment extends Fragment implements CallBack{

    @BindView(R.id.ingredientsRecyclerView)
    RecyclerView mIngredientsRecyclerView;
    private RecyclerView.LayoutManager mIngredientLayoutManager;
    private static RecyclerView.Adapter mIngredientsAdapter;
    @BindView(R.id.stepsRecyclerView) RecyclerView mStepsRecyclerView;
    private static RecyclerView.Adapter mStepsAdapter;
    private RecyclerView.LayoutManager mStepLayoutManager;
    private Recipe recipe;
    RecipeDetailFragment.OnStepSelectedListener mCallback;
    private Parcelable mListState;
    //widget
    public static final String SHARED_PREF = "prefs";
    public static final String  WIDGET_TEXT="WidgetText";
    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    @BindView(R.id.widgetButton)
    Button mWidgetButton;
    Gson gson;


    public interface OnStepSelectedListener {
        void onStepSelected(List<Step> steps, int position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        recipe = getArguments().getParcelable ("recipe");
        gson = new Gson();

    }


    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        mListState = mStepLayoutManager.onSaveInstanceState();
        state.putParcelable("test", mListState);
    }

    public void onRestoreInstanceState(Bundle outState) {
        if (outState != null) {
            mListState = outState.getParcelable ("test");

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mListState != null) {
            mStepLayoutManager.onRestoreInstanceState(mListState);
        }else{
            System.out.println("list is null");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, view);

        if(((AppCompatActivity)getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle (Constant.DETAILS);
        }


        System.out.println("recipes size is "+recipe.getIngredients().size());

        //Ingredients
        setIngRecyclerView();
        //Steps
        setStepsRecyclerView();

        mWidgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getActivity()
                        .getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
                Recipe result = recipe;
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getActivity());
                Bundle bundle = new Bundle();
                int appWidgetId = bundle.getInt(
                        AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID);
                BakingAppWidgetProvider.updateAppWidget(getActivity(), appWidgetManager, appWidgetId, result.getRecipeName(),
                        result.getIngredients());
                Toast.makeText(getActivity(), result.getRecipeName() + " has been added to Widget.", Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }

    private void setIngRecyclerView() {
        mIngredientLayoutManager = new LinearLayoutManager(getActivity ().getApplicationContext ());
        mIngredientsRecyclerView.setLayoutManager (mIngredientLayoutManager);

        mIngredientsAdapter = new IngredientListAdapter (recipe.getIngredients (), getContext ());
        mIngredientsRecyclerView.setAdapter (mIngredientsAdapter);
        mIngredientsRecyclerView.setNestedScrollingEnabled (false);


    }
    private void setStepsRecyclerView() {
        mStepLayoutManager = new LinearLayoutManager (getActivity ().getApplicationContext ());
        mStepsRecyclerView.setLayoutManager (mStepLayoutManager);

        mStepsAdapter = new StepListAdapter (recipe.getSteps (), getContext (),mCallback);
        mStepsRecyclerView.setAdapter (mStepsAdapter);
        mStepsRecyclerView.setNestedScrollingEnabled (false);
    }

    @Override
    public void onClick(Context context, Integer id, String description, String url, int stepPosition) {


    }
}
