package com.raiyansoft.mawed.ui.activities.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.raiyansoft.mawed.HomeActivity;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.model.auth.register.Register;
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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = RegisterActivity.class.getSimpleName();

    @BindView(R.id.rel_container)
    RelativeLayout container;
    @BindView(R.id.pb_loading)
    ProgressBar loading;
    @BindView(R.id.input_first_name)
    EditText inputFirstName;
    @BindView(R.id.input_last_name)
    EditText inputLastName;
    @BindView(R.id.input_phone_number)
    EditText inputPhoneNumber;
    @BindView(R.id.tv_date_of_birth)
    TextView tvDateOfBirth;
    @BindView(R.id.tv_mail)
    TextView tvMale;
    @BindView(R.id.tv_femail)
    TextView tvFemale;
    @BindView(R.id.rb_mail)
    RadioButton rbMail;
    @BindView(R.id.rb_femail)
    RadioButton rbFemail;

    @BindView(R.id.checkbox_terms)
    CheckBox cbTerms;

    // TODO Male = 1 / Female = 2
    private int gender = 1;

    @OnClick(R.id.tv_date_of_birth)
    void OnGetDateOfBirthClick(){
        HelperMethods.showDateDialog(this, "yyyy-MM-dd", tvDateOfBirth);
    }

    @OnClick(R.id.rel_mail)
    void onMailClick(){
        if (rbMail.isChecked()) {
            rbMail.setChecked(false);
            rbFemail.setChecked(true);
        } else {
            rbMail.setChecked(true);
            rbFemail.setChecked(false);
        }

        gender = 1;
    }

    @OnClick(R.id.rel_femail)
    void onFemailClick(){
        if (rbFemail.isChecked()) {
            rbFemail.setChecked(false);
            rbMail.setChecked(true);
        } else {
            rbFemail.setChecked(true);
            rbMail.setChecked(false);
        }

        gender = 2;
    }

    @OnClick(R.id.tv_skip)
    void onSkipClick(){
        startActivity(new Intent(this, HomeActivity.class));
    }

    @OnClick(R.id.tv_login)
    void onLoginClick(){
        startActivity(new Intent(this, LoginActivity.class));
    }

    @OnClick(R.id.btn_register)
    void onRegisterClick(){

        loading.setVisibility(View.VISIBLE);

        String firstName = inputFirstName.getText().toString();
        String lastName = inputLastName.getText().toString();
        String phone = inputPhoneNumber.getText().toString();
        String dateOfBirth = tvDateOfBirth.getText().toString();

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

        if (!HelperMethods.validateFirstName(this, firstName, inputFirstName) |
        !HelperMethods.validateLastName(this, lastName, inputLastName) |
        !HelperMethods.validatePhoneNumber(this, phone, inputPhoneNumber)){
            loading.setVisibility(View.GONE);
            return;
        }

        if (dateOfBirth.isEmpty()){
            Toast.makeText(this, getString(R.string.choose_your_date_of_birth), Toast.LENGTH_SHORT).show();
            loading.setVisibility(View.GONE);
            return;
        }

        if (cbTerms.isChecked()){
            register(firstName, lastName, phone, dateOfBirth, gender);
        }else {
            Toast.makeText(this, getString(R.string.please_agree_terms), Toast.LENGTH_SHORT).show();
            loading.setVisibility(View.GONE);
            return;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        loading.setVisibility(View.GONE);

//        inputFirstName.setText("Ahmed");
//        inputLastName.setText("Wasfe");
//        inputPhoneNumber.setText("1288888888");
    }

    private void register(String firstName, String lastName, String phone, String dateOfBirth, int gender) {

        String fcmToken = PreferencesManager.loadUserToken(this, Const.FIREBASE_TOKEN);
        Log.d(TAG, "register: " + firstName);
        Log.d(TAG, "register: " + lastName);
        Log.d(TAG, "register: " + phone);
        Log.d(TAG, "register: " + dateOfBirth);
        Log.d(TAG, "register: " + gender);
        Log.d(TAG, "register: " + fcmToken);



        HelperMethods.getMawedAPI()
                .register(HelperMethods.getAppLanguage(this),
                        firstName, lastName, phone,
                        "android", fcmToken,
                        dateOfBirth, gender)
                .enqueue(new Callback<Register>() {
                    @Override
                    public void onResponse(Call<Register> call, Response<Register> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()){
                            if (response.body().isStatus()){
                                if (response.body().getUser() != null){
                                    Register auth = response.body();
                                    if (auth.isStatus()){
                                        PreferencesManager.saveUserToken(RegisterActivity.this, Const.KEY_USER_TOKEN, auth.getUser().getToken());
                                        startActivity(new Intent(RegisterActivity.this, VerificationActivity.class));
                                        finish();
                                    }else {
                                        Toast.makeText(RegisterActivity.this, auth.getMessage(), Toast.LENGTH_LONG).show();
                                        Log.e(TAG, "onResponse: " + new Gson().toJson(auth));
                                    }
                                }
                            }else {
                                Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Register> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                        loading.setVisibility(View.GONE);
                    }
                });

    }

    @Override
    public void onClick(View v) {
        HelperMethods.wifiSettings(this);
    }
}
