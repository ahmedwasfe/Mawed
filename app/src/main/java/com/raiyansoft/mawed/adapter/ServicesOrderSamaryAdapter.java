package com.raiyansoft.mawed.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.model.userOrders.UserAppointmentService;
import com.raiyansoft.mawed.utils.HelperMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServicesOrderSamaryAdapter extends RecyclerView.Adapter<ServicesOrderSamaryAdapter.ServicesPayingHolder> {

    private Activity activity;
    private List<UserAppointmentService> listServices;
    private LayoutInflater inflater;

    public ServicesOrderSamaryAdapter(Activity activity, List<UserAppointmentService> listServices) {
        this.activity = activity;
        this.listServices = listServices;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public ServicesPayingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ServicesPayingHolder(inflater.inflate(R.layout.row_service_paying, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesPayingHolder holder, int position) {
        UserAppointmentService service = listServices.get(position);
        if (service != null){
            holder.tvServiceName.setText(service.getTitle());
            String price = new StringBuilder()
                    .append(service.getPrice())
                    .append(" ")
                    .append(HelperMethods.getCurrency(activity))
                    .toString();
            holder.tvServicePrice.setText(price);
        }
    }

    @Override
    public int getItemCount() {
        return listServices.size();
    }

    static class ServicesPayingHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_service_name)
        TextView tvServiceName;
        @BindView(R.id.tv_service_price)
        TextView tvServicePrice;


        public ServicesPayingHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
