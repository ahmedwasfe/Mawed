package com.raiyansoft.mawed.ui.fragments.user.favorites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.adapter.FavoriteAdapter;
import com.raiyansoft.mawed.listener.AddRemoveFavoriteListener;
import com.raiyansoft.mawed.model.favorite.Favorites;
import com.raiyansoft.mawed.model.favorite.FavoritesData;
import com.raiyansoft.mawed.model.favorite.addRemove.AddRemoveFavorite;
import com.raiyansoft.mawed.ui.activities.auth.LoginActivity;
import com.raiyansoft.mawed.utils.HelperMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesFragment extends Fragment implements AddRemoveFavoriteListener {
    private static final String TAG = FavoritesFragment.class.getSimpleName();

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
    @BindView(R.id.recycler_favorites)
    RecyclerView recyclerFavorites;

    private List<FavoritesData> listFavorites;
    private FavoriteAdapter favoriteAdapter;

    private static FavoritesFragment instance;

    public static FavoritesFragment getInstance() {
        return instance == null ? new FavoritesFragment() : instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, layoutView);
        initUI();
        loadFavorites();
        return layoutView;
    }

    private void initUI() {

        showLogin(getActivity(), recyclerFavorites, cardLogin, tvTextLogout, btnLogout, btnCancel);

        listFavorites = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerFavorites.setHasFixedSize(true);
        recyclerFavorites.setLayoutManager(layoutManager);

        favoriteAdapter = new FavoriteAdapter(getActivity(), listFavorites, this);

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

    private void loadFavorites() {
        if (HelperMethods.getCurrentUser(getActivity()) != null) {
            loading.setVisibility(View.VISIBLE);

            HelperMethods.getMawedAPI()
                    .getFavorites(HelperMethods.getAppLanguage(getActivity()),
                            HelperMethods.getUserToken(getActivity()))
                    .enqueue(new Callback<Favorites>() {
                        @Override
                        public void onResponse(Call<Favorites> call, Response<Favorites> response) {
                            loading.setVisibility(View.GONE);
                            if (response.isSuccessful()) {
                                if (response.body().isStatus()) {
                                    if (response.body().getData().getFavorites() != null) {
                                        listFavorites.clear();
                                        listFavorites.addAll(response.body().getData().getFavorites());

                                        recyclerFavorites.setAdapter(favoriteAdapter);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Favorites> call, Throwable t) {
                            Log.e(TAG, "onFailure:  " + t.getMessage());
                            loading.setVisibility(View.GONE);
                        }
                    });

        }

    }


    @Override
    public void onAddRemoveFavorite(Integer salonId, boolean isFavorite, ImageView imageView, int pos) {
        removeFromFavorite(salonId, pos);
    }

    private void removeFromFavorite(Integer salonId, int position) {

        HelperMethods.getMawedAPI()
                .removeFromFavorite(HelperMethods.getAppLanguage(getActivity()),
                        HelperMethods.getUserToken(getActivity()),
                        salonId)
                .enqueue(new Callback<AddRemoveFavorite>() {
                    @Override
                    public void onResponse(Call<AddRemoveFavorite> call, Response<AddRemoveFavorite> response) {
                        if (response.isSuccessful()){
                            if (response.body().isStatus()){
                                favoriteAdapter.updateList(position);
//                                HelperMethods.showCustomToast(getActivity(), response.body().getMessage(), true);
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
