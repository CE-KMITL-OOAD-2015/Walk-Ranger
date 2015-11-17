package com.example.nattachai.walkingranger;

import com.facebook.Profile;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClientGetPost {

    private static final String USER_AGENT = "Mozilla/5.0";

    private String sendPost() throws Exception {

        Profile profile = Profile.getCurrentProfile();
        String urlParameters  = "name="+profile.getName()+"&FBID="+profile.getId();
        byte[] postData = urlParameters.getBytes("UTF-8");
        int postDataLength = postData.length;
        URL url;
        HttpURLConnection urlConn;
        DataOutputStream printout;
        DataInputStream input;
        url = new URL("http://203.151.92.179:8080/fblogin");
        urlConn = (HttpURLConnection) url.openConnection();
        urlConn.setDoInput(true);
        urlConn.setDoOutput(true);
        urlConn.setUseCaches(false);
        urlConn.setInstanceFollowRedirects(false);
        urlConn.setRequestMethod("POST");
        urlConn.setRequestProperty("charset", "utf-8");
        urlConn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
        urlConn.setRequestProperty("User-Agent", USER_AGENT);
        urlConn.connect();
        DataOutputStream wr = new DataOutputStream(urlConn.getOutputStream());
        wr.write(postData);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(urlConn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        urlConn.disconnect();
        return response.toString();

    }
}

