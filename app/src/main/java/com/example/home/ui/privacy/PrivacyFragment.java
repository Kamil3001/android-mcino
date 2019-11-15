package com.example.home.ui.privacy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.home.MainActivity;
import com.example.home.R;

import java.util.Objects;

/*

Todo: Describe class here then comment code bits

 */

public class PrivacyFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
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
