package com.example.nattachai.walkingranger;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;


import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;

public class UserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean hasGroup;
    private boolean isLearder;
    private TextView fname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Profile profile = Profile.getCurrentProfile();
        profileSet(profile.getName(), profile);
    }

    public void profileSet(String name,Profile profile) {
        fname = (TextView)findViewById(R.id.data_fname);
        fname.setText(name);
        ProfilePictureView profilePicture;
        profilePicture = (ProfilePictureView)findViewById(R.id.profile_picture);
        profilePicture.setProfileId(profile.getId());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            finish();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_userpro) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_group) {
            if(hasGroup) {
                if(isLearder) {
                    Intent i = new Intent(getApplicationContext(), LeagroupActivity.class);
                    startActivity(i);
                }
                else{
                    Intent i = new Intent(getApplicationContext(), GroupActivity.class);
                    startActivity(i);
                }
                finish();
            }
            else {
                Intent i = new Intent(getApplicationContext(), BgroupActivity.class);
                startActivity(i);
                finish();
            }
        } else if (id == R.id.nav_board) {
            Intent i = new Intent(getApplicationContext(),BoardActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_scarvenger) {
            Intent i = new Intent(getApplicationContext(),ScarActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_info) {

        } else if (id == R.id.nav_logout) {
            AlertDialog.Builder dDialog = new AlertDialog.Builder(UserActivity.this);
            dDialog.setIcon(R.drawable.logout_exit);
            dDialog.setTitle("Logout");
            dDialog.setMessage("Are you sure you want to logout?");
            dDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //ส่งไปSV
                    if(true){
                        //ออกระบบ
                    }
                    else {

                    }
                }
            });
            dDialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
