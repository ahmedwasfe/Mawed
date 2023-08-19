package com.raiyansoft.mawed.ui.fragments.user.sections;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.adapter.SectionsAdapter;
import com.raiyansoft.mawed.model.sections.SectionData;
import com.raiyansoft.mawed.utils.Const;
import com.raiyansoft.mawed.utils.PreferencesManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SectionsFragment extends Fragment {
    private static final String TAG = SectionsFragment.class.getSimpleName();

    @BindView(R.id.pb_loading)
    ProgressBar loading;
    @BindView(R.id.recycler_sections)
    RecyclerView recyclerSections;

    private List<SectionData> listSections;

    private static SectionsFragment instance;
    public static SectionsFragment getInstance(){
        return instance == null ? new SectionsFragment() : instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_sections, container, false);
        ButterKnife.bind(this, layoutView);
        initUI();
        getSections();
        return layoutView;
    }

    private void initUI() {

        listSections = new ArrayList<>();

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerSections.setHasFixedSize(true);
        recyclerSections.setLayoutManager(layoutManager);

//        List<SectionData> listSections = new ArrayList<>();
//        listSections.add(new SectionData("Women's Salons", getActivity().getColor(R.color.colorWomnsSalon),getActivity().getColor(R.color.colorMainText), R.drawable.ic_icon_women_salon));
//        listSections.add(new SectionData("Men's Salons", getActivity().getColor(R.color.colorMensSalon), getActivity().getColor(R.color.colorWhite), R.drawable.ic_icon_men_salon));
//        listSections.add(new SectionData("Meals restaurant", getActivity().getColor(R.color.colorMeals), getActivity().getColor(R.color.colorWhite), R.drawable.ic_icon_meal_resturants));
//        listSections.add(new SectionData("Football stadium", getActivity().getColor(R.color.colorStadium), getActivity().getColor(R.color.colorMainText), R.drawable.ic_icon_football_stadium));
//        listSections.add(new SectionData("Dermatology Clinic", getActivity().getColor(R.color.colorDermatologyClinic), getActivity().getColor(R.color.colorMainText), R.drawable.ic_icon_dermatology_clinic));
//        listSections.add(new SectionData("Dental Clinic", getActivity().getColor(R.color.colorDentalClinic), getActivity().getColor(R.color.colorWhite), R.drawable.ic_icon_dental_clinic));
    }

    private void getSections() {

        listSections = PreferencesManager.getSections(getActivity(), Const.KEY_SECTIONS);
//        Log.d(TAG, "getSections: " + new Gson().toJson(listSections));

        SectionsAdapter sectionsAdapter = new SectionsAdapter(getActivity(), listSections);
        recyclerSections.setAdapter(sectionsAdapter);

//        loading.setVisibility(View.VISIBLE);

//        HelperMethods.getMawedAPI()
//                .getSections(HelperMethods.getAppLanguage(getActivity()))
//                .enqueue(new Callback<Sections>() {
//                    @Override
//                    public void onResponse(Call<Sections> call, Response<Sections> response) {
//                        loading.setVisibility(View.GONE);
//                        if (response.isSuccessful()){
//                            if (response.body().isStatus()){
//                                if (response.body().getData() != null){
//                                    listSections.clear();
//                                    listSections.addAll(response.body().getData().getCategories().getSections());
//
//
//                                }
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Sections> call, Throwable t) {
//                        Log.e(TAG, "onFailure: " + t.getMessage());
//                        loading.setVisibility(View.GONE);
//                    }
//                });
    }
}
