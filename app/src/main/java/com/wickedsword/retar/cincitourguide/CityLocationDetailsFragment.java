package com.wickedsword.retar.cincitourguide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



/**
 * A simple {@link Fragment} subclass.
 */
public class CityLocationDetailsFragment extends Fragment {
    private static final String INDEX = "index";
    private static final String IMAGE_RESOURCE_ID = "image_resource_id";
    private static final String ACTIVITY_NAME = "activity_name";
    private static final String DESCRIPTION = "description";
    private static final String WEBSITE = "website";
    private static final String PHONE = "phone";
    private static final String ADDRESS = "address";
    private static final String RATES = "rates";
    private static final String HOURS = "house";
    private String address;
    private String phone;

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
        return getArguments().getInt(INDEX);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;

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

        view = inflater.inflate(R.layout.fragment_city_location_details, container, false);

        if (getArguments() != null) {
            String activityName = getArguments().getString(ACTIVITY_NAME);
            int imageResourceId = getArguments().getInt(IMAGE_RESOURCE_ID);
            address = getArguments().getString(ADDRESS);
            phone = getArguments().getString(PHONE);
            String website = getArguments().getString(WEBSITE);
            String description = getArguments().getString(DESCRIPTION);
            String rates = getArguments().getString(RATES);
            String hours = getArguments().getString(HOURS);

            ImageView activityImage = view.findViewById(R.id.city_activity_image);
            activityImage.setImageResource(imageResourceId);

            TextView activity_name = view.findViewById(R.id.activity_name);
            activity_name.setText(activityName);

            TextView activity_address = view.findViewById(R.id.address);
            ImageView locationIcon = view.findViewById(R.id.location_icon);
            if(address == null || address.isEmpty()) {
                activity_address.setVisibility(activity_address.GONE);
                locationIcon.setVisibility(activity_address.GONE);
            } else {
                activity_address.setText(address);
            }

            TextView activity_phone = view.findViewById(R.id.phone);
            ImageView phoneIcon = view.findViewById(R.id.phone_icon);
            if(phone == null || phone.isEmpty()) {
                activity_phone.setVisibility(activity_phone.GONE);
                phoneIcon.setVisibility(activity_phone.GONE);
            } else {
                activity_phone.setText(phone);
            }

            TextView activity_website = view.findViewById(R.id.website);
            ImageView websiteIcon = view.findViewById(R.id.web_icon);
            if(website == null || website.isEmpty()) {
                activity_phone.setVisibility(activity_website.GONE);
                websiteIcon.setVisibility(activity_phone.GONE);
            } else {
                activity_website.setText(website);
            }

            TextView activity_description = view.findViewById(R.id.description);
            activity_description.setText(description);

            TextView activity_rates = view.findViewById(R.id.rates);
            TextView rates_header = view.findViewById(R.id.rates_header);
            if(rates == null || rates.isEmpty()) {
                activity_rates.setVisibility(activity_rates.GONE);
                rates_header.setVisibility(activity_rates.GONE);
            } else {
                activity_rates.setText(rates);
            }

            TextView activity_hours = view.findViewById(R.id.hours);
            TextView hours_header = view.findViewById(R.id.hours_header);
            if(hours == null || hours.isEmpty()) {
                activity_hours.setVisibility(activity_website.GONE);
                hours_header.setVisibility(activity_website.GONE);
            } else {
                activity_hours.setText(hours);
            }

            // set up click listeners for implicit intents for phone and maps
            activity_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String cleanPhone = phone.replace("-", "");
                    Uri phone_number = Uri.parse("tel:" + cleanPhone);
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, phone_number);
                    startActivity(callIntent);
                }
            });

            activity_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri mapSearchString = Uri.parse("geo:0,0?q=" + address);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapSearchString);
                    mapIntent.setPackage("com.google.android.apps.maps");

                    if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(mapIntent);
                    }
                }
            });

        }

        return view;
    }
}