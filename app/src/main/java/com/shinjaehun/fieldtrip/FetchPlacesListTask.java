package com.shinjaehun.fieldtrip;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shinjaehun on 2016-07-19.
 */

public class FetchPlacesListTask extends AsyncTask<String, Void, List<Place>> {
    private static final String TAG = "FetchPlacesListTask";

    private final Context mContext;
    ListPlacesAdapter mListPlacesAdapter;
    PlaceDAO placeDAO;

    ProgressDialog asyncDialog;

    public FetchPlacesListTask(Context context, ListPlacesAdapter listPlacesAdapter) {
        mContext = context;
        mListPlacesAdapter = listPlacesAdapter;
        asyncDialog = new ProgressDialog(mContext);
    }

    @Override
    protected void onPreExecute() {
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage("로딩중입니다...");
        asyncDialog.show();
        super.onPreExecute();
        Log.v("AsyncTask", "onPreExecute");
//        mListPlacesAdapter = new ListPlacesAdapter(mContext);

    }

    @Override
    protected List<Place> doInBackground(String... params) {
//        Log.v("AsyncTask", "doInBackground");

        placeDAO = new PlaceDAO(mContext);

//        placeDAO = PlaceDAO.getInstance(this);

//        SukSuk app = (SukSuk)getApplication();
//        app.setDAO(placeDAO);

//        List<Place> listPlaces = null;
        List<Place> listPlaces = placeDAO.getPlacesByType(params[0]);
        //category를 통해 lists of places 불러오기

        for (int i = 0; i < listPlaces.size(); i++) {
            Log.v(TAG, "place in Background : " + i + " : " + listPlaces.get(i).getName());
        }

//        mListPlacesAdapter = new ListPlacesAdapter(mContext, listPlaces);
//        mListView.setAdapter(mListPlacesAdapter);
        return listPlaces;
    }

    @Override
    protected void onPostExecute(List<Place> places) {
        asyncDialog.dismiss();
        Log.v("AsyncTask", "onPostExecute");

        for (int i = 0; i < places.size(); i++) {
            Log.v(TAG, "place in Post : " + i + " : " + places.get(i).getName());
        }

        for (Place p : places) {
            mListPlacesAdapter.add(p);
        }

//            super.onPostExecute(places);
//        if (places != null) {
//                mListPlacesAdapter.clear();
//            mListPlacesAdapter = new ListPlacesAdapter(mContext, places);
            //lists of places를 이용해서 ListPlacesAdapter 생성하기

//        }
//        return listViewPlaces.setAdapter(adapter);
//        mListView.setAdapter(lpa);
        placeDAO.close();
    }
}