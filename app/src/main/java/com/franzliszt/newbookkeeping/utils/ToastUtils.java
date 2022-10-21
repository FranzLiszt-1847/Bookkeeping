package com.franzliszt.newbookkeeping.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.franzliszt.newbookkeeping.R;


public class ToastUtils {
    private static int mLayout[] = {R.layout.toast_success_tips,R.layout.toast_warn_sussess,R.layout.toast_fail_tips};
    private static int mID[] = {R.id.SuccessTips,R.id.WarnTips,R.id.FailTips};
    private Toast toast;
    private Context context;

    public ToastUtils(Context context){
        this.context=context;
    }
    private void InitToast(int layout,int id,String tips){
        toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(layout, null, false);
        TextView text = view.findViewById(id);
        text.setText(tips);
        toast.setView(view);
        toast.setGravity(Gravity.TOP, 0, 0);
    }
    /**
     * 成功Toast*/
    public void ShowSuccess(String tips){
        InitToast(mLayout[0],mID[0],tips);
        toast.show();
    }

    /**
     * 警告Toast*/
    public void ShowWarn(String tips){
        InitToast(mLayout[1],mID[1],tips);
        toast.show();
    }

    /**
     * 失败Toast*/
    public void ShowFail(String tips){
        InitToast(mLayout[2],mID[2],tips);
        toast.show();
    }


}
