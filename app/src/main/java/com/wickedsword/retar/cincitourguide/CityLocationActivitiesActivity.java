package com.wickedsword.retar.cincitourguide;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


public class CityLocationActivitiesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // set up some variables
    private static final String CATEGORY = "category";
    private static final String THINGS_TO_DO_CATEGORY = "things_to_do";
    private static final String LANDMARKS = "landmarks_to_see";
    private static final String FOOD_CATEGORY = "eat_and_drink";
    private static final String SUBCATEGORY_ID = "category_id";
    private static final String PARENT_CATEGORY_NAME = "parent_category";
    private int subcategoryId;
    private String parentCategoryName;

    private ActionBarDrawerToggle mToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.city_activities_fragment_container);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mNavView = (NavigationView) findViewById(R.id.nav_view);
        mNavView.setNavigationItemSelectedListener(CityLocationActivitiesActivity.this);

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

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.things_to_do_drawer) {
            thingsToDoIntent();
        } else if(id == R.id.landmarks_drawer) {
            landmarksIntent();
        } else if(id == R.id.food_drawer) {
            foodIntent();
        } else if(id == R.id.city_history_drawer) {
            cityHistoryIntent();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void thingsToDoIntent() {
        // set up the intent
        Intent thingsToDoIntent = new Intent(CityLocationActivitiesActivity.this, SubcategoryActivity.class);
        thingsToDoIntent.putExtra(CATEGORY, THINGS_TO_DO_CATEGORY);

        // Start the new activity
        startActivity(thingsToDoIntent);
    }

    public void landmarksIntent() {
        // set up the intent
        Intent activitiesIntent = new Intent(CityLocationActivitiesActivity.this, CityLocationActivitiesActivity.class);

        // pass the subcategory ID to the next Activity so we can get its activities from the json
        activitiesIntent.putExtra(SUBCATEGORY_ID, 1);
        activitiesIntent.putExtra(PARENT_CATEGORY_NAME, LANDMARKS);

        // start the activity
        startActivity(activitiesIntent);
    }

    public void foodIntent() {
        // set up the intent
        Intent foodIntent = new Intent(CityLocationActivitiesActivity.this, SubcategoryActivity.class);
        foodIntent.putExtra(CATEGORY, FOOD_CATEGORY);

        // Start the new activity
        startActivity(foodIntent);
    }

    public void cityHistoryIntent() {
        // set up the intent
        Intent cityHistoryIntent = new Intent(CityLocationActivitiesActivity.this, CityHistoryActivity.class);

        // Start the new activity
        startActivity(cityHistoryIntent);
    }
}
