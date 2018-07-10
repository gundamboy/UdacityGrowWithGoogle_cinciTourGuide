package com.wickedsword.retar.cincitourguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class CityHistoryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String CATEGORY = "category";
    private static final String THINGS_TO_DO_CATEGORY = "things_to_do";
    private static final String LANDMARKS = "landmarks_to_see";
    private static final String FOOD_CATEGORY = "eat_and_drink";
    private static final String SUBCATEGORY_ID = "category_id";
    private static final String PARENT_CATEGORY_NAME = "parent_category";
    private ActionBarDrawerToggle mToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_history);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mNavView = (NavigationView) findViewById(R.id.nav_view);
        mNavView.setNavigationItemSelectedListener(CityHistoryActivity.this);
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
            return false;
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
        Intent thingsToDoIntent = new Intent(CityHistoryActivity.this, SubcategoryActivity.class);
        thingsToDoIntent.putExtra(CATEGORY, THINGS_TO_DO_CATEGORY);

        // Start the new activity
        startActivity(thingsToDoIntent);
    }

    public void landmarksIntent() {
        // set up the intent
        Intent activitiesIntent = new Intent(CityHistoryActivity.this, CityLocationActivitiesActivity.class);

        // pass the subcategory ID to the next Activity so we can get its activities from the json
        activitiesIntent.putExtra(SUBCATEGORY_ID, 1);
        activitiesIntent.putExtra(PARENT_CATEGORY_NAME, LANDMARKS);

        // start the activity
        startActivity(activitiesIntent);
    }

    public void foodIntent() {
        // set up the intent
        Intent foodIntent = new Intent(CityHistoryActivity.this, SubcategoryActivity.class);
        foodIntent.putExtra(CATEGORY, FOOD_CATEGORY);

        // Start the new activity
        startActivity(foodIntent);
    }
}
