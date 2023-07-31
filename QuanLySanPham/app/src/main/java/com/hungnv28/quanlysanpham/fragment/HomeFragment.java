package com.hungnv28.quanlysanpham.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hungnv28.quanlysanpham.R;
import com.hungnv28.quanlysanpham.adapter.HomeProductAdapter;
import com.hungnv28.quanlysanpham.dao.ProductCategoryDAO;
import com.hungnv28.quanlysanpham.dao.ProductDAO;
import com.hungnv28.quanlysanpham.model.Product;
import com.hungnv28.quanlysanpham.model.ProductCategory;
import com.hungnv28.quanlysanpham.utils.Utils;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private ProductDAO productDAO;
    private ProductCategoryDAO categoryDAO;
    private ArrayList<Product> listProduct;

    private HomeProductAdapter adapter;
    private RecyclerView rcvProduct;
    private FloatingActionButton btnAddProduct;

    private final int PERMISSION_CODE = 101;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rcvProduct = view.findViewById(R.id.homeListProduct);
        btnAddProduct = view.findViewById(R.id.homeBtnAddProduct);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productDAO = new ProductDAO(getContext());
        categoryDAO = new ProductCategoryDAO(getContext());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvProduct.setLayoutManager(layoutManager);

        listProduct = productDAO.getAll();
        adapter = new HomeProductAdapter(getContext(), listProduct);
        rcvProduct.setAdapter(adapter);

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogInsertProduct();
            }
        });

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == PERMISSION_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                openGallery();
//            } else {
//                Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    private void dialogInsertProduct() {
        if (getContext() == null) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_modify_product, null, false);
        builder.setView(view);

        Dialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimationSlide);
        dialog.show();

        Spinner spinner = view.findViewById(R.id.spinnerProductModifyCate);
        EditText edtCode = view.findViewById(R.id.edtProductModifyCode);
        EditText edtName = view.findViewById(R.id.edtProductModifyName);
        EditText edtPrice = view.findViewById(R.id.edtProductModifyPrice);
        EditText edtQuantity = view.findViewById(R.id.edtProductModifyQuantity);
        ImageView ivImage = view.findViewById(R.id.ivProductModifyImage);
        Button btnCancel = view.findViewById(R.id.btnProductModifyCancel);
        Button btnSave = view.findViewById(R.id.btnProductModifySave);

        ArrayList<String> listCateName = new ArrayList<>();
        ArrayList<ProductCategory> categoryList = categoryDAO.getAll();
        for (ProductCategory cate : categoryList) {
            listCateName.add(cate.getName());
        }
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getContext(),
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item, listCateName);
        categoryAdapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(categoryAdapter);

        final int[] categoryIdSelected = {-1};
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryIdSelected[0] = categoryList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() == null) return;
                Utils.requestPermissionFragment(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE, new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        if (result) {
                            openGallery();
                        } else {
                            Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
//                String image = edtImage.getText().toString();

                if (categoryIdSelected[0] == -1) {
                    Toast.makeText(getContext(), "Vui lòng chọn loại hàng hóa", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (code.trim().isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập mã sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (name.trim().isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (quantity.trim().isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (price.trim().isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập giá sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                }

                Product product = new Product(code.toUpperCase(), name, Long.parseLong(price),
                        Integer.parseInt(quantity), null, categoryIdSelected[0]);
                boolean check = productDAO.insertProduct(product);

                if (check) {
                    Toast.makeText(getContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    listProduct = productDAO.getAll();
                    adapter.setNotifyDataChanged(listProduct);
                    dialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openGallery() {

    }
}
