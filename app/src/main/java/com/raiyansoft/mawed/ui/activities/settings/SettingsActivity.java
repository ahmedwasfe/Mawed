package com.raiyansoft.mawed.ui.activities.settings;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.HomeActivity;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.adapter.LanguageAdapter;
import com.raiyansoft.mawed.adapter.PlansAdapter;
import com.raiyansoft.mawed.listener.PlanClickListener;
import com.raiyansoft.mawed.listener.SelectLanguageListener;
import com.raiyansoft.mawed.model.Language;
import com.raiyansoft.mawed.model.plans.Plans;
import com.raiyansoft.mawed.model.plans.PlanData;
import com.raiyansoft.mawed.model.plans.orderPlan.OrderPlan;
import com.raiyansoft.mawed.model.settings.about.About;
import com.raiyansoft.mawed.model.settings.about.AboutData;
import com.raiyansoft.mawed.ui.activities.paying.PayingActivity;
import com.raiyansoft.mawed.ui.activities.salon.HomeSalonActivity;
import com.raiyansoft.mawed.ui.activities.settings.address.AddressesActivity;
import com.raiyansoft.mawed.utils.Const;
import com.raiyansoft.mawed.utils.HelperMethods;
import com.raiyansoft.mawed.utils.PreferencesManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity implements SelectLanguageListener, PlanClickListener {
    private static final String TAG = SettingsActivity.class.getSimpleName();

    @BindView(R.id.tv_app_language)
    TextView tvLanguage;
    @BindView(R.id.ll_salon_account)
    LinearLayout llSalonAccount;
    private RecyclerView recyclerPlans;
    private List<PlanData> listPlans;
    private PlansAdapter plansAdapter;

    @OnClick(R.id.iv_back)
    void onBackClick() {
        onBackPressed();
    }

    @OnClick(R.id.ll_edit_profile)
    void onEditProfileClick() {
        startActivity(new Intent(this, EditProfileActivity.class));
    }

    @OnClick(R.id.ll_upgrade_account)
    void onUpgradeAccountClick() {
        showUpgradeAccountSheet();
    }

    @OnClick(R.id.ll_app_language)
    void onChangeLanguageClick() {
        showLanguageDialog();
    }

    @OnClick(R.id.ll_change_phone_number)
    void onChangePhoneClick() {
        startActivity(new Intent(this, ChangePhoneNumberActivity.class));
    }

    @OnClick(R.id.ll_asked_questions)
    void onAskedQuestionClick() {
        startActivity(new Intent(this, QuestionsActivity.class));
    }

    @OnClick(R.id.ll_address)
    void onAddressClick() {
        startActivity(new Intent(this, AddressesActivity.class));
    }

    @OnClick(R.id.ll_salon_account)
    void onOpenSalonAccount() {
        PreferencesManager.saveAppData(this, Const.KEY_USER_TYPE, "2");
        startActivity(new Intent(this, HomeSalonActivity.class));
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        HelperMethods.checkAppLanguage(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        initUI();
        getAppLanguage();
    }

    private void getAppLanguage() {
        if (HelperMethods.getAppLanguage(this).equals(Const.KEY_LANGUAGE_AR))
            tvLanguage.setText(getString(R.string.arabic));
        else
            tvLanguage.setText(getString(R.string.english));
    }

    private void initUI() {

        listPlans = new ArrayList<>();

        Log.d(TAG, "initUI STATUS: " + HelperMethods.getCurrentUser(this).getStatus());
        Log.d(TAG, "initUI TYPE: " + HelperMethods.getCurrentUser(this).getType());
        if (HelperMethods.getCurrentUser(this) != null) {
            if (HelperMethods.getCurrentUser(this).getType().equals("2")) {
                llSalonAccount.setVisibility(View.VISIBLE);
            }else {
                llSalonAccount.setVisibility(View.GONE);
            }
        } else {
            llSalonAccount.setVisibility(View.GONE);

    }

}

    private void showUpgradeAccountSheet() {
        BottomSheetDialog sheetDialog = new BottomSheetDialog(this);
        sheetDialog.setContentView(R.layout.layout_sheet_upgrade_account);

        CardView card = sheetDialog.findViewById(R.id.card_upgrade_account);
        card.setBackgroundResource(R.drawable.bg_bottom_sheet);

        RecyclerView recyclerPlans = sheetDialog.findViewById(R.id.recycler_plans);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerPlans.setHasFixedSize(true);
        recyclerPlans.setLayoutManager(layoutManager);
        getPackages(recyclerPlans, sheetDialog);
        sheetDialog.show();
    }

    private void showLanguageDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View viewDialog = getLayoutInflater().inflate(R.layout.dialog_language, null);
        builder.setView(viewDialog);
        AlertDialog dialog = builder.create();
        HelperMethods.showCustomDialog(dialog);

        CardView card = viewDialog.findViewById(R.id.card_dialog);
        card.setBackgroundColor(Color.TRANSPARENT);

        RecyclerView recyclerLanguage = viewDialog.findViewById(R.id.recycler_languages);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerLanguage.setHasFixedSize(true);
        recyclerLanguage.setLayoutManager(layoutManager);

        List<Language> listLanguages = new ArrayList<>();
        listLanguages.add(new Language(getString(R.string.arabic), "ar"));
        listLanguages.add(new Language(getString(R.string.english), "en"));
        LanguageAdapter languageAdapter = new LanguageAdapter(this, listLanguages, this, dialog);
        recyclerLanguage.setAdapter(languageAdapter);

        dialog.show();

    }

    private void getPackages(RecyclerView recyclerPlans, BottomSheetDialog sheetDialog) {

        HelperMethods.getMawedAPI()
                .getPackages(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this))
                .enqueue(new Callback<Plans>() {
                    @Override
                    public void onResponse(Call<Plans> call, Response<Plans> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getPackages() != null) {
                                    listPlans.clear();
                                    listPlans.addAll(response.body().getPackages());
                                    plansAdapter = new PlansAdapter(SettingsActivity.this, listPlans, SettingsActivity.this, sheetDialog);
                                    recyclerPlans.setAdapter(plansAdapter);
                                }
                            } else {
                                Log.e(TAG, "onResponse: " + response.body().getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Plans> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    @Override
    public void onSelectLanguageListener(Language language, int pos, AlertDialog dialog) {
        Log.d(TAG, "onSelectLanguageListener: " + language.getLangCode());
        saveLanguage(language.getLangCode());
        dialog.dismiss();
    }

    private void saveLanguage(String languageCode) {
        Log.d(TAG, "saveLanguage: " + languageCode);
        PreferencesManager.saveLanguage(this, languageCode);
        HelperMethods.saveAppLanguage(this, languageCode);
        getAbout(languageCode);
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void onPlanClick(PlanData plan, BottomSheetDialog sheetDialog, int position) {
        orderPackage(plan, sheetDialog);
    }

    private void orderPackage(PlanData plan, BottomSheetDialog sheetDialog) {

        HelperMethods.getMawedAPI()
                .orderPlan(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this),
                        plan.getId(),
                        HelperMethods.getDateToday(SettingsActivity.this),
                        1)
                .enqueue(new Callback<OrderPlan>() {
                    @Override
                    public void onResponse(Call<OrderPlan> call, Response<OrderPlan> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getOrderPlan() != null) {
//                                    HelperMethods.showCustomToast(SettingsActivity.this, "Order plan successfully", true);
                                    sheetDialog.dismiss();
                                    if (response.body().getOrderPlan().getUrl() != null) {
                                        startActivity(new Intent(SettingsActivity.this, PayingActivity.class)
                                                .putExtra(Const.KEY_PAYING_URL, response.body().getOrderPlan().getUrl()));
                                    }
                                }
                            } else {
                                Log.e(TAG, "onResponse: " + response.body().getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderPlan> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    private void getAbout(String language) {
        HelperMethods.getMawedAPI()
                .about(language)
                .enqueue(new Callback<About>() {
                    @Override
                    public void onResponse(Call<About> call, Response<About> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getData() != null) {
                                AboutData about = response.body().getData();
                                Log.d(TAG, "onResponse: " + about.getAbout());
                                PreferencesManager.saveAppData(SettingsActivity.this, Const.KEY_ABOUT, about.getAbout());
                                PreferencesManager.saveAppData(SettingsActivity.this, Const.KEY_PRIVACY, about.getPrivacy());
                                PreferencesManager.saveAppData(SettingsActivity.this, Const.KEY_CONDITIONS, about.getConditions());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<About> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }
}
