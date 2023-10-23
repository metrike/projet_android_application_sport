package fr.projet.pt.sport_activity.localisation;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import fr.projet.pt.R;

import androidx.fragment.app.FragmentActivity;

import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/* Page to display journey of a user on the map */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private long journeyID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle bundle = getIntent().getExtras();
        journeyID = bundle.getLong("journeyID");

        System.out.println();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("Range")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // draw polyline
        Cursor c = getContentResolver().query(JourneyProviderContract.LOCATION_URI,
                null, JourneyProviderContract.L_JID + " = " + journeyID, null, null);

        PolylineOptions line = new PolylineOptions().clickable(false);
        LatLng firstLoc = null;
        LatLng lastLoc = null;
        try {
            while(c.moveToNext()) {
               @SuppressLint("Range") LatLng loc = new LatLng(c.getDouble(c.getColumnIndex(JourneyProviderContract.L_LATITUDE)),
                        c.getDouble(c.getColumnIndex(JourneyProviderContract.L_LONGITUDE)));
                System.out.println("latitude : "+c.getDouble(c.getColumnIndex(JourneyProviderContract.L_LONGITUDE))+" longtitude : "+c.getDouble(c.getColumnIndex(JourneyProviderContract.L_LATITUDE)));

                if(c.isFirst()) {
                    firstLoc = loc;
                }
                if(c.isLast()) {
                    lastLoc = loc;
                }
                line.add(loc);
            }
        } finally {
            c.close();
        }

        float zoom = 14.0f;
        if(lastLoc != null && firstLoc != null) {
            mMap.addMarker(new MarkerOptions().position(firstLoc).title("Start"));
            mMap.addMarker(new MarkerOptions().position(lastLoc).title("End"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLoc, zoom));
        }
        mMap.addPolyline(line);
    }
}