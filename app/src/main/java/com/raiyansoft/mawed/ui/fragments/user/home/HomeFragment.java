package com.raiyansoft.mawed.ui.fragments.user.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.raiyansoft.mawed.HomeActivity;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.adapter.AddressHomeAdapter;
import com.raiyansoft.mawed.adapter.RegionsAdapter;
import com.raiyansoft.mawed.adapter.home.HomeSalonAdapter;
import com.raiyansoft.mawed.adapter.home.HomeServiceAdapter;
import com.raiyansoft.mawed.adapter.home.SliderAdsAdapter;
import com.raiyansoft.mawed.adapter.UpcomingAppointmentsAdapter;
import com.raiyansoft.mawed.listener.SelectHomeAddressListener;
import com.raiyansoft.mawed.listener.SelectHomeSalonListener;
import com.raiyansoft.mawed.listener.SelectHomeServiceListener;
import com.raiyansoft.mawed.model.filter.Filter;
import com.raiyansoft.mawed.model.filter.SalonData;
import com.raiyansoft.mawed.model.home.Ad;
import com.raiyansoft.mawed.model.home.AppUser;
import com.raiyansoft.mawed.model.home.Home;
import com.raiyansoft.mawed.model.home.HomeData;
import com.raiyansoft.mawed.model.home.HomeGovernorate;
import com.raiyansoft.mawed.model.home.HomeOrders;
import com.raiyansoft.mawed.model.home.HomeService;
import com.raiyansoft.mawed.model.region.RegionData;
import com.raiyansoft.mawed.ui.activities.search.SearchResultActivity;
import com.raiyansoft.mawed.utils.Const;
import com.raiyansoft.mawed.utils.HelperMethods;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements SelectHomeAddressListener, SelectHomeServiceListener, SelectHomeSalonListener {
    private static final String TAG = HomeFragment.class.getSimpleName();

    @BindView(R.id.swipe)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.pb_loading)
    ProgressBar loading;
    @BindView(R.id.view_pager_slider)
    ViewPager2 pagerSlider;
    @BindView(R.id.dots_indicator)
    DotsIndicator dotsIndicator;
    //    @BindView(R.id.ll_service)
//    LinearLayout llService;
//    @BindView(R.id.spinner_services)
//    MaterialSpinner spinnerService;

    // TODO Services
    @BindView(R.id.tv_services)
    TextView tvServiceName;
    @BindView(R.id.iv_arrow_services)
    ImageView ivShowServices;
    @BindView(R.id.expandable_layout_services)
    ExpandableLayout expandableServices;
    @BindView(R.id.recycler_services)
    RecyclerView recyclerServices;

    // TODO Address
    @BindView(R.id.tv_address)
    TextView tvAddressName;
    @BindView(R.id.iv_arrow_address)
    ImageView ivShowAddress;
    @BindView(R.id.expandable_layout_address)
    ExpandableLayout expandableAddress;
    @BindView(R.id.recycler_governorates)
    RecyclerView recyclerGovernorates;

    // TODO Salon
    @BindView(R.id.tv_salon)
    TextView tvSalonName;
    @BindView(R.id.iv_arrow_salon)
    ImageView ivShowSalon;
    @BindView(R.id.expandable_layout_salon)
    ExpandableLayout expandableSalon;
    @BindView(R.id.recycler_salon)
    RecyclerView recyclerSalon;

    @BindView(R.id.tv_date)
    TextView tvDate;

    @BindView(R.id.recycler_upcoming_appointments)
    RecyclerView recyclerAppointments;

    private HomeActivity homeActivity;

    private SliderAdsAdapter sliderAdapter;
    private HomeServiceAdapter serviceAdapter;
    private HomeSalonAdapter appUserAdapter;
    private AddressHomeAdapter addressHomeAdapter;

    private Handler sliderHandler = new Handler();
    private final long DELAY_MS = 3000;

    private List<Ad> listAds;
    private List<HomeService> listServices;
    private List<HomeGovernorate> listGovernorates;
    private List<AppUser> listAppUsers;
    private List<HomeOrders> listOrders;
    private List<SalonData> listFilter;

    private int serviceId = 0;
    private int governorateId = 0;
    private int salonId = 0;
    private String date = "";

    private List<RegionData> listRegions;
    private int regionId;
    RegionsAdapter regionsAdapter;
    private RegionData region;

    private static HomeFragment instance;

    public static HomeFragment getInstance() {
        return instance == null ? new HomeFragment() : instance;
    }

    @OnClick(R.id.ll_services)
    void onServiceClick() {
        HelperMethods.services = Const.KEY_SERVICES;
        homeActivity.navController.navigate(R.id.nav_sections);
//        if (expandableServices.isExpanded())
//            ivShowServices.setBackgroundResource(R.drawable.ic_icon_arrow_down);
//        else
//            ivShowServices.setBackgroundResource(R.drawable.ic_icon_arrow_up);
//        expandableServices.toggle();
    }

    @OnClick(R.id.ll_address)
    void onAddressClick() {
        if (expandableAddress.isExpanded())
            ivShowAddress.setBackgroundResource(R.drawable.ic_icon_arrow_down);
        else
            ivShowAddress.setBackgroundResource(R.drawable.ic_icon_arrow_up);
        expandableAddress.toggle();
    }

    @OnClick(R.id.ll_salon)
    void onSalonClick() {
        if (expandableSalon.isExpanded())
            ivShowSalon.setBackgroundResource(R.drawable.ic_icon_arrow_down);
        else
            ivShowSalon.setBackgroundResource(R.drawable.ic_icon_arrow_up);
        expandableSalon.toggle();
    }

    @OnClick(R.id.ll_date)
    void onDateClick() {
        // TODO 2023-06-29
        HelperMethods.showDateDialog(getActivity(), "yyyy-MM-dd", tvDate);
    }

    @OnClick(R.id.btn_search)
    void onSearchClick() {
        Log.d(TAG, "onSearchClick: ");
        if (HelperMethods.getDateInMillis() == null)
            date = "";
        else {
            date = new SimpleDateFormat("yyyy-MM-dd").format(HelperMethods.getDateInMillis());
        }
        startActivity(new Intent(getActivity(), SearchResultActivity.class)
                .putExtra(Const.KEY_SERVICE_ID, HelperMethods.servicesId)
                .putExtra(Const.KEY_GOVERNORATE_ID, governorateId)
                .putExtra(Const.KEY_DATE, date)
                .putExtra(Const.KEY_SALON_ID, salonId));
    }

    @OnClick(R.id.tv_text_more)
    void onMoreClick() {
        homeActivity.navController.navigate(R.id.nav_my_date);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, layoutView);
        initViewPager();
        initUI();
        getHomeData();
        return layoutView;
    }

    private void initUI() {

        homeActivity = (HomeActivity) getActivity();

        listAds = new ArrayList<>();
        listGovernorates = new ArrayList<>();
        listServices = new ArrayList<>();
        listAppUsers = new ArrayList<>();
        listOrders = new ArrayList<>();
        listFilter = new ArrayList<>();
        listRegions = new ArrayList<>();

        ivShowAddress.setBackgroundResource(R.drawable.ic_icon_arrow_down);
        ivShowServices.setBackgroundResource(R.drawable.ic_icon_arrow_down);
        ivShowSalon.setBackgroundResource(R.drawable.ic_icon_arrow_down);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerAppointments.setHasFixedSize(true);
        recyclerAppointments.setLayoutManager(layoutManager);

        LinearLayoutManager governorateLayoutManager = new LinearLayoutManager(getActivity());
        governorateLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerGovernorates.setHasFixedSize(true);
        recyclerGovernorates.setLayoutManager(governorateLayoutManager);

        LinearLayoutManager serviceLayoutManager = new LinearLayoutManager(getActivity());
        serviceLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerServices.setHasFixedSize(true);
        recyclerServices.setLayoutManager(serviceLayoutManager);

        LinearLayoutManager salonLayoutManager = new LinearLayoutManager(getActivity());
        salonLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerSalon.setHasFixedSize(true);
        recyclerSalon.setLayoutManager(salonLayoutManager);

        addressHomeAdapter = new AddressHomeAdapter(getActivity(), listGovernorates,this);
        serviceAdapter = new HomeServiceAdapter(getActivity(), listServices, this);
        appUserAdapter = new HomeSalonAdapter(getActivity(), listFilter, this);

        refreshLayout.setOnRefreshListener(() -> {
            getHomeData();
            refreshLayout.setRefreshing(false);
        });

    }

    private void initViewPager() {

        pagerSlider.setClipToPadding(false);
        pagerSlider.setClipChildren(false);
        pagerSlider.setOffscreenPageLimit(3);
        pagerSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_ALWAYS);
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(10));
        transformer.addTransformer((page, position) -> {
            float f = 1 - Math.abs(position);
            page.setScaleY(0.85f + f * 0.15f);
        });
        pagerSlider.setPageTransformer(transformer);
        pagerSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, DELAY_MS);
            }
        });

    }

    private void getHomeData() {

        loading.setVisibility(View.VISIBLE);
        HelperMethods.getMawedAPI()
                .getHomeData(HelperMethods.getAppLanguage(getActivity()), HelperMethods.getUserToken(getActivity()))
                .enqueue(new Callback<Home>() {
                    @Override
                    public void onResponse(Call<Home> call, Response<Home> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getHomeData() != null) {
                                    HomeData home = response.body().getHomeData();

                                    // TODO ADS
                                    listAds.clear();
                                    listAds.addAll(home.ads);

                                    sliderAdapter = new SliderAdsAdapter(getActivity(), listAds);
                                    pagerSlider.setAdapter(sliderAdapter);
                                    dotsIndicator.attachTo(pagerSlider);

                                    // TODO Governorate
                                    listGovernorates.clear();
                                    listGovernorates.addAll(home.getGovernorates());

                                    recyclerGovernorates.setAdapter(addressHomeAdapter);

                                    // TODO Services
                                    listServices.clear();
                                    listServices.addAll(home.getServices());
                                    recyclerServices.setAdapter(serviceAdapter);

                                    // TODO ORDERS
                                    if (home.orders != null) {
                                        listOrders.clear();
                                        listOrders.addAll(home.orders);
                                        UpcomingAppointmentsAdapter appointmentsAdapter = new UpcomingAppointmentsAdapter(getActivity(), listOrders);
                                        recyclerAppointments.setAdapter(appointmentsAdapter);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Home> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                        loading.setVisibility(View.GONE);
                    }
                });
    }

    private void filter(int serviceId, int governorateId, String date, int salonId) {

        loading.setVisibility(View.VISIBLE);

        HelperMethods.getMawedAPI()
                .filter(HelperMethods.getAppLanguage(getActivity()),
                        HelperMethods.getUserToken(getActivity()),
                        serviceId, governorateId, date, salonId,
                        0.0, 0.0)
                .enqueue(new Callback<Filter>() {
                    @Override
                    public void onResponse(Call<Filter> call, Response<Filter> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getData() != null) {
                                    listFilter.clear();
                                    listFilter.addAll(response.body().getData().getSalons());

                                    // TODO APP USER - SALON
                                    recyclerSalon.setAdapter(appUserAdapter);

                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Filter> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                        loading.setVisibility(View.GONE);
                    }
                });
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            pagerSlider.setCurrentItem(pagerSlider.getCurrentItem() + 1);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, DELAY_MS);
        if (HelperMethods.servicesName.equals(""))
            tvServiceName.setText(getString(R.string.service));
        else
            tvServiceName.setText(HelperMethods.servicesName);
        Log.d(TAG, "onResume: " + HelperMethods.servicesId);
        Log.d(TAG, "onResume: " + HelperMethods.servicesName);
    }

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    public void onAddressSelected(HomeGovernorate governorate, RegionData region, int pos) {
        governorateId = governorate.getId();
        tvAddressName.setText(region.getTitle());
//        getRegions(governorateId);
        filter(serviceId, governorateId, date, salonId);
        Log.d(TAG, "onAddressSelected GOVE: " + governorate.getTitle());
        Log.d(TAG, "onAddressSelected REGION: " + region.getTitle());
    }





    @Override
    public void onServiceSelected(HomeService service, int pos) {
        serviceId = service.getId();
        tvServiceName.setText(service.getTitle());
        filter(serviceId, governorateId, date, salonId);
        Log.d(TAG, "onServiceSelected: " + service.getTitle());
    }

    @Override
    public void onSalonSelected(SalonData salon, int pos) {
        salonId = salon.getId();
        tvSalonName.setText(salon.getFirstName());
        Log.d(TAG, "onAppUserSelected: " + salon.getFirstName());
    }

}
