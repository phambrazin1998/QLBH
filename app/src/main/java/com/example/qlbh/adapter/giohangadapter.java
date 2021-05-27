package com.example.qlbh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qlbh.R;
import com.example.qlbh.activity.Giohang;
import com.example.qlbh.activity.MainActivity;
import com.example.qlbh.model.giohang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class giohangadapter extends BaseAdapter {
    Context context;

    public giohangadapter(Context context, ArrayList<giohang> arraygiohang) {
        this.context = context;
        this.arraygiohang = arraygiohang;
    }

    ArrayList<giohang> arraygiohang;

    @Override
    public int getCount() {
        return arraygiohang.size();
    }

    @Override
    public Object getItem(int position) {
        return arraygiohang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class ViewHolder
    {
      public TextView txttengiohang,txtgiagiohang;
      public Button btncong,btntru,btnsoluong;
      public ImageView imggiohang;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null)
        {
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.dong_giohang,null);
            viewHolder.txttengiohang=convertView.findViewById(R.id.txttengiohang);
            viewHolder.txtgiagiohang=convertView.findViewById(R.id.txtgiagiohang);
            viewHolder.imggiohang=convertView.findViewById(R.id.imggiohang);
            viewHolder.btncong=convertView.findViewById(R.id.btncong);
            viewHolder.btntru=convertView.findViewById(R.id.btntru);
            viewHolder.btnsoluong=convertView.findViewById(R.id.btnsoluong);
            convertView.setTag(viewHolder);
        }
        else
        {
          viewHolder=(ViewHolder) convertView.getTag();
        }
        giohang giohang=(giohang) getItem(position);
        viewHolder.txttengiohang.setText(giohang.getTensp());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");// chỉnh kiểu dữ liệu giá sp.
        viewHolder.txtgiagiohang.setText(decimalFormat.format(giohang.getGia())+" vnd");
         Picasso.with(context).load(giohang.getHinhanh())
                .placeholder(R.drawable.qua_tao)
                .error(R.drawable.qua_dao)
                .into(viewHolder.imggiohang);
         viewHolder.btnsoluong.setText(giohang.getSoluong() + "");
         int sl= Integer.parseInt(viewHolder.btnsoluong.getText().toString());
         if(sl>=10)
         {
             viewHolder.btncong.setVisibility(View.INVISIBLE);
             viewHolder.btntru.setVisibility(View.VISIBLE);
         }
         else if (sl<=1)
         {
             viewHolder.btntru.setVisibility(View.INVISIBLE);
         }
         else if (sl>=1)
         {
             viewHolder.btncong.setVisibility(View.VISIBLE);
             viewHolder.btntru.setVisibility(View.VISIBLE);
         }

        ViewHolder finalViewHolder = viewHolder;
        viewHolder.btncong.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 int slmoinhat=Integer.parseInt(finalViewHolder.btnsoluong.getText().toString())+1;
                 int slhientai= MainActivity.manggiohang.get(position).getSoluong();
                 long giahientai=MainActivity.manggiohang.get(position).getGia();
                 MainActivity.manggiohang.get(position).setSoluong(slmoinhat);
                 long giamoinhat=(giahientai*slmoinhat)/slhientai;//giá hiện tại nhân số lượng mới nhất chia số lượng hiện tại
                 MainActivity.manggiohang.get(position).setGia(giamoinhat);
                 DecimalFormat decimalFormat=new DecimalFormat("###,###,###");// chỉnh kiểu dữ liệu giá sp.
                 finalViewHolder.txtgiagiohang.setText(decimalFormat.format(giamoinhat)+" vnd");
                 Giohang.EvenUtil();
                 if(slmoinhat>9)
                 {
                     finalViewHolder.btncong.setVisibility(View.INVISIBLE);
                     finalViewHolder.btntru.setVisibility(View.VISIBLE);
                     finalViewHolder.btnsoluong.setText(String.valueOf(slmoinhat));
                 }
                 else
                 {
                     finalViewHolder.btncong.setVisibility(View.VISIBLE);
                     finalViewHolder.btntru.setVisibility(View.VISIBLE);
                     finalViewHolder.btnsoluong.setText(String.valueOf(slmoinhat));
                 }
             }
         });
        ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.btntru.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 int slmoinhat=Integer.parseInt(finalViewHolder1.btnsoluong.getText().toString())-1;
                 int slhientai= MainActivity.manggiohang.get(position).getSoluong();
                 long giahientai=MainActivity.manggiohang.get(position).getGia();
                 MainActivity.manggiohang.get(position).setSoluong(slmoinhat);
                 long giamoinhat=(giahientai*slmoinhat)/slhientai;//giá hiện tại nhân số lượng mới nhất chia số lượng hiện tại
                 MainActivity.manggiohang.get(position).setGia(giamoinhat);
                 DecimalFormat decimalFormat=new DecimalFormat("###,###,###");// chỉnh kiểu dữ liệu giá sp.
                 finalViewHolder1.txtgiagiohang.setText(decimalFormat.format(giamoinhat)+" vnd");
                 Giohang.EvenUtil();
                 if(slmoinhat<2)
                 {
                     finalViewHolder1.btncong.setVisibility(View.VISIBLE);
                     finalViewHolder1.btntru.setVisibility(View.INVISIBLE);
                     finalViewHolder1.btnsoluong.setText(String.valueOf(slmoinhat));
                 }
                 else
                 {
                     finalViewHolder1.btncong.setVisibility(View.VISIBLE);
                     finalViewHolder1.btntru.setVisibility(View.VISIBLE);
                     finalViewHolder1.btnsoluong.setText(String.valueOf(slmoinhat));
                 }
             }
         });
        return convertView;
    }
}
