package com.wickedsword.retar.cincitourguide;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String THINGS_TO_DO_CATEGORY = "things_to_do";
    private static final String LANDMARKS = "landmarks_to_see";
    private static final String FOOD_CATEGORY = "eat_and_drink";
    private static final String SUBCATEGORY_ID = "category_id";
    private static final String PARENT_CATEGORY_NAME = "parent_category";

    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check that the activity is using the layout version
        // with the main_fragment FrameLayout
        if (findViewById(R.id.main_fragment) != null) {

            // If we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                //Do nothing, Android has got my back.
            } else {
                // add the main activity fragment to the layout
                MainActivityFragment mainFrag = new MainActivityFragment();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().add(R.id.main_fragment, mainFrag).commit();
            }
        }

        // set up the navigation drawer and toggle button
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationView mNavView = (NavigationView) findViewById(R.id.nav_view);
        mNavView.setNavigationItemSelectedListener(MainActivity.this);
    }

    // this method is called when the user clicks on an item in the nav drawer
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        displaySelectedScreen(id);

        return true;
    }

    // this method toggles the nav drawer when the hamburger icon is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // this method closes the nav drawer if its open when the user pushes that back button
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // this method is called from the onOptionsItemSelected. it does the heavy lifting
    // of pulling in the right fragment
    private void displaySelectedScreen(int itemId) {
        // set a default fragment variable
        Fragment fragment = null;
        Bundle fragBundle = new Bundle();

        // set up the proper fragment type and arguments
        switch(itemId) {
            case R.id.things_to_do_drawer:
                fragment = new SubcategoryFragment();
                fragBundle.putString(PARENT_CATEGORY_NAME, THINGS_TO_DO_CATEGORY);
                fragment.setArguments(fragBundle);
                break;
            case R.id.landmarks_drawer:
                fragment = new CityLocationActivitiesFragment();
                fragBundle.putString(PARENT_CATEGORY_NAME, LANDMARKS);
                fragBundle.putInt(SUBCATEGORY_ID, 1);
                fragment.setArguments(fragBundle);
                break;
            case R.id.food_drawer:
                fragment = new SubcategoryFragment();
                fragBundle.putString(PARENT_CATEGORY_NAME, FOOD_CATEGORY);
                fragment.setArguments(fragBundle);
                break;
            case R.id.city_history_drawer:
                fragment = new CityHistoryFragment();
                break;
        }

        if (fragment != null) {
            // get the fragment manager
            FragmentManager fm = getFragmentManager();

            // Execute a transaction, replacing any existing fragment
            // with this one inside the frame.
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace( R.id.main_fragment, fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
