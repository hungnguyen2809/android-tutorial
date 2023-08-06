package com.hungnv28.quanlymonhoc.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hungnv28.quanlymonhoc.MainActivity;
import com.hungnv28.quanlymonhoc.R;

public class LoginActivity extends AppCompatActivity {
    private MaterialButton btnLogin, btnRegister;
    private MaterialCheckBox cbRemember;
    private TextInputEditText edtUsername, edtPassword;
    private TextInputLayout txtUsername, txtPassword;

    private String userRegister = "", passRegister = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bootstrap();
        setEventBtnLogin();
        setEventBtnRegister();
        setEventAutoFillUserPass();
    }

    private void bootstrap() {
        btnLogin = findViewById(R.id.btnLoginLogin);
        btnRegister = findViewById(R.id.btnRegisterLogin);
        cbRemember = findViewById(R.id.cbRememberLogin);
        edtUsername = findViewById(R.id.edtUsernameLogin);
        edtPassword = findViewById(R.id.edtPasswordLogin);
        txtUsername = findViewById(R.id.txtUsernameLogin);
        txtPassword = findViewById(R.id.txtPasswordLogin);
    }

    private void setEventBtnLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getEditableText().toString();
                String password = edtPassword.getEditableText().toString();

                if (validateData(username, password)) return;

                if (!username.trim().equals(userRegister) || !password.trim().equals(passRegister)) {
                    Toast.makeText(LoginActivity.this, "Thông tin tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (cbRemember.isChecked()) {
                    SharedPreferences preferences = getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("username", username.trim());
                    editor.putString("password", password.trim());
                    editor.putBoolean("isRemember", cbRemember.isChecked());
                    editor.apply();
                } else {
                    SharedPreferences preferences = getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();
                }

                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private boolean validateData(String username, String password) {
        boolean flag = false;
        if (username.trim().length() == 0) {
            txtUsername.setError("Vui lòng nhập tài khoản");
            flag = true;
        } else {
            txtUsername.setError(null);
        }

        if (password.trim().length() == 0) {
            txtPassword.setError("Vui lòng nhập mật khẩu");
            flag = true;
        } else {
            txtPassword.setError(null);
        }

        return flag;
    }

    private void setEventBtnRegister() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                launcherActivityRegister.launch(intent);
            }
        });
    }

    private final ActivityResultLauncher<Intent> launcherActivityRegister = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 1001) {
                        Bundle bundle = result.getData().getExtras();
                        String username = bundle.getString("username");
                        String password = bundle.getString("password");
                        Log.d("LoginActivity_USER", username + ", " + password);

                        userRegister = username;
                        passRegister = password;
                        Toast.makeText(LoginActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                    }
                }
            });

    private void setEventAutoFillUserPass() {
        SharedPreferences preferences = getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        boolean isRemember = preferences.getBoolean("isRemember", false);

        if (isRemember) {
            String username = preferences.getString("username", "");
            String password = preferences.getString("password", "");
            userRegister = username;
            passRegister = password;
            edtUsername.setText(username);
            edtPassword.setText(password);
            cbRemember.setChecked(true);
        }
    }
}

