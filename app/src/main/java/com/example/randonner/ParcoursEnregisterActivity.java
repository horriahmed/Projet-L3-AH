package com.example.randonner;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mapbox.api.directions.v5.DirectionsCriteria.PROFILE_WALKING;

public class ParcoursEnregisterActivity extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener {


    private PermissionsManager permissionsManager;
    public MapboxMap mapboxMap;
    private MapView mapView;
    private ImageView localization;
    private ImageView layers;
    private Location location;
    private Point originPoint;
    private Point destinationPoint;
    private Point pointP;
    int i;
    int j=0;
    private DirectionsRoute currentRoute;
    private DirectionsRoute parcours;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;
    private ArrayList<DirectionsRoute> listeRoute;
    private ArrayList<String>parcoursName=TracerParcoursActivity.ParcoursName;
    private ArrayList<Parcours> listeParcours=TracerParcoursActivity.listeParcours;
    private Button startButton;
    private ArrayList<PointP> listePoints;
    private ArrayList<String>MarkersName=TracerParcoursActivity.MarkersName;
    private Parcours parcours1=TracerParcoursActivity.parcours1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// Mapbox access token is configured here. This needs to be called either in your application
// object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.accesToken));

// This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_parcours_enregistrer);
        localization = (ImageView) findViewById(R.id.localization);
        layers = (ImageView) findViewById(R.id.layers);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        listeRoute = new ArrayList<>();
        startButton=findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean simulateRoute = true;
                NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                        .directionsProfile(PROFILE_WALKING)
                        .directionsRoute(listeRoute.get(j))
                        .shouldSimulateRoute(simulateRoute)
                        .build();
                // Call this method with Context from within an Activity
                NavigationLauncher.startNavigation(ParcoursEnregisterActivity.this, options);

                j++;



            }
        });



        localization.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressWarnings({"MissingPermission"})
            public void onClick(View v) {
                location = mapboxMap.getLocationComponent().getLastKnownLocation();
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
        String layerType = Style.MAPBOX_STREETS;

        String tmp = getIntent().getStringExtra("layerType");

        if (tmp != null) layerType = layerTypeConvert(tmp.toString());
        ParcoursEnregisterActivity.this.mapboxMap = mapboxMap;

        mapboxMap.setStyle(layerType, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocationComponent(style);
            }
        });
        if(listeParcours.size()!=0) {

            String NomParcoursSelectioner = getIntent().getStringExtra("ParcoursName");

            for (Parcours parcours : listeParcours) {
                if (parcours.getName().equals(NomParcoursSelectioner)) {
                    listePoints = parcours.getListePoints();
                    break;
                }
            }

            Toast.makeText(getApplicationContext(), NomParcoursSelectioner, Toast.LENGTH_LONG).show();

            originPoint = Point.fromLngLat(listePoints.get(0).getPoint().getLongitude(), listePoints.get(0).getPoint().getLatitude());

            for (i = 1; i < listePoints.size(); i++) {
                destinationPoint = Point.fromLngLat(listePoints.get(i).getPoint().getLongitude(), listePoints.get(i).getPoint().getLatitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(listePoints.get(i).getPoint());
                markerOptions.setTitle(listePoints.get(i).getName());
                mapboxMap.addMarker(markerOptions);
                getRoute(originPoint, destinationPoint);
                originPoint = destinationPoint;
            }

        }

        if(MarkersName.size()!=0) {

            String nomMarker = getIntent().getStringExtra("MarkerName");
            Toast.makeText(getApplicationContext(), nomMarker, Toast.LENGTH_LONG).show();


            for (PointP point : parcours1.getListePoints()) {
                if (point.getName().equals(nomMarker)) {

                    //Point p = Point.fromLngLat(point.getPoint().getLongitude(), point.getPoint().getLatitude());
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(point.getPoint());
                    markerOptions.setTitle(point.getName());
                    mapboxMap.addMarker(markerOptions);

                    break;
                }


            }
        }

          /*originPoint = Point.fromLngLat(points.get(0).getLongitude(), points.get(0).getLatitude());

                for (i = 1; i < points.size(); i++) {
                    destinationPoint = Point.fromLngLat(points.get(i).getLongitude(), points.get(i).getLatitude());
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(points.get(i));
                    markerOptions.setTitle("Marker");
                    mapboxMap.addMarker(markerOptions);
                    getRoute(originPoint, destinationPoint);
                    originPoint = destinationPoint;
                }
                */





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


    private void getRoute(Point origin, Point destination) {
        NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())
                .profile(PROFILE_WALKING)
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        // You can get the generic HTTP info about the response
                        Log.d(TAG, "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No routes found");
                            return;
                        }

                        currentRoute = response.body().routes().get(0);

                        listeRoute.add(currentRoute);

                        // Draw the route on the map
                        if (navigationMapRoute == null) {
                            //navigationMapRoute.removeRoute();
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);

                        }
                        /*else {

                        }*/
                        navigationMapRoute.addRoutes(listeRoute);


                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });
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

    public void BackToMain(View view) {
    }



    protected OnFailureListener onFailureListener(){

        return new OnFailureListener() {

            @Override

            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getApplicationContext(), getString(R.string.error_unknown_error), Toast.LENGTH_LONG).show();

            }

        };

    }



}




