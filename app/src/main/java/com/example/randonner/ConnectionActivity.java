
package com.example.randonner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class ConnectionActivity extends MainActivity {


    EditText mail,motDePasse;
    Button login;
    SessionManager sessionManager;
    private String erreur="MAIL INCORRECT !";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        sessionManager = new SessionManager(this);
        mail = (EditText)findViewById(R.id.identifiant);
        motDePasse= (EditText)findViewById(R.id.password);
    }


    public void signInClick(View view) {
        startActivity(new Intent(this,signIn.class));
    }

    private String sha256(String pass)  {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            Toast.makeText(ConnectionActivity.this, "Error : "+e.toString(), Toast.LENGTH_LONG).show();
        }
        // Change this to UTF-16 if needed
        md.update(pass.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();

        String hex = String.format("%064x", new BigInteger(1, digest));
        return hex;
    }


    public void LogIn(View view) {

        String mail=this.mail.getText().toString().trim();
        String motDePasse=this.motDePasse.getText().toString().trim();
        if(!mail.isEmpty() || !motDePasse.isEmpty()){
            if(!motDePasse.isEmpty()){
                if(isValidEmail(mail))Login(mail.toLowerCase(),motDePasse);
                else this.mail.setError("email not valide");
            }else  this.motDePasse.setError("Please insert password");
        }else{
            this.mail.setError("Please insert email");
            this.motDePasse.setError("Please insert password");
        }

    }

    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private void Login(String imail, final String imotDePasse){
        final String mail=imail;
        final String motDePasse=sha256(imotDePasse);
        final Map<String,String> map = new HashMap<>();
        String nom,prenom,tel,dateNaissance;

        StringRequest request = new StringRequest(Request.Method.POST, Configuration.LOGIN , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                response=response.substring(response.indexOf("{"));
                System.out.println(response);


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString(Configuration.TAG_JSON_SUCCESS);

                    erreur=jsonObject.getString("message");

                    if (success.equals("1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("login");
                        for(int i=0;i<jsonArray.length();i++) {
                            JSONObject object= jsonArray.getJSONObject(i);
                            String id =object.getString(Configuration.TAG_USER_ID).trim();
                            String nom = object.getString(Configuration.TAG_USER_NOM).trim();
                            String prenom = object.getString(Configuration.TAG_USER_PRENOM).trim();
                            String mail = object.getString(Configuration.TAG_USER_MAIL).trim();
                            String tel = object.getString(Configuration.TAG_USER_TEL).trim();
                            String dateDeNaissance = object.getString(Configuration.TAG_USER_BIRTHDATE).trim();
                            sessionManager.createSession(id,nom,prenom,mail,tel,dateDeNaissance,imotDePasse);
                            Toast.makeText(ConnectionActivity.this,"Success Login \n"+nom+" "+prenom,Toast.LENGTH_LONG).show();
                        }



                        Intent intent=new Intent(ConnectionActivity.this,UserActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(ConnectionActivity.this, "Error 1: "+erreur, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ConnectionActivity.this, "Error 2: "+erreur, Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ConnectionActivity.this, "Error 3: Connection Problem", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(Configuration.KEY_USER_MAIL,mail.toLowerCase());
                params.put(Configuration.KEY_USER_PASSWORD,motDePasse);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }



    @Override
    protected void onResume() {
        super.onResume();

    }


}
