package com.raiyansoft.mawed.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.model.ImageData;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SalonProfileImagesAdapter extends RecyclerView.Adapter<SalonProfileImagesAdapter.SalonProfileImagesHolder> {
    private static final String TAG = SalonProfileImagesAdapter.class.getSimpleName();

    private Activity activity;
    private List<ImageData> listImages;
    private LayoutInflater inflater;

    public SalonProfileImagesAdapter(Activity activity, List<ImageData> listImages) {
        this.activity = activity;
        this.listImages = listImages;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public SalonProfileImagesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SalonProfileImagesHolder(inflater.inflate(R.layout.row_salon_profile_images, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SalonProfileImagesHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + listImages.size());
        ImageData image = listImages.get(position);
        if (image != null){
            Glide.with(activity).load(image.getImage()).into(holder.rivImage);

            holder.rivImage.setOnClickListener(v -> {
                listImages.remove(position);
                notifyDataSetChanged();
            });
        }
    }

    @Override
    public int getItemCount() {
        return listImages.size();
    }

    static class SalonProfileImagesHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.riv_image)
        RoundedImageView rivImage;

        public SalonProfileImagesHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
