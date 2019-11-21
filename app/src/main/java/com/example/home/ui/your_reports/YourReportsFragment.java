package com.example.home.ui.your_reports;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.home.MainActivity;
import com.example.home.R;
import com.example.home.utility.sql.DBHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

        fillRows(MainActivity.sql.getAllEntries());

        return root;
    }

    private void fillRows(ArrayList<DBHelper.Entry> output) {
        int i=0;
        TableRow row;
        TextView[] columns = new TextView[4];

        TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
        );
        tableLayoutParams.setMargins(2, 0, 0, 2);

        int dp10 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        int[] rowColours = new int[2];
        rowColours[0] = Color.parseColor("#82c5fa");
        rowColours[1] = Color.WHITE;


        for(DBHelper.Entry entry : output) {
            row = new TableRow(getContext());
            row.setId(i++);
            row.setPadding(dp10,dp10,dp10,dp10);
            row.setLayoutParams(tableLayoutParams);
            row.setBackgroundColor(rowColours[i%2]);

            for(int j=0; j<4; j++) {
                columns[j] = new TextView(getContext());
                columns[j].setLayoutParams(new TableRow.LayoutParams(j));
                columns[j].setPadding(dp10, 0, dp10, 0);
                columns[j].setTextSize(18);
                columns[j].setGravity(Gravity.CENTER);
            }

            columns[0].setText(getLocation(entry.getLatitude(), entry.getLongitude()));
            columns[1].setText(String.valueOf(entry.getPeople()));
            columns[2].setText(entry.getSheltered());
            columns[3].setText(entry.getDescription());
            columns[3].setGravity(Gravity.START);

            for(int j=0; j<4; j++) {
                row.addView(columns[j]);
            }

            reports.addView(row);
        }
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

    public void onResume() {
        ((MainActivity) getActivity()).uncheckNav();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_yourreports);
        super.onResume();
    }

    public void onPause(){
        super.onPause();
    }

}
