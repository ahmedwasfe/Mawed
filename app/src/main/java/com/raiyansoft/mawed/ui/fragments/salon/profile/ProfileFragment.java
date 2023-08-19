package com.raiyansoft.mawed.ui.fragments.salon.profile;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.raiyansoft.mawed.HomeActivity;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.model.auth.Logout;
import com.raiyansoft.mawed.model.profile.Profile;
import com.raiyansoft.mawed.model.profile.ProfileData;
import com.raiyansoft.mawed.ui.activities.auth.LoginActivity;
import com.raiyansoft.mawed.ui.activities.salon.EditSalonActivity;
import com.raiyansoft.mawed.ui.activities.salon.SalesActivity;
import com.raiyansoft.mawed.ui.activities.salon.SalonServicesActivity;
import com.raiyansoft.mawed.ui.activities.salon.WorkHouresActivity;
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

    @BindView(R.id.pb_loading)
    ProgressBar loading;
    @BindView(R.id.civ_salon_image)
    CircleImageView civSalonImage;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;

    @BindView(R.id.rel_email)
    RelativeLayout relEmail;
    @BindView(R.id.tv_email)
    TextView tvEmail;

    @BindView(R.id.rel_date_of_birth)
    RelativeLayout relDateOfBirth;
    @BindView(R.id.tv_date_of_birth)
    TextView tvDateOfBirth;

    @BindView(R.id.rel_my_services)
    RelativeLayout relServices;
    @BindView(R.id.tv_text_my_services)
    TextView tvServices;

    @BindView(R.id.rel_sales)
    RelativeLayout relSales;

    @BindView(R.id.rel_account_details)
    RelativeLayout relAccountDetails;

    @BindView(R.id.rel_socialist)
    RelativeLayout relSocialist;

    @BindView(R.id.tv_text_contact_us)
    TextView tvTextContactUs;
    @BindView(R.id.ll_contact_us)
    LinearLayout llContactUs;

    @BindView(R.id.tv_socialist)
    TextView tvSocialist;

    private ProfileData profileData;


    @OnClick(R.id.rel_work_hours)
    void onWorkHouresClick() {
        startActivity(new Intent(getActivity(), WorkHouresActivity.class));
    }

    @OnClick(R.id.rel_my_services)
    void onServicesClick() {
        startActivity(new Intent(getActivity(), SalonServicesActivity.class));
    }

    @OnClick(R.id.rel_sales)
    void onSalesClick() {
        startActivity(new Intent(getActivity(), SalesActivity.class));
    }

    @OnClick(R.id.tv_logout)
    void onLogoutClick() {
        showLogoutDialog();
    }

    @OnClick(R.id.iv_edit)
    void onEditSalonClick() {
        startActivity(new Intent(getActivity(), EditSalonActivity.class)
                .putExtra(Const.KEY_PROFILE, profileData));
    }

    @OnClick(R.id.btn_back_my_account)
    void onBackToMyAccountClick() {
        Log.d(TAG, "onCallClick: " + profileData.getMobile());
        PreferencesManager.saveAppData(getActivity(), Const.KEY_USER_TYPE, "1");
        startActivity(new Intent(getActivity(), HomeActivity.class));
        getActivity().finish();
    }

    @OnClick(R.id.tv_call)
    void onCallClick() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + profileData.getMobile()));
            startActivity(intent);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
        }

    }

    @OnClick(R.id.tv_whatsapp)
    void onWhatsappClick() {
        Log.d(TAG, "onCallClick: " + profileData.getMobile());
        openWhatsApp(profileData.getMobile());
    }

    private void openWhatsApp(String smsNumber) {
        boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
        if (isWhatsappInstalled) {

            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(smsNumber) + "@s.whatsapp.net");//phone number without "+" prefix

            startActivity(sendIntent);
        } else {
            Uri uri = Uri.parse("market://details?id=com.whatsapp");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            Toast.makeText(getActivity(), "WhatsApp not Installed",
                    Toast.LENGTH_SHORT).show();
            try {
                startActivity(goToMarket);
            } catch (android.content.ActivityNotFoundException ex) {
                Log.e(TAG, "openWhatsApp: " + ex.getMessage());
            }

        }
    }

    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = getActivity().getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_salon_profile, container, false);
        ButterKnife.bind(this, layoutView);
        initUI();
        loadSalonData();
        return layoutView;
    }

    private void initUI() {

        // TODO 1 زائر
        // TODO 2 صالون
        // TODO 3 موظف

        Log.d(TAG, "initUI: " + HelperMethods.getUserType(getActivity()));
        if (HelperMethods.getUserType(getActivity()).equals("2")) {
            // TODO SALON
            tvServices.setText(getString(R.string.list_services));
            relEmail.setVisibility(View.VISIBLE);
            relDateOfBirth.setVisibility(View.GONE);
            relSales.setVisibility(View.VISIBLE);
            relAccountDetails.setVisibility(View.VISIBLE);
            relSocialist.setVisibility(View.VISIBLE);
            tvTextContactUs.setVisibility(View.VISIBLE);
            llContactUs.setVisibility(View.VISIBLE);
        } else {
            // TODO EMPLOYEE
            tvServices.setText(getString(R.string.my_services));
            relEmail.setVisibility(View.GONE);
            relDateOfBirth.setVisibility(View.VISIBLE);
            relSales.setVisibility(View.GONE);
            relAccountDetails.setVisibility(View.GONE);
            relSocialist.setVisibility(View.GONE);
            tvTextContactUs.setVisibility(View.GONE);
            llContactUs.setVisibility(View.GONE);
        }
    }

    private void loadSalonData() {

        loading.setVisibility(View.VISIBLE);

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
                                    getSalonData(response.body().getData());
                                }
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

    private void getSalonData(ProfileData profile) {

        if (profile != null) {
            profileData = profile;
            Log.d(TAG, "getSalonData: " + profile.getAvatar());
            if (profile.getAvatar() != null)
                Glide.with(getActivity()).load(profile.getAvatar()).into(civSalonImage);

            if (profile.getSalonName() == null || profile.getSalonName().equals("")) {
                String name = new StringBuilder()
                        .append(profile.getFirstName())
                        .append(" ")
                        .append(profile.getLastName())
                        .toString();
                tvName.setText(name);
            } else {
                tvName.setText(profile.getSalonName());
            }

            tvPhone.setText(profile.getMobile());

            if (profile.getEmail() != null)
                tvEmail.setText(profile.getEmail());

            if (profile.getDateOfBirth() != null)
                tvDateOfBirth.setText(profile.getDateOfBirth());

            String days = new StringBuilder()
                    .append(profile.getDays())
                    .append(" ")
                    .append(getString(R.string.days_left))
                    .toString();
            tvSocialist.setText(days);
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
        tvTextLogout.setText(getString(R.string.sure_log_out));
        btnLogout.setText(getString(R.string.logout));

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
        super.onResume();
        loadSalonData();
    }
}
