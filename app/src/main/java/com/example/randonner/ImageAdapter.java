package com.example.randonner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.randonner.R;

public class ImageAdapter extends BaseAdapter {


    private Context context;
    public Integer[] listImage={
            R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4,R.drawable.img5
    };


    public ImageAdapter(Context context){
        this.context=context;
    }


    @Override
    public int getCount() {
        return listImage.length;
    }

    @Override
    public Object getItem(int position) {
        return listImage[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid=convertView;
        if (grid==null){
            LayoutInflater inflater =(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            grid=inflater.inflate(R.layout.activity_image,null);
        }

        ImageView imageView=(ImageView)grid.findViewById(R.id.my_image);
        imageView.setImageResource(listImage[position]);


        return imageView;
    }

}
