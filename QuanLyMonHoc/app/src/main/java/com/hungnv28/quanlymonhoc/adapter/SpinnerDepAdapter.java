package com.hungnv28.quanlymonhoc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hungnv28.quanlymonhoc.model.Department;

import java.util.ArrayList;

public class SpinnerDepAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Department> listData;

    public SpinnerDepAdapter(Context context, ArrayList<Department> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.item_spinner_department, parent, false);
//            holder.txtSpnDepart = convertView.findViewById(R.id.txtSpnDepart);
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            holder.txtSpnDepart = convertView.findViewById(android.R.id.text1);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Department department = listData.get(position);
        holder.txtSpnDepart.setText(department.getCode() + " - " + department.getName());

        return convertView;
    }

    public static class ViewHolder {
        TextView txtSpnDepart;
    }
}
