package com.example.nattachai.walkingranger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
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


public class LaunActivity extends AppCompatActivity {
    private static final String USER_AGENT = "Mozilla/";
    CallbackManager callbackManager;
    ProfileTracker profileTracker;
    private TextView userName;
    private Button mainMenu;
    private ProfilePictureView profilePicture;
    private String rub = "00";
    private Member user;
    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                updateUI();
            }

            @Override
            public void onCancel() {
                updateUI();
            }

            @Override
            public void onError(FacebookException e) {
                updateUI();
            }
        });
        setContentView(R.layout.activity_laun);

        userName = (TextView) findViewById(R.id.user_name);
        profilePicture = (ProfilePictureView) findViewById(R.id.profile_picture);
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile profile, Profile profile1) {
                updateUI();
            }
        };

        mainMenu = (Button) findViewById(R.id.main_menu);
        mainMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                profile = Profile.getCurrentProfile();
                MemberStatic.setFbID(profile.getId());
                new SimpleTask().execute("http://203.151.92.179:8080/fblogin?name="+profile.getName()+"&fb-code="+MemberStatic.getFbID());


            }
        });
    }



    private void updateUI() {
        boolean loggedIn = AccessToken.getCurrentAccessToken() != null;
        profile = Profile.getCurrentProfile();
        if (loggedIn && (profile != null)) {
            profilePicture.setProfileId(profile.getId());
            userName.setText(profile.getName());
            mainMenu.setEnabled(true);
        } else {
            profilePicture.setProfileId(null);
            userName.setText(null);
            mainMenu.setEnabled(false);
        }
    }
    @Override
    protected void onDestroy () {
        super.onDestroy();
        profileTracker.stopTracking();
    }
    @Override
    protected void onResume () {
        super.onResume();
        updateUI();
    }
    @Override
    protected  void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
        if (user != null) {

                  /*  Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("ID", profile.getId());
                    startActivity(intent);*/

            startActivity(new Intent(getApplicationContext(), MainActivity.class));

        }
        else {
            Toast.makeText(getApplicationContext(), "พัง ลองใหม่", Toast.LENGTH_SHORT).show();
        }


    }

}