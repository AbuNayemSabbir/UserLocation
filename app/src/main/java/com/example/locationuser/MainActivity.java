package com.example.locationuser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
  public class MainActivity extends AppCompatActivity {

        FusedLocationProviderClient fusedLocationProviderClient;
        TextView address, city, country,latitude,longitude;
        Button location;
        private final static int REQUEST_CODE = 100;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            address = findViewById(R.id.addressId);
            city = findViewById(R.id.cityId);
            country = findViewById(R.id.countryId);
            longitude = findViewById(R.id.longitude);
            latitude = findViewById(R.id.latitude);
            location = findViewById(R.id.locationBtn);

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

            location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getLastLocation();

                }

                private void getLastLocation() {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        fusedLocationProviderClient.getLastLocation()
                                .addOnSuccessListener(new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location) {
                                        if (location != null) {
                                            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                                            try {
                                                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                                address.setText("Address :" + addresses.get(0).getAddressLine(0));
                                                city.setText("City :" + addresses.get(0).getLocality());
                                                country.setText("Country :" + addresses.get(0).getCountryName());
                                                latitude.setText("Latitude"+addresses.get(0).getLatitude());
                                                longitude.setText("Latitude"+addresses.get(0).getLongitude());

                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }



                                });

                    }
                    else {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]

                                {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
                    }


                }
            });


        }

    }