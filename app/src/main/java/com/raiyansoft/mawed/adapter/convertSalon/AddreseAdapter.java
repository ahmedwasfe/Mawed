package com.raiyansoft.mawed.adapter.convertSalon;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.listener.AddressSelectedListener;
import com.raiyansoft.mawed.listener.ItemClickListener;
import com.raiyansoft.mawed.model.address.AddressData;
import com.raiyansoft.mawed.utils.HelperMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddreseAdapter extends RecyclerView.Adapter<AddreseAdapter.AddressHolder> {

    private Activity activity;
    private List<AddressData> listAddresses;
    private LayoutInflater inflater;

    private AddressSelectedListener selectedListener;

    private ImageView itemSelected = null;

    public AddreseAdapter(Activity activity, List<AddressData> listAddresses, AddressSelectedListener selectedListener) {
        this.activity = activity;
        this.listAddresses = listAddresses;
        this.selectedListener = selectedListener;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddressHolder(inflater.inflate(R.layout.row_governorates, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddressHolder holder, int position) {
        AddressData address = listAddresses.get(position);
        if (address != null){
//            holder.tvName.setText(address.getTitle());
            if (HelperMethods.getAppLanguage(activity).equals("ar"))
                holder.tvName.setText(address.getCity().getTitleAr());
            else
                holder.tvName.setText(address.getCity().getTitleEn());

            String fullAddress = new StringBuilder()
                    .append(HelperMethods.getAppLanguage(activity).equals("ar") ? address.getCity().getTitleAr() : address.getCity().getTitleEn())
                    .append(", ")
                    .append(HelperMethods.getAppLanguage(activity).equals("ar") ? address.getGovernate().getTitleAr() : address.getGovernate().getTitleEn())
                    .append(", ")
                    .append(activity.getString(R.string.widget))
                    .append(" ")
                    .append(address.getWidget())
                    .append(", ")
                    .append(activity.getString(R.string.street))
                    .append(" ")
                    .append(address.getStreet())
                    .append(", ")
                    .append(activity.getString(R.string.house_number))
                    .append(" ")
                    .append(address.getHouseNumber())
                    .toString();
            holder.tvFullAddress.setText(fullAddress);

            holder.setItemClickListener((view, pos) -> {
                if (itemSelected != null)
                    itemSelected.setBackgroundResource(R.drawable.ic_icon_service_uncheck);

                holder.ivCheck.setBackgroundResource(R.drawable.ic_icon_service_check);
                itemSelected = holder.ivCheck;
                selectedListener.onAddressSelect(address, pos);
            });
        }
    }

    @Override
    public int getItemCount() {
        return listAddresses.size();
    }

    class AddressHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_check)
        public ImageView ivCheck;
        @BindView(R.id.tv_name)
        public TextView tvName;
        @BindView(R.id.tv_full_address)
        TextView tvFullAddress;

        private ItemClickListener itemClickListener;

        public AddressHolder(@NonNull View itemView) {
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
