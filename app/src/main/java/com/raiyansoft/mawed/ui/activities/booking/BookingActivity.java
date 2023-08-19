package com.raiyansoft.mawed.ui.activities.booking;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.adapter.PagerBookAdapter;
import com.raiyansoft.mawed.adapter.ServiceAdapter;
import com.raiyansoft.mawed.model.employee.EmployeeData;
import com.raiyansoft.mawed.model.employee.EmployeeDataCat;
import com.raiyansoft.mawed.model.employee.Empolyee;
import com.raiyansoft.mawed.model.userOrders.sendOrder.SendService;
import com.raiyansoft.mawed.ui.fragments.user.booking.BarberDateTimeFragment;
import com.raiyansoft.mawed.ui.fragments.user.booking.BarberPayingFragment;
import com.raiyansoft.mawed.ui.fragments.user.booking.BarberServiceFragment;
import com.raiyansoft.mawed.utils.Const;
import com.raiyansoft.mawed.utils.HelperMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity {
    private static final String TAG = BookingActivity.class.getSimpleName();

    @BindView(R.id.view_pager_book)
    public ViewPager2 viewPagerBook;

    public Integer employeeId;
    public Integer salonId;
    public boolean isFavorite;
    public EmployeeData employeeData;
    public List<SendService> listServiesSelected;
    public String note;
    public int addressId;
    public int serviceTotal;
    public int home;
    public int isHome = 45;
    public String dateSelected;
    public String timeSelected;

    public PagerBookAdapter pagerBookAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activivty_booking);
        ButterKnife.bind(this);
        if (getIntent() != null){
            employeeId = getIntent().getIntExtra(Const.KEY_EMPLOYEE_ID, 0);
            salonId = getIntent().getIntExtra(Const.KEY_SALON_ID, 0);
            isFavorite = getIntent().getBooleanExtra(Const.KEY_SALON_FAV, false);
        }

        Log.d(TAG, "onCreate: "+ employeeId);
        Log.d(TAG, "onCreate: "+ salonId);
//        loadEmployeeData();
        initViewPager();
    }

    private void initViewPager() {

        pagerBookAdapter = new PagerBookAdapter(this);
        pagerBookAdapter.addFragment(BarberServiceFragment.getInstance());
        pagerBookAdapter.addFragment(BarberDateTimeFragment.getInstance());
        pagerBookAdapter.addFragment(BarberPayingFragment.getInstance());
        viewPagerBook.setAdapter(pagerBookAdapter);

        viewPagerBook.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });
    }

    public void loadEmployeeData(CircleImageView civEmployeeImage,
                                 TextView tvEmployeeName,
                                 TextView tvEmployeeDescription,
                                 AppCompatRatingBar ratingBar,
                                 List<EmployeeDataCat> listEmpServices,
                                 ServiceAdapter serviceAdapter,
                                 RecyclerView recyclerService) {
//        loading.setVisibility(View.VISIBLE);
        HelperMethods.getMawedAPI()
                .getEmpolyeeData(HelperMethods.getAppLanguage(BookingActivity.this),
                        HelperMethods.getUserToken(BookingActivity.this),
                        employeeId)
                .enqueue(new Callback<Empolyee>() {
                    @Override
                    public void onResponse(Call<Empolyee> call, Response<Empolyee> response) {
//                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getEmployeeData() != null) {
                                   getEmployeeData(response.body().getEmployeeData(),
                                           civEmployeeImage, tvEmployeeName, tvEmployeeDescription,
                                           ratingBar, listEmpServices, serviceAdapter,
                                           recyclerService);
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

    private void getEmployeeData(EmployeeData employee,
                                 CircleImageView civEmployeeImage,
                                 TextView tvEmployeeName,
                                 TextView tvEmployeeDescription,
                                 AppCompatRatingBar ratingBar,
                                 List<EmployeeDataCat> listEmpServices,
                                 ServiceAdapter serviceAdapter,
                                 RecyclerView recyclerService) {
        Log.d(TAG, "getEmployeeData: " + employee.getName());
        if (employee.getImages() != null)
            Glide.with(this).load(employee.getImages()).into(civEmployeeImage);
        Log.d(TAG, "getEmployeeData: " + employee.getName());
        tvEmployeeName.setText(employee.getName());
        tvEmployeeDescription.setText(employee.getDescription());
        ratingBar.setRating((float) employee.getReview());
        isHome = employee.getHome();
        Log.d(TAG, "getEmployeeData getHome: " + employee.getHome());

        if (employee.getCat() != null && !employee.getCat().isEmpty()) {
            if (listEmpServices != null) {
                listEmpServices.clear();
                listEmpServices.addAll(employee.getCat());
                recyclerService.setAdapter(serviceAdapter);
            }
        }
    }
}
