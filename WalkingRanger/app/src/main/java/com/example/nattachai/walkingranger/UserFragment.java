package com.example.nattachai.walkingranger;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {


    private View v;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_user, container, false);
        profileSet("Neko");
        return v;
    }

    public void profileSet(String name) {
        TextView fname = (TextView)v.findViewById(R.id.data_fname);

        // TextView fscore = (TextView)findViewById(R.id.data_fscore);
        // TextView fgroup = (TextView)findViewById(R.id.data_fgroup);
        // TextView fstatus = (TextView)findViewById(R.id.data_fstatus);
        // TextView fid= (TextView)findViewById(R.id.data_fid);

        fname.setText(name);

    }


}
