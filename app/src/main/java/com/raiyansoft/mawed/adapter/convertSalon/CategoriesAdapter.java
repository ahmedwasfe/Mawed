package com.raiyansoft.mawed.adapter.convertSalon;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.adapter.convertSalon.holder.Holder;
import com.raiyansoft.mawed.listener.CategorySelectedListener;
import com.raiyansoft.mawed.model.sections.SectionData;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<Holder> {

    private Activity activity;
    private List<SectionData> listCategories;
    private LayoutInflater inflater;

    private CategorySelectedListener selectedListener;

    private ImageView itemSelected = null;

    public CategoriesAdapter(Activity activity, List<SectionData> listCategories, CategorySelectedListener selectedListener) {
        this.activity = activity;
        this.listCategories = listCategories;
        this.selectedListener = selectedListener;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(inflater.inflate(R.layout.row_governorates, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        SectionData category = listCategories.get(position);
        if (category != null){
            holder.tvName.setText(category.getTitle());

            holder.setItemClickListener((view, pos) -> {
                if (itemSelected != null)
                    itemSelected.setBackgroundResource(R.drawable.ic_icon_service_uncheck);

                holder.ivCheck.setBackgroundResource(R.drawable.ic_icon_service_check);
                itemSelected = holder.ivCheck;
                selectedListener.onCategorySelect(category, pos);
            });
        }
    }

    @Override
    public int getItemCount() {
        return listCategories.size();
    }
}
