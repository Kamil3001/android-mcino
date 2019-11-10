package com.example.home;

import android.database.CursorWindow;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.home.utility.sql.DBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity{

    BottomNavigationView navView;
    public static DBHelper sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        sql = new DBHelper(this);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_contacts, R.id.navigation_tips)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

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

    //Todo modify this to somehow move about the backstack (currently the manager doesn't see the one accessed from MoreFragment)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                int count = getSupportFragmentManager().getBackStackEntryCount();
                if(count == 0) {
                    onBackPressed();
                }
                else {
                    //TODO
                }
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }








}
