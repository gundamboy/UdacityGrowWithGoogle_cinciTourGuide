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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // get the bundle that was sent from the previous fragment
        Bundle extras = getArguments();

        // check to see if the bundle came back empty. if it did there will be some problems.
        if (extras != null) {
            parentCategoryName = extras.getString(PARENT_CATEGORY_NAME);
            subcategoryId = extras.getInt(SUBCATEGORY_ID);
        }

        // generate the locations ArrayList
        locations = buildLocationList(subcategoryId);

        // setup the adapter with the list
        adapterSetup(locations);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position);
    }

    /**
     * this method is called when an activity card is pressed. it pulls in the next fragment
     * with the proper information
     * @param index this is the ID of the clicked item
     */
    void showDetails(int index) {
        mCurCheckPosition = index;

        // grab the data we need
        activityId = locations.get(mCurCheckPosition).getmActivityId();
        imageResourceId = locations.get(mCurCheckPosition).getmActivityImageId();
        activityName = locations.get(mCurCheckPosition).getmActivityName();
        activityDescription = locations.get(mCurCheckPosition).getmActivityDescription();
        activityWebsite = locations.get(mCurCheckPosition).getmActivityWebsite();
        activityPhoneNumber = locations.get(mCurCheckPosition).getmActivityPhoneNumber();
        activityAddressMapsQuery = locations.get(mCurCheckPosition).getmActivityDirectionsString();
        activityRates = locations.get(mCurCheckPosition).getmActivityRates();
        activityHours = locations.get(mCurCheckPosition).getmActivityHours();

        // initialize a new fragment
        CityLocationDetailsFragment details = CityLocationDetailsFragment.newInstance(mCurCheckPosition);

        // get the fragment manager
        FragmentManager fm = getFragmentManager();

        // make a new bundle of data to pass to the next fragment
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
        ft.replace(R.id.main_fragment, details);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    public CityLocationActivitiesFragment() {
        // Required empty public constructor
    }

    /**
     * this method builds the custom array adapter
     * @param subcategoryId the subcategory id that we need to find in the json file
     * @return
     */
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

                // looks to see if the current id and the subcategory id match
                if(current_category_id == subcategoryId) {
                    // get the category_activities string
                    String activitiesString = inner_array.getString("category_activities");

                    // turn the string info a jsonarray
                    JSONArray locationActivities = new JSONArray(activitiesString);

                    // we have to do another loop on the new array so we can get the proper
                    // data back to be used in a bundle for the next activity
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

    /**
     * helper method to set up the arraylist adapter
     * @param locations the arrayList of location data
     */
    private void adapterSetup(ArrayList<CityLocation> locations) {
        // set up the adapter
        CityActivityListAdapter adapter = new CityActivityListAdapter(getActivity(), locations);
        setListAdapter(adapter);
    }

}
