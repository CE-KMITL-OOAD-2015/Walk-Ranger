package com.example.nattachai.walkingranger;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.ProfileTracker;
import com.facebook.login.widget.ProfilePictureView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    ProfileTracker profileTracker;
//    private User user = new User(null,null);
    private TextView userName;
    private ProfilePictureView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
//        LoginManager.getInstance().logInWithPublishPermissions(
//                this,
//                Arrays.asList("publish_actions"));
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
        setContentView(R.layout.activity_main);
        userName = (TextView) findViewById(R.id.user_name);
        profilePicture = (ProfilePictureView) findViewById(R.id.profile_picture);
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile profile, Profile profile1) {
                updateUI();
            }
        };
    }

    private void updateUI() {
        boolean loggedIn = AccessToken.getCurrentAccessToken() != null;
        Profile profile = Profile.getCurrentProfile();
        if (loggedIn && (profile != null)) {
//            user.setUserCode(profile.getId());
//            user.setUserName(profile.getName());
            profilePicture.setProfileId(profile.getId());
            userName.setText(profile.getName());
//            Intent i = new Intent(this,Main_menu.class);
//            startActivity(i);
        } else {
            profilePicture.setProfileId(null);
            userName.setText(null);
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
}

