package com.hungnv28.quanlymonhoc.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hungnv28.quanlymonhoc.R;

public class RegisterActivity extends AppCompatActivity {
    private MaterialButton btnRegister, btnCancel;
    private TextInputEditText edtUsername, edtPassword, edtRePassword;
    private TextInputLayout txtUsername, txtPassword, txtRePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bootstrap();
        setEventBtnCancel();
        setEventBtnRegister();
        setEventEdtUsername();
        setEventEdtPassword();
        setEventEdtRePassword();
    }

    private void bootstrap() {
        btnRegister = findViewById(R.id.btnRegisterRegis);
        btnCancel = findViewById(R.id.btnCancelRegis);
        edtUsername = findViewById(R.id.edtUsernameRegis);
        edtPassword = findViewById(R.id.edtPasswordRegis);
        edtRePassword = findViewById(R.id.edtRePasswordRegis);
        txtUsername = findViewById(R.id.txtUsernameRegis);
        txtPassword = findViewById(R.id.txtPasswordRegis);
        txtRePassword = findViewById(R.id.txtRePasswordRegis);
    }

    private void setEventBtnCancel() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setEventBtnRegister() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getEditableText().toString();
                String password = edtPassword.getEditableText().toString();
                String rePassword = edtRePassword.getEditableText().toString();

                if (validateData(username, password, rePassword)) {
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putString("username", username.trim());
                bundle.putString("password", password.trim());

                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(1001, intent);
                finish();
            }
        });
    }

    private void setEventEdtUsername() {
        edtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String username = s.toString();
                if (username.trim().length() == 0) {
                    txtUsername.setError("Vui lòng nhập tài khoản");
                } else if (username.trim().length() < 4) {
                    txtUsername.setError("Tài khoản tối thiểu 4 ký tự");
                } else {
                    txtUsername.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setEventEdtPassword() {
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = s.toString();
                if (password.trim().length() == 0) {
                    txtPassword.setError("Vui lòng nhập mật khẩu");
                } else if (password.trim().length() <= 6) {
                    txtPassword.setError("Mật khẩu tối thiểu 6 ký tự");
                } else {
                    txtPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setEventEdtRePassword() {
        edtRePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String rePass = s.toString();
                String password = edtPassword.getEditableText().toString();

                if (rePass.trim().length() == 0) {
                    txtRePassword.setError("Vui lòng nhập lại mật khẩu");
                } else if (!password.trim().equals(rePass.trim())) {
                    txtRePassword.setError("Nhập lại mật khẩu không đúng");
                } else {
                    txtRePassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    private boolean validateData(String username, String password, String rePass) {
        boolean flag = false;
        //setError ở TextInputLayout (hiển thị error ở bên dưới) hoặc  có thể set error ở TextInputEditText (hiển thị error dạng tooltip)

        if (username.trim().length() == 0) {
            txtUsername.setError("Vui lòng nhập tài khoản");
            flag = true;
        } else if (username.trim().length() < 4) {
            txtUsername.setError("Tài khoản tối thiểu 4 ký tự");
            flag = true;
        } else {
            txtUsername.setError(null);
        }

        if (password.trim().length() == 0) {
            txtPassword.setError("Vui lòng nhập mật khẩu");
            flag = true;
        } else if (password.trim().length() <= 6) {
            txtPassword.setError("Mật khẩu tối thiểu 6 ký tự");
            flag = true;
        } else {
            txtPassword.setError(null);
        }

        if (rePass.trim().length() == 0) {
            txtRePassword.setError("Vui lòng nhập lại mật khẩu");
            flag = true;
        } else if (!password.trim().equals(rePass.trim())) {
            txtRePassword.setError("Nhập lại mật khẩu không đúng");
            flag = true;
        } else {
            txtRePassword.setError(null);
        }

        return flag;
    }
}