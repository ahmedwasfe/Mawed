package com.raiyansoft.mawed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.raiyansoft.mawed.model.settings.about.About;
import com.raiyansoft.mawed.model.settings.about.AboutData;
import com.raiyansoft.mawed.ui.activities.auth.LoginActivity;
import com.raiyansoft.mawed.ui.activities.salon.HomeSalonActivity;
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
import com.google.android.gms.tasks.Task;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();

    @BindView(R.id.giv_logo)
    GifImageView gifLogo;
    private LocationManager locationManager = null;
    private boolean isEnabled = false;
    private boolean isPermissionGranted = false;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        HelperMethods.checkAppLanguage(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        gifLogo.setImageResource(R.drawable.mawed);
        getFirebaseToken();
        checkUserExists();
        getAbout();
        initLocationRequest();
        getCurrentLocation();
    }

    private void checkUserExists() {
        Log.d(TAG, "checkUserExists: " + PreferencesManager.isSaveStarted(this, Const.KEY_STARTED_SCREENS));
        Log.d(TAG, "checkUserExists: " + PreferencesManager.loadAppData(this, Const.KEY_USER_TYPE));
        new Handler()
                .postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (PreferencesManager.isSaveStarted(SplashActivity.this, Const.KEY_STARTED_SCREENS)) {
                            if (PreferencesManager.loadUserToken(SplashActivity.this, Const.KEY_USER_TOKEN) != null && !PreferencesManager.loadUserToken(SplashActivity.this, Const.KEY_USER_TOKEN).equals("")) {
                                if (PreferencesManager.loadAppData(SplashActivity.this, Const.KEY_USER_TYPE) != null && !PreferencesManager.loadAppData(SplashActivity.this, Const.KEY_USER_TYPE).isEmpty()){
                                    if (PreferencesManager.loadAppData(SplashActivity.this, Const.KEY_USER_TYPE).equals("1")){
                                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                                        finish();
                                    }else {
                                        startActivity(new Intent(SplashActivity.this, HomeSalonActivity.class));
                                        finish();
                                    }
                                }else {
                                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                                    finish();
                                }
                            } else {
                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                finish();
                            }
                        } else {
                            startActivity(new Intent(SplashActivity.this, StartedAcivivty.class));
                            finish();
                        }
                    }
                }, 5000);

    }

    private void getAbout(){
        HelperMethods.getMawedAPI()
                .about(HelperMethods.getAppLanguage(this))
                .enqueue(new Callback<About>() {
                    @Override
                    public void onResponse(Call<About> call, Response<About> response) {
                        if (response.isSuccessful()){
                            if (response.body().getData() != null){
                                AboutData about = response.body().getData();
                                Log.d(TAG, "onResponse: " + about.getAbout());
                                PreferencesManager.saveAppData(SplashActivity.this, Const.KEY_ABOUT, about.getAbout());
                                PreferencesManager.saveAppData(SplashActivity.this, Const.KEY_PRIVACY, about.getPrivacy());
                                PreferencesManager.saveAppData(SplashActivity.this, Const.KEY_CONDITIONS, about.getConditions());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<About> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    private void getFirebaseToken() {
        Log.d(TAG, "getFirebaseToken: ");
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getFirebaseToken token failed", task.getException());
                            return;
                        }

                        String token = task.getResult();
                        PreferencesManager.saveUserToken(this, Const.FIREBASE_TOKEN, token);
                        Log.d(TAG, "getFirebaseToken: " + token);
                    }
                }).addOnFailureListener(e -> Log.e(TAG, "getFirebaseToken: " + e.getMessage()));

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

                                            LocationServices.getFusedLocationProviderClient(SplashActivity.this)
                                                    .removeLocationUpdates(this);

                                            if (locationResult.getLocations().size() > 0) {
                                                PreferencesManager.saveAppData(SplashActivity.this, Const.KEY_CURRENT_LATITUDE, String.valueOf(locationResult.getLastLocation().getLatitude()));
                                                PreferencesManager.saveAppData(SplashActivity.this, Const.KEY_CURRENT_LONGITUDE, String.valueOf(locationResult.getLastLocation().getLongitude()));
                                                Log.d(TAG, "onLocationResult: " + "ALLOW");
                                                checkUserExists();
                                            }
                                        }
                                    }
                                    , Looper.getMainLooper());
                } else {
                    statusCheck();
//                    trunOnGPS();
                }
            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
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
                Toast.makeText(SplashActivity.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();
            } catch (ApiException e) {
                switch (e.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                            resolvableApiException.startResolutionForResult(SplashActivity.this, Const.REQUEST_CHECK_SETTINGS);
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

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
    }

    private void buildAlertMessageNoGps() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_location_permisstion, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        HelperMethods.showCustomDialog(dialog);
        TextView tvYes = dialogView.findViewById(R.id.tv_yes);
        TextView tvCancel = dialogView.findViewById(R.id.tv_cance);

        tvCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        tvYes.setOnClickListener(v -> {
            dialog.dismiss();
            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        });

        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getCurrentLocation();
    }

}