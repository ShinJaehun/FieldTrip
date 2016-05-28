package com.shinjaehun.fieldtrip;


import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by shinjaehun on 2016-05-28.
 */
public class InformationFragment extends Fragment {
    private static final String DESCRIBABLE_KEY = "describable_key";


    public static InformationFragment newInstance(Place place) {
        InformationFragment fragment = new InformationFragment();
        Bundle args = new Bundle();
        args.putSerializable(DESCRIBABLE_KEY, place);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_information, container, false);
        Place place = (Place)getArguments().getSerializable(DESCRIBABLE_KEY);

        TextView detail = (TextView)v.findViewById(R.id.detail);
        detail.setText(place.getDetail());


        ImageView map = (ImageView)v.findViewById(R.id.map);
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


        return v;
    }
}
