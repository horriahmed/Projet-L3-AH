package com.example.randonner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.randonner.externelDataBase.Configuration;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

import static com.mapbox.api.directions.v5.DirectionsCriteria.PROFILE_WALKING;

public class ShowParcoursInformations extends UserActivity {

    private MapView mapView;
    private EditText nom,description;
    private TextView date;
    private ArrayList<PointParcours> listPoints;
    private Parcours parcours;
    SessionManager sessionManager;
    private static final String TAG = "DirectionsActivity";
    private MapboxMap mapboxMap;
    private DirectionsRoute currentRoute;
    private ArrayList<DirectionsRoute> listeRoute;
    private NavigationMapRoute navigationMapRoute;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoiaW5hbWhhZGFpciIsImEiOiJjanMxMzNrM3Axazl4NDNtMWt3cnlyMGtmIn0.KtCpe48N-HJ3bfhXp-Ywrw");
        setContentView(R.layout.activity_show_parcours_informations);


        sessionManager = new SessionManager(this);
        mapView = findViewById(R.id.mapParcours);
        mapView.onCreate(savedInstanceState);



        nom = findViewById(R.id.parcoursNom);
        description = findViewById(R.id.parcoursDescription);
        date = findViewById(R.id.parcoursDate);



        parcours = (Parcours) getIntent().getSerializableExtra("parcours");
        System.out.println(parcours);
        System.out.println("NOM"+parcours.getNom());


        nom.setText(parcours.getNom().toUpperCase());
        description.setText(parcours.getDescription());
        date.setText(parcours.getDate());

        nom.setEnabled(false);
        date.setEnabled(false);
        description.setEnabled(false);

        listeRoute=new ArrayList<>();
        listPoints = new ArrayList<PointParcours>();
        getPoint();


        mapView.getMapAsync(this);
    }

    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

// Map is set up and the style has loaded. Now you can add data or make other map adjustments


            }
        });

    }


    void getPoint(){
        StringRequest request = new StringRequest(Request.Method.POST, Configuration.GET_POINTS , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("1"+response);
                response=response.substring(response.indexOf("{"));
                System.out.println("2"+response);


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString(Configuration.TAG_JSON_SUCCESS);

                    if (success.equals("1")) {
                        int num_rows=Integer.parseInt(jsonObject.getString(Configuration.TAG_JSON_NUM_ROWS));
                        for(int i=0;i<num_rows;i++){
                            JSONArray jsonArray = jsonObject.getJSONArray(String.valueOf(i));
                            double latitude=0,longitude=0;
                            int position=0;
                            for(int j=0;j<jsonArray.length();j++) {
                                JSONObject object= jsonArray.getJSONObject(j);
                                latitude=object.getDouble(Configuration.TAG_POINT_LATITUDE);
                                longitude=object.getDouble(Configuration.TAG_POINT_LONGITUDE);
                                position=object.getInt(Configuration.TAG_POINT_POSITION);
                            }

                         listPoints.add(new PointParcours(parcours.getId_utilisateur(),parcours.getId_parcours()
                                        ,latitude,longitude,position));

                        }
                        System.out.println(listPoints);
                        for (int i=0;i<listPoints.size()-1;i++){
                            Point origin=Point.fromLngLat(listPoints.get(i).getLongitude(),listPoints.get(i).getLatitude());
                            Point destination=Point.fromLngLat(listPoints.get(i+1).getLongitude(),listPoints.get(i+1).getLatitude());
                            getRoute(origin,destination);
                        }
                        System.out.println(listPoints);
                    }
                    else{
                        Toast.makeText(ShowParcoursInformations.this, "Error 1: ", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ShowParcoursInformations.this, "Error 2: "+e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShowParcoursInformations.this, "Error 3: ", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                System.out.println("session"+sessionManager.getUserDetail().get(SessionManager.ID));
                params.put(Configuration.TAG_PARCOURS_ID, String.valueOf(parcours.getId_parcours()));
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
        System.out.println("3"+listPoints);
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



    private void getRoute(Point origin, Point destination) {

        NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())
                .profile(PROFILE_WALKING)
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, retrofit2.Response<DirectionsResponse> response) {
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
}