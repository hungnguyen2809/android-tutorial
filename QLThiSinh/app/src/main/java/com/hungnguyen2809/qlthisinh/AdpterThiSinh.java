package com.hungnguyen2809.qlthisinh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdpterThiSinh extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<ThiSinh> dsThiSinh;

    public AdpterThiSinh(Context context, int layout, ArrayList<ThiSinh> dsThiSinh) {
        this.context = context;
        this.layout = layout;
        this.dsThiSinh = dsThiSinh;
    }

    @Override
    public int getCount() {
        return dsThiSinh.size();
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
        TextView txtSBD, txtName, txtPoint;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout, null);

            holder = new ViewHolder();
            holder.txtSBD = (TextView) view.findViewById(R.id.txtSBD);
            holder.txtName = (TextView) view.findViewById(R.id.txtName);
            holder.txtPoint = (TextView) view.findViewById(R.id.txtPoint);

            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        ThiSinh ts = dsThiSinh.get(i);
        holder.txtName.setText(ts.getName());
        holder.txtSBD.setText(ts.getSBD());
        holder.txtPoint.setText(String.valueOf((double) Math.round(ts.TongDiem() * 100) / 100));

        return view;
    }
}
