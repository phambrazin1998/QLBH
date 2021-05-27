package com.example.qlbh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qlbh.R;
import com.example.qlbh.model.loaisp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class loaispadapter extends BaseAdapter {
    ArrayList<loaisp> arrayListloaisp;
    Context context;

    public loaispadapter(ArrayList<loaisp> arrayListloaisp, Context context) {
        this.arrayListloaisp = arrayListloaisp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListloaisp.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListloaisp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
 public class  ViewHolder{
TextView txtloaisp;
ImageView imgviewloaisp;

 }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null)
        {
            viewHolder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.dong_listview_loaisp,null);
            viewHolder.txtloaisp= convertView.findViewById(R.id.txtloaisp);
            viewHolder.imgviewloaisp= convertView.findViewById(R.id.imgviewloaisp);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        loaisp loaisp=(loaisp) getItem(position);
        viewHolder.txtloaisp.setText(loaisp.getTenloaisp());
        Picasso.with(context).load(loaisp.getHinhloaisp())
                .placeholder(R.drawable.qua_tao)
                .error(R.drawable.qua_dao)
                .into(viewHolder.imgviewloaisp);

        return convertView;
    }
}
