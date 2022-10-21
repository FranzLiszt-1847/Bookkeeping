package com.franzliszt.newbookkeeping.utils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.franzliszt.newbookkeeping.R;


public class PoPWindows {
    private Context context;
    private PopupWindow window;
    private View view;
    private boolean flag = true;
    public PoPWindows(Context context){
        this.context = context;
    }
    public void Init(int layout){
        view  = LayoutInflater.from(context).inflate(layout,null,false);
        window = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,true);
        InitPoPUpWindows(window);
    }
    private void InitPoPUpWindows(PopupWindow window){
        window.setFocusable( true ); //获取焦点
        window.setBackgroundDrawable( new BitmapDrawable(  ) );
        window.setOutsideTouchable( true ); //点击外面地方，取消
        window.setTouchable( true ); //允许点击
        window.setAnimationStyle( R.style.PopupWindow ); //设置动画
    }
    private void show(){
       window.showAtLocation(view,Gravity.CENTER,0,0);
    }
    public void DisplayWindows(){
        flag = true;
        if (flag){
            show();
            flag = false;
        }
    }
    public void Dismiss(){
        window.dismiss();
    }
    public PopupWindow getWindow(){
        return this.window;
    }
    public View getView(){
        return this.view;
    }

}
