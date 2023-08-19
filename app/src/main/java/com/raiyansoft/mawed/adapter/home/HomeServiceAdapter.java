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
import com.raiyansoft.mawed.listener.SelectHomeServiceListener;
import com.raiyansoft.mawed.model.home.HomeService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeServiceAdapter extends RecyclerView.Adapter<HomeServiceAdapter.HomeServiceHolder> {
    private static final String TAG = HomeServiceAdapter.class.getSimpleName();

    private Activity activity;
    private List<HomeService> listGovernorates;
    private LayoutInflater inflater;

    private SelectHomeServiceListener serviceListener;
    private ImageView itemSelected = null;

    public HomeServiceAdapter(Activity activity, List<HomeService> listGovernorates, SelectHomeServiceListener serviceListener) {
        this.activity = activity;
        this.listGovernorates = listGovernorates;
        this.serviceListener = serviceListener;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public HomeServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeServiceHolder(inflater.inflate(R.layout.row_home_governorate, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeServiceHolder holder, int position) {
        HomeService service = listGovernorates.get(position);
        if (service != null){
            holder.tvGovernorate.setText(service.getTitle());

            holder.setItemClickListener((view, pos) -> {
                if (itemSelected != null)
                    itemSelected.setBackgroundResource(R.drawable.ic_icon_service_uncheck);

                holder.ivGovernorate.setBackgroundResource(R.drawable.ic_icon_service_check);
                itemSelected = holder.ivGovernorate;
                serviceListener.onServiceSelected(service, pos);
            });
        }
    }

    @Override
    public int getItemCount() {
        return listGovernorates.size();
    }

    static class HomeServiceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_check)
        ImageView ivGovernorate;
        @BindView(R.id.tv_name)
        TextView tvGovernorate;

        private ItemClickListener itemClickListener;

        public HomeServiceHolder(@NonNull View itemView) {
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
