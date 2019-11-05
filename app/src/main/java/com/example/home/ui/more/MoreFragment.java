package com.example.home.ui.more;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.home.MainActivity;
import com.example.home.R;
import com.example.home.ui.faq.FaqFragment;
import com.example.home.ui.privacy.PrivacyFragment;
import com.example.home.ui.stats.StatsFragment;

public class MoreFragment extends Fragment implements View.OnClickListener {

    private MoreViewModel moreViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.more_fragment, container, false);

        Button btn1 = root.findViewById(R.id.btn_stats);
        btn1.setOnClickListener(this);

        Button btn2 = root.findViewById(R.id.btn_faq);
        btn2.setOnClickListener(this);

        Button btn3 = root.findViewById(R.id.btn_privacy);
        btn3.setOnClickListener(this);

        Button btn4 = root.findViewById(R.id.btn_contactus);
        btn4.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        MainActivity main = ((MainActivity) getActivity());
        Fragment f = null;
        int titleId = 0;
        int containerId = R.id.nav_host_fragment;

        switch(v.getId()) {
            case R.id.btn_stats:
                f = new StatsFragment();
                titleId = R.string.title_stats;
                break;
            case R.id.btn_privacy:
                f = new PrivacyFragment();
                titleId = R.string.title_privacy;
                break;
            case R.id.btn_faq:
                f = new FaqFragment();
                titleId = R.string.title_faq;
                break;
            case R.id.btn_contactus:
                Toast.makeText(getContext(), "Contact Us clicked", Toast.LENGTH_SHORT).show();
                break;
        }

        if(f != null) {
            FragmentTransaction t = getFragmentManager().beginTransaction();
            t.replace(containerId, f);
            t.addToBackStack(null);
            t.commit();
        }
    }
}