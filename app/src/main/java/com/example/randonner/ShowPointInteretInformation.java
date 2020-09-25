package com.example.randonner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.ArrayList;

public class ShowPointInteretInformation extends UserActivity {

    private MapboxMap mapboxMap;
    private MapView mapView;
    private EditText pointNom,pointDescription;
    int id;
    SessionManager sessionManager;
   private PointInteret pointInteret;
   private Button imagesButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoiaW5hbWhhZGFpciIsImEiOiJjanMxMzNrM3Axazl4NDNtMWt3cnlyMGtmIn0.KtCpe48N-HJ3bfhXp-Ywrw");
        setContentView(R.layout.activity_show_point_interet_information);

        sessionManager = new SessionManager(this);
        mapView = findViewById(R.id.mapParcours);
        imagesButton=(Button)findViewById(R.id.buttonImages);
        mapView.onCreate(savedInstanceState);
          pointNom=(EditText)findViewById(R.id.pointNom);
          pointDescription=(EditText)findViewById(R.id.pointDescription);
        pointInteret = (PointInteret) getIntent().getSerializableExtra("PointInteret");
        System.out.println(pointInteret);
        System.out.println("NOM"+pointInteret.getName());

        pointNom.setText(pointInteret.getName());
        pointDescription.setText(pointInteret.getDescription());
        nom.setEnabled(false);
        mapView.getMapAsync(this);
  }



    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    public void getImages(View view){
        Intent intent=new Intent(this,)
    }

}
