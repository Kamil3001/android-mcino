package com.example.home.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.home.MainActivity;
import com.example.home.R;
import com.example.home.ui.report.ReportFragment;

/*

TODO: Comment this class and make sure methods are adequately commented

 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    private MainActivity main;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        main = (MainActivity) getActivity();
        View root = inflater.inflate(R.layout.home_fragment, container, false);
        WebView webView = root.findViewById(R.id.home_web_view);
        webView.setVerticalScrollBarEnabled(false);
        webView.loadData(getString(R.string.home_msg), "text/html; charset=utf-8", "utf-8");

        Button reportBtn = root.findViewById(R.id.btn_report);
        reportBtn.setOnClickListener(this);

        return root;
    }

    public void onResume() {
        main.checkNav(R.id.navigation_home);
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