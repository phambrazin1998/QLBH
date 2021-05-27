package com.example.qlbh.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.qlbh.ultil.checkinternetconnection;
import com.example.qlbh.R;
import com.example.qlbh.ultil.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Thanhtoan extends AppCompatActivity {
EditText txttenkh,txtsdt,txtdiachi;
Button btnxacnhan,btnquaylai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanhtoan);
        Anhxa();
        btnquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(checkinternetconnection.haveNetworkConnection(getApplicationContext()))
        {
            EventButton();
        }else
        {
            checkinternetconnection.ShowToast_Short(getApplicationContext(),"Kiểm tra lại kết nối");
        }
    }

    private void EventButton() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenkh=txttenkh.getText().toString().trim();
                String sdt=txtsdt.getText().toString().trim();
                String diachi=txtdiachi.getText().toString().trim();
                if(tenkh.length()>0 &&sdt.length()>0 &&diachi.length()>0)
                {
                    RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, server.duongdankh, new Response.Listener<String>() {
                        @Override
                        public void onResponse( String madonhang) {
                          Log.d("madonhang",madonhang);
                          if(Integer.parseInt(madonhang)>0)
                          {
                              RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                              StringRequest request=new StringRequest(Request.Method.POST, server.duongdandonhang, new Response.Listener<String>() {
                                  @Override
                                  public void onResponse(String response) {
                                  if(response.equals("1"))
                                  {
                                      MainActivity.manggiohang.clear();
                                      checkinternetconnection.ShowToast_Short(getApplicationContext(),"Đã thanh toán thành công");
                                      Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                      startActivity(intent);
                                      checkinternetconnection.ShowToast_Short(getApplicationContext(),"Mời quý khách tiếp tục mua hàng");
                                  }
                                  else
                                  {
                                      checkinternetconnection.ShowToast_Short(getApplicationContext(),"Dữ liệu giỏ hàng bị lỗi, mời thử lại");
                                  }
                                  }
                              }, new Response.ErrorListener() {
                                  @Override
                                  public void onErrorResponse(VolleyError error) {

                                  }
                              }){
                                  @Override
                                  protected Map<String, String> getParams() throws AuthFailureError {
                                      JSONArray jsonArray =new JSONArray();
                                      for(int i=0;i<MainActivity.manggiohang.size();i++)
                                      {
                                          JSONObject jsonObject=new JSONObject();
                                          try {
                                              jsonObject.put("madonhang",madonhang);
                                              jsonObject.put("masp",MainActivity.manggiohang.get(i).getId());
                                              jsonObject.put("tensp",MainActivity.manggiohang.get(i).getTensp());
                                              jsonObject.put("giasp",MainActivity.manggiohang.get(i).getGia());
                                              jsonObject.put("soluong",MainActivity.manggiohang.get(i).getSoluong());
                                          } catch (JSONException e) {
                                              e.printStackTrace();
                                          }
                                          jsonArray.put(jsonObject);
                                      }
                                      HashMap<String,String> hashMap =new HashMap<String,String>();
                                      hashMap.put("json",jsonArray.toString());
                                      return hashMap;
                                  }
                              };
                              queue.add(request);
                          }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap=new HashMap<String,String>();
                            hashMap.put("tenkh",String.valueOf(tenkh));
                            hashMap.put("sdt",String.valueOf(sdt));
                            hashMap.put("diachi",String.valueOf(diachi));
                            return hashMap;
                        }
                    };
                        requestQueue.add(stringRequest);
                }else
                {
                    checkinternetconnection.ShowToast_Short(getApplicationContext(),"Kiểm tra lại dữ liệu");
                }
            }
        });
    }
    private void Anhxa() {
        txttenkh = (EditText) findViewById(R.id.txttenkh);
        txtsdt = (EditText) findViewById(R.id.txtsdt);
        txtdiachi = (EditText) findViewById(R.id.txtdiachi);
        btnxacnhan = (Button) findViewById(R.id.btnxacnhan);
        btnquaylai = (Button) findViewById(R.id.btnquaylai);
    }
}