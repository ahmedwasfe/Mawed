package com.raiyansoft.mawed.ui.activities.salon;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.adapter.salon.SalonOrdersAdapter;
import com.raiyansoft.mawed.adapter.salon.SalonEmployeeAdapter;
import com.raiyansoft.mawed.listener.EmployeeSelectedListener;
import com.raiyansoft.mawed.model.salon.Employee;
import com.raiyansoft.mawed.model.salon.OrderData;
import com.raiyansoft.mawed.model.salon.Orders;
import com.raiyansoft.mawed.model.salon.management.ManagementSalon;
import com.raiyansoft.mawed.model.userSalon.EmployeeData;
import com.raiyansoft.mawed.utils.HelperMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatesOrdersActivity extends AppCompatActivity implements View.OnClickListener, EmployeeSelectedListener {
    private static final String TAG = DatesOrdersActivity.class.getSimpleName();

    @BindView(R.id.pb_loading)
    ProgressBar loading;
    @BindView(R.id.tv_upcoming)
    TextView tabUpcoming;
    @BindView(R.id.tv_archive)
    TextView tabArchive;

    @BindView(R.id.rel_employees)
    RelativeLayout relEmployees;
    @BindView(R.id.recycler_employees)
    RecyclerView recyclerEmpolyees;
    @BindView(R.id.tv_tab_selected)
    TextView tabSelect;

    @BindView(R.id.recycerl_orders)
    RecyclerView recyclerOrders;

    private List<EmployeeData> listEmployees;
    private List<OrderData> listOrders;
    private Integer employeeId;

    @OnClick(R.id.tv_empolyee_all)
    void showAllEmployee() {
        getOrders("",0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activivty_orders_dates);
        ButterKnife.bind(this);
        initUI();
        if (HelperMethods.getUserType(this).equals("2"))
            getEmployees();
        getOrders("",0);
    }

    private void initUI() {

        listEmployees = new ArrayList<>();
        listOrders = new ArrayList<>();

        tabUpcoming.setText(getString(R.string.upcoming_appointments));
        tabArchive.setText(getString(R.string.appointments_archive));

        tabUpcoming.setOnClickListener(this);
        tabArchive.setOnClickListener(this);

        if (HelperMethods.getUserType(this).equals("2")) {
            // TODO SALON
            relEmployees.setVisibility(View.VISIBLE);
        }else {
            // TODO EMPLOYEE
            relEmployees.setVisibility(View.GONE);
        }

        LinearLayoutManager employeeLayoutManager = new LinearLayoutManager(this);
        employeeLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerEmpolyees.setHasFixedSize(true);
        recyclerEmpolyees.setLayoutManager(employeeLayoutManager);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerOrders.setHasFixedSize(true);
        recyclerOrders.setLayoutManager(layoutManager);
    }

    private void getEmployees() {

        HelperMethods.getMawedAPI()
                .getSalonEmployee(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this))
                .enqueue(new Callback<Employee>() {
                    @Override
                    public void onResponse(Call<Employee> call, Response<Employee> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getEmployees() != null) {
                                    listEmployees.clear();
                                    listEmployees.addAll(response.body().getEmployees());
                                    SalonEmployeeAdapter employeeAdapter = new SalonEmployeeAdapter(DatesOrdersActivity.this, listEmployees, DatesOrdersActivity.this);
                                    recyclerEmpolyees.setAdapter(employeeAdapter);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Employee> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    private void getAppoinmentsSalon(String upcoming, String show, Integer empId) {

        loading.setVisibility(View.VISIBLE);
        HelperMethods.getMawedAPI()
                .getManagementSalon(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this),
                        upcoming, show, empId)
                .enqueue(new Callback<ManagementSalon>() {
                    @Override
                    public void onResponse(Call<ManagementSalon> call, Response<ManagementSalon> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getData() != null) {
//                                    listOrders.clear();
//                                    listOrders.addAll(response.body().getData());
//                                    SalonAppointmentsAdapter salonAppointmentsAdapter = new SalonAppointmentsAdapter(DatesManagementActivity.this, listOrders);
//                                    recyclerOrders.setAdapter(salonAppointmentsAdapter);

                                }
                            }
                        }
                    }


                    @Override
                    public void onFailure(Call<ManagementSalon> call, Throwable t) {
                        Log.e(TAG, "onFailure:  " + t.getMessage());
                        loading.setVisibility(View.GONE);
                    }
                });

    }

    private void getOrders(String type, Integer empId) {

        loading.setVisibility(View.VISIBLE);
        HelperMethods.getMawedAPI()
                .getOrders(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this),
                        empId, type)
                .enqueue(new Callback<Orders>() {
                    @Override
                    public void onResponse(Call<Orders> call, Response<Orders> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()){
                            if (response.body().isStatus()){
                                if (response.body().getData() != null){
                                    listOrders.clear();
                                    listOrders.addAll(response.body().getData().getOrders());
                                    SalonOrdersAdapter salonOrdersAdapter = new SalonOrdersAdapter(DatesOrdersActivity.this, listOrders);
                                    recyclerOrders.setAdapter(salonOrdersAdapter);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Orders> call, Throwable t) {
                        loading.setVisibility(View.GONE);
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_upcoming) {
            tabArchive.setBackgroundResource(R.drawable.bg_tv_trips_prev);
            tabUpcoming.setBackgroundResource(R.drawable.bg_tv_trips_now);

            tabUpcoming.setTextColor(Color.parseColor("#FFFFFF"));
            tabArchive.setTextColor(Color.parseColor("#1581FA"));

            tabSelect.animate().x(0).setDuration(60);
            getOrders("appointments",0);
        } else {
            if (view.getId() == R.id.tv_archive) {
                tabArchive.setBackgroundResource(R.drawable.bg_tv_trips_now);
                tabUpcoming.setBackgroundResource(R.drawable.bg_tv_trips_prev);

                tabUpcoming.setTextColor(Color.parseColor("#1581FA"));
                tabArchive.setTextColor(Color.parseColor("#FFFFFF"));

                int size = tabUpcoming.getWidth();
                tabSelect.animate().x(size).setDuration(60);
                getOrders("dates",0);
            }
        }
    }

    @Override
    public void onEmployeeSelected(EmployeeData employee, int pos) {
        employeeId = employee.getId();
        getOrders("", employeeId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrders("",0);
    }
}
