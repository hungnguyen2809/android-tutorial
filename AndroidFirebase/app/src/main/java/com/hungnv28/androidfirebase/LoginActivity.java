package com.hungnv28.androidfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hungnv28.androidfirebase.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        setEventBtnSingUp();
        setEventEdtEmail();
        setEventEdtPassword();
        setEventTxtLogin();
    }

    private void setEventTxtLogin() {
        binding.signRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                    binding.edtPassword.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setEventEdtEmail() {
        binding.edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if (text.toString().trim().length() != 0) {
                    binding.edtEmail.setError("");
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
                String email = binding.edtEmail.getText().toString().trim();
                String pass = binding.edtPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    binding.edtEmail.setError("Please enter email");
                    return;
                }
                if (pass.isEmpty()) {
                    binding.edtPassword.setError("Please enter password");
                    return;
                }

                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Login error: " + task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}