package com.example.nattachai.walkingranger;
import android.content.DialogInterface;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
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
public class EventFragment extends ListFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    ArrayList<String> listEvent = new ArrayList<>();
    private GoogleApiClient mGoogleApiClient;
    private ArrayList<Event> events = new ArrayList<>();
    private String[] eventName;
    private Event eventSelect = new Event();
    private boolean success;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        new SimpleTask().execute("http://203.151.92.179:8080/eventlist?id=" + MemberStatic.getFbID());

        return super.onCreateView(inflater, container, savedInstanceState);
        
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {


        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        ImageView image = new ImageView(this.getActivity());
       // image.setImageResource(R.drawable.ic_person_black_24dp);
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this.getActivity());
        eventSelect = events.get(position);
        if (eventSelect.isCanDo()) {
            builder.setMessage(events.get(position).getDescription() + "\n\nยืนยันการแลกแต้ม");
            builder.setView(image);
            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    mGoogleApiClient.connect();//ไปคำสั่ง onConnected

                }
            });
            builder.setPositiveButton("Cancle", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //dialog.dismiss();
                }
            });
        }
        else
        {
            builder.setMessage("คุณเคยร่วมกิจกรรมนี้ไปแล้ว");
            builder.setView(image);
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {


                }
            });

        }
        builder.show();
    }

    @Override
    public void onConnected(Bundle bundle) {

        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (lastLocation != null) {

            new SimpleTaskL().execute("http://203.151.92.179:8080/participate-event?id=" + MemberStatic.getFbID() + "&eventcode=" + eventSelect.getEventCode() + "&lat=" + lastLocation.getLatitude() + "&long=" + lastLocation.getLongitude());
            //ส่ง lastLocation.getLatitude() กับ lastLocation.getLongitude() ไปเซิพเวอร์

            mGoogleApiClient.disconnect();
        } else {
            Toast.makeText(getActivity(), "Can not connect Location!" , Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

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
        //Blog blog = gson.fromJson(jsonString, Blog.class);
        //Map map = gson.fromJson(jsonString, Map.class);
        JsonParser parser = new JsonParser();
        JsonArray jArray = parser.parse(jsonString).getAsJsonArray();
        events = new ArrayList<>();

        for (JsonElement obj : jArray){
            Event event = gson.fromJson(obj,Event.class);
            events.add(event);
        }

        int x = events.size();
        eventName = new String[x];
        for(int i = 0; i < x; i++)
        {
            eventName[i] = (i+1) + ".  " + events.get(i).getEventName()+"\n";
            int k=String.valueOf(events.get(i).getPoint()).length();
            for(int j=k;j<30;j++){
                eventName[i]+="  ";
            }
            eventName[i]+=events.get(i).getPoint()+"       ";
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1,
                eventName);
        setListAdapter(adapter);
    }

    private class SimpleTaskL extends AsyncTask<String, Void, String> {

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
            showDataL(jsonString);
        }
    }

    private void showDataL(String jsonString) {

        Gson gson = new Gson();
        success = gson.fromJson(jsonString, Boolean.class);

        if(success)
        {
            Toast.makeText(getActivity(), "+" + eventSelect.getPoint() + " Points" , Toast.LENGTH_SHORT).show();
            new SimpleTask().execute("http://203.151.92.179:8080/eventlist?id=" + MemberStatic.getFbID());
        }
        else
        {
            Toast.makeText(getActivity(), "Wrong location! No point", Toast.LENGTH_SHORT).show();
        }
    }
}