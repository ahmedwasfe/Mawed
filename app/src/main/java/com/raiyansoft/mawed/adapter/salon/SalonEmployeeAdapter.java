package com.raiyansoft.mawed.adapter.salon;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.listener.EmployeeSelectedListener;
import com.raiyansoft.mawed.listener.ItemClickListener;
import com.raiyansoft.mawed.model.userSalon.EmployeeData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SalonEmployeeAdapter extends RecyclerView.Adapter<SalonEmployeeAdapter.SalonEmployeeHolder> {

    private Activity activity;
    private List<EmployeeData> listEmployees;
    private LayoutInflater inflater;

    private TextView itemSelected = null;

    private EmployeeSelectedListener selectedListener;

    public SalonEmployeeAdapter(Activity activity, List<EmployeeData> listEmployees, EmployeeSelectedListener selectedListener) {
        this.activity = activity;
        this.listEmployees = listEmployees;
        this.selectedListener = selectedListener;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public SalonEmployeeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SalonEmployeeHolder(inflater.inflate(R.layout.row_salon_employee, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SalonEmployeeHolder holder, int position) {

        EmployeeData employee = listEmployees.get(position);
        if (employee != null){
            holder.tvEmployeeName.setText(employee.getName());

            holder.setItemClickListener((view, pos) -> {
                if (itemSelected != null){
                    itemSelected.setBackgroundResource(R.drawable.bg_employee_unselect);
                    itemSelected.setTextColor(activity.getColor(R.color.colorPrimary));
                }

                holder.tvEmployeeName.setBackgroundResource(R.drawable.bg_employee_select);
                holder.tvEmployeeName.setTextColor(activity.getColor(R.color.colorWhite));
                itemSelected = holder.tvEmployeeName;
                selectedListener.onEmployeeSelected(employee, position);
            });
        }

    }

    @Override
    public int getItemCount() {
        return listEmployees.size();
    }

    static class SalonEmployeeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_employee_name)
        TextView tvEmployeeName;

        private ItemClickListener itemClickListener;

        public SalonEmployeeHolder(@NonNull View itemView) {
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
