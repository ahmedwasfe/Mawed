package com.raiyansoft.mawed.adapter.salon;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.listener.ItemClickListener;
import com.raiyansoft.mawed.listener.SalonServicesSelectedListener;
import com.raiyansoft.mawed.model.salon.services.SalonServicesData;
import com.raiyansoft.mawed.utils.HelperMethods;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SalonServiceAdapter extends RecyclerView.Adapter<SalonServiceAdapter.SalonServiceHolder> {
    private static final String TAG = SalonServiceAdapter.class.getSimpleName();

    private Activity activity;
    private List<SalonServicesData> listSerives;
    private LayoutInflater inflater;

    private SalonServicesSelectedListener selectedListener;

    private ImageView itemSelected = null;

    public SalonServiceAdapter(Activity activity, List<SalonServicesData> listSerives, SalonServicesSelectedListener selectedListener) {
        this.activity = activity;
        this.listSerives = listSerives;
        this.selectedListener = selectedListener;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public SalonServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SalonServiceHolder(inflater.inflate(R.layout.row_salon_service, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SalonServiceHolder holder, int position) {
        SalonServicesData service = listSerives.get(position);
        if (service != null){
            if (service.getImage() != null)
                Glide.with(activity).load(service.getImage()).into(holder.rivService);
            Log.d(TAG, "onBindViewHolder: " + service.getTitle());
            Log.d(TAG, "onBindViewHolder: " + service.getTime());
            Log.d(TAG, "onBindViewHolder: " + service.getPrice());
            holder.tvName.setText(service.getTitle());

            if (service.getTime() != null) {
                String time = new StringBuilder()
                        .append(activity.getString(R.string.time))
                        .append(": ")
                        .append(service.getTime())
                        .append(" ")
                        .append(activity.getString(R.string.minute))
                        .toString();
                holder.tvTime.setText(time);
            }else {
                holder.tvTime.setVisibility(View.GONE);
            }

            if (service.getPrice() != null) {
                String price = new StringBuilder()
                        .append(activity.getString(R.string.price))
                        .append(": ")
                        .append(service.getPrice())
                        .append(" ")
                        .append(HelperMethods.getCurrency(activity))
                        .toString();
                holder.tvPrice.setText(price);
            }else {
                holder.tvPrice.setVisibility(View.GONE);
            }


            if (service.isCheck())
                holder.checkService.setBackgroundResource(R.drawable.ic_icon_service_check);
            else
                holder.checkService.setBackgroundResource(R.drawable.ic_icon_service_uncheck);

            holder.checkService.setOnClickListener(v -> {
                if (itemSelected != null)
                    itemSelected.setBackgroundResource(R.drawable.ic_icon_service_uncheck);

                holder.checkService.setBackgroundResource(R.drawable.ic_icon_service_check);
                itemSelected = holder.checkService;
                selectedListener.onSalonServicesDelete(service,holder.checkService, position);
            });

            holder.setItemClickListener((view, pos) -> {
                if (itemSelected != null)
                    itemSelected.setBackgroundResource(R.drawable.ic_icon_service_uncheck);

                holder.checkService.setBackgroundResource(R.drawable.ic_icon_service_check);
                itemSelected = holder.checkService;
                selectedListener.onSalonServicesSelected(service, position);
            });
        }
    }

    @Override
    public int getItemCount() {
        return listSerives.size();
    }

    static class SalonServiceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.riv_service_image)
        RoundedImageView rivService;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.checkbox_service)
        ImageView checkService;


        private ItemClickListener itemClickListener;

        public SalonServiceHolder(@NonNull View itemView) {
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
