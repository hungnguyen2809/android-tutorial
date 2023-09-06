package com.hungnv28.androidfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hungnv28.androidfirebase.databinding.ActivityLoginBinding;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        setEventTxtLogin();
        setEventEdtPassword();
        setEventEdtUsername();
        setEventBtnSingUp();
    }

    private void setEventEdtUsername() {
        binding.edtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if (text.toString().trim().length() != 0) {
                    binding.edtUsername.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    private void setEventTxtLogin() {
        binding.signRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    private void setEventEdtPassword() {
        binding.edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if (text.toString().trim().length() != 0) {
                    binding.edtPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    private void setEventBtnSingUp() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = binding.edtUsername.getText().toString().trim();
                String pass = binding.edtPassword.getText().toString().trim();

                if (user.isEmpty()) {
                    binding.edtUsername.setError("Please enter email");
                    return;
                }
                if (pass.isEmpty()) {
                    binding.edtPassword.setError("Please enter password");
                    return;
                }

                DatabaseReference reference = database.getReference("users");
                Query query = reference.orderByChild("username").equalTo(user);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()) {
                            binding.edtUsername.setError("Username not exists");
                            binding.edtUsername.requestFocus();
                            return;
                        }

                        binding.edtUsername.setError(null);
                        String passFormDb = snapshot.child(user).child("password").getValue(String.class);

                        if (!Objects.equals(passFormDb, pass)) {
                            binding.edtPassword.setError("Password is wrong");
                            binding.edtPassword.requestFocus();
                            return;
                        }

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });


//                auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                    @Override
//                    public void onSuccess(AuthResult authResult) {
//                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
//
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });
    }
}