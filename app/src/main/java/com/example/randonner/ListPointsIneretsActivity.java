package com.example.randonner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class ListPointsIneretsActivity extends UserActivity {
    private ListView listView;
    private ArrayList<? extends PointInteret> listPointInteret;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_points_inerets);


        listView=(ListView)findViewById(R.id.listPointInterets);
        sessionManager=new SessionManager(this);
        listPointInteret= getIntent().getParcelableArrayListExtra("listPointInteret");
        String[] tmp = new String[listPointInteret.size()];
        for (int i=0;i<listPointInteret.size();i++){
            tmp[i]=listPointInteret.get(i).toString();

        }
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(ListPointsIneretsActivity.this,
                android.R.layout.simple_list_item_1,tmp);
        System.out.println("arrayadapter"+arrayAdapter.getItem(0));
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ListPointsIneretsActivity.this,ShowPointInteretInformation.class);
                System.out.println(position);
                System.out.println(listPointInteret.get(position));
                intent.putExtra("PointInteret", (Serializable) listPointInteret.get(position));
                startActivity(intent);

            }
        });

    }
    }

