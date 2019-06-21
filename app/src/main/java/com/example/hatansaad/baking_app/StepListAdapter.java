package com.example.hatansaad.baking_app;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.ViewHolder> {

    private Context context;
    private static List<Step> steps;
    //RecipeDetailFragment.OnStepSelectedListener listener;
    private CallBack clickCallBack;

    public StepListAdapter(List<Step> steps, Context context,RecipeDetailFragment.OnStepSelectedListener listener) {
        this.context = context;
        this.steps = steps;
        //this.listener = listener;
    }

    @NonNull
    @Override
    public StepListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ConstraintLayout layout = (ConstraintLayout) LayoutInflater.from (viewGroup.getContext ())
                .inflate (R.layout.step_list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder (layout);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StepListAdapter.ViewHolder viewHolder, int i) {
        String shortDesc = steps.get(i).getStepShortDescription ();

        viewHolder.stepShortDescTV.setText (shortDesc);
        /*viewHolder.itemView.setOnClickListener ((View v) -> {
            if (null != listener) {
                System.out.println("1111");
                listener.onStepSelected(steps, i);
            }else {
                System.out.println("2222");
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shortDescTextView)
        TextView stepShortDescTV;

        public ViewHolder(View itemView) {
            super (itemView);
            itemView.setClickable(true);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener ((View v) -> {
                Bundle bundle = new Bundle();
                bundle.putParcelable ("step",steps.get(getAdapterPosition()));
                bundle.putParcelableArrayList("steps",(ArrayList<Step>) steps);
                StepVideoFragment fragment = new StepVideoFragment ();
                fragment.setArguments (bundle);

                //((FragmentActivity)context).getSupportFragmentManager().addOnBackStackChangedListener (null);
                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction ()
                        .replace (R.id.frame_container, fragment)
                        .addToBackStack (null)
                        .commit ();
            });

        }
    }
}
