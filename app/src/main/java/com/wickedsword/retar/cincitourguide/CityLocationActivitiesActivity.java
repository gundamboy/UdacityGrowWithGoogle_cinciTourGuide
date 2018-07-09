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
            subcategoryId = savedInstanceState.getInt(SUBCATEGORY_ID);
            parentCategoryName = savedInstanceState.getString(PARENT_CATEGORY_NAME);
        } else {
            // get the extra info that came with the intent
            Bundle extras = getIntent().getExtras();

            // it is possible extras could be null depending on how the user navigated the app
            if(extras != null) {
                // get the extras
                subcategoryId = extras.getInt(SUBCATEGORY_ID);
                parentCategoryName = extras.getString(PARENT_CATEGORY_NAME);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SUBCATEGORY_ID, subcategoryId);
        outState.putString(PARENT_CATEGORY_NAME, parentCategoryName);
    }
}
