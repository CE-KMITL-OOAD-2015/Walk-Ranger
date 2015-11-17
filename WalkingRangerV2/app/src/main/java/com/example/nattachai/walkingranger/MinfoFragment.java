package com.example.nattachai.walkingranger;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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


public class MinfoFragment extends ListFragment {

    //private static final String USER_AGENT = "Mozilla/5.0";
    //  ArrayList<String> leads = new ArrayList<String>();
    ArrayList<Group> groups = new ArrayList<>();
    private String[] leads;
    private Group group = new Group();
    private Member leader = new Member();

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getActivity(),"test "+(position+1), Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        new SimpleTask().execute("http://203.151.92.179:8080/getgroupprofile?id=" + MemberStatic.getFbID());

        return super.onCreateView(inflater, container, savedInstanceState);
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
        //String[] gData = jsonString.split(",\"memberlist\"");
        group = gson.fromJson(jsonString, Group.class);
        leader = group.getLeader();
        ArrayList<Member> members = group.getMemberlist();
        int x = members.size();
        x += 1;
        leads = new String[x];
        leads[0] = "1.  " + leader.getMemberName();
        for(int i = 0; i < x-1; i++)
        {
            leads[i+1]= (i+2) + ".  " + members.get(i).getMemberName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1,
                leads);
        setListAdapter(adapter);
    }


}
