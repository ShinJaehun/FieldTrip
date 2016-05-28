package com.shinjaehun.fieldtrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by shinjaehun on 2016-05-28.
 */
public class UserInputActivity extends AppCompatActivity {
    public static final String EXTRA_SELECTED_PLACE = "extra_key_selected_place";
    private Place place;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_information);

        Intent intent = getIntent();
        place = (Place)intent.getExtras().getSerializable(EXTRA_SELECTED_PLACE);


    }
}
