package com.example.brush.brushgo;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;

import java.util.ArrayList;

/**
 * Created by pig98520 on 2017/7/28.
 */

public class Tutorial_Adapter extends PagerAdapter {
/*    private int[] imageUrl={R.drawable.tutorial_1,R.drawable.tutorial_2,R.drawable.tutorial_3,R.drawable.tutorial_4};*/
    private String [] imageUrl ={
        "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2Ftutorial_1.png?alt=media&token=5a594bc4-e84e-4a37-8a92-3eb6a1f5b086",
        "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2Ftutorial_2.png?alt=media&token=44c36655-78cb-44e5-8045-01eb5d9e7a70",
        "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2Ftutorial_3.png?alt=media&token=8e3620d1-d334-4c7c-8053-5def241cc77c",
            "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2Ftutorial_4.png?alt=media&token=66704f38-4ea5-4b07-b47e-d07367bd4ff6"
    };
    private LayoutInflater layoutInflater;
    private Context context;
    private View view;
    private ImageView imageView;
    private Firebase dbRef;
    private ArrayList<String > imageList =new ArrayList<String>();

    public Tutorial_Adapter (Context context)
    {
        this.context=context;
    }

    @Override
    public int getCount() {
        return imageUrl.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view== object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=layoutInflater.inflate(R.layout.tutorial_image,container,false);
        imageView=(ImageView)view.findViewById(R.id.imageView);
/*        imageView.setImageResource(imageUrl[position]);*/
        Glide.with(context).load(Uri.parse(imageUrl[position])).into(imageView);
        container.removeView(view);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        container.invalidate();
    }
}
