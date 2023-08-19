package com.raiyansoft.mawed.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.listener.ItemClickListener;
import com.raiyansoft.mawed.listener.SalonCategoriesListener;
import com.raiyansoft.mawed.model.userSalon.AllCategory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SalonCategoriesAdapter extends RecyclerView.Adapter<SalonCategoriesAdapter.BarberTypeHolder> {

    private Activity activity;
    private List<AllCategory> listTypes;
    private LayoutInflater inflater;
    private SalonCategoriesListener categoriesListener;


    public SalonCategoriesAdapter(Activity activity, List<AllCategory> listTypes,  SalonCategoriesListener categoriesListener) {
        this.activity = activity;
        this.listTypes = listTypes;
        this.categoriesListener = categoriesListener;
        inflater  = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public BarberTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BarberTypeHolder(inflater.inflate(R.layout.row_salon_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BarberTypeHolder holder, int position) {
        AllCategory category = listTypes.get(position);
        if (category != null){
            holder.tvCategoryName.setText(category.getCategoryEmpName());
            holder.setItemClickListener((view, pos) -> {
                categoriesListener.onCategoryClick(category, pos);
            });

        }
    }

    @Override
    public int getItemCount() {
        return listTypes.size();
    }

    static class BarberTypeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_all_categories)
        TextView tvCategoryName;

        private ItemClickListener itemClickListener;

        public BarberTypeHolder(@NonNull View itemView) {
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
