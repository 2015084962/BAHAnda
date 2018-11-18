package com.cornell.diaz;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class map_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolBar = null;
    Intent i;

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

//        setTitle("Home");
//        Home homeFragment = new Home();
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.content_frame, homeFragment, "Home");
//        fragmentTransaction.commit();
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
//        //FragmentManager fragmentManager = getSupportFragmentManager();
//
//        if (id == R.id.nav_safetytips) {
//            // Handle the safetytips
//            setTitle("Safety Tips");
//            safetytips safetytipsFragment = new safetytips();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.content_frame, safetytipsFragment, "Safety Tips");
//            fragmentTransaction.commit();
//        } else if (id == R.id.nav_firstaid) {
//            // Handle the map
//            setTitle("First Aid");
//            firstaid firstaidFragment = new firstaid();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.content_frame, firstaidFragment, "First Aid");
//            fragmentTransaction.commit();
//        } else if (id == R.id.nav_map) {
//            // Handle the map
//            setTitle("Map");
//            map mapFragment = new map();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.content_frame, mapFragment, "Map");
//            fragmentTransaction.commit();
//        } else if (id == R.id.nav_hotlines) {
//            // Handle the hotlines
//            setTitle("Hotlines");
//            hotlines hotlinesFragment = new hotlines();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.content_frame, hotlinesFragment, "Hotlines");
//            fragmentTransaction.commit();
//        } else if (id == R.id.nav_home) {
//            setTitle("Home");
//            Home homeFragment = new Home();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.content_frame, homeFragment, "Home");
//            fragmentTransaction.commit();
//        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void callHotlines() {
        final Dialog hotlines_layout = new Dialog(this);
        hotlines_layout.requestWindowFeature(Window.FEATURE_NO_TITLE);
        hotlines_layout.setContentView(R.layout.hotline_popup);

        Button ok = (Button)hotlines_layout.findViewById(R.id.ok);
        ok.setEnabled(true);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hotlines_layout.cancel();
            }
        });

        hotlines_layout.show();
    }
}
