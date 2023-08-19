package com.raiyansoft.mawed.ui.activities.auth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.raiyansoft.mawed.HomeActivity;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.model.auth.login.Auth;
import com.raiyansoft.mawed.ui.activities.settings.PrivacyPolicyActivity;
import com.raiyansoft.mawed.utils.Const;
import com.raiyansoft.mawed.utils.HelperMethods;
import com.raiyansoft.mawed.utils.PreferencesManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = LoginActivity.class.getSimpleName();

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rel_container)
    RelativeLayout container;
    @BindView(R.id.pb_loading)
    ProgressBar loading;

    @BindView(R.id.input_phone_number)
    EditText inputPhone;

    @BindView(R.id.checkbox_terms)
    CheckBox cbTerms;
    @BindView(R.id.tv_terms)
    TextView tvTerms;

    @OnClick(R.id.btn_login)
    void onLoginClick(){

        loading.setVisibility(View.VISIBLE);

        String phone = inputPhone.getText().toString();

        if (!HelperMethods.isInternetConnected(this)){
            HelperMethods.showCustomSnackBar(this,
                    container,
                    getString(R.string.check_internet),
                    false,
                    Snackbar.LENGTH_LONG,
                    Gravity.BOTTOM,
                    this);
            loading.setVisibility(View.GONE);
            return;
        }

        if (!HelperMethods.validatePhoneNumber(this, phone, inputPhone)){
            loading.setVisibility(View.GONE);
            return;
        }

        if (cbTerms.isChecked()){
            login(phone);
        }else {
            Toast.makeText(this, getString(R.string.please_agree_terms), Toast.LENGTH_SHORT).show();
            loading.setVisibility(View.GONE);
            return;
        }

    }

    @OnClick(R.id.tv_skip)
    void onSkipClick(){
        startActivity(new Intent(this, HomeActivity.class));
    }

    @OnClick(R.id.tv_create_account)
    void onCreateAccountClick() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @OnClick(R.id.tv_terms)
    void onTermsClick() {
        startActivity(new Intent(this, PrivacyPolicyActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initUI();

    }

    @SuppressLint("SetTextI18n")
    private void initUI() {

        loading.setVisibility(View.GONE);
        tvTerms.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        // TODO SALON
//        inputPhone.setText("1288888884");
        // TODO USER
//        inputPhone.setText("1288888885");
    }

    private void login(String phone) {

        HelperMethods.getMawedAPI()
                .login(phone)
                .enqueue(new Callback<Auth>() {
                    @Override
                    public void onResponse(Call<Auth> call, Response<Auth> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()){
                            if (response.body().isStatus()){
                                if (response.body().getUser() != null){
                                    Auth auth = response.body();
                                    Log.d(TAG, "onResponse AUTH: " + new Gson().toJson(auth.getUser()));
                                    Log.d(TAG, "onResponse AUTH: " + auth.getUser().getExpDate());
                                    Log.d(TAG, "onResponse: " + auth.getUser().getType());
                                    Log.d(TAG, "onResponse USER: " + new Gson().toJson(auth.getUser()));
                                    if (auth.getUser().getStatus() != null){
//                                        if (auth.getUser().getSalonStatus() == 1){
                                            if (auth.getUser().getType().equals("1")){
                                                PreferencesManager.saveUserToken(LoginActivity.this, Const.KEY_USER_TOKEN, auth.getUser().getToken());
                                                PreferencesManager.saveUserData(LoginActivity.this, Const.KEY_USER_DATA, auth.getUser());
                                                PreferencesManager.saveAppData(LoginActivity.this, Const.KEY_USER_TYPE, auth.getUser().getType());
                                                startActivity(new Intent(LoginActivity.this, VerificationActivity.class));
                                                finish();
                                            }else {
                                                PreferencesManager.saveUserToken(LoginActivity.this, Const.KEY_USER_TOKEN, auth.getUser().getToken());
                                                PreferencesManager.saveUserData(LoginActivity.this, Const.KEY_USER_DATA, auth.getUser());
                                                PreferencesManager.saveAppData(LoginActivity.this, Const.KEY_USER_TYPE, auth.getUser().getType());
                                                startActivity(new Intent(LoginActivity.this, VerificationActivity.class));
                                                finish();
                                            }
//                                        }else {
//                                            HelperMethods.showCustomToast(LoginActivity.this, getString(R.string.account_not_active), false);
//                                            PreferencesManager.saveUserToken(LoginActivity.this, Const.KEY_USER_TOKEN, auth.getUser().getToken());
//                                            startActivity(new Intent(LoginActivity.this, VerificationActivity.class));
//                                            finish();
//                                        }
                                    }
                                }
                            }else {
                                Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Auth> call, Throwable t) {
                        Log.e(TAG, "onFailure: " +  t.getMessage());
                        loading.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        HelperMethods.wifiSettings(this);
    }
}
