package com.raiyansoft.mawed.ui.activities.settings;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.utils.Const;
import com.raiyansoft.mawed.utils.PreferencesManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrivacyPolicyActivity extends AppCompatActivity {
    private static final String TAG = PrivacyPolicyActivity.class.getSimpleName();

    @BindView(R.id.pb_loading)
    ProgressBar loading;
    @BindView(R.id.tv_privacy)
    TextView tvPrivac;
    @BindView(R.id.tv_conditions)
    TextView tvConditions;

    @OnClick(R.id.iv_back)
    void onBackClick() {
        onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        ButterKnife.bind(this);

        if (PreferencesManager.loadAppData(this, Const.KEY_CONDITIONS) != null)
            tvPrivac.setText(PreferencesManager.loadAppData(this, Const.KEY_CONDITIONS));

        if (PreferencesManager.loadAppData(this, Const.KEY_PRIVACY) != null)
            tvConditions.setText(PreferencesManager.loadAppData(this, Const.KEY_PRIVACY));
    }
}
