package com.example.randonner;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
import com.mapbox.mapboxsdk.annotations.Marker;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mapbox.api.directions.v5.DirectionsCriteria.PROFILE_WALKING;


/**
 * Use the LocationComponent to easily add a device location "puck" to a Mapbox map.
 */
public class TracerParcoursActivity extends AppCompatActivity implements
        OnMapReadyCallback, PermissionsListener ,MapboxMap.OnMapClickListener ,MapboxMap.OnMarkerClickListener
{

    private PermissionsManager permissionsManager;
    public MapboxMap mapboxMap;
    private MapView mapView;
    private ImageView localization;
    private ImageView layers;
    private Location location;
    private DirectionsRoute currentRoute;
    private DirectionsRoute parcours;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;
    private Button LunchButton;
    private ArrayList<PointInteret> listesPointInterets;
    private ArrayList<DirectionsRoute> listeRoute;
    public static Parcours parcours1;
    private  Point originPoint;
    private Point destinationPoint;
    boolean premeierFois =true;
    private Point pointP;
    private TracerParcoursActivity activity;
    private int i=0;
    private ImageView dropMarkers;
    private ChangeTitelPupUp changeTitlePopup;
    private ImageView addRoute;
    private ImageView menu;
    private ArrayList<Point> listePoints;
    public static ArrayList<String> ParcoursName;
    public PointP p;
    public static ArrayList<Parcours> listeParcours;
    private Button startButton;
    private boolean localoiationB=true;
    public static ArrayList<String> MarkersName;
    public static ArrayList<LatLng> points;
    ArrayList<Point> waypoints=new ArrayList<>();



    private int j=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// Mapbox access token is configured here. This needs to be called either in your application
// object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.accesToken));
        this.activity=this;

// This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_tracer_parcours);
        localization=(ImageView)findViewById(R.id.localization);
        layers=(ImageView)findViewById(R.id.layers);
        dropMarkers=(ImageView)findViewById(R.id.dropMarkers);
        addRoute=(ImageView)findViewById(R.id.addRoute);
        menu=(ImageView)findViewById(R.id.Menu);
        LunchButton = findViewById(R.id.lunchButton);
        startButton=findViewById(R.id.startButton);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        listesPointInterets=new ArrayList<>();
        listeRoute = new ArrayList<>();
        listeParcours =new ArrayList<>();
        parcours1= new Parcours("Route 1");
        listePoints=new ArrayList<>();
        points=new ArrayList<>();
        ParcoursName=new ArrayList<>();
        MarkersName=new ArrayList<>();


        addRoute.setVisibility(View.INVISIBLE);
        dropMarkers.setVisibility(View.INVISIBLE);

        LunchButton.setVisibility(View.INVISIBLE);
        LunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean simulateRoute = true;
                NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                        .directionsProfile(PROFILE_WALKING)
                        .directionsRoute(currentRoute)
                        .shouldSimulateRoute(simulateRoute)
                        .build();
                // Call this method with Context from within an Activity
                NavigationLauncher.startNavigation(TracerParcoursActivity.this, options);





            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TracerParcour(listePoints);
                LunchButton.setVisibility(View.VISIBLE);
                startButton.setVisibility(View.INVISIBLE);
            }
        });

        addRoute.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                changeTitlePopup =new ChangeTitelPupUp(activity);
                changeTitlePopup.setTitle("Route Name");
                changeTitlePopup.getEnregitrer().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Parcours enregitré",Toast.LENGTH_LONG).show();
                        parcours1.setName(changeTitlePopup.getEditText().getText().toString());
                        listeParcours.add(parcours1);
                        changeTitlePopup.dismiss();
                        ParcoursName.add(parcours1.getName());
                    }
                });
                changeTitlePopup.build();
            }
        });



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


        dropMarkers.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mapboxMap.clear();
                if(navigationMapRoute!=null) {
                    navigationMapRoute.removeRoute();
                }
                premeierFois=true;
                listePoints = new ArrayList<>();
                points= new ArrayList<>();
                listeRoute=new ArrayList<>();
                parcours1=new Parcours("Route 1");
                LunchButton.setVisibility(View.INVISIBLE);
                startButton.setVisibility(View.VISIBLE);

                // boite de dialogue
                AlertDialog.Builder myPupUp=new AlertDialog.Builder(activity);
                myPupUp.setCancelable(true);
                myPupUp.setMessage("Voulez-vous commancer le parcours par votre point de localisation ??");
                myPupUp.setPositiveButton("oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"le premier point est le point de départ",Toast.LENGTH_LONG).show();
                        localoiationB=true;
                    }
                });

                myPupUp.setNegativeButton("non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"le premier point n'est pas le point de départ",Toast.LENGTH_LONG).show();
                        localoiationB=false;
                    }
                });

                myPupUp.create();
                myPupUp.show();

                Toast.makeText(getApplicationContext(),"Parcours Supprimé",Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        String layerType=Style.MAPBOX_STREETS;

        String tmp=getIntent().getStringExtra("layerType");

        if(tmp !=null)layerType=layerTypeConvert(tmp.toString());
        TracerParcoursActivity.this.mapboxMap = mapboxMap;

        mapboxMap.setStyle(layerType, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocationComponent(style);
                //addDestinationIconSymbolLayer(style);

                mapboxMap.addOnMapClickListener(TracerParcoursActivity.this);
                mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {


                        LatLng position =marker.getPosition();
                        // supprission dan la liste des points
                        Point point =Point.fromLngLat(position.getLongitude(), position.getLatitude());
                        if(listePoints.size()!=0){

                            listePoints.remove(point);

                            if(currentRoute!=null) {
                                navigationMapRoute.removeRoute();
                                currentRoute = null;
                                TracerParcour(listePoints);
                            }

                            if(listePoints.size()<2) {

                                AlertDialog.Builder myPupUp=new AlertDialog.Builder(activity);
                                myPupUp.setCancelable(true);
                                myPupUp.setTitle("Attention");
                                myPupUp.setMessage("y'en a pas assez de point d'interet pour tracer le parcours");
                                myPupUp.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


                                    }
                                });
                                myPupUp.create();
                                myPupUp.show();

                                //LunchButton.setVisibility(View.INVISIBLE);
                                startButton.setVisibility(View.INVISIBLE);

                                currentRoute=null;

                            }


                        }


                        mapboxMap.removeMarker(marker);

                        return true;
                    }
                });

            }
        });

        AlertDialog.Builder myPupUp=new AlertDialog.Builder(activity);
        myPupUp.setCancelable(true);
        myPupUp.setMessage("Voulez-vous commancer le parcours par votre point de localisation ??");
        myPupUp.setPositiveButton("oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"le premier point est le point de départ",Toast.LENGTH_LONG).show();
                localoiationB=true;
            }
        });

        myPupUp.setNegativeButton("non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"le premier point n'est pas le point de départ",Toast.LENGTH_LONG).show();
                localoiationB=false;
            }
        });

        myPupUp.create();
        myPupUp.show();


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




    @SuppressWarnings( {"MissingPermission"})
    @Override
    public boolean onMapClick(@NonNull LatLng point) {

        if(localoiationB) {
            location = mapboxMap.getLocationComponent().getLastKnownLocation();
            originPoint = Point.fromLngLat(location.getLongitude(), location.getLatitude());
            listePoints.add(originPoint);
            localoiationB=false;
        }

        destinationPoint = Point.fromLngLat(point.getLongitude(), point.getLatitude());
        // Modifier le nom du point d'interet
        changeTitlePopup =new ChangeTitelPupUp(activity);
        changeTitlePopup.setTitle("Marker Name");
        // changeTitlePopup.setSubTitle("Entrer le Nom du Point d'interet !!");
        changeTitlePopup.getEnregitrer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Nom du Marker enregitré",Toast.LENGTH_LONG).show();
                p=new PointP(point,changeTitlePopup.getEditText().getText().toString());
                MarkersName.add(p.getName());
                PointInteret pointInteret =new PointInteret(point,mapboxMap,changeTitlePopup.getEditText().getText().toString());
                PointInteret.AddPointInteret(pointInteret);
                changeTitlePopup.dismiss();
            }
        });
        changeTitlePopup.build();

        parcours1.AddPoint(p);
        points.add(point);
        listePoints.add(destinationPoint);

        if(currentRoute !=null){
            navigationMapRoute.removeRoute();
            currentRoute=null;

            TracerParcour(listePoints);
        }


        addRoute.setVisibility(View.VISIBLE);
        dropMarkers.setVisibility(View.VISIBLE);
        startButton.setEnabled(true);
        startButton.setBackgroundResource(R.color.mapbox_navigation_view_color_accent);
        startButton.setVisibility(View.VISIBLE);

        return true;
    }

    public void TracerParcour(ArrayList<Point> liste){

        Point origin=liste.get(0);
        Point destination=liste.get(liste.size()-1);
        getRoute(origin,destination);

    }


    /*
    private void getRoute(Point origin, Point destination) {
       // double bearing = Float.valueOf(location.getBearing()).doubleValue();
       // double tolerance = 90d;

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
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                        }
                        /*else {

                        }*/

                     /*       navigationMapRoute.addRoutes(listeRoute);

                                                }
                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });
    }

*/


    private void getRoute(Point origin, Point destination) {
        // double bearing = Float.valueOf(location.getBearing()).doubleValue();
        // double tolerance = 90d;

        NavigationRoute.Builder builder = NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())
                .profile(PROFILE_WALKING)
                .origin(origin)
                .destination(destination);



        for (int i=1;i<listePoints.size()-1;i++) {
            builder.addWaypoint(listePoints.get(i));
        }
        builder.build()


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

                        //listeRoute.add(currentRoute);

                        // Draw the route on the map
                        if (navigationMapRoute == null) {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                        }
                        /*else {

                        }*/

                        navigationMapRoute.addRoute(currentRoute);

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


    public void goListeItems(View view) {
        Intent intent=new Intent(this,ListeItemsActivity.class);
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


    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        return false;
    }
}

