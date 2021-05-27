package com.example.qlbh.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
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
import com.example.qlbh.adapter.dienthoaiadapter;
import com.example.qlbh.R;
import com.example.qlbh.adapter.dienthoaiadapter;
import com.example.qlbh.model.chitietsp;
import com.example.qlbh.ultil.checkinternetconnection;
import com.example.qlbh.ultil.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dienthoai extends AppCompatActivity {
Toolbar toolbardienthoai;
ListView lstdienthoai;
dienthoaiadapter dienthoaiadapter;
ArrayList<chitietsp> mangdt;
int page=1;
int iddt=0;
View footerview;
boolean isloading=false;
boolean limitdata=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dienthoai);
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
        lstdienthoai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),thongtinsp.class);
                intent.putExtra("thongtinsp",mangdt.get(position));
                startActivity(intent);
            }
        });}

    private void Getdata() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
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
               if(response!=null&&response.length()!=2)
               {
                  // lstdienthoai.removeFooterView(footerview);
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
                           mangdt.add(new chitietsp(id,tendt,gia,hinhanh,mota,idloaisp));
                           dienthoaiadapter.notifyDataSetChanged();
                       }
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }
//               else {
//                   limitdata=true;
//                   lstdienthoai.removeFooterView(footerview);
//                   checkinternetconnection.ShowToast_Short(getApplicationContext(),"Hết dữ liệu");
//               }
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
        setActionBar(toolbardienthoai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardienthoai.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbardienthoai.setNavigationOnClickListener(new View.OnClickListener() {
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

    private void Anhxa() {
        toolbardienthoai=(Toolbar) findViewById(R.id.toolbardienthoai);
        lstdienthoai=(ListView) findViewById(R.id.lstdienthoai);
        mangdt=new ArrayList<>();
        dienthoaiadapter= new dienthoaiadapter(getApplicationContext(),mangdt);
        lstdienthoai.setAdapter(dienthoaiadapter);
       // LayoutInflater inflater=(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
      //  footerview = inflater.inflate(R.layout.progessbar,null);
       // mHandler =new mHandler();
    }
//    public class mHandler extends Handler{
//        public void handlerMessage(Message msg)
//        {
//            switch (msg.what)
//            {
//                case 0:
//                    lstdienthoai.addFooterView(footerview);
//                    break;
//                case 1:
//                    Getdata();
//                    isloading=false;
//                    break;
//            }
//            super.handleMessage(msg);
//        }
//
//    }
//    public class ThreadData extends Thread
//    {
//       public void run()
//       {
//           mHandler.sendEmptyMessage(0);
//           try {
//               Thread.sleep(3000);
//           } catch (InterruptedException e) {
//               e.printStackTrace();
//           }
//           Message message=mHandler.obtainMessage(1);
//           mHandler.sendMessage(message);
//           super.run();
//       }
//    }
}