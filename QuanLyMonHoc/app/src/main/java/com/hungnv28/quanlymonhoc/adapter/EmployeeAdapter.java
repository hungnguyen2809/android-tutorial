package com.hungnv28.quanlymonhoc.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.hungnv28.quanlymonhoc.R;
import com.hungnv28.quanlymonhoc.model.Employee;

import java.util.ArrayList;

public class EmployeeAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Employee> listData;

    public EmployeeAdapter(Context context, ArrayList<Employee> data) {
        this.context = context;
        this.listData = data;
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
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_employee, parent, false);

            holder.txtItemEmployee = convertView.findViewById(R.id.txtItemEmployee);
            holder.txtItemEmpDep = convertView.findViewById(R.id.txtItemEmpDep);
            holder.txtEditEmployee = convertView.findViewById(R.id.txtEditEmployee);
            holder.txtDelEmployee = convertView.findViewById(R.id.txtDelEmployee);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Employee employee = listData.get(position);
        holder.txtItemEmployee.setText(employee.getCode() + " - " + employee.getName());
        holder.txtItemEmpDep.setText(employee.getNameDep());

        return convertView;
    }

    private static class ViewHolder {
        TextView txtItemEmployee, txtItemEmpDep, txtEditEmployee, txtDelEmployee;
    }

    private void onDeleteEmployee(Employee employee) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Bạn có chắc muốn xóa nhân viên: " + employee.getName());
        builder.setPositiveButton("Hủy bỏ", null);
        builder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }
}
