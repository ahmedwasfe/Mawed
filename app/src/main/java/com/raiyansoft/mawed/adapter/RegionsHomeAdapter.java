package com.raiyansoft.mawed.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.listener.ItemClickListener;
import com.raiyansoft.mawed.listener.SelectRegionsListener;
import com.raiyansoft.mawed.model.region.RegionData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegionsHomeAdapter extends RecyclerView.Adapter<RegionsHomeAdapter.RegionsHolder> {
    private static final String TAG = RegionsHomeAdapter.class.getSimpleName();

    private Activity activity;
    private List<RegionData> listRegions;
    private LayoutInflater inflater;

    private SelectRegionsListener regionsListener;

    private ImageView itemSelected = null;

    public RegionsHomeAdapter(Activity activity, List<RegionData> listRegions, SelectRegionsListener regionsListener) {
        this.activity = activity;
        this.listRegions = listRegions;
        this.regionsListener = regionsListener;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public RegionsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RegionsHolder(inflater.inflate(R.layout.row_regions, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RegionsHolder holder, int position) {
        RegionData region = listRegions.get(position);

        if (region != null){
            holder.tvGovernorate.setText(region.getTitle());

            holder.setItemClickListener((view, pos) -> {
                if (itemSelected != null)
                    itemSelected.setBackgroundResource(R.drawable.ic_icon_service_uncheck);

                holder.ivGovernorate.setBackgroundResource(R.drawable.ic_icon_service_check);
                itemSelected = holder.ivGovernorate;
                try {
                    regionsListener.onRegionSelected(region, pos);
                }catch (IndexOutOfBoundsException exception){
                    Log.d(TAG, "onRegionSelected: " + exception.getMessage());
                }

            });
        }
    }

    @Override
    public int getItemCount() {
        return listRegions.size();
    }

    static class RegionsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_check)
        ImageView ivGovernorate;
        @BindView(R.id.tv_name)
        TextView tvGovernorate;

        private ItemClickListener itemClickListener;

        public RegionsHolder(@NonNull View itemView) {
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
