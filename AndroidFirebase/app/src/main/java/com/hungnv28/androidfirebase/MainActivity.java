package com.hungnv28.androidfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

        binding.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUser();
            }
        });
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

    private void editUser() {
        Bundle bundlePrv = getIntent().getExtras();
        if (bundlePrv == null) return;

        Person person = (Person) bundlePrv.getSerializable("data");
        if (person == null) return;

        Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable("data", person);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}