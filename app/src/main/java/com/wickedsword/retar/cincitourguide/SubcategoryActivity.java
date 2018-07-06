package com.wickedsword.retar.cincitourguide;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SubcategoryActivity extends AppCompatActivity {
    private JSONArray category_jArray;
    private static final String CATEGORY = "category";
    private static final String PARENT_CATEGORY = "parentCategory";
    private String parentCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subcategory_grid);

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            parentCategory = savedInstanceState.getString(CATEGORY);
        } else {
            // get the extra info that came with the intent
            Bundle extras = getIntent().getExtras();

            // it is possible extras could be null if the user goes to the next activity
            // and pressed the devices back button
            if(extras != null) {
                // get the extras
                parentCategory = extras.getString(CATEGORY);
            }
        }

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
}
