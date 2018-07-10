package com.wickedsword.retar.cincitourguide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SubcategoryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String CATEGORY = "category";
    private static final String THINGS_TO_DO_CATEGORY = "things_to_do";
    private static final String LANDMARKS = "landmarks_to_see";
    private static final String FOOD_CATEGORY = "eat_and_drink";
    private static final String SUBCATEGORY_ID = "category_id";
    private static final String PARENT_CATEGORY_NAME = "parent_category";
    private JSONArray category_jArray;
    private String parentCategory;

    private ActionBarDrawerToggle mToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subcategory_grid);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mNavView = (NavigationView) findViewById(R.id.nav_view);
        mNavView.setNavigationItemSelectedListener(SubcategoryActivity.this);

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            Log.v("SubcategoryActivity", "savedInstanceState != null");
            parentCategory = savedInstanceState.getString(CATEGORY);
        } else {
            // get the extra info that came with the intent
            Bundle extras = getIntent().getExtras();

            // it is possible extras could be null if the user goes to the next activity
            // and pressed the devices back button
            if(extras != null) {
                Log.v("SubcategoryActivity", "savedInstanceState was null, extras was not null");
                // get the extras
                parentCategory = extras.getString(CATEGORY);
            }
        }

        Log.v("SubcategoryActivity", "parentCategory: " + parentCategory);

        // holds the json string
        String json;

        // create the gridview container
        GridView things_to_do_grid = findViewById(R.id.subcategory_gridview);

        // create the activities context
        Context context = things_to_do_grid.getContext();

        // make an array to hold the categories in
        ArrayList<Subcategory> categories = new ArrayList<Subcategory>();

        // try catch block to grab the json file. json requires a try catch block
        try {
            // get the json file from the assets folder
            InputStream is = getAssets().open("cincitour.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // create a string of the json file
            json = new String(buffer, "UTF-8");

            // turns the json string into an object
            JSONObject obj = new JSONObject(json);

            // sets the category_jArray to be an array of all the objects under the subcategory_grid key in the json
            category_jArray = obj.getJSONArray(parentCategory);

            // build the array of categories by looping through the json array
            for(int i = 0; i<category_jArray.length(); i++) {
                JSONObject inner_array = category_jArray.getJSONObject(i);
                String category_name = inner_array.getString("category_name");
                int category_id = inner_array.getInt("category_id");
                String category_image_thumb = inner_array.getString("category_thumbnail");
                int category_image_id = context.getResources().getIdentifier(category_image_thumb, "drawable", context.getPackageName());
                categories.add(new Subcategory(parentCategory, category_name, category_id, category_image_id));
            }

            SubcategoryAdapter adapter = new SubcategoryAdapter(this, categories);
            things_to_do_grid.setAdapter(adapter);

        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v("SubcategoryActivity", "onSavedInstanceState should be storing parentCategory: " + parentCategory);
        outState.putString(CATEGORY, parentCategory);
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
        Intent thingsToDoIntent = new Intent(SubcategoryActivity.this, SubcategoryActivity.class);
        thingsToDoIntent.putExtra(CATEGORY, THINGS_TO_DO_CATEGORY);

        // Start the new activity
        startActivity(thingsToDoIntent);
    }

    public void landmarksIntent() {
        // set up the intent
        Intent activitiesIntent = new Intent(SubcategoryActivity.this, CityLocationActivitiesActivity.class);

        // pass the subcategory ID to the next Activity so we can get its activities from the json
        activitiesIntent.putExtra(SUBCATEGORY_ID, 1);
        activitiesIntent.putExtra(PARENT_CATEGORY_NAME, LANDMARKS);

        // start the activity
        startActivity(activitiesIntent);
    }

    public void foodIntent() {
        // set up the intent
        Intent foodIntent = new Intent(SubcategoryActivity.this, SubcategoryActivity.class);
        foodIntent.putExtra(CATEGORY, FOOD_CATEGORY);

        // Start the new activity
        startActivity(foodIntent);
    }

    public void cityHistoryIntent() {
        // set up the intent
        Intent cityHistoryIntent = new Intent(SubcategoryActivity.this, CityHistoryActivity.class);

        // Start the new activity
        startActivity(cityHistoryIntent);
    }
}
