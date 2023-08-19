package com.raiyansoft.mawed.ui.activities.salon;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.raiyansoft.mawed.MapActivity;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.adapter.ServicesOrderSamaryAdapter;
import com.raiyansoft.mawed.model.salon.updateStatus.UpdateOrderStatus;
import com.raiyansoft.mawed.model.userOrders.UserAppointmentData;
import com.raiyansoft.mawed.model.userOrders.UserAppointmentDetails;
import com.raiyansoft.mawed.model.userOrders.UserAppointmentService;
import com.raiyansoft.mawed.utils.Const;
import com.raiyansoft.mawed.utils.HelperMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalonOrderSamaryActivity extends AppCompatActivity {
    private static final String TAG = SalonOrderSamaryActivity.class.getSimpleName();

    @BindView(R.id.swipe)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.ll_order_details)
    LinearLayout llOrderDetails;
    @BindView(R.id.pb_loading)
    ProgressBar loading;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.tv_salon_name)
    TextView tvSalonName;
    @BindView(R.id.tv_Employee_name)
    TextView tvEmployeeName;
    @BindView(R.id.tv_payment_method)
    TextView tvPaymentMethod;
    @BindView(R.id.tv_discount_coupon)
    TextView tvDiscountCoupon;
    @BindView(R.id.tv_service_fee_price)
    TextView tvServiceFeePrice;
    @BindView(R.id.tv_discount_price)
    TextView tvDiscountPrice;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_booking_date)
    TextView tvBookingDate;
    @BindView(R.id.tv_booking_time)
    TextView tvBookingTime;
    @BindView(R.id.recycler_services)
    RecyclerView recyclerServices;

    @BindView(R.id.btn_prepare)
    Button btnPrepare;
    @BindView(R.id.tv_attended)
    TextView tvAttended;

    private Integer orderId;

    private double lat;
    private double lng;
    private String salonName;
    private List<UserAppointmentService> listServices;

    @OnClick(R.id.iv_back)
    void onBackClick() {
        onBackPressed();
    }

    @OnClick(R.id.rel_location)
    void onLocationClick() {
        startActivity(new Intent(this, MapActivity.class)
                .putExtra(Const.KEY_SALON_LATITUDE,lat)
                .putExtra(Const.KEY_SALON_LONGITUDE, lng)
                .putExtra(Const.KEY_SALON_NAME,salonName));
    }

    @OnClick(R.id.btn_prepare)
    void onOrepareOrderClick() {
        updateOrderStatus();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_order_samary);
        ButterKnife.bind(this);
        initUI();
        loadAppointmentDetails();
    }

    private void initUI() {

        if (getIntent() != null)
            orderId = getIntent().getIntExtra(Const.KEY_APPOINTMENT_ID, 11);

        Log.d(TAG, "initUI: " + orderId);
        listServices = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerServices.setHasFixedSize(true);
        recyclerServices.setLayoutManager(layoutManager);

        refreshLayout.setOnRefreshListener(() -> {
            loadAppointmentDetails();
            refreshLayout.setRefreshing(false);
        });
    }

    private void loadAppointmentDetails() {

        loading.setVisibility(View.VISIBLE);
        llOrderDetails.setVisibility(View.GONE);

        HelperMethods.getMawedAPI()
                .getUserAppointmentDetails(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this), orderId)
                .enqueue(new Callback<UserAppointmentDetails>() {
                    @Override
                    public void onResponse(Call<UserAppointmentDetails> call, Response<UserAppointmentDetails> response) {
                        loading.setVisibility(View.GONE);
                        llOrderDetails.setVisibility(View.VISIBLE);
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getData() != null) {

                                    getAppointmentDetails(response.body().getData());
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserAppointmentDetails> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                        loading.setVisibility(View.GONE);
                    }
                });
    }

    private void getAppointmentDetails(UserAppointmentData orderDetails) {

        if (orderDetails != null) {

            if (orderDetails.getAddress() != null) {
                lat = orderDetails.getAddress().getLat();
                lng = orderDetails.getAddress().getLng();
            }
            salonName = orderDetails.getSalon();
            tvOrderNumber.setText(String.valueOf(orderDetails.getId()));
            tvOrderTime.setText(orderDetails.getBookingTime());
            tvOrderStatus.setText(orderDetails.getStatus());
            tvSalonName.setText(orderDetails.getSalon());
            tvPaymentMethod.setText(orderDetails.getPayment());

            if (orderDetails.getPromoCode() == null)
                tvDiscountCoupon.setText(getString(R.string.nothing));
            else
                tvDiscountCoupon.setText(orderDetails.getPromoCode().getCode());

            tvTotalPrice.setText(orderDetails.getTotal());
            tvBookingDate.setText(orderDetails.getBookingDate());
            tvBookingTime.setText(orderDetails.getBookingTime());

            // TODO 1 تم الحجز بنجاح / 2 تم الحضور / 3 موعد مضى
            if (orderDetails.getStatus().equals("1")) {
                tvOrderStatus.setText(getString(R.string.reservation_done));
            } else if (orderDetails.getStatus().equals("2")) {
                tvOrderStatus.setText(getString(R.string.attended));
            } else if (orderDetails.getStatus().equals("3")) {
                tvOrderStatus.setText(getString(R.string.past_date));
            }

            if (orderDetails.getServices() != null && !orderDetails.getServices().isEmpty()) {
                listServices.clear();
                listServices.addAll(orderDetails.getServices());
                ServicesOrderSamaryAdapter servicesOrderSamaryAdapter = new ServicesOrderSamaryAdapter(this, listServices);
                recyclerServices.setAdapter(servicesOrderSamaryAdapter);
            }
        }

    }

    private void updateOrderStatus() {

        loading.setVisibility(View.VISIBLE);
        HelperMethods.getMawedAPI()
                .updateOrderStatus(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this),
                        orderId, 2)
                .enqueue(new Callback<UpdateOrderStatus>() {
                    @Override
                    public void onResponse(Call<UpdateOrderStatus> call, Response<UpdateOrderStatus> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getData() != null) {
                                    HelperMethods.showCustomToast(SalonOrderSamaryActivity.this, getString(R.string.change_order_status_success), true);
                                    finish();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateOrderStatus> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                        loading.setVisibility(View.GONE);

                    }
                });
    }
}
