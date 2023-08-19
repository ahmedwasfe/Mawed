package com.raiyansoft.mawed.adapter;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.listener.CalendarClickListener;
import com.raiyansoft.mawed.listener.CalendarSelectedListener;
import com.raiyansoft.mawed.model.calendar.Calendar;
import com.raiyansoft.mawed.utils.HelperMethods;

import java.time.LocalDate;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarHolder> {
    private static final String TAG = CalendarAdapter.class.getSimpleName();

    private Activity activity;
    private List<Calendar> listDays;
    private LayoutInflater inflater;
    private TextView itemSelected = null;

    private LocalDate currentDay;

    private CalendarSelectedListener selectedListener;

    public CalendarAdapter(Activity activity, List<Calendar> listDays, CalendarSelectedListener selectedListener) {
        this.activity = activity;
        this.listDays = listDays;
        this.selectedListener = selectedListener;
        inflater = LayoutInflater.from(activity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            currentDay = LocalDate.now();
        }
    }

    @NonNull
    @Override
    public CalendarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.row_calendar_cell, parent, false);
//        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
//        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new CalendarHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull CalendarHolder holder, int position) {

        Calendar calendar = listDays.get(position);
        if (calendar != null) {

            holder.tvDay.setTextColor(activity.getColor(R.color.colorMainText));
            holder.tvDay.setText(calendar.getCurrentDay());
//            Log.d(TAG, "onBindViewHolder currentDay: " + HelperMethods.monthYearFromDate(activity, currentDay, "dd"));
//            Log.d(TAG, "onBindViewHolder getCurrentDay: " + calendar.getCurrentDay() + "-" + calendar.getCurrentDate());

            if (calendar.getCurrentDay() != null) {
                if (HelperMethods.monthYearFromDate(activity, currentDay, "d").equals(calendar.getCurrentDay())) {
                    holder.tvDay.setBackgroundResource(R.drawable.bg_day_selected);
                    holder.tvDay.setTextColor(activity.getColor(R.color.colorWhite));
                }else {
                    holder.tvDay.setBackgroundResource(R.drawable.bg_day_unselected);
                    holder.tvDay.setTextColor(activity.getColor(R.color.colorMainText));
                }
            }

            holder.setCalendarClickListener((day, pos) -> {

                if (itemSelected != null) {
                    itemSelected.setBackgroundResource(R.drawable.bg_day_unselected);
                    itemSelected.setTextColor(activity.getColor(R.color.colorMainText));
                }

                holder.tvDay.setBackgroundResource(R.drawable.bg_day_selected);
                holder.tvDay.setTextColor(activity.getColor(R.color.colorWhite));
                itemSelected = holder.tvDay;

                if (!day.equals("")) {
                    Log.d(TAG, "Selected Date: " + calendar.getCurrentDate());
                    String message = "Selected Date " + day + " - " + calendar.getCurrentDate();
//                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                    selectedListener.onDateSelected(calendar, position);
                }

            });
        }

    }

    @Override
    public int getItemCount() {
        return listDays.size();
    }

    static class CalendarHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_cell_day)
        TextView tvDay;

        private CalendarClickListener calendarClickListener;

        public CalendarHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void setCalendarClickListener(CalendarClickListener calendarClickListener) {
            this.calendarClickListener = calendarClickListener;
        }

        @Override
        public void onClick(View view) {
            calendarClickListener.onItemClick(tvDay.getText().toString(), getAdapterPosition());
        }
    }
}
