package com.example.home.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Button reportBtn = root.findViewById(R.id.btn_report);
        reportBtn.setOnClickListener(this);

        return root;
    }

    public void onResume() {
        ((MainActivity) getActivity()).checkNav(R.id.navigation_home);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_home);
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
        }
    }
}