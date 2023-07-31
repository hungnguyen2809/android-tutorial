package com.hungnv28.quanlysanpham.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hungnv28.quanlysanpham.R;
import com.hungnv28.quanlysanpham.dao.ProductCategoryDAO;
import com.hungnv28.quanlysanpham.dao.ProductDAO;
import com.hungnv28.quanlysanpham.model.Product;
import com.hungnv28.quanlysanpham.model.ProductCategory;
import com.hungnv28.quanlysanpham.utils.Utils;

import java.util.ArrayList;

public class HomeProductAdapter extends RecyclerView.Adapter<HomeProductAdapter.MyViewHolder> {
    private final Context context;
    private ArrayList<Product> data;
    private final ProductDAO productDAO;
    private final ProductCategoryDAO categoryDAO;
    private ImageView ivImageProduct;

    public HomeProductAdapter(Context context, ArrayList<Product> data) {
        this.context = context;
        this.data = data;
        this.productDAO = new ProductDAO(context);
        this.categoryDAO = new ProductCategoryDAO(context);
    }

    public void setNotifyDataChanged(ArrayList<Product> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_home_product, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = data.get(position);

        holder.txtName.setText(product.getName());
        holder.txtPrice.setText(Utils.formatNumber(product.getPrice()) + " VNĐ");
        holder.txtQuantity.setText("SL: " + Utils.formatNumber(product.getQuantity()));

        if (product.getImageUrl() != null && !product.getImageUrl().isEmpty()) {
            Glide.with(context).load(product.getImageUrl()).into(holder.ivImageProduct);
        }

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDeleteProduct(product);
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogUpdateProduct(product);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName, txtPrice, txtQuantity, btnEdit, btnDelete;
        public ImageView ivImageProduct;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtHomeItemNameProduct);
            txtPrice = itemView.findViewById(R.id.txtHomeItemPriceProduct);
            txtQuantity = itemView.findViewById(R.id.txtHomeItemQuantityProduct);
            btnEdit = itemView.findViewById(R.id.btnHomeItemProductEdit);
            btnDelete = itemView.findViewById(R.id.btnHomeItemProductDel);
            ivImageProduct = itemView.findViewById(R.id.ivHomeItemImageProduct);
        }
    }

    private void dialogDeleteProduct(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Bạn có chắc muốn xóa " + product.getName() + " ?");
        builder.setNegativeButton("Hủy bỏ", null);
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean check = productDAO.deleteProduct(product.getId());
                if (check) {
                    data.clear();
                    data.addAll(productDAO.getAll());
                    notifyDataSetChanged();
                    Toast.makeText(context, "Xóa sản phẩm thàng công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.show();
    }

    private int findIndex(ArrayList<ProductCategory> listCate, int cateId) {
        int id = -1;
        for (int i = 0; i <= listCate.size(); i++) {
            if (listCate.get(i).getId() == cateId) {
                id = i;
                break;
            }
        }
        return id;
    }

    private void dialogUpdateProduct(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_modify_product, null, false);
        builder.setView(view);

        Dialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimationSlide);
        dialog.show();

        TextView txtTitle = view.findViewById(R.id.txtProductModifyTitle);
        Spinner spinner = view.findViewById(R.id.spinnerProductModifyCate);
        EditText edtCode = view.findViewById(R.id.edtProductModifyCode);
        EditText edtName = view.findViewById(R.id.edtProductModifyName);
        EditText edtPrice = view.findViewById(R.id.edtProductModifyPrice);
        EditText edtQuantity = view.findViewById(R.id.edtProductModifyQuantity);
        Button btnCancel = view.findViewById(R.id.btnProductModifyCancel);
        Button btnSave = view.findViewById(R.id.btnProductModifySave);
        ivImageProduct = view.findViewById(R.id.ivProductModifyImage);

        txtTitle.setText("Cập nhật sản phẩm");
        btnSave.setText("Cập nhật");
        edtCode.setText(product.getCode());
        edtName.setText(product.getName());
        edtPrice.setText(Utils.formatNumber2(product.getPrice()));
        edtQuantity.setText(Utils.formatNumber2(product.getQuantity()));

        ArrayList<String> listCateName = new ArrayList<>();
        ArrayList<ProductCategory> categoryList = categoryDAO.getAll();
        for (ProductCategory cate : categoryList) {
            listCateName.add(cate.getName());
        }
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(context,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item, listCateName);
        categoryAdapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(categoryAdapter);
        spinner.setSelection(this.findIndex(categoryList, product.getCategoryId()));

        final int[] categoryIdSelected = {product.getCategoryId()};
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryIdSelected[0] = categoryList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = edtCode.getText().toString();
                String name = edtName.getText().toString();
                String quantity = edtQuantity.getText().toString();
                String price = edtPrice.getText().toString();

                if (categoryIdSelected[0] == -1) {
                    Toast.makeText(context, "Vui lòng chọn loại hàng hóa", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (code.trim().isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập mã sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (name.trim().isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (quantity.trim().isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (price.trim().isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập giá sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                }

                Product productUpdate = new Product(product.getId(), code.toUpperCase(), name, Long.parseLong(price),
                        Integer.parseInt(quantity), "", categoryIdSelected[0]);
                boolean check = productDAO.updateProduct(productUpdate);

                if (check) {
                    Toast.makeText(context, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    data.clear();
                    data.addAll(productDAO.getAll());
                    notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Cập nhật sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
