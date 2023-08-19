package com.raiyansoft.mawed.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.raiyansoft.mawed.model.sections.SectionData;
import com.raiyansoft.mawed.utils.Const;
import com.raiyansoft.mawed.utils.HelperMethods;
import com.github.angads25.toggle.widget.LabeledSwitch;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalonsActivity extends AppCompatActivity implements AddRemoveFavoriteListener {
    private static final String TAG = SalonsActivity.class.getSimpleName();

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.pb_loading)
    ProgressBar loading;

    @BindView(R.id.recycler_search)
    RecyclerView recyclerSearch;
    @BindView(R.id.tv_nearest)
    TextView tvNearest;
    @BindView(R.id.label_switch_nearest)
    LabeledSwitch labeledSwitch;

    private SectionData section;
    private List<SalonData> listSalons;
    private SearchResultAdapter searchResultAdapter;

    @OnClick(R.id.iv_back)
    void onBackClick() {
        onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
        initUI();
        getSalons(section.getId());
    }

    private void initUI() {

        if (getIntent() != null)
            section = (SectionData) getIntent().getSerializableExtra(Const.KEY_SECTION);

        labeledSwitch.setVisibility(View.GONE);
        tvNearest.setVisibility(View.GONE);
        tvTitle.setText(section.getTitle());

        listSalons = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerSearch.setHasFixedSize(true);
        recyclerSearch.setLayoutManager(layoutManager);
    }

    private void getSalons(Integer sectionId) {

        loading.setVisibility(View.VISIBLE);
        HelperMethods.getMawedAPI()
                .getSalons(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this),
                        sectionId)
                .enqueue(new Callback<Filter>() {
                    @Override
                    public void onResponse(Call<Filter> call, Response<Filter> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()){
                            if (response.body().isStatus()){
                                if (response.body().getData() != null){
                                    listSalons.clear();
                                    listSalons.addAll(response.body().getData().getSalons());

                                    searchResultAdapter = new SearchResultAdapter(SalonsActivity.this, listSalons, SalonsActivity.this);
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
            removeFromFavorite(salonId, pos);
        else
            addToFavrite(salonId, pos);
    }

    private void addToFavrite(Integer salonId, int position) {
        HelperMethods.getMawedAPI()
                .addToFavorite(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this),
                        salonId)
                .enqueue(new Callback<AddRemoveFavorite>() {
                    @Override
                    public void onResponse(Call<AddRemoveFavorite> call, Response<AddRemoveFavorite> response) {
                        if (response.isSuccessful()){
                            if (response.body().isStatus()){
//                                searchResultAdapter.updateList(listSalons);
                                getSalons(section.getId());
//                                HelperMethods.showCustomToast(SalonsActivity.this, response.body().getMessage(), true);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AddRemoveFavorite> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    private void removeFromFavorite(Integer salonId, int position) {

        HelperMethods.getMawedAPI()
                .removeFromFavorite(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this),
                        salonId)
                .enqueue(new Callback<AddRemoveFavorite>() {
                    @Override
                    public void onResponse(Call<AddRemoveFavorite> call, Response<AddRemoveFavorite> response) {
                        if (response.isSuccessful()){
                            if (response.body().isStatus()){
//                                searchResultAdapter.updateList(listSalons);
                                getSalons(section.getId());
//                                HelperMethods.showCustomToast(SalonsActivity.this, response.body().getMessage(), true);
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
