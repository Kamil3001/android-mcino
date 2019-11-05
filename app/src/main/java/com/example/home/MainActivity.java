package com.example.home;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.home.ui.faq.FaqFragment;
import com.example.home.ui.privacy.PrivacyFragment;
import com.example.home.ui.stats.StatsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

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

                }
                return false;
        }
        return super.onOptionsItemSelected(item);
    }
}
