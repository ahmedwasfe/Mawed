package com.raiyansoft.mawed.listener;


import com.raiyansoft.mawed.model.home.HomeGovernorate;
import com.raiyansoft.mawed.model.region.RegionData;

public interface SelectHomeAddressListener {

    void onAddressSelected(HomeGovernorate governorate, RegionData region, int pos);
}
