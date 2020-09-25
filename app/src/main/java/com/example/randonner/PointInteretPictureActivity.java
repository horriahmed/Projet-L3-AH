package com.example.randonner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PointInteretPictureActivity extends ProfilePictureActivity {

    private ImageView pointInteretPicture;
    private static final String TAG= PointInteretPictureActivity.class.getSimpleName();//getting the info
    private Bitmap bitmap;
    private double latitude,logitude;
    private int id;
    private String nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_interet_picture);
    }

    public void savePicture(View view) {
        addPicture(String.valueOf(id),nom,latitude,logitude,getStringImage(bitmap));
        Intent intent=new Intent(this,UserActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK ){
            if(requestCode==1){
                System.out.println("requestCode 1");
                Uri filePath = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                    pointInteretPicture.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();}
            }else if(requestCode==0) {
                System.out.println("requestCode 0");
                bitmap = (Bitmap) data.getExtras().get("data");
                pointInteretPicture.setImageBitmap(bitmap);
            }
        }

    }


    private void addPicture(final String id,final String nom,final double latitude,final double longitude,final String photo){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Configuration.ADD_POINT_INTERET_PICTURE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                System.out.println(response);
                response=response.substring(response.indexOf("{"));
                Log.i( TAG,response.toString());
                System.out.println(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString(Configuration.TAG_JSON_SUCCESS);
                    if (success.equals("1")) {
                        Toast.makeText(PointInteretPictureActivity.this, "Register success", Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(PointInteretPictureActivity.this,UserActivity.class);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(PointInteretPictureActivity.this, "Try Again !!: "+e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PointInteretPictureActivity.this, "Try Again ! "+error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(Configuration.KEY_USER_ID,id);
                params.put(Configuration.TAG_USER_NOM,nom);
                params.put(Configuration.LATITUDE,String.valueOf(latitude));
                params.put(Configuration.LONGITUDE,String.valueOf(longitude));
                params.put(Configuration.KEY_USER_SRC_IMAGE,photo);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imageByteArray =byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray,Base64.DEFAULT);
        return encodedImage;
    }
}

