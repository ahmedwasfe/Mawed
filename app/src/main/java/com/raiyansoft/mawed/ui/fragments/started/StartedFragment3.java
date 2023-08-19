package com.raiyansoft.mawed.ui.fragments.started;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.raiyansoft.mawed.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartedFragment3 extends Fragment {

    @BindView(R.id.tv_title_started)
    TextView tvTitleStarted;
    @BindView(R.id.tv_desc_started)
    TextView tvDescStarted;

    private String title;
    private String description;

    public StartedFragment3(String title, String description) {
        this.title = title;
        this.description = description;
    }

    private static StartedFragment3 instance;

    public static StartedFragment3 getInstance(String title, String description){
        return instance == null ? new StartedFragment3(title, description) : instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_started_3, container, false);
        ButterKnife.bind(this, layoutView);
        return layoutView;
    }
}
