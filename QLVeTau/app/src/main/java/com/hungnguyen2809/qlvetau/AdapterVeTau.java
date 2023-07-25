package com.hungnguyen2809.qlvetau;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterVeTau extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<VeTau> dsVetau;

    public AdapterVeTau(Context context, int layout, ArrayList<VeTau> dsVetau) {
        this.context = context;
        this.layout = layout;
        this.dsVetau = dsVetau;
    }

    @Override
    public int getCount() {
        return dsVetau.size();
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
        TextView txtContent;
        TextView txtStatus;
        TextView txtGia;
    }

    public Filter filterVeTau(){
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();




                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            }
        };
        return filter;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout, null);

            holder = new ViewHolder();
            holder.txtContent = (TextView) view.findViewById(R.id.txtContent);
            holder.txtGia = (TextView) view.findViewById(R.id.txtGia);
            holder.txtStatus = (TextView) view.findViewById(R.id.txtStatus);

            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        VeTau veTau = dsVetau.get(i);
        holder.txtContent.setText(veTau.getGaDi() + " => " + veTau.getGaDen());
        holder.txtGia.setText(veTau.isStatus() ? String.valueOf(veTau.getGia() * 2 * 0.95) : String.valueOf(veTau.getGia() * 1.0));
        holder.txtStatus.setText(veTau.isStatus() ? "Khu hoi" : "Mot chieu");

        return view;
    }
}
