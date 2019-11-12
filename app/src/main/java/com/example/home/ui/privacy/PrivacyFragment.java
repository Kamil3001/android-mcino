package com.example.home.ui.privacy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.home.MainActivity;
import com.example.home.R;

import java.util.Objects;

public class PrivacyFragment extends Fragment {

    private PrivacyViewModel privacyViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        privacyViewModel =
                ViewModelProviders.of(this).get(PrivacyViewModel.class);
        View root = inflater.inflate(R.layout.privacy_fragment, container, false);
        final ListView listView = root.findViewById(R.id.privacy);
        String[] privacy = getResources().getStringArray(R.array.privacy);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                Objects.requireNonNull(getContext()),
                android.R.layout.simple_list_item_1,
                privacy
        );

        listView.setAdapter(arrayAdapter);

        MainActivity main = (MainActivity) getActivity();
        main.getSupportActionBar().setTitle(R.string.title_privacy);
        main.uncheckNav();

        return root;
    }

}
