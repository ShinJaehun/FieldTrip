package com.shinjaehun.fieldtrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shinjaehun on 2016-05-20.
 */
public class HistoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String TAG = "HistoryActivity";

    private ListView listViewPlaces;
    private ListPlacesAdapter adapter;
    private List<Place> listPlaces;
    private PlaceDAO placeDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initLayout();

        placeDAO = new PlaceDAO(this);

        listPlaces = placeDAO.getPlacesByType("history");

        adapter = new ListPlacesAdapter(this, listPlaces);

        for (int i = 0; i < listPlaces.size(); i++) {
            Log.v(TAG, "place " + i + " : " + listPlaces.get(i).getName());
        }
        listViewPlaces.setAdapter(adapter);

    }

    private void initLayout() {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        ctl.setTitle("역사");

        this.listViewPlaces = (ListView)findViewById(R.id.list_places);
        this.listViewPlaces.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Place clickedPlace = adapter.getItem(position);
        Log.d(TAG, "clickedItem : " + clickedPlace.getName());
        Intent intent = new Intent(this, PlaceActivity.class);
        intent.putExtra(PlaceActivity.EXTRA_SELECTED_PLACE, clickedPlace);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        placeDAO.close();
    }
}
