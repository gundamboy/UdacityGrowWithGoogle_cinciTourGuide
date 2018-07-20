package com.wickedsword.retar.cincitourguide;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
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
    private Context context;

    public SubcategoryAdapter(Activity context, ArrayList<Subcategory> categories) {
        super(context, 0, categories);
        this.context = context;
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

        //set an setOnClickListener for each gird item
        gridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityLocationActivitiesFragment frag = new CityLocationActivitiesFragment();
                // create a bundle to pass some needed info the next fragment
                Bundle fragBundle = new Bundle();
                fragBundle.putInt(SUBCATEGORY_ID, currentCategory.getCategoryId());
                fragBundle.putString(PARENT_CATEGORY_NAME, currentCategory.getParentCategoryName());
                frag.setArguments(fragBundle);

                replaceFragment(frag, R.id.main_fragment);
            }
        });

        return gridView;
    }

    private void replaceFragment(Fragment frag, int viewId) {

        // get the fragment manager
        FragmentManager fm = ((Activity) context).getFragmentManager();

        // Execute a transaction, replacing any existing fragment
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(viewId, frag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
    }

}
