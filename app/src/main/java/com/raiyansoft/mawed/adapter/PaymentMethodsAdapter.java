package com.raiyansoft.mawed.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.listener.ItemClickListener;
import com.raiyansoft.mawed.listener.PaymentMethodSelectListener;
import com.raiyansoft.mawed.model.payment.paymentMethods.PaymentMethodData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentMethodsAdapter extends RecyclerView.Adapter<PaymentMethodsAdapter.PaymentMethodsHolder> {

    private Activity activity;
    private List<PaymentMethodData> listPayments;
    private LayoutInflater inflater;

    private ImageView itemSelected = null;

    private PaymentMethodSelectListener paymentMethodSelectListener;

    public PaymentMethodsAdapter(Activity activity, List<PaymentMethodData> listPayments, PaymentMethodSelectListener paymentMethodSelectListener) {
        this.activity = activity;
        this.listPayments = listPayments;
        this.paymentMethodSelectListener = paymentMethodSelectListener;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public PaymentMethodsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PaymentMethodsHolder(inflater.inflate(R.layout.row_payments, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentMethodsHolder holder, int position) {
        PaymentMethodData payment = listPayments.get(position);
        if (payment != null){
            holder.tvPayment.setText(payment.getTitle());
            if(payment.getImage() != null)
                Glide.with(activity).load(payment.getImage()).into(holder.ivPayment);

            holder.setItemClickListener((view, pos) -> {
                if (itemSelected != null)
                    itemSelected.setBackgroundResource(R.drawable.ic_icon_service_uncheck);

                holder.ivCheckboxPayment.setBackgroundResource(R.drawable.ic_icon_service_check);
                itemSelected = holder.ivCheckboxPayment;

                paymentMethodSelectListener.onPaymentSelected(payment, position);
            });

        }
    }

    @Override
    public int getItemCount() {
        return listPayments.size();
    }

    static class PaymentMethodsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.rel_payment)
        RelativeLayout relPayment;
        @BindView(R.id.iv_check)
        ImageView ivCheckboxPayment;
        @BindView(R.id.tv_payment)
        TextView tvPayment;
        @BindView(R.id.iv_payment)
        ImageView ivPayment;

        private ItemClickListener itemClickListener;

        public PaymentMethodsHolder(@NonNull View itemView) {
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
