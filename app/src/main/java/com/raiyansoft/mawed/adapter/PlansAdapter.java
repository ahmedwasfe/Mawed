package com.raiyansoft.mawed.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.listener.ItemClickListener;
import com.raiyansoft.mawed.listener.PlanClickListener;
import com.raiyansoft.mawed.model.plans.PlanData;
import com.raiyansoft.mawed.utils.Const;
import com.raiyansoft.mawed.utils.HelperMethods;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlansAdapter extends RecyclerView.Adapter<PlansAdapter.PlansHolder> {

    private Activity activity;
    private List<PlanData> listPackages;
    private LayoutInflater inflater;

    private PlanClickListener planClickListener;
    private BottomSheetDialog sheetDialog;

    public PlansAdapter(Activity activity, List<PlanData> listPackages, PlanClickListener planClickListener, BottomSheetDialog sheetDialog) {
        this.activity = activity;
        this.listPackages = listPackages;
        this.planClickListener = planClickListener;
        this.sheetDialog = sheetDialog;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public PlansHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlansHolder(inflater.inflate(R.layout.row_plans, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlansHolder holder, int position) {

        PlanData plan = listPackages.get(position);

        String price = new StringBuilder()
                .append(plan.getPrice())
                .append(" ")
                .append(HelperMethods.getCurrency(activity))
                .toString();
        String days = new StringBuilder()
                .append(plan.getDays())
                .append(" ")
                .append(activity.getString(R.string.days))
                .toString();
        holder.tvPlanName.setText(plan.getTitle());
        holder.tvPlanDays.setText(days);
        holder.tvPlanPrice.setText(price);

        if (listPackages.size() > 0) {
            if (position == 0) {
                holder.cardPlan.setBackgroundResource(R.drawable.bg_plan_blue);
                holder.ivIconStar.setBackgroundResource(R.drawable.ic_icon_star_white);
                holder.tvPlanName.setTextColor(activity.getColor(R.color.colorWhite));
                holder.tvPlanDays.setTextColor(activity.getColor(R.color.colorWhite));
                holder.tvPlanPrice.setTextColor(activity.getColor(R.color.colorWhite));
            } else if (position == 1) {

                holder.cardPlan.setBackgroundResource(R.drawable.bg_plan_gold);
                holder.ivIconStar.setBackgroundResource(R.drawable.ic_icon_star_black);
                holder.tvPlanName.setTextColor(activity.getColor(R.color.colorMainText));
                holder.tvPlanDays.setTextColor(activity.getColor(R.color.colorMainText));
                holder.tvPlanPrice.setTextColor(activity.getColor(R.color.colorMainText));
            } else {
                holder.cardPlan.setBackgroundResource(R.drawable.bg_plan_silver);
                holder.ivIconStar.setBackgroundResource(R.drawable.ic_icon_star_black);
                holder.tvPlanName.setTextColor(activity.getColor(R.color.colorMainText));
                holder.tvPlanDays.setTextColor(activity.getColor(R.color.colorMainText));
                holder.tvPlanPrice.setTextColor(activity.getColor(R.color.colorMainText));
            }
        }


        if (HelperMethods.getAppLanguage(activity).equals(Const.KEY_LANGUAGE_EN))
            Glide.with(activity).load(R.drawable.bg_circle_start).into(holder.ivCircleStart);
        else
            Glide.with(activity).load(R.drawable.bg_circle_end).into(holder.ivCircleStart);

        if (HelperMethods.getAppLanguage(activity).equals(Const.KEY_LANGUAGE_EN))
            Glide.with(activity).load(R.drawable.bg_circle_end).into(holder.ivCircleEnd);
        else
            Glide.with(activity).load(R.drawable.bg_circle_start).into(holder.ivCircleEnd);

        holder.setItemClickListener((view, pos) -> {
            planClickListener.onPlanClick(plan, sheetDialog, position);
        });

    }

    @Override
    public int getItemCount() {
        return listPackages.size();
    }

    static class PlansHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_circle_start)
        ImageView ivCircleStart;
        @BindView(R.id.iv_circle_end)
        ImageView ivCircleEnd;

        @BindView(R.id.card_plan)
        CardView cardPlan;
        @BindView(R.id.iv_icon_star)
        ImageView ivIconStar;
        @BindView(R.id.tv_plan_name)
        TextView tvPlanName;
        @BindView(R.id.tv_plan_days)
        TextView tvPlanDays;
        @BindView(R.id.tv_plan_price)
        TextView tvPlanPrice;

        private ItemClickListener itemClickListener;

        public PlansHolder(@NonNull View itemView) {
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
