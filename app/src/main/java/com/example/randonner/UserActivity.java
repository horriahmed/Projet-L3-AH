package com.example.randonner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.randonner.externelDataBase.Configuration;
import com.mapbox.mapboxsdk.geometry.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends MainActivity{
    CircleImageView profilePicture;
    ImageView edit,save;
    EditText nom,prenom,mail,tel,password;
    TextView dateDeNaissance;
    String id;
    SessionManager sessionManager;
    ArrayList<Parcours> listParcours;
    ArrayList<PointInteret> listPointInteret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        listParcours=new ArrayList<>();
        nom = (EditText) findViewById(R.id.firstName);
        prenom = (EditText) findViewById(R.id.lastName);
        password = (EditText) findViewById(R.id.password);
        mail = (EditText) findViewById(R.id.email);
        dateDeNaissance = (TextView) findViewById(R.id.birthDay);
        tel = (EditText) findViewById(R.id.phoneNumber);
        profilePicture = (CircleImageView) findViewById(R.id.profilePicture);
        edit=(ImageView)findViewById(R.id.edit);
        save=(ImageView)findViewById(R.id.save);
        edit.setVisibility(View.VISIBLE);
        save.setVisibility(View.INVISIBLE);
        nom.setEnabled(false);
        prenom.setEnabled(false);
        mail.setEnabled(false);
        tel.setEnabled(false);
        dateDeNaissance.setEnabled(false);
        profilePicture.setEnabled(false);
        findViewById(R.id.passwordRow).setVisibility(View.INVISIBLE);


        HashMap<String, String> user = sessionManager.getUserDetail();
        id = user.get(SessionManager.ID);
        System.out.println(id);
        nom.setText(user.get(SessionManager.NOM));
        prenom.setText(user.get(SessionManager.PRENOM));
        mail.setText(user.get(SessionManager.MAIL));
        dateDeNaissance.setText(user.get(SessionManager.BIRTHDAY));
        tel.setText(user.get(SessionManager.TEL));

        System.out.println(Configuration.PATH_PROFILE_PICTURE + id + ".jpeg");

        Glide.with(UserActivity.this)
                .load(Configuration.PATH_PROFILE_PICTURE + id + ".jpeg")
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.user)
                .into(profilePicture);
    }




    public void homeClick(View view) {
    }

    public void parcoursClick(View view) {
    }

    public void pointInteretClick(View view) {
        Intent intent=new Intent(this,ShowPointInteretInformation.class);
        startActivity(intent);
    }

    public void profilePictureChoosen(View view) {

        Intent intent = new Intent(this,ProfilePictureActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    public void logOut(View view) {
        sessionManager.logout();
    }

    public void edit(View view) {
        edit.setVisibility(View.INVISIBLE);
        save.setVisibility(View.VISIBLE);
        nom.setEnabled(true);
        prenom.setEnabled(true);
        mail.setEnabled(true);
        tel.setEnabled(true);
        dateDeNaissance.setEnabled(true);
        profilePicture.setEnabled(true);
        findViewById(R.id.passwordRow).setVisibility(View.VISIBLE);


    }

    public void save(View view) {
        final String inom,iprenom,imail,itel,idateNaissance,imotDePasse;
        inom=nom.getText().toString().trim();
        iprenom=prenom.getText().toString().trim();
        imail=mail.getText().toString().trim().toLowerCase();
        itel=tel.getText().toString().trim();
        idateNaissance=dateDeNaissance.getText().toString().trim();
        imotDePasse=password.getText().toString().trim();
        findViewById(R.id.passwordRow).setVisibility(View.INVISIBLE);

        edit.setVisibility(View.VISIBLE);
        save.setVisibility(View.INVISIBLE);
        nom.setEnabled(false);
        prenom.setEnabled(false);
        mail.setEnabled(false);
        tel.setEnabled(false);
        dateDeNaissance.setEnabled(false);
        profilePicture.setEnabled(false);

        StringRequest request = new StringRequest(Request.Method.POST, Configuration.UPDATE , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                response=response.substring(response.indexOf("{"));
                System.out.println(response);


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString(Configuration.TAG_JSON_SUCCESS);

                    if (success.equals("1")) {
                        if(!imotDePasse.isEmpty()) sessionManager.createSession(id,inom,iprenom,imail,itel,idateNaissance,imotDePasse);
                        else  sessionManager.createSession(id,inom,iprenom,imail,itel,idateNaissance,sessionManager.getUserDetail().get(SessionManager.PASSWORD));

                                                      Toast.makeText(UserActivity.this,"Success Update \n"+inom+" "+iprenom,Toast.LENGTH_LONG).show();



                        Intent intent=new Intent(UserActivity.this,UserActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(UserActivity.this, "Error 1: ", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(UserActivity.this, "Error 2: "+e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserActivity.this, "Error 3: ", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                System.out.println(inom+" "+iprenom+" "+imail+" "+idateNaissance+" "+itel+" "+imotDePasse);
                params.put(Configuration.KEY_USER_ID, sessionManager.getUserDetail().get(SessionManager.ID));
                params.put(Configuration.KEY_USER_NOM, inom);
                params.put(Configuration.KEY_USER_PRENOM, iprenom);
                params.put(Configuration.KEY_USER_BIRTHDATE, idateNaissance);
                params.put(Configuration.KEY_USER_MAIL, imail);
                params.put(Configuration.KEY_USER_TEL, itel);
                if(!imotDePasse.isEmpty()) params.put(Configuration.KEY_USER_PASSWORD, sha256(imotDePasse));
                else  params.put(Configuration.KEY_USER_PASSWORD, sha256(sessionManager.getUserDetail().get(SessionManager.PASSWORD)));
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    private String sha256(String pass)  {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            Toast.makeText(UserActivity.this, "Error : "+e.toString(), Toast.LENGTH_LONG).show();
        }
        // Change this to UTF-16 if needed
        md.update(pass.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();

        String hex = String.format("%064x", new BigInteger(1, digest));
        return hex;
    }

    public void menu(View view) {
        final PopupMenu popupMenu=new PopupMenu(this,view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_user_profile,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                System.out.println(item.getItemId());
                System.out.println(R.id.deleteCompte);

                switch (item.getItemId()){
                    case R.id.deleteCompte:
                        delete();break;
                    case R.id.afficherParcours:
                        getListParcours();

                }
                return true;
            }
        });
        popupMenu.show();
    }

    void getListParcours(){

        StringRequest request = new StringRequest(Request.Method.POST, Configuration.GET_PARCOURS , new Response.Listener<String>() {
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
                            String id = null,nom = null,date = null,description = null;
                            for(int j=0;j<jsonArray.length();j++) {
                                JSONObject object= jsonArray.getJSONObject(j);
                                id =object.getString(Configuration.TAG_USER_ID).trim();
                                nom = object.getString(Configuration.TAG_USER_NOM).trim();
                                date = object.getString("date").trim();
                                description = object.getString("descreption").trim();
                            }
                            listParcours.add(new Parcours(Integer.parseInt(id), Integer.parseInt(sessionManager.getUserDetail().get(SessionManager.ID)),nom,date,description));

                        }
                        Intent intent=new Intent(UserActivity.this,ListParcoursActivity.class);
                        intent.putParcelableArrayListExtra("listParcours", listParcours);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(UserActivity.this, "Error 1: ", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(UserActivity.this, "Error 2: "+e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserActivity.this, "Error 3: ", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(Configuration.KEY_USER_ID, sessionManager.getUserDetail().get(SessionManager.ID));
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
    private void delete(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Configuration.DELETE_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                System.out.println(response);
                response=response.substring(response.indexOf("{"));
                System.out.println(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString(Configuration.TAG_JSON_SUCCESS);
                    if (success.equals("1")) {
                        Toast.makeText(UserActivity.this, "Delete success", Toast.LENGTH_LONG).show();
                        sessionManager.logout();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(UserActivity.this, "Try Again !!: "+e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserActivity.this, "Try Again ! "+error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(Configuration.KEY_USER_ID,id);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    void getListPointInterets(){

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
                            String id = null,nom = null,lat = null,lng=null,description = null;
                            for(int j=0;j<jsonArray.length();j++) {
                                JSONObject object= jsonArray.getJSONObject(j);
                                id =object.getString(Configuration.TAG_USER_ID).trim();
                                nom = object.getString("nom").trim();
                                lat=object.getString("latitude").trim();
                                lng=object.getString("longitude").trim();
                                description = object.getString("descreption").trim();
                            }
                            listPointInteret.add(new PointInteret(new LatLng(Integer.parseInt(lat),Integer.parseInt(lng)),mapboxMap,nom,description));

                        }
                        Intent intent=new Intent(UserActivity.this,ListPointsIneretsActivity.class);
                        intent.putParcelableArrayListExtra("listPointInteret", (ArrayList<? extends Parcelable>) listPointInteret);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(UserActivity.this, "Error 1: ", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(UserActivity.this, "Error 2: "+e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserActivity.this, "Error 3: ", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(Configuration.KEY_USER_ID, sessionManager.getUserDetail().get(SessionManager.ID));
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
}
