package com.example.home.ui.contacts;

import android.database.Cursor;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import com.example.home.MainActivity;
import com.example.home.MyApplication;
import com.example.home.R;
import com.example.home.ui.location.AddressManager;
import com.example.home.utility.sql.DBAssetHelper;

import java.util.ArrayList;

/*

TODO: Comment this class and make sure methods are adequately commented

 */

public class ContactsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private DBAssetHelper dbAssetHelper;
    private ContactsAdapter contactsAdapter;
    private static String county = "";
    private ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dbAssetHelper = new DBAssetHelper(super.getContext());
        View root = inflater.inflate(R.layout.contacts_fragment, container, false);
        setAddress();

        spinner = root.findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.counties_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        listView = root.findViewById(R.id.contacts_list);

        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item;

        if(!county.equals("")) {
            item = county;
            int index=0;
            String [] counties = getContext().getResources().getStringArray(R.array.counties_array);
            while(index<counties.length){
                if(county.equals(counties[index])) {
                    spinner.setSelection(index);
                    index=counties.length;
                }
                index++;
            }
        }else {
            item = adapterView.getItemAtPosition(i).toString();
            if(item.contains("¬"))
                item = item.substring(1);
        }
        Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

        ArrayList<ServiceDetails> details = new ArrayList<>();
        for (String[] str : setServices(item))
            details.add(new ServiceDetails(str[0], str[1], str[2], str[3]));

        contactsAdapter = new ContactsAdapter(getActivity(), details);
        listView.setAdapter(contactsAdapter);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onResume() {
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        super.onResume();
    }
    private ArrayList<String[]> setServices(String item) {
        ArrayList<String[]> services = new ArrayList<>();
        Cursor cursor = dbAssetHelper.getColumns(item);

        do{
            String [] serviceDetails = new String[4];
            serviceDetails[0] = cursor.getString(cursor.getColumnIndex("Name")).trim();
            serviceDetails[1] = cursor.getString(cursor.getColumnIndex("PhoneNum")).trim();
            serviceDetails[2] = cursor.getString(cursor.getColumnIndex("Website")).trim();
            serviceDetails[3] = cursor.getString(cursor.getColumnIndex("county_name")).trim();
            services.add(serviceDetails);
        }while (cursor.moveToNext());
        return services;
    }
    private void setAddress(){
        if(((MyApplication) getActivity().getApplication()).hasLocation()) {
            AddressManager addressManager = new AddressManager();
            double longitude = ((MyApplication) getActivity().getApplication()).getLongitude();
            double latitude = ((MyApplication) getActivity().getApplication()).getLatitude();
            county = addressManager.getAddress(getContext(), longitude, latitude);
        }
    }
}

