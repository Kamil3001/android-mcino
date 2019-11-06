package com.example.home.ui.report;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.home.MainActivity;
import com.example.home.R;

public class ReportFragment extends Fragment {

    private ReportViewModel reportViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        reportViewModel =
                ViewModelProviders.of(this).get(ReportViewModel.class);
        View root = inflater.inflate(R.layout.faq_fragment, container, false);
        final TextView textView = root.findViewById(R.id.text_faq);
        reportViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        MainActivity main = (MainActivity) getActivity();
        main.getSupportActionBar().setTitle(R.string.title_report);
        main.uncheckNav();

        return root;
    }

}
