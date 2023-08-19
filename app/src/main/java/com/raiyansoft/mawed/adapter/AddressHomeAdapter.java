package com.raiyansoft.mawed.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.listener.ItemClickListener;
import com.raiyansoft.mawed.listener.SelectHomeAddressListener;
import com.raiyansoft.mawed.listener.SelectRegionsListener;
import com.raiyansoft.mawed.model.home.HomeGovernorate;
import com.raiyansoft.mawed.model.region.Region;
import com.raiyansoft.mawed.model.region.RegionData;
import com.raiyansoft.mawed.ui.activities.settings.address.AddAddressActivity;
import com.raiyansoft.mawed.utils.HelperMethods;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressHomeAdapter extends RecyclerView.Adapter<AddressHomeAdapter.RegionsHolder> implements SelectRegionsListener {
    private static final String TAG = AddAddressActivity.class.getSimpleName();

    private Activity activity;
    private List<HomeGovernorate> listGovernorates;
    private LayoutInflater inflater;

//    private SelectRegionsListener regionsListener;
    private SelectHomeAddressListener addressListener;
    private RegionData regionData;

    private ImageView itemSelected = null;

    public AddressHomeAdapter(Activity activity, List<HomeGovernorate> listGovernorates, SelectHomeAddressListener addressListener) {
        this.activity = activity;
        this.listGovernorates = listGovernorates;
        this.addressListener = addressListener;
        this.regionData = regionData;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public RegionsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RegionsHolder(inflater.inflate(R.layout.row_home_address, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RegionsHolder holder, int position) {
        HomeGovernorate governorate = listGovernorates.get(position);
        if (governorate != null){
            holder.tvGovernorate.setText(governorate.getTitle());
            initRecycler(governorate, holder, position);
//            holder.ivGovernorate.setOnClickListener(view -> {
//                if (itemSelected != null)
//                    itemSelected.setBackgroundResource(R.drawable.ic_icon_service_uncheck);
//
//                holder.ivGovernorate.setBackgroundResource(R.drawable.ic_icon_service_check);
//                itemSelected = holder.ivGovernorate;
//                governorateListener.onGovernorateSelected(governorate, position);
//            });

            holder.ivArrow.setBackgroundResource(R.drawable.ic_icon_arrow_down);
            holder.llAddress.setOnClickListener(v -> {
                if (holder.expandableLayout.isExpanded())
                    holder.ivArrow.setBackgroundResource(R.drawable.ic_icon_arrow_down);
                else
                    holder.ivArrow.setBackgroundResource(R.drawable.ic_icon_arrow_up);
                holder.expandableLayout.toggle();
            });
        }
    }

    private void initRecycler(HomeGovernorate governorate, RegionsHolder holder, int position) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        holder.recyclerRegions.setHasFixedSize(true);
        holder.recyclerRegions.setLayoutManager(layoutManager);
        getRegions(governorate, holder, position);
    }

    private void getRegions(HomeGovernorate governorate, RegionsHolder holder, int position) {

        List<RegionData> listRegions = new ArrayList<>();

        RegionsHomeAdapter regionsAdapter = new RegionsHomeAdapter(activity, listRegions, this);
        HelperMethods.getMawedAPI()
                .getRegions(HelperMethods.getAppLanguage(activity), governorate.getId())
                .enqueue(new Callback<Region>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(Call<Region> call, Response<Region> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getRegions() != null) {
                                    listRegions.clear();
                                    listRegions.addAll(response.body().getRegions());
//                                    regionId = listRegions.get(0).getRegionId();
                                    regionData = listRegions.get(position);

                                    holder.recyclerRegions.setAdapter(regionsAdapter);
                                } else {
                                    listRegions.clear();
//                                    regionsAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Region> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });

    }


    @Override
    public int getItemCount() {
        return listGovernorates.size();
    }

    @Override
    public void onRegionSelected(RegionData region, int pos) {
        regionData = region;
        try {
            addressListener.onAddressSelected(listGovernorates.get(pos), region, pos);
        }catch (IndexOutOfBoundsException exception){
            Log.d(TAG, "onRegionSelected: " + exception.getMessage());
        }

        Log.d(TAG, "onRegionSelected: " + region.getTitle());
    }

    static class RegionsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.ll_governorate)
        LinearLayout llAddress;
        @BindView(R.id.iv_arrow)
        ImageView ivArrow;
        @BindView(R.id.expandable_layout_address)
        ExpandableLayout expandableLayout;
        @BindView(R.id.recycler_regions)
        RecyclerView recyclerRegions;
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
