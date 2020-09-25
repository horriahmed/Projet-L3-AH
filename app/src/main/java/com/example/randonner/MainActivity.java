package com.example.randonner;

import android.content.Intent;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Use the LocationComponent to easily add a device location "puck" to a Mapbox map.
 */
public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback, PermissionsListener {


    public static DataBaseManager dataBase;
    private PermissionsManager permissionsManager;
    public static MapboxMap mapboxMap;
    private MapView mapView;
    private ImageView localization;
    private ImageView layers,imgSearch;
    private Location location;
    SessionManager sessionManager;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


// Mapbox access token is configured here. This needs to be called either in your application
// object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.accesToken));

// This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);
        localization=(ImageView)findViewById(R.id.localization);
        layers=(ImageView)findViewById(R.id.layers);
        mapView = findViewById(R.id.mapView);
        //imgSearch= findViewById(R.id.imgSearch);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
       // editText=(EditText)findViewById(R.id.edit_search);
        editText.setVisibility(View.INVISIBLE);
        imgSearch.setVisibility(View.INVISIBLE);
        localization.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressWarnings( {"MissingPermission"})
            public void onClick(View v) {
                location= mapboxMap.getLocationComponent().getLastKnownLocation();
                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude())) // Sets the new camera position
                        .zoom(17) // Sets the zoom
                        .bearing(180) // Rotate the camera
                        .tilt(30) // Set the camera tilt
                        .build(); // Creates a CameraPosition from the builder

                mapboxMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(position), 7000);

            }
        });
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        String layerType=Style.MAPBOX_STREETS;

        String tmp=getIntent().getStringExtra("layerType");

        if(tmp !=null)layerType=layerTypeConvert(tmp.toString());
        MainActivity.this.mapboxMap = mapboxMap;

        mapboxMap.setStyle(layerType, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        enableLocationComponent(style);
                    }
                });
    }
    private String layerTypeConvert(String layer){
        String tmp=null;
        switch (layer){
            case"satellite":tmp=Style.SATELLITE;break;
            case"streets":tmp=Style.MAPBOX_STREETS;break;
            case"outdoors":tmp=Style.OUTDOORS;break;
            case"darkLayer":tmp=Style.DARK;break;
            case"lightLayer":tmp=Style.LIGHT;break;
            default:tmp=Style.MAPBOX_STREETS;
        }
        return tmp;
    }
    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
// Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

// Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();

// Activate with options
            locationComponent.activateLocationComponent(this, loadedMapStyle);

// Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

// Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

// Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }


@Override
    @SuppressWarnings( {"MissingPermission"})
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    public void layersClick(View view) {
        Intent intent=new Intent(this,LayersActivity.class);
        startActivity(intent);
    }

    public void goToMain(View view) {
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
    }


    public void goToProfile(View view) {

        Intent intent=new Intent(this, ConnectionActivity.class);
        if(sessionManager.isLoggin()){
            intent=new Intent(this, UserActivity.class);
        }
        startActivity(intent);
    }


    protected OnFailureListener onFailureListener(){

        return new OnFailureListener() {

            @Override

            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getApplicationContext(), getString(R.string.error_unknown_error), Toast.LENGTH_LONG).show();

            }

        };

    }




    public void goToSearch(View view) {

        editText.setVisibility(View.VISIBLE);
        imgSearch.setVisibility(View.VISIBLE);

        localization.setEnabled(false);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH
                        || actionId==EditorInfo.IME_ACTION_DONE
                        || event.getAction()==KeyEvent.ACTION_DOWN
                        || event.getAction()==KeyEvent.KEYCODE_ENTER){
                    //executer la methode de recherche


                    String editSearch=editText.getText().toString();
                    Geocoder geocoder=new Geocoder(MainActivity.this);
                    List<Address> list=new ArrayList<>();
                    try{
                        list=geocoder.getFromLocationName(editSearch,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(list.size()>0){
                        Address address=list.get(0);
                        //probleme ici
                        System.out.println("adresse trouvée :-------------"+address.toString());

                      //  location= mapboxMap.getLocationComponent().getLastKnownLocation();

                        CameraPosition position = new CameraPosition.Builder()
                                .target(new LatLng(address.getLatitude(), address.getLongitude())) // Sets the new camera position
                                .zoom(12) // Sets the zoom
                                .bearing(0) // Rotate the camera
                                .tilt(30) // Set the camera tilt
                                .build(); // Creates a CameraPosition from the builder


                        MarkerOptions markerOption =new MarkerOptions();
                        markerOption.title("Marker");
                        markerOption.position(new LatLng(address.getLatitude(),address.getLongitude()));
                        mapboxMap.addMarker(markerOption);

                        mapboxMap.animateCamera(CameraUpdateFactory
                                .newCameraPosition(position), 7000);

                       /* mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                                new CameraPosition.Builder()
                                        .target(new LatLng( address.getLatitude(),
                                                address.getLongitude()))
                                        .zoom(14)
                                        .build()), 4000);
                         */

                                    localization.setEnabled(true);



                    }




                }

                return false;
            }
        });



    }



}

