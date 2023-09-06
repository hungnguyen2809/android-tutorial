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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hungnv28.androidfirebase.databinding.ActivitySignUpBinding;
import com.hungnv28.androidfirebase.models.Person;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private FirebaseAuth auth;

    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        setEventBtnSingUp();
        setEventEdtEmail();
        setEventEdtFullName();
        setEventEdtUsername();
        setEventEdtPassword();
        setEventTxtLogin();
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

    private void setEventEdtFullName() {
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
        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
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

    private void setEventEdtEmail() {
        binding.edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if (text.toString().trim().length() != 0) {
                    binding.edtEmail.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setEventBtnSingUp() {
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = binding.edtFullName.getText().toString().trim();
                String email = binding.edtEmail.getText().toString().trim();
                String user = binding.edtUsername.getText().toString().trim();
                String pass = binding.edtPassword.getText().toString().trim();

                if (fullName.isEmpty()) {
                    binding.edtFullName.setError("Please enter full name");
                    return;
                }
                if (email.isEmpty()) {
                    binding.edtEmail.setError("Please enter email");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.edtEmail.setError("Email invalid");
                    return;
                }
                if (user.isEmpty()) {
                    binding.edtPassword.setError("Please enter user name");
                    return;
                }
                if (pass.isEmpty()) {
                    binding.edtPassword.setError("Please enter password");
                    return;
                }

                Person person = new Person(fullName, email, user, pass);
                DatabaseReference reference = database.getReference("users");
                reference.child(user).setValue(person).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Sing up Successfully", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignUpActivity.this, "Sing up error: " + task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

//                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(SignUpActivity.this, "Sing up Successfully", Toast.LENGTH_SHORT).show();
//
//                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
//                            startActivity(intent);
//                        } else {
//                            Toast.makeText(SignUpActivity.this, "Sing up error: " + task.getException().toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });

            }
        });
    }


}