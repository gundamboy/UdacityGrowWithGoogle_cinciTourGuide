package com.wickedsword.retar.cincitourguide;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class CityActivityListAdapter extends ArrayAdapter<CityLocation> {

    // constructor
    public CityActivityListAdapter(Activity context, ArrayList<CityLocation> locations) {
        super(context, 0, locations);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // check if the existing view is being reused, otherwise inflate the view
        View listView = convertView;
        if(listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.city_activity_list_item, parent, false);
        }

        // get the current location
        final CityLocation currentLocation = getItem(position);

        // set the image
        ImageView locationImage = listView.findViewById(R.id.activity_card_image);
        locationImage.setImageResource(currentLocation.getmActivityImageId());

        // set the location name
        TextView locationName = listView.findViewById(R.id.activity_card_title);
        locationName.setText(currentLocation.getmActivityName());

        // teaser text
        TextView teaserText = listView.findViewById(R.id.activity_card_teaser_text);
        teaserText.setText(currentLocation.getmActivityTeaserText());

        return listView;
    }
}
