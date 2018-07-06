package com.wickedsword.retar.cincitourguide;

import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.wickedsword.retar.cincitourguide.CityLocationDetailsFragment;

public class CityDetailsActivity extends FragmentActivity {
    private static final String SUBCATEGORY_ID = "category_id";
    private static final String PARENT_CATEGORY_NAME = "parent_category";
    private int subcategoryId;
    private String parentCategoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // If the screen is now in landscape mode, we can show the
            // dialog in-line with the list so we don't need this activity.
            finish();
            return;
        }

        if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment.
            CityLocationDetailsFragment details = new CityLocationDetailsFragment();
            details.setArguments(getIntent().getExtras());

            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().add(android.R.id.content, details).commit();
        }
    }
}