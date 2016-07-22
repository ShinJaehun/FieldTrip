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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by shinjaehun on 2016-05-21.
 */
public class PlaceActivity extends AppCompatActivity {
    public static final String EXTRA_SELECTED_PLACE = "extra_key_selected_place";
//    public PlaceDAO placeDAO;
//    private Place place;
//    long placeId;

//    private FragmentManager fm;
//    private FragmentTransaction ft;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        Intent intent = getIntent();
//        place = (Place)intent.getExtras().getSerializable(EXTRA_SELECTED_PLACE);

        //CategoryActivity에서 호출할 때 넘긴 place 받아오기

        long id = 0;
        long placeId = intent.getLongExtra(EXTRA_SELECTED_PLACE, id);
        PlaceDAO placeDAO = new PlaceDAO(this);
        Place place = placeDAO.getPlaceById(placeId);

        //CollaspingToolbarLayout을 위한 설정
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        ctl.setTitle(place.getName());

        ImageView img = (ImageView)findViewById(R.id.pic);
        int resourceId = getApplicationContext().getResources().getIdentifier(place.getPic() + "_title", "drawable", getApplicationContext().getPackageName());
        //웃기긴 한데 이런식으로 파일명을 찾는 것도 가능하긴 하구만...
        //어쨌든 나중에 통일할 필요가 있다.
        if (resourceId != 0) {
            img.setImageResource(resourceId);
        } else {
            img.setImageResource(R.drawable.noimage);
            //resource 찾을 수 없으면 no image 표시하기
        }

//이런 식으로는 DB에 저장된 자료가 기하급수적으로 늘어났을 때 처리하기 곤란할 수 밖에 없다.
//        switch (place.getPic()) {
//            case "jejumuseum_icon":
//                img.setImageResource(R.drawable.jejumuseum_title);
//                break;
//            default:
//                img.setImageResource(R.drawable.noimage);
//                break;
//        }


        InformationFragment informationFragment = InformationFragment.newInstance(placeId);
//        InformationFragment informationFragment = InformationFragment.newInstance(place);
        //기본적으로 InformationFragment가 표시됨
        openFragment(informationFragment);

////        FloatingActionButton userInput = (FloatingActionButton)findViewById(R.id.user_input);
//        Button userInput = (Button)findViewById(R.id.btn_usr_input);
////        ImageButton userInput = (ImageButton)findViewById(R.id.user_input);
//        userInput.setOnClickListener(new Button.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                InputFragment inputFragment = InputFragment.newInstance(placeId);
//                openFragment(inputFragment);
//                //버튼을 클릭하면 InputFragment로 교체
//            }
//        });

    }

    private void openFragment(final Fragment fragment) {
        //Fragment 교체를 위해 FragmentTransaction을 시작하는 부분을 openFragment()로 넘김

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

}
