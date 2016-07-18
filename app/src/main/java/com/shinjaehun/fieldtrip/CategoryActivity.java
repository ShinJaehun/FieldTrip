package com.shinjaehun.fieldtrip;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shinjaehun on 2016-05-20.
 */
public class CategoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String EXTRA_SELECTED_CATEGORY = "extra_key_selected_category";
    public static final String TAG = "HistoryActivity";
    //Debuging을 위한 TAG - 나중에 삭제할 것

    ListView listViewPlaces;
    ListPlacesAdapter adapter;
//    private List<Place> listPlaces;
//    private PlaceDAO placeDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Intent intent = getIntent();
        String category;
        category = intent.getExtras().getString(EXTRA_SELECTED_CATEGORY);
        //MainActivity에서 호출할 때 넘긴 category 받아오기

        initLayout(category);
        //레이아웃 초기화(이런 식으로 정리하니까 깔끔한 거 같아여;;;)

//        placeDAO = new PlaceDAO(this);

        listViewPlaces = (ListView)findViewById(R.id.list_places);

        adapter = new ListPlacesAdapter(this, new ArrayList<Place>());
        listViewPlaces.setAdapter(adapter);

        FetchPlacesListTask placesListTask = new FetchPlacesListTask(this, adapter);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
//            placesListTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, category);
//        else
//            placesListTask.execute(category);
        placesListTask.execute(category);

//        this.listViewPlaces.setAdapter(adapter);

        listViewPlaces.setOnItemClickListener(this);

//        FetchPlacesListTask placesListTask = new FetchPlacesListTask(this);
//        placesListTask.execute(category);
//
////        placeDAO = PlaceDAO.getInstance(this);
//
////        SukSuk app = (SukSuk)getApplication();
////        app.setDAO(placeDAO);
//
//        listPlaces = placeDAO.getPlacesByType(category);
//        //category를 통해 lists of places 불러오기

//        adapter = new ListPlacesAdapter(this, listPlaces);
//        //lists of places를 이용해서 ListPlacesAdapter 생성하기
//
//        for (int i = 0; i < listPlaces.size(); i++) {
//            Log.v(TAG, "place " + i + " : " + listPlaces.get(i).getName());
//        }
        //유형에 따라 lists of places가 제대로 불려왔는지 확인하기 위한 Log 출력 - 나중에 삭제할 것
//        listViewPlaces.setAdapter(adapter);
        //list view에 list view adapter setting하기

    }


//    public class FetchPlacesListTask extends AsyncTask<String, Void, List<Place>> {
//
//        private final Context mContext;
////        private ListPlacesAdapter mListPlacesAdapter;
//        private List<Place> listPlaces;
//
//        public FetchPlacesListTask(Context context) {
//            mContext = context;
////            mListPlacesAdapter = listPlacesAdapter;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            Log.v("AsyncTask", "onPreExecute");
//
//        }
//
//        @Override
//        protected List<Place> doInBackground(String... params) {
//            Log.v("AsyncTask", "doInBackground");
//
//            placeDAO = new PlaceDAO(mContext);
//
////        placeDAO = PlaceDAO.getInstance(this);
//
////        SukSuk app = (SukSuk)getApplication();
////        app.setDAO(placeDAO);
//
//            listPlaces = placeDAO.getPlacesByType(params[0]);
//            //category를 통해 lists of places 불러오기
//            return listPlaces;
//        }
//
//        @Override
//        protected void onPostExecute(List<Place> places) {
////            super.onPostExecute(places);
//            if (places != null) {
////                mListPlacesAdapter.clear();
//                adapter = new ListPlacesAdapter(mContext, places);
//                //lists of places를 이용해서 ListPlacesAdapter 생성하기
//
//                for (int i = 0; i < listPlaces.size(); i++) {
//                    Log.v(TAG, "place " + i + " : " + listPlaces.get(i).getName());
//                }
//                listViewPlaces.setAdapter(adapter);
//            }
//            placeDAO.close();
//        }
//    }

    private void initLayout(String category) {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //setSupportActionBar()는 인자로 받은 툴바를 Activity의 ActionBar로 대체하는 역할을 함

        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        //CollapsingToolbarLayout에 텍스트로 Title을 넣기 위해 필요
        ImageView img = (ImageView)findViewById(R.id.image);
        //CollapsingToolbarLayout에 이미지를 넣기 위해 필요

        switch (category) {
            case "people":
                ctl.setTitle("사람들");
                img.setImageResource(R.drawable.people_bg);
                break;
            case "history":
                ctl.setTitle("역사");
                img.setImageResource(R.drawable.history_bg);
                break;
            case "nature":
                ctl.setTitle("자연환경");
                img.setImageResource(R.drawable.nature_bg);
                break;
            default:
                break;
        }
        //CollapsingToolbarLayout에 타이틀과 배경 이미지 넣기


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Place clickedPlace = adapter.getItem(position);
        //adapter를 이용해서 선택된 place 불러오기

        //Place clickedPlace = placeDAO.getPlaceById(position);
        //이런 형태의 place 불러오기는 안된다. position은 dapter에서 현재 선택된 ID이며
        //getPlaceById의 인자로 넘기는 ID는 DB에 저장된 ID이기 때문이다...

        long placeId = clickedPlace.getId();

        Log.d(TAG, "clickedItem : " + clickedPlace.getName());
        //Debuging을 위한 TAG - 나중에 삭제할 것

        Intent intent = new Intent(this, PlaceActivity.class);
        intent.putExtra(PlaceActivity.EXTRA_SELECTED_PLACE, placeId);

//        intent.putExtra(PlaceActivity.EXTRA_SELECTED_PLACE, clickedPlace);
        startActivity(intent);
        //선택된 place를 넘겨서 PlaceActivity 시작
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
////        placeDAO.close();
//    }
}
