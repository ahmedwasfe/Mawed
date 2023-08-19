package com.raiyansoft.mawed.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.listener.DeleteAddressListener;
import com.raiyansoft.mawed.model.address.AddressData;
import com.raiyansoft.mawed.utils.HelperMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressessAdapter extends RecyclerView.Adapter<AddressessAdapter.AddressesHolder> {
    private static final String TAG = AddressessAdapter.class.getSimpleName();

    private Activity activity;
    private List<AddressData> listAddresses;
    private LayoutInflater inflater;

    private DeleteAddressListener deleteAddressListener;

    public AddressessAdapter(Activity activity, List<AddressData> listAddresses, DeleteAddressListener deleteAddressListener) {
        this.activity = activity;
        this.listAddresses = listAddresses;
        this.deleteAddressListener = deleteAddressListener;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public AddressesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddressesHolder(inflater.inflate(R.layout.row_address_book, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddressesHolder holder, int position) {

        AddressData address = listAddresses.get(position);
        if (address != null){
            Log.d(TAG, "onBindViewHolder: " + address.getAddress());
            if (address.getAddress() != null)
                holder.tvAddress.setText(address.getAddress());
            else
                holder.tvAddress.setText(address.getTitle());

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

            holder.ivRemove.setOnClickListener(v -> {
                deleteAddressListener.onDeleteAddress(address, position);
            });
        }
    }

    public void updateList(int position) {
        listAddresses.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listAddresses.size();
    }

    static class AddressesHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_remove)
        ImageView ivRemove;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_full_address)
        TextView tvFullAddress;

        public AddressesHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
