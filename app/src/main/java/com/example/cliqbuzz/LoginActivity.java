package com.example.cliqbuzz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {

  CircularProgressButton btn_submit,btn_login;
  EditText et_number,et_otp;
  ProgressBar progressBar;
  LinearLayout llmobile , llotp;
  private String verificationid;
  private FirebaseAuth mAuth;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    mAuth = FirebaseAuth.getInstance();
    progressBar = findViewById(R.id.progressBar);
    btn_submit = findViewById(R.id.btn_submit);
    btn_login = findViewById(R.id.btn_login);
    et_number = findViewById(R.id.et_number);
    et_otp = findViewById(R.id.et_otp);
    llmobile = findViewById(R.id.ll_mobile);
    llotp = findViewById(R.id.ll_otp);

  }

  public void onLogin(View v)
  {
    String number = et_number.getText().toString().trim();
    if (number.isEmpty() || number.length() < 10) {
      et_number.setError("Valid number is required");
      et_number.requestFocus();
      return;
    }
    else
    {
      String phonenumber = "+91"+number;
      sendVerificationCode(phonenumber);
      progressBar.setVisibility(View.VISIBLE);
      new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
          progressBar.setVisibility(View.GONE);
          llmobile.setVisibility(View.GONE);
          llotp.setVisibility(View.VISIBLE);
        }
      },3000);
    }
  }
  public void onSubmit(View v)
  {

    String code = et_otp.getText().toString().trim();

    if ((code.isEmpty() || code.length() < 6)){

      et_otp.setError("Enter code...");
      et_otp.requestFocus();
      return;
    }
    verifyCode(code);
  }

  private void sendVerificationCode(String number){

    PhoneAuthProvider.getInstance().verifyPhoneNumber(
            number,
            60,
            TimeUnit.SECONDS,
            TaskExecutors.MAIN_THREAD,
            mCallBack
    );
  }

  private PhoneAuthProvider.OnVerificationStateChangedCallbacks
          mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

    @Override
    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken)
    {
      super.onCodeSent(s, forceResendingToken);
      verificationid = s;
    }

    @Override
    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
      String code = phoneAuthCredential.getSmsCode();
      if (code != null){
        progressBar.setVisibility(View.VISIBLE);
        verifyCode(code);
      }
    }

    @Override
    public void onVerificationFailed(FirebaseException e) {
      Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

    }
  };
  private void verifyCode(String code){
    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, code);
    signInWithCredential(credential);
  }

  private void signInWithCredential(PhoneAuthCredential credential) {
    mAuth.signInWithCredential(credential)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                  Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                  startActivity(intent);

                } else {
                  Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
              }

            });
  }

  @Override
  protected void onStart() {
    if(mAuth.getCurrentUser()!=null){
      startActivity(new Intent(getApplicationContext(),MainActivity.class));
      finish();
    }
    super.onStart();
  }
}