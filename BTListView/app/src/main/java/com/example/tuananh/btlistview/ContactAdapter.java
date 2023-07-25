package com.example.tuananh.btlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private int layout;
    private ArrayList<Contact> data;
    private ArrayList<Contact> backupData;

    public ContactAdapter(Context context, int layout, ArrayList<Contact> data) {
        this.context = context;
        this.layout = layout;
        this.data = data;
    }

    public void setData(ArrayList<Contact> contacts) {
        this.data = contacts;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
        public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(backupData == null){
                    backupData = new ArrayList<>(data);
                }
                if(constraint == null || constraint.length() == 0){
                    filterResults.values = backupData;
                    filterResults.count = backupData.size();
                }
                else{
                    ArrayList<Contact> newData = new ArrayList<>();
                    for(Contact c:data){
                        if(c.getName().toLowerCase().contains(constraint.toString().toLowerCase())){
                            newData.add(c);
                        }
                    }
                    filterResults.values = newData;
                    filterResults.count = newData.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                ArrayList<Contact> tmp = (ArrayList<Contact>) results.values;
                data.clear();
                for(Contact c:tmp){
                    data.add(c);
                }
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    private class ViewHolder{
        TextView txtId,txtName,txtPhone;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder.txtId = convertView.findViewById(R.id.textViewId);
            holder.txtName = convertView.findViewById(R.id.textViewName);
            holder.txtPhone = convertView.findViewById(R.id.textViewPhone);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Contact contact = data.get(position);
        holder.txtId.setText(contact.getId()+"");
        holder.txtName.setText(contact.getName());
        holder.txtPhone.setText(contact.getPhone());

        return convertView;
    }
}
