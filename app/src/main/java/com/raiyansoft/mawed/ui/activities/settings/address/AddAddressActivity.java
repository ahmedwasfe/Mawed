package com.raiyansoft.mawed.ui.activities.settings.address;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.adapter.GovernoratesAdapter;
import com.raiyansoft.mawed.adapter.RegionsAdapter;
import com.raiyansoft.mawed.listener.SelectGovernorateListener;
import com.raiyansoft.mawed.listener.SelectRegionsListener;
import com.raiyansoft.mawed.model.address.add.AddAddress;
import com.raiyansoft.mawed.model.governorate.Governorate;
import com.raiyansoft.mawed.model.governorate.GovernorateData;
import com.raiyansoft.mawed.model.region.Region;
import com.raiyansoft.mawed.model.region.RegionData;
import com.raiyansoft.mawed.utils.Const;
import com.raiyansoft.mawed.utils.HelperMethods;
import com.raiyansoft.mawed.utils.PreferencesManager;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressActivity extends FragmentActivity implements OnMapReadyCallback, SelectGovernorateListener, SelectRegionsListener, View.OnClickListener {
    private static final String TAG = AddAddressActivity.class.getSimpleName();

    @BindView(R.id.rel_container)
    RelativeLayout container;
    @BindView(R.id.pb_loading)
    ProgressBar loading;

    @BindView(R.id.recycler_governorates)
    RecyclerView recyclerGovernorates;
    @BindView(R.id.recycler_regions)
    RecyclerView recyclerRegions;
    @BindView(R.id.input_widget)
    EditText inputWidget;
    @BindView(R.id.input_street)
    EditText inputStreet;
    @BindView(R.id.input_house_numbert)
    EditText inputHouseNumber;

    @BindView(R.id.expandable_layout_governorate)
    ExpandableLayout expandableGovernorate;

    @BindView(R.id.expandable_layout_region)
    ExpandableLayout expandableRegion;
    @BindView(R.id.iv_arrow_governorate)
    ImageView ivArrowGovernorate;
    @BindView(R.id.iv_arrow_region)
    ImageView ivArrowRegion;

    private GoogleMap mMap;

    private int maxResults = 1;

    private LocationManager locationManager = null;
    private boolean isEnabled = false;
    private LocationRequest locationRequest;

    GovernoratesAdapter governoratesAdapter;
    RegionsAdapter regionsAdapter;
    private List<GovernorateData> listGovernorates;
    private List<RegionData> listRegions;

    private Integer governorateId, regionId;

    @OnClick(R.id.iv_current_location)
    void onCurrentLocationClick() {
        checkPermission();
    }

    @OnClick(R.id.iv_back)
    void onBacClick() {
        clearData();
        onBackPressed();
    }

    @OnClick(R.id.ll_governorate)
    void onGovernorateClick() {
        if (expandableGovernorate.isExpanded())
            ivArrowGovernorate.setBackgroundResource(R.drawable.ic_icon_arrow_down);
        else
            ivArrowGovernorate.setBackgroundResource(R.drawable.ic_icon_arrow_up);
        expandableGovernorate.toggle();
    }

    @OnClick(R.id.ll_region)
    void onRegionClick() {
        if (expandableRegion.isExpanded())
            ivArrowRegion.setBackgroundResource(R.drawable.ic_icon_arrow_down);
        else
            ivArrowRegion.setBackgroundResource(R.drawable.ic_icon_arrow_up);
        expandableRegion.toggle();
    }

    @OnClick(R.id.btn_cancel)
    void onCancelClick() {
        clearData();
        finish();
    }

    @OnClick(R.id.btn_save)
    void onSaveClick() {

        loading.setVisibility(View.VISIBLE);

        String widget = inputWidget.getText().toString();
        String street = inputStreet.getText().toString();
        String houseNumber = inputHouseNumber.getText().toString();

        if (!HelperMethods.isInternetConnected(this)) {
            HelperMethods.showCustomSnackBar(this, container, getString(R.string.check_internet),
                    false, Snackbar.LENGTH_SHORT,
                    Gravity.BOTTOM,
                    this);
            loading.setVisibility(View.GONE);
            return;
        }

        if (TextUtils.isEmpty(widget)) {
            Toast.makeText(this, getString(R.string.please_enter_widget), Toast.LENGTH_SHORT).show();
            loading.setVisibility(View.GONE);
            return;
        }

        if (TextUtils.isEmpty(street)) {
            Toast.makeText(this, getString(R.string.please_enter_street), Toast.LENGTH_SHORT).show();
            loading.setVisibility(View.GONE);
            return;
        }

        if (TextUtils.isEmpty(houseNumber)) {
            Toast.makeText(this, getString(R.string.please_enter_house_number), Toast.LENGTH_SHORT).show();
            loading.setVisibility(View.GONE);
            return;
        }

        if (HelperMethods.LATITUDE == null && HelperMethods.LONGITUDE == null) {
            Toast.makeText(this, getString(R.string.please_select_location), Toast.LENGTH_SHORT).show();
            loading.setVisibility(View.GONE);
            return;
        }

        saveAddress(governorateId, regionId, widget, street, houseNumber, HelperMethods.LATITUDE, HelperMethods.LONGITUDE);

    }

    private void saveAddress(Integer governorateId, Integer regionId, String widget, String street, String houseNumber, Double latitude, Double longitude) {

        loading.setVisibility(View.VISIBLE);
        String fullAddress = new StringBuilder()
                .append(widget)
                .append(", ")
                .append(street)
                .append(", ")
                .append(houseNumber)
                .toString();
        HelperMethods.getMawedAPI()
                .addAddress(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this),
                        fullAddress,
                        widget, street, houseNumber, governorateId, regionId, latitude, longitude)
                .enqueue(new Callback<AddAddress>() {
                    @Override
                    public void onResponse(Call<AddAddress> call, Response<AddAddress> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            if (response.body().isStatus()) {
//                                Toast.makeText(AddAddressActivity.this, getString(R.string.add_address_success), Toast.LENGTH_SHORT).show();
                                HelperMethods.ADDRESS = null;
                                HelperMethods.LATITUDE = null;
                                HelperMethods.LONGITUDE = null;
                                startActivity(new Intent(AddAddressActivity.this, AddressesActivity.class));
                                finish();
                            } else {
                                Log.e(TAG, "onResponse: " + response.body().getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AddAddress> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                        loading.setVisibility(View.GONE);
                    }
                });

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        HelperMethods.checkAppLanguage(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        initLocationRequest();
        getCurrentLocation();
        initUI();
        getGovernorate();

    }

    private void getGovernorate() {

        HelperMethods.getMawedAPI()
                .getGovernorate(HelperMethods.getAppLanguage(this))
                .enqueue(new Callback<Governorate>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(Call<Governorate> call, Response<Governorate> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getGovernorates() != null) {
                                    listGovernorates.clear();
                                    listGovernorates.addAll(response.body().getGovernorates());
                                    governorateId = listGovernorates.get(0).getCityId();
                                    getRegions(governorateId);
                                    governoratesAdapter = new GovernoratesAdapter(AddAddressActivity.this, listGovernorates, AddAddressActivity.this);
                                    recyclerGovernorates.setAdapter(governoratesAdapter);
                                } else {
                                    listGovernorates.clear();
                                    governoratesAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Governorate> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    private void getRegions(int governorateId) {

        HelperMethods.getMawedAPI()
                .getRegions(HelperMethods.getAppLanguage(this), governorateId)
                .enqueue(new Callback<Region>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(Call<Region> call, Response<Region> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getRegions() != null) {
                                    listRegions.clear();
                                    listRegions.addAll(response.body().getRegions());
                                    regionId = listRegions.get(0).getRegionId();
                                    regionsAdapter = new RegionsAdapter(AddAddressActivity.this, listRegions, AddAddressActivity.this);
                                    recyclerRegions.setAdapter(regionsAdapter);
                                } else {
                                    listRegions.clear();
                                    regionsAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Region> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });

    }

    private void initUI() {

        listGovernorates = new ArrayList<>();
        listRegions = new ArrayList<>();

        ivArrowGovernorate.setBackgroundResource(R.drawable.ic_icon_arrow_down);
        ivArrowRegion.setBackgroundResource(R.drawable.ic_icon_arrow_down);

        LinearLayoutManager governorateLayoutManager = new LinearLayoutManager(this);
        governorateLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerGovernorates.setHasFixedSize(true);
        recyclerGovernorates.setLayoutManager(governorateLayoutManager);

        LinearLayoutManager regionLayoutManager = new LinearLayoutManager(this);
        regionLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerRegions.setHasFixedSize(true);
        recyclerRegions.setLayoutManager(regionLayoutManager);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);

        mMap.setOnMapClickListener(latLng -> {

            // Create Marker
            MarkerOptions markerOptions = new MarkerOptions();
            //Setting Position for marker
            markerOptions.position(latLng);
            // Here add Icon
            /* Setting the title for the marker
             * This will be displayed on taping the marker
             */

            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_map_location));

            Geocoder geocoder = new Geocoder(this, HelperMethods.getAppLocale(this));
            /*
             * Geocoder.getFromLocation - Returns an array of Addresses
             * that are known to describe the area immediately surrounding the given LATITUDE and LONGITUDE.
             */
            try {
                List<Address> listAddress = geocoder.getFromLocation(latLng.latitude, latLng.longitude, maxResults);

                if (listAddress.size() > 0) {

                    Address address = listAddress.get(0);

                    HelperMethods.ADDRESS = address.getAddressLine(0);
                    HelperMethods.LATITUDE = address.getLatitude();
                    HelperMethods.LONGITUDE = address.getLongitude();

                    markerOptions.title(address.getAddressLine(0));
                }

            } catch (IOException e) {
                Log.e(TAG, "onMapReady: " + e.getMessage());
                Log.e(TAG, "onMapReady: " + e.getLocalizedMessage());
                e.printStackTrace();
            }

            // Clears the previously touched position
            mMap.clear();
            // Animating to the touched position
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f));
            // Place Marker on the touched position
            mMap.addMarker(markerOptions);

        });
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            // TODO 31.510995899999994, 34.466222099999996
            double lat = Double.parseDouble(PreferencesManager.loadAppData(this, Const.KEY_CURRENT_LATITUDE));
            double lng = Double.parseDouble(PreferencesManager.loadAppData(this, Const.KEY_CURRENT_LONGITUDE));

            LatLng currentlatLng = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions()
                    .title(getString(R.string.my_location))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_map_location))
                    .position(currentlatLng));

            Geocoder geocoder = new Geocoder(this, HelperMethods.getAppLocale(this));
            try {
                List<Address> listAddress = geocoder.getFromLocation(lat, lng, maxResults);

                if (listAddress.size() > 0) {

                    Address address = listAddress.get(0);
                    HelperMethods.ADDRESS = address.getAddressLine(0);
                    HelperMethods.LATITUDE = address.getLatitude();
                    HelperMethods.LONGITUDE = address.getLongitude();
                }

            } catch (IOException e) {
                Log.e(TAG, "onMapReady: " + e.getMessage());
                Log.e(TAG, "onMapReady: " + e.getLocalizedMessage());
                e.printStackTrace();
            }
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentlatLng, 14f));
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, Const.CODE_REQUEST_LOCATION);
        }
    }

    private void initLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
    }

    private void getCurrentLocation() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnable()) {

                    LocationServices.getFusedLocationProviderClient(this)
                            .requestLocationUpdates(locationRequest,
                                    new LocationCallback() {
                                        @Override
                                        public void onLocationResult(@NonNull LocationResult locationResult) {
                                            super.onLocationResult(locationResult);

                                            LocationServices.getFusedLocationProviderClient(AddAddressActivity.this)
                                                    .removeLocationUpdates(this);

                                            if (locationResult.getLocations().size() > 0) {
                                                PreferencesManager.saveAppData(AddAddressActivity.this, Const.KEY_CURRENT_LATITUDE, String.valueOf(locationResult.getLastLocation().getLatitude()));
                                                PreferencesManager.saveAppData(AddAddressActivity.this, Const.KEY_CURRENT_LONGITUDE, String.valueOf(locationResult.getLastLocation().getLongitude()));
                                                Log.d(TAG, "onLocationResult: " + "ALLOW");
                                                Log.d(TAG, "onLocationResult: " + PreferencesManager.loadAppData(AddAddressActivity.this, Const.KEY_CURRENT_LATITUDE) + "," + PreferencesManager.loadAppData(AddAddressActivity.this, Const.KEY_CURRENT_LONGITUDE));
                                                checkPermission();
                                            }
                                        }
                                    }
                                    , Looper.getMainLooper());
                } else {
                    trunOnGPS();
                }
            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, Const.CODE_REQUEST_LOCATION);
            }
        }
    }

    private void trunOnGPS() {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true);

        Task<LocationSettingsResponse> taskResult = LocationServices.getSettingsClient(this)
                .checkLocationSettings(builder.build());

        taskResult.addOnCompleteListener(task -> {
            try {
                LocationSettingsResponse response = task.getResult(ApiException.class);
                Toast.makeText(AddAddressActivity.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();
            } catch (ApiException e) {
                switch (e.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                            resolvableApiException.startResolutionForResult(AddAddressActivity.this, Const.REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        //Device does not have location
                        break;
                }
            }
        });
    }

    private boolean isGPSEnable() {

        if (locationManager == null)
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Const.CODE_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this,"GREAT",Toast.LENGTH_SHORT).show();
//                permissionGranted = true;
                getCurrentLocation();
//                checkUserExisits();
            } else {
                Toast.makeText(this, "Permission Needed To Run The App", Toast.LENGTH_LONG).show();
//                permissionGranted = false;
            }
        }
    }

    @Override
    public void onGovernorateSelected(GovernorateData governorate, int pos) {
        governorateId = governorate.getCityId();
        getRegions(governorateId);
    }

    private void clearData() {
        HelperMethods.ADDRESS = null;
        HelperMethods.LATITUDE = null;
        HelperMethods.LONGITUDE = null;
        governorateId = null;
        regionId = null;
    }

    @Override
    public void onRegionSelected(RegionData region, int pos) {
        regionId = region.getRegionId();
        Log.d(TAG, "onRegionSelected: " + regionId);
    }

    @Override
    public void onClick(View v) {
        HelperMethods.wifiSettings(this);
    }
}
