package com.raiyansoft.mawed.ui.activities.salon;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.adapter.salon.SalonServiceAdapter;
import com.raiyansoft.mawed.listener.SalonServicesSelectedListener;
import com.raiyansoft.mawed.model.DeleteSalonService;
import com.raiyansoft.mawed.model.salon.services.SalonServices;
import com.raiyansoft.mawed.model.salon.services.SalonServicesData;
import com.raiyansoft.mawed.model.salon.services.save.SaveService;
import com.raiyansoft.mawed.utils.HelperMethods;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalonServicesActivity extends AppCompatActivity implements SalonServicesSelectedListener {
    private static final String TAG = SalonServicesActivity.class.getSimpleName();

    @BindView(R.id.pb_loading)
    ProgressBar loading;

    @BindView(R.id.recycler_services)
    RecyclerView recyclerServices;

    private List<SalonServicesData> listServices;
    private int catId;
    private int time;
    private int price;

    @OnClick(R.id.iv_back)
    void onBackClick() {
        onBackPressed();
    }

    @OnClick(R.id.btn_save)
    void onSaveClick() {
        Log.d(TAG, "onSaveClick: ");
        saveService(catId, time, price, null);
    }

    @OnClick(R.id.btn_back)
    void onBtnBackClick() {
        onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_services);
        ButterKnife.bind(this);
        initUI();
        getServices();
    }

    private void initUI() {

        listServices = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerServices.setHasFixedSize(true);
        recyclerServices.setLayoutManager(layoutManager);
    }

    private void getServices() {
        loading.setVisibility(View.VISIBLE);
        HelperMethods.getMawedAPI()
                .getSalonServices(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this))
                .enqueue(new Callback<SalonServices>() {
                    @Override
                    public void onResponse(Call<SalonServices> call, Response<SalonServices> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getServices() != null) {
                                    listServices.clear();
                                    listServices.addAll(response.body().getServices());
                                    SalonServiceAdapter salonServiceAdapter = new SalonServiceAdapter(SalonServicesActivity.this, listServices, SalonServicesActivity.this);
                                    recyclerServices.setAdapter(salonServiceAdapter);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SalonServices> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                        loading.setVisibility(View.GONE);
                    }
                });
    }

    private void saveService(int serviceId, int time, int price, BottomSheetDialog sheetDialog) {
        loading.setVisibility(View.VISIBLE);
        HelperMethods.getMawedAPI()
                .saveService(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this),
                        serviceId, time, price)
                .enqueue(new Callback<SaveService>() {
                    @Override
                    public void onResponse(Call<SaveService> call, Response<SaveService> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()){
                            if (response.body().isStatus()){
                                if (response.body().getService() != null){
                                    getServices();
                                    if (sheetDialog != null)
                                        sheetDialog.dismiss();
                                }
                            }else {
                                Log.d(TAG, "onResponse: " + response.body().getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SaveService> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                        loading.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onSalonServicesSelected(SalonServicesData services, int pos) {
        Log.d(TAG, "onSalonServicesSelected ID: " + services.getId());
        Log.d(TAG, "onSalonServicesSelected TIME: " + services.getTime());
        Log.d(TAG, "onSalonServicesSelected PRICE: " + services.getPrice());

        if (HelperMethods.getUserType(this).equals("2")) {
            catId = Integer.parseInt(services.getId());
            showSelectServiceSheet(services, pos);
        }else {
            catId = Integer.parseInt(services.getCatId());
            if (services.getTime() != null)
                time = Integer.parseInt(services.getTime());
            if (services.getPrice() != null)
                price = Integer.parseInt(services.getPrice());
        }

    }

    @Override
    public void onSalonServicesDelete(SalonServicesData services, ImageView imageView, int pos) {
        Log.d(TAG, "onSalonServicesDelete: " + services.getTitle());
        showDeleteServiceDialog(services, imageView);
    }

    private void showDeleteServiceDialog(SalonServicesData services, ImageView imageView) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_delete_service, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        HelperMethods.showCustomDialog(dialog);

        CardView card = dialogView.findViewById(R.id.card_dialog);
        card.setBackgroundColor(Color.TRANSPARENT);

        TextView tvService = dialogView.findViewById(R.id.tv_service_name);
        tvService.setText(services.getTitle());

        dialogView.findViewById(R.id.btn_cancel)
                .setOnClickListener(view -> dialog.dismiss());

        dialogView.findViewById(R.id.btn_logout)
                .setOnClickListener(v -> {
                    deleteService(services, imageView, dialog);

                });

        dialog.show();

    }

    private void showSelectServiceSheet(SalonServicesData services, int pos) {
        BottomSheetDialog sheetDialog = new BottomSheetDialog(this);
        sheetDialog.setContentView(R.layout.layout_sheet_select_service);

        CardView card = sheetDialog.findViewById(R.id.card_select_service);
        card.setBackgroundResource(R.drawable.bg_bottom_sheet);
        TextView tvServiceName = sheetDialog.findViewById(R.id.tv_text_service);
        EditText inputServiceTime = sheetDialog.findViewById(R.id.input_service_time);
        EditText inputServicePrice = sheetDialog.findViewById(R.id.input_service_price);

        tvServiceName.setText(services.getTitle());

        sheetDialog.findViewById(R.id.btn_save)
                        .setOnClickListener(v -> {
                            String serviceTime = inputServiceTime.getText().toString();
                            String servicePrice = inputServicePrice.getText().toString();

                            if (serviceTime.isEmpty()){
                                inputServiceTime.setError(getString(R.string.please_enter_service_time));
                                return;
                            }

                            if (servicePrice.isEmpty()){
                                inputServicePrice.setError(getString(R.string.please_enter_service_price));
                                return;
                            }

                            saveService(Integer.parseInt(services.getId()), Integer.parseInt(serviceTime), Integer.parseInt(servicePrice), sheetDialog);
                        });

        sheetDialog.show();
    }

    private void deleteService(SalonServicesData services, ImageView imageView, AlertDialog dialog){

        HelperMethods.getMawedAPI()
                .deleteSalonService(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this), Integer.parseInt(services.getId()))
                .enqueue(new Callback<DeleteSalonService>() {
                    @Override
                    public void onResponse(Call<DeleteSalonService> call, Response<DeleteSalonService> response) {
                        if (response.isSuccessful()){
                            if (response.body().isStatus()){
                                if (services.getPrice() != null) {
                                    dialog.dismiss();
                                    Glide.with(SalonServicesActivity.this).load(R.drawable.ic_icon_service_uncheck).into(imageView);
                                    getServices();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DeleteSalonService> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });

    }
}
