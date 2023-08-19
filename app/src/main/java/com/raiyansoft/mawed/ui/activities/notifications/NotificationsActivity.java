package com.raiyansoft.mawed.ui.activities.notifications;

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
import com.raiyansoft.mawed.adapter.NotificationsAdapter;
import com.raiyansoft.mawed.listener.NotificationReadListener;
import com.raiyansoft.mawed.model.notifications.NotificationData;
import com.raiyansoft.mawed.model.notifications.Notifications;
import com.raiyansoft.mawed.model.notifications.ReadNotification;
import com.raiyansoft.mawed.ui.activities.orders.OrderSamaryActivity;
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

public class NotificationsActivity extends AppCompatActivity implements NotificationReadListener {
    private static final String TAG = NotificationsActivity.class.getSimpleName();
    @BindView(R.id.pb_loading)
    ProgressBar loading;
    @BindView(R.id.recycler_notifications)
    RecyclerView recyclerNotifications;

    private List<NotificationData> listNotifications;

    @OnClick(R.id.iv_back)
    void onBackCLick(){
        onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        ButterKnife.bind(this);
        initUI();
        getNotifications();
    }

    private void initUI() {

        listNotifications = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerNotifications.setHasFixedSize(true);
        recyclerNotifications.setLayoutManager(layoutManager);
    }

    private void getNotifications() {

        loading.setVisibility(View.VISIBLE);

        HelperMethods.getMawedAPI()
                .getNotifications(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this))
                .enqueue(new Callback<Notifications>() {
                    @Override
                    public void onResponse(Call<Notifications> call, Response<Notifications> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()){
                            if (response.body().isStatus()){
                                if (response.body().getData() != null){
                                    listNotifications.clear();
                                    listNotifications.addAll(response.body().getData().getNotifiactions());
                                    NotificationsAdapter notificationsAdapter = new NotificationsAdapter(NotificationsActivity.this, listNotifications, NotificationsActivity.this);
                                    recyclerNotifications.setAdapter(notificationsAdapter);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Notifications> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                        loading.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onReadNotification(NotificationData notification, int pos) {
        readNotification(notification.getId());
        if(notification.getOrderId() != null){
            startActivity(new Intent(this, OrderSamaryActivity.class)
                    .putExtra(Const.KEY_APPOINTMENT_ID, notification.getOrderId()));
        }
    }

    void readNotification(int notificationId) {
        HelperMethods.getMawedAPI()
                .readNotification(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this),
                        notificationId)
                .enqueue(new Callback<ReadNotification>() {
                    @Override
                    public void onResponse(Call<ReadNotification> call, Response<ReadNotification> response) {
                        if(response.isSuccessful()){
                            if (response.body().isStatus()){
                                getNotifications();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ReadNotification> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage() );
                    }
                });
    }
}
