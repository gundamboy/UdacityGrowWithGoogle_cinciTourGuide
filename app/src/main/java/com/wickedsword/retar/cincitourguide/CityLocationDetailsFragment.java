package com.wickedsword.retar.cincitourguide;


import android.os.Bundle;
import android.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import junit.framework.Assert;


/**
 * A simple {@link Fragment} subclass.
 */
public class CityLocationDetailsFragment extends Fragment {
    private static final String INDEX = "index";
    private static final String SUBCATEGORY_ID = "category_id";
    private static final String PARENT_CATEGORY_NAME = "parent_category";
    private static final String CURRENT_CHOICE = "choice";
    private static final String ACTIVITY_ID = "activity_id";
    private static final String IMAGE_RESOURCE_ID = "image_resource_id";
    private static final String ACTIVITY_NAME = "activity_name";
    private static final String ACTIVITY_IMAGE = "activity_image";
    private static final String DESCRIPTION = "description";
    private static final String WEBSITE = "website";
    private static final String PHONE = "phone";
    private static final String ADDRESS = "address";
    private static final String RATES = "rates";
    private static final String HOURS = "house";


    // we need some variables and setters to display info from the clicked list items
    private String parent_category;
    private int subcategory_id;
    private int activity_id;
    private int imageResourceId;
    private String parentCategoryName;
    private String imageName;
    private String city_activity_image_name;
    private String activityName;
    private String address;
    private String phone;
    private String website;
    private String description;
    private String rates;
    private String hours;

    public CityLocationDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
     */
    public static CityLocationDetailsFragment newInstance(int index) {
        CityLocationDetailsFragment f = new CityLocationDetailsFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt(INDEX, index);
        f.setArguments(args);

        return f;
    }

    public int getShownIndex() {
        return getArguments().getInt(INDEX, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist. The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // isn't displayed. Note this isn't needed -- we could just
            // run the code below, where we would create and return the
            // view hierarchy; it would just never be used.
            return null;
        }

        View view = inflater.inflate(R.layout.fragment_city_location_details, container, false);

        if(getArguments() != null) {

            subcategory_id = getArguments().getInt(SUBCATEGORY_ID);
            parentCategoryName = getArguments().getString(PARENT_CATEGORY_NAME);
            imageName = getArguments().getString(ACTIVITY_IMAGE);
            activityName = getArguments().getString(ACTIVITY_NAME);
            imageResourceId = getArguments().getInt(IMAGE_RESOURCE_ID);
            address = getArguments().getString(ADDRESS);
            phone = getArguments().getString(PHONE);
            website = getArguments().getString(WEBSITE);
            description = getArguments().getString(DESCRIPTION);
            rates = getArguments().getString(RATES);
            hours = getArguments().getString(HOURS);

            ImageView activityImage = view.findViewById(R.id.city_activity_image);
            //int imageResourceId = getActivity().getResources().getIdentifier(imageName, "drawable", getActivity().getPackageName());
            activityImage.setImageResource(imageResourceId);

            TextView activity_name = view.findViewById(R.id.activity_name);
            activity_name.setText(activityName);

            TextView activity_address = view.findViewById(R.id.address);
            activity_address.setText(address);

            TextView activity_phone = view.findViewById(R.id.phone);
            activity_phone.setText(phone);

            TextView activity_website = view.findViewById(R.id.website);
            activity_website.setText(website);

            TextView activity_description = view.findViewById(R.id.description);
            activity_description.setText(description);

            TextView activity_rates = view.findViewById(R.id.rates);
            activity_rates.setText(rates);

            TextView activity_hours = view.findViewById(R.id.hours);
            activity_hours.setText(hours);

        }


        return view;
    }

}
