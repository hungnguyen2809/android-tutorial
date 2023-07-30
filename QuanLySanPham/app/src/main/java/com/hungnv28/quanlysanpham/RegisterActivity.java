package com.hungnv28.quanlysanpham;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hungnv28.quanlysanpham.dao.UserDAO;
import com.hungnv28.quanlysanpham.model.User;
import com.hungnv28.quanlysanpham.utils.CommonUtils;

public class RegisterActivity extends AppCompatActivity {

    UserDAO userDAO;
    CommonUtils commonUtils;

    EditText edtUsername, edtPassword, edtFullName;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        userDAO = new UserDAO(RegisterActivity.this);
        commonUtils = new CommonUtils(RegisterActivity.this);
        commonUtils.transparentStatusAndNavigation();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                String fullName = edtFullName.getText().toString();

                if (username.trim().isEmpty() || password.trim().isEmpty() || fullName.trim().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu tối thiểu 6 ký tự", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = new User(username.trim(), password.trim(), fullName.trim());
                try {
                    userDAO.registerUser(user);
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (Exception exception) {
                    Toast.makeText(RegisterActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {
        edtUsername = findViewById(R.id.edtRegisterUsername);
        edtPassword = findViewById(R.id.edtRegisterPassword);
        edtFullName = findViewById(R.id.edtRegisterFullName);
        btnRegister = findViewById(R.id.btnRegister);
    }
}