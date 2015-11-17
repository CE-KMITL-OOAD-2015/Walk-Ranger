package com.example.nattachai.walkingranger;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class LeagroupActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String[] listmem;

    private Member mSelected = new Member();

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private Group group;
    private TextView gname,gcode,astep,wstep,mstep;
    private ArrayList<Member> members;
    private TextView lname;
    private Profile profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leagroup);
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

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        new SimpleTaskG().execute("http://203.151.92.179:8080/getgroupprofile?id=" + MemberStatic.getFbID());

        //  dG = (Button)findViewById(R.id.disbandG);
        // disband();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.leagroup, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Change) {
            changeLea();
            return true;
        }
        else if (id == R.id.action_Kick) {
            kick();
            return true;
        }
        else if (id == R.id.action_Disband) {
            if(group.getMemberlist().size() == 0)
                disband();
            else
                Toast.makeText(getApplicationContext(), "Alert : Only no member in the group !", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
            AlertDialog.Builder dDialog = new AlertDialog.Builder(LeagroupActivity.this);
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
                    if(true){

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

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0) return new GinfoFragment();
            else return new MinfoFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Info";
                case 1:
                    return "Member";
            }
            return null;
        }
    }

    private void kick() {


        AlertDialog.Builder builder = new AlertDialog.Builder(LeagroupActivity.this);
        builder.setTitle("Select Favorite Team");
        members = group.getMemberlist();
        listmem = new String[members.size()];
        for(int i = 0 ; i < members.size(); i++)
            listmem[i] =  (members.get(i)).getMemberName();
        mSelected = members.get(0);
        builder.setSingleChoiceItems(listmem, 0, new DialogInterface.OnClickListener() {
            // mSelected = members.get(0);
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mSelected = members.get(which);
            }
        });
        builder.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new SimpleTaskK().execute("http://203.151.92.179:8080/kickmember?id=" + MemberStatic.getFbID() + "&memberid=" + mSelected.getFbCode() );
            }
        });

        builder.setNegativeButton("ไม่ชอบซักทีม", null);
        builder.create();
        builder.show();

    }


    private void disband(){
        AlertDialog.Builder dDialog = new AlertDialog.Builder(LeagroupActivity.this);
        dDialog.setIcon(R.drawable.ic_info_black_24dp);
        dDialog.setTitle("Disband Group");
        dDialog.setMessage("Are you sure you want to disband group?");
        dDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new SimpleTaskD().execute("http://203.151.92.179:8080/leavegroup?id=" + MemberStatic.getFbID());

            }
        });
        dDialog.show();
    }

    private void changeLea(){


        AlertDialog.Builder builder = new AlertDialog.Builder(LeagroupActivity.this);
        builder.setTitle("Select Favorite Team");
        members = group.getMemberlist();
        listmem = new String[members.size()];
        for(int i = 0 ; i < members.size(); i++)
            listmem[i] =  (members.get(i)).getMemberName();
        mSelected = members.get(0);
        builder.setSingleChoiceItems(listmem, 0, new DialogInterface.OnClickListener() {
           // mSelected = members.get(0);
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mSelected = members.get(which);
            }
        });
        builder.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new SimpleTaskC().execute("http://203.151.92.179:8080/changeleader?id=" + MemberStatic.getFbID() + "&memberid=" + mSelected.getFbCode() );
            }
        });

        builder.setNegativeButton("ไม่ชอบซักทีม", null);
        builder.create();
        builder.show();
    }
    private class SimpleTaskG extends AsyncTask<String, Void, String> {

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
            showDataG(jsonString);
        }
    }

    private void showDataG(String jsonString) {

        Gson gson = new Gson();
        group = gson.fromJson(jsonString, Group.class);
        gname = (TextView)findViewById(R.id.ggname);
        gname.setText(group.getGroupName());
        gcode = (TextView)findViewById(R.id.ggcode);
        gcode.setText(group.getGroupCode());
        astep = (TextView)findViewById(R.id.gastep);
        astep.setText(String.valueOf(group.getAvgSteps()));
        wstep = (TextView)findViewById(R.id.gwstep);
        wstep.setText(String.valueOf(group.getAvgWeeklySteps()));
        mstep = (TextView)findViewById(R.id.gmstep);
        mstep.setText(String.valueOf(group.getAvgMonthlySteps()));
        lname = (TextView)findViewById(R.id.gmlea);
        lname.setText(group.getLeader().getMemberName());
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
        boolean ldone = gson.fromJson(jsonString, boolean.class);
        if(ldone){
            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), GroupActivity.class));
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(), "Some problems has just occurred. Please try again later . . .", Toast.LENGTH_SHORT).show();
        }

    }
    private class SimpleTaskK extends AsyncTask<String, Void, String> {

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
            showDataK(jsonString);
        }
    }

    private void showDataK(String jsonString) {

        Gson gson = new Gson();
        boolean ldone = gson.fromJson(jsonString, boolean.class);
        if(ldone){
            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), LeagroupActivity.class));
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(), "Some problems has just occurred. Please try again later . . .", Toast.LENGTH_SHORT).show();
        }
    }
    private class SimpleTaskD extends AsyncTask<String, Void, String> {

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
            showDataD(jsonString);
        }
    }

    private void showDataD(String jsonString) {

        Gson gson = new Gson();
        boolean ldone = gson.fromJson(jsonString, boolean.class);
        if(ldone){
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(), "Some problems has just occurred. Please try again later . . .", Toast.LENGTH_SHORT).show();
        }

    }



}





