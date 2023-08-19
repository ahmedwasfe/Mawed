package com.raiyansoft.mawed.ui.activities.paying;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.model.BookingData;
import com.raiyansoft.mawed.utils.Const;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayingActivity extends AppCompatActivity {
    private static final String TAG = PayingActivity.class.getSimpleName();

    @BindView(R.id.wv_load_payment)
    WebView wbPaying;

    private String payingUrl;
    private BookingData booking;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paying);
        ButterKnife.bind(this);
        initUI();

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initUI() {

        if (getIntent() != null) {
            booking = (BookingData) getIntent().getSerializableExtra(Const.KEY_BOOKING_DATA);

            if (booking == null)
                payingUrl = getIntent().getStringExtra(Const.KEY_PAYING_URL);
        }

//        Log.d(TAG, "initUI: " + booking.getPaymentUrl());

        wbPaying.loadUrl(booking != null ? booking.getPaymentUrl() : payingUrl);
        wbPaying.setWebChromeClient(new WebChromeClient());
        wbPaying.getSettings().setJavaScriptEnabled(true);
        wbPaying.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                Log.d(TAG, "onPageStarted: " + url);
//                Toast.makeText(PayingActivity.this, "Start Page", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                Log.d(TAG, "onPageFinished: " + url);
//                Toast.makeText(PayingActivity.this, "End Page", Toast.LENGTH_SHORT).show();
//                finish();


            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

//                Log.d(TAG, "onReceivedError: " + new Gson().toJson(error));
//                Log.d(TAG, "onReceivedError: " + error.toString());
//                Log.d(TAG, "onReceivedError: " + error.getDescription());
//                Log.d(TAG, "onReceivedError: " + new Gson().toJson(request));
//                Log.d(TAG, "onReceivedError: " + request.getMethod());
//                Log.d(TAG, "onReceivedError: " + request.getUrl());
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                Log.d(TAG, "onReceivedHttpError: " + errorResponse.toString());
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                Log.d(TAG, "onReceivedSslError: " + error.getUrl());
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.d(TAG, "shouldOverrideUrlLoading: " + new Gson().toJson(request));
                if (request.getUrl().toString().contains("http://mawed.work/api/callback/error") ||
                request.getUrl().toString().contains("https://www.kpay.com.kw/kpg/paymentpage.htm?PaymentID=101202316196276795")) {
                    Log.d(TAG, "shouldOverrideUrlLoading IF: " + new Gson().toJson(request));
                    Log.d(TAG, "shouldOverrideUrlLoading:  FAILED" );
                    startActivity(new Intent(PayingActivity.this, BookingDoneActivity.class)
                            .putExtra(Const.KEY_BOOKING_DATA, booking)
                            .putExtra(Const.KEY_PAYMENT_STATUS, Const.PAYMENT_STATUS_FAILED));
                }
//                else if(request.getUrl().toString().contains("https://www.kpay.com.kw/kpg/paymentpage.htm?PaymentID=")){
//                    Log.d(TAG, "shouldOverrideUrlLoading ELSEIF: " + new Gson().toJson(request));
//
////                    startActivity(new Intent(PayingActivity.this, BookingDoneActivity.class)
////                            .putExtra(Const.KEY_BOOKING_DATA, booking)
////                            .putExtra(Const.KEY_PAYMENT_STATUS, Const.PAYMENT_STATUS_SUCCESS));
//                }
                else {
                    Log.d(TAG, "shouldOverrideUrlLoading ELSE: " + new Gson().toJson(request));
                }
                return true;

            }
        });
    }
}
