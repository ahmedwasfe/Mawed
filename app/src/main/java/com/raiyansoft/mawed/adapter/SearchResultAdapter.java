package com.raiyansoft.mawed.adapter;

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
import com.raiyansoft.mawed.model.filter.SalonData;
import com.raiyansoft.mawed.ui.activities.salonData.SalonActivity;
import com.raiyansoft.mawed.utils.Const;
import com.raiyansoft.mawed.utils.HelperMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultHolder> {

    private Activity activity;
    private List<SalonData> listSearch;
    private LayoutInflater inflater;

    private AddRemoveFavoriteListener favoriteListener;

    public SearchResultAdapter(Activity activity, List<SalonData> listSearch, AddRemoveFavoriteListener favoriteListener) {
        this.activity = activity;
        this.listSearch = listSearch;
        this.favoriteListener = favoriteListener;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public SearchResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchResultHolder(inflater.inflate(R.layout.row_search_result, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultHolder holder, int position) {

        SalonData filter = listSearch.get(position);
        if (filter != null){

            if (filter.getImage() != null)
                Glide.with(activity).load(filter.getImage()).into(holder.ivSalonImage);


            if (filter.getCommercailName() != null)
                holder.tvSalonName.setText(filter.getCommercailName());

            if (filter.getCityData() != null)
                holder.tvSalonAddress.setText(filter.getCityData());

            if (filter.isFav())
                holder.ivFavorite.setBackgroundResource(R.drawable.ic_icon_added_favorite);
            else
                holder.ivFavorite.setBackgroundResource(R.drawable.ic_icon_add_favorite);

            if (filter.isHome() != null){
                if (filter.isHome())
                    holder.ivSalonHome.setVisibility(View.VISIBLE);
                else
                    holder.ivSalonHome.setVisibility(View.INVISIBLE);
            }

            if (filter.getReview() != null){

                holder.tvRate.setText(HelperMethods.formatDecimalValue(filter.getReview()));
                holder.ratingBar.setRating(Float.parseFloat(String.valueOf(filter.getReview())));
            }else {
                holder.tvRate.setText(HelperMethods.formatDecimalValue(0.0));
                holder.ratingBar.setRating(Float.parseFloat(String.valueOf(0.0)));
            }

            holder.ivFavorite.setOnClickListener(v -> {
                favoriteListener.onAddRemoveFavorite(filter.getId(), filter.isFav(),holder.ivFavorite, position);
            });

            holder.setItemClickListener((view, pos) -> {
                activity.startActivity(new Intent(activity, SalonActivity.class)
                        .putExtra(Const.KEY_SALON_ID, filter.getId())
                        .putExtra(Const.KEY_SALON_FAV, filter.isFav()));
            });

        }
    }

    @Override
    public int getItemCount() {
        return listSearch.size();
    }

    public void updateList(List<SalonData> list) {
        listSearch.addAll(list);
        notifyDataSetChanged();
    }

    static class SearchResultHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_favorite)
        ImageView ivFavorite;
        @BindView(R.id.iv_salon_image)
        ImageView ivSalonImage;
        @BindView(R.id.tv_salon_name)
        TextView tvSalonName;
        @BindView(R.id.tv_salon_address)
        TextView tvSalonAddress;
        @BindView(R.id.iv_salon_home)
        ImageView ivSalonHome;
        @BindView(R.id.tv_rate)
        TextView tvRate;
        @BindView(R.id.ratingBar)
        AppCompatRatingBar ratingBar;

        private ItemClickListener itemClickListener;
        public SearchResultHolder(@NonNull View itemView) {
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
