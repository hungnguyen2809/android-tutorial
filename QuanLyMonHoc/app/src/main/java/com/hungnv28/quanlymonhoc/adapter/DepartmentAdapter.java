package com.hungnv28.quanlymonhoc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hungnv28.quanlymonhoc.R;
import com.hungnv28.quanlymonhoc.model.Department;

import java.util.ArrayList;

public class DepartmentAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Department> listData;


    public DepartmentAdapter(Context context, ArrayList<Department> listData) {
        this.listData = listData;
        this.context = context;
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
            convertView = inflater.inflate(R.layout.item_department, parent, false);

            holder.txtDepartment = convertView.findViewById(R.id.txtItemDepartment);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Department department = listData.get(position);
        holder.txtDepartment.setText(department.getCode() + " - " + department.getName());

        return convertView;
    }

    public static class ViewHolder {
        private TextView txtDepartment;
    }
}
