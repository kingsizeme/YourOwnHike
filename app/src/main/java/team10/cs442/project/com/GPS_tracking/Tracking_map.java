package team10.cs442.project.com.GPS_tracking;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Arrays;
import java.util.jar.Manifest;

import team10.cs442.project.com.R;
import team10.cs442.project.com.Trail_Detail.TrailsItemsListActivity;

public class Tracking_map extends FragmentActivity implements LocationListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    public static FragmentManager fragmentManager;
    private LocationManager locationManager;
    private String provider;
    private int counter[] = {0,0,0,0,0};
    double lati[] = {43.407849,43.429097,43.422284,43.410742,43.373094};
    double longi[] = {-89.751893,-89.671477,-89.736414, -89.711895,-89.723673};
    String trails[] = {"Balanced Rock","Devil’s Doorway","West Bluff","Group Camp Trail","Sauk Point Trail"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track__me);

        setUpMapIfNeeded();
        fragmentManager = getSupportFragmentManager();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.4147, -89.7131), 12.0f));
        Log.v("Map On", "one");
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(43.4147, -89.7131))
                .title("Me")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))).showInfoWindow();

        for(int i = 0;i<lati.length;i++) {
            Log.v("Map On", "two");
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lati[i], longi[i]))
                    .title(trails[i]));
            Log.v("outside : ", "Print : " + i);
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                Toast.makeText(getBaseContext(), arg0.getTitle().toString(), Toast.LENGTH_SHORT).show();
                String[] tmp = {arg0.getTitle().toString()};
                if(!tmp[0].equals("Me")) {
                    int tmp_int = return_pos(trails, tmp[0]);
                    if(tmp_int!=-1) {
                        counter[tmp_int]++;
                        if (counter[tmp_int] == 2) {
                            clear_counter();
                            Intent intent = new Intent(getBaseContext(), TrailsItemsListActivity.class);
                            intent.putExtra("trails_array", tmp);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getBaseContext(), tmp[0], Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                return true;
            }

        });

        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = null;
        if(locationManager!= null){
            if(ContextCompat.checkSelfPermission(getBaseContext(),android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                location = locationManager.getLastKnownLocation(provider);
            }
        }

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            //latituteField.setText("Location not available");
            //longitudeField.setText("Location not avalable");
        }
    }

    public void clear_counter(){
        for(int i = 0; i< 5; i++){
            counter[i]=0;
        }
    }

    public int return_pos(String[] tmp, String target){
        for(int i = 0; i< tmp.length; i++){
            if(tmp[i].equals(target)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onLocationChanged(Location location) {
        int lat = (int) (location.getLatitude());
        int lng = (int) (location.getLongitude());
        curLocation(lat,lng);

        /// Static code but i can use Database Queries.

        //latituteField.setText(String.valueOf(lat));
        //longitudeField.setText(String.valueOf(lng));
    }

    private void curLocation(int lat, int lng) {
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("Trails"));
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(locationManager!= null) {
            if (ContextCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.removeUpdates(this);
            }
        }
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
}
