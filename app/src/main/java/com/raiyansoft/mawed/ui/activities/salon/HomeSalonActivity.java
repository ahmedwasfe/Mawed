package com.raiyansoft.mawed.ui.activities.salon;


import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.ScannerActitvity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeSalonActivity extends AppCompatActivity {
    private static final String TAG = HomeSalonActivity.class.getSimpleName();

    // TODO 1 زائر
    // TODO 2 صالون
    // TODO 3 موظف

    @BindView(R.id.btn_navigation)
    BottomNavigationView btnNavigation;

    private AppBarConfiguration appBarConfiguration;
    public NavController navController;

    @OnClick(R.id.iv_my_dates)
    void onMyDatesClick(){
        startActivity(new Intent(this, DatesOrdersActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_salon);
        ButterKnife.bind(this);
        initNavigation();
//
//        Log.d(TAG, "onCreate TOKEN: " + HelperMethods.getUserToken(this));
//        Log.d(TAG, "onCreate USER: " + new Gson().toJson(HelperMethods.getCurrentUser(this)));
//        Log.d(TAG, "onCreate USER TYPE: " + HelperMethods.getUserType(this));
    }

    private void initNavigation() {

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_salon_profile, R.id.nav_scan
        ).build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragments);
        NavigationUI.setupWithNavController(btnNavigation, navController);


        btnNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_salon_profile:
                    navController.navigate(R.id.nav_salon_profile);
                    break;
                case R.id.nav_scan:
                    startActivity(new Intent(this, ScannerActitvity.class));
                    break;
            }
            return false;
        });
    }
}
