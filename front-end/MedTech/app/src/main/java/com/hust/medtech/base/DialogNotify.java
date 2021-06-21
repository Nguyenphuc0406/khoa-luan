package com.hust.medtech.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hust.medtech.R;

public class DialogNotify  extends Dialog {
    protected Context mContext;
    private String title;

    private CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    //    protected T mBinding;
    public enum OPTION {
        TOP, CENTER, BOTTOM,FULL
    }

    private OPTION type;



    public DialogNotify(@NonNull Context context,String title) {
        super(context);
        this.mContext = context;
        this.title = title;
        this.type = OPTION.CENTER;
    }
    public DialogNotify(@NonNull Context context,String title,CallBack callBack) {
        super(context);
        this.mContext = context;
        this.title = title;
        this.type = OPTION.CENTER;
        this.callBack = callBack;
    }
    public DialogNotify(@NonNull Context context) {
        super(context);
        this.mContext = context;
        this.title = title;
        this.type = OPTION.CENTER;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(idLayoutRes());


        if (type == OPTION.TOP) {
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            getWindow().setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,

                    WindowManager.LayoutParams.WRAP_CONTENT
            );
            getWindow().setGravity(Gravity.TOP);
        } else if (type == OPTION.BOTTOM) {
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            getWindow().setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
            );
            getWindow().setGravity(Gravity.BOTTOM);


        } else if (type == OPTION.FULL) {
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            getWindow().setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT
            );
            getWindow().setGravity(Gravity.CENTER);
        }
        else {
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            getWindow().setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,

                    WindowManager.LayoutParams.WRAP_CONTENT
            );
            getWindow().setGravity(Gravity.CENTER);

        }
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        initView();

    }


    protected  void initView(){
        TextView tv = findViewById(R.id.message);
        if(!TextUtils.isEmpty(title)){
            tv.setText(title);
        }
        findViewById(R.id.accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callBack != null){
                    callBack.onConfirm();
                }else {
                    ((BaseActivity) mContext).finish();
                }
            }
        });
    }


    protected  int idLayoutRes(){
        return R.layout.dilog_notify;
    }

    public interface CallBack{
        void onConfirm();
    }


}
