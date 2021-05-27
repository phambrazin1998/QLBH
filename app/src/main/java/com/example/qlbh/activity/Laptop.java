package com.example.qlbh.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.qlbh.R;

import com.example.qlbh.adapter.laptopadapter;
import com.example.qlbh.model.chitietsp;
import com.example.qlbh.ultil.checkinternetconnection;
import com.example.qlbh.ultil.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Laptop extends AppCompatActivity {
    Toolbar toolbarlaptop;
    ListView lstlaptop;
    com.example.qlbh.adapter.laptopadapter laptopadapter;
    ArrayList<chitietsp> manglaptop;
    int iddt=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        Anhxa();
        if(checkinternetconnection.haveNetworkConnection(getApplicationContext()))
        {
            Getidloaisp();
            Actionbar();
            Getdata();
            Loadmoredata();
        }
        else
        {
            checkinternetconnection.ShowToast_Short(getApplicationContext(),"Kiểm tra lại kết nối");
            finish();
        }
    }
    private void Loadmoredata() {
        lstlaptop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),thongtinsp.class);
                intent.putExtra("thongtinsp",manglaptop.get(position));
                startActivity(intent);
            }
        });}
    private void Anhxa() {
        toolbarlaptop=(Toolbar) findViewById(R.id.toolbarlaptop);
        lstlaptop=(ListView) findViewById(R.id.lstlaptop);
        manglaptop=new ArrayList<>();
        laptopadapter= new laptopadapter(getApplicationContext(),manglaptop);
        lstlaptop.setAdapter(laptopadapter);
    }

    private void Getdata() { RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String duongdan= server.duongdandt+String.valueOf(iddt);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                String tendt="";
                int gia=0;
                String hinhanh="";
                String mota="";
                int idloaisp=0;
                if(response!=null)
                {
                    try {
                        JSONArray jsonArray=new JSONArray(response);
                        for (int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            id=jsonObject.getInt("id");
                            tendt=jsonObject.getString("tensp");
                            gia=jsonObject.getInt("gia");
                            hinhanh=jsonObject.getString("hinhsp");
                            mota=jsonObject.getString("mota");
                            idloaisp=jsonObject.getInt("idloaisp");
                            manglaptop.add(new chitietsp(id,tendt,gia,hinhanh,mota,idloaisp));
                            laptopadapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param=new HashMap<String,String>();
                param.put("idloaisp",String.valueOf(iddt));
                return super.getParams();
            }
        };
        requestQueue.add(stringRequest);
    }


    private void Actionbar() {
        setActionBar(toolbarlaptop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarlaptop.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbarlaptop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menugiohang,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menuiohang:
                Intent intent=new Intent(getApplicationContext(),Giohang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void Getidloaisp() {
        iddt=getIntent().getIntExtra("idloaisp",-1);
        Log.d("giatriloaisp",iddt+"");
    }
}