package com.example.home.ui.faq;

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

public class FaqFragment extends Fragment {

    private FaqViewModel faqViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        faqViewModel =
                ViewModelProviders.of(this).get(FaqViewModel.class);
        View root = inflater.inflate(R.layout.contacts_fragment, container, false);
        final TextView textView = root.findViewById(R.id.text_contacts);
        faqViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

}
