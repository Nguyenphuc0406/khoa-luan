package com.hust.medtech;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.hust.medtech.model.DanhMuc;
import com.hust.medtech.model.OKItem;
import com.hust.medtech.request.YeuCauKhamBenhRequest;
import com.hust.medtech.service.ServiceFetchData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormRegisterActivity  extends AppCompatActivity {

    private TextInputEditText username;
    private AppCompatCheckBox check1,check2,check3,check4;
    private RecyclerView recyclerView;
    private AppCompatButton btnConfirm;
    private CheckAdapter checkAdapter;
    private List<DanhMuc> listSanPham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = findViewById(R.id.edt_user_name);
        check1 = findViewById(R.id.check_1);
        check2 = findViewById(R.id.check_2);
        check3 = findViewById(R.id.check_3);
        check4 = findViewById(R.id.check_4);
        btnConfirm = findViewById(R.id.btn_confirm);
        recyclerView = findViewById(R.id.recycler);
        listSanPham = new ArrayList<>();;
        btnConfirm.setOnClickListener(v ->{
            onConfirm();
        });
        getDanhSachDanhMuc();




    }

    private void getDanhSachDanhMuc() {
        ServiceFetchData.getInstance().getDanhSachDanhMuc().enqueue(new Callback<List<DanhMuc>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<DanhMuc>> call, Response<List<DanhMuc>> response) {
                listSanPham.addAll(response.body());
                checkAdapter=new CheckAdapter(FormRegisterActivity.this,listSanPham);
                recyclerView.setLayoutManager(new LinearLayoutManager(FormRegisterActivity.this));
                recyclerView.setAdapter(checkAdapter);
                checkAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<DanhMuc>> call, Throwable t) {
                Toast.makeText(FormRegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onConfirm() {
        if(TextUtils.isEmpty(username.getText().toString())){
            Toast.makeText(this, "Vui long nhap ho ten", Toast.LENGTH_SHORT).show();
            return;
        }
        List<DanhMuc> danhMucs = checkAdapter.danhMucs();
        boolean isCheck = false;
        for (DanhMuc danhMuc : danhMucs){
            if(danhMuc.isCheck()){
                isCheck = true;
                break;
            }
        }
        if(!isCheck){
            Toast.makeText(this, "Vui long chon cac muc can kham", Toast.LENGTH_SHORT).show();
            return;
        }
        YeuCauKhamBenhRequest request = new YeuCauKhamBenhRequest();
        List<Integer> lsItem = new ArrayList<>();
        for (DanhMuc danhMuc : danhMucs){
            if(danhMuc.isCheck()){
                lsItem.add(danhMuc.getId());
            }
        }
        request.setUsername(username.getText().toString());
        request.setDanhmucKhamId(lsItem);
        ServiceFetchData.getInstance().getDanhSachDanhMuc(request)
                .enqueue(new Callback<OKItem>() {
                    @Override
                    public void onResponse(Call<OKItem> call, Response<OKItem> response) {
                        if(response.code() == 200){
                            Toast.makeText(FormRegisterActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(FormRegisterActivity.this, "Tao yeu cau that bai", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<OKItem> call, Throwable t) {
                        Toast.makeText(FormRegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
