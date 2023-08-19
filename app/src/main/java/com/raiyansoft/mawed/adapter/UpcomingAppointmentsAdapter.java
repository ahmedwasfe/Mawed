package com.raiyansoft.mawed.adapter;

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
import com.raiyansoft.mawed.model.BookingData;
import com.raiyansoft.mawed.model.home.HomeOrders;
import com.raiyansoft.mawed.ui.activities.orders.OrderSamaryActivity;
import com.raiyansoft.mawed.ui.activities.paying.BookingDoneActivity;
import com.raiyansoft.mawed.utils.Const;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpcomingAppointmentsAdapter extends RecyclerView.Adapter<UpcomingAppointmentsAdapter.UpcomingAppointmentsHolder> {

    private Activity activity;
    private List<HomeOrders> listAppointments;
    private LayoutInflater inflater;

    public UpcomingAppointmentsAdapter(Activity activity, List<HomeOrders> listAppointments) {
        this.activity = activity;
        this.listAppointments = listAppointments;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public UpcomingAppointmentsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UpcomingAppointmentsHolder(inflater.inflate(R.layout.row_upcoming_appointments, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingAppointmentsHolder holder, int position) {
        HomeOrders order = listAppointments.get(position);
        if (order != null){
            holder.tvName.setText(order.getSalonName());
            holder.tvDate.setText(order.getDateTime());

            holder.setItemClickListener((view, pos) -> {

                BookingData bookingData = new BookingData();
                bookingData.setPaymentUrl("");
                bookingData.setOrderId(order.getId());
                bookingData.setSalonName(order.getSalonName());
//                bookingData.setTotal(String.valueOf(order.getTotalCost()));
//                bookingData.setPaymentDate(order.getDate());
//                bookingData.setPaymentMethod(order.getPayment());
//                activity.startActivity(new Intent(activity, BookingDoneActivity.class)
//                        .putExtra(Const.KEY_BOOKING_DATA, bookingData));

                activity.startActivity(new Intent(activity, OrderSamaryActivity.class)
                        .putExtra(Const.KEY_APPOINTMENT_ID, order.getId()));
            });
        }
    }

    @Override
    public int getItemCount() {
        return listAppointments.size();
    }

    static class UpcomingAppointmentsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_salon_name)
        TextView tvName;
        @BindView(R.id.tv_salon_date)
        TextView tvDate;

        private ItemClickListener itemClickListener;

        public UpcomingAppointmentsHolder(@NonNull View itemView) {
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
