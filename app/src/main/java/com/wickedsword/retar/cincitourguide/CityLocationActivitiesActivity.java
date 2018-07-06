package com.wickedsword.retar.cincitourguide;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class CityLocationActivitiesActivity extends AppCompatActivity {
    // set up some variables
    private static final String SUBCATEGORY_ID = "category_id";
    private static final String PARENT_CATEGORY_NAME = "parent_category";
    private int subcategoryId;
    private String parentCategoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_activities_fragment_container);

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            Log.v("locationsActivity", "savedInstance was not null");
            subcategoryId = savedInstanceState.getInt(SUBCATEGORY_ID);
            parentCategoryName = savedInstanceState.getString(PARENT_CATEGORY_NAME);
        } else {
            Log.v("locationsActivity", "trying to get extras, savedInstanceState was null");
            // get the extra info that came with the intent
            Bundle extras = getIntent().getExtras();

            // it is possible extras could be null depending on how the user navigated the app
            if(extras != null) {
                // get the extras
                Log.v("locationsActivity", "extra s was not null");
                subcategoryId = extras.getInt(SUBCATEGORY_ID);
                parentCategoryName = extras.getString(PARENT_CATEGORY_NAME);
            }
        }
        buildFragment();

    }

    public void buildFragment() {
        Log.v("locationsActivity", "in locationsActivity buildFragment");
        Log.v("locationsActivity", "subcategoryID: " + subcategoryId);
        Log.v("locationsActivity", "parent name: " + parentCategoryName);

        FragmentManager fm = getFragmentManager();

        // send some info to the list fragment
        Bundle fragBundle = new Bundle();
        fragBundle.putInt(SUBCATEGORY_ID, subcategoryId);
        fragBundle.putString(PARENT_CATEGORY_NAME, parentCategoryName);

        // get a reference to the fragment that will hold the list of the activities at the location the user chose.
        CityLocationActivitiesFragment listFrag = new CityLocationActivitiesFragment();
        listFrag.setArguments(fragBundle);
        fm.beginTransaction().replace(R.id.CityLocationActivitiesFragment, listFrag).commit();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        subcategoryId = savedInstanceState.getInt(SUBCATEGORY_ID);
        parentCategoryName = savedInstanceState.getString(PARENT_CATEGORY_NAME);

        if (savedInstanceState != null) {
            buildFragment();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SUBCATEGORY_ID, subcategoryId);
        outState.putString(PARENT_CATEGORY_NAME, parentCategoryName);
    }
}
