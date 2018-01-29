package com.steven.example.seamlessotpexample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.widget.Toast;

import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private AppCompatEditText mDigit1, mDigit2, mDigit3, mDigit4;
    private SmsVerifyCatcher mSmsVerifyCatcher;
    private static final int READ_SMS_PERMISSION = 104;
    private static final String PHONE_NUMBER = "123456789";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDigit1 = findViewById(R.id.digit1);
        mDigit2 = findViewById(R.id.digit2);
        mDigit3 = findViewById(R.id.digit3);
        mDigit4 = findViewById(R.id.digit4);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        }
        catchSMS();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSmsVerifyCatcher.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSmsVerifyCatcher.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            catchSMS();
        }
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, READ_SMS_PERMISSION);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, READ_SMS_PERMISSION);
            }
        }
    }

    private void catchSMS() {
        mSmsVerifyCatcher = new SmsVerifyCatcher(this, message -> {
            logMessage(message);
            mDigit1.setText(String.valueOf(message.charAt(0)));
            mDigit2.setText(String.valueOf(message.charAt(1)));
            mDigit3.setText(String.valueOf(message.charAt(2)));
            mDigit4.setText(String.valueOf(message.charAt(3)));
        });
        mSmsVerifyCatcher.setPhoneNumberFilter(PHONE_NUMBER);
    }

    private void logMessage(String message) {
        Log.d(TAG, message);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
