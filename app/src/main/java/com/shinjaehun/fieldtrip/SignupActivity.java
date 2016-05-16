package com.shinjaehun.fieldtrip;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by shinjaehun on 2016-05-16.
 */
public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    EditText unameET;
    EditText emailET;
    EditText passET;
    Button signupBT;
    TextView loginTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        unameET = (EditText)findViewById(R.id.input_uname);
        emailET = (EditText)findViewById(R.id.input_email);
        passET = (EditText)findViewById(R.id.input_password);

        signupBT = (Button)findViewById(R.id.btn_signup);
        signupBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginTV = (TextView)findViewById(R.id.link_login);
        loginTV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signupBT.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("사용자를 생성합니다...");
        progressDialog.show();

        String uname = unameET.getText().toString();
        String email = emailET.getText().toString();
        String pass = passET.getText().toString();


        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        onSignupSucess();
                        progressDialog.dismiss();
                    }
                }, 3000
        );
    }

    public void onSignupSucess() {
        signupBT.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "로그인에 실패했습니다", Toast.LENGTH_LONG).show();
        signupBT.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String uname = unameET.getText().toString();
        String email = emailET.getText().toString();
        String pass = passET.getText().toString();

        if (uname.isEmpty() || uname.length() < 3) {
            unameET.setError("최소 3자 이상 입력해주세요");
            valid = false;
        } else {
            unameET.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailET.setError("전자메일 형식에 맞게 입력하세요");
            valid = false;
        } else {
            emailET.setError(null);
        }

        if (pass.isEmpty() || pass.length() < 4 || pass.length() > 10) {
            passET.setError("영어 또는 숫자로 4자 이상, 10자 이하로 입력해주세요");
            valid = false;
        } else {
            passET.setError(null);
        }

        return valid;
    }
}
