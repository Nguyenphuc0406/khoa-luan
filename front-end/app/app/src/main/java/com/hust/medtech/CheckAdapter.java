package com.hust.medtech;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.hust.medtech.model.DanhMuc;

import java.util.List;

public class CheckAdapter extends RecyclerView.Adapter<CheckAdapter.ViewHolder> {
   private Context context;
   private List<DanhMuc> lstDanhMuc;

    public CheckAdapter(Context context, List<DanhMuc> lstDanhMuc) {
        this.context = context;
        this.lstDanhMuc = lstDanhMuc;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // gán view
        View view = LayoutInflater.from(context).inflate(R.layout.item_check_box, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Gán dữ liêuk
        DanhMuc sanPham = lstDanhMuc.get(position);
        holder.checkBox.setText(sanPham.getName());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            lstDanhMuc.get(position).setCheck(isChecked);

        });

    }

    public List<DanhMuc> danhMucs(){
        return lstDanhMuc;
    }
    @Override
    public int getItemCount() {
        return lstDanhMuc.size(); // trả item tại vị trí postion
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatCheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view
            checkBox = itemView.findViewById(R.id.check_1);

        }
    }
}
