package com.hungnv28.labfragment.lab2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hungnv28.labfragment.R;

public class FragmentBanner extends Fragment {
    public TextView txtBanner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lab2_banner, container, false);

        txtBanner = view.findViewById(R.id.txtLab2Banner);

        return view;
    }
}
