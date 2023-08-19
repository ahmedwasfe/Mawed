package com.raiyansoft.mawed.ui.activities.paying;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.raiyansoft.mawed.MapActivity;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.model.BookingData;
import com.raiyansoft.mawed.model.payment.paymentStatus.PaymentStatus;
import com.raiyansoft.mawed.ui.activities.orders.OrderSamaryActivity;
import com.raiyansoft.mawed.utils.Const;
import com.raiyansoft.mawed.utils.HelperMethods;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingDoneActivity extends AppCompatActivity {
    private static final String TAG = BookingDoneActivity.class.getSimpleName();

    @BindView(R.id.rel_container)
    RelativeLayout relContainer;
    @BindView(R.id.iv_qr_code)
    ImageView ivQRCode;

    @BindView(R.id.iv_circle_start)
    ImageView ivCircleStart;
    @BindView(R.id.iv_circle_end)
    ImageView ivCircleEnd;

    @BindView(R.id.tv_salon_name)
    TextView tvSalonName;
    @BindView(R.id.tv_price)
    TextView tvTotal;
    @BindView(R.id.tv_payment_method)
    TextView tvPaymentMethod;
    @BindView(R.id.tv_payment_date)
    TextView tvPaymentDate;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;

    private BookingData booking;
    private String paymentStatus;

    @OnClick(R.id.tv_details)
    void onDetailsClick(){
        startActivity(new Intent(this, OrderSamaryActivity.class)
                .putExtra(Const.KEY_APPOINTMENT_ID, booking.getOrderId()));
    }

    @OnClick(R.id.tv_location)
    void onLocationClick() {
        startActivity(new Intent(this, MapActivity.class)
                .putExtra(Const.KEY_SALON_LATITUDE, booking.getLatitude())
                .putExtra(Const.KEY_SALON_LONGITUDE, booking.getLongitude())
                .putExtra(Const.KEY_SALON_NAME, booking.getSalonName()));
    }

    @OnClick(R.id.tv_share)
    void onShareClick() {
        shareQRCode();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        HelperMethods.checkAppLanguage(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_done);
        ButterKnife.bind(this);

        if (getIntent() != null){
            booking = (BookingData) getIntent().getSerializableExtra(Const.KEY_BOOKING_DATA);
            paymentStatus = getIntent().getStringExtra(Const.KEY_PAYMENT_STATUS);
        }

        Log.d(TAG, "onCreate: " + paymentStatus);
        if (paymentStatus != null) {
            if (paymentStatus == Const.PAYMENT_STATUS_SUCCESS) {
                changePaymentStatus(1);
                HelperMethods.showSnackBar(this,
                        relContainer, getString(R.string.order_done), true,
                        Snackbar.LENGTH_SHORT,
                        Gravity.TOP);
            } else {
                changePaymentStatus(0);
                HelperMethods.showSnackBar(this,
                        relContainer, getString(R.string.order_done), false,
                        Snackbar.LENGTH_SHORT,
                        Gravity.TOP);
            }

        }
        String totalPrice = new StringBuilder()
                .append(booking.getTotal())
                        .append("")
                                .append(HelperMethods.getCurrency(this))
                                        .toString();

        tvSalonName.setText(booking.getSalonName());
        tvTotal.setText(totalPrice);
        tvPaymentMethod.setText(booking.getPaymentMethod());
        tvPaymentDate.setText(booking.getPaymentDate());
        tvOrderNumber.setText(String.valueOf(booking.getOrderId()));


        generationQRCode(String.valueOf(booking.getOrderId()));

        if (HelperMethods.getAppLanguage(this).equals(Const.KEY_LANGUAGE_EN))
            Glide.with(this).load(R.drawable.bg_circle_start).into(ivCircleStart);
        else
            Glide.with(this).load(R.drawable.bg_circle_end).into(ivCircleStart);

        if (HelperMethods.getAppLanguage(this).equals(Const.KEY_LANGUAGE_EN))
            Glide.with(this).load(R.drawable.bg_circle_end).into(ivCircleEnd);
        else
            Glide.with(this).load(R.drawable.bg_circle_start).into(ivCircleEnd);
    }

    Bitmap bitmap;

    private void generationQRCode(String result) {

        // initialize multi format writer
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            // initialize bit materix
            BitMatrix matrix = writer.encode(result, BarcodeFormat.QR_CODE, 350, 350);
            // initialize barcode encoder
            BarcodeEncoder encoder = new BarcodeEncoder();
            // initialize bitmap
            Bitmap bitmap = encoder.createBitmap(matrix);
            this.bitmap = bitmap;
            // set bitmap on imageview
            ivQRCode.setImageBitmap(bitmap);
            // initialize input manager
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            // Hide soft keyboard
            manager.hideSoftInputFromWindow(ivQRCode.getApplicationWindowToken(), 0);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void shareQRCode() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
        Uri imageUri =  Uri.parse(path);
        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(share, "Select"));
    }

    private void changePaymentStatus(int status) {

        HelperMethods.getMawedAPI()
                .changePaymentStatus(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this),
                        status, booking.getOrderId(), "order")
                .enqueue(new Callback<PaymentStatus>() {
                    @Override
                    public void onResponse(Call<PaymentStatus> call, Response<PaymentStatus> response) {
                        if(response.isSuccessful()){
                            if (response.body() != null){
                                if (response.body().isStatus()){

                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PaymentStatus> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage() );
                    }
                });
    }
}
