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
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class UserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String hasGroup;
    private String isLearder;
    private TextView fname;
    private Profile profile;
    private Member user = new Member();
    private TextView score, group, status, fid, step;
    private int count1 =0;
    boolean activityRunning;
    private ImageView rs;

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
        View nav_header = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        profile = Profile.getCurrentProfile();
        ((TextView)nav_header.findViewById(R.id.data_ffffffname)).setText(profile.getName());
        ((ProfilePictureView)nav_header.findViewById(R.id.profile_picture)).setProfileId(profile.getId());
        navigationView.addHeaderView(nav_header);


        profile = Profile.getCurrentProfile();

        new SimpleTask().execute("http://203.151.92.179:8080/getuserprofile?id=" + MemberStatic.getFbID());
        profileSet( profile);

        rs = (ImageView)findViewById(R.id.reset);
        reset();

    }


    public void profileSet(Profile profile) {


                ProfilePictureView profilePicture;
                profilePicture = (ProfilePictureView) findViewById(R.id.profile_picture);
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

    private void reset() {
        rs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dDialog = new AlertDialog.Builder(UserActivity.this);
                dDialog.setTitle("Reset");
                dDialog.setMessage("Are you sure you want to Reset?");
                dDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //ส่งไปSV
                        if (true) {
                            //ออกระบบ
                        } else {

                        }
                    }
                });
                dDialog.show();
            }
        });
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fname = (TextView) findViewById(R.id.data_fname);

        if (id == R.id.nav_home) {
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_userpro) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_group) {
            if(hasGroup != null) {
                if(isLearder.equals("Leader")) {
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
                        android.os.Process.killProcess(android.os.Process.myPid());
                }
            });
            dDialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class SimpleTask extends AsyncTask<String, Void, String> {

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
            showData(jsonString);
        }
    }

    private void showData(String jsonString) {

        Gson gson = new Gson();
        user = gson.fromJson(jsonString, Member.class);
        fname = (TextView) findViewById(R.id.data_fname);
        fname.setText(user.getMemberName());
        score = (TextView)findViewById(R.id.data_fscore);
        score.setText(String.valueOf(user.getScavengerPoint()));
        group = (TextView)findViewById(R.id.data_fgroup);
        group.setText(user.getGroupName());
        status = (TextView)findViewById(R.id.data_fstatus);
        status.setText(user.getRole());
        fid = (TextView)findViewById(R.id.data_fid);
        fid.setText(user.getFbCode());
        step = (TextView)findViewById(R.id.data_fsstep);
        step.setText(String.valueOf(user.getStepCount()));
        hasGroup = user.getGroupName();
        isLearder = user.getRole();




    }
}
