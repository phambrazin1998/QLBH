package com.example.qlbh.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.qlbh.R;
import com.example.qlbh.model.chitietsp;
import com.example.qlbh.model.giohang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class thongtinsp extends AppCompatActivity {
Toolbar toolbarthongtinsp;
ImageView imgthongtinsp;
TextView txttensp,txtgiasp,txtmota;
Spinner spinner;
Button btnthem;
    int id=0;
    String ten=" ";
    String mota=" ";
    String hinhanh=" ";
    int gia = 0;
    int idloaisp=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsp);
        Anhxa();
        ActionBar();
        Getthongtin();
        CatchSpinner();
        Buttonthem();
    }

    private void Buttonthem() {
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.manggiohang.size()>0)
                {
                  int sl=Integer.parseInt(spinner.getSelectedItem().toString());
                  boolean exists=false;
                  for(int i=0;i<MainActivity.manggiohang.size();i++)
                  {
                      if(MainActivity.manggiohang.get(i).getId()==id)
                      {
                          MainActivity.manggiohang.get(i).setSoluong(MainActivity.manggiohang.get(i).getSoluong()+sl);
                          if(MainActivity.manggiohang.get(i).getSoluong()>=10)
                          {
                              MainActivity.manggiohang.get(i).setSoluong(10);
                          }
                          MainActivity.manggiohang.get(i).setGia(gia*MainActivity.manggiohang.get(i).getSoluong());
                          exists=true;
                      }
                  }
                  if(exists==false)
                  {
                      int soluong=Integer.parseInt(spinner.getSelectedItem().toString());
                      long tongtien=soluong*gia;
                      MainActivity.manggiohang.add(new giohang(id,ten,tongtien,hinhanh,soluong));
                  }
                }
                else
                {
                    int soluong=Integer.parseInt(spinner.getSelectedItem().toString());
                    long tongtien=soluong*gia;
                    MainActivity.manggiohang.add(new giohang(id,ten,tongtien,hinhanh,soluong));
                }
                Intent intent=new Intent(getApplicationContext(),Giohang.class);
                startActivity(intent);
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
    private void CatchSpinner() {
        Integer[] soluong=new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter=new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item,soluong );
        spinner.setAdapter(arrayAdapter);
    }

    @SuppressLint("SetTextI18n")
    private void Getthongtin() {

        chitietsp chitietsp= (chitietsp) getIntent().getSerializableExtra("thongtinsp");
        id=chitietsp.getId();
        ten=chitietsp.getTensp();
        gia=chitietsp.getGia();
        hinhanh=chitietsp.getHinhanh();
        mota=chitietsp.getMota();
        idloaisp=chitietsp.getIdloaisp();
        txttensp.setText(ten);
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        txtgiasp.setText("Giá: "+ decimalFormat.format(gia)+" vnd");
        txtmota.setText(mota);
        Picasso.with(getApplicationContext()).load(hinhanh)
                .placeholder(R.drawable.qua_tao)
                .error(R.drawable.qua_dao)
                .into(imgthongtinsp);
    }

    private void ActionBar() {
        setActionBar(toolbarthongtinsp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarthongtinsp.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbarthongtinsp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }

    private void Anhxa() {
        toolbarthongtinsp=(Toolbar)   findViewById(R.id.toolbarthongtinsp);
        imgthongtinsp    =(ImageView) findViewById(R.id.imgthongtinsp);
        txttensp         =(TextView)  findViewById(R.id.txttensp);
        txtgiasp         =(TextView)  findViewById(R.id.txtgiasp);
        txtmota          =(TextView)  findViewById(R.id.txtmota);
        spinner          =(Spinner)   findViewById(R.id.spinner);
        btnthem          =(Button)    findViewById(R.id.btnthem);
    }
}