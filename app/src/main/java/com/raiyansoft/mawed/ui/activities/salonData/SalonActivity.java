package com.raiyansoft.mawed.ui.activities.salonData;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.raiyansoft.mawed.MapActivity;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.adapter.EmployeeAdapter;
import com.raiyansoft.mawed.adapter.SalonCategoriesAdapter;
import com.raiyansoft.mawed.adapter.SalonImagesAdapter;
import com.raiyansoft.mawed.listener.SalonCategoriesListener;
import com.raiyansoft.mawed.model.userSalon.AllCategory;
import com.raiyansoft.mawed.model.userSalon.EmployeeData;
import com.raiyansoft.mawed.model.userSalon.SalonDetails;
import com.raiyansoft.mawed.model.userSalon.SalonDetailsData;
import com.raiyansoft.mawed.model.userSalon.SalonImages;
import com.raiyansoft.mawed.utils.Const;
import com.raiyansoft.mawed.utils.HelperMethods;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalonActivity extends AppCompatActivity implements SalonCategoriesListener {
    private static final String TAG = SalonActivity.class.getSimpleName();

    @BindView(R.id.pb_loading)
    ProgressBar loading;
    @BindView(R.id.view_pager_salon_images)
    ViewPager2 pagerSalonImages;
    @BindView(R.id.dots_indicator)
    DotsIndicator dotsIndicator;

    @BindView(R.id.tv_salon_name)
    TextView tvSalonName;
    @BindView(R.id.tv_employee_description)
    TextView tvSalonDescription;
    @BindView(R.id.tv_rate)
    TextView tvRate;

    @BindView(R.id.tv_all_categories)
    TextView tvSalonAllCategories;
    @BindView(R.id.recycler_salon_categories)
    RecyclerView recyclerSalonCategories;
    @BindView(R.id.recycler_barbers)
    RecyclerView recyclerEmployees;

    @BindView(R.id.tv_category_type_selected)
    TextView tvCategorySelected;

    private Integer salonId;
    private boolean isFavorite;
    private double latitude, longitude;

    private List<SalonImages> listImages;
    private List<AllCategory> listAllCategorises;
    private List<EmployeeData> listEmployees;

    private SalonImagesAdapter salonImagesAdapter;
    private Handler sliderHandler = new Handler();
    private final long DELAY_MS = 3000;

    @OnClick(R.id.iv_back)
    void onBackClick() {
        onBackPressed();
    }

    @OnClick(R.id.iv_salon_location)
    void onLocationClick() {
        Log.d(TAG, "onLocationClick: " + latitude + "," + longitude);
        startActivity(new Intent(this, MapActivity.class)
                .putExtra(Const.KEY_SALON_LATITUDE, latitude)
                .putExtra(Const.KEY_SALON_LONGITUDE, longitude)
                .putExtra(Const.KEY_SALON_NAME, tvSalonName.getText().toString()));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon);
        ButterKnife.bind(this);
        initUI();
        initViewPager();
        loadSalonDetails(0);
    }

    private void initUI() {

        if (getIntent() != null) {
            salonId = getIntent().getIntExtra(Const.KEY_SALON_ID, 0);
            isFavorite = getIntent().getBooleanExtra(Const.KEY_SALON_FAV, false);
        }
        Log.d(TAG, "initUI SALON ID: " + salonId);
        Log.d(TAG, "initUI SALON ID: " + isFavorite);

        listImages = new ArrayList<>();
        listAllCategorises = new ArrayList<>();
        listEmployees = new ArrayList<>();

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this);
        horizontalLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerSalonCategories.setHasFixedSize(true);
        recyclerSalonCategories.setLayoutManager(horizontalLayoutManager);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerEmployees.setHasFixedSize(true);
        recyclerEmployees.setLayoutManager(gridLayoutManager);

    }

    private void initViewPager() {

        pagerSalonImages.setClipToPadding(false);
        pagerSalonImages.setClipChildren(false);
        pagerSalonImages.setOffscreenPageLimit(3);
        pagerSalonImages.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_ALWAYS);
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(10));
        transformer.addTransformer((page, position) -> {
            float f = 1 - Math.abs(position);
            page.setScaleY(0.85f + f * 0.15f);
        });
        pagerSalonImages.setPageTransformer(transformer);
        pagerSalonImages.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, DELAY_MS);
            }
        });
    }

    private void loadSalonDetails(Integer categoryId) {

        loading.setVisibility(View.VISIBLE);
        HelperMethods.getMawedAPI()
                .getSalonDetails(HelperMethods.getAppLanguage(this),
                        salonId, categoryId)
                .enqueue(new Callback<SalonDetails>() {
                    @Override
                    public void onResponse(Call<SalonDetails> call, Response<SalonDetails> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getSalonDetails() != null) {
                                    getSalonDetails(response.body().getSalonDetails());
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SalonDetails> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                        loading.setVisibility(View.GONE);
                    }
                });
    }

    private void getSalonDetails(SalonDetailsData salon) {

        if (salon != null) {

            Log.d(TAG, "getSalonDetails INTENT: " + salonId);
            Log.d(TAG, "getSalonDetails OBJECT: " + salon.getId());
            if (salon.getLat() != null)
                latitude = salon.getLat();
            if (salon.getLng() != null)
                longitude = salon.getLng();

            HelperMethods.salonName = salon.getCommercailName();
            tvSalonName.setText(salon.getCommercailName());
            tvSalonDescription.setText(salon.getDescription());
            tvRate.setText(HelperMethods.formatDecimalValue(salon.getReview()));

            // TODO IMAGES
            if (salon.getImages() != null && !salon.getImages().isEmpty()) {
                listImages.clear();
                listImages.addAll(salon.getImages());
                salonImagesAdapter = new SalonImagesAdapter(this, listImages);
                pagerSalonImages.setAdapter(salonImagesAdapter);
                dotsIndicator.attachTo(pagerSalonImages);
            }


            // TODO ALL CATEGORISES
            if (salon.getAllCategories() != null && !salon.getAllCategories().isEmpty()) {
                listAllCategorises.clear();
                listAllCategorises.addAll(salon.getAllCategories());
                SalonCategoriesAdapter salonCategoriesAdapter = new SalonCategoriesAdapter(this, listAllCategorises, this);
                recyclerSalonCategories.setAdapter(salonCategoriesAdapter);
            }


            if (salon.getEmployees() != null && !salon.getEmployees().isEmpty()){
                listEmployees.clear();
                listEmployees.addAll(salon.getEmployees());

                EmployeeAdapter employeeAdapter = new EmployeeAdapter(this, listEmployees, salon.getId(), isFavorite);
                recyclerEmployees.setAdapter(employeeAdapter);
            }

            tvSalonAllCategories.setOnClickListener(v -> {
                tvCategorySelected.setText(getString(R.string.all));
                if (salon.getEmployees() != null && !salon.getEmployees().isEmpty()){
                    listEmployees.clear();
                    listEmployees.addAll(salon.getEmployees());

                    EmployeeAdapter employeeAdapter = new EmployeeAdapter(this, listEmployees, salon.getId(), isFavorite);
                    recyclerEmployees.setAdapter(employeeAdapter);
                }
            });
        }
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            pagerSalonImages.setCurrentItem(pagerSalonImages.getCurrentItem() + 1);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, DELAY_MS);
    }

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    public void onCategoryClick(AllCategory category, int pos) {
        Log.d(TAG, "onCategoryClick: " + category.getEmployees().size());
        tvCategorySelected.setText(category.getCategoryEmpName());
        if (category.getEmployees() != null && !category.getEmployees().isEmpty()){
            listEmployees.clear();
            listEmployees.addAll(category.getEmployees());

            EmployeeAdapter employeeAdapter = new EmployeeAdapter(this, listEmployees, salonId, isFavorite);
            recyclerEmployees.setAdapter(employeeAdapter);
        }
    }
}
