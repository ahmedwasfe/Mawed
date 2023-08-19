package com.raiyansoft.mawed.ui.fragments.user.booking;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.adapter.ServiceAdapter;
import com.raiyansoft.mawed.adapter.convertSalon.AddreseAdapter;
import com.raiyansoft.mawed.listener.AddressSelectedListener;
import com.raiyansoft.mawed.listener.SendServicesListener;
import com.raiyansoft.mawed.model.address.AddressData;
import com.raiyansoft.mawed.model.address.Addresses;
import com.raiyansoft.mawed.model.employee.EmployeeDataCat;
import com.raiyansoft.mawed.model.employee.Empolyee;
import com.raiyansoft.mawed.model.employee.review.Review;
import com.raiyansoft.mawed.model.favorite.addRemove.AddRemoveFavorite;
import com.raiyansoft.mawed.model.userOrders.sendOrder.SendService;
import com.raiyansoft.mawed.ui.activities.booking.BookingActivity;
import com.raiyansoft.mawed.ui.activities.settings.address.AddAddressActivity;
import com.raiyansoft.mawed.utils.HelperMethods;
import com.google.gson.Gson;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarberServiceFragment extends Fragment implements SendServicesListener, AddressSelectedListener {
    private static final String TAG = BarberServiceFragment.class.getSimpleName();

    @BindView(R.id.swipe)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.pb_loading)
    ProgressBar loading;
    @BindView(R.id.iv_favorite)
    ImageView ivFavorite;
    @BindView(R.id.civ_employee_image)
    CircleImageView civEmployeeImage;
    @BindView(R.id.tv_employee_name)
    TextView tvEmployeeName;
    @BindView(R.id.tv_employee_description)
    TextView tvEmployeeDescription;
    @BindView(R.id.ratingBar)
    AppCompatRatingBar ratingBar;

    @BindView(R.id.recycler_service)
    RecyclerView recyclerService;

    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.iv_add_address)
    ImageView ivAddAddress;
    @BindView(R.id.iv_arrow)
    ImageView ivShowAddress;
    @BindView(R.id.expandable_layout_address)
    ExpandableLayout expandableAddress;
    @BindView(R.id.recycler_address)
    RecyclerView recyclerAddress;
    @BindView(R.id.sw_home)
    SwitchCompat swOnOff;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.input_note)
    EditText inputNote;

    private BookingActivity bookingActivity;

    private List<AddressData> listAddresses;
    private List<EmployeeDataCat> listEmpServices;
    private ServiceAdapter serviceAdapter;
    // TODO HOME 1
    private List<SendService> services;
    private int addressId;
    private int serviceTotal;
    public List<SendService> listServiesSelected;

    private AddreseAdapter addressessAdapter;

    int isHome;

    public BarberServiceFragment() {
    }

    private static BarberServiceFragment instance;

    public static BarberServiceFragment getInstance() {
        return instance == null ? new BarberServiceFragment() : instance;
    }

    @OnClick(R.id.iv_add_rate)
    void onAddRateClick() {
        showRateDialog();
    }

    @OnClick(R.id.ll_address)
    void onAddressClick() {
        if (expandableAddress.isExpanded())
            ivShowAddress.setBackgroundResource(R.drawable.ic_icon_arrow_white_down);
        else
            ivShowAddress.setBackgroundResource(R.drawable.ic_icon_arrow_white_up);
        expandableAddress.toggle();
    }

    @OnClick(R.id.iv_add_address)
    void onAddAddressClick() {
        startActivity(new Intent(getActivity(), AddAddressActivity.class));
    }

    @OnClick(R.id.btn_book)
    void onBookClick() {
        nextStep();
    }

    private void nextStep() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                String note = inputNote.getText().toString();
                if (services.size() == 0) {
                    HelperMethods.showCustomToast(getActivity(), getString(R.string.please_select_services), false);
                    return;
                }

                Log.d(TAG, "run: " + HelperMethods.isHome);
                Log.d(TAG, "run: " + bookingActivity.salonId);

                if (isHome == 1) {
                    if (addressId == 0) {
                        HelperMethods.showCustomToast(getActivity(), getString(R.string.please_select_your_address), false);
                        return;
                    }
                }

                bookingActivity.listServiesSelected = services;

                bookingActivity.note = note;
                bookingActivity.addressId = addressId;
                bookingActivity.serviceTotal = serviceTotal;
                bookingActivity.pagerBookAdapter.addFragment(BarberDateTimeFragment.getInstance());
                bookingActivity.viewPagerBook.setCurrentItem(bookingActivity.viewPagerBook.getCurrentItem() + 1);

            }
        }, 100);
    }

    @OnClick(R.id.iv_back)
    void onBackClick() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.iv_favorite)
    void onFavoriteClick() {
        Log.d(TAG, "onFavoriteClick: " + bookingActivity.isFavorite);
        if (bookingActivity.isFavorite)
            removeFromFavorite(bookingActivity.salonId);
        else
            addToFavrite(bookingActivity.salonId);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_barber_service, container, false);
        ButterKnife.bind(this, layoutView);

        initUI();
        bookingActivity.loadEmployeeData(civEmployeeImage, tvEmployeeName,
                tvEmployeeDescription, ratingBar, listEmpServices,
                serviceAdapter, recyclerService);

        getAddressess();
        return layoutView;
    }

    private void initUI() {

        bookingActivity = (BookingActivity) getActivity();

        loadEmployeeData();

        Log.d(TAG, "initUI SALON: " + bookingActivity.salonId);
        Log.d(TAG, "initUI EMPID: " + bookingActivity.employeeId);

        ivShowAddress.setBackgroundResource(R.drawable.ic_icon_arrow_white_down);



        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerService.setHasFixedSize(true);
        recyclerService.setLayoutManager(layoutManager);

        LinearLayoutManager addressLayoutManager = new LinearLayoutManager(getActivity());
        addressLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerAddress.setHasFixedSize(true);
        recyclerAddress.setLayoutManager(addressLayoutManager);

        services = new ArrayList<>();
        listEmpServices = new ArrayList<>();
        listAddresses = new ArrayList<>();
        serviceAdapter = new ServiceAdapter(getActivity(), listEmpServices, this);
        addressessAdapter = new AddreseAdapter(getActivity(), listAddresses, this);

        refreshLayout.setOnRefreshListener(() -> {
            loadEmployeeData();
            bookingActivity.loadEmployeeData(civEmployeeImage, tvEmployeeName,
                    tvEmployeeDescription, ratingBar, listEmpServices,
                    serviceAdapter, recyclerService);
            refreshLayout.setRefreshing(false);
        });

        if (bookingActivity.isFavorite){
            Glide.with(getActivity()).load(R.drawable.ic_icon_added_favorite).into(ivFavorite);
        }else {
            Glide.with(getActivity()).load(R.drawable.ic_icon_add_favorite).into(ivFavorite);
        }

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
                    reviewSalon(dialog, bookingActivity.employeeId, rating.getRating());
                });

        dialog.show();
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
                                    if(isHome == 1)
                                        llAddress.setVisibility(View.VISIBLE);
                                    else
                                        llAddress.setVisibility(View.GONE);
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

    private void getAddressess() {

        loading.setVisibility(View.VISIBLE);

        HelperMethods.getMawedAPI()
                .getAddresses(HelperMethods.getAppLanguage(getActivity()),
                        HelperMethods.getUserToken(getActivity()))
                .enqueue(new Callback<Addresses>() {
                    @Override
                    public void onResponse(Call<Addresses> call, Response<Addresses> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                listAddresses.clear();
                                listAddresses.addAll(response.body().getAddressesData().getAddresses());

                                if (listAddresses.size() == 0){
                                    ivAddAddress.setVisibility(View.VISIBLE);
                                    ivShowAddress.setVisibility(View.GONE);
                                    ivAddAddress.setBackgroundResource(R.drawable.ic_icon_add);
                                }else {
                                    ivAddAddress.setVisibility(View.GONE);
                                    ivShowAddress.setVisibility(View.VISIBLE);
                                    ivShowAddress.setBackgroundResource(R.drawable.ic_icon_arrow_down);
                                }

                                Log.d(TAG, "onResponse: " + listAddresses.size());
                                recyclerAddress.setAdapter(addressessAdapter);
                            } else {
                                Log.d(TAG, "onResponse: " + response.body().getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Addresses> call, Throwable t) {
                        loading.setVisibility(View.GONE);
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });

    }

    @Override
    public void onSendService(List<SendService> services, int pos, boolean isCheck) {
        Log.d(TAG, "onSendService: " + services.size());
        Log.d(TAG, "onSendService: " + new Gson().toJson(services));

        int total = 0;
        for (SendService service : services) {
            total += service.getPrice();
        }

        serviceTotal = total;
        this.services = services;
        String totalPrice = new StringBuilder()
                .append(total)
                .append(" ")
                .append(HelperMethods.getCurrency(getActivity()))
                .toString();
        tvTotalPrice.setText(totalPrice);
        Log.d(TAG, "onSendService: " + total);
    }

    @Override
    public void onAddressSelect(AddressData address, int pos) {
        addressId = address.getId();
    }

    @Override
    public void onResume() {
        super.onResume();
        getAddressess();
    }

    private void addToFavrite(Integer salonId) {
        Log.d(TAG, "addToFavrite: " + salonId);
        HelperMethods.getMawedAPI()
                .addToFavorite(HelperMethods.getAppLanguage(getActivity()),
                        HelperMethods.getUserToken(getActivity()),
                        salonId)
                .enqueue(new Callback<AddRemoveFavorite>() {
                    @Override
                    public void onResponse(Call<AddRemoveFavorite> call, Response<AddRemoveFavorite> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                Glide.with(getActivity()).load(R.drawable.ic_icon_added_favorite).into(ivFavorite);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AddRemoveFavorite> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    private void removeFromFavorite(Integer salonId) {

        HelperMethods.getMawedAPI()
                .removeFromFavorite(HelperMethods.getAppLanguage(getActivity()),
                        HelperMethods.getUserToken(getActivity()),
                        salonId)
                .enqueue(new Callback<AddRemoveFavorite>() {
                    @Override
                    public void onResponse(Call<AddRemoveFavorite> call, Response<AddRemoveFavorite> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                Glide.with(getActivity()).load(R.drawable.ic_icon_add_favorite).into(ivFavorite);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AddRemoveFavorite> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }
}
