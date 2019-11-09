package com.example.home.ui.tips;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.home.R;

public class TipsFragment extends Fragment {

    private TipsViewModel tipsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tipsViewModel =
                ViewModelProviders.of(this).get(TipsViewModel.class);
        View root = inflater.inflate(R.layout.tips_fragment, container, false);
        final ListView dosListView = root.findViewById(R.id.dos_list);
        final ListView dontsListView = root.findViewById(R.id.donts_list);
        String[] dos = getResources().getStringArray(R.array.dos_list);
        String[] donts = getResources().getStringArray(R.array.donts_list);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_1,
                dos
        );

        dosListView.setAdapter(arrayAdapter);
        setListHeight(dosListView);

        arrayAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_1,
                donts
        );

        dontsListView.setAdapter(arrayAdapter);
//        setListHeight(dontsListView);


        return root;
    }

    //solution to second list view overflowing found here: https://stackoverflow.com/questions/17693578/android-how-to-display-2-listviews-in-one-activity-one-after-the-other
    private void setListHeight(ListView lv) {
        ListAdapter la = lv.getAdapter();
        if(la == null)
            return;

        int height = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(lv.getWidth(), View.MeasureSpec.UNSPECIFIED);
        for(int i = 0; i < la.getCount(); i++) {
            View listItem = la.getView(i, null, lv);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            height += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = lv.getLayoutParams();
        params.height = height + (lv.getDividerHeight() * (la.getCount()-1));
        lv.setLayoutParams(params);
        lv.requestLayout();
    }
}