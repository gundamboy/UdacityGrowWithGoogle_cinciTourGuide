package com.wickedsword.retar.cincitourguide;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CityLocationActivitiesFragment extends ListFragment {
    private static final String SUBCATEGORY_ID = "category_id";
    private static final String PARENT_CATEGORY_NAME = "parent_category";
    private static final String CURRENT_CHOICE = "curChoice";
    private static final String INDEX = "index";
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

    boolean mDualPane;
    int mCurCheckPosition = 0;

    // we need to pass some stuff to this details fragment to get the right info
    private int subcategoryId;
    private String parentCategoryName;
    private ArrayList<CityLocation> locations= new ArrayList<CityLocation>();

    private int activityId;
    private int imageResourceId;
    private String activityName;
    private String activityImage;
    private String activityDescription;
    private String activityWebsite;
    private String activityPhoneNumber;
    private String activityAddressMapsQuery;
    private String activityRates;
    private String activityHours;

    // this method listens for the onBackPressed() method.
    // if that was used, and the results came back ok,
    // set the variables to use the data from that intent
    // otherwise, the extras bundle will be null and the app will crash.

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v("CityLocActivityActFrag", "onActivityCreated");

        Bundle extras = getActivity().getIntent().getExtras();

        if(extras != null) {
            // get the extras
            subcategoryId = extras.getInt(SUBCATEGORY_ID);
            parentCategoryName = extras.getString(PARENT_CATEGORY_NAME);
        }

        // generate the locations ArrayList
        locations = buildLocationList(subcategoryId);

        // setup the adapter with the list
        adapterSetup(locations);

        // Check to see if we have a frame in which to embed the details
        // fragment directly in the containing UI
        View detailsFrame = getActivity().findViewById(R.id.details);
        mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt(CURRENT_CHOICE, 0);
        }

        if (mDualPane) {
            // In dual-pane mode, the list view highlights the selected item.
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

            // Make sure our UI is in the correct state.
            showDetails(mCurCheckPosition);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position);
    }

    /**
     * Helper function to show the details of a selected item, either by
     * displaying a fragment in-place in the current UI, or starting a
     * whole new activity in which it is displayed.
     */
    void showDetails(int index) {
        mCurCheckPosition = index;

        activityId = locations.get(mCurCheckPosition).getmActivityId();
        imageResourceId = locations.get(mCurCheckPosition).getmActivityImageId();
        activityName = locations.get(mCurCheckPosition).getmActivityName();
        activityDescription = locations.get(mCurCheckPosition).getmActivityDescription();
        activityWebsite = locations.get(mCurCheckPosition).getmActivityWebsite();
        activityPhoneNumber = locations.get(mCurCheckPosition).getmActivityPhoneNumber();
        activityAddressMapsQuery = locations.get(mCurCheckPosition).getmActivityDirectionsString();
        activityRates = locations.get(mCurCheckPosition).getmActivityRates();
        activityHours = locations.get(mCurCheckPosition).getmActivityHours();

        if (mDualPane) {
            // We can display everything in-place with fragments, so update
            // the list to highlight the selected item and show the data.
            getListView().setItemChecked(mCurCheckPosition, true);

            // Check what fragment is currently shown, replace if needed.
            CityLocationDetailsFragment details = (CityLocationDetailsFragment) getFragmentManager().findFragmentById(R.id.details);

            if (details == null || details.getShownIndex() != mCurCheckPosition) {
                // Make new fragment to show this selection.
                details = CityLocationDetailsFragment.newInstance(mCurCheckPosition);

                FragmentManager fm = getFragmentManager();

                Bundle fragBundle = new Bundle();
                fragBundle.putInt(SUBCATEGORY_ID, subcategoryId);
                fragBundle.putString(PARENT_CATEGORY_NAME, parentCategoryName);
                fragBundle.putInt(CURRENT_CHOICE, mCurCheckPosition);
                fragBundle.putInt(INDEX, mCurCheckPosition);
                fragBundle.putInt(ACTIVITY_ID, activityId);
                fragBundle.putInt(IMAGE_RESOURCE_ID, imageResourceId);
                fragBundle.putString(ACTIVITY_NAME, activityName);
                fragBundle.putString(ACTIVITY_IMAGE, activityImage);
                fragBundle.putString(DESCRIPTION, activityDescription);
                fragBundle.putString(WEBSITE, activityWebsite);
                fragBundle.putString(PHONE, activityPhoneNumber);
                fragBundle.putString(ADDRESS, activityAddressMapsQuery);
                fragBundle.putString(RATES, activityRates);
                fragBundle.putString(HOURS, activityHours);
                details.setArguments(fragBundle);

                // Execute a transaction, replacing any existing fragment
                // with this one inside the frame.
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.details, details);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        } else {
            // Otherwise we need to launch a new activity to display
            // the dialog fragment with selected text.
            Intent intent = new Intent();
            intent.setClass(getActivity(), CityDetailsActivity.class);
            intent.putExtra(INDEX, index);
            intent.putExtra(PARENT_CATEGORY_NAME, parentCategoryName);
            intent.putExtra(SUBCATEGORY_ID, subcategoryId);
            intent.putExtra(CURRENT_CHOICE, mCurCheckPosition);
            intent.putExtra(ACTIVITY_ID, activityId);
            intent.putExtra(IMAGE_RESOURCE_ID, imageResourceId);
            intent.putExtra(ACTIVITY_NAME, activityName);
            intent.putExtra(ACTIVITY_IMAGE, activityImage);
            intent.putExtra(DESCRIPTION, activityDescription);
            intent.putExtra(WEBSITE, activityWebsite);
            intent.putExtra(PHONE, activityPhoneNumber);
            intent.putExtra(ADDRESS, activityAddressMapsQuery);
            intent.putExtra(RATES, activityRates);
            intent.putExtra(HOURS, activityHours);
            startActivity(intent);
        }
    }

    public CityLocationActivitiesFragment() {
        // Required empty public constructor
    }

    private ArrayList<CityLocation> buildLocationList(int subcategoryId) {

        // holds the json string
        String json;

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
            JSONArray category_jArray = obj.getJSONArray(parentCategoryName);

            // build the array of categories by looping through the json array
            for(int i = 0; i< category_jArray.length(); i++) {
                JSONObject inner_array = category_jArray.getJSONObject(i);
                int current_category_id = inner_array.getInt("category_id");

                if(current_category_id == subcategoryId) {
                    String activitiesString = inner_array.getString("category_activities");
                    JSONArray locationActivities = new JSONArray(activitiesString);

                    for (int j = 0; j < locationActivities.length(); j++) {
                        JSONObject inner_activity = locationActivities.getJSONObject(j);

                        // get all the values from the json
                        activityId = inner_activity.getInt("activity_id");
                        activityName = inner_activity.getString("activity_name");
                        activityImage = inner_activity.getString("activity_thumbnail");
                        String activityTeaserText = inner_activity.getString("activity_teaser_text");
                        activityDescription = inner_activity.getString("activity_description");
                        activityWebsite = inner_activity.getString("activity_website");
                        activityPhoneNumber = inner_activity.getString("activity_phone_number");
                        String activityAddressStreet = inner_activity.getString("address_street");
                        String activityAddressCityStateZip = inner_activity.getString("city_state_zip");
                        activityAddressMapsQuery = inner_activity.getString("address_directions_link");
                        activityRates = inner_activity.getString("activity_rates");
                        activityHours = inner_activity.getString("activity_hours");

                        // get resourceID from the image name
                        imageResourceId = getActivity().getResources().getIdentifier(activityImage, "drawable", getActivity().getPackageName());

                        // add the information into a new CityLocation object
                        locations.add(new CityLocation(activityId, activityName, imageResourceId, activityTeaserText, activityDescription, activityWebsite, activityPhoneNumber, activityAddressStreet, activityAddressCityStateZip, activityAddressMapsQuery, activityRates, activityHours));
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return locations;
    }

    private void adapterSetup(ArrayList<CityLocation> locations) {
        // set up the adapter
        CityActivityListAdapter adapter = new CityActivityListAdapter(getActivity(), locations);
        setListAdapter(adapter);
    }

}
