package com.hungnguyen2809.appqlstudent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Observable;

public class AdapterStudent extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Student> dsStudent;
    private ArrayList<Student> backupdata;

    public AdapterStudent(Context context, int layout, ArrayList<Student> dsStudent) {
        this.context = context;
        this.layout = layout;
        this.dsStudent = dsStudent;
    }

    public Filter getFilter(){
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (backupdata == null){
                    backupdata = new ArrayList<>(dsStudent);
                }
                if (constraint == null || constraint.length() == 0){
                    filterResults.values = backupdata;
                    filterResults.count = backupdata.size();
                }
                else {
                    ArrayList<Student> newData = new ArrayList<>();
                    for (Student vl : dsStudent){
                        if (vl.getSbd().toLowerCase().contains(constraint.toString().toLowerCase())){
                            newData.add(vl);
                        }
                    }
                    filterResults.values = newData;
                    filterResults.count = newData.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                ArrayList<Student> tmp = (ArrayList<Student>) results.values;
                dsStudent.clear();
                for (Student vl : tmp){
                    dsStudent.add(vl);
                }
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    @Override
    public int getCount() {
        return dsStudent.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView txtSDB, txtName, txtTongDiem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout, null);
            holder = new ViewHolder();

            holder.txtSDB = (TextView) convertView.findViewById(R.id.textViewSBD);
            holder.txtName = (TextView) convertView.findViewById(R.id.textViewName);
            holder.txtTongDiem = (TextView) convertView.findViewById(R.id.textViewTongDiem);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Student student = dsStudent.get(position);
        holder.txtSDB.setText(student.getSbd());
        holder.txtName.setText(student.getName());
        holder.txtTongDiem.setText(student.TongDiem()+"");

        return convertView;
    }
}
