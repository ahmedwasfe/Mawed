package com.raiyansoft.mawed.ui.fragments.user.booking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.adapter.PaymentMethodsAdapter;
import com.raiyansoft.mawed.adapter.ServicesPayingAdapter;
import com.raiyansoft.mawed.listener.PaymentMethodSelectListener;
import com.raiyansoft.mawed.model.BookingData;
import com.raiyansoft.mawed.model.payment.paymentMethods.PaymentMethodData;
import com.raiyansoft.mawed.model.payment.paymentMethods.PaymentMethods;
import com.raiyansoft.mawed.model.userOrders.checkout.CheckOut;
import com.raiyansoft.mawed.model.userOrders.coupon.CheckCoupon;
import com.raiyansoft.mawed.model.userOrders.sendOrder.SendService;
import com.raiyansoft.mawed.ui.activities.booking.BookingActivity;
import com.raiyansoft.mawed.ui.activities.paying.PayingActivity;
import com.raiyansoft.mawed.utils.Const;
import com.raiyansoft.mawed.utils.HelperMethods;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarberPayingFragment extends Fragment implements PaymentMethodSelectListener {
    private static final String TAG = BarberPayingFragment.class.getSimpleName();

    @BindView(R.id.pb_loading)
    ProgressBar loading;
    @BindView(R.id.recycler_payments)
    RecyclerView recyclerPaymentType;
    @BindView(R.id.recycler_services)
    RecyclerView recyclerServices;
    @BindView(R.id.tv_service_fee_price)
    TextView tvServiceFee;
    @BindView(R.id.tv_discount_price)
    TextView tvDiscount;
    @BindView(R.id.input_coupon)
    EditText inputCoupon;
    @BindView(R.id.input_customer_name)
    EditText inputCustomerName;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;

    private BookingActivity bookingActivity;

    private int couponDiscount;
    private String promoCode;

    private List<PaymentMethodData> listPayments;
    private PaymentMethodsAdapter paymentMethodsAdapter;
    private String paymentMethod;
    private int paymentId;

    private static BarberPayingFragment instance;

    public static BarberPayingFragment getInstance() {
        return instance == null ? new BarberPayingFragment() : instance;
    }

    @OnClick(R.id.btn_pay)
    void onPayClick() {

        if (paymentId == 0) {
            HelperMethods.showCustomToast(getActivity(), getString(R.string.please_select_payment_method), false);
            return;
        }

        checkOut();
//        startActivity(new Intent(getActivity(), BookingDoneActivity.class));
    }

    @OnClick(R.id.tv_apply_coupon)
    void onApplyCouponClick() {
        // TODO 1 = Fixed 2 = Precentage
        String coupon = inputCoupon.getText().toString();
        promoCode = coupon;
        applyCoupon(coupon);
    }

    private void applyCoupon(String coupon) {

        HelperMethods.getMawedAPI()
                .checkCoupon(HelperMethods.getAppLanguage(getActivity()),
                        HelperMethods.getUserToken(getActivity()),
                        coupon)
                .enqueue(new Callback<CheckCoupon>() {
                    @Override
                    public void onResponse(Call<CheckCoupon> call, Response<CheckCoupon> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getData() != null) {
                                    try {

                                        int discount = Integer.parseInt(response.body().getData().getDiscount());
                                        int totalDiscount = bookingActivity.serviceTotal - discount;
                                        bookingActivity.serviceTotal = totalDiscount;
                                        couponDiscount = discount;
                                        String textDiscount = new StringBuilder()
                                                .append(discount)
                                                .append(" ")
                                                .append(HelperMethods.getCurrency(getActivity()))
                                                .toString();
                                        String textTotal = new StringBuilder()
                                                .append(totalDiscount)
                                                .append(" ")
                                                .append(HelperMethods.getCurrency(getActivity()))
                                                .toString();

                                        tvDiscount.setText(textDiscount);
                                        tvTotalPrice.setText(textTotal);

                                        HelperMethods.showCustomToast(getActivity(), response.body().getMessage(), true);
                                    } catch (Exception e) {
                                        Log.d(TAG, "onResponse: " + e.getMessage());
                                    }
                                }
                            } else {
                                HelperMethods.showCustomToast(getActivity(), response.body().getMessage(), false);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckCoupon> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_barber_paying, container, false);
        ButterKnife.bind(this, layoutView);
        initUI();
        getPaymentsMethods();
        return layoutView;
    }

    private void initUI() {

        bookingActivity = (BookingActivity) getActivity();
        listPayments = new ArrayList<>();

        Log.d(TAG, "initUI EMPID: " + bookingActivity.employeeId);
        Log.d(TAG, "initUI SERVICES: " + new Gson().toJson(bookingActivity.listServiesSelected));
        Log.d(TAG, "initUI TOTAL: " + bookingActivity.serviceTotal);
        Log.d(TAG, "initUI NOTE: " + bookingActivity.note);
        Log.d(TAG, "initUI ADDRESS: " + bookingActivity.addressId);
        Log.d(TAG, "initUI HOME: " + bookingActivity.home);
        Log.d(TAG, "initUI DATE: " + bookingActivity.dateSelected);
        Log.d(TAG, "initUI TIME: " + bookingActivity.timeSelected);
        Log.d(TAG, "initUI CODE: " + promoCode);
        Log.d(TAG, "initUI DISCOUNT: " + couponDiscount);

        if (HelperMethods.getCurrentUser(getActivity()) != null){
            String fee = new StringBuilder()
                    .append(HelperMethods.getCurrentUser(getActivity()).getFee())
                            .append(" ")
                                    .append(HelperMethods.getCurrency(getActivity()))
                                            .toString();
            tvServiceFee.setText(fee);
        }



        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerServices.setHasFixedSize(true);
        recyclerServices.setLayoutManager(layoutManager);

        LinearLayoutManager layoutPayment = new LinearLayoutManager(getActivity());
        layoutPayment.setOrientation(RecyclerView.VERTICAL);
        recyclerPaymentType.setHasFixedSize(true);
        recyclerPaymentType.setLayoutManager(layoutPayment);

        paymentMethodsAdapter = new PaymentMethodsAdapter(getActivity(), listPayments, this);

        String totalPrice = new StringBuilder()
                .append(bookingActivity.serviceTotal)
                .append(" ")
                .append(HelperMethods.getCurrency(getActivity()))
                .toString();
        tvTotalPrice.setText(totalPrice);
        ServicesPayingAdapter servicesOrderSamaryAdapter = new ServicesPayingAdapter(getActivity(), bookingActivity.listServiesSelected);
        recyclerServices.setAdapter(servicesOrderSamaryAdapter);
    }

    private void checkOut() {

        Log.d(TAG, "initUI EMPID: " + bookingActivity.employeeId);
        Log.d(TAG, "initUI SERVICES: " + new Gson().toJson(bookingActivity.listServiesSelected));
        Log.d(TAG, "initUI TOTAL: " + bookingActivity.serviceTotal);
        Log.d(TAG, "initUI NOTE: " + bookingActivity.note);
        Log.d(TAG, "initUI ADDRESS: " + bookingActivity.addressId);
        Log.d(TAG, "initUI HOME: " + bookingActivity.home);
        Log.d(TAG, "initUI DATE: " + bookingActivity.dateSelected);
        Log.d(TAG, "initUI TIME: " + bookingActivity.timeSelected);
        Log.d(TAG, "initUI CODE: " + promoCode);
        Log.d(TAG, "initUI DISCOUNT: " + couponDiscount);


        int fee = HelperMethods.getCurrentUser(getActivity()).getFee();
//        int total = bookingActivity.serviceTotal + fee;
        int total = bookingActivity.serviceTotal;
        Log.d(TAG, "checkOut SERID: " + bookingActivity.timeSelected.substring(0, 5));
        Log.d(TAG, "checkOut TOTAL: " + bookingActivity.serviceTotal);
        Log.d(TAG, "checkOut TOTAL WITH FEE: " + total);

        Map<String, Object> fields = new HashMap<>();
        // TODO services[][item_id]
        for (int i = 0; i < bookingActivity.listServiesSelected.size(); i++) {
            SendService service = bookingActivity.listServiesSelected.get(i);
            fields.put("services[" + i + "][item_id]", service.getItemId());
            fields.put("services[" + i + "][price]", service.getPrice());
            fields.put("services[" + i + "][title]", service.getTitle());
            fields.put("services[" + i + "][time]", service.getTime());
        }
        Log.d(TAG, "checkOut: " + new Gson().toJson(fields));

        loading.setVisibility(View.VISIBLE);
        HelperMethods.getMawedAPI()
                .checkOut(HelperMethods.getAppLanguage(getActivity()),
                        HelperMethods.getUserToken(getActivity()),
                        fields,
                        bookingActivity.employeeId, total,
                        bookingActivity.note, bookingActivity.addressId,
                        bookingActivity.dateSelected, bookingActivity.timeSelected.substring(0, 5),
                        bookingActivity.home, paymentId, promoCode, couponDiscount)
                .enqueue(new Callback<CheckOut>() {
                    @Override
                    public void onResponse(Call<CheckOut> call, Response<CheckOut> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getData() != null) {
                                    BookingData bookingData = new BookingData();
                                    bookingData.setPaymentUrl(response.body().getData().getUrl());
                                    bookingData.setOrderId(response.body().getData().getOrderId());
                                    bookingData.setSalonName(HelperMethods.salonName);
                                    bookingData.setTotal(String.valueOf(bookingActivity.serviceTotal));
                                    bookingData.setPaymentDate(bookingActivity.dateSelected);
                                    bookingData.setPaymentMethod(paymentMethod);

                                    Log.d(TAG, "onResponse: " + new Gson().toJson(bookingData));

//                                    startActivity(new Intent(getActivity(), BookingDoneActivity.class)
//                                            .putExtra(Const.KEY_BOOKING_DATA, bookingData));
                                    startActivity(new Intent(getActivity(), PayingActivity.class)
                                            .putExtra(Const.KEY_PAYING_URL, response.body().getData().getUrl())
                                            .putExtra(Const.KEY_BOOKING_DATA, bookingData));
                                    Log.d(TAG, "onResponse URL: " + response.body().getData().getUrl());
                                    Log.d(TAG, "onResponse ID: " + response.body().getData().getOrderId());
                                }
                            } else {
                                Log.d(TAG, "onResponse: " + response.body().getMessage());
                                if (response.body().getMessage().equals("كود الخصم غير متاح")) {

                                    HelperMethods.getMawedAPI()
                                            .checkOut(HelperMethods.getAppLanguage(getActivity()),
                                                    HelperMethods.getUserToken(getActivity()),
                                                    fields,
                                                    bookingActivity.employeeId, total,
                                                    bookingActivity.note, bookingActivity.addressId,
                                                    bookingActivity.dateSelected, bookingActivity.timeSelected.substring(0, 5),
                                                    bookingActivity.home, paymentId, "", 0)
                                            .enqueue(new Callback<CheckOut>() {
                                                @Override
                                                public void onResponse(Call<CheckOut> call, Response<CheckOut> response) {
                                                    loading.setVisibility(View.GONE);
                                                    if (response.isSuccessful()) {
                                                        if (response.body().isStatus()) {
                                                            if (response.body().getData() != null) {

                                                                BookingData bookingData = new BookingData();
                                                                bookingData.setPaymentUrl(response.body().getData().getUrl());
                                                                bookingData.setOrderId(response.body().getData().getOrderId());
                                                                bookingData.setSalonName(HelperMethods.salonName);
                                                                bookingData.setTotal(String.valueOf(bookingActivity.serviceTotal));
                                                                bookingData.setPaymentMethod(paymentMethod);
                                                                bookingData.setPaymentDate(bookingActivity.dateSelected);

                                                                Log.d(TAG, "onResponse: " + new Gson().toJson(bookingData));

//                                                                startActivity(new Intent(getActivity(), BookingDoneActivity.class)
//                                                                        .putExtra(Const.KEY_APPOINTMENT_ID, response.body().getData().getOrderId()));
                                                                startActivity(new Intent(getActivity(), PayingActivity.class)
                                                                        .putExtra(Const.KEY_PAYING_URL, response.body().getData().getUrl())
                                                                        .putExtra(Const.KEY_BOOKING_DATA, bookingData));
                                                                Log.d(TAG, "onResponse URL: " + response.body().getData().getUrl());
                                                                Log.d(TAG, "onResponse ID: " + response.body().getData().getOrderId());
                                                            }
                                                        } else {
                                                            Log.d(TAG, "onResponse: " + response.body().getMessage());
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<CheckOut> call, Throwable t) {
                                                    Log.e(TAG, "onFailure: " + t.getMessage());
                                                    loading.setVisibility(View.GONE);
                                                }
                                            });

                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckOut> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                        loading.setVisibility(View.GONE);
                    }
                });
    }

    private void getPaymentsMethods() {

        HelperMethods.getMawedAPI()
                .getPaymentMethods(HelperMethods.getAppLanguage(getActivity()))
                .enqueue(new Callback<PaymentMethods>() {
                    @Override
                    public void onResponse(Call<PaymentMethods> call, Response<PaymentMethods> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().isStatus()) {
                                    listPayments.clear();
                                    listPayments.addAll(response.body().getData());
                                    paymentMethod = listPayments.get(0).getTitle();
//                                    paymentId = listPayments.get(0).getId();
                                    recyclerPaymentType.setAdapter(paymentMethodsAdapter);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PaymentMethods> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    @Override
    public void onPaymentSelected(PaymentMethodData payment, int pos) {
        paymentMethod = payment.getTitle();
        paymentId = payment.getId();
        Log.d(TAG, "onPaymentSelected: " + paymentMethod);

    }
}
