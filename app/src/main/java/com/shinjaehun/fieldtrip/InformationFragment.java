package com.shinjaehun.fieldtrip;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
//    Place place;

//    long id;

//    public static InformationFragment newInstance(Place place) {

    public static InformationFragment newInstance(long placeId) {

        //이건 effective java에 나오는 기술인데
        //생성자 대신 static factory 메소드 사용하기
        //'자신의 클래스 인스턴스만 반환하는 생성자와 달리 static factory 메소드는 자신이 반환하는 타입의
        // 어떤 서브 타입 객체도 반환할 수 있다.'

        InformationFragment fragment = new InformationFragment();
        Bundle args = new Bundle();
//        args.putSerializable(DESCRIBABLE_KEY, place);
        args.putLong(DESCRIBABLE_KEY, placeId);
        fragment.setArguments(args);

        //이렇게 하여 PlaceActivity에서 다루고 있는 place 객체를 Fragment로 넘길 수 있다.
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_information, container, false);
//        place = (Place)getArguments().getSerializable(DESCRIBABLE_KEY);
        final long id = getArguments().getLong(DESCRIBABLE_KEY);
        PlaceDAO placeDAO = new PlaceDAO(this.getActivity());
        Place place = placeDAO.getPlaceById(id);
        //위 생성자를 통해 넘어온 place를 받아온다

        TextView detail = (TextView)v.findViewById(R.id.detail);
        detail.setText(place.getDetail());
        //해당 place에 대한 세부 정보 표시하기

        ImageView map = (ImageView)v.findViewById(R.id.map);
        int resourceId = getActivity().getResources().getIdentifier(place.getPic() + "_map", "drawable", getActivity().getPackageName());

        if (resourceId != 0) {
            map.setImageResource(resourceId);
        } else {
            map.setImageResource(R.drawable.noimage);
        }
        //map 표시하기

//        switch (place.getPic()) {
//            case "jejumuseum":
//                map.setImageResource(R.drawable.jejumuseum_map);
//                break;
//            default:
//                map.setImageResource(R.drawable.noimage);
//                break;
//        }

        final String location = place.getLocation();
        //place 좌표 받아오기

        map.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse(location);
//                Uri gmmIntentUri = Uri.parse("geo:33.5140015,126.5467813?q=국립제주박물관");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                //place 좌표를 이용해서 명시적 Intent 실행하기
            }
        });

//        FloatingActionButton userInput = (FloatingActionButton)findViewById(R.id.user_input);
        Button userInput = (Button)v.findViewById(R.id.btn_usr_input);
//        ImageButton userInput = (ImageButton)findViewById(R.id.user_input);

        userInput.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
//                InputFragment inputFragment = InputFragment.newInstance(place);
                InputFragment inputFragment = InputFragment.newInstance(id);
                openFragment(inputFragment);
                //버튼을 클릭하면 InputFragment로 교체
            }
        });

        return v;
    }


    private void openFragment(final Fragment fragment) {
        //Fragment 교체를 위해 FragmentTransaction을 시작하는 부분을 openFragment()로 넘김

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.animator.gla_there_come, R.animator.gla_there_gone);
        //프레그먼트 전환할 때 애니메이션 효과 추가
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }
}
