package com.raiyansoft.mawed.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.listener.ItemClickListener;
import com.raiyansoft.mawed.model.BookingData;
import com.raiyansoft.mawed.model.userOrders.UserOrdersData;
import com.raiyansoft.mawed.ui.activities.orders.OrderSamaryActivity;
import com.raiyansoft.mawed.ui.activities.paying.BookingDoneActivity;
import com.raiyansoft.mawed.utils.Const;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserUpcomingDatesAdapter extends RecyclerView.Adapter<UserUpcomingDatesAdapter.UserOrdersHolder> {
    private static final String TAG = UserUpcomingDatesAdapter.class.getSimpleName();

    private Activity activity;
    private List<UserOrdersData> listDates;
    private LayoutInflater inflater;

    public UserUpcomingDatesAdapter(Activity activity, List<UserOrdersData> listDates) {
        this.activity = activity;
        this.listDates = listDates;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public UserOrdersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserOrdersHolder(inflater.inflate(R.layout.row_dates, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserOrdersHolder holder, int position) {
        UserOrdersData order = listDates.get(position);
        if (order != null) {
            String title = new StringBuilder()
                    .append("An Dates with")
                    .append(" ")
                    .append(order.getEmployeeName())
                    .toString();

            holder.tvDate.setText(order.getDate());
            holder.tvTime.setText(order.getTime());
            holder.tvRemaining.setText(order.getDateTime());
            holder.tvName.setText(title);
            holder.tvAddress.setText(order.getSalonName());

            // TODO 1 تم الحجز بنجاح / 2 تم الحضور / 3 موعد مضى
//            if (order.getStatus() == 1) {
//                holder.btnStatus.setText(activity.getString(R.string.reservation_done));
//                holder.btnStatus.setTextColor(activity.getColor(R.color.colorGreen1));
//                if (HelperMethods.getAppLanguage(activity).equals("en"))
//                    holder.btnStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_icon_ready, 0,0,0);
//                else
//                    holder.btnStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.ic_icon_ready,0);
//            }else if (order.getStatus() == 2){
//                holder.btnStatus.setText(activity.getString(R.string.attended));
//                holder.btnStatus.setTextColor(activity.getColor(R.color.colorPrimary));
//                if (HelperMethods.getAppLanguage(activity).equals("en"))
//                    holder.btnStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_icon_done, 0,0,0);
//                else
//                    holder.btnStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.ic_icon_done,0);
//            }else if (order.getStatus() == 3){
//                holder.btnStatus.setText(activity.getString(R.string.past_date));
//                holder.btnStatus.setTextColor(activity.getColor(R.color.colorRed));
//                if (HelperMethods.getAppLanguage(activity).equals("en"))
//                    holder.btnStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_icon_cancel, 0,0,0);
//                else
//                    holder.btnStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.ic_icon_cancel,0);
//            }

            holder.btnStatus.setText(activity.getString(R.string.change));
            holder.btnStatus.setTextColor(activity.getColor(R.color.colorPrimary));
            holder.btnStatus.setOnClickListener(v -> {
                activity.startActivity(new Intent(activity, OrderSamaryActivity.class)
                        .putExtra(Const.KEY_APPOINTMENT_ID, order.getId()));
            });

            holder.setItemClickListener((view, pos) -> {
                BookingData bookingData = new BookingData();
                bookingData.setPaymentUrl("");
                bookingData.setOrderId(order.getId());
                bookingData.setSalonName(order.getSalonName());
                bookingData.setTotal(String.valueOf(order.getTotalCost()));
                bookingData.setPaymentDate(order.getDate());
                bookingData.setPaymentMethod(order.getPayment());
                activity.startActivity(new Intent(activity, BookingDoneActivity.class)
                        .putExtra(Const.KEY_BOOKING_DATA, bookingData));
            });
        }
    }

    @Override
    public int getItemCount() {
        return listDates.size();
    }

    static class UserOrdersHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_favorite)
        ImageView ivFavorite;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_remaining)
        TextView tvRemaining;
        @BindView(R.id.tv_location)
        TextView tvAddress;
        @BindView(R.id.btn_status)
        Button btnStatus;

        private ItemClickListener itemClickListener;

        public UserOrdersHolder(@NonNull View itemView) {
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
