package com.hust.medtech.screen;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.ObservableField;

import com.hust.medtech.R;
import com.hust.medtech.api.MedTedInstance;
import com.hust.medtech.api.request.PaymentRequest;
import com.hust.medtech.api.request.RegisterMedRequest;
import com.hust.medtech.api.response.EmptyResponse;
import com.hust.medtech.api.response.GetNewsResponse;
import com.hust.medtech.api.response.GetTotalPaymentResponse;
import com.hust.medtech.base.BaseActivity;
import com.hust.medtech.base.BaseRecyclerview;
import com.hust.medtech.base.DialogNotify;
import com.hust.medtech.databinding.ActivityPaymentBinding;
import com.hust.medtech.model.Item;
import com.hust.medtech.screen.home.HomeActivity;
import com.hust.medtech.utils.DataUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.momo.momo_partner.AppMoMoLib;
import vn.momo.momo_partner.MoMoParameterNamePayment;

public class PaymentActivity extends BaseActivity<ActivityPaymentBinding, PaymentActivity> {

    public ObservableField<BaseRecyclerview<Item>> mAdapter;
    public ObservableField<String> userName;
    public ObservableField<String> totalPrice;
    private List<Item> data;
    private int amount;
    private int transId;
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "MOMOLB5S20210516 MedTech";
    private String merchantCode = "MOMOLB5S20210516";
    private String merchantNameLabel = "Nhà cung cấp";
    private String description = "Thanh toán viện phí";

    @Override
    public int layoutId() {
        return R.layout.activity_payment;
    }

    @Override
    public void initData() {
        data = new ArrayList<>();
        userName = new ObservableField<>();
        totalPrice = new ObservableField<>();
        mAdapter = new ObservableField<>(new BaseRecyclerview<>(this, data, R.layout.item_payment));
        mBinding.setPresenter(this);
        getDataPayment(DataUtils.getToken(this));
    }

    public void onCancel() {
        finish();
    }

    public void payment() {
        requestPayment();
    }

    public void requestPayment() {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);

        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put(MoMoParameterNamePayment.MERCHANT_NAME, merchantName);
        eventValue.put(MoMoParameterNamePayment.MERCHANT_CODE, merchantCode);
        eventValue.put(MoMoParameterNamePayment.AMOUNT, amount + "");
        eventValue.put(MoMoParameterNamePayment.DESCRIPTION, description);
        //client Optional
        eventValue.put(MoMoParameterNamePayment.FEE, fee);
        eventValue.put(MoMoParameterNamePayment.MERCHANT_NAME_LABEL, merchantNameLabel);
        String string = merchantCode + "-" + UUID.randomUUID().toString();
        eventValue.put(MoMoParameterNamePayment.REQUEST_ID, string);
        Log.d("REQUEST_ID", string);
        eventValue.put(MoMoParameterNamePayment.PARTNER_CODE, "MOMOLB5S20210516");

//        JSONObject objExtraData = new JSONObject();
//        try {
//            objExtraData.put("site_code", "008");
//            objExtraData.put("site_name", "CGV Cresent Mall");
//            objExtraData.put("screen_code", 0);
//            objExtraData.put("screen_name", "Special");
//            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
//            objExtraData.put("movie_format", "2D");
//            objExtraData.put("ticket", "{\"ticket\":{\"01\":{\"type\":\"std\",\"price\":110000,\"qty\":3}}}");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        eventValue.put(MoMoParameterNamePayment.EXTRA_DATA, objExtraData.toString());
        eventValue.put(MoMoParameterNamePayment.REQUEST_TYPE, "payment");
        eventValue.put(MoMoParameterNamePayment.LANGUAGE, "vi");
        eventValue.put(MoMoParameterNamePayment.EXTRA, "");
        //Request momo app
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);
    }

    // lay thong tin gia + ten cac chi muc kham truoc khi thanh toan
    private void getDataPayment(String token) {
        MedTedInstance.getInstance().getTotalPayment(token)
                .enqueue(new Callback<GetTotalPaymentResponse>() {
                    @Override
                    public void onResponse(Call<GetTotalPaymentResponse> call, Response<GetTotalPaymentResponse> response) {
                        if (response.code() == 200) {
                            amount = (int) response.body().getData().getTotalPrice();
                            transId = (int) response.body().getData().getTransMedId();
                            totalPrice.set(response.body().getData().getTotalPrice() + "");
                            userName.set(response.body().getData().getNamePatient());
                            data.addAll(response.body().getData().getItemOfDepts());
                            mAdapter.notifyChange();
                        } else {
                            onFailure(null, null);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetTotalPaymentResponse> call, Throwable t) {
                        Toast.makeText(PaymentActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //goi api thanh toan toi server app
    private void payment(String token, String phone) {
        PaymentRequest request = new PaymentRequest();
        request.setPhoneNumber(phone);
        request.setToken(token);
        request.setPrice(amount);
        request.setTransId(transId);
        MedTedInstance.getInstance().payment(request, DataUtils.getToken(this))
                .enqueue(new Callback<EmptyResponse>() {
                    @Override
                    public void onResponse(Call<EmptyResponse> call, Response<EmptyResponse> response) {
                        if (response.code() == 200) {
                            new DialogNotify(PaymentActivity.this).show();
                        } else {
                            onFailure(null, null);
                        }
                    }

                    @Override
                    public void onFailure(Call<EmptyResponse> call, Throwable t) {
                        Toast.makeText(PaymentActivity.this, "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // xac nhan thanh toan tu app momo, gui ve token cho app client
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if (data != null) {
                if (data.getIntExtra("status", -1) == 0) {
//                    tvMessage.setText("message: " + "Get token " + data.getStringExtra("message"));

                    if (data.getStringExtra("data") != null && !data.getStringExtra("data").equals("")) {
                        // TODO:
                        Toast.makeText(this, "" + data.getStringExtra("data"), Toast.LENGTH_SHORT).show();
                        Log.d("TOKEN", data.getStringExtra("data"));

                        payment(data.getStringExtra("data"), data.getStringExtra("phonenumber"));

                    } else {
//                        tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
                    }
                } else if (data.getIntExtra("status", -1) == 1) {
                    String message = data.getStringExtra("message") != null ? data.getStringExtra("message") : "Thất bại";
//                    tvMessage.setText("message: " + message);
                } else if (data.getIntExtra("status", -1) == 2) {
//                    tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
                } else {
//                    tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
                }
            } else {
//                tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
            }
        } else {
//            tvMessage.setText("message: " + this.getString(R.string.not_receive_info_err));
        }
    }
}
