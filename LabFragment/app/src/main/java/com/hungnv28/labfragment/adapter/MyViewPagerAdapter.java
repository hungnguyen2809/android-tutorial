package com.hungnv28.labfragment.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.hungnv28.labfragment.lab3.Lab3Tab1;
import com.hungnv28.labfragment.lab3.Lab3Tab2;
import com.hungnv28.labfragment.lab3.Lab3TabDynamic;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    String[] str = {"Page 1", "Page 2", "Page 3"};

    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        //fragmentActivity chính là activity chứa cái fragment đó
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new Lab3TabDynamic();

        String txt = str[position];

        Bundle bundle = new Bundle();
        bundle.putString("content", txt);
        fragment.setArguments(bundle);

//        if (position == 1) {
//            fragment = new Lab3Tab2();
//        } else {
//            fragment = new Lab3Tab1();
//        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        //trả về số lượng view paper, hiện tại có 2 fragment nên trả về 2
//        return 2
        return str.length;
    }
}
