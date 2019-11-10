package com.example.home.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.home.MainActivity;
import com.example.home.R;
import com.example.home.ui.report.ReportFragment;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.home_fragment, container, false);
        WebView webView = root.findViewById(R.id.home_web_view);
        webView.setVerticalScrollBarEnabled(false);
        webView.loadData(getString(R.string.home_msg), "text/html; charset=utf-8", "utf-8");

        Button reportBtn = root.findViewById(R.id.btn_report);
        reportBtn.setOnClickListener(this);

        return root;
    }

    public void onResume() {
        MainActivity main = (MainActivity) getActivity();
        main.checkNav(R.id.navigation_home);
        main.getSupportActionBar().setTitle(R.string.title_home);
        main.getSupportActionBar().setDisplayShowHomeEnabled(true);
        super.onResume();
    }

    public void onClick(View v) {
        Fragment f = null;
        int containerId = R.id.nav_host_fragment;

        if (v.getId() == R.id.btn_report) {
            getFragmentManager().beginTransaction()
                    .replace(containerId, new ReportFragment())
                    .addToBackStack(null)
                    .commit();
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
    }
}