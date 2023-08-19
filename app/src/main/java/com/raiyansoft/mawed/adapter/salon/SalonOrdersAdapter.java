package com.raiyansoft.mawed.adapter.salon;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.listener.ItemClickListener;
import com.raiyansoft.mawed.model.salon.OrderData;
import com.raiyansoft.mawed.ui.activities.salon.SalonOrderSamaryActivity;
import com.raiyansoft.mawed.utils.Const;
import com.raiyansoft.mawed.utils.HelperMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SalonOrdersAdapter extends RecyclerView.Adapter<SalonOrdersAdapter.SalonOrdersHolder> {

    private Activity activity;
    private List<OrderData> listOrders;
    private LayoutInflater inflater;

    public SalonOrdersAdapter(Activity activity, List<OrderData> listOrders) {
        this.activity = activity;
        this.listOrders = listOrders;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public SalonOrdersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SalonOrdersHolder(inflater.inflate(R.layout.row_salon_appointments, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SalonOrdersHolder holder, int position) {
        OrderData order = listOrders.get(position);
        if (order != null){
            holder.tvName.setText(order.getUserName());
            holder.tvDate.setText(order.getDate());

            // TODO 1 تم الحجز بنجاح / 2 تم الحضور / 3 موعد مضى
            if (order.getStatus() == 1) {
                holder.tvStatus.setText(activity.getString(R.string.reservation_done));
                holder.tvStatus.setTextColor(activity.getColor(R.color.colorGreen1));
                if (HelperMethods.getAppLanguage(activity).equals("en"))
                    holder.tvStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_icon_ready, 0,0,0);
                else
                    holder.tvStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.ic_icon_ready,0);
            }else if (order.getStatus() == 2){
                holder.tvStatus.setText(activity.getString(R.string.attended));
                holder.tvStatus.setTextColor(activity.getColor(R.color.colorPrimary));
                if (HelperMethods.getAppLanguage(activity).equals("en"))
                    holder.tvStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_icon_done, 0,0,0);
                else
                    holder.tvStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.ic_icon_done,0);
            }else if (order.getStatus() == 3){
                holder.tvStatus.setText(activity.getString(R.string.past_date));
                holder.tvStatus.setTextColor(activity.getColor(R.color.colorRed));
                if (HelperMethods.getAppLanguage(activity).equals("en"))
                    holder.tvStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_icon_cancel, 0,0,0);
                else
                    holder.tvStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.ic_icon_cancel,0);
            }

            holder.setItemClickListener((view, pos) -> {
                activity.startActivity(new Intent(activity, SalonOrderSamaryActivity.class)
                        .putExtra(Const.KEY_APPOINTMENT_ID, order.getId()));
            });

        }
    }

    @Override
    public int getItemCount() {
        return listOrders.size();
    }

    static class SalonOrdersHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_status)
        TextView tvStatus;

        private ItemClickListener itemClickListener;

        public SalonOrdersHolder(@NonNull View itemView) {
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
