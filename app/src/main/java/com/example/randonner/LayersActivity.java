package com.example.randonner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LayersActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layers);
    }

    public void layerClick(View view) {
        Intent intent=new Intent(this,MainActivity.class);
        Bundle bundle=new Bundle();
        intent.putExtra("layerType",view.getResources().getResourceEntryName(view.getId()));
        startActivity(intent);
    }

    public void BackToMain(View view) {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void goProfile(View view) {
        Intent intent=new Intent(this, ConnectionActivity.class);
        startActivity(intent);
    }


}
