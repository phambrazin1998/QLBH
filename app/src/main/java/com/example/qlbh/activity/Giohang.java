package com.example.qlbh.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.qlbh.R;
import com.example.qlbh.adapter.giohangadapter;
import com.example.qlbh.ultil.checkinternetconnection;

import java.text.DecimalFormat;

public class Giohang extends AppCompatActivity {
ListView lstgiohang;
TextView txtthongbao;
static TextView txttongtien;
Button btnthanhtoan,btntieptuc;
Toolbar toolbargiohang;
giohangadapter giohangadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        Anhxa();
        Actionbar();
        CheckData();
        EvenUtil();//Tổng tiền
        Xoasanpham();
        Button();//Thanh toán hoặc tiếp tục mua

    }

    private void Button() {
        btntieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.manggiohang.size()>0)
                {
                    Intent intent=new Intent(getApplicationContext(),Thanhtoan.class);
                    startActivity(intent);
                }
                else
                {
                    checkinternetconnection.ShowToast_Short(getApplicationContext(),"Giỏ hàng đang trống, không thể thanh toán !!");
                }
            }
        });
    }


    private void Xoasanpham() {
        lstgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Giohang.this);
                builder.setTitle("Xóa sản phẩm ?");
                builder.setMessage("Bạn có muốn xóa sản phẩm này không ?");
                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        giohangadapter.notifyDataSetChanged();
                        EvenUtil();
                    }
                });
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.manggiohang.size()<=0)
                        {
                            txtthongbao.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            MainActivity.manggiohang.remove(position);
                            giohangadapter.notifyDataSetChanged();
                            EvenUtil();
                            if(MainActivity.manggiohang.size()<=0)
                            {
                                txtthongbao.setVisibility(View.VISIBLE);
                            }else
                            {
                                txtthongbao.setVisibility(View.INVISIBLE);
                                giohangadapter.notifyDataSetChanged();
                                EvenUtil();
                            }
                        }
                    }
                });
                builder.show();
                return false;
            }
        });
    }

    public static void EvenUtil() {
        long tongtien=0;
        for(int i=0;i<MainActivity.manggiohang.size();i++)
        {
            tongtien+=MainActivity.manggiohang.get(i).getGia();
        }
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        txttongtien.setText(decimalFormat.format(tongtien)+" vnd");
    }

    private void CheckData() {
        if(MainActivity.manggiohang.size()<=0)
        {
            giohangadapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE);
            lstgiohang.setVisibility(View.INVISIBLE);
        }
        else
        {
            giohangadapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            lstgiohang.setVisibility(View.VISIBLE);
        }
    }

    private void Actionbar() {
        setActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        lstgiohang =(ListView) findViewById(R.id.lstgiohang);
        txtthongbao=(TextView) findViewById(R.id.txtthongbao);
        txttongtien=(TextView) findViewById(R.id.txttongtien);
        btnthanhtoan=(Button) findViewById(R.id.btnthanhtoan);
        btntieptuc=(Button) findViewById(R.id.btntieptuc);
        toolbargiohang=(Toolbar) findViewById(R.id.toolbargiohang);
        giohangadapter=new giohangadapter(Giohang.this,MainActivity.manggiohang);
        lstgiohang.setAdapter(giohangadapter);
    }
}