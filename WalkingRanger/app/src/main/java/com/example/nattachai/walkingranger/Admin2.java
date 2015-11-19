package com.example.nattachai.walkingranger;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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

public class Admin2 extends AppCompatActivity {
    ArrayList<Event> events = new ArrayList<>();
    private String[] listevent;
    private Event eSelected = new Event();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin2);
        final Button B = (Button) findViewById(R.id.B);
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SimpleTaskR().execute("http://203.151.92.179:8080/eventlist?id=" + MemberStatic.getFbID());
            }
        });
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
            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), Admin1.class));
            finish();

    }

    private class SimpleTaskR extends AsyncTask<String, Void, String> {

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
            showDataR(jsonString);
        }
    }

    private void showDataR(String jsonString) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonArray jArray = parser.parse(jsonString).getAsJsonArray();
        events = new ArrayList<>();

        for (JsonElement obj : jArray){
            Event event = gson.fromJson(obj,Event.class);
            events.add(event);
        }
        int x = events.size();
        listevent = new String[x];
        for(int i = 0 ; i < x; i++)
            listevent[i] =  (events.get(i)).getEventName();
        eSelected = events.get(0);
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin2.this);
        builder.setTitle("Select Event");
        builder.setSingleChoiceItems(listevent, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                eSelected = events.get(which);
                Toast.makeText(getApplicationContext(), String.valueOf(eSelected.getEventCode()), Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new SimpleTaskK().execute("http://203.151.92.179:8080/delete-event?eventCode="+eSelected.getEventCode());
            }
        });
        builder.setNegativeButton("Cancle", null);
        builder.create();
        builder.show();

    }

}
