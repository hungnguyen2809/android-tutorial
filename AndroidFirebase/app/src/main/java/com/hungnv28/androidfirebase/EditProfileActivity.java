package com.hungnv28.androidfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;
import com.hungnv28.androidfirebase.databinding.ActivityEditProfileBinding;
import com.hungnv28.androidfirebase.models.Person;

public class EditProfileActivity extends AppCompatActivity {
    private ActivityEditProfileBinding binding;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();

        showUserData();
    }


    private void showUserData() {
        Bundle bundle = getIntent().getExtras();

        if (bundle == null) return;
        Person person = (Person) bundle.getSerializable("data");

        if (person == null) return;
        binding.editName.setText(person.getFullName());
        binding.editEmail.setText(person.getEmail());
        binding.editUsername.setText(person.getUsername());
        binding.editPassword.setText(person.getPassword());
    }
}