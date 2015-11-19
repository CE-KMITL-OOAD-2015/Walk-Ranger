package com.example.nattachai.walkingranger;


import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class RewardFragment extends ListFragment  {
    private String[] eventName;
    private boolean success;
    private ArrayList<Reward> rewards = new ArrayList<>();
    private Reward rewardSelect = new Reward();
    private String code;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        new SimpleTask().execute("http://203.151.92.179:8080/rewardlist");


        return super.onCreateView(inflater, container, savedInstanceState);

    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {



        ImageView image = new ImageView(this.getActivity());
 //       image.setImageResource(R.drawable.ic_person_black_24dp);
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this.getActivity());
        rewardSelect = rewards.get(position);
        builder.setMessage(rewardSelect.getDescription() + "\n\nยืนยันการแลกของขวัญ");
        builder.setView(image);
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


                new SimpleTaskR().execute("http://203.151.92.179:8080/redeemreward?id=" + MemberStatic.getFbID() + "&rewardCode=" + rewardSelect.getRewardID());

            }
        });
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dialog.dismiss();
            }
        });
        builder.show();
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
        rewards = new ArrayList<>();

        for (JsonElement obj : jArray){
            Reward reward = gson.fromJson(obj,Reward.class);
            rewards.add(reward);
        }

        int x = rewards.size();
        eventName = new String[x];
        for(int i = 0; i < x; i++)
        {
            eventName[i] = (i+1) + ".  " + rewards.get(i).getRewardName()+"\n";
            int k=String.valueOf(rewards.get(i).getPoint()).length();
            for(int j=k;j<30;j++){
                eventName[i]+="  ";
            }
            eventName[i]+=rewards.get(i).getPoint()+"       ";
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1,
                eventName);
        setListAdapter(adapter);
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



        if(!jsonString.equals(""))
        {

            AlertDialog.Builder dDialog = new AlertDialog.Builder(getContext());
            dDialog.setTitle("Success");
            dDialog.setMessage(jsonString);
            dDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dDialog.show();
            new SimpleTask().execute("http://203.151.92.179:8080/rewardlist");
        }
        else
        {
            Toast.makeText(getActivity(), "Your point is not enough!" , Toast.LENGTH_SHORT).show();
        }
    }

}