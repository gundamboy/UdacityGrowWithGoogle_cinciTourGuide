package com.wickedsword.retar.cincitourguide;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubcategoryFragment extends Fragment {
    // some constants and variables
    private static final String PARENT_CATEGORY_NAME = "parent_category";
    private static final String SUBCATEGORY_ID = "category_id";
    private String parentCategory;

    public SubcategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subcategory, container, false);

        // get the gridView
        GridView subcategoryGrid = view.findViewById(R.id.subcategory_gridview);

        // make an array to hold the categories in
        final ArrayList<Subcategory> categories = new ArrayList<Subcategory>();

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            parentCategory = savedInstanceState.getString(PARENT_CATEGORY_NAME);
        } else {
            Bundle args = getArguments();

            if (args != null) {
                parentCategory = args.getString(PARENT_CATEGORY_NAME);
            }
        }

        // holds the json string
        String json;

        // create the activities context
        Context context = subcategoryGrid.getContext();

        // try catch block to grab the json file. json requires a try catch block
        try {
            // get the json file from the assets folder
            InputStream is = getActivity().getAssets().open("cincitour.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // create a string of the json file
            json = new String(buffer, "UTF-8");

            // turns the json string into an object
            JSONObject obj = new JSONObject(json);

            // sets the category_jArray to be an array of all the objects under the subcategory_grid key in the json
            JSONArray category_jArray = obj.getJSONArray(parentCategory);

            // build the array of categories by looping through the json array
            for(int i = 0; i< category_jArray.length(); i++) {
                JSONObject inner_array = category_jArray.getJSONObject(i);
                String category_name = inner_array.getString("category_name");
                int category_id = inner_array.getInt("category_id");
                String category_image_thumb = inner_array.getString("category_thumbnail");
                int category_image_id = context.getResources().getIdentifier(category_image_thumb, "drawable", context.getPackageName());
                categories.add(new Subcategory(parentCategory, category_name, category_id, category_image_id));
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SubcategoryAdapter adapter = new SubcategoryAdapter(getActivity(), categories);
        subcategoryGrid.setAdapter(adapter);

        subcategoryGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Subcategory currentCategory = categories.get(i);

                Intent activitiesIntent = new Intent(getActivity(), CityLocationActivitiesActivity.class);

                // pass the subcategory ID to the next Activity so we can get its activities from the json
                activitiesIntent.putExtra(SUBCATEGORY_ID, currentCategory.getCategoryId());
                activitiesIntent.putExtra(PARENT_CATEGORY_NAME, currentCategory.getParentCategoryName());

                // start the activity
                getActivity().startActivity(activitiesIntent);

            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PARENT_CATEGORY_NAME, parentCategory);
    }
}
