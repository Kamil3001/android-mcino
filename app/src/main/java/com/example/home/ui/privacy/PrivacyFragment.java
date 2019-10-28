package com.example.home.ui.privacy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.home.R;

public class PrivacyFragment extends Fragment {

    private PrivacyViewModel privacyViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        privacyViewModel =
                ViewModelProviders.of(this).get(PrivacyViewModel.class);
        View root = inflater.inflate(R.layout.contacts_fragment, container, false);
        final TextView textView = root.findViewById(R.id.text_contacts);
        privacyViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

}
