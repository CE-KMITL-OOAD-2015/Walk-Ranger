package com.example.nattachai.walkingranger;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class BgroupActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String USER_AGENT = "Mozilla/5.0";
    Button jG;
    Button cG;
    String code;
    String nameG;
    String rub;
    private Group group;
    private Profile profile;

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
        View nav_header = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        profile = Profile.getCurrentProfile();
        ((TextView)nav_header.findViewById(R.id.data_ffffffname)).setText(profile.getName());
        ((ProfilePictureView)nav_header.findViewById(R.id.profile_picture)).setProfileId(profile.getId());
        navigationView.addHeaderView(nav_header);

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
                        new SimpleTaskJ().execute("http://203.151.92.179:8080/joingroup?id=" + MemberStatic.getFbID() + "&groupCode=" + code);

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
                        new SimpleTaskC().execute("http://203.151.92.179:8080/creategroup?id=" + MemberStatic.getFbID() + "&groupName=" + nameG);

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
    private class SimpleTaskJ extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // Create Show ProgressBar
        }

        protected String doInBackground(String... urls) {
            String result = "";
            IOException ee;
            Exception ex;
            try {

                HttpGet httpGet = new HttpGet(urls[0]);
                HttpClient client = new DefaultHttpClient();

                HttpResponse response = client.execute(httpGet);

                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    InputStream inputStream = response.getEntity().getContent();
                    BufferedReader reader = new BufferedReader
                            (new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result += line;
                    }
                }

            } catch (ClientProtocolException e) {

            } catch (IOException e) {
                ee=e;
            }catch (Exception e){
                ex=e;
            }
            return result;
        }

        protected void onPostExecute(String jsonString) {
            // Dismiss ProgressBar
            showDataJ(jsonString);
        }
    }

    private void showDataJ(String jsonString) {

        Gson gson = new Gson();
        group = gson.fromJson(jsonString, Group.class);
        if (group != null) {//ถ้าcodeถูก
            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
            //SV สร้างกลุ่ม
            Intent i = new Intent(getApplicationContext(), GroupActivity.class);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "codeผิดๆ", Toast.LENGTH_LONG).show();
        }

    }
    private class SimpleTaskC extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // Create Show ProgressBar
        }

        protected String doInBackground(String... urls) {
            String result = "";
            IOException ee;
            Exception ex;
            try {

                HttpGet httpGet = new HttpGet(urls[0]);
                HttpClient client = new DefaultHttpClient();

                HttpResponse response = client.execute(httpGet);

                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    InputStream inputStream = response.getEntity().getContent();
                    BufferedReader reader = new BufferedReader
                            (new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result += line;
                    }
                }

            } catch (ClientProtocolException e) {

            } catch (IOException e) {
                ee=e;
            }catch (Exception e){
                ex=e;
            }
            return result;
        }

        protected void onPostExecute(String jsonString) {
            // Dismiss ProgressBar
            showDataC(jsonString);
        }
    }

    private void showDataC(String jsonString) {

        Gson gson = new Gson();
        group = gson.fromJson(jsonString, Group.class);
        if (group != null)//ถ้าชื่อไม่ซ้ำ
        {
            Toast.makeText(getApplicationContext(), "Success your code is "+ group.getGroupCode(), Toast.LENGTH_SHORT).show();
            //SV สร้างกลุ่ม
            Intent i = new Intent(getApplicationContext(), LeagroupActivity.class);
            startActivity(i);
            finish();
        }

        else

        {
            Toast.makeText(getApplicationContext(), "ชื่อซ้ำนะจ้ะ", Toast.LENGTH_SHORT).show();
        }

    }
}
