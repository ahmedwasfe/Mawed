package com.raiyansoft.mawed.ui.fragments.user.myDates;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.adapter.UserPastAppointmentsAdapter;
import com.raiyansoft.mawed.adapter.UserUpcomingDatesAdapter;
import com.raiyansoft.mawed.model.userOrders.UserOrders;
import com.raiyansoft.mawed.model.userOrders.UserOrdersData;
import com.raiyansoft.mawed.ui.activities.auth.LoginActivity;
import com.raiyansoft.mawed.utils.HelperMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDatesFragment extends Fragment {
    private static final String TAG = MyDatesFragment.class.getSimpleName();

    @BindView(R.id.layout_login)
    CardView cardLogin;
    @BindView(R.id.tv_text_logout)
    TextView tvTextLogout;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.btn_cancel)
    Button btnCancel;

    @BindView(R.id.pb_loading)
    ProgressBar loading;

    @BindView(R.id.tv_text_upcoming_dates)
    TextView tvTextUpcomingDates;
    @BindView(R.id.tv_text_past_appointments)
    TextView tvTextPastAppointments;

    @BindView(R.id.recycler_upcoming_dates)
    RecyclerView recyclerUpcomingDate;
    @BindView(R.id.recycler_past_appointments)
    RecyclerView recyclerPastAppointments;

    private List<UserOrdersData> listUpcomingDatesOrders;
    private List<UserOrdersData> listPastAppointmentsOrders;


    private static MyDatesFragment instance;

    public static MyDatesFragment getInstance() {
        return instance == null ? new MyDatesFragment() : instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_my_dates, container, false);
        ButterKnife.bind(this, layoutView);
        initUI();
        getUpcomingDates();
        getPastAppointments();
        return layoutView;
    }

    private void initUI() {

        if (HelperMethods.getCurrentUser(getActivity()) == null){
            tvTextUpcomingDates.setVisibility(View.GONE);
            tvTextPastAppointments.setVisibility(View.GONE);
        }
        showLogin(getActivity(), recyclerPastAppointments, cardLogin, tvTextLogout, btnLogout, btnCancel);

        listUpcomingDatesOrders = new ArrayList<>();
        listPastAppointmentsOrders = new ArrayList<>();


        LinearLayoutManager layoutManagerUpcomingDates = new LinearLayoutManager(getActivity());
        layoutManagerUpcomingDates.setOrientation(RecyclerView.VERTICAL);
        recyclerUpcomingDate.setHasFixedSize(true);
        recyclerUpcomingDate.setLayoutManager(layoutManagerUpcomingDates);

        LinearLayoutManager layoutManagerPastAppointments = new LinearLayoutManager(getActivity());
        layoutManagerPastAppointments.setOrientation(RecyclerView.VERTICAL);
        recyclerPastAppointments.setHasFixedSize(true);
        recyclerPastAppointments.setLayoutManager(layoutManagerPastAppointments);
    }

    private void showLogin(Activity activity,
                           RecyclerView scrollProfile, CardView cardLogin,
                           TextView tvTextLogout, Button btnLogout, Button btnCancel) {
        btnCancel.setVisibility(View.GONE);
        if (HelperMethods.getCurrentUser(activity) != null){
            scrollProfile.setVisibility(View.VISIBLE);
            cardLogin.setVisibility(View.GONE);

            tvTextLogout.setText(activity.getString(R.string.sure_log_out));
            btnLogout.setText(activity.getString(R.string.logout));

        }else{
            scrollProfile.setVisibility(View.GONE);
            cardLogin.setVisibility(View.VISIBLE);

            tvTextLogout.setText(activity.getString(R.string.please_login));
            btnLogout.setText(activity.getString(R.string.log_in));

            btnLogout.setOnClickListener(v -> {
                activity.startActivity(new Intent(activity, LoginActivity.class));
                activity.finish();
            });
        }
    }

    private void getUpcomingDates() {

        if (HelperMethods.getCurrentUser(getActivity()) != null) {

            loading.setVisibility(View.VISIBLE);
            Log.d(TAG, "getOrders: ");
            HelperMethods.getMawedAPI()
                    .getUserOrdersUpcomingDates(HelperMethods.getAppLanguage(getActivity()),
                            HelperMethods.getUserToken(getActivity()))
                    .enqueue(new Callback<UserOrders>() {
                        @Override
                        public void onResponse(Call<UserOrders> call, Response<UserOrders> response) {
                            loading.setVisibility(View.GONE);
                            if (response.isSuccessful()) {
                                if (response.body().isStatus()) {
                                    if (response.body().getData() != null) {

//                                    Log.d(TAG, "onResponse: " + new Gson().toJson(response.body().getData().getOrders()));
                                        listUpcomingDatesOrders.clear();
                                        listUpcomingDatesOrders.addAll(response.body().getData().getOrders());
                                        Log.d(TAG, "onResponse: " + listUpcomingDatesOrders.size());
                                        UserUpcomingDatesAdapter userUpcomingDatesAdapter = new UserUpcomingDatesAdapter(getActivity(), listUpcomingDatesOrders);
                                        recyclerUpcomingDate.setAdapter(userUpcomingDatesAdapter);

                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<UserOrders> call, Throwable t) {
                            Log.e(TAG, "onFailure: " + t.getMessage());
                            loading.setVisibility(View.GONE);
                        }
                    });

        }

    }

    private void getPastAppointments() {

        if (HelperMethods.getCurrentUser(getActivity()) != null) {

            loading.setVisibility(View.VISIBLE);
            Log.d(TAG, "getOrders: ");
            HelperMethods.getMawedAPI()
                    .getUserOrdersPastAppointments(HelperMethods.getAppLanguage(getActivity()),
                            HelperMethods.getUserToken(getActivity()))
                    .enqueue(new Callback<UserOrders>() {
                        @Override
                        public void onResponse(Call<UserOrders> call, Response<UserOrders> response) {
                            loading.setVisibility(View.GONE);
                            if (response.isSuccessful()) {
                                if (response.body().isStatus()) {
                                    if (response.body().getData() != null) {

//                                    Log.d(TAG, "onResponse: " + new Gson().toJson(response.body().getData().getOrders()));
                                        listPastAppointmentsOrders.clear();
                                        listPastAppointmentsOrders.addAll(response.body().getData().getOrders());
                                        Log.d(TAG, "onResponse: " + listPastAppointmentsOrders.size());
                                        UserPastAppointmentsAdapter userPastAppointmentsAdapter = new UserPastAppointmentsAdapter(getActivity(), listPastAppointmentsOrders);
                                        recyclerPastAppointments.setAdapter(userPastAppointmentsAdapter);

                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<UserOrders> call, Throwable t) {
                            Log.e(TAG, "onFailure: " + t.getMessage());
                            loading.setVisibility(View.GONE);
                        }
                    });

        }

    }
}
