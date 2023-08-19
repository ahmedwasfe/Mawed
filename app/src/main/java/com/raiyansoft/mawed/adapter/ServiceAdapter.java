package com.raiyansoft.mawed.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.listener.SendServicesListener;
import com.raiyansoft.mawed.model.employee.EmployeeDataCat;
import com.raiyansoft.mawed.model.userOrders.sendOrder.SendService;
import com.raiyansoft.mawed.utils.HelperMethods;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceHolder> {
    private static final String TAG = ServiceAdapter.class.getSimpleName();

    private Activity activity;
    private List<EmployeeDataCat> listServices;
    private LayoutInflater inflater;

    private SendServicesListener sendServicesListener;
    private List<SendService> listSnedServices;

    public ServiceAdapter(Activity activity, List<EmployeeDataCat> listServices, SendServicesListener sendServicesListener) {
        this.activity = activity;
        this.listServices = listServices;
        this.sendServicesListener = sendServicesListener;
        listSnedServices = new ArrayList<>();
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public ServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ServiceHolder(inflater.inflate(R.layout.row_service, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceHolder holder, int position) {
        EmployeeDataCat service = listServices.get(position);
        if (service != null) {
            holder.tvServiceName.setText(service.getTitle());

            String price = new StringBuilder()
                    .append(service.getPrice())
                    .append(" ")
                    .append(HelperMethods.getCurrency(activity))
                    .toString();
            holder.tvServicePrice.setText(price);

            holder.checkService.setOnClickListener(v -> {
                if (holder.checkService.isChecked()) {
                    listSnedServices.add(new SendService(service.getId(), service.getPrice(), service.getTitle(), service.getTime()));
                } else {
                    try{
                        for (int i = 0; i < listSnedServices.size(); i++){
                            int id = listSnedServices.get(i).getItemId();
                            if (id == service.getId())
                                listSnedServices.remove(listSnedServices.get(i));
//                            if (listSnedServices.contains(id))

//                            notifyDataSetChanged();
                        }
                    }catch (ConcurrentModificationException e) {
                        Log.e(TAG, "onBindViewHolder: " + e.toString());
                    }

//                    for (int i = listSnedServices.size() - 1; i >= 0 ; i--) {
//
//                    }
//                    listSnedServices.remove();
                }
                sendServicesListener.onSendService(listSnedServices, position, holder.checkService.isChecked());
            });
        }
    }

    @Override
    public int getItemCount() {
        return listServices.size();
    }

    static class ServiceHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.checkbox_service)
        CheckBox checkService;
        @BindView(R.id.tv_service_name)
        TextView tvServiceName;
        @BindView(R.id.tv_service_price)
        TextView tvServicePrice;

        public ServiceHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
