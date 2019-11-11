package com.example.home.ui.your_reports;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.home.MainActivity;
import com.example.home.R;
import com.example.home.utility.sql.DBHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class YourReportsFragment extends Fragment {

    private YourReportsViewModel mViewModel;
    private ListView sqlEntries;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.your_reports_fragment, container, false);
        mViewModel = ViewModelProviders.of(this).get(YourReportsViewModel.class);

        MainActivity main = (MainActivity) getActivity();
        main.getSupportActionBar().setTitle(R.string.title_yourreports);
        main.uncheckNav();

        sqlEntries = root.findViewById(R.id.sqlEntries);
        ArrayList<String> output = generateList(MainActivity.sql.getAllEntries());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                Objects.requireNonNull(getContext()),
                android.R.layout.simple_list_item_1,
                output
        );
        sqlEntries.setAdapter(arrayAdapter);
        return root;
    }

    private String getLocation(String latitude, String longitude){

        String location = "";
        Geocoder gcd = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), 1);
            if (addresses.size() > 0) {
                location = addresses.get(0).getLocality();
                Log.v("YOUR_REPORTS", latitude+ " " +longitude + " => " + location);
            }
        }
        catch (IOException e) {
            Log.d("YOUR_REPORTS", e.getMessage());
        }

        return location;
    }

    //TODO Fix format issues
    private ArrayList<String> generateList(ArrayList<DBHelper.Entry> entries){
        ArrayList<String> result = new ArrayList<>();
        String heading = String.format("%-26s %-6s %-7s %-20s", "\uD83D\uDCCC", " \u200E\uD83D\uDC64", "\uD83C\uDFE0", "Description");
        result.add(heading);
        for(DBHelper.Entry entry: entries){
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-26s",getLocation(entry.getLatitude(), entry.getLongitude())));
            sb.append(String.format("%-6s",entry.getPeople()));
            sb.append(String.format("%-7s", entry.getSheltered()));
            sb.append(String.format("%-20s", entry.getDescription()));
            result.add(sb.toString());
        }
        return result;
    }

    public void onResume() {
        ((MainActivity) getActivity()).uncheckNav();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_yourreports);
        super.onResume();
    }

    public void onPause(){
        super.onPause();
    }

}
