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
import com.raiyansoft.mawed.listener.SelectHomeSalonListener;
import com.raiyansoft.mawed.model.filter.SalonData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeSalonAdapter extends RecyclerView.Adapter<HomeSalonAdapter.HomeServiceHolder> {
    private static final String TAG = HomeSalonAdapter.class.getSimpleName();

    private Activity activity;
    private List<SalonData> listAppUsers;
    private LayoutInflater inflater;

    private SelectHomeSalonListener appUserListener;
    private ImageView itemSelected = null;

    public HomeSalonAdapter(Activity activity, List<SalonData> listAppUsers, SelectHomeSalonListener appUserListener) {
        this.activity = activity;
        this.listAppUsers = listAppUsers;
        this.appUserListener = appUserListener;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public HomeServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeServiceHolder(inflater.inflate(R.layout.row_home_governorate, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeServiceHolder holder, int position) {
        SalonData salon = listAppUsers.get(position);
        if (salon != null){
            holder.tvGovernorate.setText(salon.getFirstName());

            holder.setItemClickListener((view, pos) -> {
                if (itemSelected != null)
                    itemSelected.setBackgroundResource(R.drawable.ic_icon_service_uncheck);

                holder.ivGovernorate.setBackgroundResource(R.drawable.ic_icon_service_check);
                itemSelected = holder.ivGovernorate;
                appUserListener.onSalonSelected(salon, pos);
            });
        }
    }

    @Override
    public int getItemCount() {
        return listAppUsers.size();
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
