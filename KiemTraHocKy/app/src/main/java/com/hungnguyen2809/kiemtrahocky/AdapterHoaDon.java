package com.hungnguyen2809.kiemtrahocky;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterHoaDon extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<HoaDon> dsHoaDon;

    public AdapterHoaDon(Context context, int layout, ArrayList<HoaDon> dsHoaDon) {
        this.context = context;
        this.layout = layout;
        this.dsHoaDon = dsHoaDon;
    }

    @Override
    public int getCount() {
        return dsHoaDon.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView txtName;
        TextView txtPhong;
        TextView txtPrice;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout, null);

            holder = new ViewHolder();
            holder.txtName = view.findViewById(R.id.txtName);
            holder.txtPhong = view.findViewById(R.id.txtPhong);
            holder.txtPrice = view.findViewById(R.id.txtPrice);

            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        HoaDon hd = dsHoaDon.get(i);
        holder.txtName.setText(hd.getName());
        holder.txtPhong.setText("Phòng: " + hd.getSoPhong());
        holder.txtPrice.setText(String.valueOf(hd.TongTien()));

        return view;
    }

    private String XuLyGia(int gia){
        String res = "";



        return  res;
    }
}
