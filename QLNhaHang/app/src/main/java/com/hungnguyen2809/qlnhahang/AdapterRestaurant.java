package com.hungnguyen2809.qlnhahang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterRestaurant extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Restaurant> dsRestaurant;
    private ArrayList<Restaurant> dataBackup;

    public AdapterRestaurant(Context context, int layout, ArrayList<Restaurant> dsRestaurant) {
        this.context = context;
        this.layout = layout;
        this.dsRestaurant = dsRestaurant;
    }

    public Filter filterRestaurant(){
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if (dataBackup == null){
                    dataBackup = new ArrayList<>(dsRestaurant);
                }
                if (charSequence.length() == 0 || charSequence == null){
                    filterResults.values = dataBackup;
                    filterResults.count = dataBackup.size();
                }
                else {
                    ArrayList<Restaurant> listData = new ArrayList<>();
                    for (Restaurant items : dataBackup){
                        if (items.getAddress().trim().toLowerCase().contains(charSequence.toString().trim().toLowerCase())){
                            listData.add(items);
                        }
                    }
                    filterResults.values = listData;
                    filterResults.count = listData.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                ArrayList<Restaurant> dataTemp = (ArrayList<Restaurant>) filterResults.values;
                dsRestaurant.clear();
                for (Restaurant items : dataTemp){
                    dsRestaurant.add(items);
                }
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    @Override
    public int getCount() {
        return dsRestaurant.size();
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
        TextView txtPoint;
        TextView txtAddress;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout, null);

            holder = new ViewHolder();
            holder.txtName = view.findViewById(R.id.txtName);
            holder.txtAddress = view.findViewById(R.id.txtAddress);
            holder.txtPoint = view.findViewById(R.id.txtPoint);

            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        Restaurant restaurant = dsRestaurant.get(position);
        holder.txtName.setText(restaurant.getName());
        holder.txtAddress.setText(restaurant.getAddress());
        holder.txtPoint.setText(restaurant.getPoint() + "");

        return view;
    }
}
