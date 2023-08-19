package com.raiyansoft.mawed.ui.activities.settings;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.model.auth.User;
import com.raiyansoft.mawed.model.profile.Profile;
import com.raiyansoft.mawed.model.profile.ProfileData;
import com.raiyansoft.mawed.model.profile.updateProfile.UpdateProfile;
import com.raiyansoft.mawed.model.profile.updateProfile.UpdateProfileData;
import com.raiyansoft.mawed.utils.Const;
import com.raiyansoft.mawed.utils.HelperMethods;
import com.raiyansoft.mawed.utils.PreferencesManager;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = EditProfileActivity.class.getSimpleName();

    @BindView(R.id.pb_loading)
    ProgressBar loading;
    @BindView(R.id.rel_container)
    RelativeLayout container;
    @BindView(R.id.civ_user_image)
    CircleImageView civUserImage;
    @BindView(R.id.input_first_name)
    EditText inputFirstName;
    @BindView(R.id.input_last_name)
    EditText inputLastName;
    @BindView(R.id.input_phone_number)
    EditText inputPhone;
    @BindView(R.id.input_email)
    EditText inputEmail;
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

    @OnClick(R.id.iv_back)
    void onBackClick() {
        onBackPressed();
    }

    @OnClick(R.id.card_select_image)
    void onSelectImageClick() {
        HelperMethods.openSingleGallery(this, civUserImage);
    }

    // TODO Male = 1 / Female = 2
    private int gender = 1;

    @OnClick(R.id.tv_date_of_birth)
    void OnGetDateOfBirthClick() {
        HelperMethods.showDateDialog(this, "yyyy-MM-dd", tvDateOfBirth);
    }

    @OnClick(R.id.rel_mail)
    void onMailClick() {
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
    void onFemailClick() {
        if (rbFemail.isChecked()) {
            rbFemail.setChecked(false);
            rbMail.setChecked(true);
        } else {
            rbFemail.setChecked(true);
            rbMail.setChecked(false);
        }

        gender = 2;
    }

    @OnClick(R.id.btn_save)
    void onEditProfileClick() {

        loading.setVisibility(View.VISIBLE);

        String firstName = inputFirstName.getText().toString();
        String lastName = inputLastName.getText().toString();
        String phone = inputPhone.getText().toString();
        String email = inputEmail.getText().toString();
        String dateOfBirth = tvDateOfBirth.getText().toString();

        if (!HelperMethods.isInternetConnected(this)) {
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
                !HelperMethods.validatePhoneNumber(this, phone, inputPhone)) {
            loading.setVisibility(View.GONE);
            return;
        }

        if (dateOfBirth.isEmpty()) {
            Toast.makeText(this, getString(R.string.choose_your_date_of_birth), Toast.LENGTH_SHORT).show();
            loading.setVisibility(View.GONE);
            return;
        }

        updateProfile(firstName, lastName, phone, email, dateOfBirth);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        loadUserData();

       /* if (HelperMethods.getCurrentUser(this) != null) {
            User profile = HelperMethods.getCurrentUser(this);
            if (profile.getAvatar() != null) {
                if (profile.getAvatar().equals(Const.tmpUserImage))
                    Glide.with(this).load(Const.defaultUserImage).into(civUserImage);
                else
                    Glide.with(this).load(profile.getAvatar()).into(civUserImage);
            } else
                Glide.with(this).load(Const.defaultUserImage).into(civUserImage);


            if (HelperMethods.getCurrentUser(this).getFirstName() != null && HelperMethods.getCurrentUser(this).getLastName() != null) {
                inputFirstName.setText(profile.getFirstName());
                inputLastName.setText(profile.getLastName());
            }

            if (profile.getMobile() != null)
                inputPhone.setText(profile.getMobile());

//            if (profile.getEmail() != null)
//                tvEmail.setText(profile.getEmail());

//            if (profile.getDateOfBirth() != null)
//                tvDateOfBirth.setText(profile.getDateOfBirth());

//            if (profile.getSex() != 0) {
//                String gender;
//                if (profile.getSex() == 1)
//                    gender = getString(R.string.mail);
//                else if (profile.getSex() == 2)
//                    gender = getString(R.string.female);
//                else
//                    gender = "";
//                tvGender.setText(gender);
//            }
        }*/
    }

    private void loadUserData() {

        loading.setVisibility(View.VISIBLE);
        HelperMethods.getMawedAPI()
                .getProfile(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this))
                .enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getData() != null) {
                                    getUserData(response.body().getData());
                                }
                            } else {
                                Log.e(TAG, "onResponse: " + response.body().getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                        loading.setVisibility(View.GONE);
                    }
                });
    }

    private void getUserData(ProfileData profile) {

        if (profile != null) {
            Log.d(TAG, "getSalonData: " + profile.getAvatar());
            if (profile.getAvatar() != null)
                Glide.with(this).load(profile.getAvatar()).into(civUserImage);
            inputFirstName.setText(profile.getFirstName());
            inputLastName.setText(profile.getLastName());
//            if (profile.getSalonName() == null  || profile.getSalonName().equals("")) {
//                inputFirstName.setText(profile.getFirstName());
//                inputLastName.setText(profile.getLastName());
//            } else {
//                inputFirstName.setText(profile.getSalonName());
//                inputLastName.setText(profile.getSalonName());
//            }

            inputPhone.setText(profile.getMobile());

            if (profile.getEmail() != null)
                inputEmail.setText(profile.getEmail());

            if (profile.getDateOfBirth() != null)
                tvDateOfBirth.setText(profile.getDateOfBirth());

            if(HelperMethods.getCurrentUser(this) != null){
                if (HelperMethods.getCurrentUser(this).getGender() != null){
                    if(HelperMethods.getCurrentUser(this).getGender() == 1){
                        rbMail.setChecked(true);
                        rbFemail.setChecked(false);
                    }else if(HelperMethods.getCurrentUser(this).getGender() == 2){
                        rbMail.setChecked(false);
                        rbFemail.setChecked(true);
                    }else {
                        rbMail.setChecked(true);
                        rbFemail.setChecked(false);
                    }
                }
            }
        }
    }

    private void updateProfile(String firstName, String lastName, String phone, String email, String dateOfBirth) {

        loading.setVisibility(View.VISIBLE);

        MultipartBody.Part image = HelperMethods.convertFileToMultiPart(HelperMethods.getImageUrl(), "image[]");
        RequestBody firstNameBody = HelperMethods.convertToRequestBody(firstName);
        RequestBody lastNameBody = HelperMethods.convertToRequestBody(lastName);
        RequestBody phoneBody = HelperMethods.convertToRequestBody(phone);
        RequestBody emailBody = HelperMethods.convertToRequestBody(email);
        RequestBody dateOfBirthBody = HelperMethods.convertToRequestBody(dateOfBirth);
        RequestBody genderBody = HelperMethods.convertToRequestBody(String.valueOf(gender));

        HelperMethods.getMawedAPI()
                .updateUserProfile(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this),
                        image, firstNameBody, lastNameBody, emailBody,
                        phoneBody, dateOfBirthBody, genderBody)
                .enqueue(new Callback<UpdateProfile>() {
                    @Override
                    public void onResponse(Call<UpdateProfile> call, Response<UpdateProfile> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getProfileData() != null) {
                                    UpdateProfileData profile = response.body().getProfileData();
                                    Log.d(TAG, "onResponse: " + profile.getAvatar());
                                    User currentUser = HelperMethods.getCurrentUser(EditProfileActivity.this);
                                    User user = new User();
                                    user.setUserId(profile.getUser_id());
                                    user.setAvatar(profile.getAvatar());
                                    user.setMobile(profile.getMobile());
                                    user.setFirstName(profile.getFirstName());
                                    user.setLastName(profile.getLastName());
                                    user.setGender(gender);
                                    user.setType(currentUser.getType());
                                    user.setAddress(currentUser.getAddress());
                                    user.setDeviceToken(currentUser.getDeviceToken());
                                    user.setExpDate(currentUser.getExpDate());
                                    user.setFee(currentUser.getFee());
                                    user.setSalonStatus(currentUser.getSalonStatus());
                                    user.setStatus(currentUser.getStatus());
                                    user.setToken(currentUser.getToken());
//                                    user.setGender(profile);
                                    PreferencesManager.saveUserData(EditProfileActivity.this, Const.KEY_USER_DATA, user);
                                    finish();
                                }
                            } else
                                Log.e(TAG, "onResponse: " + response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateProfile> call, Throwable t) {
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
