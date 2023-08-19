package com.raiyansoft.mawed.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.listener.ItemClickListener;
import com.raiyansoft.mawed.model.sections.SectionData;
import com.raiyansoft.mawed.ui.activities.SalonsActivity;
import com.raiyansoft.mawed.utils.Const;
import com.raiyansoft.mawed.utils.HelperMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SectionsAdapter extends RecyclerView.Adapter<SectionsAdapter.SectionsHolder> {
    private static final String TAG = SectionsAdapter.class.getSimpleName();

    private Activity activity;
    private List<SectionData> listSections;
    private LayoutInflater inflater;

    public SectionsAdapter(Activity activity, List<SectionData> listSections) {
        this.activity = activity;
        this.listSections = listSections;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public SectionsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SectionsHolder(inflater.inflate(R.layout.row_sections, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SectionsHolder holder, int position) {

        SectionData section = listSections.get(position);
        if (section != null){
            holder.cardSection.setCardBackgroundColor(Color.parseColor(section.getColor()));
            Glide.with(activity).load(section.getImage()).into(holder.ivSectionIcon);
            holder.tvSectionName.setText(section.getTitle());
            if (position % 2 == 0)
                holder.tvSectionName.setTextColor(activity.getColor(R.color.colorMainText));
            else
                holder.tvSectionName.setTextColor(activity.getColor(R.color.colorWhite));

            holder.setItemClickListener((view, pos) -> {
                if (HelperMethods.services.equals(Const.KEY_SERVICES)) {
                    Log.d(TAG, "onBindViewHolder: From Home services" );
                    HelperMethods.servicesId = section.getId();
                    HelperMethods.servicesName = section.getTitle();
                    activity.onBackPressed();
                } else if (HelperMethods.services.equals(Const.KEY_CATEGORIES)){
                    Log.d(TAG, "onBindViewHolder: Go to Salons By Categoty");
                    activity.startActivity(new Intent(activity, SalonsActivity.class)
                            .putExtra(Const.KEY_SECTION, section));
                }
            });

        }



    }

    @Override
    public int getItemCount() {
        return listSections.size();
    }

    static class SectionsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.card_section)
        CardView cardSection;
        @BindView(R.id.iv_section_icon)
        ImageView ivSectionIcon;
        @BindView(R.id.tv_section_name)
        TextView tvSectionName;

        private ItemClickListener itemClickListener;

        public SectionsHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
