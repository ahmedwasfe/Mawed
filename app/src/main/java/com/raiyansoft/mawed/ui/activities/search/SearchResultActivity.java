package com.raiyansoft.mawed.ui.activities.search;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.adapter.SearchResultAdapter;
import com.raiyansoft.mawed.listener.AddRemoveFavoriteListener;
import com.raiyansoft.mawed.model.favorite.addRemove.AddRemoveFavorite;
import com.raiyansoft.mawed.model.filter.Filter;
import com.raiyansoft.mawed.model.filter.SalonData;
import com.raiyansoft.mawed.utils.Const;
import com.raiyansoft.mawed.utils.HelperMethods;
import com.raiyansoft.mawed.utils.PreferencesManager;
import com.github.angads25.toggle.widget.LabeledSwitch;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends AppCompatActivity implements AddRemoveFavoriteListener {
    private static final String TAG = SearchResultActivity.class.getSimpleName();

    @BindView(R.id.pb_loading)
    ProgressBar loading;
    @BindView(R.id.recycler_search)
    RecyclerView recyclerSearch;
    @BindView(R.id.label_switch_nearest)
    LabeledSwitch labeledSwitch;

    private List<SalonData> listFilter;
    private SearchResultAdapter searchResultAdapter;

    private int serviceId;
    private int governorateId;
    private int salonId;
    private String date;

    @OnClick(R.id.iv_back)
    void onBackClick() {
        HelperMethods.servicesId = 0;
        HelperMethods.servicesName = "";
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        HelperMethods.servicesId = 0;
        HelperMethods.servicesName = "";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
        initUI();
        filter(0.0,0.0);
    }

    private void initUI() {

        if (getIntent() != null) {
            serviceId = getIntent().getIntExtra(Const.KEY_SERVICE_ID, 0);
            governorateId = getIntent().getIntExtra(Const.KEY_GOVERNORATE_ID, 0);
            salonId = getIntent().getIntExtra(Const.KEY_SALON_ID, 0);
            date = getIntent().getStringExtra(Const.KEY_DATE);
        }

        Log.d(TAG, "initUI: " + serviceId);
        Log.d(TAG, "initUI: " + governorateId);
        Log.d(TAG, "initUI: " + date);
        Log.d(TAG, "initUI: " + salonId);

        listFilter = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerSearch.setHasFixedSize(true);
        recyclerSearch.setLayoutManager(layoutManager);

        labeledSwitch.setOnToggledListener((toggleableView, isOn) -> {
            double lat = Double.parseDouble(PreferencesManager.loadAppData(this, Const.KEY_CURRENT_LATITUDE));
            double lng = Double.parseDouble(PreferencesManager.loadAppData(this, Const.KEY_CURRENT_LONGITUDE));
            if (isOn){
                filter(lat, lng);
            }else {
                filter(0.0, 0.0);
            }
        });

    }

    private void filter(double lat, double lng) {

        loading.setVisibility(View.VISIBLE);

        Log.d(TAG, "filter: " + lat);
        Log.d(TAG, "filter: " + lng);
        Log.d(TAG, "filter: " + serviceId);
        Log.d(TAG, "filter: " + salonId);
        Log.d(TAG, "filter: " + date);
        Log.d(TAG, "filter: " + governorateId);

        HelperMethods.getMawedAPI()
                .filter(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this),
                        serviceId, governorateId, date, salonId,
                        lat, lng)
                .enqueue(new Callback<Filter>() {
                    @Override
                    public void onResponse(Call<Filter> call, Response<Filter> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getData() != null) {
                                    listFilter.clear();
                                    listFilter.addAll(response.body().getData().getSalons());
                                    Log.d(TAG, "onResponse: " + listFilter.size());
                                    searchResultAdapter = new SearchResultAdapter(SearchResultActivity.this, listFilter, SearchResultActivity.this);
                                    recyclerSearch.setAdapter(searchResultAdapter);
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

    @Override
    public void onAddRemoveFavorite(Integer salonId, boolean isFavorite, ImageView imageView, int pos) {
        if (isFavorite)
            removeFromFavorite(salonId, imageView, pos);
        else
            addToFavrite(salonId, imageView, pos);
    }

    private void addToFavrite(Integer salonId, ImageView imageView, int position) {
        HelperMethods.getMawedAPI()
                .addToFavorite(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this),
                        salonId)
                .enqueue(new Callback<AddRemoveFavorite>() {
                    @Override
                    public void onResponse(Call<AddRemoveFavorite> call, Response<AddRemoveFavorite> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
//                                searchResultAdapter.updateList(listFilter);
                                filter(0.0, 0.0);
//                                HelperMethods.showCustomToast(SearchResultActivity.this, response.body().getMessage(), true);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AddRemoveFavorite> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    private void removeFromFavorite(Integer salonId, ImageView imageView, int position) {

        HelperMethods.getMawedAPI()
                .removeFromFavorite(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this),
                        salonId)
                .enqueue(new Callback<AddRemoveFavorite>() {
                    @Override
                    public void onResponse(Call<AddRemoveFavorite> call, Response<AddRemoveFavorite> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
//                                searchResultAdapter.updateList(listFilter);
                                filter(0.0, 0.0);
//                                HelperMethods.showCustomToast(SearchResultActivity.this, response.body().getMessage(), true);
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
