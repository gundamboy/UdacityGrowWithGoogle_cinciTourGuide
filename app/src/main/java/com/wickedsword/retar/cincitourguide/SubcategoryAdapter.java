package com.wickedsword.retar.cincitourguide;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SubcategoryAdapter extends ArrayAdapter<Subcategory> {
    private static final String SUBCATEGORY_ID = "category_id";
    private static final String PARENT_CATEGORY_NAME = "parent_category";

    public SubcategoryAdapter(Activity context, ArrayList<Subcategory> categories) {
        super(context, 0, categories);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // check if the existing view is being reused, otherwise inflate the view
        View gridView = convertView;
        if(gridView == null) {
            gridView = LayoutInflater.from(getContext()).inflate(R.layout.subcategory_item, parent, false);
        }

        //get the current category
        final Subcategory currentCategory = getItem(position);

        //set the current category image
        ImageView categoryImage = gridView.findViewById(R.id.category_image);
        categoryImage.setImageResource(currentCategory.getCategoryImage());

        // Set the category name
        TextView categoryName = gridView.findViewById(R.id.category_name);
        categoryName.setText(currentCategory.getCategoryName());

        //set an setOnClickListener for each gird item to go to the CityLocationActivitiesActivity activity. that's a whole of activities!
        /*
        gridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set up the intent
                Intent activitiesIntent = new Intent(getContext(), CityLocationActivitiesActivity.class);

                // pass the subcategory ID to the next Activity so we can get its activities from the json
                activitiesIntent.putExtra(SUBCATEGORY_ID, currentCategory.getCategoryId());
                activitiesIntent.putExtra(PARENT_CATEGORY_NAME, currentCategory.getParentCategoryName());

                // start the activity
                getContext().startActivity(activitiesIntent);
            }
        });
        */

        return gridView;
    }

}
