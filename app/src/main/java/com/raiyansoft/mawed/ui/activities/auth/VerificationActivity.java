package com.raiyansoft.mawed.ui.activities.auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.raiyansoft.mawed.HomeActivity;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.model.auth.verifyOTP.ResendCode;
import com.raiyansoft.mawed.model.auth.verifyOTP.VerifyOTP;
import com.raiyansoft.mawed.ui.activities.salon.HomeSalonActivity;
import com.raiyansoft.mawed.utils.Const;
import com.raiyansoft.mawed.utils.HelperMethods;
import com.raiyansoft.mawed.utils.PreferencesManager;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = VerificationActivity.class.getSimpleName();

    @BindView(R.id.rel_container)
    RelativeLayout container;
    @BindView(R.id.pb_loading)
    ProgressBar loading;
    @BindView(R.id.tv_timer)
    TextView tvTimer;
    @BindView(R.id.tv_resend_code)
    TextView tvResendCode;
    @BindView(R.id.pin_view_code)
    PinView pinView;

    private CountDownTimer countTimer;

    @OnClick(R.id.tv_resend_code)
    void onResendCodeClick() {
        startTimer();
        resendVerificationCode();
    }

    @OnClick(R.id.btn_activation)
    void onActivationClick(){

        loading.setVisibility(View.VISIBLE);

        String otp = pinView.getText().toString();

        if (!HelperMethods.isInternetConnected(this)) {
            HelperMethods.showCustomSnackBar(this, container, getString(R.string.check_internet),
                    false, Snackbar.LENGTH_SHORT,
                    Gravity.BOTTOM,
                    this);
            loading.setVisibility(View.GONE);
            return;
        }

        if (!HelperMethods.validateOTP(this, otp, pinView)) {
            loading.setVisibility(View.GONE);
            return;
        }

        verifyPhoneNumber(otp);

    }

    private void verifyPhoneNumber(String otp) {

        String token = HelperMethods.getUserToken(this);
        Log.d(TAG, "verifyPhoneNumber: " + token);

        loading.setVisibility(View.VISIBLE);
        HelperMethods.getMawedAPI()
                .verifyPhoneNumber(token, otp,1)
                .enqueue(new Callback<VerifyOTP>() {
                    @Override
                    public void onResponse(Call<VerifyOTP> call, Response<VerifyOTP> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()){
                            if (response.body().isStatus()){
                                Log.d(TAG, "onResponse: " + response.body().getUser().getType());
                                if (response.body().getUser() != null){
                                    if (PreferencesManager.loadAppData(VerificationActivity.this, Const.KEY_USER_TYPE).equals("1")){
                                        startActivity(new Intent(VerificationActivity.this, HomeActivity.class));
                                        finish();
                                    }else {
                                        startActivity(new Intent(VerificationActivity.this, HomeSalonActivity.class));
                                        finish();
                                    }
                                }
                            }else {
                                Log.e(TAG, "onResponse: " + response.body().getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<VerifyOTP> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                        loading.setVisibility(View.GONE);
                    }
                });

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        ButterKnife.bind(this);
        initUI();

    }

    private void initUI() {
        startTimer();
        pinView.addTextChangedListener(new TextWatcherOTP());
    }

    private void startTimer() {

        long maxTimeInMilliseconds = 60000;
        int tick = 1000;
        countTimer = new CountDownTimer(maxTimeInMilliseconds, tick) {

            @Override
            public void onTick(long millisUntilFinished) {
                tvResendCode.setEnabled(false);
                long remainedSecs = millisUntilFinished / 1000;
                tvTimer.setText("0" + (remainedSecs / 60) + ":" + (remainedSecs % 60));
//                sendVerificationCode(phoneNumber);
            }

            @Override
            public void onFinish() {
                tvTimer.setText("00:00");
                cancel();
                tvResendCode.setEnabled(true);
            }
        }.start();

    }

    private void resendVerificationCode() {

        HelperMethods.getMawedAPI()
                .resendCode(HelperMethods.getUserToken(this))
                .enqueue(new Callback<ResendCode>() {
                    @Override
                    public void onResponse(Call<ResendCode> call, Response<ResendCode> response) {
                        if (response.isSuccessful()){
                            if (response.body().isStatus()){
                                Toast.makeText(VerificationActivity.this, "Resend code successfully", Toast.LENGTH_SHORT).show();
                            }else {
                                Log.e(TAG, "onResponse: " + response.body().getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResendCode> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    @Override
    public void onClick(View v) {
        HelperMethods.wifiSettings(this);
    }

    private class TextWatcherOTP implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String otp = pinView.getText().toString();
            if (!TextUtils.isEmpty(otp)) {
                if (otp.trim().length() == 4)
                    verifyPhoneNumber(otp);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
