package com.raiyansoft.mawed.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.listener.ItemClickListener;
import com.raiyansoft.mawed.model.userSalon.EmployeeData;
import com.raiyansoft.mawed.ui.activities.booking.BookingActivity;
import com.raiyansoft.mawed.utils.Const;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeHolder> {
    private static final String TAG = EmployeeAdapter.class.getSimpleName();

    private Activity activity;
    private List<EmployeeData> listEmployees;
    private LayoutInflater inflater;
    private int salonId;
    private boolean isFavorite;

    public EmployeeAdapter(Activity activity, List<EmployeeData> listEmployees, int salonId, boolean isFavorite) {
        this.activity = activity;
        this.listEmployees = listEmployees;
        this.salonId = salonId;
        this.isFavorite = isFavorite;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public EmployeeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EmployeeHolder(inflater.inflate(R.layout.row_employee, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeHolder holder, int position) {
        EmployeeData employee = listEmployees.get(position);
        if (employee != null){

            Log.d(TAG, "onBindViewHolder: " + listEmployees.size());
            Log.d(TAG, "onBindViewHolder: " + employee.getImage());
            Log.d(TAG, "onBindViewHolder: " + employee.getName());
            if (employee.getImage() != null)
                Glide.with(activity).load(employee.getImage()).into(holder.rivEmployee);

            holder.tvEmployeeName.setText(employee.getName());

            holder.setItemClickListener((view, pos) -> {
                activity.startActivity(new Intent(activity, BookingActivity.class)
                        .putExtra(Const.KEY_EMPLOYEE_ID, employee.getId())
                        .putExtra(Const.KEY_SALON_ID, salonId)
                        .putExtra(Const.KEY_SALON_FAV, isFavorite));
            });
        }
    }

    @Override
    public int getItemCount() {
        return listEmployees.size();
    }

    static class EmployeeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.riv_employee_image)
        RoundedImageView rivEmployee;
        @BindView(R.id.tv_employee_name)
        TextView tvEmployeeName;

        private ItemClickListener itemClickListener;

        public EmployeeHolder(@NonNull View itemView) {
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
