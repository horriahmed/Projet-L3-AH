package com.example.randonner;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.randonner.externelDataBase.Configuration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtilsExterne {

    private static int max;




    //ajouter un point

    public static void addPoint(int id_utilisateur, int id_parcours, double latitude, double longitude, int position, Context context){

        StringRequest request = new StringRequest(Request.Method.POST, Configuration.URL_ADD_POINT , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response=response.substring(response.indexOf("{"));

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString(Configuration.TAG_JSON_SUCCESS);

                    if (success.equals("1")) {

                    }
                    else{
                        Toast.makeText(context, "Error 1: "+jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Error 2: "+e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error 3: ", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(Configuration.USER_ID, String.valueOf(id_utilisateur));
                params.put(Configuration.PARCOURS_ID, String.valueOf(id_parcours));
                params.put(Configuration.LATITUDE, String.valueOf(latitude));
                params.put(Configuration.LONGITUDE, String.valueOf(longitude));
                params.put(Configuration.POSITION, String.valueOf(position));
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(request);


    }


    //ajouter un parcours

    public static int addParcours(int id_utilisateur,String nom,String date,String description,Context context){

        StringRequest request = new StringRequest(Request.Method.POST, Configuration.URL_ADD_PARCOURS , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response=response.substring(response.indexOf("{"));

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString(Configuration.TAG_JSON_SUCCESS);


                    if (success.equals("1")) {
                        max= Integer.parseInt(jsonObject.getString("max"));

                    }
                    else{
                        Toast.makeText(context, "Error 1: "+jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Error 2: "+e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error 3: ", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(Configuration.USER_ID, String.valueOf(id_utilisateur));
                params.put(Configuration.PARCOURS_NOM, nom);
                params.put(Configuration.PARCOURS_DATE,date);
                params.put(Configuration.PARCOURS_DESCRIPTION, description);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(request);

     return max;
    }


    public static void addPointInteret(int id_utilisateur, String nom, double latitude, double longitude, String description, Context context){

        StringRequest request = new StringRequest(Request.Method.POST, Configuration.URL_ADD_POINT_INTERET , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response=response.substring(response.indexOf("{"));

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString(Configuration.TAG_JSON_SUCCESS);

                    if (success.equals("1")) {

                    }
                    else{
                        Toast.makeText(context, "Error 1: "+jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Error 2: "+e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error 3: ", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(Configuration.USER_ID, String.valueOf(id_utilisateur));
                params.put(Configuration.POINT_INTERET_NOM, nom);
                params.put(Configuration.LATITUDE, String.valueOf(latitude));
                params.put(Configuration.LONGITUDE, String.valueOf(longitude));
                params.put(Configuration.POINT_INTERET_DESCRIPTION, description);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(request);


    }



    public static void addPointInteretPicture(int id_utilisateur, String nom, double latitude, double longitude, String photo, Context context){

        StringRequest request = new StringRequest(Request.Method.POST, Configuration.URL_ADD_POINT_INTERET_PICTURE , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response=response.substring(response.indexOf("{"));

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString(Configuration.TAG_JSON_SUCCESS);

                    if (success.equals("1")) {

                    }
                    else{
                        Toast.makeText(context, "Error 1: "+jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Error 2: "+e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error 3: ", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(Configuration.USER_ID, String.valueOf(id_utilisateur));
                params.put(Configuration.POINT_INTERET_NOM, nom);
                params.put(Configuration.LATITUDE, String.valueOf(latitude));
                params.put(Configuration.LONGITUDE, String.valueOf(longitude));
                params.put(Configuration.POINT_INTERET_DESCRIPTION, photo);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(request);


    }


    public static List<Integer> getPointInteretPictures(double latitude, double longitude, Context context){

        ArrayList<Integer> tmp=new ArrayList<Integer>();

        StringRequest request = new StringRequest(Request.Method.POST, Configuration.GET_POINTS_INTERETS_PICTURES , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response=response.substring(response.indexOf("{"));

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString(Configuration.TAG_JSON_SUCCESS);

                    if (success.equals("1")) {

                        int num_rows=Integer.parseInt(jsonObject.getString(Configuration.TAG_JSON_NUM_ROWS));
                        for(int i=0;i<num_rows;i++){

                            String id = null;
                            id=jsonObject.getString(String.valueOf(i));
                            tmp.add(Integer.parseInt(id));

                        }}
                    else{
                        Toast.makeText(context, "Error 1: "+jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Error 2: "+e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error 3: "+error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(Configuration.LATITUDE, String.valueOf(latitude));
                params.put(Configuration.LONGITUDE, String.valueOf(longitude));
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(request);

        return tmp;

    }


    public static List<PointInteret> getPointsInterets(int id_utilisateur,Context context){
        ArrayList<PointInteret> tmp =new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.POST, Configuration.GET_POINTS_INTERETS , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response=response.substring(response.indexOf("{"));



                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString(Configuration.TAG_JSON_SUCCESS);

                    if (success.equals("1")) {
                        int num_rows=Integer.parseInt(jsonObject.getString(Configuration.TAG_JSON_NUM_ROWS));
                        for(int i=0;i<num_rows;i++){
                            JSONArray jsonArray = jsonObject.getJSONArray(String.valueOf(i));
                            String latitude = null,nom = null,longitude = null,description = null;
                            for(int j=0;j<jsonArray.length();j++) {
                                JSONObject object= jsonArray.getJSONObject(j);
                                nom =object.getString(Configuration.TAG_USER_NOM).trim();
                                latitude = object.getString(Configuration.LATITUDE).trim();
                                longitude = object.getString(Configuration.LONGITUDE).trim();
                                description = object.getString("descreption").trim();
                            }
                            tmp.add(new PointInteret(id_utilisateur,nom,description,Double.parseDouble(latitude),Double.parseDouble(longitude)));

                        }

                    }
                    else{
                        Toast.makeText(context, "Error 1: ", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Error 2: "+e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error 3: ", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(Configuration.KEY_USER_ID, String.valueOf(id_utilisateur));
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(request);

        return tmp;


    }









}
