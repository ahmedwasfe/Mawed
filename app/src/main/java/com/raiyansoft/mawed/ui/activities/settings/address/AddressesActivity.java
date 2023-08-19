package com.raiyansoft.mawed.ui.activities.settings.address;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.adapter.AddressessAdapter;
import com.raiyansoft.mawed.listener.DeleteAddressListener;
import com.raiyansoft.mawed.model.address.AddressData;
import com.raiyansoft.mawed.model.address.Addresses;
import com.raiyansoft.mawed.model.address.delete.DeleteAddress;
import com.raiyansoft.mawed.utils.HelperMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressesActivity extends AppCompatActivity implements DeleteAddressListener {
    private static String TAG = AddressesActivity.class.getSimpleName();

    @BindView(R.id.pb_loading)
    ProgressBar loading;
    @BindView(R.id.recycler_addresses)
    RecyclerView recyclerAddresses;

    private List<AddressData> listAddresses;
    private AddressessAdapter addressessAdapter;

    @OnClick(R.id.iv_add)
    void onAddAddressClick() {
        startActivity(new Intent(this, AddAddressActivity.class));
    }

    @OnClick(R.id.iv_back)
    void onBackClick() {
        onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresses);
        ButterKnife.bind(this);
        initUI();
        getAddressess();
    }

    private void initUI() {

        listAddresses = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerAddresses.setHasFixedSize(true);
        recyclerAddresses.setLayoutManager(layoutManager);
    }

    private void getAddressess() {

        loading.setVisibility(View.VISIBLE);

        HelperMethods.getMawedAPI()
                .getAddresses(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this))
                .enqueue(new Callback<Addresses>() {
                    @Override
                    public void onResponse(Call<Addresses> call, Response<Addresses> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                listAddresses.clear();
                                listAddresses.addAll(response.body().getAddressesData().getAddresses());
                                addressessAdapter = new AddressessAdapter(AddressesActivity.this, listAddresses, AddressesActivity.this);
                                recyclerAddresses.setAdapter(addressessAdapter);
                            } else {
                                Log.d(TAG, "onResponse: " + response.body().getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Addresses> call, Throwable t) {
                        loading.setVisibility(View.GONE);
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });

    }

    @Override
    public void onDeleteAddress(AddressData address, int pos) {
        deleteAddress(address, pos);
    }

    private void deleteAddress(AddressData address, int pos) {

        HelperMethods.getMawedAPI()
                .deleteAddress(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this),
                        address.getId())
                .enqueue(new Callback<DeleteAddress>() {
                    @Override
                    public void onResponse(Call<DeleteAddress> call, Response<DeleteAddress> response) {
                        if (response.isSuccessful()) {
                            addressessAdapter.updateList(pos);
                        }
                    }

                    @Override
                    public void onFailure(Call<DeleteAddress> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }
}
