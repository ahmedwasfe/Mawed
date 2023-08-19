package com.raiyansoft.mawed.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.model.userSalon.SalonImages;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SalonImagesAdapter extends RecyclerView.Adapter<SalonImagesAdapter.SalonImagesHolder> {

    private Activity activity;
    private List<SalonImages> listImages;
    private LayoutInflater inflater;

    public SalonImagesAdapter(Activity activity, List<SalonImages> listImages) {
        this.activity = activity;
        this.listImages = listImages;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public SalonImagesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SalonImagesHolder(inflater.inflate(R.layout.row_salon_images, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SalonImagesHolder holder, int position) {
        SalonImages image = listImages.get(position);
        if (image != null){
            if (image.getImage() != null)
                Glide.with(activity).load(image.getImage()).into(holder.rivSalonImage);

        }
    }

    @Override
    public int getItemCount() {
        return listImages.size();
    }

    static class SalonImagesHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.riv_salon_image)
        RoundedImageView rivSalonImage;

        public SalonImagesHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
