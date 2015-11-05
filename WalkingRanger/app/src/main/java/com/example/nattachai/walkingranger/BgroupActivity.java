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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;




public class BgroupActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button jG;
    Button cG;
    String code;
    String nameG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bgroup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        jG = (Button) findViewById(R.id.joinG);
        cG = (Button) findViewById(R.id.createG);
        join();
        create();
    }

    private void join(){
        jG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dDialog = new AlertDialog.Builder(BgroupActivity.this);
                dDialog.setIcon(android.R.drawable.ic_menu_edit);
                final EditText input = new EditText(BgroupActivity.this);
                dDialog.setTitle("Join Group :    ");
                dDialog.setMessage("Please input code");
                dDialog.setView(input);
                dDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        code = input.getText().toString();
                        //ส่งไปSV
                        if(true){//ถ้าcodeถูก
                            Toast.makeText(getApplicationContext(),"Success", Toast.LENGTH_LONG).show();
                            //SV สร้างกลุ่ม
                            Intent i = new Intent(getApplicationContext(),GroupActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"codeผิดๆ", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                dDialog.show();
            }
        });
    }

    private void create(){
        cG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dDialog = new AlertDialog.Builder(BgroupActivity.this);
                dDialog.setIcon(android.R.drawable.ic_menu_edit);
                final EditText input = new EditText(BgroupActivity.this);
                dDialog.setTitle("Create Group :    ");
                dDialog.setMessage("Please input group name");
                dDialog.setView(input);
                dDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nameG = input.getText().toString();
                        //ส่งไปSV
                        if(true){//ถ้าชื่อไม่ซ้ำ
                            Toast.makeText(getApplicationContext(),"Success your coode is xxxxxxx", Toast.LENGTH_SHORT).show();
                            //SV สร้างกลุ่ม
                            Intent i = new Intent(getApplicationContext(),LeagroupActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"ชื่อซ้ำนะจ้ะ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dDialog.show();
            }
        });

    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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
            Intent i = new Intent(getApplicationContext(),UserActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_group) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
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
            AlertDialog.Builder dDialog = new AlertDialog.Builder(BgroupActivity.this);
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
