package com.example.nattachai.walkingranger;


import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RewardFragment extends ListFragment  {
    ArrayList<String> listEvent = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        listEvent.add("rrrr1");
        listEvent.add("rrrr2");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(), android.R.layout.simple_list_item_1,
                listEvent);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);

    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {



        ImageView image = new ImageView(this.getActivity());
        image.setImageResource(R.drawable.ic_person_black_24dp);
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this.getActivity());
        builder.setMessage("รับขนมจีบซาลาเปาเพิ่มมั้ยครับ?\n\n\n");
        builder.setView(image);
        builder.setPositiveButton("รับ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {




            }
        });
        builder.setNegativeButton("ไม่รับ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dialog.dismiss();
            }
        });
        builder.show();
    }

}