package com.hungnv28.labfragment.lab2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.hungnv28.labfragment.R;

public class FragmentForm extends Fragment {
    TextInputEditText editText;
    MaterialButton btnSend;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lab2_form, container, false);

        editText = view.findViewById(R.id.edtLab2);
        btnSend = view.findViewById(R.id.btnLab2);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Activity activity = getActivity(); // Lab2Activity
//                Context context = getContext(); // Lab2Activity.this
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentBanner banner = (FragmentBanner) fragmentManager.findFragmentById(R.id.frameBanner);

                String content = editText.getText().toString();
                banner.txtBanner.setText(content);
            }
        });
    }
}
