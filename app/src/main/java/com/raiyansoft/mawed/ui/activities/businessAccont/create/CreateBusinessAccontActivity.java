package com.raiyansoft.mawed.ui.activities.businessAccont.create;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.adapter.convertSalon.AddreseAdapter;
import com.raiyansoft.mawed.adapter.convertSalon.CategoriesAdapter;
import com.raiyansoft.mawed.listener.AddressSelectedListener;
import com.raiyansoft.mawed.listener.CategorySelectedListener;
import com.raiyansoft.mawed.model.address.AddressData;
import com.raiyansoft.mawed.model.address.Addresses;
import com.raiyansoft.mawed.model.convertSalon.ConvertToSalon;
import com.raiyansoft.mawed.model.sections.SectionData;
import com.raiyansoft.mawed.model.sections.Sections;
import com.raiyansoft.mawed.ui.activities.settings.address.AddAddressActivity;
import com.raiyansoft.mawed.utils.Const;
import com.raiyansoft.mawed.utils.HelperMethods;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateBusinessAccontActivity extends AppCompatActivity implements CategorySelectedListener, AddressSelectedListener, View.OnClickListener {
    private static final String TAG = CreateBusinessAccontActivity.class.getSimpleName();


    @BindView(R.id.swipe)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rel_container)
    RelativeLayout container;
    @BindView(R.id.pb_loading)
    ProgressBar loading;
    @BindView(R.id.input_full_name)
    EditText inputFullName;
    @BindView(R.id.input_commercial_name)
    EditText inputCommercialName;
    @BindView(R.id.input_type_business)
    EditText inputTypeBusiness;
    @BindView(R.id.input_phone_number)
    EditText inputPhoneNumber;
    @BindView(R.id.input_whatsapp_number)
    EditText inputWhatsappNumber;
    @BindView(R.id.iv_arrow_category)
    ImageView ivShowCategory;
    @BindView(R.id.expandable_layout_category)
    ExpandableLayout expandableCategories;
    @BindView(R.id.recycler_categories)
    RecyclerView recyclerCategories;

    @BindView(R.id.iv_add_address)
    ImageView ivAddAddress;
    @BindView(R.id.iv_arrow_address)
    ImageView ivShowAddress;
    @BindView(R.id.expandable_layout_address)
    ExpandableLayout expandableAddress;
    @BindView(R.id.recycler_addresses)
    RecyclerView recyclerAddresses;

    private List<SectionData> listCategories;
    private List<AddressData> listAddresses;

    private int categoryId = 0;
    private int addressId = 0;

    @OnClick(R.id.iv_back)
    void onBackClick() {
        onBackPressed();
    }

    @OnClick(R.id.ll_category)
    void onCategoryClick() {
        if (expandableCategories.isExpanded())
            ivShowCategory.setBackgroundResource(R.drawable.ic_icon_arrow_down);
        else
            ivShowCategory.setBackgroundResource(R.drawable.ic_icon_arrow_up);
        expandableCategories.toggle();
    }

    @OnClick(R.id.iv_add_address)
    void onAddAddressClick() {
        startActivity(new Intent(this, AddAddressActivity.class));
    }

    @OnClick(R.id.ll_address)
    void onAddressClick() {
//        Log.d(TAG, "onAddressClick: " + listAddresses.size());
//        if (listAddresses.size() == 0){
//            startActivity(new Intent(this, AddAddressActivity.class));
//        }else {
            if (expandableAddress.isExpanded())
                ivShowAddress.setBackgroundResource(R.drawable.ic_icon_arrow_down);
            else
                ivShowAddress.setBackgroundResource(R.drawable.ic_icon_arrow_up);
            expandableAddress.toggle();
//        }
    }

    @OnClick(R.id.btn_cancel)
    void onCancelClick() {
        onBackPressed();
    }

    @OnClick(R.id.btn_send_request)
    void onSendRequestClick() {

        String fullName = inputFullName.getText().toString();
        String commercialName = inputCommercialName.getText().toString();
        String typeBusiness = inputTypeBusiness.getText().toString();
        String phoneNumber = inputPhoneNumber.getText().toString();
        String whatsappNumber = inputWhatsappNumber.getText().toString();

        if (!HelperMethods.isInternetConnected(this)){
            HelperMethods.showCustomSnackBar(this,
                    container,
                    getString(R.string.check_internet),
                    false,
                    Snackbar.LENGTH_LONG,
                    Gravity.BOTTOM,
                    this);
            loading.setVisibility(View.GONE);
            return;
        }

        if (!HelperMethods.validateFirstName(this, fullName, inputFullName) |
                !HelperMethods.validateCommercialName(this, commercialName, inputCommercialName) |
                !HelperMethods.validateTypeBusiness(this, typeBusiness, inputTypeBusiness) |
                !HelperMethods.validatePhoneNumber(this, phoneNumber, inputPhoneNumber) |
                !HelperMethods.validateWhatsappNumber(this, fullName, inputWhatsappNumber)){
            loading.setVisibility(View.GONE);
            return;
        }

        if (categoryId == 0){
            Toast.makeText(this, getString(R.string.please_select_category), Toast.LENGTH_SHORT).show();
            loading.setVisibility(View.GONE);
            return;
        }

        if (addressId == 0){
            Toast.makeText(this, getString(R.string.please_select_your_address), Toast.LENGTH_SHORT).show();
            loading.setVisibility(View.GONE);
            return;
        }

        convertToSalon(fullName, categoryId, commercialName, typeBusiness, phoneNumber, whatsappNumber, addressId);
    }

    private void convertToSalon(String fullName, int categoryId, String commercialName, String typeBusiness, String phoneNumber, String whatsappNumber, int addressId) {

        loading.setVisibility(View.VISIBLE);
        HelperMethods.getMawedAPI()
                .convertToSalon(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this),
                        fullName, commercialName, categoryId, typeBusiness,
                        phoneNumber, whatsappNumber, addressId)
                .enqueue(new Callback<ConvertToSalon>() {
                    @Override
                    public void onResponse(Call<ConvertToSalon> call, Response<ConvertToSalon> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getData() != null) {
                                    showDoneConvertSalon();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ConvertToSalon> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                        loading.setVisibility(View.GONE);
                    }
                });
    }

    private void showDoneConvertSalon() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_convert_salon, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        HelperMethods.showCustomDialog(dialog);


//        CardView card = dialogView.findViewById(R.id.card_dialog);
//        card.setBackgroundColor(Color.TRANSPARENT);

        ImageView ivCircleStart = dialogView.findViewById(R.id.iv_circle_start);
        ImageView ivCircleEnd = dialogView.findViewById(R.id.iv_circle_end);

        if (HelperMethods.getAppLanguage(this).equals(Const.KEY_LANGUAGE_EN))
            Glide.with(this).load(R.drawable.bg_circle_start).into(ivCircleStart);
        else
            Glide.with(this).load(R.drawable.bg_circle_end).into(ivCircleStart);

        if (HelperMethods.getAppLanguage(this).equals(Const.KEY_LANGUAGE_EN))
            Glide.with(this).load(R.drawable.bg_circle_end).into(ivCircleEnd);
        else
            Glide.with(this).load(R.drawable.bg_circle_start).into(ivCircleEnd);

        dialog.show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_business_account);
        ButterKnife.bind(this);
        initUI();
        getCategories();
        getAddressess();
    }

    private void initUI() {

        ivShowCategory.setBackgroundResource(R.drawable.ic_icon_arrow_down);
        ivShowAddress.setBackgroundResource(R.drawable.ic_icon_arrow_down);

        listCategories = new ArrayList<>();
        listAddresses = new ArrayList<>();


        if(HelperMethods.getCurrentUser(this) != null) {
            inputPhoneNumber.setText(HelperMethods.getCurrentUser(this).getMobile());
            inputWhatsappNumber.setText(HelperMethods.getCurrentUser(this).getMobile());
        }

        LinearLayoutManager categoryLayoutManager = new LinearLayoutManager(this);
        categoryLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerCategories.setHasFixedSize(true);
        recyclerCategories.setLayoutManager(categoryLayoutManager);

        LinearLayoutManager addressLayoutManager = new LinearLayoutManager(this);
        addressLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerAddresses.setHasFixedSize(true);
        recyclerAddresses.setLayoutManager(addressLayoutManager);

        refreshLayout.setOnRefreshListener(() -> {
            getCategories();
            getAddressess();
            refreshLayout.setRefreshing(false);
        });

    }

    private void getCategories() {

        HelperMethods.getMawedAPI()
                .getSections(
                        HelperMethods.getUserToken(this),
                        HelperMethods.getAppLanguage(this),0)
                .enqueue(new Callback<Sections>() {
                    @Override
                    public void onResponse(Call<Sections> call, Response<Sections> response) {

                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getData() != null) {
                                    listCategories.clear();
                                    listCategories.addAll(response.body().getData().getCategories().getSections());

                                    CategoriesAdapter categoriesAdapter = new CategoriesAdapter(CreateBusinessAccontActivity.this, listCategories, CreateBusinessAccontActivity.this);
                                    recyclerCategories.setAdapter(categoriesAdapter);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Sections> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    private void getAddressess() {
        Log.d(TAG, "getAddressess: ");
        HelperMethods.getMawedAPI()
                .getAddresses(HelperMethods.getAppLanguage(this),
                        HelperMethods.getUserToken(this))
                .enqueue(new Callback<Addresses>() {
                    @Override
                    public void onResponse(Call<Addresses> call, Response<Addresses> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                if (response.body().getAddressesData() != null) {
                                    listAddresses.clear();
                                    listAddresses.addAll(response.body().getAddressesData().getAddresses());
                                    AddreseAdapter addressessAdapter = new AddreseAdapter(CreateBusinessAccontActivity.this, listAddresses, CreateBusinessAccontActivity.this);
                                    recyclerAddresses.setAdapter(addressessAdapter);

                                    Log.d(TAG, "initUI: " + listAddresses.size());
                                    Log.d(TAG, "initUI: " + new Gson().toJson(listAddresses));
                                    if (listAddresses.size() == 0){
                                        ivAddAddress.setVisibility(View.VISIBLE);
                                        ivShowAddress.setVisibility(View.GONE);
                                        ivAddAddress.setBackgroundResource(R.drawable.ic_icon_add);
                                    }else {
                                        ivAddAddress.setVisibility(View.GONE);
                                        ivShowAddress.setVisibility(View.VISIBLE);
                                        ivShowAddress.setBackgroundResource(R.drawable.ic_icon_arrow_down);
                                    }

                                }
                            } else {
                                Log.d(TAG, "onResponse: " + response.body().getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Addresses> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });

    }

    @Override
    public void onCategorySelect(SectionData category, int pos) {
        categoryId = category.getId();
        Log.d(TAG, "onCategorySelect: " + category.getTitle());
    }

    @Override
    public void onAddressSelect(AddressData address, int pos) {
        addressId = address.getId();
        Log.d(TAG, "onAddressSelect: " + address.getId());
    }

    @Override
    public void onClick(View v) {
        HelperMethods.wifiSettings(this);
    }
}
