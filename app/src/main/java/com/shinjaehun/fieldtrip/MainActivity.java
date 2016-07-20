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

    DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //로그인 관련 처리가 필요함....startActivityResult로...
//        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//        startActivity(intent);

//        ImageView peopleIV = (ImageView)findViewById(R.id.image_people);
//        peopleIV.setOnClickListener(new Button.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), PeopleActivity.class);
//                startActivity(intent);
//            }
//        });

        dbHelper = DBHelper.getInstance(this);
        //CategoryActiviy에서 DB에 들어간 raw data를 화면에 표시하려면 그 전에 먼저 DBHelper가 한번은 실행되어야 한다.

        ImageView peopleIV = (ImageView)findViewById(R.id.image_people);
        peopleIV.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                intent.putExtra(CategoryActivity.EXTRA_SELECTED_CATEGORY, "people");
                startActivity(intent);
            }
        });

        ImageView historyIV = (ImageView)findViewById(R.id.image_history);
        historyIV.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                intent.putExtra(CategoryActivity.EXTRA_SELECTED_CATEGORY, "history");
                startActivity(intent);
            }
        });

        ImageView natureIV = (ImageView)findViewById(R.id.image_nature);
        natureIV.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                intent.putExtra(CategoryActivity.EXTRA_SELECTED_CATEGORY, "nature");
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
