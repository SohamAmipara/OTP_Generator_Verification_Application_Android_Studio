package com.example.otpgenerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class HomePage extends AppCompatActivity {

    EditText etContactNumber;
    Button btnGetOTP;
    ProgressBar progressBarForGetOTP;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // NoActionBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_home_page);

        etContactNumber = findViewById(R.id.etContactNumber);
        btnGetOTP = findViewById(R.id.btnGetOTP);
        progressBarForGetOTP = findViewById(R.id.progressBarForGetOTP);


        btnGetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etContactNumber.getText().toString().trim().isEmpty()) {
                    if ((etContactNumber.getText().toString().trim()).length() == 10) {

                        progressBarForGetOTP.setVisibility(View.VISIBLE);
                        btnGetOTP.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + etContactNumber.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                HomePage.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBarForGetOTP.setVisibility(View.GONE);
                                        btnGetOTP.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBarForGetOTP.setVisibility(View.GONE);
                                        btnGetOTP.setVisibility(View.VISIBLE);
                                        Toast.makeText(HomePage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendOTP, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBarForGetOTP.setVisibility(View.GONE);
                                        btnGetOTP.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(getApplicationContext(), VerificationOTP.class);
                                        intent.putExtra("mobile", etContactNumber.getText().toString());
                                        intent.putExtra("backendOTP", backendOTP);
                                        startActivity(intent);
                                    }
                                }
                        );

                    } else {
                        Toast.makeText(HomePage.this, "Please Enter Correct Number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomePage.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}