package com.shinjaehun.fieldtrip;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.media.Rating;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.Exchanger;

/**
 * Created by shinjaehun on 2016-05-28.
 */
public class InputFragment extends Fragment {

    private RatingBar ratingRB;
    //아직까지 ratingRB를 이용해서 처리할 내용을 정하지 못함.
    private EditText opinionET;
    private Button submitBT;
    private EditText dateET;
    private ImageView datePickerIV;
    private String score;
    private int year, month, day;
    private String date;
    private Place place;


    private static final String DESCRIBABLE_KEY = "describable_key";

    public static InputFragment newInstance(Place place) {
        //Activity에서 Fragment로 object를 넘기기 위해 static factory 매소드를 이용함
        InputFragment fragment = new InputFragment();
        Bundle args = new Bundle();
        args.putSerializable(DESCRIBABLE_KEY, place);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_input, container, false);

        place = (Place)getArguments().getSerializable(DESCRIBABLE_KEY);
        //place 받아오기

        ratingRB = (RatingBar)v.findViewById(R.id.rating);
        opinionET = (EditText)v.findViewById(R.id.opinion);
        submitBT = (Button)v.findViewById(R.id.btn_submit);
        dateET = (EditText)v.findViewById(R.id.date_info);
        datePickerIV = (ImageView)v.findViewById(R.id.date_picker);

        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day= calendar.get(Calendar.DAY_OF_MONTH);
        //오늘날짜를 받아오기 위한 year, month, day 정보

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        date = sdf.format(new Date());
        //SimpleDateFormat을 이용하여 오늘 날짜를 String 타입의 date로 넘기기

        dateET.setText(date);
        //EditText에 오늘 날짜 박아 넣기

        datePickerIV.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), dateSetListener, year, month, day).show();
            }
        });
        //달력 아이콘 클릭하면 DatePickerDialog가 실행됨

        submitBT.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                submit();
            }
        });

        ratingRB.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                score = (String.valueOf(rating));
                //점수를 받아오는 것 까지는 했는데 아직 뭘 해야할지 결정하지 못함.
            }
        });

        return v;
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateET.setText(String.format("%d/%d/%d", year, monthOfYear+1, dayOfMonth));
            //DatePickerDialog에서 지정한 날짜로 EditText에 박아 넣기
        }
    };

    private void submit() {
        if (!validate()) {
            onSubmitFailed();
            return;
        }

        //InputFragment에서 입력한 내용을 명시적 Intent를 활용하여 메일 클라이언트로 전달하기
        String opinion = opinionET.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.putExtra(Intent.EXTRA_SUBJECT, dateET.getText().toString() + " " + place.getName() + " 다녀와서");
        intent.putExtra(Intent.EXTRA_TEXT, dateET.getText().toString() + " " + place.getName() + "에 다녀왔습니다.\n" + opinion);
        intent.setType("text/plain");
        startActivity(intent);
    }

    private void onSubmitFailed() {
        Toast.makeText(getActivity(), "메일 보내기에 실패했습니다", Toast.LENGTH_SHORT).show();
    }

    private boolean validate() {
        //dateET에 입력한 형식이 날짜 형식이 맞는지 검증
        boolean valid = true;

//        sendEmail = sendEmailET.getText().toString();
//
//        if (sendEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(sendEmail).matches()) {
//            sendEmailET.setError("메일 형식에 맞게 입력하세요");
//            valid = false;
//        } else {
//            sendEmailET.setError(null);
//        }
        String msg = null;

        date = dateET.getText().toString();
        if (date == null) {
            msg = "날짜 형식에 맞게 입력하세요";
            valid = false;
        }
//        else {
//            String cleanDate = date.replaceAll("[^\\d.]", "");
//            if (!isNumber(cleanDate)) {
//                msg = "숫자형식이 아닙니다";
//                valid = false;
//            }
//        }

         Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

         return valid;
    }

//    private static boolean isNumber(String str) {
//        boolean result = false;
//
//        try {
//            Double.parseDouble(str);
//            result = true;
//        } catch (Exception e) {
//
//        }
//
//        return result;
//    }

}
