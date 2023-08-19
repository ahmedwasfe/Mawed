package com.raiyansoft.mawed.listener;

import android.widget.ImageView;

public interface AddRemoveFavoriteListener {

    void onAddRemoveFavorite(Integer salonId, boolean isFavorite, ImageView imageView, int pos);
}
