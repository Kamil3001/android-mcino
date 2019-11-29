package com.example.home.ui.faq;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.home.MainActivity;
import com.example.home.R;

import java.util.ArrayList;
import java.util.List;

/*

This Fragment implements the FAQ screen using a listView with a custom adapter
reading the question-answer pairs from the strings.xml

 */

public class FaqFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.faq_fragment, container, false);

        // set title and make sure bottomNavigationBar is unchecked
        MainActivity main = (MainActivity) getActivity();
        main.getSupportActionBar().setTitle(R.string.title_faq);
        main.uncheckNav();

        // get the listView
        final ListView faqListView = root.findViewById(R.id.faq_list);

        // Read in faq list into an array and then place it into a List of QA objects
        String[] faq = getResources().getStringArray(R.array.faq);
        List<QA> faqList = new ArrayList<>();
        for(int i=0; i+1<faq.length; i++) {
            faqList.add(new QA(faq[i], faq[i+1]));
        }

        //applying my custom adapter
        FaqAdapter myAdapter = new FaqAdapter(
                faqList,
                getContext()
        );
        faqListView.setAdapter(myAdapter);

        return root;
    }
}
