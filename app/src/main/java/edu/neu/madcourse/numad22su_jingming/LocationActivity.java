package edu.neu.madcourse.numad22su_jingming;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.Priority;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;


public class LocationActivity extends AppCompatActivity {
    private static final String TAG = "LocationActivity";
    private static final int REQUEST_CHECK_SETTINGS = 100;
    private static final long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private static final long FASTEST_INTERVAL = 2 * 1000; /* 2 sec */
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private int priority;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private TextView latitudeTV;
    private TextView longitudeTV;
    private TextView totalDistanceTV;
    private TextView locationAccuracyTV;
    private Double lastLocationLatitude;
    private Double lastLocationLongitude;
    private float totalDistance;
    private boolean requestingLocationUpdates;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        latitudeTV = findViewById(R.id.latitudeTV);
        longitudeTV = findViewById(R.id.longitudeTV);
        totalDistanceTV = findViewById(R.id.totalDistanceTV);
        locationAccuracyTV = findViewById(R.id.locationAccuracyTV);

        lastLocationLatitude = null;
        lastLocationLongitude = null;
        totalDistance = 0;

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                if (lastLocationLatitude != null && location != null) {
                    float[] results = new float[3];
                    Location.distanceBetween(lastLocationLatitude,
                            lastLocationLongitude, location.getLatitude(), location.getLongitude(), results);
                    totalDistance += results[0];
                    Log.v(String.valueOf(results[0]), "results");
                    Log.v(String.valueOf(totalDistance), "totalDistance");
                    totalDistanceTV.setText(String.format("Distance: %s", totalDistance));
                }
                if (location != null) {
                    lastLocationLatitude = location.getLatitude();
                    lastLocationLongitude = location.getLongitude();
                    Log.v(String.valueOf(lastLocationLatitude), "lastLocationLatitude");
                    Log.v(String.valueOf(lastLocationLongitude), "lastLocationLongitude");
                    latitudeTV.setText(String.format("Latitude: %s", lastLocationLatitude));
                    longitudeTV.setText(String.format("Longitude: %s", lastLocationLongitude));
                    locationAccuracyTV.setText(String.format("Accuracy: %s", location.getAccuracy()));
                }
            }
        };

        priority = Priority.PRIORITY_PASSIVE;

        Button highAccuracyBtN = findViewById(R.id.highAccuracyBtN);
        highAccuracyBtN.setOnClickListener(v -> {
            Log.v(TAG, "highAccuracy Pressed");
            priority = Priority.PRIORITY_HIGH_ACCURACY;
            startLocationUpdates();
        });

        Button balancedPowerAccuracyBtN = findViewById(R.id.balancedPowerAccuracyBtN);
        balancedPowerAccuracyBtN.setOnClickListener(v -> {
            Log.v(TAG, "balanced Pressed");
            priority = Priority.PRIORITY_BALANCED_POWER_ACCURACY;
            startLocationUpdates();
        });

        Button lowPowerBtN = findViewById(R.id.lowPowerBtN);
        lowPowerBtN.setOnClickListener(v -> {
            Log.v(TAG, "low Power Pressed");
            priority = Priority.PRIORITY_LOW_POWER;
            startLocationUpdates();
        });

        Button noPowerBtN = findViewById(R.id.noPowerBtN);
        noPowerBtN.setOnClickListener(v -> {
            Log.v(TAG, "no power Pressed");
            priority = Priority.PRIORITY_PASSIVE;
            startLocationUpdates();
        });

        Button resetDistanceBtN = findViewById(R.id.resetDistanceBtN);
        resetDistanceBtN.setOnClickListener(v -> {
            totalDistance = 0;
            totalDistanceTV.setText(String.format("Distance: %s", totalDistance));
        });
    }

    private void startLocationUpdates() {
        requestingLocationUpdates = true;
        // create the location request to start receiving updates
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationRequest.setPriority(priority);

        // get the current location settings of a user's device
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        // check whether the current location settings are satisfied
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        Log.w(TAG, "ZS: task scheduled.");

        task.addOnSuccessListener(this, locationSettingsResponse -> {
            // All location settings are satisfied. The client can initialize
            // location requests here.
            Log.w(TAG, "ZS: task success.");
            if (checkLocationPermission()) {
                Log.w(TAG, "ZS: checkLocationPermission passed.");
                fusedLocationClient.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        Looper.getMainLooper());
            } else {
                Log.w(TAG, "ZS: checkLocationPermission failed.");
                askLocationPermission();
            }
        });

        task.addOnFailureListener(this, e -> {
            if (e instanceof ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    requestingLocationUpdates = false;
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(LocationActivity.this,
                            REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException sendEx) {
                    // Ignore the error.
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == RESULT_OK) {
                Log.w(TAG, "ZS: device location enabled.");
                startLocationUpdates();
            }
        }
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        totalDistanceTV.setText(String.format("Distance: %s", totalDistance));
        Log.w(TAG, "ZS: onResume!");
        if (requestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putFloat("total_distance", totalDistance);
        outState.putDouble("last_distance_latitude", lastLocationLatitude);
        outState.putDouble("last_distance_longitude", lastLocationLongitude);
        outState.putInt("priority", priority);
        outState.putBoolean("requestingLocationUpdates", requestingLocationUpdates);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        totalDistance = savedInstanceState.getFloat("total_distance");
        lastLocationLatitude = savedInstanceState.getDouble("last_distance_latitude");
        lastLocationLongitude = savedInstanceState.getDouble("last_distance_longitude");
        priority = savedInstanceState.getInt("priority");
        requestingLocationUpdates = savedInstanceState.getBoolean("requestingLocationUpdates");
    }

    public boolean checkLocationPermission() {
        boolean isPermitted = false;
        if (ContextCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(LocationActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
            isPermitted = true;
        }
        return isPermitted;
    }

    public void askLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Log.w(TAG, "showing request permission rationales.");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("To show your current location, requires your permission to access device's location");
            builder.setPositiveButton("OK", (dialog, which) -> {
                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(LocationActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            Log.w(TAG, "ZS: directly requesting permissions.");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.w(TAG, "ZS: onRequestPermissionResult.");

        // If request is cancelled, the result arrays are empty.
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted. Continue the action or workflow
                // in your app.
                Log.w(TAG, "ZS: fine granted.");
                startLocationUpdates();
            } else if (grantResults.length > 1 &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.w(TAG, "ZS: coarse granted.");
                priority = Priority.PRIORITY_BALANCED_POWER_ACCURACY;
                startLocationUpdates();
            } else {
                // Explain to the user that the feature is unavailable because
                // the features requires a permission that the user has denied.
                // At the same time, respect the user's decision. Don't link to
                // system settings in an effort to convince the user to change
                // their decision.
                Log.w(TAG, "ZS: showing denied dialog.");
                requestingLocationUpdates = false;
            }
        }
    }
}
