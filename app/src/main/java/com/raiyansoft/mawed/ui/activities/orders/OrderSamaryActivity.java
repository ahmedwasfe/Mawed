package com.raiyansoft.mawed.ui.activities.orders;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.raiyansoft.mawed.MapActivity;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.adapter.ServicesOrderSamaryAdapter;
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

public class OrderSamaryActivity extends AppCompatActivity {
    private static final String TAG = OrderSamaryActivity.class.getSimpleName();

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

    private UserAppointmentData orderDetails;
    private Integer id;
    private double lat;
    private double lng;
    private String salonName;
    private List<UserAppointmentService> listServices;

    private String phoneNumber;

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

    @OnClick(R.id.btn_call)
    void onCallClick() {
        Log.d(TAG, "onCallClick: " + phoneNumber);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        HelperMethods.checkAppLanguage(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_samary);
        ButterKnife.bind(this);
        initUI();
        loadAppointmentDetails();
    }

    private void initUI() {

        if (getIntent() != null)
            id = getIntent().getIntExtra(Const.KEY_APPOINTMENT_ID, 0);

        Log.d(TAG, "initUI: " + id);
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
                        HelperMethods.getUserToken(this), id)
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
                        llOrderDetails.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void getAppointmentDetails(UserAppointmentData appointmentDetails) {

        if (appointmentDetails != null) {

            if (appointmentDetails.getAddress() != null) {
                lat = appointmentDetails.getAddress().getLat();
                lng = appointmentDetails.getAddress().getLng();
            }

            salonName = appointmentDetails.getSalon();
            orderDetails = appointmentDetails;
            phoneNumber = appointmentDetails.getCallNumber();
            tvOrderNumber.setText(String.valueOf(appointmentDetails.getId()));
            tvOrderTime.setText(appointmentDetails.getBookingTime());
            tvOrderStatus.setText(appointmentDetails.getStatus());
            tvSalonName.setText(appointmentDetails.getSalon());
            if (appointmentDetails.getEmployee() != null)
                tvEmployeeName.setText(appointmentDetails.getEmployee());
            tvPaymentMethod.setText(appointmentDetails.getPayment());

//            Log.d(TAG, "getAppointmentDetails: " + appointmentDetails.getPromoCode().getCode());
            if (appointmentDetails.getPromoCode() == null) {
                tvDiscountCoupon.setText(getString(R.string.nothing));
                tvDiscountPrice.setText("0");
            }else {
                tvDiscountCoupon.setText(appointmentDetails.getPromoCode().getCode());
                String discount = new StringBuilder()
                        .append(appointmentDetails.getDiscount())
                        .append(" ")
                        .append(HelperMethods.getCurrency(this))
                        .toString();
//                Log.d(TAG, "getAppointmentDetails Dis: " + appointmentDetails.getDiscount());
                if (!appointmentDetails.getDiscount().equals(""))
                    tvDiscountPrice.setText(discount);
                else {
                    tvDiscountPrice.setText("0");
                }
            }
            String total = new StringBuilder()
                    .append(appointmentDetails.getTotal())
                    .append(" ")
                    .append(HelperMethods.getCurrency(this))
                    .toString();
            tvTotalPrice.setText(total);
            tvBookingDate.setText(appointmentDetails.getBookingDate());
            tvBookingTime.setText(appointmentDetails.getBookingTime());

            if (HelperMethods.getCurrentUser(this) != null){
                String fee = new StringBuilder()
                        .append(HelperMethods.getCurrentUser(this).getFee())
                        .append(" ")
                        .append(HelperMethods.getCurrency(this))
                        .toString();
                tvServiceFeePrice.setText(fee);
            }

            if (appointmentDetails.getServices() != null && !appointmentDetails.getServices().isEmpty()) {
                listServices.clear();
                listServices.addAll(appointmentDetails.getServices());
                ServicesOrderSamaryAdapter servicesOrderSamaryAdapter = new ServicesOrderSamaryAdapter(this, listServices);
                recyclerServices.setAdapter(servicesOrderSamaryAdapter);
            }

            // TODO 1 تم الحجز بنجاح / 2 تم الحضور / 3 موعد مضى
            if (appointmentDetails.getStatus().equals("1")) {
                tvOrderStatus.setText(getString(R.string.reservation_done));
            } else if (appointmentDetails.getStatus().equals("2")) {
                tvOrderStatus.setText(getString(R.string.attended));
            } else if (appointmentDetails.getStatus().equals("3")) {
                tvOrderStatus.setText(getString(R.string.past_date));
            }
        }

    }
}
