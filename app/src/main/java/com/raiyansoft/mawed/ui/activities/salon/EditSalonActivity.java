package com.raiyansoft.mawed.ui.activities.salon;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.adapter.SalonProfileImagesAdapter;
import com.raiyansoft.mawed.model.ImageData;
import com.raiyansoft.mawed.model.profile.ProfileData;
import com.raiyansoft.mawed.model.profile.updateProfile.UpdateProfile;
import com.raiyansoft.mawed.utils.Const;
import com.raiyansoft.mawed.utils.HelperMethods;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditSalonActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = EditSalonActivity.class.getSimpleName();

    @BindView(R.id.pb_loading)
    ProgressBar loading;
    @BindView(R.id.rel_container)
    RelativeLayout container;
    @BindView(R.id.sw_home)
    SwitchCompat swOnOff;
    @BindView(R.id.civ_user_image)
    CircleImageView civUserImage;
    @BindView(R.id.input_first_name)
    EditText inputFirstName;
    @BindView(R.id.input_last_name)
    EditText inputLastName;
    @BindView(R.id.input_commercail_name_ar)
    EditText inputCommercailNameAr;
    @BindView(R.id.input_commercail_name_en)
    EditText inputCommercailNameEn;
    @BindView(R.id.input_phone_number)
    EditText inputPhone;
    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.tv_date_of_birth)
    TextView tvDateOfBirth;
    @BindView(R.id.input_description_name_ar)
    EditText inputDescriptionAr;
    @BindView(R.id.input_description_name_en)
    EditText inputDescriptionEn;

    @BindView(R.id.recycler_images)
    RecyclerView recyclerImages;

    private ProfileData profile;

    private SalonProfileImagesAdapter profileImagesAdapter;
    private List<ImageData> listImages;

    private int home = 2;

    @OnClick(R.id.card_select_image)
    void onSelectImageClick() {
        HelperMethods.selectMultipleImage(this, false, listImages, profileImagesAdapter);
        HelperMethods.listImagesUrls.clear();
        recyclerImages.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tv_date_of_birth)
    void OnGetDateOfBirthClick() {
        HelperMethods.showDateDialog(this, "yyyy-MM-dd", tvDateOfBirth);
    }

    @OnClick(R.id.btn_save)
    void onUpdateProfileClick() {
        loading.setVisibility(View.VISIBLE);

        String firstName = inputFirstName.getText().toString();
        String lastName = inputLastName.getText().toString();
        String phone = inputPhone.getText().toString();
        String email = inputEmail.getText().toString();
        String dateOfBirth = tvDateOfBirth.getText().toString();
        String commercailNameAr = inputCommercailNameAr.getText().toString();
        String commercailNameEn = inputCommercailNameEn.getText().toString();
        String descriptionAn = inputDescriptionAr.getText().toString();
        String descriptionEn = inputDescriptionEn.getText().toString();

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

        if (listImages.isEmpty()) {
            Toast.makeText(this, getString(R.string.select_image), Toast.LENGTH_SHORT).show();
            loading.setVisibility(View.GONE);
            return;
        }

        if (!HelperMethods.validateFirstName(this, firstName, inputFirstName) |
                !HelperMethods.validateLastName(this, lastName, inputLastName) |
                !HelperMethods.validateCommercialName(this, commercailNameAr, inputCommercailNameAr) |
                !HelperMethods.validateCommercialName(this, commercailNameEn, inputCommercailNameEn) |
                !HelperMethods.validatePhoneNumber(this, phone, inputPhone) |
                !HelperMethods.validateEmail(this, email, inputEmail)) {
            loading.setVisibility(View.GONE);
            return;
        }

        if (dateOfBirth.isEmpty()) {
            Toast.makeText(this, getString(R.string.choose_your_date_of_birth), Toast.LENGTH_SHORT).show();
            loading.setVisibility(View.GONE);
            return;
        }

        updateProfile(firstName, lastName, phone, email, dateOfBirth, commercailNameAr, commercailNameEn, descriptionAn, descriptionEn);

    }

    @OnClick(R.id.iv_back)
    void onBackClick() {
        onBackPressed();
    }

    private void updateProfile(String firstName, String lastName, String phone, String email, String dateOfBirth, String commercailNameAr, String commercailNameEn, String descriptionAr, String descriptionEn) {

        loading.setVisibility(View.VISIBLE);

        List<MultipartBody.Part> images = new ArrayList<>();
        for (int i = 0; i < listImages.size(); i++)
            images.add(HelperMethods.convertFileToMultiPart(listImages.get(i).getImage(), "image[]"));

        RequestBody bodyFirstName = HelperMethods.convertToRequestBody(firstName);
        RequestBody bodyLastName = HelperMethods.convertToRequestBody(lastName);
        RequestBody bodyPhone = HelperMethods.convertToRequestBody(phone);
        RequestBody bodyEmail = HelperMethods.convertToRequestBody(email);
        RequestBody bodyDateOfBirth = HelperMethods.convertToRequestBody(dateOfBirth);
        RequestBody bodyCommercailNameAr = HelperMethods.convertToRequestBody(commercailNameAr);
        RequestBody bodyCommercailNameEn = HelperMethods.convertToRequestBody(commercailNameEn);
        RequestBody bodyDescriptionAr = HelperMethods.convertToRequestBody(descriptionAr);
        RequestBody bodyDescriptionEn = HelperMethods.convertToRequestBody(descriptionEn);
        RequestBody bodyHome = HelperMethods.convertToRequestBody(String.valueOf(home));

        HelperMethods.getMawedAPI()
                .updateSalonProfile(HelperMethods.getAppLanguage(this),
        HelperMethods.getUserToken(this),
                        images, bodyFirstName, bodyLastName, bodyEmail,
                        bodyCommercailNameAr, bodyCommercailNameEn,
                        bodyPhone, bodyDateOfBirth, bodyDescriptionAr, bodyDescriptionEn, bodyHome)
                .enqueue(new Callback<UpdateProfile>() {
                    @Override
                    public void onResponse(Call<UpdateProfile> call, Response<UpdateProfile> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()){
                            if (response.body().isStatus()){
                                if (response.body().getProfileData() != null){
                                    HelperMethods.showCustomToast(EditSalonActivity.this, response.body().getMessage(), true);
                                    finish();
                                }
                            }
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_salon);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {

       if (getIntent() != null){
           profile = (ProfileData) getIntent().getSerializableExtra(Const.KEY_PROFILE);
       }

        listImages = new ArrayList<>();

       if (listImages.size() == 0)
           recyclerImages.setVisibility(View.GONE);
       else
           recyclerImages.setVisibility(View.VISIBLE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerImages.setHasFixedSize(true);
        recyclerImages.setLayoutManager(layoutManager);

        swOnOff.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                home = 1;
            else
                home = 2;
        });

        profileImagesAdapter = new SalonProfileImagesAdapter(this, listImages);
        recyclerImages.setAdapter(profileImagesAdapter);

        if (profile != null){

            if (profile.getAvatar()!= null)
                Glide.with(this).load(profile.getAvatar()).into(civUserImage);

            if (profile.getHome() == 1)
                swOnOff.setChecked(true);
            else
                swOnOff.setChecked(false);

            inputFirstName.setText(profile.getFirstName());
            inputLastName.setText(profile.getLastName());
            tvDateOfBirth.setText(profile.getDateOfBirth());
            inputPhone.setText(profile.getMobile());

            if (profile.getEmail() != null)
                inputEmail.setText(profile.getEmail());

            if (profile.getCommercailNameAr() != null)
                inputCommercailNameAr.setText(profile.getCommercailNameAr());

            if (profile.getCommercailNameEn() != null)
                inputCommercailNameEn.setText(profile.getCommercailNameEn());

            if (profile.getDescriptionAr() != null)
                inputDescriptionAr.setText(profile.getDescriptionAr());

            if (profile.getDescriptionEn() != null)
                inputDescriptionEn.setText(profile.getDescriptionEn());
        }

    }

    @Override
    public void onClick(View v) {
        HelperMethods.wifiSettings(this);
    }
}
