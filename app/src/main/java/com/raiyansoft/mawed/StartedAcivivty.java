package com.raiyansoft.mawed;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.adapter.PagerAdapter;
import com.raiyansoft.mawed.model.settings.Settings;
import com.raiyansoft.mawed.model.settings.SettingsData;
import com.raiyansoft.mawed.ui.activities.auth.LoginActivity;
import com.raiyansoft.mawed.ui.fragments.started.StartedFragment1;
import com.raiyansoft.mawed.ui.fragments.started.StartedFragment2;
import com.raiyansoft.mawed.ui.fragments.started.StartedFragment3;
import com.raiyansoft.mawed.utils.Const;
import com.raiyansoft.mawed.utils.HelperMethods;
import com.raiyansoft.mawed.utils.PreferencesManager;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartedAcivivty extends AppCompatActivity {
    private static final String TAG = StartedAcivivty.class.getSimpleName();

    @BindView(R.id.pb_loading)
    ProgressBar loading;
    @BindView(R.id.view_pager_started)
    public ViewPager2 vpStarted;
    @BindView(R.id.dots_indicator)
    DotsIndicator dotsIndicator;

    @OnClick(R.id.tv_skip)
    void onSkipClick() {
        PreferencesManager.saveStartedScreensStatus(this, Const.KEY_STARTED_SCREENS, true);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @OnClick(R.id.btn_next)
    void onNextClick() {
        Log.d(TAG, "onNextClick: " + vpStarted.getCurrentItem());
        if (vpStarted.getCurrentItem() == 2) {
            PreferencesManager.saveStartedScreensStatus(this, Const.KEY_STARTED_SCREENS, true);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else
            vpStarted.setCurrentItem(vpStarted.getCurrentItem() + 1);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started);
        ButterKnife.bind(this);
        getSettings();

    }

    private void getSettings() {

        loading.setVisibility(View.VISIBLE);

        HelperMethods.getMawedAPI()
                .getSettings()
                .enqueue(new Callback<Settings>() {
                    @Override
                    public void onResponse(Call<Settings> call, Response<Settings> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                SettingsData setting = response.body().getSettings();
                                PagerAdapter pagerAdapter = new PagerAdapter(StartedAcivivty.this);

                                vpStarted.setAdapter(pagerAdapter);
                                pagerAdapter.addFragment(StartedFragment1.getInstance(setting.getTitleFirstInterface(), setting.getDesFirstInterface()));
                                pagerAdapter.addFragment(StartedFragment2.getInstance(setting.getTitleSecondInterface(), setting.getDesSecondInterface()));
                                pagerAdapter.addFragment(StartedFragment3.getInstance(setting.getTitleThirdInterface(), setting.getDesThirdInterface()));
                                dotsIndicator.attachTo(vpStarted);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Settings> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                        loading.setVisibility(View.GONE);
                    }
                });
    }
}
