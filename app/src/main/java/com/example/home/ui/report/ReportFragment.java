package com.example.home.ui.report;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.home.MainActivity;
import com.example.home.R;
import com.example.home.ui.location.LocationFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

public class ReportFragment extends Fragment implements LocationListener {

    private ReportViewModel reportViewModel;
    private Button btnGetLocation, btnSetLocation, btnReport;
    private FloatingActionButton btnCamera;
    private EditText txtDescription, txtNum;
    private TextView txtLocation;
    private Switch swSheltered;
    private LocationManager mLocationManager;
    private String reportedLocation;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        reportViewModel = ViewModelProviders.of(this).get(ReportViewModel.class);
        View root = inflater.inflate(R.layout.report_fragment, container, false);
        MainActivity main = (MainActivity) getActivity();
        main.getSupportActionBar().setTitle(R.string.title_report);
        main.uncheckNav();

        btnGetLocation = root.findViewById(R.id.btnGetLocation);
        btnSetLocation = root.findViewById(R.id.btnSetLocation);
        btnCamera = root.findViewById(R.id.btnCamera);
        txtDescription = root.findViewById(R.id.txtDescription);
        txtNum = root.findViewById(R.id.txtNum);
        txtLocation = root.findViewById(R.id.txtLocation);
        swSheltered = root.findViewById(R.id.swSheltered);
        btnReport = root.findViewById(R.id.btnReport);

        reportViewModel.getText().observe(this, s-> txtDescription.setHint("Description"));
        reportViewModel.getText().observe(this, s-> txtNum.setHint("No. of people"));

        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        reportViewModel.getText().observe(this, s -> btnGetLocation.setOnClickListener(this::onClick));
        reportViewModel.getText().observe(this, s->btnSetLocation.setOnClickListener(this::onClick));
        reportViewModel.getText().observe(this, s->btnReport.setOnClickListener(this::onClick));
        reportViewModel.getText().observe(this, s->btnCamera.setOnClickListener(this::onClick));
        return root;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "Permission granted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void onPause(){
        super.onPause();
        mLocationManager.removeUpdates(this);
    }

    public void onResume() {
        ((MainActivity) getActivity()).checkNav(R.id.navigation_home);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_report);
        super.onResume();
    }


    @Override
    public void onLocationChanged(Location location) {
        reportedLocation = location.getLatitude() +","+location.getLongitude();
        txtLocation.setText(reportedLocation);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private void onClick(View v){
        if(v.getId() == R.id.btnSetLocation){
            FragmentTransaction ft = this.getFragmentManager().beginTransaction();
            LocationFragment locationFragment = new LocationFragment();
            locationFragment.setTargetFragment(ReportFragment.this, 1);
            ft.addToBackStack(locationFragment.getClass().getName());
            ft.add(R.id.nav_host_fragment, locationFragment, "location");
            ft.commit();
        } else if(v.getId() == R.id.btnGetLocation){
            if (checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PermissionChecker.PERMISSION_GRANTED && checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PermissionChecker.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return;
            }
            Toast.makeText(getContext(), "Getting your location", Toast.LENGTH_LONG).show();
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 500, this);
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 500, this);
        }else if(v.getId() == R.id.btnCamera){
            // TODO
        }else if(v.getId() == R.id.btnReport){
            // TODO
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            if (requestCode==1){
                reportedLocation = data.getStringExtra("location");
                txtLocation.setText(reportedLocation);
            }
        }
    }
}
