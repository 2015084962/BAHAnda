package com.cornell.diaz;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class map_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    Constant c = new Constant();
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolBar = null;
    Intent i;
    private RadioGroup rg;
    private RadioButton rb;
    private EditText edt, edt2;
    private Spinner spin;
    private GoogleMap map;
    private FusedLocationProviderClient client;
    private Location current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Floating Button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callHotlines();
            }
        });

        //Drawer Layout
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_map);
        navigationView.setNavigationItemSelectedListener(this);

        //Radio Group
        rg = (RadioGroup) findViewById(R.id.rgroup);

        //Text field
        edt = (EditText) findViewById(R.id.diffloc);
        edt2 = (EditText) findViewById(R.id.diffloc2);
        edt.setEnabled(false);
        edt2.setEnabled(false);
        edt.setFocusableInTouchMode(false);
        edt2.setFocusableInTouchMode(false);
        edt.clearFocus();
        edt2.clearFocus();

        //Spinner
        spin = (Spinner) findViewById(R.id.establishments);
        ArrayAdapter<String> list = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.establishments));
        list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(list);

        //Map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        locman = (LocationManager) getSystemService(LOCATION_SERVICE);
//        loclis = (LocationListener) new LocationListener() {
//
//            @Override
//            public void onLocationChanged(Location location) {
//                Log.d("Location", location.toString());
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//
//            }
//        };
//
//        if (Build.VERSION.SDK_INT < 23) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                return;
//            }
//            locman.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, loclis);
//        } else {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                //Ask for Permission
//                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
//
//            } else {
//                //With Permission
//                locman.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, loclis);
//            }
//        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    //Menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Drawer Menu items
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                i = new Intent(map_activity.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;
            case R.id.nav_firstaid:
                i = new Intent(map_activity.this, firstaid_activity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;
            case R.id.nav_safetytips:
                i = new Intent(map_activity.this, safetytips_activity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;
            case R.id.nav_map:
                i = new Intent(map_activity.this, map_activity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;
        }
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void callHotlines() {
        final Dialog hotlines_layout = new Dialog(this);
        hotlines_layout.requestWindowFeature(Window.FEATURE_NO_TITLE);
        hotlines_layout.setContentView(R.layout.hotline_popup);

        Button ok = (Button) hotlines_layout.findViewById(R.id.ok);
        ok.setEnabled(true);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hotlines_layout.cancel();
            }
        });

        hotlines_layout.show();
    }

    //MAP FUNCTIONS
    public void rbclick(View v) {
        rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());

        if (rb.getId() == R.id.diffLocation) {
            edt.setEnabled(true);
            edt2.setEnabled(true);
            edt.setFocusableInTouchMode(true);
            edt2.setFocusableInTouchMode(true);
            edt.requestFocus();
            edt2.requestFocus();
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(14.572538, 121.189629), 8f));
        }else {
            getMyLocation();
            edt.setEnabled(false);
            edt2.setEnabled(false);
            edt.setFocusableInTouchMode(false);
            edt2.setFocusableInTouchMode(true);
            edt.requestFocus();
            edt2.requestFocus();
        }
    }

    //MAP FUNCTIONS!
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        getLocationPermission();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(14.572538, 121.189629), 8f));
    }

    private void getLocationPermission() {
        Log.d(c.LOG_MAPS, "Getting Permission");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //Ask for Permission
                Log.d(c.LOG_MAPS, "Asking Permission");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, c.REQUEST_CODE);
            }
        } else {
            Log.d(c.LOG_MAPS, "Permission Granted.");
            map.setMyLocationEnabled(true);
        }
    }

    private void getMyLocation() {
        Log.d(c.LOG_MAPS, "Getting current location.");
        client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
        Task location = client.getLastLocation();
        location.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()) {
                    Log.d(c.LOG_MAPS, "Found location.");
                    current = (Location) task.getResult();
                    moveCamera(new LatLng(current.getLatitude(), current.getLongitude()), c.DEFAULT_ZOOM);
                } else {
                    Log.d(c.LOG_MAPS, "Error, no location found.");

                }
            }
        });
    }

    private void moveCamera(LatLng latlng, float zoom) {
        Log.d(c.LOG_MAPS, "moveCamera: Moving camera to: " + latlng.latitude +", lng: " + latlng.longitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,zoom));

    }


}
