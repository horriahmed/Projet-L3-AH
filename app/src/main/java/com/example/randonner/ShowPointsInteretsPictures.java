package com.example.randonner;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

public class ShowPointsInteretsPictures extends ShowPointInteretInformation{
    private GridView gridView;
    SessionManager sessionManager;
    private Button buttonAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager=new SessionManager(this);
        gridView=(GridView)findViewById(R.id.my_grid_image);
        buttonAdd=(Button)findViewById(R.id.)
    }
}
