package com.raiyansoft.mawed.listener;

import android.widget.ImageView;

import com.raiyansoft.mawed.model.salon.services.SalonServicesData;

public interface SalonServicesSelectedListener {

    void onSalonServicesSelected(SalonServicesData services, int pos);
    void onSalonServicesDelete(SalonServicesData services, ImageView imageView, int pos);
}
