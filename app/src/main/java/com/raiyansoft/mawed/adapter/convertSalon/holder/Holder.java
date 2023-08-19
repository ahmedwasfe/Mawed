package com.raiyansoft.mawed.adapter.convertSalon.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.listener.ItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.iv_check)
    public ImageView ivCheck;
    @BindView(R.id.tv_name)
    public TextView tvName;

    private ItemClickListener itemClickListener;

    public Holder(@NonNull View itemView) {
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
