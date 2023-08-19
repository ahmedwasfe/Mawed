package com.raiyansoft.mawed.ui.activities.settings;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.utils.Const;
import com.raiyansoft.mawed.utils.PreferencesManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends AppCompatActivity {
    private static final String TAG = AboutActivity.class.getSimpleName();


    @BindView(R.id.tv_about)
    TextView tvAbout;

    @OnClick(R.id.iv_back)
    void onBackClick() {
        onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        if (PreferencesManager.loadAppData(this, Const.KEY_ABOUT) != null)
            tvAbout.setText(PreferencesManager.loadAppData(this, Const.KEY_ABOUT));
    }
}
