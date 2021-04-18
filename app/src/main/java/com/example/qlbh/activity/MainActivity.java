package com.example.qlbh.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.qlbh.R;
import com.example.qlbh.adapter.loaispadapter;
import com.example.qlbh.model.sanpham;
import com.example.qlbh.ultil.server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.example.qlbh.ultil.checkinternetconnection;
import com.squareup.picasso.RequestCreator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lstmhc;
    NavigationView navigationview;
    RecyclerView recyclerview;
    Toolbar toolbar;
    ViewFlipper viewflipper;
    DrawerLayout drawerlayout;
    ArrayList<sanpham> mangloaisp;
    loaispadapter loaispadapter;
    int id=0;
    String tenloaisp="";
    String hinhloaisp="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        if (checkinternetconnection.haveNetworkConnection(getApplicationContext()))
        {
           ActionBar();
           ActionViewFlipper();
           Getdulieuloaisp();
        }
     else
        {
            checkinternetconnection.ShowToast_Short(getApplicationContext(),"Kiểm tra lại kết nối");
            finish();
        }
    }

    private void Getdulieuloaisp() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(server.duongdan, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
              if(response!=null)
              {
                for(int i=0;i<response.length();i++)
                {
                    try {
                        JSONObject jsonObject=response.getJSONObject(i);
                        id=jsonObject.getInt("idloaisp");
                        tenloaisp=jsonObject.getString("tenloaisp");
                        hinhloaisp=jsonObject.getString("hinhloaisp");
                        mangloaisp.add(new sanpham(id,tenloaisp,hinhloaisp));
                        loaispadapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mangloaisp.add(3,new sanpham(0,"Liên hệ","https://image.flaticon.com/icons/png/128/901/901141.png"));
                mangloaisp.add(4,new sanpham(0,"Thông tin","https://t3.ftcdn.net/jpg/02/32/71/62/240_F_232716200_xTsnomMS5djsC6m9cDNQmEKtPgt11Xjo.jpg"));

              }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                checkinternetconnection.ShowToast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
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
        mangloaisp=new ArrayList<>();
        mangloaisp.add(0,new sanpham(0,"Trang chính",
                "https://image.flaticon.com/icons/png/128/1946/1946433.png"));

        loaispadapter=new loaispadapter(mangloaisp,getApplicationContext());
        lstmhc.setAdapter(loaispadapter);
    }
}