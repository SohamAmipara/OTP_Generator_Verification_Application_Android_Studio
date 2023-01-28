package com.example.otpgenerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class VerificationOTP extends AppCompatActivity {

    EditText inputOTP1, inputOTP2, inputOTP3, inputOTP4, inputOTP5, inputOTP6;
    TextView tvContactNumber, tvResendOTP;
    Button btnSubmit;
    String getBackendOTP;
    ProgressBar progressBarForVerifyOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // NoActionBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_verification_otp);

        inputOTP1 = findViewById(R.id.inputOTP1);
        inputOTP2 = findViewById(R.id.inputOTP2);
        inputOTP3 = findViewById(R.id.inputOTP3);
        inputOTP4 = findViewById(R.id.inputOTP4);
        inputOTP5 = findViewById(R.id.inputOTP5);
        inputOTP6 = findViewById(R.id.inputOTP6);

        tvResendOTP = findViewById(R.id.tvResendOTP);
        tvContactNumber = findViewById(R.id.tvContactNumber);
        tvContactNumber.setText(String.format(
                "+91-%s", getIntent().getStringExtra("mobile")
        ));

        getBackendOTP = getIntent().getStringExtra("backendOTP");

        progressBarForVerifyOTP = findViewById(R.id.progressBarForVerifyOTP);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputOTP1.getText().toString().trim().isEmpty()
                        && !inputOTP2.getText().toString().trim().isEmpty()
                        && !inputOTP3.getText().toString().trim().isEmpty()
                        && !inputOTP4.getText().toString().trim().isEmpty()
                        && !inputOTP5.getText().toString().trim().isEmpty()
                        && !inputOTP6.getText().toString().trim().isEmpty()) {
                    String enterCodeOPT = inputOTP1.getText().toString()
                            + inputOTP2.getText().toString()
                            + inputOTP3.getText().toString()
                            + inputOTP4.getText().toString()
                            + inputOTP5.getText().toString()
                            + inputOTP6.getText().toString();

                    if (getBackendOTP != null) {
                        progressBarForVerifyOTP.setVisibility(View.VISIBLE);
                        btnSubmit.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                getBackendOTP, enterCodeOPT
                        );

                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBarForVerifyOTP.setVisibility(View.GONE);
                                        btnSubmit.setVisibility(View.VISIBLE);

                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(VerificationOTP.this, "Please enter the correct OTP", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(VerificationOTP.this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                    }
//                    Toast.makeText(VerificationOTP.this, "OTP Successfully Verify", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(VerificationOTP.this, "Please Enter All Numbers", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvResendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        VerificationOTP.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(VerificationOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newBackendOTP, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                getBackendOTP = newBackendOTP;
                                Toast.makeText(VerificationOTP.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });

        moveOTPNumber();
    }

    private void moveOTPNumber() {
        inputOTP1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputOTP2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputOTP2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputOTP3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputOTP3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputOTP4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputOTP4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputOTP5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputOTP5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputOTP6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}