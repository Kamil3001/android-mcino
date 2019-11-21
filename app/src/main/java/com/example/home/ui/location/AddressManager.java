package com.example.home.ui.location;

import android.content.Context;

import android.location.Address;
import android.location.Geocoder;

import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddressManager {


    public String getAddress(Context context, double longitude ,double latitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String address = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            Address obj = addresses.get(0);
            address = obj.getAdminArea().substring(7);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return address;
    }
}
