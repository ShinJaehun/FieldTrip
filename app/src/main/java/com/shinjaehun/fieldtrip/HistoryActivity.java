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
//        initTempData();

        listPlaces = placeDAO.getAllPlaces();
//        listPlaces = new ArrayList<Place>();
//        Place p1 = placeDAO.createPlace("history", "국립제주박물관", "p1pic.jpg", "p1map.jpg", "lat1, long1", "국립제주박물관입니다.");
//        listPlaces.add(p1);
//        Place p2 = placeDAO.createPlace("history", "신재훈네집", "p2pic.jpg", "p2map.jpg", "lat2, long2", "신재훈님네 집입니다.");
//        listPlaces.add(p2);

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
