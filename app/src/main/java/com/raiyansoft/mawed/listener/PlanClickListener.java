package com.raiyansoft.mawed.listener;

import com.raiyansoft.mawed.model.plans.PlanData;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public interface PlanClickListener {

    void onPlanClick(PlanData plan, BottomSheetDialog sheetDialog, int position);
}
