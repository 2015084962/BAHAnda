package com.cornell.diaz;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.cornell.diaz.models.Constant;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class map_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {//, OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    Constant c = new Constant();
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolBar = null;
    Intent i;
    String selectedItem;
    //int PLACE_PICKER_REQUEST = 1;
    ArrayAdapter<String> list;
    Intent googleMaps;
    double lat, lng;

    private RadioGroup rg;
    private RadioButton rb;
    private EditText edt;
    private Spinner spin;
//    private GoogleMap map;
    private FusedLocationProviderClient client;
    private Location current_location;
//    private GoogleApiClient mGoogleApiClient;
//    private GeoDataClient mGeoDataClients;
//    private PlaceDetectionClient mPlaceDetectionClients;
//    private PlaceAutoCompleteAdapter mPlaceAutoCompleteAdapters;
//    private PlaceInfo mPlace;
//    Object dataTransfer[];
//    GetNearbyPlaces getNearbyPlacesData;

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
        rb = null;

        //Text field
        edt = (EditText) findViewById(R.id.diffloc);
        edt.setEnabled(false);
        edt.setFocusableInTouchMode(false);
        edt.clearFocus();

        //Spinner
        spin = (Spinner) findViewById(R.id.establishments);
        list = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.establishments));
        list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(list);

//        //Map
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedItem = parent.getItemAtPosition(position).toString().trim();
                if(!selectedItem.equals("Choose Establishment")) {
                    Log.d(c.LOG_MAPS, "Searching nearby " + selectedItem);
                } else {
                    Toast.makeText(map_activity.this, "Please choose among the choices.", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }

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

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        map = googleMap;
//
//        getLocationPermission();
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(14.572538, 121.189629), 8f));
//    }



    ///////////////////////////////////////////////////////USER DECLARED METHODS///////////////////////////////////////////////////////////////

//    public void autocomplete() {
////        mGoogleApiClient = new GoogleApiClient
////                .Builder(this)
////                .addApi(Places.GEO_DATA_API)
////                .enableAutoManage(this,this)
////                .build();
//        // Construct a GeoDataClient.
//        mGeoDataClients = Places.getGeoDataClient(this, null);
//
//        // Construct a PlaceDetectionClient.
//        mPlaceDetectionClients = Places.getPlaceDetectionClient(this, null);
//
//        mPlaceAutoCompleteAdapters = new PlaceAutoCompleteAdapter(this, mGeoDataClients, c.LAT_LNG_BOUNDS, null);
//    }



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

    public void rbclick(View v) {
        rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
        if (rb.getId() == R.id.diffLocation) {
            edt.setEnabled(true);
            edt.setFocusableInTouchMode(true);
            edt.requestFocus();
            //map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(14.572538, 121.189629), 8f));
        }else {
            getMyLocation();
            edt.setEnabled(false);
            edt.setFocusableInTouchMode(false);
            edt.requestFocus();
        }
    }

    public void openGoogleMaps(View v) {
        googleMaps = new Intent(Intent.ACTION_VIEW);
        if (spin != null && spin.getSelectedItem().equals("Choose Establishment")) {
            Toast.makeText(this, "Please select the Location and Establishment.", Toast.LENGTH_SHORT).show();

        } else {
            if (rb == null) {
                Toast.makeText(this, "Please select the Location and Establishment.", Toast.LENGTH_SHORT).show();
            } else {
                lat = current_location.getLatitude();
                lng = current_location.getLongitude();
                if (rb.getId() == R.id.myLocation && rb.isChecked()) {
                    googleMaps.setData(Uri.parse("geo:" + lat + "," + lng + "?q=" + selectedItem));
                    startActivity(googleMaps);
                } else if (rb.getId() == R.id.diffLocation && rb.isChecked()) {
                    Toast.makeText(this, "Different Location", Toast.LENGTH_SHORT).show();
//              googleMaps.setData(Uri.parse("geo:"+lat+","+lng+"?q="+ selectedItem));
//              startActivity(googleMaps);
                }
            }
        }
    }

    public void getMyLocation() {
        Log.d(c.LOG_MAPS, "Getting current location.");
        client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task location = client.getLastLocation();
        location.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()) {
                    Log.d(c.LOG_MAPS, "Found current location.");
                    current_location = (Location) task.getResult();

                } else {
                    Log.d(c.LOG_MAPS, "Error, no location found.");

                }
            }


        });
    }

//    //MAP FUNCTIONS!
//    private void getLocationPermission() {
//        Log.d(c.LOG_MAPS, "Getting Permission");
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                //Ask for Permission
//                Log.d(c.LOG_MAPS, "Asking Permission");
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, c.REQUEST_CODE);
//            }
//        } else {
//            Log.d(c.LOG_MAPS, "Permission Granted.");
//            map.setMyLocationEnabled(true);
//        }
//    }
//
//    private void getMyLocation() {
//        Log.d(c.LOG_MAPS, "Getting current location.");
//
//
//        client = LocationServices.getFusedLocationProviderClient(this);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        map.setMyLocationEnabled(true);
//        Task location = client.getLastLocation();
//        location.addOnCompleteListener(new OnCompleteListener() {
//            @Override
//            public void onComplete(@NonNull Task task) {
//                if(task.isSuccessful()) {
//                    Log.d(c.LOG_MAPS, "Found current location.");
//                    current_location = (Location) task.getResult();
//                    moveCamera(new LatLng(current_location.getLatitude(), current_location.getLongitude()), c.DEFAULT_ZOOM);
//                    spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                            selectedItem = parent.getItemAtPosition(position).toString().trim();
//                            if(!selectedItem.equals("Choose Establishment")) {
//                                Log.d(c.LOG_MAPS, "Searching nearby " + selectedItem);
//                                Geocoder geocoder = new Geocoder(map_activity.this);
//                                List<Address> list = new ArrayList<>();
//                                try {
//                                        list = geocoder.getFromLocationName(selectedItem, 10);
//                                }catch (IOException e){
//                                    Log.d(c.LOG_MAPS, "Exception occured: "+ e.getMessage());
//                                }
//
//                                if(list.size()>0) {
//                                    Address address = list.get(0);
//                                    Log.d(c.LOG_MAPS, "Location: " + address.toString());
//                                    // Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();
//                                }
//
////                                String url = getUrl(current_location.getLatitude(),current_location.getLongitude(),selectedItem);
////                                Log.d(c.LOG_MAPS, url);
////                                dataTransfer[0] = map;
////                                dataTransfer[1]= url;
////                                getNearbyPlacesData.execute(dataTransfer);
//                            } else {
//                                Toast.makeText(map_activity.this, "Please choose among the choices.", Toast.LENGTH_LONG);
//                            }
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//
//                        }
//                    });
//                } else {
//                    Log.d(c.LOG_MAPS, "Error, no location found.");
//
//                }
//            }
//
//
//        });
//    }
//
//    private void moveCamera(LatLng latlng, float zoom) {
//        Log.d(c.LOG_MAPS, "moveCamera: Moving camera to: " + latlng.latitude +", lng: " + latlng.longitude);
//        map.moveCamera(CameraUpdateFactory.newLatLng(latlng));
//        map.animateCamera(CameraUpdateFactory.zoomTo(c.DEFAULT_ZOOM));
//
//    }

//    //////////////////////////////////////////////////////////////PLACE PICKER FUNCTIONS///////////////////////////////////////////////////
//    private ResultCallback<PlaceBuffer> mResultCallbacks = new ResultCallback<PlaceBuffer>() {
//        @Override
//        public void onResult(@NonNull PlaceBuffer places) {
//            if(!places.getStatus().isSuccess()) {
//                Log.d(c.LOG_MAPS, "Error: Place Query did not complete successfully:" + places.getStatus().toString());
//                places.release();
//                return;
//            }
//            final Place place = places.get(0);
//
//            try {
//                mPlace = new PlaceInfo();
//                mPlace.setName(place.getName().toString());
//                mPlace.setAddres(place.getAddress().toString());
//                mPlace.setAttribution(place.getAttributions().toString());
//                mPlace.setId(place.getId().toString());
//                mPlace.setLatlng(place.getLatLng());
//                mPlace.setRating(place.getRating());
//                mPlace.setPhone(place.getPhoneNumber().toString());
//                mPlace.setWebsiteUri(place.getWebsiteUri());
//
//                Log.d(c.LOG_MAPS, "Place Details:" + mPlace.toString());
//            } catch(NullPointerException e) {
//                Log.d(c.LOG_MAPS, "NullPointerException:" + e.getMessage());
//            }
//            //moveCamera(LatLng());
//        }
//    };
//
//    public void openPlacePicker(){
//        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//        try {
//            startActivityForResult(builder.build(map_activity.this), PLACE_PICKER_REQUEST);
//        } catch (GooglePlayServicesRepairableException e) {
//            Log.d(c.LOG_MAPS, "Error: " + e.getMessage());
//        } catch (GooglePlayServicesNotAvailableException e) {
//            Log.d(c.LOG_MAPS, "Error: " + e.getMessage());
//        }
//    }
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == PLACE_PICKER_REQUEST) {
//            if (resultCode == RESULT_OK) {
//                Place place = PlacePicker.getPlace(this, data);
//
//                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, place.getId());
//                placeResult.setResultCallback(mResultCallbacks);
//            }
//        }
//    }
//
//
//
////    private String getUrl(double latitude, double longitude, String nearbyPlace) {
////        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
////        googlePlaceUrl.append("location=" + latitude + "," + longitude);
////        googlePlaceUrl.append("&radius=" + PROXIMITY_RADIUS);
////        googlePlaceUrl.append("&type=" + nearbyPlace);
////        googlePlaceUrl.append("&sensor=true");
////        googlePlaceUrl.append("&key="+c.key);
////
////        return googlePlaceUrl.toString();
////    }


}
