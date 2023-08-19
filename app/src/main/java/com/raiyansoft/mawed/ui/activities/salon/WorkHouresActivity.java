package com.raiyansoft.mawed.ui.activities.salon;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.adapter.salon.WorkHouresAdapter;
import com.raiyansoft.mawed.listener.WorkHoursListener;
import com.raiyansoft.mawed.model.salon.workHour.SaveWorkHours;
import com.raiyansoft.mawed.model.salon.workHour.SendWorkHours;
import com.raiyansoft.mawed.model.salon.workHour.WorkHour;
import com.raiyansoft.mawed.model.salon.workHour.WorkHourData;
import com.raiyansoft.mawed.model.salon.workHour.WorkHoursStatus;
import com.raiyansoft.mawed.utils.HelperMethods;
import com.google.gson.Gson;
import com.jaredrummler.materialspinner.MaterialSpinner;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkHouresActivity extends AppCompatActivity implements WorkHoursListener {
    private static final String TAG = WorkHouresActivity.class.getSimpleName();

    @BindView(R.id.pb_loading)
    ProgressBar loading;
    @BindView(R.id.recycler_work_hours)
    RecyclerView recyclerWorkHours;

    // TODO Saturday
    @BindView(R.id.tv_saturday)
    TextView tvSaturday;
    @BindView(R.id.spinner_saturday_status)
    MaterialSpinner spinnerSaturdayStatus;
    @BindView(R.id.iv_saturday_arrow_hour)
    ImageView ivSaturdayArrowHour;
    @BindView(R.id.expandable_saturday_layout_hour)
    ExpandableLayout expandableLayoutSaturday;
    @BindView(R.id.ll_saturday_start_time)
    LinearLayout llSaturdayStartTime;
    @BindView(R.id.tv_saturday_start_time_1)
    TextView tvSaturdayStartTime1;
    @BindView(R.id.tv_saturday_end_time_1)
    TextView tvSaturdayEndTime1;
    @BindView(R.id.ll_saturday_end_time)
    LinearLayout llSaturdayEndTime;
    @BindView(R.id.tv_saturday_start_time_2)
    TextView tvSaturdayStartTime2;
    @BindView(R.id.tv_saturday_end_time_2)
    TextView tvSaturdayEndTime2;

    // TODO Sunday
    @BindView(R.id.tv_sunday)
    TextView tvSunday;
    @BindView(R.id.spinner_sunday_status)
    MaterialSpinner spinnerSundayStatus;
    @BindView(R.id.iv_sunday_arrow_hour)
    ImageView ivSundayArrowHour;
    @BindView(R.id.expandable_sunday_layout_hour)
    ExpandableLayout expandableLayoutSunday;
    @BindView(R.id.ll_sunday_start_time)
    LinearLayout llSundayStartTime;
    @BindView(R.id.tv_sunday_start_time_1)
    TextView tvSundayStartTime1;
    @BindView(R.id.tv_sunday_end_time_1)
    TextView tvSundayEndTime1;
    @BindView(R.id.ll_sunday_end_time)
    LinearLayout llSundayEndTime;
    @BindView(R.id.tv_sunday_start_time_2)
    TextView tvSundayStartTime2;
    @BindView(R.id.tv_sunday_end_time_2)
    TextView tvSundayEndTime2;

    // TODO Monday
    @BindView(R.id.tv_monday)
    TextView tvMonday;
    @BindView(R.id.spinner_monday_status)
    MaterialSpinner spinnerMondayStatus;
    @BindView(R.id.iv_monday_arrow_hour)
    ImageView ivMondayArrowHour;
    @BindView(R.id.expandable_monday_layout_hour)
    ExpandableLayout expandableLayoutMonday;
    @BindView(R.id.ll_monday_start_time)
    LinearLayout llMondayStartTime;
    @BindView(R.id.tv_monday_start_time_1)
    TextView tvMondayStartTime1;
    @BindView(R.id.tv_monday_end_time_1)
    TextView tvMondayEndTime1;
    @BindView(R.id.ll_monday_end_time)
    LinearLayout llMondayEndTime;
    @BindView(R.id.tv_monday_start_time_2)
    TextView tvMondayStartTime2;
    @BindView(R.id.tv_monday_end_time_2)
    TextView tvMondayEndTime2;

    // TODO Tuesday
    @BindView(R.id.tv_tuesday)
    TextView tvTuesday;
    @BindView(R.id.spinner_tuesday_status)
    MaterialSpinner spinnerTuesdayStatus;
    @BindView(R.id.iv_tuesday_arrow_hour)
    ImageView ivTuesdayArrowHour;
    @BindView(R.id.expandable_tuesday_layout_hour)
    ExpandableLayout expandableLayoutTuesday;
    @BindView(R.id.ll_tuesday_start_time)
    LinearLayout llTuesdayStartTime;
    @BindView(R.id.tv_tuesday_start_time_1)
    TextView tvTuesdayStartTime1;
    @BindView(R.id.tv_tuesday_end_time_1)
    TextView tvTuesdayEndTime1;
    @BindView(R.id.ll_tuesday_end_time)
    LinearLayout llTuesdayEndTime;
    @BindView(R.id.tv_tuesday_start_time_2)
    TextView tvTuesdayStartTime2;
    @BindView(R.id.tv_tuesday_end_time_2)
    TextView tvTuesdayEndTime2;

    // TODO Wednesday
    @BindView(R.id.tv_wednesday)
    TextView tvWednesday;
    @BindView(R.id.spinner_wednesday_status)
    MaterialSpinner spinnerWednesdayStatus;
    @BindView(R.id.iv_wednesday_arrow_hour)
    ImageView ivWednesdayArrowHour;
    @BindView(R.id.expandable_wednesday_layout_hour)
    ExpandableLayout expandableLayoutWednesday;
    @BindView(R.id.ll_wednesday_start_time)
    LinearLayout llWednesdayStartTime;
    @BindView(R.id.tv_wednesday_start_time_1)
    TextView tvWednesdayStartTime1;
    @BindView(R.id.tv_wednesday_end_time_1)
    TextView tvWednesdayEndTime1;
    @BindView(R.id.ll_wednesday_end_time)
    LinearLayout llWednesdayEndTime;
    @BindView(R.id.tv_wednesday_start_time_2)
    TextView tvWednesdayStartTime2;
    @BindView(R.id.tv_wednesday_end_time_2)
    TextView tvWednesdayEndTime2;

    // TODO Thursday
    @BindView(R.id.tv_thursday)
    TextView tvThursday;
    @BindView(R.id.spinner_thursday_status)
    MaterialSpinner spinnerThursdayStatus;
    @BindView(R.id.iv_thursday_arrow_hour)
    ImageView ivThursdayArrowHour;
    @BindView(R.id.expandable_thursday_layout_hour)
    ExpandableLayout expandableLayoutThursday;
    @BindView(R.id.ll_thursday_start_time)
    LinearLayout llThursdayStartTime;
    @BindView(R.id.tv_thursday_start_time_1)
    TextView tvThursdayStartTime1;
    @BindView(R.id.tv_thursday_end_time_1)
    TextView tvThursdayEndTime1;
    @BindView(R.id.ll_thursday_end_time)
    LinearLayout llThursdayEndTime;
    @BindView(R.id.tv_thursday_start_time_2)
    TextView tvThursdayStartTime2;
    @BindView(R.id.tv_thursday_end_time_2)
    TextView tvThursdayEndTime2;

    // TODO Friday
    @BindView(R.id.tv_friday)
    TextView tvFriday;
    @BindView(R.id.spinner_friday_status)
    MaterialSpinner spinnerFridayStatus;
    @BindView(R.id.iv_friday_arrow_hour)
    ImageView ivFridayArrowHour;
    @BindView(R.id.expandable_friday_layout_hour)
    ExpandableLayout expandableLayoutFriday;
    @BindView(R.id.ll_friday_start_time)
    LinearLayout llFridayStartTime;
    @BindView(R.id.tv_friday_start_time_1)
    TextView tvFridayStartTime1;
    @BindView(R.id.tv_friday_end_time_1)
    TextView tvFridayEndTime1;
    @BindView(R.id.ll_friday_end_time)
    LinearLayout llFridayEndTime;
    @BindView(R.id.tv_friday_start_time_2)
    TextView tvFridayStartTime2;
    @BindView(R.id.tv_friday_end_time_2)
    TextView tvFridayEndTime2;

    // TODO PERMISSOINS
    @BindView(R.id.spinner_permissions)
    MaterialSpinner spinnerPermissions;

    private List<WorkHourData> listWorkHours;
    private List<WorkHoursStatus> listSaturdayStatus;
    private List<WorkHoursStatus> listSundayStatus;
    private List<WorkHoursStatus> listMondayStatus;
    private List<WorkHoursStatus> listTuesdayStatus;
    private List<WorkHoursStatus> listWednesdayStatus;
    private List<WorkHoursStatus> listThursdayStatus;
    private List<WorkHoursStatus> listFridayStatus;
    private List<SendWorkHours> listSendWorkHours;
    private WorkHouresAdapter workHouresAdapter;

    private String statusSaturdayName;
    private String statusSundayName;
    private String statusMondayName;
    private String statusTuesdayName;
    private String statusWednesdayName;
    private String statusThursdayName;
    private String statusFridayName;
    private Integer statusSaturdayType = 1;
    private Integer statusSundayType = 1;
    private Integer statusMondayType = 1;
    private Integer statusTuesdayType = 1;
    private Integer statusWednesdayType = 1;
    private Integer statusThursdayType = 1;

    private Integer statusFridayType = 1;

    private String permissions;

    SendWorkHours saturdayWorkHours = new SendWorkHours();
    SendWorkHours sundayWorkHours = new SendWorkHours();
    SendWorkHours mondayWorkHours = new SendWorkHours();
    SendWorkHours tuesdayWorkHours = new SendWorkHours();
    SendWorkHours wednesdayWorkHours = new SendWorkHours();
    SendWorkHours thursdayWorkHours = new SendWorkHours();
    SendWorkHours fridayWorkHours = new SendWorkHours();

    public WorkHouresActivity() {
    }

    @OnClick(R.id.iv_back)
    void onBackClick() {
        onBackPressed();
    }

    @OnClick(R.id.btn_back)
    void onBtnBackClick() {
        onBackPressed();
    }

    @OnClick(R.id.ll_saturday_work_hour)
    void onSaturdayClick() {
        if (statusSaturdayType == 1) {
            expandableLayoutSaturday.setExpanded(false);
        } else {
            if (expandableLayoutSaturday.isExpanded())
                ivSaturdayArrowHour.setBackgroundResource(R.drawable.ic_icon_arrow_down);
            else
                ivSaturdayArrowHour.setBackgroundResource(R.drawable.ic_icon_arrow_up);
            expandableLayoutSaturday.toggle();
        }
    }

    @OnClick(R.id.ll_sunday_work_hour)
    void onSundayClick() {
        if (statusSundayType == 1) {
            expandableLayoutSunday.setExpanded(false);
        } else {
            if (expandableLayoutSunday.isExpanded())
                ivSundayArrowHour.setBackgroundResource(R.drawable.ic_icon_arrow_down);
            else
                ivSundayArrowHour.setBackgroundResource(R.drawable.ic_icon_arrow_up);
            expandableLayoutSunday.toggle();
        }
    }

    @OnClick(R.id.ll_monday_work_hour)
    void onMondayClick() {
        if (statusMondayType == 1) {
            expandableLayoutMonday.setExpanded(false);
        } else {
            if (expandableLayoutMonday.isExpanded())
                ivMondayArrowHour.setBackgroundResource(R.drawable.ic_icon_arrow_down);
            else
                ivMondayArrowHour.setBackgroundResource(R.drawable.ic_icon_arrow_up);
            expandableLayoutMonday.toggle();
        }
    }

    @OnClick(R.id.ll_tuesday_work_hour)
    void onTuesdayClick() {
        if (statusTuesdayType == 1) {
            expandableLayoutTuesday.setExpanded(false);
        } else {
            if (expandableLayoutTuesday.isExpanded())
                ivTuesdayArrowHour.setBackgroundResource(R.drawable.ic_icon_arrow_down);
            else
                ivTuesdayArrowHour.setBackgroundResource(R.drawable.ic_icon_arrow_up);
            expandableLayoutTuesday.toggle();
        }
    }

    @OnClick(R.id.ll_wednesday_work_hour)
    void onWednesdayClick() {
        if (statusWednesdayType == 1) {
            expandableLayoutWednesday.setExpanded(false);
        } else {
            if (expandableLayoutWednesday.isExpanded())
                ivWednesdayArrowHour.setBackgroundResource(R.drawable.ic_icon_arrow_down);
            else
                ivWednesdayArrowHour.setBackgroundResource(R.drawable.ic_icon_arrow_up);
            expandableLayoutWednesday.toggle();
        }
    }

    @OnClick(R.id.ll_thursday_work_hour)
    void onThursdayClick() {
        if (statusThursdayType == 1) {
            expandableLayoutThursday.setExpanded(false);
        } else {
            if (expandableLayoutThursday.isExpanded())
                ivThursdayArrowHour.setBackgroundResource(R.drawable.ic_icon_arrow_down);
            else
                ivThursdayArrowHour.setBackgroundResource(R.drawable.ic_icon_arrow_up);
            expandableLayoutThursday.toggle();
        }
    }

    @OnClick(R.id.ll_friday_work_hour)
    void onFridayClick() {
        if (statusFridayType == 1) {
            expandableLayoutFriday.setExpanded(false);
        } else {
            if (expandableLayoutFriday.isExpanded())
                ivFridayArrowHour.setBackgroundResource(R.drawable.ic_icon_arrow_down);
            else
                ivFridayArrowHour.setBackgroundResource(R.drawable.ic_icon_arrow_up);
            expandableLayoutFriday.toggle();
        }
    }

    // TODO SATUDAY
    // TODO SATUDAY START 1
    @OnClick(R.id.tv_saturday_start_time_1)
    void onSaturdayStartTime1Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvSaturdayStartTime1);
    }

    // TODO SATUDAY END 1
    @OnClick(R.id.tv_saturday_end_time_1)
    void onSaturdayEndTime1Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvSaturdayEndTime1);
    }

    // TODO SATUDAY START 2
    @OnClick(R.id.tv_saturday_start_time_2)
    void onSaturdayStartTime2Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvSaturdayStartTime2);
    }

    // TODO SATUDAY END 2
    @OnClick(R.id.tv_saturday_end_time_2)
    void onSaturdayEndTime2Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvSaturdayEndTime2);
    }

    // TODO SUNDAY
    // TODO SUNDAY START 1
    @OnClick(R.id.tv_sunday_start_time_1)
    void onSundayStartTime1Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvSundayStartTime1);
    }

    // TODO SUNDAY END 1
    @OnClick(R.id.tv_sunday_end_time_1)
    void onSundayEndTime1Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvSundayEndTime1);
    }

    // TODO SUNDAY START 2
    @OnClick(R.id.tv_sunday_start_time_2)
    void onSundayStartTime2Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvSundayStartTime2);
    }

    // TODO SUNDAY END 2
    @OnClick(R.id.tv_sunday_end_time_2)
    void onSundayEndTime2Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvSundayEndTime2);
    }


    // TODO MONDAY
    // TODO MONDAY START 1
    @OnClick(R.id.tv_monday_start_time_1)
    void onMondayStartTime1Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvMondayStartTime1);
    }

    // TODO MONDAY END 1
    @OnClick(R.id.tv_monday_end_time_1)
    void onMondayEndTime1Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvMondayEndTime1);
    }

    // TODO MONDAY START 2
    @OnClick(R.id.tv_monday_start_time_2)
    void onMondayStartTime2Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvMondayStartTime2);
    }

    // TODO MONDAY END 2
    @OnClick(R.id.tv_monday_end_time_2)
    void onMondayEndTime2Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvMondayEndTime2);
    }


    // TODO Tuesday
    // TODO Tuesday START 1
    @OnClick(R.id.tv_tuesday_start_time_1)
    void onTuesdayStartTime1Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvTuesdayStartTime1);
    }

    // TODO Tuesday END 1
    @OnClick(R.id.tv_tuesday_end_time_1)
    void onTuesdayEndTime1Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvTuesdayEndTime1);
    }

    // TODO Tuesday START 2
    @OnClick(R.id.tv_tuesday_start_time_2)
    void onTuesdayStartTime2Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvTuesdayStartTime2);
    }

    // TODO Tuesday END 2
    @OnClick(R.id.tv_tuesday_end_time_2)
    void onTuesdayEndTime2Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvTuesdayEndTime2);
    }

    // TODO Wednesday
    // TODO Wednesday START 1
    @OnClick(R.id.tv_wednesday_start_time_1)
    void onWednesdayStartTime1Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvWednesdayStartTime1);
    }

    // TODO Wednesday END 1
    @OnClick(R.id.tv_wednesday_end_time_1)
    void onWednesdayEndTime1Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvWednesdayEndTime1);
    }

    // TODO Wednesday START 2
    @OnClick(R.id.tv_wednesday_start_time_2)
    void onWednesdayStartTime2Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvWednesdayStartTime2);
    }

    // TODO Wednesday END 2
    @OnClick(R.id.tv_wednesday_end_time_2)
    void onWednesdayEndTime2Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvWednesdayEndTime2);
    }


    // TODO Thursday
    // TODO Thursday START 1
    @OnClick(R.id.tv_thursday_start_time_1)
    void onThursdayStartTime1Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvThursdayStartTime1);
    }

    // TODO Thursday END 1
    @OnClick(R.id.tv_thursday_end_time_1)
    void onThursdayEndTime1Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvThursdayEndTime1);
    }

    // TODO Thursday START 2
    @OnClick(R.id.tv_thursday_start_time_2)
    void onThursdayStartTime2Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvThursdayStartTime2);
    }

    // TODO Thursday END 2
    @OnClick(R.id.tv_thursday_end_time_2)
    void onThursdayEndTime2Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvThursdayEndTime2);
    }

    // TODO Friday
    // TODO Friday START 1
    @OnClick(R.id.tv_friday_start_time_1)
    void onFridayStartTime1Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvFridayStartTime1);
    }

    // TODO Friday END 1
    @OnClick(R.id.tv_friday_end_time_1)
    void onFridayEndTime1Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvFridayEndTime1);
    }

    // TODO Friday START 2
    @OnClick(R.id.tv_friday_start_time_2)
    void onFridayStartTime2Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvFridayStartTime2);
    }

    // TODO Friday END 2
    @OnClick(R.id.tv_friday_end_time_2)
    void onFridayEndTime2Click() {
        HelperMethods.showTimeDialog(this, "HH:mm", tvFridayEndTime2);
    }

    @OnClick(R.id.btn_save)
    void onSaveClick() {

        loading.setVisibility(View.VISIBLE);

//        Log.d(TAG, "onSaveClick: " + new Gson().toJson(workHouresAdapter.listSendWorkHours));
        String saturday = tvSaturday.getText().toString();
        String sunday = tvSunday.getText().toString();
        String monday = tvMonday.getText().toString();
        String tuesday = tvTuesday.getText().toString();
        String wednesday = tvWednesday.getText().toString();
        String thursday = tvThursday.getText().toString();
        String friday = tvFriday.getText().toString();

        // TODO Saturday
        String saturdayStartTime1 = tvSaturdayStartTime1.getText().toString();
        String saturdayEndTime1 = tvSaturdayEndTime1.getText().toString();
        String saturdayStartTime2 = tvSaturdayStartTime1.getText().toString();
        String saturdayEndTime2 = tvSaturdayEndTime2.getText().toString();

        // TODO Sunday
        String sundayStartTime1 = tvSundayStartTime1.getText().toString();
        String sundayEndTime1 = tvSundayEndTime1.getText().toString();
        String sundayStartTime2 = tvSundayStartTime1.getText().toString();
        String sundayEndTime2 = tvSundayEndTime2.getText().toString();

        // TODO Monday
        String mondayStartTime1 = tvMondayStartTime1.getText().toString();
        String mondayEndTime1 = tvMondayEndTime1.getText().toString();
        String mondayStartTime2 = tvMondayStartTime1.getText().toString();
        String mondayEndTime2 = tvMondayEndTime2.getText().toString();

        // TODO Tuesday
        String tuesdayStartTime1 = tvTuesdayStartTime1.getText().toString();
        String tuesdayEndTime1 = tvTuesdayEndTime1.getText().toString();
        String tuesdayStartTime2 = tvTuesdayStartTime1.getText().toString();
        String tuesdayEndTime2 = tvTuesdayEndTime2.getText().toString();

        // TODO Wednesday
        String wednesdayStartTime1 = tvWednesdayStartTime1.getText().toString();
        String wednesdayEndTime1 = tvWednesdayEndTime1.getText().toString();
        String wednesdayStartTime2 = tvWednesdayStartTime1.getText().toString();
        String wednesdayEndTime2 = tvWednesdayEndTime2.getText().toString();

        // TODO Thursday
        String thursdayStartTime1 = tvThursdayStartTime1.getText().toString();
        String thursdayEndTime1 = tvThursdayEndTime1.getText().toString();
        String thursdayStartTime2 = tvThursdayStartTime1.getText().toString();
        String thursdayEndTime2 = tvThursdayEndTime2.getText().toString();

        // TODO Friday
        String fridayStartTime1 = tvFridayStartTime1.getText().toString();
        String fridayEndTime1 = tvFridayEndTime1.getText().toString();
        String fridayStartTime2 = tvFridayStartTime1.getText().toString();
        String fridayEndTime2 = tvFridayEndTime2.getText().toString();

        saturdayWorkHours.setType(statusSaturdayType);
        saturdayWorkHours.setValidity(permissions);
        saturdayWorkHours.setDay(saturday);
        saturdayWorkHours.setVendorId(HelperMethods.getCurrentUser(this).getUserId());
        saturdayWorkHours.setStartTime1(saturdayStartTime1);
        saturdayWorkHours.setEndTime1(saturdayEndTime1);
        saturdayWorkHours.setStartTime2(saturdayStartTime2);
        saturdayWorkHours.setEndTime2(saturdayEndTime2);

        sundayWorkHours.setType(statusSundayType);
        sundayWorkHours.setValidity(permissions);
        sundayWorkHours.setDay(sunday);
        sundayWorkHours.setVendorId(HelperMethods.getCurrentUser(this).getUserId());
        sundayWorkHours.setStartTime1(sundayStartTime1);
        sundayWorkHours.setEndTime1(sundayEndTime1);
        sundayWorkHours.setStartTime2(sundayStartTime2);
        sundayWorkHours.setEndTime2(sundayEndTime2);

        mondayWorkHours.setType(statusMondayType);
        mondayWorkHours.setValidity(permissions);
        mondayWorkHours.setDay(monday);
        mondayWorkHours.setVendorId(HelperMethods.getCurrentUser(this).getUserId());
        mondayWorkHours.setStartTime1(mondayStartTime1);
        mondayWorkHours.setEndTime1(mondayEndTime1);
        mondayWorkHours.setStartTime2(mondayStartTime2);
        mondayWorkHours.setEndTime2(mondayEndTime2);

        tuesdayWorkHours.setType(statusTuesdayType);
        tuesdayWorkHours.setValidity(permissions);
        tuesdayWorkHours.setDay(tuesday);
        tuesdayWorkHours.setVendorId(HelperMethods.getCurrentUser(this).getUserId());
        tuesdayWorkHours.setStartTime1(tuesdayStartTime1);
        tuesdayWorkHours.setEndTime1(tuesdayEndTime1);
        tuesdayWorkHours.setStartTime2(tuesdayStartTime2);
        tuesdayWorkHours.setEndTime2(tuesdayEndTime2);

        wednesdayWorkHours.setType(statusWednesdayType);
        wednesdayWorkHours.setValidity(permissions);
        wednesdayWorkHours.setDay(wednesday);
        wednesdayWorkHours.setVendorId(HelperMethods.getCurrentUser(this).getUserId());
        wednesdayWorkHours.setStartTime1(wednesdayStartTime1);
        wednesdayWorkHours.setEndTime1(wednesdayEndTime1);
        wednesdayWorkHours.setStartTime2(wednesdayStartTime2);
        wednesdayWorkHours.setEndTime2(wednesdayEndTime2);

        thursdayWorkHours.setType(statusThursdayType);
        thursdayWorkHours.setValidity(permissions);
        thursdayWorkHours.setDay(thursday);
        thursdayWorkHours.setVendorId(HelperMethods.getCurrentUser(this).getUserId());
        thursdayWorkHours.setStartTime1(thursdayStartTime1);
        thursdayWorkHours.setEndTime1(thursdayEndTime1);
        thursdayWorkHours.setStartTime2(thursdayStartTime2);
        thursdayWorkHours.setEndTime2(thursdayEndTime2);

        fridayWorkHours.setType(statusFridayType);
        fridayWorkHours.setValidity(permissions);
        fridayWorkHours.setDay(friday);
        fridayWorkHours.setVendorId(HelperMethods.getCurrentUser(this).getUserId());
        fridayWorkHours.setStartTime1(fridayStartTime1);
        fridayWorkHours.setEndTime1(fridayEndTime1);
        fridayWorkHours.setStartTime2(fridayStartTime2);
        fridayWorkHours.setEndTime2(fridayEndTime2);

        listSendWorkHours.add(saturdayWorkHours);
        listSendWorkHours.add(sundayWorkHours);
        listSendWorkHours.add(mondayWorkHours);
        listSendWorkHours.add(tuesdayWorkHours);
        listSendWorkHours.add(wednesdayWorkHours);
        listSendWorkHours.add(thursdayWorkHours);
        listSendWorkHours.add(fridayWorkHours);

        Log.d(TAG, "onSaveClick: " + new Gson().toJson(listSendWorkHours));
        Log.d(TAG, "onSaveClick: " + listSendWorkHours.size());

        Map<String, Object> fields = new HashMap<>();
        for (int i = 0; i < listSendWorkHours.size(); i++) {
            SendWorkHours workHours = listSendWorkHours.get(i);
            fields.put("work_hours[" + i + "][type]", workHours.getType());
            fields.put("work_hours[" + i + "][day]", workHours.getDay());
            fields.put("work_hours[" + i + "][vendor_id]", workHours.getVendorId());
            fields.put("work_hours[" + i + "][validity]", workHours.getValidity());
            fields.put("work_hours[" + i + "][start_time_1]", workHours.getStartTime1());
            fields.put("work_hours[" + i + "][end_time_1]", workHours.getEndTime1());
            fields.put("work_hours[" + i + "][start_time_2]", workHours.getStartTime2());
            fields.put("work_hours[" + i + "][end_time_2]", workHours.getEndTime2());
        }

        HelperMethods.getMawedAPI()
                .saveWorkHours(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this),
                        fields)
                .enqueue(new Callback<SaveWorkHours>() {
                    @Override
                    public void onResponse(Call<SaveWorkHours> call, Response<SaveWorkHours> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()){
                            if (response.body().isStatus()){
                                finish();
//                                HelperMethods.showCustomToast(WorkHouresActivity.this, response.body().getMessage(), true);
                            }else {
                                HelperMethods.showCustomToast(WorkHouresActivity.this, response.body().getMessage(), false);
                                Log.e(TAG, "onResponse: " + response.body().getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SaveWorkHours> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                        loading.setVisibility(View.GONE);
                    }
                });

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_houres_work);
        ButterKnife.bind(this);
        initUI();
        getSalonWorkHours();
    }

    private void initUI() {

        listWorkHours = new ArrayList<>();
        listSaturdayStatus = new ArrayList<>();
        listSundayStatus = new ArrayList<>();
        listMondayStatus = new ArrayList<>();
        listTuesdayStatus = new ArrayList<>();
        listWednesdayStatus = new ArrayList<>();
        listThursdayStatus = new ArrayList<>();
        listFridayStatus = new ArrayList<>();
        listSendWorkHours = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerWorkHours.setHasFixedSize(true);
        recyclerWorkHours.setLayoutManager(layoutManager);

        ivSaturdayArrowHour.setBackgroundResource(R.drawable.ic_icon_arrow_down);
        ivSundayArrowHour.setBackgroundResource(R.drawable.ic_icon_arrow_down);
        ivMondayArrowHour.setBackgroundResource(R.drawable.ic_icon_arrow_down);
        ivTuesdayArrowHour.setBackgroundResource(R.drawable.ic_icon_arrow_down);
        ivWednesdayArrowHour.setBackgroundResource(R.drawable.ic_icon_arrow_down);
        ivThursdayArrowHour.setBackgroundResource(R.drawable.ic_icon_arrow_down);
        ivFridayArrowHour.setBackgroundResource(R.drawable.ic_icon_arrow_down);

        setupSaturdayHoursStatus(spinnerSaturdayStatus);
        setupSundayHoursStatus(spinnerSundayStatus);
        setupMondayHoursStatus(spinnerMondayStatus);
        setupTuesdayHoursStatus(spinnerTuesdayStatus);
        setupWednesdayHoursStatus(spinnerWednesdayStatus);
        setupThursdayHoursStatus(spinnerThursdayStatus);
        setupFridayHoursStatus(spinnerFridayStatus);
        setupPermissions();

    }

    private void setupPermissions() {

        List<String> listPermissions = new ArrayList<>();
        listPermissions.add(getString(R.string.month));
        listPermissions.add(getString(R.string.three_month));
        listPermissions.add(getString(R.string.six_month));
        listPermissions.add(getString(R.string.year));
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listPermissions);
        spinnerPermissions.setAdapter(arrayAdapter);

        permissions = listPermissions.get(0);
        spinnerPermissions.setOnItemSelectedListener((view, position, id, item) -> {
            permissions = item.toString();
            Log.d(TAG, "setupePermissions: " + permissions);
//            workHours.setValidity(permissions);
//            listSendWorkHours.add(workHours);
        });
    }

    private void setupSaturdayHoursStatus(MaterialSpinner spinner) {

        final String status1 = "";

        listSaturdayStatus.add(new WorkHoursStatus(1, getString(R.string.closed)));
        listSaturdayStatus.add(new WorkHoursStatus(2, getString(R.string.hours24)));
        listSaturdayStatus.add(new WorkHoursStatus(3, getString(R.string.one_time)));
        listSaturdayStatus.add(new WorkHoursStatus(4, getString(R.string.two_shifts)));


        List<String> names = new ArrayList<>();
        //        names.add(0, activity.getString(R.string.select_status));
        for (WorkHoursStatus status : listSaturdayStatus) {
            names.add(status.getName());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, names);
        spinner.setAdapter(arrayAdapter);

        spinnerSaturdayStatus.setOnItemSelectedListener((view, pos, id, item) -> {

            statusSaturdayName = item.toString();
            statusSaturdayType = listSaturdayStatus.get(pos).getType();

            Log.d(TAG, "setupHoursStatus: " + statusSaturdayName);
            Log.d(TAG, "setupHoursStatus: " + statusSaturdayType);

            if (item.equals(getString(R.string.closed))) {
                spinnerSaturdayStatus.setTextColor(getColor(R.color.colorDarkRed));
                expandableLayoutSaturday.setExpanded(false);
            } else if (item.equals(getString(R.string.hours24))) {
                spinnerSaturdayStatus.setTextColor(getColor(R.color.colorGreen2));
                expandableLayoutSaturday.setExpanded(false);
//                llSaturdayStartTime.setVisibility(View.GONE);
//                llSaturdayEndTime.setVisibility(View.GONE);
            } else if (item.equals(getString(R.string.one_time))) {
                spinnerSaturdayStatus.setTextColor(getColor(R.color.colorPrimary));
                expandableLayoutSaturday.setExpanded(true);
                llSaturdayEndTime.setVisibility(View.GONE);
            } else if (item.equals(getString(R.string.two_shifts))) {
                spinnerSaturdayStatus.setTextColor(getColor(R.color.colorPrimary));
                expandableLayoutSaturday.setExpanded(true);
                llSaturdayStartTime.setVisibility(View.VISIBLE);
                llSaturdayEndTime.setVisibility(View.VISIBLE);
            }
//            workHours.setType(statusSaturdayType);
//            listSendWorkHours.add(workHours);
        });

    }

    private void setupSundayHoursStatus(MaterialSpinner spinner) {

        listSundayStatus.add(new WorkHoursStatus(1, getString(R.string.closed)));
        listSundayStatus.add(new WorkHoursStatus(2, getString(R.string.hours24)));
        listSundayStatus.add(new WorkHoursStatus(3, getString(R.string.one_time)));
        listSundayStatus.add(new WorkHoursStatus(4, getString(R.string.two_shifts)));


        List<String> names = new ArrayList<>();
        //        names.add(0, activity.getString(R.string.select_status));
        for (WorkHoursStatus status : listSundayStatus) {
            names.add(status.getName());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, names);
        spinner.setAdapter(arrayAdapter);

        spinnerSundayStatus.setOnItemSelectedListener((view, pos, id, item) -> {

            statusSundayName = item.toString();
            statusSundayType = listSundayStatus.get(pos).getType();

            Log.d(TAG, "setupHoursStatus: " + statusSundayName);
            Log.d(TAG, "setupHoursStatus: " + statusSundayType);

            if (item.equals(getString(R.string.closed))) {
                spinnerSundayStatus.setTextColor(getColor(R.color.colorDarkRed));
                expandableLayoutSunday.setExpanded(false);
            } else if (item.equals(getString(R.string.hours24))) {
                spinnerSundayStatus.setTextColor(getColor(R.color.colorGreen2));
                expandableLayoutSaturday.setExpanded(false);
//                llSaturdayStartTime.setVisibility(View.GONE);
//                llSaturdayEndTime.setVisibility(View.GONE);
            } else if (item.equals(getString(R.string.one_time))) {
                spinnerSundayStatus.setTextColor(getColor(R.color.colorPrimary));
                expandableLayoutSunday.setExpanded(true);
                llSundayEndTime.setVisibility(View.GONE);
            } else if (item.equals(getString(R.string.two_shifts))) {
                spinnerSundayStatus.setTextColor(getColor(R.color.colorPrimary));
                expandableLayoutSunday.setExpanded(true);
                llSundayStartTime.setVisibility(View.VISIBLE);
                llSundayEndTime.setVisibility(View.VISIBLE);
            }
        });

    }

    private void setupMondayHoursStatus(MaterialSpinner spinner) {

        listMondayStatus.add(new WorkHoursStatus(1, getString(R.string.closed)));
        listMondayStatus.add(new WorkHoursStatus(2, getString(R.string.hours24)));
        listMondayStatus.add(new WorkHoursStatus(3, getString(R.string.one_time)));
        listMondayStatus.add(new WorkHoursStatus(4, getString(R.string.two_shifts)));


        List<String> names = new ArrayList<>();
        //        names.add(0, activity.getString(R.string.select_status));
        for (WorkHoursStatus status : listMondayStatus) {
            names.add(status.getName());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, names);
        spinner.setAdapter(arrayAdapter);

        spinnerMondayStatus.setOnItemSelectedListener((view, pos, id, item) -> {

            statusMondayName = item.toString();
            statusMondayType = listMondayStatus.get(pos).getType();

            Log.d(TAG, "setupHoursStatus: " + statusMondayName);
            Log.d(TAG, "setupHoursStatus: " + statusMondayType);

            if (item.equals(getString(R.string.closed))) {
                spinnerMondayStatus.setTextColor(getColor(R.color.colorDarkRed));
                expandableLayoutMonday.setExpanded(false);
            } else if (item.equals(getString(R.string.hours24))) {
                spinnerMondayStatus.setTextColor(getColor(R.color.colorGreen2));
                expandableLayoutSaturday.setExpanded(false);
//                llSaturdayStartTime.setVisibility(View.GONE);
//                llSaturdayEndTime.setVisibility(View.GONE);
            } else if (item.equals(getString(R.string.one_time))) {
                spinnerMondayStatus.setTextColor(getColor(R.color.colorPrimary));
                expandableLayoutMonday.setExpanded(true);
                llMondayEndTime.setVisibility(View.GONE);
            } else if (item.equals(getString(R.string.two_shifts))) {
                spinnerMondayStatus.setTextColor(getColor(R.color.colorPrimary));
                expandableLayoutMonday.setExpanded(true);
                llMondayStartTime.setVisibility(View.VISIBLE);
                llMondayEndTime.setVisibility(View.VISIBLE);
            }
        });

    }

    private void setupTuesdayHoursStatus(MaterialSpinner spinner) {

        listTuesdayStatus.add(new WorkHoursStatus(1, getString(R.string.closed)));
        listTuesdayStatus.add(new WorkHoursStatus(2, getString(R.string.hours24)));
        listTuesdayStatus.add(new WorkHoursStatus(3, getString(R.string.one_time)));
        listTuesdayStatus.add(new WorkHoursStatus(4, getString(R.string.two_shifts)));


        List<String> names = new ArrayList<>();
        //        names.add(0, activity.getString(R.string.select_status));
        for (WorkHoursStatus status : listTuesdayStatus) {
            names.add(status.getName());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, names);
        spinner.setAdapter(arrayAdapter);

        spinnerTuesdayStatus.setOnItemSelectedListener((view, pos, id, item) -> {

            statusTuesdayName = item.toString();
            statusTuesdayType = listTuesdayStatus.get(pos).getType();

            Log.d(TAG, "setupHoursStatus: " + statusTuesdayName);
            Log.d(TAG, "setupHoursStatus: " + statusTuesdayType);

            if (item.equals(getString(R.string.closed))) {
                spinnerTuesdayStatus.setTextColor(getColor(R.color.colorDarkRed));
                expandableLayoutTuesday.setExpanded(false);
            } else if (item.equals(getString(R.string.hours24))) {
                spinnerTuesdayStatus.setTextColor(getColor(R.color.colorGreen2));
                expandableLayoutSaturday.setExpanded(false);
//                llSaturdayStartTime.setVisibility(View.GONE);
//                llSaturdayEndTime.setVisibility(View.GONE);
            } else if (item.equals(getString(R.string.one_time))) {
                spinnerTuesdayStatus.setTextColor(getColor(R.color.colorPrimary));
                expandableLayoutTuesday.setExpanded(true);
                llTuesdayEndTime.setVisibility(View.GONE);
            } else if (item.equals(getString(R.string.two_shifts))) {
                spinnerTuesdayStatus.setTextColor(getColor(R.color.colorPrimary));
                expandableLayoutTuesday.setExpanded(true);
                llTuesdayStartTime.setVisibility(View.VISIBLE);
                llTuesdayEndTime.setVisibility(View.VISIBLE);
            }
        });

    }

    private void setupWednesdayHoursStatus(MaterialSpinner spinner) {

        listWednesdayStatus.add(new WorkHoursStatus(1, getString(R.string.closed)));
        listWednesdayStatus.add(new WorkHoursStatus(2, getString(R.string.hours24)));
        listWednesdayStatus.add(new WorkHoursStatus(3, getString(R.string.one_time)));
        listWednesdayStatus.add(new WorkHoursStatus(4, getString(R.string.two_shifts)));


        List<String> names = new ArrayList<>();
        //        names.add(0, activity.getString(R.string.select_status));
        for (WorkHoursStatus status : listWednesdayStatus) {
            names.add(status.getName());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, names);
        spinner.setAdapter(arrayAdapter);

        spinnerWednesdayStatus.setOnItemSelectedListener((view, pos, id, item) -> {

            statusWednesdayName = item.toString();
            statusWednesdayType = listWednesdayStatus.get(pos).getType();

            Log.d(TAG, "setupHoursStatus: " + statusWednesdayName);
            Log.d(TAG, "setupHoursStatus: " + statusWednesdayType);

            if (item.equals(getString(R.string.closed))) {
                spinnerWednesdayStatus.setTextColor(getColor(R.color.colorDarkRed));
                expandableLayoutWednesday.setExpanded(false);
            } else if (item.equals(getString(R.string.hours24))) {
                spinnerWednesdayStatus.setTextColor(getColor(R.color.colorGreen2));
                expandableLayoutSaturday.setExpanded(false);
//                llSaturdayStartTime.setVisibility(View.GONE);
//                llSaturdayEndTime.setVisibility(View.GONE);
            } else if (item.equals(getString(R.string.one_time))) {
                spinnerWednesdayStatus.setTextColor(getColor(R.color.colorPrimary));
                expandableLayoutWednesday.setExpanded(true);
                llWednesdayEndTime.setVisibility(View.GONE);
            } else if (item.equals(getString(R.string.two_shifts))) {
                spinnerWednesdayStatus.setTextColor(getColor(R.color.colorPrimary));
                expandableLayoutWednesday.setExpanded(true);
                llWednesdayStartTime.setVisibility(View.VISIBLE);
                llWednesdayEndTime.setVisibility(View.VISIBLE);
            }
        });

    }

    private void setupThursdayHoursStatus(MaterialSpinner spinner) {

        listThursdayStatus.add(new WorkHoursStatus(1, getString(R.string.closed)));
        listThursdayStatus.add(new WorkHoursStatus(2, getString(R.string.hours24)));
        listThursdayStatus.add(new WorkHoursStatus(3, getString(R.string.one_time)));
        listThursdayStatus.add(new WorkHoursStatus(4, getString(R.string.two_shifts)));


        List<String> names = new ArrayList<>();
        //        names.add(0, activity.getString(R.string.select_status));
        for (WorkHoursStatus status : listThursdayStatus) {
            names.add(status.getName());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, names);
        spinner.setAdapter(arrayAdapter);

        spinnerThursdayStatus.setOnItemSelectedListener((view, pos, id, item) -> {

            statusThursdayName = item.toString();
            statusThursdayType = listThursdayStatus.get(pos).getType();

            Log.d(TAG, "setupHoursStatus: " + statusThursdayName);
            Log.d(TAG, "setupHoursStatus: " + statusThursdayType);

            if (item.equals(getString(R.string.closed))) {
                spinnerThursdayStatus.setTextColor(getColor(R.color.colorDarkRed));
                expandableLayoutThursday.setExpanded(false);
            } else if (item.equals(getString(R.string.hours24))) {
                spinnerThursdayStatus.setTextColor(getColor(R.color.colorGreen2));
                expandableLayoutSaturday.setExpanded(false);
//                llSaturdayStartTime.setVisibility(View.GONE);
//                llSaturdayEndTime.setVisibility(View.GONE);
            } else if (item.equals(getString(R.string.one_time))) {
                spinnerThursdayStatus.setTextColor(getColor(R.color.colorPrimary));
                expandableLayoutThursday.setExpanded(true);
                llThursdayEndTime.setVisibility(View.GONE);
            } else if (item.equals(getString(R.string.two_shifts))) {
                spinnerThursdayStatus.setTextColor(getColor(R.color.colorPrimary));
                expandableLayoutThursday.setExpanded(true);
                llThursdayStartTime.setVisibility(View.VISIBLE);
                llThursdayEndTime.setVisibility(View.VISIBLE);
            }
        });

    }

    private void setupFridayHoursStatus(MaterialSpinner spinner) {

        listFridayStatus.add(new WorkHoursStatus(1, getString(R.string.closed)));
        listFridayStatus.add(new WorkHoursStatus(2, getString(R.string.hours24)));
        listFridayStatus.add(new WorkHoursStatus(3, getString(R.string.one_time)));
        listFridayStatus.add(new WorkHoursStatus(4, getString(R.string.two_shifts)));


        List<String> names = new ArrayList<>();
        //        names.add(0, activity.getString(R.string.select_status));
        for (WorkHoursStatus status : listFridayStatus) {
            names.add(status.getName());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, names);
        spinner.setAdapter(arrayAdapter);

        spinnerFridayStatus.setOnItemSelectedListener((view, pos, id, item) -> {

            statusFridayName = item.toString();
            statusFridayType = listFridayStatus.get(pos).getType();

            Log.d(TAG, "setupHoursStatus: " + statusFridayName);
            Log.d(TAG, "setupHoursStatus: " + statusFridayType);

            if (item.equals(getString(R.string.closed))) {
                spinnerFridayStatus.setTextColor(getColor(R.color.colorDarkRed));
                expandableLayoutFriday.setExpanded(false);
            } else if (item.equals(getString(R.string.hours24))) {
                spinnerFridayStatus.setTextColor(getColor(R.color.colorGreen2));
                expandableLayoutSaturday.setExpanded(false);
//                llSaturdayStartTime.setVisibility(View.GONE);
//                llSaturdayEndTime.setVisibility(View.GONE);
            } else if (item.equals(getString(R.string.one_time))) {
                spinnerFridayStatus.setTextColor(getColor(R.color.colorPrimary));
                expandableLayoutFriday.setExpanded(true);
                llFridayEndTime.setVisibility(View.GONE);
            } else if (item.equals(getString(R.string.two_shifts))) {
                spinnerFridayStatus.setTextColor(getColor(R.color.colorPrimary));
                expandableLayoutFriday.setExpanded(true);
                llFridayStartTime.setVisibility(View.VISIBLE);
                llFridayEndTime.setVisibility(View.VISIBLE);
            }
        });

    }

    private void getSalonWorkHours() {

        loading.setVisibility(View.VISIBLE);
        HelperMethods.getMawedAPI()
                .getWorkHour(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this))
                .enqueue(new Callback<WorkHour>() {
                    @Override
                    public void onResponse(Call<WorkHour> call, Response<WorkHour> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getData() != null) {
                                    listWorkHours.clear();
                                    listWorkHours.addAll(response.body().getData());

                                    if (!listWorkHours.isEmpty()) {

                                        for (WorkHourData hourData : listWorkHours){

//                                            SendWorkHours workHours = new SendWorkHours();
//                                            workHours.setDay(hourData.getDay());
//                                            workHours.setType(hourData.getType());
//                                            workHours.setVendorId(hourData.getVendorId());
//                                            workHours.setValidity(hourData.getValidity());
//                                            workHours.setStartTime1(hourData.getStartTime1());
//                                            workHours.setEndTime1(hourData.getEndTime1());
//                                            workHours.setStartTime2(hourData.getStartTime2());
//                                            workHours.setEndTime2(hourData.getEndTime2());
//                                            listSendWorkHours.add(workHours);

                                            if (hourData.getDay().equals(getString(R.string.saturday))){

                                                // TODO Saturday
                                                Log.d(TAG, "onResponse: " + hourData.getType());
                                                if (hourData.getType() == 1){
                                                    spinnerSaturdayStatus.setSelectedIndex(0);
                                                    spinnerSaturdayStatus.setTextColor(getColor(R.color.colorDarkRed));
                                                }else if(hourData.getType() == 2){
                                                    spinnerSaturdayStatus.setSelectedIndex(1);
                                                    spinnerSaturdayStatus.setTextColor(getColor(R.color.colorGreen2));
                                                }else if(hourData.getType() == 3){
                                                    spinnerSaturdayStatus.setSelectedIndex(2);
                                                    spinnerSaturdayStatus.setTextColor(getColor(R.color.colorPrimary));
                                                }else if(hourData.getType() == 4){
                                                    spinnerSaturdayStatus.setSelectedIndex(3);
                                                    spinnerSaturdayStatus.setTextColor(getColor(R.color.colorPrimary));
                                                }else{
                                                    spinnerSaturdayStatus.setTextColor(getColor(R.color.colorBlack));
                                                }

                                                tvSaturday.setText(hourData.getDay());
                                                if (hourData.getStartTime1() != null)
                                                    tvSaturdayStartTime1.setText(hourData.getStartTime1().substring(0,5));
                                                if (hourData.getEndTime1() != null)
                                                    tvSaturdayEndTime1.setText(hourData.getEndTime1().substring(0,5));
                                                if (hourData.getStartTime2() != null)
                                                    tvSaturdayStartTime2.setText(hourData.getStartTime2().substring(0,5));
                                                if (hourData.getEndTime2() != null)
                                                    tvSaturdayEndTime2.setText(hourData.getEndTime2().substring(0,5));
                                            }else if(hourData.getDay().equals(getString(R.string.sunday))){
                                                // TODO Sunday

                                                if (hourData.getType() == 1){
                                                    spinnerSundayStatus.setSelectedIndex(0);
                                                    spinnerSundayStatus.setTextColor(getColor(R.color.colorDarkRed));
                                                }else if(hourData.getType() == 2){
                                                    spinnerSundayStatus.setSelectedIndex(1);
                                                    spinnerSundayStatus.setTextColor(getColor(R.color.colorGreen2));
                                                }else if(hourData.getType() == 3){
                                                    spinnerSundayStatus.setSelectedIndex(2);
                                                    spinnerSundayStatus.setTextColor(getColor(R.color.colorPrimary));
                                                }else if(hourData.getType() == 4){
                                                    spinnerSundayStatus.setSelectedIndex(3);
                                                    spinnerSundayStatus.setTextColor(getColor(R.color.colorPrimary));
                                                }

                                                tvSunday.setText(hourData.getDay());
                                                if (hourData.getStartTime1() != null)
                                                    tvSundayStartTime1.setText(hourData.getStartTime1().substring(0,5));
                                                if (hourData.getEndTime1() != null)
                                                    tvSundayEndTime1.setText(hourData.getEndTime1().substring(0,5));
                                                if (hourData.getStartTime2() != null)
                                                    tvSundayStartTime2.setText(hourData.getStartTime2().substring(0,5));
                                                if (hourData.getEndTime2() != null)
                                                    tvSundayEndTime2.setText(hourData.getEndTime2().substring(0,5));
                                            }else if(hourData.getDay().equals(getString(R.string.monday))){
                                                // TODO Monday

                                                if (hourData.getType() == 1){
                                                    spinnerMondayStatus.setSelectedIndex(0);
                                                    spinnerMondayStatus.setTextColor(getColor(R.color.colorDarkRed));
                                                }else if(hourData.getType() == 2){
                                                    spinnerMondayStatus.setSelectedIndex(1);
                                                    spinnerMondayStatus.setTextColor(getColor(R.color.colorGreen2));
                                                }else if(hourData.getType() == 3){
                                                    spinnerMondayStatus.setSelectedIndex(2);
                                                    spinnerMondayStatus.setTextColor(getColor(R.color.colorPrimary));
                                                }else if(hourData.getType() == 4){
                                                    spinnerMondayStatus.setSelectedIndex(3);
                                                    spinnerMondayStatus.setTextColor(getColor(R.color.colorPrimary));
                                                }

                                                tvMonday.setText(hourData.getDay());
                                                if (hourData.getStartTime1() != null)
                                                    tvMondayStartTime1.setText(hourData.getStartTime1().substring(0,5));
                                                if (hourData.getEndTime1() != null)
                                                    tvMondayEndTime1.setText(hourData.getEndTime1().substring(0,5));
                                                if (hourData.getStartTime2() != null)
                                                    tvMondayStartTime2.setText(hourData.getStartTime2().substring(0,5));
                                                if (hourData.getEndTime2() != null)
                                                    tvMondayEndTime2.setText(hourData.getEndTime2().substring(0,5));
                                            }else if(hourData.getDay().equals(getString(R.string.tuesday))){
                                                // TODO Tuesday

                                                if (hourData.getType() == 1){
                                                    spinnerTuesdayStatus.setSelectedIndex(0);
                                                    spinnerTuesdayStatus.setTextColor(getColor(R.color.colorDarkRed));
                                                }else if(hourData.getType() == 2){
                                                    spinnerTuesdayStatus.setSelectedIndex(1);
                                                    spinnerTuesdayStatus.setTextColor(getColor(R.color.colorGreen2));
                                                }else if(hourData.getType() == 3){
                                                    spinnerTuesdayStatus.setSelectedIndex(2);
                                                    spinnerTuesdayStatus.setTextColor(getColor(R.color.colorPrimary));
                                                }else if(hourData.getType() == 4){
                                                    spinnerTuesdayStatus.setSelectedIndex(3);
                                                    spinnerTuesdayStatus.setTextColor(getColor(R.color.colorPrimary));
                                                }

                                                tvTuesday.setText(hourData.getDay());
                                                if (hourData.getStartTime1() != null)
                                                    tvTuesdayStartTime1.setText(hourData.getStartTime1().substring(0,5));
                                                if (hourData.getEndTime1() != null)
                                                    tvTuesdayEndTime1.setText(hourData.getEndTime1().substring(0,5));
                                                if (hourData.getStartTime2() != null)
                                                    tvTuesdayStartTime2.setText(hourData.getStartTime2().substring(0,5));
                                                if (hourData.getEndTime2() != null)
                                                    tvTuesdayEndTime2.setText(hourData.getEndTime2().substring(0,5));
                                            }else if(hourData.getDay().equals(getString(R.string.wednesday))){
                                                // TODO Wednesday

                                                if (hourData.getType() == 1){
                                                    spinnerWednesdayStatus.setSelectedIndex(0);
                                                    spinnerWednesdayStatus.setTextColor(getColor(R.color.colorDarkRed));
                                                }else if(hourData.getType() == 2){
                                                    spinnerWednesdayStatus.setSelectedIndex(1);
                                                    spinnerWednesdayStatus.setTextColor(getColor(R.color.colorGreen2));
                                                }else if(hourData.getType() == 3){
                                                    spinnerWednesdayStatus.setSelectedIndex(2);
                                                    spinnerWednesdayStatus.setTextColor(getColor(R.color.colorPrimary));
                                                }else if(hourData.getType() == 4){
                                                    spinnerWednesdayStatus.setSelectedIndex(3);
                                                    spinnerWednesdayStatus.setTextColor(getColor(R.color.colorPrimary));
                                                }

                                                tvWednesday.setText(hourData.getDay());
                                                if (hourData.getStartTime1() != null)
                                                    tvWednesdayStartTime1.setText(hourData.getStartTime1().substring(0,5));
                                                if (hourData.getEndTime1() != null)
                                                    tvWednesdayEndTime1.setText(hourData.getEndTime1().substring(0,5));
                                                if (hourData.getStartTime2() != null)
                                                    tvWednesdayStartTime2.setText(hourData.getStartTime2().substring(0,5));
                                                if (hourData.getEndTime2() != null)
                                                    tvWednesdayEndTime2.setText(hourData.getEndTime2().substring(0,5));
                                            }else if(hourData.getDay().equals(getString(R.string.thursday))){
                                                // TODO Thursday

                                                if (hourData.getType() == 1){
                                                    spinnerThursdayStatus.setSelectedIndex(0);
                                                    spinnerThursdayStatus.setTextColor(getColor(R.color.colorDarkRed));
                                                }else if(hourData.getType() == 2){
                                                    spinnerThursdayStatus.setSelectedIndex(1);
                                                    spinnerThursdayStatus.setTextColor(getColor(R.color.colorGreen2));
                                                }else if(hourData.getType() == 3){
                                                    spinnerThursdayStatus.setSelectedIndex(2);
                                                    spinnerThursdayStatus.setTextColor(getColor(R.color.colorPrimary));
                                                }else if(hourData.getType() == 4){
                                                    spinnerThursdayStatus.setSelectedIndex(3);
                                                    spinnerThursdayStatus.setTextColor(getColor(R.color.colorPrimary));
                                                }

                                                tvThursday.setText(hourData.getDay());
                                                if (hourData.getStartTime1() != null)
                                                    tvThursdayStartTime1.setText(hourData.getStartTime1().substring(0,5));
                                                if (hourData.getEndTime1() != null)
                                                    tvThursdayEndTime1.setText(hourData.getEndTime1().substring(0,5));
                                                if (hourData.getStartTime2() != null)
                                                    tvThursdayStartTime2.setText(hourData.getStartTime2().substring(0,5));
                                                if (hourData.getEndTime2() != null)
                                                    tvThursdayEndTime2.setText(hourData.getEndTime2().substring(0,5));
                                            }else if(hourData.getDay().equals(getString(R.string.friday))){
                                                // TODO Friday

                                                if (hourData.getType() == 1){
                                                    spinnerFridayStatus.setSelectedIndex(0);
                                                    spinnerFridayStatus.setTextColor(getColor(R.color.colorDarkRed));
                                                }else if(hourData.getType() == 2){
                                                    spinnerFridayStatus.setSelectedIndex(1);
                                                    spinnerFridayStatus.setTextColor(getColor(R.color.colorGreen2));
                                                }else if(hourData.getType() == 3){
                                                    spinnerFridayStatus.setSelectedIndex(2);
                                                    spinnerFridayStatus.setTextColor(getColor(R.color.colorPrimary));
                                                }else if(hourData.getType() == 4){
                                                    spinnerFridayStatus.setSelectedIndex(3);
                                                    spinnerFridayStatus.setTextColor(getColor(R.color.colorPrimary));
                                                }else{
                                                    spinnerFridayStatus.setTextColor(getColor(R.color.colorBlack));
                                                }

                                                tvFriday.setText(hourData.getDay());
                                                if (hourData.getStartTime1() != null)
                                                    tvFridayStartTime1.setText(hourData.getStartTime1().substring(0,5));
                                                if (hourData.getEndTime1() != null)
                                                    tvFridayEndTime1.setText(hourData.getEndTime1().substring(0,5));
                                                if (hourData.getStartTime2() != null)
                                                    tvFridayStartTime2.setText(hourData.getStartTime2().substring(0,5));
                                                if (hourData.getEndTime2() != null)
                                                    tvFridayEndTime2.setText(hourData.getEndTime2().substring(0,5));
                                            }
                                        }







                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<WorkHour> call, Throwable t) {
                        loading.setVisibility(View.GONE);
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    @Override
    public void onWorkHoursSelected(List<SendWorkHours> sendWorkHours, int pos) {
        Log.d(TAG, "onWorkHoursSelected: " + new Gson().toJson(sendWorkHours));
        list.addAll(sendWorkHours);
    }

    private List<SendWorkHours> list = new ArrayList<>();
}
