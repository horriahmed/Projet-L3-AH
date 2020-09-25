package com.example.randonner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class ListParcoursActivity extends UserActivity {

SessionManager sessionManager;
    private ArrayList<Parcours> listParcours;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_parcours);

        listView=(ListView)findViewById(R.id.listParcours);
        sessionManager=new SessionManager(this);
        listParcours=getIntent().getParcelableArrayListExtra("listParcours");
        String[] tmp = new String[listParcours.size()];
        for (int i=0;i<listParcours.size();i++){
            tmp[i]=listParcours.get(i).toString();

        }
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(ListParcoursActivity.this,
                android.R.layout.simple_list_item_1,tmp);
        System.out.println("arrayadapter"+arrayAdapter.getItem(0));
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ListParcoursActivity.this,ShowParcoursInformations.class);
                System.out.println(position);
                System.out.println(listParcours.get(position));
                intent.putExtra("parcours", (Serializable) listParcours.get(position));
                startActivity(intent);

            }
        });

    }



}
