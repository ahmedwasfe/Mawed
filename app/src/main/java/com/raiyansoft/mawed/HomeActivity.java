package com.raiyansoft.mawed;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.adapter.LanguageAdapter;
import com.raiyansoft.mawed.adapter.PlansAdapter;
import com.raiyansoft.mawed.listener.PlanClickListener;
import com.raiyansoft.mawed.listener.SelectLanguageListener;
import com.raiyansoft.mawed.model.Language;
import com.raiyansoft.mawed.model.plans.PlanData;
import com.raiyansoft.mawed.model.plans.Plans;
import com.raiyansoft.mawed.model.plans.orderPlan.OrderPlan;
import com.raiyansoft.mawed.model.sections.Sections;
import com.raiyansoft.mawed.ui.activities.auth.LoginActivity;
import com.raiyansoft.mawed.ui.activities.businessAccont.create.CreateBusinessAccontActivity;
import com.raiyansoft.mawed.ui.activities.notifications.NotificationsActivity;
import com.raiyansoft.mawed.ui.activities.paying.PayingActivity;
import com.raiyansoft.mawed.ui.activities.salon.HomeSalonActivity;
import com.raiyansoft.mawed.ui.activities.settings.AboutActivity;
import com.raiyansoft.mawed.ui.activities.settings.ContactUsActivity;
import com.raiyansoft.mawed.ui.activities.settings.PrivacyPolicyActivity;
import com.raiyansoft.mawed.ui.activities.settings.QuestionsActivity;
import com.raiyansoft.mawed.utils.Const;
import com.raiyansoft.mawed.utils.HelperMethods;
import com.raiyansoft.mawed.utils.PreferencesManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements SelectLanguageListener, PlanClickListener {
    private static final String TAG = HomeActivity.class.getSimpleName();

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.btn_navigation)
    BottomNavigationView btnNavigation;
    private AppBarConfiguration appBarConfiguration;
    public NavController navController;

    private List<PlanData> listPlans;
    private PlansAdapter plansAdapter;

    private DateFormat dateFormat;

    @OnClick(R.id.iv_my_dates)
    void onMyDatesClick() {
        navController.navigate(R.id.nav_my_date);
    }

    @OnClick(R.id.iv_show_menu)
    void onShowMenuClick() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        HelperMethods.checkAppLanguage(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        getSections();
        initUI();
        initNavigation();

        Log.d(TAG, "onCreate TOKEN: " + HelperMethods.getUserToken(this));
        Log.d(TAG, "onCreate USER: " + new Gson().toJson(HelperMethods.getCurrentUser(this)));
//        Log.d(TAG, "onCreate EXIPRE DATE: " + new Gson().toJson(HelperMethods.getCurrentUser(this).getExpDate()));
//        Log.d(TAG, "onCreate EXIPRE Fee: " + new Gson().toJson(HelperMethods.getCurrentUser(this).getFee()));
//        Log.d(TAG, "onCreate USER TYPE: " + new Gson().toJson(HelperMethods.getUserType(this)));
//
//        Log.d(TAG, "onCreate USER LAT: " + PreferencesManager.loadAppData(this, Const.KEY_CURRENT_LATITUDE));
//        Log.d(TAG, "onCreate USER LNG: " + PreferencesManager.loadAppData(this, Const.KEY_CURRENT_LONGITUDE));
    }

    private void initUI() {


        listPlans = new ArrayList<>();

    }

    private void initNavigation() {

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_sections,
                R.id.nav_my_date, R.id.nav_favorite,
                R.id.nav_profile
        ).build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragments);
        NavigationUI.setupWithNavController(btnNavigation, navController);

        View headerView = navigationView.getHeaderView(0);
        RelativeLayout relHeader = headerView.findViewById(R.id.rel_header);
        TextView tvbBusinessAccount = headerView.findViewById(R.id.tv_create_business_account);
        TextView tvBook = headerView.findViewById(R.id.tv_book_lady);


//        Log.d(TAG, "initNavigation: " + new Gson().toJson(HelperMethods.getCurrentUser(this)));
//        Log.d(TAG, "initNavigation: " + HelperMethods.getCurrentUser(this).getGender());
//        Log.d(TAG, "initNavigation: " + HelperMethods.getCurrentUser(this).getGender());
        if (HelperMethods.getCurrentUser(this) != null) {
            if (HelperMethods.getCurrentUser(this).getGender() != null) {
                if (HelperMethods.getCurrentUser(this).getGender() == 1) {
                    navigationView.setBackgroundColor(getColor(R.color.colorMail));
                    relHeader.setBackgroundColor(getColor(R.color.colorMail));
                    tvBook.setText(getText(R.string.book_for_lady));
                } else {
                    navigationView.setBackgroundColor(getColor(R.color.colorFemail));
                    relHeader.setBackgroundColor(getColor(R.color.colorFemail));
                    tvBook.setText(getText(R.string.book_for_man));
                }
            } else {
                navigationView.setBackgroundColor(getColor(R.color.colorMail));
                relHeader.setBackgroundColor(getColor(R.color.colorMail));
            }
        } else {
            navigationView.setBackgroundColor(getColor(R.color.colorMail));
            relHeader.setBackgroundColor(getColor(R.color.colorMail));
        }

//        if (HelperMethods.getCurrentUser(this) != null){
//            if (HelperMethods.getCurrentUser(this).getType())
//        }else{
//            navigationView.setBackgroundColor(getColor(R.color.colorMail));
//            relHeader.setBackgroundColor(getColor(R.color.colorMail));
//        }

//        Log.d(TAG, "onLocationResult: " + PreferencesManager.loadAppData(HomeActivity.this, Const.KEY_CURRENT_LATITUDE) + "," + PreferencesManager.loadAppData(HomeActivity.this, Const.KEY_CURRENT_LONGITUDE));
//        Log.d(TAG, "initNavigation: " + HelperMethods.getCurrentUser(this).getType());
        if (HelperMethods.getCurrentUser(this) != null) {
            if (HelperMethods.getCurrentUser(this).getType() != null) {
                if (HelperMethods.getCurrentUser(this).getType().equals("1")) {
                    tvbBusinessAccount.setText(getString(R.string.create_business_accont));
                } else {
                    tvbBusinessAccount.setText(getString(R.string.salon_account));
                }
            }
        }
        btnNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    navController.navigate(R.id.nav_home);
                    break;
                case R.id.nav_sections:
                    HelperMethods.services = Const.KEY_CATEGORIES;
                    navController.navigate(R.id.nav_sections);
                    break;
                case R.id.nav_my_date:
                    navController.navigate(R.id.nav_my_date);
                    break;
                case R.id.nav_favorite:
                    navController.navigate(R.id.nav_favorite);
                    break;
                case R.id.nav_profile:
                    navController.navigate(R.id.nav_profile);
                    break;
            }
            return false;
        });

        headerView.findViewById(R.id.tv_upgrade_account)
                .setOnClickListener(v -> {
                    drawerLayout.close();
                    if (HelperMethods.getCurrentUser(this) != null)
                        showUpgradeAccountSheet();
                    else
                        showLogoutDialog();
                });

        headerView.findViewById(R.id.tv_book_lady)
                        .setOnClickListener(v -> {
                            navController.navigate(R.id.nav_sections);
                            drawerLayout.close();
                        });

        headerView.findViewById(R.id.tv_language)
                .setOnClickListener(v -> {
                    drawerLayout.close();
                    showLanguageDialog();
                });

        headerView.findViewById(R.id.tv_about_us)
                .setOnClickListener(v -> {
                    drawerLayout.close();
                    startActivity(new Intent(this, AboutActivity.class));
                });

        headerView.findViewById(R.id.tv_asked_question)
                .setOnClickListener(v -> {
                    drawerLayout.close();
                    startActivity(new Intent(this, QuestionsActivity.class));
                });

        headerView.findViewById(R.id.tv_terms)
                .setOnClickListener(v -> {
                    drawerLayout.close();
                    startActivity(new Intent(this, PrivacyPolicyActivity.class));
                });

        headerView.findViewById(R.id.tv_notifications)
                .setOnClickListener(v -> {
                    drawerLayout.close();
                    if (HelperMethods.getCurrentUser(this) != null)
                        startActivity(new Intent(this, NotificationsActivity.class));
                    else
                        showLogoutDialog();

                });

        headerView.findViewById(R.id.tv_message_manager)
                .setOnClickListener(v -> {
                    drawerLayout.close();
                    startActivity(new Intent(this, ContactUsActivity.class));
                });

        headerView.findViewById(R.id.tv_create_business_account)
                .setOnClickListener(v -> {
                    Log.d(TAG, "initNavigation: " + HelperMethods.getUserType(this));
                    drawerLayout.close();
                    if (HelperMethods.getUserType(this).equals("1")) {
                        PreferencesManager.saveAppData(this, Const.KEY_USER_TYPE, "2");
                        startActivity(new Intent(this, HomeSalonActivity.class));
                        finish();
                    } else {
                        if (HelperMethods.getCurrentUser(this) != null)
                            startActivity(new Intent(this, CreateBusinessAccontActivity.class));
                        else
                            showLogoutDialog();
                    }
                });

    }

    @Override
    public boolean onSupportNavigateUp() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragments);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
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

    private void showLogoutDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_logout, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        HelperMethods.showCustomDialog(dialog);

        CardView card = dialogView.findViewById(R.id.card_dialog);
        card.setBackgroundColor(Color.TRANSPARENT);

        TextView tvTextLogout = dialogView.findViewById(R.id.tv_text_logout);
        Button btnLogout = dialogView.findViewById(R.id.btn_logout);

        tvTextLogout.setText(getString(R.string.please_login));
        btnLogout.setText(getString(R.string.log_in));


        dialogView.findViewById(R.id.btn_cancel)
                .setOnClickListener(view -> dialog.dismiss());

        dialogView.findViewById(R.id.btn_logout)
                .setOnClickListener(v -> {
                    dialog.dismiss();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                });

        dialog.show();
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
        startActivity(new Intent(this, HomeActivity.class));
        finish();
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
                                    plansAdapter = new PlansAdapter(HomeActivity.this, listPlans, HomeActivity.this, sheetDialog);
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
    public void onPlanClick(PlanData plan, BottomSheetDialog sheetDialog, int position) {
        orderPackage(plan, sheetDialog);
    }

    private void orderPackage(PlanData plan, BottomSheetDialog sheetDialog) {

        HelperMethods.getMawedAPI()
                .orderPlan(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this),
                        plan.getId(),
                        HelperMethods.getDateToday(this),
                        1)
                .enqueue(new Callback<OrderPlan>() {
                    @Override
                    public void onResponse(Call<OrderPlan> call, Response<OrderPlan> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getOrderPlan() != null) {
//                                    HelperMethods.showCustomToast(HomeActivity.this, "Order plan successfully", true);
                                    sheetDialog.dismiss();
                                    if (response.body().getOrderPlan().getUrl() != null) {
                                        startActivity(new Intent(HomeActivity.this, PayingActivity.class)
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

    private void getSections() {
        HelperMethods.getMawedAPI()
                .getSections(
                        HelperMethods.getUserToken(this),
                        HelperMethods.getAppLanguage(this),
                        HelperMethods.getCurrentUser(this) != null
                                ? HelperMethods.getCurrentUser(this).getGender() != null ?  HelperMethods.getCurrentUser(this).getGender() : 0
                                : 0)
                .enqueue(new Callback<Sections>() {
                    @Override
                    public void onResponse(Call<Sections> call, Response<Sections> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getData() != null) {
                                    PreferencesManager.clear(HomeActivity.this, Const.KEY_SECTIONS);
                                    PreferencesManager.saveSections(HomeActivity.this, Const.KEY_SECTIONS,
                                            response.body().getData().getCategories().getSections());
//                                    Log.d(TAG, "onResponse SERVER: " + new Gson().toJson(response.body().getData().getCategories()));
//                                    Log.d(TAG, "onResponse LOCAL: " + new Gson().toJson(PreferencesManager.getSections(HomeActivity.this, Const.KEY_SECTIONS)));
//                                    Log.d(TAG, "onResponse: " + response.body().getData().getCategories().getSections().size());
//                                    Log.d(TAG, "onResponse: " + new Gson().toJson(response.body().getData().getCategories().getSections()));
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Sections> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }
}