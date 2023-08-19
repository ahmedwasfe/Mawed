package com.raiyansoft.mawed.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.listener.AddRemoveFavoriteListener;
import com.raiyansoft.mawed.listener.ItemClickListener;
import com.raiyansoft.mawed.model.favorite.FavoritesData;
import com.raiyansoft.mawed.ui.activities.salonData.SalonActivity;
import com.raiyansoft.mawed.utils.Const;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder> {

    private Activity activity;
    private List<FavoritesData> listFavorites;
    private LayoutInflater inflater;

    private AddRemoveFavoriteListener favoriteListener;


    public FavoriteAdapter(Activity activity, List<FavoritesData> listFavorites, AddRemoveFavoriteListener favoriteListener) {
        this.activity = activity;
        this.listFavorites = listFavorites;
        this.favoriteListener = favoriteListener;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteHolder(inflater.inflate(R.layout.row_search_result, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHolder holder, int position) {

        FavoritesData favorite = listFavorites.get(position);
        if (favorite != null) {
            if (favorite.getImage() != null)
                Glide.with(activity).load(favorite.getImage()).into(holder.ivSalonImage);

            String name = new StringBuilder()
                    .append(favorite.getFirstName())
                    .append(" ")
                    .append(favorite.getLastName())
                    .toString();
            holder.tvSalonName.setText(favorite.getCommercailName());

            if (favorite.isFav())
                holder.ivFavorite.setBackgroundResource(R.drawable.ic_icon_added_favorite);
            else
                holder.ivFavorite.setBackgroundResource(R.drawable.ic_icon_add_favorite);
        }

        holder.ivFavorite.setOnClickListener(v -> {
            favoriteListener.onAddRemoveFavorite(favorite.getId(),favorite.isFav(), holder.ivFavorite, position);
        });

        holder.setItemClickListener((view, pos) -> {
            activity.startActivity(new Intent(activity, SalonActivity.class)
                    .putExtra(Const.KEY_SALON_ID, favorite.getId())
                    .putExtra(Const.KEY_SALON_FAV, favorite.isFav()));
        });

    }

    @Override
    public int getItemCount() {
        return listFavorites.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(int position) {
        listFavorites.remove(position);
        notifyDataSetChanged();
    }

    static class FavoriteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_favorite)
        ImageView ivFavorite;
        @BindView(R.id.iv_salon_image)
        ImageView ivSalonImage;
        @BindView(R.id.tv_salon_name)
        TextView tvSalonName;
        @BindView(R.id.tv_rate)
        TextView tvRate;
        @BindView(R.id.ratingBar)
        AppCompatRatingBar ratingBar;

        private ItemClickListener itemClickListener;

        public FavoriteHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
