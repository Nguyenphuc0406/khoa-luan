package com.hust.medtech.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import java.util.List;


/**
 * Created by DacND on 07/08/2019.
 */

public class BaseRecyclerview<T> extends RecyclerView.Adapter<BaseRecyclerview.BaseViewHolder> {
    public static final int TYPE_NO_DATA = 1;
    public static final int TYPE_NORMAL = 2;
    public static final int TYPE_LOADING = 3;

    private List<T> mList;
    private Context mContext;
    @LayoutRes
    private int layoutId;
    @LayoutRes
    private int layoutIdChild;
    private int typeItem;

    private CallBack<T> callBack;

    public void setCallBack(CallBack<T> callBack) {
        this.callBack = callBack;
    }

    public BaseRecyclerview(Context mContext, List<T> lstData, @LayoutRes int layoutId) {
        this.mList = lstData;
        this.mContext = mContext;
        this.layoutId = layoutId;
        if (mList == null) mList = new ArrayList<>();
    }







    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                                layoutId, parent, false);
        return new BaseViewHolder(mBinding);
    }

    public void showLoading(){
        mList.clear();
        typeItem = TYPE_LOADING;
        notifyDataSetChanged();
    }
    public void updateData(List<T> data){
        if(data != null && !data.isEmpty()){
            this.mList = data;
            typeItem = TYPE_NORMAL;
        }else {
            this.mList = data;
            typeItem = TYPE_NO_DATA;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {


        return typeItem;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerview.BaseViewHolder holder, int position) {
        if (!mList.isEmpty()) holder.bind(mList.get(position));

    }

    @Override
    public int getItemCount() {
        if (typeItem == TYPE_LOADING && mList.isEmpty()) {
            return 1;
        } else if (typeItem == TYPE_NO_DATA && mList.isEmpty()) {
            return 1;
        }
        return mList.size();
    }

    public class BaseViewHolder<M extends ViewDataBinding> extends RecyclerView.ViewHolder {
        private M mBinding;
        private T mItem;

        public BaseViewHolder(M itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }

        public void bind(T mItem) {
            this.mItem = mItem;
            mBinding.setVariable(BR.item, mItem);
            mBinding.setVariable(BR.viewHolder, this);


        }

        public void click(){
            if(callBack != null)callBack.onClick(mList.get(getAdapterPosition()));
        }



    }

    public int getCountList() {
        return mList != null ? mList.size() : 0;
    }


}