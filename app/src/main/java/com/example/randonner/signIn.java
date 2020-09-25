package com.example.randonner;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class signIn extends Activity implements DatePickerDialog.OnDateSetListener {

    private TextView birthDay;
    private EditText nom;
    private EditText prenom;
    private EditText motDePasse;
    private EditText motDePasseCOnfirmation;
    private EditText mail;
    private EditText tel;
    private ProgressDialog pDialog;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        sessionManager = new SessionManager(this);

        birthDay=(TextView)findViewById(R.id.birthDay);
        nom=(EditText)findViewById(R.id.firstName);
        prenom=(EditText)findViewById(R.id.lastName);
        mail=(EditText)findViewById(R.id.email);
        tel=(EditText)findViewById(R.id.phoneNumber);
        motDePasse=(EditText)findViewById(R.id.password);
        motDePasseCOnfirmation=(EditText)findViewById(R.id.passwordConfirmation);
        birthDay.setText(Calendar.getInstance().get(Calendar.YEAR)+"-"+Calendar.getInstance().get(Calendar.MONTH)+"-"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    }

    public void birthDayClick(View view) {
        DatePickerDialog datePickerDialog=new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        birthDay.setText(year+"-"+month+"-"+dayOfMonth);
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(signIn.this);
        pDialog.setMessage("Signing Up.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    //Adding Userj
    private  void addUser() {

        final String nom = this.nom.getText().toString().trim();
        final String prenom = this.prenom.getText().toString().trim();
        final String motDePasse = this.motDePasse.getText().toString().trim();
        final String motDePasseConfirmation = this.motDePasseCOnfirmation.getText().toString().trim();
        final String mail = this.mail.getText().toString().trim().toLowerCase();
        final String tel = this.tel.getText().toString().trim();
        final String dateNaissance = this.birthDay.getText().toString();
        ////////////////////////////////////////////////////////////////
        if (isValidEmail(mail)) {
            if (motDePasse.equals(motDePasseConfirmation)&& !motDePasse.isEmpty()) {

                StringRequest request = new StringRequest(Request.Method.POST, Configuration.URL_ADD_USER, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        response = response.substring(response.indexOf("{"));
                        System.out.println(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString(Configuration.TAG_JSON_SUCCESS);
                            if (success.equals("1")) {
                                Toast.makeText(signIn.this, "Register success", Toast.LENGTH_LONG).show();
                                sessionManager.createSession(jsonObject.getString(Configuration.TAG_USER_ID), nom, prenom, mail, tel, dateNaissance,motDePasse);
                                Intent intent = new Intent(signIn.this, UserActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(signIn.this, "Error : " + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(signIn.this, "Error : " + error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put(Configuration.KEY_USER_NOM, nom);
                        params.put(Configuration.KEY_USER_PRENOM, prenom);
                        params.put(Configuration.KEY_USER_BIRTHDATE, dateNaissance);
                        params.put(Configuration.KEY_USER_MAIL, mail);
                        params.put(Configuration.KEY_USER_TEL, tel);
                        params.put(Configuration.KEY_USER_PASSWORD, sha256(motDePasse));


                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(request);
            }else{
                this.motDePasseCOnfirmation.setError("password and the password confirmation not the same");
            }
        }else{
            this.mail.setError("email not valid");
        }
    }

    public void ajouterUtilisateur(View view) {
        this.addUser();
    }

    private String sha256(String pass)  {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            Toast.makeText(signIn.this, "Error : "+e.toString(), Toast.LENGTH_LONG).show();
        }
        // Change this to UTF-16 if needed
        md.update(pass.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();

        String hex = String.format("%064x", new BigInteger(1, digest));
        return hex;
    }

    public void goBackInscription(View view) {
        startActivity(new Intent(this,ConnectionActivity.class));
    }
}
