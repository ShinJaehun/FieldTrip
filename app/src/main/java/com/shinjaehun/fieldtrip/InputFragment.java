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
//    private EditText sendEmailET;
    private EditText opinionET;
    private Button submitBT;
    private EditText dateET;
    private ImageView datePickerIV;
    private String score;
    private String sendEmail;
    private int year, month, day;
    private String date;
    private Place place;


    private static final String DESCRIBABLE_KEY = "describable_key";


    public static InputFragment newInstance(Place place) {
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

        ratingRB = (RatingBar)v.findViewById(R.id.rating);
//        sendEmailET = (EditText)v.findViewById(R.id.send_email);
        opinionET = (EditText)v.findViewById(R.id.opinion);
        submitBT = (Button)v.findViewById(R.id.btn_submit);
        dateET = (EditText)v.findViewById(R.id.date_info);
        datePickerIV = (ImageView)v.findViewById(R.id.date_picker);

        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day= calendar.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        date = sdf.format(new Date());
        dateET.setText(date);

        datePickerIV.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), dateSetListener, year, month, day).show();
            }
        });

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
            }
        });


        return v;
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateET.setText(String.format("%d/%d/%d", year, monthOfYear+1, dayOfMonth));
        }
    };

    public void submit() {
        if (!validate()) {
            onSubmitFailed();
            return;
        }

        String opinion = opinionET.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);

//        String[] tos = {sendEmail};
//        intent.putExtra(Intent.EXTRA_EMAIL, tos);
        intent.putExtra(Intent.EXTRA_SUBJECT, dateET.getText().toString() + " " + place.getName() + " 다녀와서");
        intent.putExtra(Intent.EXTRA_TEXT, dateET.getText().toString() + " " + place.getName() + "에 다녀왔습니다.\n" + opinion);
        intent.setType("text/plain");
        startActivity(intent);

//        Uri uri = Uri.parse("mailTo:" + sendEmail);
//        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
//        startActivity(intent);
    }

    public void onSubmitFailed() {
        Toast.makeText(getActivity(), "메일 보내기에 실패했습니다", Toast.LENGTH_SHORT).show();
    }

    private boolean validate() {
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
