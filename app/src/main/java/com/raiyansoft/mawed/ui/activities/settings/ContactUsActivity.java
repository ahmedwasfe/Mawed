package com.raiyansoft.mawed.ui.activities.settings;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.model.settings.ContactUs;
import com.raiyansoft.mawed.utils.HelperMethods;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsActivity extends AppCompatActivity {
    private static final String TAG = ContactUsActivity.class.getSimpleName();

    @BindView(R.id.pb_loading)
    ProgressBar loading;

    @BindView(R.id.input_message)
    EditText inputMessage;
    @BindView(R.id.input_subject)
    EditText inputSubject;

    @OnClick(R.id.btn_send_request)
    void onSendRequest() {

        String message = inputMessage.getText().toString();
        String subject = inputSubject.getText().toString();
        String name = HelperMethods.getCurrentUser(this).getFirstName() + " " + HelperMethods.getCurrentUser(this).getLastName();
        Log.d(TAG, "onSendRequest: " + HelperMethods.getCurrentUser(this).getMobile());
        Log.d(TAG, "onSendRequest: " + HelperMethods.getCurrentUser(this).getFirstName() + " " + HelperMethods.getCurrentUser(this).getLastName());

        contactUs(message, subject, name, "");

    }

    @OnClick(R.id.iv_back)
    void onBackClick() {
        onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        ButterKnife.bind(this);
    }

    private void contactUs(String message, String subject, String name, String email) {

        loading.setVisibility(View.VISIBLE);
        HelperMethods.getMawedAPI()
                .contactUs(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this),
                        message, subject, name, email)
                .enqueue(new Callback<ContactUs>() {
                    @Override
                    public void onResponse(Call<ContactUs> call, Response<ContactUs> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()){
                            if (response.body().isStatus()){
                                Toast.makeText(ContactUsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }else {
                                Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ContactUs> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                        loading.setVisibility(View.GONE);
                    }
                });
    }
}
