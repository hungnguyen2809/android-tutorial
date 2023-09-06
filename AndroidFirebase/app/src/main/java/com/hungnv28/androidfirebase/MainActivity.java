package com.hungnv28.androidfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hungnv28.androidfirebase.databinding.ActivityMainBinding;
import com.hungnv28.androidfirebase.models.Person;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        showUserData();
    }

    private void showUserData() {
        Bundle bundle = getIntent().getExtras();

        if (bundle == null) return;
        Person person = (Person) bundle.getSerializable("data");

        if (person == null) return;
        binding.titleName.setText(person.getFullName());
        binding.titleUsername.setText(person.getUsername());
        binding.profileName.setText(person.getFullName());
        binding.profileEmail.setText(person.getEmail());
        binding.profileUsername.setText(person.getUsername());
        binding.profilePassword.setText(person.getPassword());
    }
}