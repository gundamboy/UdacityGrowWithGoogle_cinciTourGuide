package com.wickedsword.retar.cincitourguide;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment {
    private static final String THINGS_TO_DO_CATEGORY = "things_to_do";
    private static final String LANDMARKS = "landmarks_to_see";
    private static final String FOOD_CATEGORY = "eat_and_drink";
    private static final String SUBCATEGORY_ID = "category_id";
    private static final String PARENT_CATEGORY_NAME = "parent_category";

    public MainActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate the fragments layout
        View view = inflater.inflate(R.layout.fragment_main_activity, container, false);

        // grab the four main category views so we can interact with them
        View things_to_do = view.findViewById(R.id.things_to_do);
        View landmarks = view.findViewById(R.id.landmarks);
        View food = view.findViewById(R.id.eat_drink);
        View city_history = view.findViewById(R.id.history);

        // click listener for things to do
        things_to_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start the things to do fragment call
                SubcategoryFragment frag = new SubcategoryFragment();

                // create a bundle to pass some needed info the next fragment
                Bundle fragBundle = new Bundle();
                fragBundle.putString(PARENT_CATEGORY_NAME, THINGS_TO_DO_CATEGORY);
                frag.setArguments(fragBundle);

                replaceFragment(frag, R.id.main_fragment);
            }
        });

        // click listener for landmarks
        landmarks.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 CityLocationActivitiesFragment frag = new CityLocationActivitiesFragment();

                 Bundle fragBundle = new Bundle();
                 fragBundle.putString(PARENT_CATEGORY_NAME, LANDMARKS);
                 fragBundle.putInt(SUBCATEGORY_ID, 1);
                 frag.setArguments(fragBundle);
                 replaceFragment(frag, R.id.main_fragment);
             }
         });

        // click listener for eat & drink (food)
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubcategoryFragment frag = new SubcategoryFragment();

                // create a bundle to pass some needed info the next fragment
                Bundle fragBundle = new Bundle();
                fragBundle.putString(PARENT_CATEGORY_NAME, FOOD_CATEGORY);
                frag.setArguments(fragBundle);

                replaceFragment(frag, R.id.main_fragment);
            }
        });

        // click listener for city history
        city_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityHistoryFragment frag = new CityHistoryFragment();
                replaceFragment(frag, R.id.main_fragment);
            }
        });

        return view;
    }

    private void replaceFragment(Fragment frag, int viewId) {

        // get the fragment manager
        FragmentManager fm = getFragmentManager();

        // Execute a transaction, replacing any existing fragment
        // with this one inside the frame.
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(viewId, frag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
    }

}
