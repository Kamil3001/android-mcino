package com.example.home.ui.more;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.home.R;

public class MoreFragment extends Fragment {

//    private MoreViewModel moreViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        moreViewModel =
//                ViewModelProviders.of(this).get(MoreViewModel.class);
        return inflater.inflate(R.layout.more_fragment, container, false);

    }
}