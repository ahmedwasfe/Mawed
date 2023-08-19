package com.raiyansoft.mawed.ui.fragments.user.booking;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.adapter.BookTimeAdapter;
import com.raiyansoft.mawed.adapter.CalendarAdapter;
import com.raiyansoft.mawed.listener.CalendarSelectedListener;
import com.raiyansoft.mawed.listener.TimeSelectedListener;
import com.raiyansoft.mawed.model.calendar.Calendar;
import com.raiyansoft.mawed.model.calendar.Events;
import com.raiyansoft.mawed.model.employee.Empolyee;
import com.raiyansoft.mawed.model.employee.review.Review;
import com.raiyansoft.mawed.model.userOrders.sendOrder.SendService;
import com.raiyansoft.mawed.ui.activities.booking.BookingActivity;
import com.raiyansoft.mawed.utils.HelperMethods;
import com.google.gson.Gson;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarberDateTimeFragment extends Fragment implements CalendarSelectedListener, TimeSelectedListener {
    private static final String TAG = BarberDateTimeFragment.class.getSimpleName();

    @BindView(R.id.swipe)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.pb_loading)
    ProgressBar loading;
    @BindView(R.id.civ_employee_image)
    CircleImageView civEmployeeImage;
    @BindView(R.id.tv_employee_name)
    TextView tvEmployeeName;
    @BindView(R.id.tv_employee_description)
    TextView tvEmployeeDescription;
    @BindView(R.id.ratingBar)
    AppCompatRatingBar ratingBar;
    @BindView(R.id.sw_home)
    SwitchCompat swHome;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.recycler_calendar)
    RecyclerView recyclerCalendar;
    @BindView(R.id.recycler_time)
    RecyclerView recyclerTime;
    @BindView(R.id.tv_text_no_times)
    TextView tvNoTimes;

    public LocalDate selectedDate;

    private List<String> listTimes;
    private BookTimeAdapter bookTimeAdapter;

    private BookingActivity bookingActivity;

    private int home = 1;
    private String dateSelected;
    private String timeselected;

    int isHome;

    public BarberDateTimeFragment() {

    }

    private static BarberDateTimeFragment instance;

    public static BarberDateTimeFragment getInstance() {
        return instance == null ? new BarberDateTimeFragment() : instance;
    }

    @OnClick(R.id.iv_add_rate)
    void onAddRateClick() {
        showRateDialog();
    }

    @OnClick(R.id.btn_book)
    void onBookClick() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "onBookClick: ");
                bookingActivity.home = home;
                if (dateSelected == null){
                    bookingActivity.dateSelected = HelperMethods.getDateToday(getActivity(), "yyyy-MM-dd");
                }else
                    bookingActivity.dateSelected = dateSelected;

                bookingActivity.timeSelected = timeselected;
                bookingActivity.pagerBookAdapter.addFragment(BarberPayingFragment.getInstance());
                bookingActivity.viewPagerBook.setCurrentItem(bookingActivity.viewPagerBook.getCurrentItem() + 1);
            }
        }, 100);

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

    @OnClick(R.id.iv_back)
    void onBackClick() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_barber_date_time, container, false);
        ButterKnife.bind(this, layoutView);
        initUI();
        bookingActivity.loadEmployeeData(civEmployeeImage, tvEmployeeName, tvEmployeeDescription, ratingBar,
                null, null, null);
        loadCalendar();
        loadEmpolyeeTimes(dateSelected);
        return layoutView;
    }

    private void initUI() {

        bookingActivity = (BookingActivity) getActivity();
        loadEmployeeData();
        listTimes = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            selectedDate = LocalDate.now();

        Log.d(TAG, "initUI: " + bookingActivity.note);
        Log.d(TAG, "initUI: " + isHome);
        Log.d(TAG, "initUI: " + bookingActivity.addressId);
        Log.d(TAG, "initUI: " + bookingActivity.employeeId);
        Log.d(TAG, "initUI: " + bookingActivity.serviceTotal);
        Log.d(TAG, "initUI: " + new Gson().toJson(bookingActivity.listServiesSelected));

        swHome.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                home = 1;
            else
                home = 2;
        });


        bookTimeAdapter = new BookTimeAdapter(getActivity(), listTimes, this);

        refreshLayout.setOnRefreshListener(() -> {
            loadEmployeeData();
            bookingActivity.loadEmployeeData(civEmployeeImage, tvEmployeeName,
                    tvEmployeeDescription, ratingBar, null,
                    null, null);
            refreshLayout.setRefreshing(false);
        });

    }

    public void loadEmployeeData() {
        Log.d(TAG, "loadEmployeeData: " + bookingActivity.employeeId);
        HelperMethods.getMawedAPI()
                .getEmpolyeeData(HelperMethods.getAppLanguage(getActivity()),
                        HelperMethods.getUserToken(getActivity()),
                        bookingActivity.employeeId)
                .enqueue(new Callback<Empolyee>() {
                    @Override
                    public void onResponse(Call<Empolyee> call, Response<Empolyee> response) {
//                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getEmployeeData() != null) {
                                    isHome = response.body().getEmployeeData().getHome();
                                    Log.d(TAG, "onResponse HOME: " + isHome);
                                    if (isHome == 1)
                                        swHome.setVisibility(View.VISIBLE);
                                    else
                                        swHome.setVisibility(View.GONE);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Empolyee> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
//                        loading.setVisibility(View.GONE);
                    }
                });
    }

    private void loadCalendar() {

        tvDate.setText(HelperMethods.monthYearFromDate(getActivity(), selectedDate, "MMMM yyyy"));

        List<Calendar> listDaysInMonth = daysInMonth(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(getActivity(), listDaysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 7);
        recyclerCalendar.setLayoutManager(layoutManager);
        recyclerCalendar.setHasFixedSize(true);
        recyclerCalendar.setAdapter(calendarAdapter);

        StaggeredGridLayoutManager timeLayoutManager = new StaggeredGridLayoutManager(3, RecyclerView.VERTICAL);
//        recyclerTime.setHasFixedSize(true);
        recyclerTime.setLayoutManager(timeLayoutManager);
    }

    private void loadEmpolyeeTimes(String dateSelected) {

        loading.setVisibility(View.VISIBLE);
        List<Integer> listIds = new ArrayList<>();
        if (bookingActivity.listServiesSelected != null) {
            for (SendService service : bookingActivity.listServiesSelected)
                listIds.add(service.getItemId());
        }
        Log.d(TAG, "loadEmpolyeeTimes: " + new Gson().toJson(listIds));
        Log.d(TAG, "loadEmpolyeeTimes: " + dateSelected);

        HelperMethods.getMawedAPI()
                .getEmpolyeeTimes(HelperMethods.getAppLanguage(getActivity()),
                        HelperMethods.getUserToken(getActivity()),
                        bookingActivity.employeeId,
                        listIds, dateSelected)
                .enqueue(new Callback<Empolyee>() {
                    @Override
                    public void onResponse(Call<Empolyee> call, Response<Empolyee> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getEmployeeData() != null) {
//                                    Log.d(TAG, "onResponse: " + response.body().getEmployeeData().getTimes().size());
                                    listTimes.clear();
                                    listTimes.addAll(response.body().getEmployeeData().getTimes());
                                    recyclerTime.setAdapter(bookTimeAdapter);

                                    if (listTimes.size() == 0)
                                        tvNoTimes.setVisibility(View.VISIBLE);
                                    else
                                        tvNoTimes.setVisibility(View.GONE);

                                } else {
                                    Log.d(TAG, "onResponse: " + response.body().getMessage());
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Empolyee> call, Throwable t) {
                        loading.setVisibility(View.GONE);
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    private List<Calendar> daysInMonth(LocalDate date) {

        List<Calendar> listDaysInMonth = new ArrayList<>();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            YearMonth yearMonth = YearMonth.from(date);

            int daysInMonth = yearMonth.lengthOfMonth();
            LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
            int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

            String today = HelperMethods.monthYearFromDate(getActivity(), selectedDate, "yyyy-MM-dd");
            dateSelected = today;
            Log.d(TAG, "daysInMonth TODAY: " + today);
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

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", new Locale("en"));
                    String dateFormate = dateFormat.format(new Date(currentDate));
                    calendar.setCurrentDate(dateFormate);

                    calendar.setEvents(listEvents);
                    listDaysInMonth.add(calendar);


//                    Log.d(TAG, "daysInMonth: " + (i - dayOfWeek));
//                    Log.d(TAG, "currentDate: " + currentDate);
                    String formate = dateFormat.format(new Date(currentDate));
//                    Log.d(TAG, "daysInMonth: " + dateFormate);
//                    Log.d(TAG, "daysInMonth: " + formate);
                }
            }

        }
        return listDaysInMonth;
    }

    private void showRateDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_rate, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        HelperMethods.showCustomDialog(dialog);

        CardView card = dialogView.findViewById(R.id.card_dialog);
        card.setBackgroundColor(Color.TRANSPARENT);

        RatingBar rating = dialogView.findViewById(R.id.rating);


        dialogView.findViewById(R.id.btn_cancel)
                .setOnClickListener(view -> dialog.dismiss());

        dialogView.findViewById(R.id.btn_rate)
                .setOnClickListener(v -> {
                    Log.d(TAG, "showRateDialog: " + rating.getRating());
                    reviewSalon(dialog, 4, rating.getRating());
                });

        dialog.show();
    }

    private void reviewSalon(AlertDialog dialog, Integer employeeId, double rate) {
        HelperMethods.getMawedAPI()
                .review(HelperMethods.getAppLanguage(getActivity()),
                        HelperMethods.getUserToken(getActivity()),
                        employeeId, rate)
                .enqueue(new Callback<Review>() {
                    @Override
                    public void onResponse(Call<Review> call, Response<Review> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body() != null) {
                                    Toast.makeText(bookingActivity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            } else {
                                Log.e(TAG, "onResponse: " + new Gson().toJson(response.body().getData()));
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<Review> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }


    @Override
    public void onDateSelected(Calendar calendar, int pos) {
        dateSelected = calendar.getCurrentDate();
        loadEmpolyeeTimes(dateSelected);
        Log.d(TAG, "onDateSelected: " + calendar.getCurrentDate());
//        HelperMethods.monthYearFromDate(getActivity(), calendar.getCurrentDate(), "dd")
    }

    @Override
    public void onTimeSelected(String time, int pos) {
        timeselected = time;
        Log.d(TAG, "onTimeSelected: " + time);
    }
}