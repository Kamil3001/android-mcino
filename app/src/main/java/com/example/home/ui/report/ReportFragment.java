package com.example.home.ui.report;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
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

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

public class ReportFragment extends Fragment implements LocationListener {

    private static final int RESULT_OK = -1;
    private static final int REQUEST_PASS_DATA = 1;
    private static final int REQUEST_TAKE_PHOTO = 2;
    private static final int REQUEST_PERMISSION_LOCATION = 1;
    private static final int REQUEST_PERMISSION_STORAGE = 2;
    private ReportViewModel reportViewModel;

    private Button btnGetLocation, btnSetLocation, btnReport;
    private FloatingActionButton btnCamera;
    private EditText txtDescription, txtNum;
    private TextView txtLocation;
    private Switch swSheltered;
    private LocationManager mLocationManager;
    private String reportedLocation;

    private Uri uriImage;
    private Bitmap bmImage;


    private String location, people, description, sheltered, image;

    private MainActivity main;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        reportViewModel = ViewModelProviders.of(this).get(ReportViewModel.class);
        View root = inflater.inflate(R.layout.report_fragment, container, false);
        main = (MainActivity) getActivity();
        main.getSupportActionBar().setTitle(R.string.title_report);
        main.uncheckNav();

        btnGetLocation = root.findViewById(R.id.btnGetLocation);
        btnSetLocation = root.findViewById(R.id.btnSetLocation);
        btnCamera = root.findViewById(R.id.btnCamera);
        btnReport = root.findViewById(R.id.btnReport);
        txtDescription = root.findViewById(R.id.txtDescription);
        txtNum = root.findViewById(R.id.txtNum);
        txtLocation = root.findViewById(R.id.txtLocation);
        swSheltered = root.findViewById(R.id.swSheltered);

        reportViewModel.getText().observe(this, s-> txtDescription.setHint("Description"));
        reportViewModel.getText().observe(this, s-> txtNum.setHint("No. of people"));

        mLocationManager = (LocationManager) main.getSystemService(Context.LOCATION_SERVICE);

        reportViewModel.getText().observe(this, s -> btnGetLocation.setOnClickListener(this::onClick));
        reportViewModel.getText().observe(this, s-> btnSetLocation.setOnClickListener(this::onClick));
        reportViewModel.getText().observe(this, s-> btnReport.setOnClickListener(this::onClick));
        reportViewModel.getText().observe(this, s-> btnCamera.setOnClickListener(this::onClick));
        return root;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "Location permission granted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(main, "Location permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case REQUEST_PERMISSION_STORAGE: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    File photo = null;
                    try {
                        photo =  File.createTempFile(String.valueOf(Calendar.getInstance().getTime()), ".jpg", Environment.getExternalStorageDirectory());
                        uriImage = Uri.fromFile(photo);
                        Log.v("REPORT", "Created: " + photo.getAbsolutePath());
                        Intent intentCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT,uriImage);
                        Log.i("REPORT", "Loading camera..");
                        // Needed for VM use
                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                        StrictMode.setVmPolicy(builder.build());
                        startActivityForResult(intentCamera, REQUEST_TAKE_PHOTO);
                    } catch (IOException e) {
                        Log.d("REPORT", "Could not create temp file:", e);
                    }
                }else{
                    Toast.makeText(main, "Writing to storage permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void onPause(){
        super.onPause();
        mLocationManager.removeUpdates(this);
    }

    public void onResume() {
        main.uncheckNav();
        main.getSupportActionBar().setTitle(R.string.title_report);
        super.onResume();
    }


    @Override
    public void onLocationChanged(Location location) {
        reportedLocation = location.getLatitude() +","+location.getLongitude();
        txtLocation.setText(reportedLocation);
    }

    private void onClick(View v){
        if(v.getId() == R.id.btnSetLocation){
            Log.i("REPORT", "Moving to location fragment...");
            FragmentTransaction ft = this.getFragmentManager().beginTransaction();
            LocationFragment locationFragment = new LocationFragment();
            locationFragment.setTargetFragment(ReportFragment.this, REQUEST_PASS_DATA);
            ft.addToBackStack(locationFragment.getClass().getName());
            ft.add(R.id.nav_host_fragment, locationFragment, "location");
            ft.commit();
        } else if(v.getId() == R.id.btnGetLocation){
            if (checkSelfPermission(main, Manifest.permission.ACCESS_FINE_LOCATION) != PermissionChecker.PERMISSION_GRANTED && checkSelfPermission(main, Manifest.permission.ACCESS_COARSE_LOCATION) != PermissionChecker.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
                return;
            }
            Toast.makeText(main, "Getting your location", Toast.LENGTH_LONG).show();
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 500, this);
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 500, this);
        }else if(v.getId() == R.id.btnCamera){
            if (checkSelfPermission(main, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_STORAGE);
                return;
            }else{
                File photo = null;
                try {
                    photo =  File.createTempFile(String.valueOf(Calendar.getInstance().getTime()), ".jpg", Environment.getExternalStorageDirectory());
                    uriImage = Uri.fromFile(photo);
                    Log.v("REPORT", "Created: " + photo.getAbsolutePath());
                    Intent intentCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                    intentCamera.putExtra(MediaStore.EXTRA_OUTPUT,uriImage);
                    Log.i("REPORT", "Loading camera..");
                    // Needed for VM use
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());
                    startActivityForResult(intentCamera, REQUEST_TAKE_PHOTO);
                } catch (IOException e) {
                    Log.d("REPORT", "Could not create temp file:", e);
                }
            }



        }else if(v.getId() == R.id.btnReport){
            Log.i("REPORT", "Checking values...");
            location = txtLocation.getText().toString();
            people = txtNum.getText().toString();
            if(swSheltered.isChecked()){
                sheltered = "Yes";
            }else{
                sheltered = "No";
            }
            description =  txtDescription.getText().toString();
            if(!location.contains(",")){
                new AlertDialog.Builder(getContext())
                        .setTitle("Location not set")
                        .setMessage("Please specify your location before sending your report")
                        .setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.cancel())
                        .show();
            }
            else if(description.isEmpty() || people.isEmpty()){
                new AlertDialog.Builder(getContext())
                        .setTitle("Empty fields")
                        .setMessage("All fields must be filled in before sending your report.")
                        .setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.cancel())
                        .show();
            }
            else{
                Log.i("REPORT", "All good...");
                Log.i("REPORT", "Sending values to DB...");
                if(MainActivity.sql.insertReport(location, people, sheltered, description, bmImage)){
                    Toast.makeText(getContext(), "Reported successfully", Toast.LENGTH_LONG).show();
                    Log.i("SQL", MainActivity.sql.getLastEntry());
                    if(uriImage != null) {
                        Log.i("REPORT", "Removing captured image from external storage...");
                        new File(uriImage.getPath()).delete();
                    }
                    Log.i("REPORT", "Moving user back to home screen...");
                    main.onBackPressed();
                }
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Log.v("REPORT", "RESULT_OKAY");
            if (requestCode == REQUEST_PASS_DATA) {
                Log.i("REPORT", "REQUEST_PASS_DATA");
                reportedLocation = data.getStringExtra("location");
                txtLocation.setText(reportedLocation);
            }
            if(requestCode == REQUEST_TAKE_PHOTO){
                Log.i("REPORT", "REQUEST_TAKE_PHOTO");
                bmImage = grabImage(); // grabbing captured image stored in temp file
                Log.i("REPORT", "Success grabbing image");
                btnCamera.setEnabled(false);
                btnCamera.setImageResource(R.drawable.tick);
            }
        }

    }

    private Bitmap grabImage() {
        ContentResolver cr = main.getContentResolver();
        try
        {
            return MediaStore.Images.Media.getBitmap(cr, uriImage);
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), "Failed to upload captured image", Toast.LENGTH_SHORT).show();
            Log.d("REPORT", "Failed to load captured image", e);
            return null;
        }
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
}
