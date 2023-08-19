package com.raiyansoft.mawed.ui.activities.salon;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.adapter.CalendarAdapter;
import com.raiyansoft.mawed.adapter.salon.SalonEmployeeAdapter;
import com.raiyansoft.mawed.listener.CalendarSelectedListener;
import com.raiyansoft.mawed.listener.EmployeeSelectedListener;
import com.raiyansoft.mawed.model.calendar.Calendar;
import com.raiyansoft.mawed.model.calendar.Events;
import com.raiyansoft.mawed.model.salon.Employee;
import com.raiyansoft.mawed.model.salon.sales.Sales;
import com.raiyansoft.mawed.model.userSalon.EmployeeData;
import com.raiyansoft.mawed.utils.HelperMethods;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesActivity extends AppCompatActivity implements CalendarSelectedListener, EmployeeSelectedListener {
    private static final String TAG = SalesActivity.class.getSimpleName();

    @BindView(R.id.pb_loading)
    ProgressBar loading;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.recycler_calendar)
    RecyclerView recyclerCalendar;

    @BindView(R.id.recycler_employees)
    RecyclerView recyclerEmpolyees;
    @BindView(R.id.tv_total)
    TextView tvTotal;

    public LocalDate selectedDate;
    private String dateSelected;
    private Integer empId;

    private List<EmployeeData> listEmployees;

    @OnClick(R.id.iv_back)
    void onBackClick() {
        onBackPressed();
    }

    @OnClick(R.id.btn_next_month)
    void onNextMonthClick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            selectedDate = selectedDate.plusMonths(1);
            loadCalendar();
        }
    }

    @OnClick(R.id.btn_previous_month)
    void onPreviousMonthClick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            selectedDate = selectedDate.minusMonths(1);
            loadCalendar();
        }
    }

    @OnClick(R.id.tv_empolyee_all)
    void onShowAllClick(){
        getSalesTotal("",0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        ButterKnife.bind(this);
        initUI();
        loadCalendar();
        getEmployees();
        getSalesTotal("",0);
    }

    private void initUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            selectedDate = LocalDate.now();

        listEmployees = new ArrayList<>();

        StaggeredGridLayoutManager employeeLayoutManager = new StaggeredGridLayoutManager(3, RecyclerView.VERTICAL);
        employeeLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerEmpolyees.setHasFixedSize(true);
        recyclerEmpolyees.setLayoutManager(employeeLayoutManager);
    }

    private void loadCalendar() {

        tvDate.setText(HelperMethods.monthYearFromDate(this, selectedDate, "MMMM yyyy"));

        List<Calendar> listDaysInMonth = daysInMonth(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(this, listDaysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 7);
        recyclerCalendar.setLayoutManager(layoutManager);
        recyclerCalendar.setHasFixedSize(true);
        recyclerCalendar.setAdapter(calendarAdapter);
    }

    private List<Calendar> daysInMonth(LocalDate date) {

        List<Calendar> listDaysInMonth = new ArrayList<>();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            YearMonth yearMonth = YearMonth.from(date);

            int daysInMonth = yearMonth.lengthOfMonth();
            LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
            int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();


            for (int i = 1; i <= 42; i++) {
                if (i <= dayOfWeek || i > daysInMonth + dayOfWeek)
                    listDaysInMonth.add(new Calendar());
                else {
                    String currentDate = (i - dayOfWeek) + "-" + firstOfMonth.getMonth() + "-" + firstOfMonth.getYear();

                    List<Events> listEvents = new ArrayList<>();
                    Events event = new Events();
                    event.setEvent("Event1");
                    event.setDate("14-12-2022");

                    Events event1 = new Events();
                    event1.setEvent("Event2");
                    event1.setDate("14-12-2022");

                    Events event2 = new Events();
                    event2.setEvent("Event3");
                    event2.setDate("14-12-2022");


                    Events event3 = new Events();
                    event3.setEvent("Event4");
                    event3.setDate("29-11-2022");

                    Events event4 = new Events();
                    event4.setEvent("Event5");
                    event4.setDate("12-12-2022");


                    listEvents.add(event);
                    listEvents.add(event1);
                    listEvents.add(event2);
                    listEvents.add(event3);
                    listEvents.add(event4);

                    Calendar calendar = new Calendar();
                    calendar.setCurrentDay(String.valueOf(i - dayOfWeek));

                    // TODO 2023-03-31 / yyyy-MM-dd
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String dateFormate = dateFormat.format(new Date(currentDate));
                    calendar.setCurrentDate(dateFormate);

                    calendar.setEvents(listEvents);
                    listDaysInMonth.add(calendar);


                    Log.d(TAG, "daysInMonth: " + (i - dayOfWeek));
                    Log.d(TAG, "currentDate: " + currentDate);
                    String formate = dateFormat.format(new Date(currentDate));
                    Log.d(TAG, "daysInMonth: " + formate);
                }
            }

        }
        return listDaysInMonth;
    }

    private void getEmployees() {

        loading.setVisibility(View.VISIBLE);
        HelperMethods.getMawedAPI()
                .getSalonEmployee(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this))
                .enqueue(new Callback<Employee>() {
                    @Override
                    public void onResponse(Call<Employee> call, Response<Employee> response) {
                        loading.setVisibility(View.VISIBLE);
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getEmployees() != null) {
                                    listEmployees.clear();
                                    listEmployees.addAll(response.body().getEmployees());
                                    SalonEmployeeAdapter employeeAdapter = new SalonEmployeeAdapter(SalesActivity.this, listEmployees, SalesActivity.this);
                                    recyclerEmpolyees.setAdapter(employeeAdapter);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Employee> call, Throwable t) {
                        loading.setVisibility(View.VISIBLE);
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    private void getSalesTotal(String date, int empId) {
        loading.setVisibility(View.VISIBLE);
        HelperMethods.getMawedAPI()
                .getSales(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this), empId, date)
                .enqueue(new Callback<Sales>() {
                    @Override
                    public void onResponse(Call<Sales> call, Response<Sales> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getSales() != null) {
                                    String total = new StringBuilder()
                                            .append(getString(R.string.total))
                                            .append(" : ")
                                            .append(response.body().getSales().getTotalCost())
                                            .append(" ")
                                            .append(HelperMethods.getCurrency(SalesActivity.this))
                                            .toString();
                                    tvTotal.setText(total);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Sales> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                        loading.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onDateSelected(Calendar calendar, int pos) {
        dateSelected = calendar.getCurrentDate();
        Log.d(TAG, "onDateSelected: " + dateSelected);
        getSalesTotal(dateSelected, 0);
    }

    @Override
    public void onEmployeeSelected(EmployeeData employee, int pos) {
        empId = employee.getId();
        getSalesTotal("", empId);
    }
}
