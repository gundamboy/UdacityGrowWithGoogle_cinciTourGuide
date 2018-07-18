package com.wickedsword.retar.cincitourguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    private ExpandableListView listView;
    private ExpandableListAdapter adapter;
    private List<String> listHistoryHeader;
    private HashMap<String, List<String>> listHash;

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

        listView = (ExpandableListView) findViewById(R.id.historyList);
        initData();
        adapter = new HistoryExpandableListAdapter(this, listHistoryHeader, listHash);
        listView.setAdapter(adapter);
    }

    private void initData() {
        listHistoryHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listHistoryHeader.add(getResources().getString(R.string.history_header_early_history));
        listHistoryHeader.add(getResources().getString(R.string.history_header_civil_war));
        listHistoryHeader.add(getResources().getString(R.string.history_header_underground_railroad));
        listHistoryHeader.add(getResources().getString(R.string.history_header_sports));
        listHistoryHeader.add(getResources().getString(R.string.history_header_fountain));
        listHistoryHeader.add(getResources().getString(R.string.history_header_disasters));

        List<String> early_history = new ArrayList<>();
        early_history.add(getResources().getString(R.string.history_early_history));

        List<String> civil_war = new ArrayList<>();
        civil_war.add(getResources().getString(R.string.history_civil_war));

        List<String> railroad = new ArrayList<>();
        railroad.add(getResources().getString(R.string.history_underground_railroad));

        List<String> sports = new ArrayList<>();
        sports.add(getResources().getString(R.string.history_sports));

        List<String> fountain = new ArrayList<>();
        fountain.add(getResources().getString(R.string.history_fountain));

        List<String> disasters = new ArrayList<>();
        disasters.add(getResources().getString(R.string.history_disasters));

        listHash.put(listHistoryHeader.get(0), early_history);
        listHash.put(listHistoryHeader.get(1), civil_war);
        listHash.put(listHistoryHeader.get(2), railroad);
        listHash.put(listHistoryHeader.get(3), sports);
        listHash.put(listHistoryHeader.get(4), fountain);
        listHash.put(listHistoryHeader.get(5), disasters);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.things_to_do_drawer) {

        } else if(id == R.id.landmarks_drawer) {

        } else if(id == R.id.food_drawer) {

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
}
