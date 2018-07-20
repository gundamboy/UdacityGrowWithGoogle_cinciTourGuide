package com.wickedsword.retar.cincitourguide;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CityHistoryFragment extends Fragment {
    private ExpandableListView listView;
    private ExpandableListAdapter adapter;
    private List<String> listHistoryHeader;
    private HashMap<String, List<String>> listHash;

    public CityHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.city_history, container, false);

        // get the listview
        listView = (ExpandableListView) view.findViewById(R.id.historyList);

        // call the initData() method
        initData();

        // set up the adapter
        adapter = new HistoryExpandableListAdapter(getActivity(), listHistoryHeader, listHash);

        listView.setAdapter(adapter);

        return view;
    }

    /**
     * this method builds the string array and the hashmap needed for creating
     * needed for getting up the adapter
     */
    private void initData() {
        listHistoryHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listHistoryHeader.add(getResources().getString(R.string.history_header_early_history));
        listHistoryHeader.add(getResources().getString(R.string.history_header_civil_war));
        listHistoryHeader.add(getResources().getString(R.string.history_header_underground_railroad));
        listHistoryHeader.add(getResources().getString(R.string.history_header_sports));
        listHistoryHeader.add(getResources().getString(R.string.history_header_fountain));
        listHistoryHeader.add(getResources().getString(R.string.history_header_disasters));

        List<String> early_history = new ArrayList<>();
        early_history.add(getResources().getString(R.string.history_early_history));

        List<String> civil_war = new ArrayList<>();
        civil_war.add(getResources().getString(R.string.history_civil_war));

        List<String> railroad = new ArrayList<>();
        railroad.add(getResources().getString(R.string.history_underground_railroad));

        List<String> sports = new ArrayList<>();
        sports.add(getResources().getString(R.string.history_sports));

        List<String> fountain = new ArrayList<>();
        fountain.add(getResources().getString(R.string.history_fountain));

        List<String> disasters = new ArrayList<>();
        disasters.add(getResources().getString(R.string.history_disasters));

        listHash.put(listHistoryHeader.get(0), early_history);
        listHash.put(listHistoryHeader.get(1), civil_war);
        listHash.put(listHistoryHeader.get(2), railroad);
        listHash.put(listHistoryHeader.get(3), sports);
        listHash.put(listHistoryHeader.get(4), fountain);
        listHash.put(listHistoryHeader.get(5), disasters);
    }

}
