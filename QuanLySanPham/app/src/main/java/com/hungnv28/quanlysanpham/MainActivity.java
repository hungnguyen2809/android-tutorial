package com.hungnv28.quanlysanpham;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.hungnv28.quanlysanpham.fragment.FavoriteFragment;
import com.hungnv28.quanlysanpham.fragment.HomeFragment;
import com.hungnv28.quanlysanpham.fragment.ProductFragment;
import com.hungnv28.quanlysanpham.fragment.SettingFragment;
import com.hungnv28.quanlysanpham.model.User;
import com.hungnv28.quanlysanpham.utils.Utils;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private HomeFragment homeFragment;
    private ProductFragment productFragment;
    private FavoriteFragment favoriteFragment;
    private SettingFragment settingFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setToolbar();
        setDrawerToggle();
        setNavigationView();
        Utils.setStatusBarColor(this, R.color.status_bar_home);
    }

    private void init() {
        toolbar = findViewById(R.id.toolBar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);

        homeFragment = new HomeFragment();
        setFragmentView(homeFragment, true);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setTitle("Trang chủ");
    }

    private void setDrawerToggle() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(MainActivity.this,
                drawerLayout, toolbar, R.string.open, R.string.close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
    }

    private void setFragmentView(Fragment fragment, boolean add) {
        if (add) {
            getSupportFragmentManager().beginTransaction().add(R.id.frameContainer, fragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, fragment).commit();
        }
    }

    private void setNavigationView() {
        View viewHeader = navigationView.getHeaderView(0);
        TextView txtFullName = viewHeader.findViewById(R.id.txtNavigationFullName);
        TextView txtUserName = viewHeader.findViewById(R.id.txtNavigationUserName);

        User userInfo = ((MainApplication) getApplication()).getUserInfo();
        txtFullName.setText(userInfo.getFullName());
        txtUserName.setText("#" + userInfo.getUsername());

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.menuHome) {
                    setFragmentView(homeFragment, false);
                } else if (id == R.id.menuProduct) {
                    if (productFragment == null) {
                        productFragment = new ProductFragment();
                    }
                    setFragmentView(productFragment, false);
                } else if (id == R.id.menuFavorite) {
                    if (favoriteFragment == null) {
                        favoriteFragment = new FavoriteFragment();
                    }
                    setFragmentView(favoriteFragment, false);
                } else if (id == R.id.menuSetting) {
                    if (settingFragment == null) {
                        settingFragment = new SettingFragment();
                    }
                    setFragmentView(settingFragment, false);
                } else if (id == R.id.menuLogout) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

                getSupportActionBar().setTitle(item.getTitle());
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
    }
}