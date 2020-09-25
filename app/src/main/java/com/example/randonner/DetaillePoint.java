package com.example.randonner;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.net.ProtocolFamily;
import java.util.ArrayList;
import java.util.LinkedList;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
public class DetaillePoint extends Activity {

    private ListView mesImagesListView;
    private ArrayList<File> listFiles;
    private GridView gridView;
    public int pos;
    private static final int PICK_IMAGE=100;
    private Uri imageURI;ImageView imageView;
    private EditText description,titre;
    private ImageView change;
    private DataBaseManager dataBaseManager;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaille_point);
        dataBaseManager=MainActivity.dataBase;
        change=(ImageView)findViewById(R.id.modification);
        titre=(EditText)findViewById(R.id.titrePoint);
        description=(EditText)findViewById(R.id.description);
        description.setEnabled(false);
        titre.setEnabled(false);
        Intent intent=getIntent();
        id=intent.getIntExtra("point",0)+1;
        System.out.println("Extra----------"+id);
        System.out.print("ressource:"+change.getResources().toString());
        titre.setText(dataBaseManager.getPointName(id));
        description.setText(dataBaseManager.getPointDescription(id));
        gridView=(GridView)findViewById(R.id.my_grid_image);

        gridView.setAdapter(new ImageAdapter(this));
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),FullImageActivity.class);
                intent.putExtra("id",position);
                System.out.print("position: "+position);
                pos=position;
                startActivity(intent);
            }
        });
    }


    public void afficherGallerie(View view){
        openGallery();
    }

    private void openGallery() {
        Intent gallerie=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallerie,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode==RESULT_OK && requestCode==PICK_IMAGE){
            imageURI=data.getData();


        }
        Intent intent=new Intent(getApplicationContext(),FullImageActivity.class);
        imageView=(ImageView)findViewById(R.id.myFullImage);
        imageView.setImageURI(imageURI);
        startActivity(intent);
    }



    private ArrayList<File> imageReader(File externalStorageDirectory) {

        ArrayList<File> list=new ArrayList<>();
        File[] files=externalStorageDirectory.listFiles();
        for(int i=0;i<files.length;i++){
            if (files[i].isDirectory()){
                list.addAll(imageReader(files[i]));
            }
            else{
                if(files[i].getName().endsWith(".jpg")){
                    list.add(files[i]);
                }
            }
        }

        return list;
    }


    public void modifierInfo(View view){
        Drawable drawable=change.getDrawable();
        Drawable drawable1=getDrawable(R.drawable.modification);
        if(drawable.getConstantState().equals(drawable1.getConstantState())){
            description.setEnabled(true);
            titre.setEnabled(true);
            change.setImageResource(R.drawable.check);
            String changeTitre=titre.getText().toString();
            String changeDescription=description.getText().toString();
            titre.setText(changeTitre);
            description.setText(changeDescription);

        }
        else{
            dataBaseManager.alterNamePoint(titre.getText().toString(),id);
            dataBaseManager.alterDescription(description.getText().toString(),id);
            description.setEnabled(false);
            titre.setEnabled(false);
            change.setImageResource(R.drawable.modification);
        }



    }
}
