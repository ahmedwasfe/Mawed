package com.raiyansoft.mawed.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class PagerBookAdapter extends FragmentStateAdapter {


    private List<Fragment> listFragments;

    public PagerBookAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        listFragments = new ArrayList<>();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return listFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return listFragments.size();
    }

    public void addFragment(Fragment fragment) {
        listFragments.add(fragment);
    }

    public void clearList() {
        listFragments.clear();
    }

    public void removeFragment(Fragment fragment){
        listFragments.remove(fragment);
    }
}
