package com.example.randonner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListeParcoursActivity extends AppCompatActivity {

    private ArrayList<String> Parcours = TracerParcoursActivity.ParcoursName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_items_2);





        ListAdapter Adapter =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Parcours);

        final ListView listeView1 =(ListView) findViewById(R.id.listView2);

        listeView1.setAdapter(Adapter);

        listeView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;
                String value = (String) listeView1.getItemAtPosition(position);

                Intent intent =new Intent(ListeParcoursActivity.this,ParcoursEnregisterActivity.class);
                intent.putExtra("ParcoursName",value);
                ListeParcoursActivity.this.startActivity(intent);



            }
        });


    }
}