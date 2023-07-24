package com.hungnv28.labfragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hungnv28.labfragment.lab2.FragmentBanner;
import com.hungnv28.labfragment.lab2.FragmentForm;

public class Lab2Activity extends AppCompatActivity {
    FragmentBanner banner;
    FragmentForm form;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab2);

        banner = new FragmentBanner();
        form = new FragmentForm();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frameBanner, banner)
                .add(R.id.frameForm, form)
                .commit();
    }
}