package com.shinjaehun.fieldtrip;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by shinjaehun on 2016-05-16.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //로그인 관련 처리가 필요함....startActivityResult로...
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);

        ImageView peopleIV = (ImageView)findViewById(R.id.image_people);
        peopleIV.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PeopleActivity.class);
                startActivity(intent);
            }
        });

        ImageView historyIV = (ImageView)findViewById(R.id.image_history);
        historyIV.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(intent);
            }
        });

    }

}
