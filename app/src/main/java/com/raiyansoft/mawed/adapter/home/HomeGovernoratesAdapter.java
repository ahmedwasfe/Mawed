package com.raiyansoft.mawed.adapter.home;

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
import com.raiyansoft.mawed.listener.SelectHomeGovernorateListener;
import com.raiyansoft.mawed.model.home.HomeGovernorate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeGovernoratesAdapter extends RecyclerView.Adapter<HomeGovernoratesAdapter.HomeGovernoratesHolder> {
    private static final String TAG = HomeGovernoratesAdapter.class.getSimpleName();

    private Activity activity;
    private List<HomeGovernorate> listGovernorates;
    private LayoutInflater inflater;

    private SelectHomeGovernorateListener governorateListener;
    private ImageView itemSelected = null;

    public HomeGovernoratesAdapter(Activity activity, List<HomeGovernorate> listGovernorates, SelectHomeGovernorateListener governorateListener) {
        this.activity = activity;
        this.listGovernorates = listGovernorates;
        this.governorateListener = governorateListener;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public HomeGovernoratesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeGovernoratesHolder(inflater.inflate(R.layout.row_home_governorate, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeGovernoratesHolder holder, int position) {
        HomeGovernorate governorate = listGovernorates.get(position);
        if (governorate != null){
            holder.tvGovernorate.setText(governorate.getTitle());

            holder.setItemClickListener((view, pos) -> {
                if (itemSelected != null)
                    itemSelected.setBackgroundResource(R.drawable.ic_icon_service_uncheck);

                holder.ivGovernorate.setBackgroundResource(R.drawable.ic_icon_service_check);
                itemSelected = holder.ivGovernorate;
                governorateListener.onGovernorateSelected(governorate, pos);
            });
        }
    }

    @Override
    public int getItemCount() {
        return listGovernorates.size();
    }

    static class HomeGovernoratesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_check)
        ImageView ivGovernorate;
        @BindView(R.id.tv_name)
        TextView tvGovernorate;

        private ItemClickListener itemClickListener;

        public HomeGovernoratesHolder(@NonNull View itemView) {
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
