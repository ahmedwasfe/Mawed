package com.raiyansoft.mawed.adapter.home;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.listener.ItemClickListener;
import com.raiyansoft.mawed.model.home.Ad;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SliderAdsAdapter extends RecyclerView.Adapter<SliderAdsAdapter.SliderAdsHolder> {

    private Activity activity;
    private List<Ad> listSlider;
    private LayoutInflater inflater;

    public SliderAdsAdapter(Activity activity, List<Ad> listSlider) {
        this.activity = activity;
        this.listSlider = listSlider;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public SliderAdsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderAdsHolder(inflater.inflate(R.layout.row_slider_ads, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderAdsHolder holder, int position) {

        Ad ad = listSlider.get(position);
        if (ad != null){
            if (ad.getImage() != null)
                Glide.with(activity).load(ad.getImage()).into(holder.rivSlider);

            holder.setItemClickListener((view, pos) -> {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ad.getUrl())));
            });
        }

    }

    @Override
    public int getItemCount() {
        return listSlider.size();
    }

    static class SliderAdsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.riv_slider)
        RoundedImageView rivSlider;

        private ItemClickListener itemClickListener;

        public SliderAdsHolder(@NonNull View itemView) {
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
