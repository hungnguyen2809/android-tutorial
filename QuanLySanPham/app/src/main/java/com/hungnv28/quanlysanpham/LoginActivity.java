package com.hungnv28.quanlysanpham;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.hungnv28.quanlysanpham.dao.UserDAO;
import com.hungnv28.quanlysanpham.model.User;
import com.hungnv28.quanlysanpham.utils.Utils;

public class LoginActivity extends AppCompatActivity {

    UserDAO userDAO;

    EditText edtUsername, edtPassword;
    Button btnLogin;
    TextView btnSignup, btnForget;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        userDAO = new UserDAO(LoginActivity.this);
        Utils.transparentStatusAndNavigation(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogForgetPass();
            }
        });
    }

    private void init() {
        edtUsername = findViewById(R.id.edtLoginUsername);
        edtPassword = findViewById(R.id.edtLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignUp);
        btnForget = findViewById(R.id.btnForgetPass);
    }


    private void login() {
        String us = edtUsername.getText().toString();
        String pw = edtPassword.getText().toString();

        if (us.trim().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Vui lòng nhập tài khoản", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pw.trim().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = userDAO.checkLogin(us, pw);
        if (user != null) {
            Log.d("USER_INFO", user.toString());
            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

            MainApplication application = (MainApplication) getApplication();
            application.setUserInfo(user);

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "Tài khoản không tồn tại hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialogForgetPass() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_forget_password, null);
        builder.setView(view);

        Dialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        EditText edtUser = view.findViewById(R.id.edtForgotUser);
        EditText edtPass = view.findViewById(R.id.edtForgotPass);
        Button btnSubmit = view.findViewById(R.id.btnForgetPassSubmit);
        Button btnCancel = view.findViewById(R.id.btnForgetPassCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String us = edtUser.getText().toString();
                String pw = edtPass.getText().toString();

                if (us.trim().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập tài khoản", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pw.trim().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    userDAO.forgetPassword(us, pw);
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Đổi mật khẩu thàng công", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Toast.makeText(LoginActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}