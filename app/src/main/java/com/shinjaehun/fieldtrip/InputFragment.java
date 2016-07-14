package com.shinjaehun.fieldtrip;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Rating;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
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
    private Button sendMailBT;
    private Button submitBT;
    private EditText dateET;
    private ImageView datePickerIV;
//    private String score;
//    private int year, month, day;
//    private String date;
//    private Place place;
//    private String userInput;
//    PlaceDAO placeDAO;
//    String today;
//    long id;

    private static final String DESCRIBABLE_KEY = "describable_key";

//    public static InputFragment newInstance(Place place) {

    public static InputFragment newInstance(long placeId) {
        //Activity에서 Fragment로 object를 넘기기 위해 static factory 매소드를 이용함
        InputFragment fragment = new InputFragment();
        Bundle args = new Bundle();
//        args.putSerializable(DESCRIBABLE_KEY, place);
        args.putLong(DESCRIBABLE_KEY, placeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_input, container, false);
//        place = (Place)getArguments().getSerializable(DESCRIBABLE_KEY);
        //place 받아오기

        final long id = getArguments().getLong(DESCRIBABLE_KEY);
        final PlaceDAO placeDAO = new PlaceDAO(this.getActivity());
        final Place place = placeDAO.getPlaceById(id);

        ratingRB = (RatingBar)v.findViewById(R.id.rating);
        opinionET = (EditText)v.findViewById(R.id.opinion);
        sendMailBT = (Button)v.findViewById(R.id.btn_send_mail);
        submitBT = (Button)v.findViewById(R.id.btn_submit);
        dateET = (EditText)v.findViewById(R.id.date_info);
        datePickerIV = (ImageView)v.findViewById(R.id.date_picker);

        GregorianCalendar calendar = new GregorianCalendar();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        //오늘날짜를 받아오기 위한 year, month, day 정보

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String today = sdf.format(new Date());
        //SimpleDateFormat을 이용하여 오늘 날짜를 String 타입의 date로 넘기기

        if (place.isVisited() == 0) {
        //방문한 적이 없다면...

            dateET.setText(today);
            //EditText에 오늘 날짜 박아 넣기

        } else {
            //방문한 적이 있다면...

            if (place.getTheDate() != null) {
                dateET.setText(place.getTheDate());
            } else {
                dateET.setText(today);
            }
            if (place.getScore() != null) {
                ratingRB.setRating(Float.parseFloat(place.getScore()));
            } else {
                ratingRB.setRating(0.0f);
            }

            if (place.getUserInput() != null) {
                opinionET.setText(place.getUserInput());
            } else {
                opinionET.setText("");
            }
        }

        datePickerIV.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), dateSetListener, year, month, day).show();
            }
        });
        //달력 아이콘 클릭하면 DatePickerDialog가 실행됨

        sendMailBT.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                sendMail(place);
            }
        });

//        ratingRB.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
////                String s = (String.valueOf(rating));
//                //점수를 받아오는 것 까지는 했는데 아직 뭘 해야할지 결정하지 못함.
//            }
//        });

        submitBT.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
//                AlertDialog mDialog = createDialog();
//                mDialog.show();
                String date = dateET.getText().toString();
                String score = String.valueOf(ratingRB.getRating());
                String userInput = opinionET.getText().toString();
                placeDAO.updatePlace(id, 1, date, score, userInput);
                // DB 업데이트

                getActivity().finish();
                //activity를 종료해서 다시 categoryActivity로 돌아간다.
            }
        });

        return v;
    }

//    private AlertDialog createDialog() {
//        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
//        ab.setMessage("저장하시겠습니까?");
//        ab.setCancelable(true);
//        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String date = dateET.getText().toString();
//                String score = String.valueOf(ratingRB.getRating());
//                String userInput = opinionET.getText().toString();
//                placeDAO.updatePlace(id, 1, date, score, userInput);
//
//                getActivity().finish();
////                changeFragment();
//            }
//
//        });
//
//        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        return ab.create();
//
//    }

//    private void changeFragment() {
//        InformationFragment informationFragment = InformationFragment.newInstance(id);
//        //기본적으로 InformationFragment가 표시됨
//        openFragment(informationFragment);
//    }

//    private void openFragment(final Fragment fragment) {
//        //Fragment 교체를 위해 FragmentTransaction을 시작하는 부분을 openFragment()로 넘김
//
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.fragment_container, fragment);
//        ft.commit();
//    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateET.setText(String.format("%d/%d/%d", year, monthOfYear+1, dayOfMonth));
            //DatePickerDialog에서 지정한 날짜로 EditText에 박아 넣기
        }
    };

    private void sendMail(Place p) {
//        if (!validate()) {
//            onSubmitFailed();
//            return;
//        }

        //InputFragment에서 입력한 내용을 명시적 Intent를 활용하여 메일 클라이언트로 전달하기
        String opinion = opinionET.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.putExtra(Intent.EXTRA_SUBJECT, dateET.getText().toString() + " " + p.getName() + " 다녀와서");
        intent.putExtra(Intent.EXTRA_TEXT, dateET.getText().toString() + " " + p.getName() + "에 다녀왔습니다.\n" + opinion);
        intent.setType("text/plain");
        startActivity(intent);
    }

//    private void onSubmitFailed() {
//        Toast.makeText(getActivity(), "메일 보내기에 실패했습니다", Toast.LENGTH_SHORT).show();
//    }

//    private boolean validate() {
//        //dateET에 입력한 형식이 날짜 형식이 맞는지 검증
//        boolean valid = true;
//
////        sendEmail = sendEmailET.getText().toString();
////
////        if (sendEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(sendEmail).matches()) {
////            sendEmailET.setError("메일 형식에 맞게 입력하세요");
////            valid = false;
////        } else {
////            sendEmailET.setError(null);
////        }
//        String msg = null;
//
//        date = dateET.getText().toString();
//        if (date == null) {
//            msg = "날짜 형식에 맞게 입력하세요";
//            valid = false;
//        }
////        else {
////            String cleanDate = date.replaceAll("[^\\d.]", "");
////            if (!isNumber(cleanDate)) {
////                msg = "숫자형식이 아닙니다";
////                valid = false;
////            }
////        }
//
//         Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
//
//         return valid;
//    }

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
