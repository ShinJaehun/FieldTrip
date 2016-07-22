package com.shinjaehun.fieldtrip;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Rating;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.Exchanger;

/**
 * Created by shinjaehun on 2016-05-28.
 */
public class InputFragment extends Fragment {

    public static final String TAG = "InputFragment";

    private RatingBar ratingRB;
    private EditText opinionET;
    private Button sendMailBT;
    private Button submitBT;
    private Button getPhotoBT;
    private EditText dateET;
    private ImageView datePickerIV;
    private ImageView photoIV;

    private Uri selectedImage;
//    private String score;
//    private int year, month, day;
//    private String date;
//    private Place place;
//    private String userInput;
//    PlaceDAO placeDAO;
//    String today;
//    long id;

    private static final String DESCRIBABLE_KEY = "describable_key";
    private static final int REQ_CODE_SELECT_IMAGE = 100;
    long id;

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

        id = getArguments().getLong(DESCRIBABLE_KEY);
        final PlaceDAO placeDAO = new PlaceDAO(this.getActivity());
        final Place place = placeDAO.getPlaceById(id);

        ratingRB = (RatingBar)v.findViewById(R.id.rating);
        opinionET = (EditText)v.findViewById(R.id.opinion);
        sendMailBT = (Button)v.findViewById(R.id.btn_send_mail);
        submitBT = (Button)v.findViewById(R.id.btn_submit);
        getPhotoBT = (Button)v.findViewById(R.id.btn_get_photo);
        dateET = (EditText)v.findViewById(R.id.date_info);
        datePickerIV = (ImageView)v.findViewById(R.id.date_picker);
        photoIV = (ImageView)v.findViewById(R.id.photo);

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
//            Log.v(TAG, "Get Rating : " + place.getScore() + "!\n");


            if (place.getUserInput() != null) {
                opinionET.setText(place.getUserInput());
            } else {
                opinionET.setText("");
            }

            if (place.getUserPhoto() != null) {
                try {
                    InputStream imageStream = getActivity().getContentResolver().openInputStream(Uri.parse(place.getUserPhoto()));
                    Log.v(TAG, "Get User Photo Image : " + Uri.parse(place.getUserPhoto()).toString());

                    Bitmap image_bitmap = BitmapFactory.decodeStream(imageStream);
                    //place에서 사진 경로가 저장되어 있는 String을 URI 형태로 변환하고

                    photoIV.setImageBitmap(image_bitmap);
                    photoIV.setVisibility(View.VISIBLE);
                    //해당 비트맵을 photoIV에 표시

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                photoIV.setVisibility(View.GONE);
            }
        }

        datePickerIV.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), dateSetListener, year, month, day).show();
            }
        });
        //달력 아이콘 클릭하면 DatePickerDialog가 실행됨

        getPhotoBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
                //갤러리에서 이미지를 받아올 수 있도록 명시적 intent 실행
            }
        });

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

                if (selectedImage == null) {
                    placeDAO.updatePlace(id, 1, date, score, userInput, null);
                } else {
                    placeDAO.updatePlace(id, 1, date, score, userInput, selectedImage.toString());
                }
                // DB 업데이트

//                getActivity().finish();
                //activity를 종료해서 다시 categoryActivity로 돌아간다.

                InformationFragment informationFragment = InformationFragment.newInstance(id);
                openFragment(informationFragment);
            }
        });

        return v;
    }

    private void openFragment(final Fragment fragment) {
        //Fragment 교체를 위해 FragmentTransaction을 시작하는 부분을 openFragment()로 넘김

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.animator.gla_back_gone, R.animator.gla_back_come);
        //프레그먼트 전환할 때 애니메이션 효과 추가
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //갤러리에서 받아온 이미지 이미지 뷰로 표시
//        Toast.makeText(getBaseContext(), "resultCode : "+resultCode,Toast.LENGTH_SHORT).show();

        if(requestCode == REQ_CODE_SELECT_IMAGE)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                try {

                    selectedImage = data.getData();
                    Log.v(TAG, "Selected Image : " + selectedImage.toString());
                    //Uri에서 이미지 이름을 얻어온다.

                    InputStream imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                    Bitmap image_bitmap = BitmapFactory.decodeStream(imageStream);
                    //이미지 데이터를 비트맵으로 받아온다.

                    //배치해놓은 ImageView에 set
                    photoIV.setImageBitmap(image_bitmap);
                    photoIV.setVisibility(View.VISIBLE);
                }
                catch (FileNotFoundException e) { 		e.printStackTrace(); 			}
                catch (IOException e)           {		e.printStackTrace(); 			}
                catch (Exception e)		         {      e.printStackTrace();			}
            }
        }
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

        intent.putExtra(Intent.EXTRA_SUBJECT, dateET.getText().toString() + " " + p.getName());
        intent.putExtra(Intent.EXTRA_TEXT, dateET.getText().toString() + " " + p.getName() + "에 다녀왔습니다.\n" + "별점 : " + (int)(ratingRB.getRating()) + "점!\n" + opinion);
//        Log.v(TAG, "Rating : " + ratingRB.getRating() + "!\n");

        /*
        알고리즘
        이미지를 선택한 경우
        무조건 selected

        이미지를 선택하지 않은 경우
        DB에 있음 전에 선택했던 이미지 선택
        DB에 없음 plain
        */
        if (selectedImage != null) {
            intent.putExtra(Intent.EXTRA_STREAM, selectedImage);
            intent.setType("image/png");
        } else {
            if (p.getUserPhoto() == null) {
                intent.setType("text/plain");
            } else {
                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(p.getUserPhoto()));
                intent.setType("image/png");
            }
        }

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
