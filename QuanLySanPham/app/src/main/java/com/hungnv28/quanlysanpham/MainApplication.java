package com.hungnv28.quanlysanpham;

import android.app.Application;

import com.hungnv28.quanlysanpham.model.User;

public class MainApplication extends Application {
    private User userInfo;

    public void setUserInfo(User user) {
        this.userInfo = user;
    }

    public User getUserInfo() {
        return this.userInfo;
    }
}
