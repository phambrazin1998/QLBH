package com.example.qlbh.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlbh.R;
import com.example.qlbh.activity.thongtinsp;
import com.example.qlbh.model.chitietsp;
import com.example.qlbh.ultil.checkinternetconnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class chitietspadapter extends RecyclerView.Adapter<chitietspadapter.ItemHolder> {
    Context context;
    ArrayList<chitietsp> arraysp;

    public chitietspadapter(Context context, ArrayList<chitietsp> arraysp) {
        this.context = context;
        this.arraysp = arraysp;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_spmoinhat,null);
        ItemHolder itemHolder=new ItemHolder(v);
        return itemHolder;
    }   

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
     chitietsp chitietsp=arraysp.get(position);
     holder.tensp.setText(chitietsp.getTensp());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");// chỉnh kiểu dữ liệu giá sp.
    holder.gia.setText("Giá: " + decimalFormat.format(chitietsp.getGia())+ " đ" );
        Picasso.with(context).load(chitietsp.getHinhanh())
                .placeholder(R.drawable.qua_tao)
                .error(R.drawable.qua_dao)
                .into(holder.hinhanh);
    }

    @Override
    public int getItemCount() {

        return arraysp.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView hinhanh;
        public TextView tensp,gia;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            hinhanh = (ImageView) itemView.findViewById(R.id.imgsanpham);
            tensp = (TextView) itemView.findViewById(R.id.txttensp);
            gia = (TextView) itemView.findViewById(R.id.txtgiasp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, thongtinsp.class);
                    intent.putExtra("thongtinsp",arraysp.get(getPosition()));
                    checkinternetconnection.ShowToast_Short(context,arraysp.get(getPosition()).tensp);
                    context.startActivity(intent);
                }
            });
        }
    }
}
