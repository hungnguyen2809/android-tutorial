package com.hungnv28.todoapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hungnv28.todoapp.R;
import com.hungnv28.todoapp.dao.TodoDAO;
import com.hungnv28.todoapp.model.Todo;

import java.util.ArrayList;

public class TodoRecyclerAdapter extends RecyclerView.Adapter<TodoRecyclerAdapter.MyViewHolder> {
    Context context;
    ArrayList<Todo> data;
    TodoDAO todoDAO;

    public TodoRecyclerAdapter(Context context, ArrayList<Todo> data) {
        this.context = context;
        this.data = data;
        todoDAO = new TodoDAO(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.todo_item_card, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final int positionTmp = position;

        Todo todo = data.get(position);
        holder.checkBox.setText(todo.getContent());
        holder.txtDate.setText(todo.getCreDate());


        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDel(todo);
            }
        });

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFormEdit(todo, positionTmp);
            }
        });

    }

    public void setNotifyDataChange(ArrayList<Todo> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView txtDate;
        ImageView ivDel, ivEdit;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.cbRvcTodo);
            txtDate = itemView.findViewById(R.id.txtRcvDate);
            ivDel = itemView.findViewById(R.id.btnRcvDel);
            ivEdit = itemView.findViewById(R.id.btnRcvEdit);
            cardView = itemView.findViewById(R.id.cardRcvTodo);
        }
    }

    private void showConfirmDel(Todo todo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);


//        builder.setTitle("Confirm");
        builder.setMessage("Do you want delete item?");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean result = todoDAO.delete(todo.getId());
                if (result) {
                    data.clear();
                    data.addAll(todoDAO.getAll());
                    notifyDataSetChanged();
                    Toast.makeText(context, "Delete successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Delete failed", Toast.LENGTH_LONG).show();
                }

                dialog.dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void showFormEdit(Todo todo, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_todo, null);
        builder.setView(view);

        Dialog dialog = builder.create();
        dialog.show();

        Button btnSave = view.findViewById(R.id.btnDLogSave);
        Button btnCancel = view.findViewById(R.id.btnDLogCancel);
        EditText edtTitle = view.findViewById(R.id.edtDLogTitle);
        EditText edtContent = view.findViewById(R.id.edtDLogContent);
        EditText edtDate = view.findViewById(R.id.edtDLogDate);
        EditText edtType = view.findViewById(R.id.edtDLogType);

        edtTitle.setText(todo.getTitle());
        edtContent.setText(todo.getContent());
        edtDate.setText(todo.getCreDate());
        edtType.setText(todo.getType());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTitle.getText().toString();
                String content = edtContent.getText().toString();
                String creDate = edtDate.getText().toString();
                String type = edtType.getText().toString();

                Todo saveTodo = new Todo(todo.getId(), title, content, creDate, type);
                Log.d("UPDATE_TODO", saveTodo.toString());

                boolean result = todoDAO.upgrade(saveTodo);
                if (result) {
                    data.clear();
                    data.addAll(todoDAO.getAll());
                    notifyItemChanged(position);

                    dialog.dismiss();
                    Toast.makeText(context, "Update successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Update failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

