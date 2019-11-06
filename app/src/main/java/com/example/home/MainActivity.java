package com.example.home;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{

    BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_contacts, R.id.navigation_tips, R.id.navigation_more)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.home_logo);
        actionBar.setDisplayUseLogoEnabled(true);
    }

    //unchecks the current navigation menu item (called by MoreFragment class when replacing fragments)
    public void uncheckNav() {
        int id = navView.getSelectedItemId();
        Menu menu = navView.getMenu();

        menu.setGroupCheckable(0, true, false);
        menu.findItem(id).setChecked(false);
        menu.setGroupCheckable(0, true, true);
    }

    public void checkNav(int id) {
        Menu menu = navView.getMenu();
        menu.setGroupCheckable(0, true, false);
        menu.findItem(id).setChecked(true);
        menu.setGroupCheckable(0, true, true);
    }

}
