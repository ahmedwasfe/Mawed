package com.raiyansoft.mawed.listener;

import com.raiyansoft.mawed.model.salon.workHour.SendWorkHours;

import java.util.List;

public interface WorkHoursListener {

    void onWorkHoursSelected(List<SendWorkHours> sendWorkHours, int pos);
}
