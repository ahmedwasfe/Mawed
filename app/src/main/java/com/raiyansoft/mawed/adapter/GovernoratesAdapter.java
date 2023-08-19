package com.raiyansoft.mawed.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.listener.ItemClickListener;
import com.raiyansoft.mawed.listener.SelectGovernorateListener;
import com.raiyansoft.mawed.model.governorate.GovernorateData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GovernoratesAdapter extends RecyclerView.Adapter<GovernoratesAdapter.GovernoratesHolder> {

    private Activity activity;
    private List<GovernorateData> listGovernorates;
    private LayoutInflater inflater;
    private SelectGovernorateListener governorateListner;

    private ImageView itemSelected = null;

    public GovernoratesAdapter(Activity activity, List<GovernorateData> listGovernorates, SelectGovernorateListener governorateListner) {
        this.activity = activity;
        this.listGovernorates = listGovernorates;
        this.governorateListner = governorateListner;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public GovernoratesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GovernoratesHolder(inflater.inflate(R.layout.row_governorates, parent, false));
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull GovernoratesHolder holder, int position) {
        GovernorateData governorate = listGovernorates.get(position);
        if (governorate != null){

            holder.tvGovernorate.setText(governorate.getTitle());

            holder.setItemClickListener((view, pos) -> {
               if (itemSelected != null)
                   itemSelected.setBackgroundResource(R.drawable.ic_icon_service_uncheck);

               holder.ivGovernorate.setBackgroundResource(R.drawable.ic_icon_service_check);
               itemSelected = holder.ivGovernorate;
               governorateListner.onGovernorateSelected(governorate, pos);
            });
        }
    }

    @Override
    public int getItemCount() {
        return listGovernorates.size();
    }

    static class GovernoratesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_check)
        ImageView ivGovernorate;
        @BindView(R.id.tv_name)
        TextView tvGovernorate;

        private ItemClickListener itemClickListener;

        public GovernoratesHolder(@NonNull View itemView) {
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
