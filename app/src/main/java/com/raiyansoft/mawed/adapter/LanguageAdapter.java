package com.raiyansoft.mawed.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.listener.SelectLanguageListener;
import com.raiyansoft.mawed.model.Language;
import com.raiyansoft.mawed.utils.HelperMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageHolder> {
    private static final String TAG = LanguageAdapter.class.getSimpleName();

    private Activity activity;
    private List<Language> listLanguages;
    private LayoutInflater inflater;

    private SelectLanguageListener selectLanguageListener;

    private int lastPositionCheck = -1;

  private AlertDialog dialog;

    public LanguageAdapter(Activity activity, List<Language> listLanguages, SelectLanguageListener selectLanguageListener, AlertDialog dialog) {
        this.activity = activity;
        this.listLanguages = listLanguages;
        this.selectLanguageListener = selectLanguageListener;
        this.dialog = dialog;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public LanguageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LanguageHolder(inflater.inflate(R.layout.row_languages, parent, false));
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull LanguageHolder holder, int position) {

        Language language = listLanguages.get(position);
        holder.tvLanguage.setText(language.getName());

        Log.d(TAG, "showLanguageDialog: " + HelperMethods.getAppLanguage(activity));

        if (position == 0)
            holder.view.setBackgroundColor(activity.getColor(R.color.colorPrimary));
        else
            holder.view.setBackgroundColor(activity.getColor(R.color.colorWhite));


        if (HelperMethods.getAppLanguage(activity).equals(language.getLangCode()))
            holder.rbLanguages.setChecked(true);
        else if(HelperMethods.getAppLanguage(activity).equals(language.getLangCode()))
            holder.rbLanguages.setChecked(true);
        else
            holder.rbLanguages.setChecked(position == lastPositionCheck);

        holder.rbLanguages.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                lastPositionCheck = holder.getAdapterPosition();
                selectLanguageListener.onSelectLanguageListener(language, position, dialog);
                notifyDataSetChanged();
            }

        });
    }

    @Override
    public int getItemCount() {
        return listLanguages.size();
    }

    static class LanguageHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.view)
        View view;
        @BindView(R.id.rel_language)
        RelativeLayout relLanguage;

        @BindView(R.id.rb_language)
        RadioButton rbLanguages;
        @BindView(R.id.tv_language)
        TextView tvLanguage;

        public LanguageHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}