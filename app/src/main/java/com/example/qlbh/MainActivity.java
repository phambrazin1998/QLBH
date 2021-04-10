package com.example.qlbh;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.Toolbar;
import android.widget.ViewFlipper;
import com.google.android.material.navigation.NavigationView;
import com.example.qlbh.R;
import com.squareup.picasso.Picasso;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lstmhc;
    NavigationView navigationview;
    RecyclerView recyclerview;
    Toolbar toolbar;
    ViewFlipper viewflipper;
    DrawerLayout drawerlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        ActionBar();
        ActionViewFlipper();
    }
    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://cdn.cellphones.com.vn/media/ltsoft/promotion/690x300-MFF.png");
        mangquangcao.add("https://cdn.tgdd.vn/2021/04/banner/S21-800-300-800x300-1.png");
        for(int i=0;i<mangquangcao.size();i++)
        {
            ImageView imageView=new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewflipper.addView(imageView);
        }
        viewflipper.setFlipInterval(5000);
        viewflipper.setAutoStart(true);
        Animation animation_slide_in= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide);
        Animation animation_slide_out= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slideout);
        viewflipper.setInAnimation(animation_slide_in);


    }

    private void ActionBar() {
      setActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerlayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void AnhXa()
    {
        drawerlayout= (DrawerLayout) findViewById(R.id.drawerlayout);
        toolbar     =  findViewById(R.id.toolbar);
        viewflipper =(ViewFlipper) findViewById(R.id.viewflipper);
        recyclerview =(RecyclerView) findViewById(R.id.recyclerview);
        navigationview=(NavigationView) findViewById(R.id.navigationview);
        lstmhc=(ListView)findViewById(R.id.lstmhc);
    }
}