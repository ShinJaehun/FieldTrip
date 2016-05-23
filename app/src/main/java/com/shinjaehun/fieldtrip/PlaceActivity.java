package com.shinjaehun.fieldtrip;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.CollapsibleActionView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        ctl.setTitle(place.getName());

        ImageView img = (ImageView)findViewById(R.id.pic);
        switch (place.getPic()) {
            case "jejumuseum":
                img.setImageResource(R.drawable.jejumuseum_big);
                break;
            default:
                img.setImageResource(R.drawable.noimage);
                break;
        }

        TextView detail = (TextView)findViewById(R.id.detail);
        detail.setText(place.getDetail());


        ImageView map = (ImageView)findViewById(R.id.map);
        switch (place.getPic()) {
            case "jejumuseum":
                map.setImageResource(R.drawable.map_jejumuseum);
                break;
            default:
                map.setImageResource(R.drawable.noimage);
                break;
        }

        map.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:33.5140015,126.5467813?q=국립제주박물관");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });


    }
}
