package com.hungnv28.todoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hungnv28.todoapp.R;
import com.hungnv28.todoapp.model.Todo;

import java.util.ArrayList;

public class TodoAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Todo> data;

    public TodoAdapter(Context context, ArrayList<Todo> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
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
//            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.todo_item_adapter, null);
            holder.txtTitle = convertView.findViewById(R.id.txtTitle);
            holder.txtContent = convertView.findViewById(R.id.txtContent);
            holder.txtCreDate = convertView.findViewById(R.id.txtDate);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Todo todo = data.get(position);
        holder.txtTitle.setText(todo.getTitle());
        holder.txtContent.setText(todo.getContent());
        holder.txtCreDate.setText(todo.getCreDate());

        return convertView;
    }

    public class ViewHolder {
        TextView txtTitle, txtContent, txtCreDate;
    }

    public void setNotifyDataChange(ArrayList<Todo> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
