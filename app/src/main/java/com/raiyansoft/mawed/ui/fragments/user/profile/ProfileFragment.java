package com.raiyansoft.mawed.ui.fragments.user.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.model.auth.Logout;
import com.raiyansoft.mawed.model.profile.Profile;
import com.raiyansoft.mawed.model.profile.ProfileData;
import com.raiyansoft.mawed.ui.activities.auth.LoginActivity;
import com.raiyansoft.mawed.ui.activities.settings.SettingsActivity;
import com.raiyansoft.mawed.utils.Const;
import com.raiyansoft.mawed.utils.HelperMethods;
import com.raiyansoft.mawed.utils.PreferencesManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private static final String TAG = ProfileFragment.class.getSimpleName();

    @BindView(R.id.layout_login)
    CardView cardLogin;
    @BindView(R.id.tv_text_logout)
    TextView tvTextLogout;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.scroll_profile)
    NestedScrollView scrollProfile;
    @BindView(R.id.pb_loading)
    ProgressBar loading;
    @BindView(R.id.civ_user_image)
    CircleImageView civUserImage;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_date_of_birth)
    TextView tvDateOfBirth;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.tv_logout)
    TextView tvLogout;

    private static ProfileFragment instance;

    public static ProfileFragment getInstance() {
        return instance == null ? new ProfileFragment() : instance;
    }

    @OnClick(R.id.iv_settings)
    void onSettingsClick() {
        if (HelperMethods.getUserType(getActivity()) != null)
            startActivity(new Intent(getActivity(), SettingsActivity.class));
        else
            showLogoutDialog();
    }

    @OnClick(R.id.tv_logout)
    void onLogoutClick() {
        showLogoutDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, layoutView);
        initUI();
        loadUserData();
        return layoutView;
    }

    private void initUI() {
        showLogin(getActivity(), scrollProfile, cardLogin, tvLogout, tvTextLogout, btnLogout, btnCancel);
    }

    private void showLogin(Activity activity,
                                 NestedScrollView scrollProfile, CardView cardLogin,
                                 TextView tvLogout, TextView tvTextLogout, Button btnLogout, Button btnCancel) {
        btnCancel.setVisibility(View.GONE);
        if (HelperMethods.getCurrentUser(activity) != null){
            scrollProfile.setVisibility(View.VISIBLE);
            cardLogin.setVisibility(View.GONE);

            tvLogout.setText(activity.getString(R.string.logout));

            tvTextLogout.setText(activity.getString(R.string.sure_log_out));
            btnLogout.setText(activity.getString(R.string.logout));

        }else{
            scrollProfile.setVisibility(View.GONE);
            cardLogin.setVisibility(View.VISIBLE);

            tvLogout.setText(activity.getString(R.string.log_in));

            tvTextLogout.setText(activity.getString(R.string.please_login));
            btnLogout.setText(activity.getString(R.string.log_in));

            btnLogout.setOnClickListener(v -> {
                activity.startActivity(new Intent(activity, LoginActivity.class));
                activity.finish();
            });
        }
    }

    private void loadUserData() {

        loading.setVisibility(View.VISIBLE);
        if (HelperMethods.getCurrentUser(getActivity()) != null) {
            HelperMethods.getMawedAPI()
                    .getProfile(HelperMethods.getAppLanguage(getActivity()),
                            HelperMethods.getUserToken(getActivity()))
                    .enqueue(new Callback<Profile>() {
                        @Override
                        public void onResponse(Call<Profile> call, Response<Profile> response) {
                            loading.setVisibility(View.GONE);
                            if (response.isSuccessful()) {
                                if (response.body().isStatus()) {
                                    if (response.body().getData() != null) {
                                        getProfileData(response.body().getData());
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

        }else {
            loading.setVisibility(View.GONE);
        }
    }

    private void getProfileData(ProfileData profile) {

        if (profile != null) {
            if (profile.getAvatar() != null) {
                Log.d(TAG, "getProfileData: " + profile.getAvatar());
                if (profile.getAvatar().equals(Const.tmpUserImage))
                    Glide.with(getActivity()).load(Const.defaultUserImage).into(civUserImage);
                else
                    Glide.with(getActivity()).load(profile.getAvatar()).into(civUserImage);
            } else
                Glide.with(getActivity()).load(Const.defaultUserImage).into(civUserImage);


            if (profile.getFirstName() != null && profile.getLastName() != null) {
                String name = new StringBuilder()
                        .append(profile.getFirstName())
                        .append(" ")
                        .append(profile.getLastName())
                        .toString();
                tvName.setText(name);
            }

            if (profile.getMobile() != null)
                tvPhone.setText(profile.getMobile());

            if (profile.getEmail() != null)
                tvEmail.setText(profile.getEmail());

            if (profile.getDateOfBirth() != null)
                tvDateOfBirth.setText(profile.getDateOfBirth());

            if (profile.getSex() != 0) {
                String gender;
                if (profile.getSex() == 1)
                    gender = getString(R.string.mail);
                else if (profile.getSex() == 2)
                    gender = getString(R.string.female);
                else
                    gender = "";
                tvGender.setText(gender);
            }
        }
    }

    private void showLogoutDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_logout, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        HelperMethods.showCustomDialog(dialog);

        CardView card = dialogView.findViewById(R.id.card_dialog);
        card.setBackgroundColor(Color.TRANSPARENT);

        TextView tvTextLogout = dialogView.findViewById(R.id.tv_text_logout);
        Button btnLogout = dialogView.findViewById(R.id.btn_logout);

        if (HelperMethods.getCurrentUser(getActivity()) != null){
            tvTextLogout.setText(getString(R.string.sure_log_out));
            btnLogout.setText(getString(R.string.logout));
        }else {
            tvTextLogout.setText(getString(R.string.please_login));
            btnLogout.setText(getString(R.string.log_in));
        }


        dialogView.findViewById(R.id.btn_cancel)
                .setOnClickListener(view -> dialog.dismiss());

        dialogView.findViewById(R.id.btn_logout)
                .setOnClickListener(v -> {
                    dialog.dismiss();
                    if (HelperMethods.getUserToken(getActivity()) != null) {
                        logout();
                    } else {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    }
                });

        dialog.show();
    }

    private void logout() {
        HelperMethods.getMawedAPI()
                .logout(HelperMethods.getAppLanguage(getActivity()),
                        HelperMethods.getUserToken(getActivity()))
                .enqueue(new Callback<Logout>() {
                    @Override
                    public void onResponse(Call<Logout> call, Response<Logout> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                PreferencesManager.clear(getActivity(), Const.KEY_USER_TOKEN);
                                PreferencesManager.clear(getActivity(), Const.KEY_USER_DATA);
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                getActivity().finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Logout> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    @Override
    public void onResume() {
        loadUserData();
        super.onResume();
    }
}