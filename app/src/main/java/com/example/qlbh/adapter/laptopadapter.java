package com.example.qlbh.adapter;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.qlbh.R;
import com.example.qlbh.model.chitietsp;
import com.squareup.picasso.Picasso;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class laptopadapter extends BaseAdapter {
    Context context;
    ArrayList<chitietsp> arraydt;
    public laptopadapter(Context context, ArrayList<chitietsp> arraydt) {
        this.context = context;
        this.arraydt = arraydt;
    }
    @Override
    public int getCount() {

        return arraydt.size();
    }

    @Override
    public Object getItem(int position) {
        return arraydt.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txttenlaptop,txtgialaptop,txtmota;
        public ImageView imglaptop;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        laptopadapter.ViewHolder viewHolder = null;
        if(convertView==null)
        {
            viewHolder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.dong_laptop,null);
            viewHolder.txttenlaptop=(TextView) convertView.findViewById(R.id.txttenlaptop);
            viewHolder.txtgialaptop=(TextView) convertView.findViewById(R.id.txtgialaptop);
            viewHolder.txtmota=(TextView) convertView.findViewById(R.id.txtmota);
            viewHolder.imglaptop=(ImageView) convertView.findViewById(R.id.imglaptop);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder=(laptopadapter.ViewHolder) convertView.getTag();
        }
        chitietsp chitietsp=(chitietsp) getItem(position);
        viewHolder.txttenlaptop.setText(chitietsp.getTensp());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");// chỉnh kiểu dữ liệu giá sp.
        viewHolder.txtgialaptop.setText("Giá: " + decimalFormat.format(chitietsp.getGia())+ " đ" );
        viewHolder.txtmota.setMaxLines(2);
        viewHolder.txtmota.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmota.setText(chitietsp.getMota());
        Picasso.with(context).load(chitietsp.getHinhanh())
                .placeholder(R.drawable.qua_tao)
                .error(R.drawable.qua_dao)
                .into(viewHolder.imglaptop);
        return convertView;
    }
}
