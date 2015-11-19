package com.example.nattachai.walkingranger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Admin3 extends AppCompatActivity {
    String rName;
    String rDes;
    String point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin3);
        final EditText rewardName = (EditText) findViewById(R.id.pw1);
        final EditText rewardDescription = (EditText) findViewById(R.id.pw2);
        final EditText pointt = (EditText) findViewById(R.id.pw3);
        final Button B1 = (Button) findViewById(R.id.B1);
        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rName = rewardName.getText().toString();
                rDes = rewardDescription.getText().toString();
                point = pointt.getText().toString();
                new HttpTask().execute();
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Admin1.class));
                finish();

            }
        });
    }

    private final String USER_AGENT = "Mozilla/5.0";

    public String sendPost1() throws Exception {

        String urlParameters = "rewardName="+rName+"&description="+rDes+"&point="+point;
        byte[] postData = urlParameters.getBytes("UTF-8");
        int postDataLength = postData.length;
        URL url;
        HttpURLConnection urlConn;
        DataOutputStream printout;
        DataInputStream input;
        url = new URL("http://203.151.92.179:8080/addreward");
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

    private class HttpTask extends AsyncTask<String, Integer, String> {

        protected String doInBackground(String... params) {
            try {
                sendPost1();
                // postData();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "result";
        }

        protected void onProgressUpdate(Integer... values) {

        }

        protected void onPostExecute(String result) {

        }
    }
}