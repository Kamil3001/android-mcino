package com.example.home.ui.your_reports;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.home.MainActivity;
import com.example.home.R;
import com.example.home.utility.sql.DBHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class YourReportsFragment extends Fragment {



    private TableLayout reports;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.your_reports_fragment, container, false);

        MainActivity main = (MainActivity) getActivity();
        main.getSupportActionBar().setTitle(R.string.title_yourreports);
        main.uncheckNav();

        reports = root.findViewById(R.id.reportTable);

        ArrayList<DBHelper.Entry> output = MainActivity.sql.getAllEntries();


        int i=0;
        TableRow row;
        TextView location;
        TextView numOfPeople;
        TextView sheltered;
        TextView description;

        TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
        );
        tableLayoutParams.setMargins(2, 0, 0, 2);


        int dp5 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
        int dp10 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        int dp20 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        int[] rowColours = new int[2];
        rowColours[0] = Color.parseColor("#82c5fa");
        rowColours[1] = Color.WHITE;


        for(DBHelper.Entry entry : output) {
            row = new TableRow(getContext());
            row.setId(i++);
            row.setPadding(dp10,dp10,dp10,dp10);
            row.setLayoutParams(tableLayoutParams);
            row.setBackgroundColor(rowColours[i%2]);

            location = new TextView(getContext());
            location.setLayoutParams(new TableRow.LayoutParams(0));
            location.setText(getLocation(entry.getLatitude(), entry.getLongitude()));
            location.setTextSize(18);
            location.setPadding(dp5,0,dp5,0);
            location.setGravity(Gravity.CENTER);

            numOfPeople = new TextView(getContext());
            numOfPeople.setLayoutParams(new TableRow.LayoutParams(1));
            numOfPeople.setText(String.valueOf(entry.getPeople()));
            numOfPeople.setTextSize(18);
            numOfPeople.setPadding(dp5,0,dp5,0);
            numOfPeople.setGravity(Gravity.CENTER);

            sheltered = new TextView(getContext());
            sheltered.setLayoutParams(new TableRow.LayoutParams(2));
            sheltered.setText(entry.getSheltered());
            sheltered.setTextSize(18);
            sheltered.setPadding(dp5,0,dp5,0);
            sheltered.setGravity(Gravity.CENTER);

            description = new TextView(getContext());
            description.setLayoutParams(new TableRow.LayoutParams(3));
            description.setText(entry.getDescription());
            description.setTextSize(18);
            description.setPadding(dp20,0,dp5,0);


            row.addView(location);
            row.addView(numOfPeople);
            row.addView(sheltered);
            row.addView(description);

            reports.addView(row);
        }

//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
//                Objects.requireNonNull(getContext()),
//                android.R.layout.simple_list_item_1,
//                output
//        );
//        sqlEntries.setAdapter(arrayAdapter);
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
