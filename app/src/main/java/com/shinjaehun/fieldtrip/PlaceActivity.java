package com.shinjaehun.fieldtrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by shinjaehun on 2016-05-21.
 */
public class PlaceActivity extends AppCompatActivity {
    public static final String EXTRA_SELECTED_PLACE = "extra_key_selected_place";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        Intent intent = getIntent();
        Place place = (Place)intent.getExtras().getSerializable(EXTRA_SELECTED_PLACE);

        TextView name = (TextView)findViewById(R.id.name);
        name.setText(place.getName());

        TextView pic = (TextView)findViewById(R.id.pic);
        pic.setText(place.getPic());

        TextView detail = (TextView)findViewById(R.id.detail);
        detail.setText(place.getDetail());

        TextView map = (TextView)findViewById(R.id.map);
        map.setText(place.getMap());



    }
}
