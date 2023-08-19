package com.raiyansoft.mawed.ui.activities.settings;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.model.auth.changePhone.ChangePhone;
import com.raiyansoft.mawed.utils.HelperMethods;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePhoneNumberActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = ChangePhoneNumberActivity.class.getSimpleName();

    @BindView(R.id.rel_container)
    RelativeLayout relContainer;

    @BindView(R.id.pb_loading)
    ProgressBar loading;

    @BindView(R.id.input_old_phone_number)
    EditText inputOldPhone;

    @BindView(R.id.input_new_phone_number)
    EditText inputNewPhone;

    @OnClick(R.id.btn_send_request)
    void onChangePhoneClick() {

        loading.setVisibility(View.VISIBLE);

        String oldPhone = inputOldPhone.getText().toString();
        String newPhone = inputNewPhone.getText().toString();

        if (!HelperMethods.isInternetConnected(this)){
            HelperMethods.showCustomSnackBar(this, relContainer,
                    getString(R.string.check_internet),
                    false, Snackbar.LENGTH_SHORT,
                    Gravity.BOTTOM,
                    this);
            loading.setVisibility(View.GONE);
            return;
        }

        if (!HelperMethods.validateOldPhoneNumber(this, oldPhone, inputOldPhone) |
        !HelperMethods.validateNewPhoneNumber(this, newPhone, inputNewPhone)){
            loading.setVisibility(View.GONE);
            return;
        }

        changePhoneNumber(oldPhone, newPhone);
    }

    @OnClick(R.id.btn_cancel)
    void onCancelClick() {
        finish();
    }

    @OnClick(R.id.iv_back)
    void onBackCLick() {
        onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone_number);
        ButterKnife.bind(this);
    }

    private void changePhoneNumber(String oldPhone, String newPhone) {

        loading.setVisibility(View.VISIBLE);
        HelperMethods.getMawedAPI()
                .changePhone(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this),
                        oldPhone, newPhone)
                .enqueue(new Callback<ChangePhone>() {
                    @Override
                    public void onResponse(Call<ChangePhone> call, Response<ChangePhone> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()){
                            if (response.body().isStatus()){
                                if (response.body().getData() != null){
                                    Toast.makeText(ChangePhoneNumberActivity.this, getString(R.string.change_phone_number_successfully), Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ChangePhone> call, Throwable t) {
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
