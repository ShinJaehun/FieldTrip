package com.shinjaehun.fieldtrip;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
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
    private Place place;

//    private FragmentManager fm;
//    private FragmentTransaction ft;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        Intent intent = getIntent();
        place = (Place)intent.getExtras().getSerializable(EXTRA_SELECTED_PLACE);

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


//        fm = getFragmentManager();
//        ft = fm.beginTransaction();

        InformationFragment informationFragment = InformationFragment.newInstance(place);
        openFragment(informationFragment);
//        ft.add(R.id.fragment_container, informationFragment);


        FloatingActionButton userInput = (FloatingActionButton)findViewById(R.id.user_input);
        userInput.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(this, UserInputActivity.class);
//                intent.putExtra(EXTRA_SELECTED_PLACE, place);
//                startActivity(intent);
                InputFragment inputFragment = InputFragment.newInstance(place);
                openFragment(inputFragment);
//                ft.replace(R.id.fragment_container, inputFragment);


            }
        });

    }

    private void openFragment(final Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }
}
