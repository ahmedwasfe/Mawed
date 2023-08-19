package com.raiyansoft.mawed.adapter.salon;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.listener.WorkHoursListener;
import com.raiyansoft.mawed.model.salon.workHour.SendWorkHours;
import com.raiyansoft.mawed.model.salon.workHour.WorkHourData;
import com.raiyansoft.mawed.model.salon.workHour.WorkHoursStatus;
import com.raiyansoft.mawed.utils.HelperMethods;
import com.jaredrummler.materialspinner.MaterialSpinner;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkHouresAdapter extends RecyclerView.Adapter<WorkHouresAdapter.WorkHouresHolder> {
    private static final String TAG = WorkHouresAdapter.class.getSimpleName();

    private Activity activity;
    private List<WorkHourData> listWorkHours;
    private LayoutInflater inflater;

    public List<SendWorkHours> listSendWorkHours;
    private WorkHoursListener workHoursListener;
    private List<WorkHoursStatus> listStatus;
    private String statusName;
    private Integer statusType = 1;

    public WorkHouresAdapter(Activity activity, List<WorkHourData> listWorkHours, WorkHoursListener workHoursListener) {
        this.activity = activity;
        this.listWorkHours = listWorkHours;
        listSendWorkHours = new ArrayList<>();
        listStatus = new ArrayList<>();
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public WorkHouresHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WorkHouresHolder(inflater.inflate(R.layout.row_work_houres, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WorkHouresHolder holder, int position) {

        holder.ivShowHour.setBackgroundResource(R.drawable.ic_icon_arrow_down);

        WorkHourData workHour = listWorkHours.get(position);
        if (workHour != null) {
            setupHoursStatus(holder, workHour, position);

            if (workHour.getDay() != null)
                holder.tvDay.setText(workHour.getDay());

            if (workHour.getStartTime1() != null)
                holder.tvStartTime1.setText(workHour.getStartTime1().substring(0, 5));

            if (workHour.getEndTime1() != null)
                holder.tvEndTime1.setText(workHour.getEndTime1().substring(0, 5));

            if (workHour.getStartTime2() != null)
                holder.tvStartTime2.setText(workHour.getStartTime2().substring(0, 5));

            if (workHour.getEndTime2() != null)
                holder.tvEndTime2.setText(workHour.getEndTime2().substring(0, 5));


            holder.llWorkHour.setOnClickListener(v -> {
                if (statusType == 1) {
                    holder.expandableHour.setExpanded(false);
                } else {
                    if (holder.expandableHour.isExpanded())
                        holder.ivShowHour.setBackgroundResource(R.drawable.ic_icon_arrow_down);
                    else
                        holder.ivShowHour.setBackgroundResource(R.drawable.ic_icon_arrow_up);
                    holder.expandableHour.toggle();
                }

            });

            // TODO START TIME 1
            SendWorkHours workHours = new SendWorkHours();
            workHours.setType(workHour.getType());
            workHours.setDay(workHour.getDay());
            workHours.setVendorId(workHour.getVendorId());
            workHours.setValidity(workHour.getValidity());

            holder.tvStartTime1.setOnClickListener(v -> {
                HelperMethods.showTimeDialog(activity, "HH:mm", holder.tvStartTime1);
                workHours.setStartTime1(holder.tvStartTime1.getText().toString());
                Log.d(TAG, "onBindViewHolder: " + holder.tvStartTime1.getText().toString());
                Log.d(TAG, "onBindViewHolder: " + workHours.getStartTime1());
                listSendWorkHours.add(workHours);
            });

            // TODO END TIME 1
            holder.tvEndTime1.setOnClickListener(v -> {
                HelperMethods.showTimeDialog(activity, "HH:mm", holder.tvEndTime1);
                workHours.setEndTime1(holder.tvEndTime1.getText().toString());
                listSendWorkHours.add(workHours);

            });

            // TODO START TIME 2
            holder.tvStartTime2.setOnClickListener(v -> {
                HelperMethods.showTimeDialog(activity, "HH:mm", holder.tvStartTime2);
                workHours.setStartTime2(holder.tvStartTime2.getText().toString());
                listSendWorkHours.add(workHours);
            });

            // TODO END TIME 2
            holder.tvEndTime2.setOnClickListener(v -> {
                HelperMethods.showTimeDialog(activity, "HH:mm", holder.tvEndTime2);
                workHours.setEndTime2(holder.tvEndTime2.getText().toString());
                listSendWorkHours.add(workHours);
            });

        }

    }

    private void setupHoursStatus(WorkHouresHolder holder, WorkHourData workHour, int position) {

        listStatus.add(new WorkHoursStatus(1, activity.getString(R.string.closed)));
        listStatus.add(new WorkHoursStatus(2, activity.getString(R.string.hours24)));
        listStatus.add(new WorkHoursStatus(3, activity.getString(R.string.one_time)));
        listStatus.add(new WorkHoursStatus(4, activity.getString(R.string.two_shifts)));


        List<String> names = new ArrayList<>();
        names.clear();
//        names.add(0, activity.getString(R.string.select_status));
        for (WorkHoursStatus status : listStatus){
            names.add(status.getName());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(activity, android.R.layout.simple_spinner_item, names);
        holder.spinnerStatus.setAdapter(arrayAdapter);

        holder.spinnerStatus.setOnItemSelectedListener((view, pos, id, item) -> {
            statusName = item.toString();
            statusType = listStatus.get(pos).getType();
            Log.d(TAG, "setupHoursStatus: " + statusName);
            Log.d(TAG, "setupHoursStatus: " + statusType);
            if (item.equals(activity.getString(R.string.closed))) {
                holder.spinnerStatus.setTextColor(activity.getColor(R.color.colorDarkRed));
                holder.expandableHour.setExpanded(false);
            } else if (item.equals(activity.getString(R.string.hours24))) {
                holder.spinnerStatus.setTextColor(activity.getColor(R.color.colorGreen2));
                holder.expandableHour.setExpanded(true);
                holder.llEndTime.setVisibility(View.GONE);
            } else if (item.equals(activity.getString(R.string.one_time))){
                holder.spinnerStatus.setTextColor(activity.getColor(R.color.colorPrimary));
                holder.expandableHour.setExpanded(true);
                holder.llEndTime.setVisibility(View.GONE);
            }else if (item.equals(activity.getString(R.string.two_shifts))){
                holder.spinnerStatus.setTextColor(activity.getColor(R.color.colorPrimary));
                holder.expandableHour.setExpanded(true);
                holder.llStartTime.setVisibility(View.VISIBLE);
                holder.llEndTime.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listWorkHours.size();
    }

    static class WorkHouresHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_day)
        TextView tvDay;
        @BindView(R.id.spinner_status)
        MaterialSpinner spinnerStatus;

        @BindView(R.id.ll_start_time)
        LinearLayout llStartTime;
        @BindView(R.id.ll_end_time)
        LinearLayout llEndTime;

        @BindView(R.id.tv_start_time_1)
        TextView tvStartTime1;
        @BindView(R.id.tv_end_time_1)
        TextView tvEndTime1;

        @BindView(R.id.tv_start_time_2)
        TextView tvStartTime2;
        @BindView(R.id.tv_end_time_2)
        TextView tvEndTime2;

        @BindView(R.id.ll_work_hour)
        LinearLayout llWorkHour;
        @BindView(R.id.iv_arrow_hour)
        ImageView ivShowHour;
        @BindView(R.id.expandable_layout_hour)
        ExpandableLayout expandableHour;

        public WorkHouresHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
