package com.raiyansoft.mawed.listener;

import com.raiyansoft.mawed.model.address.AddressData;

public interface DeleteAddressListener {

    void onDeleteAddress(AddressData address, int pos);
}
