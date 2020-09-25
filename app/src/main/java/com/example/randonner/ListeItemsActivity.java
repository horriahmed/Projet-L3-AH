package com.example.randonner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListeItemsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_items);

        String[] listeitems={"parcours","Points Interets"};
        String [] Parcours ={"parcours 1","parcours 2","parcours 3"};
        String [] Markers={"point interet 1","point interet 2","point interet 3"};

        ListAdapter Adapter =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listeitems);

        final ListView listeView1 =(ListView) findViewById(R.id.listView1);

        listeView1.setAdapter(Adapter);

        listeView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition=position;
                String value=(String)listeView1.getItemAtPosition(position);

                switch (value){
                    case "parcours":
                        Intent ListeParcours =new Intent(ListeItemsActivity.this,ListeParcoursActivity.class);
                        ListeItemsActivity.this.startActivity(ListeParcours);
                        break;

                    case "Points Interets":
                        Intent ListeMarkers =new Intent(ListeItemsActivity.this,ListeMarkersActivity.class);
                        ListeItemsActivity.this.startActivity(ListeMarkers);
                        break;

                }
            }
        });
    }



}
