package com.example.home.ui.faq;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.home.MainActivity;
import com.example.home.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FaqFragment extends Fragment {

    private FaqViewModel faqViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        faqViewModel =
                ViewModelProviders.of(this).get(FaqViewModel.class);
        View root = inflater.inflate(R.layout.faq_fragment, container, false);

        MainActivity main = (MainActivity) getActivity();
        main.getSupportActionBar().setTitle(R.string.title_faq);
        main.uncheckNav();

        final ListView faqListView = root.findViewById(R.id.faq_list);
        String[] faq = getResources().getStringArray(R.array.faq);

        List<QA> faqList = new ArrayList<>();
        for(int i=0; i+1<faq.length; i++) {
            faqList.add(new QA(faq[i], faq[i+1]));
        }

        FaqAdapter myAdapter = new FaqAdapter(
                faqList,
                getContext()
        );

        faqListView.setAdapter(myAdapter);

        return root;
    }
}
