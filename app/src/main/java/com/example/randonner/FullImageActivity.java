package com.example.randonner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;


public class FullImageActivity extends AppCompatActivity {
   private ImageView imageView;
    private int positionImage;
    private ImageAdapter imageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
       // flipperLayout=(FlipperLayout)findViewById(R.id.flipper);
        imageAdapter=new ImageAdapter(this);
        DetaillePoint detaillePoint=new DetaillePoint();
       // setLayout(imageAdapter.listImage);
         imageView=(ImageView)findViewById(R.id.myFullImage);
        setImageView(0);
    }

    public void goToPrevious(View view){
        positionImage--;
        if (positionImage<0){
            positionImage=imageAdapter.listImage.length-1;
        }
       imageView.setImageResource(imageAdapter.listImage[positionImage]);
    }
    public void goToNext(View view){
        positionImage++;
        if (positionImage>=imageAdapter.listImage.length){
            positionImage=0;
        }
        imageView.setImageResource(imageAdapter.listImage[positionImage]);

    }

   private void setImageView(int i){

        Intent intent=getIntent();
        positionImage=(intent.getExtras().getInt("id"))+i;
        imageView.setImageResource(imageAdapter.listImage[positionImage]);
   }
}
