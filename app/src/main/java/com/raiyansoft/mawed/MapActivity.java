package com.raiyansoft.mawed;

import android.Manifest;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.utils.Const;
import com.raiyansoft.mawed.utils.HelperMethods;
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

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback  {
    private static final String TAG = MapActivity.class.getSimpleName();

    private GoogleMap mMap;
    private double salonLat, salonLng;
    private String salonName;

    private int maxResults = 1;

    private LocationManager locationManager = null;
    private boolean isEnabled = false;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

        if (getIntent() != null){
            salonLat = getIntent().getDoubleExtra(Const.KEY_SALON_LATITUDE, 0.0);
            salonLng = getIntent().getDoubleExtra(Const.KEY_SALON_LONGITUDE, 0.0);
            salonName = getIntent().getStringExtra(Const.KEY_SALON_NAME);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        initLocationRequest();
        getCurrentLocation();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);

        LatLng currentlatLng = new LatLng(salonLat, salonLng);
        mMap.addMarker(new MarkerOptions()
                .title(salonName)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.salon_location))
                .position(currentlatLng));

    }

    private void checkPermission(double latitude, double longitude) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            // TODO 31.510995899999994, 34.466222099999996
//            double lat = Double.parseDouble(PreferencesManager.loadAppData(this, Const.KEY_CURRENT_LATITUDE));
//            double lng = Double.parseDouble(PreferencesManager.loadAppData(this, Const.KEY_CURRENT_LONGITUDE));

            LatLng currentlatLng = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions()
                    .title(getString(R.string.my_location))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_map_location))
                    .position(currentlatLng));

            Geocoder geocoder = new Geocoder(this, HelperMethods.getAppLocale(this));
            try {
                List<Address> listAddress = geocoder.getFromLocation(currentlatLng.latitude, currentlatLng.longitude, maxResults);

                if (listAddress.size() > 0) {

                    Address address = listAddress.get(0);
                    HelperMethods.ADDRESS = address.getAddressLine(0);
                    HelperMethods.LATITUDE = address.getLatitude();
                    HelperMethods.LONGITUDE = address.getLongitude();
//                    tvLocation.setText(HelperMethods.ADDRESS);
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
                    android.Manifest.permission.ACCESS_FINE_LOCATION
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

                                            LocationServices.getFusedLocationProviderClient(MapActivity.this)
                                                    .removeLocationUpdates(this);

                                            if (locationResult.getLocations().size() > 0) {
//                                                PreferencesManager.saveAppData(MapActivity.this, Const.KEY_CURRENT_LATITUDE, String.valueOf(locationResult.getLastLocation().getLatitude()));
//                                                PreferencesManager.saveAppData(MapActivity.this, Const.KEY_CURRENT_LONGITUDE, String.valueOf(locationResult.getLastLocation().getLongitude()));
                                                Log.d(TAG, "onLocationResult: " + "ALLOW");
//                                                Log.d(TAG, "onLocationResult: " + PreferencesManager.loadAppData(MapActivity.this, Const.KEY_CURRENT_LATITUDE) + "," + PreferencesManager.loadAppData(MapActivity.this, Const.KEY_CURRENT_LONGITUDE));
                                                checkPermission(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());
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
                Toast.makeText(MapActivity.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();
            } catch (ApiException e) {
                switch (e.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                            resolvableApiException.startResolutionForResult(MapActivity.this, Const.REQUEST_CHECK_SETTINGS);
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
}
