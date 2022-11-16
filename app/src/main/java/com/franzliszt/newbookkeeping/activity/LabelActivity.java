package com.franzliszt.newbookkeeping.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.franzliszt.newbookkeeping.R;

import com.franzliszt.newbookkeeping.utils.SP;
import com.franzliszt.newbookkeeping.utils.StatusBarUtils;
import com.franzliszt.newbookkeeping.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 类型一：日用百货
 * 类型二：文化休闲
 * 类型三：交通出行
 * 类型四：生活服务
 * 类型五：服装装扮
 * 类型六：餐饮美食
 * 类型七：数码电器
 * 类型八：其他标签*/
public class LabelActivity extends AppCompatActivity {
    private TextView EditFunc;
    private LinearLayout type_1,type_2,type_3,type_4,type_5,type_6,type_7,type_8;
    private static final String TAG = "LabelActivity";
    private int TotalNum = 0;
    private ToastUtils toastUtils;
    private boolean[] b_select = {false,false,false,false,false,false,false,false};
    private String[] s_select = {"日用百货","文化休闲","交通出行","生活服务","服装装扮","餐饮美食","数码电器","其他标签"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setStatusBarHide(getWindow());
        StatusBarUtils.setStatusBarLightMode(getWindow());
        StatusBarUtils.setStatusBarColor(getWindow(),LabelActivity.this, R.color.grey);
        setContentView(R.layout.activity_label);
        InitView();
        Listener();
    }
    private void InitView(){
        EditFunc = findViewById(R.id.EditFunc);
        EditFunc.setText("标签");
        type_1 = findViewById(R.id.type_1);
        type_2 = findViewById(R.id.type_2);
        type_3 = findViewById(R.id.type_3);
        type_4 = findViewById(R.id.type_4);
        type_5 = findViewById(R.id.type_5);
        type_6 = findViewById(R.id.type_6);
        type_7 = findViewById(R.id.type_7);
        type_8 = findViewById(R.id.type_8);

        toastUtils = new ToastUtils(LabelActivity.this);
    }
    private void setBG(LinearLayout layout,int index){
        int tag = (int)layout.getTag();
        if (tag % 2 == 0) {
            layout.setBackground(getResources().getDrawable(R.drawable.blue_radius_bg));
            TotalNum++;
            b_select[index-1] = true;
        }
        else {
            layout.setBackground(getResources().getDrawable(R.drawable.grey_radius_bg));
            TotalNum--;
            b_select[index-1] = false;
        }
    }
    private void setTag(LinearLayout layout,int tag){
        tag++;
        layout.setTag(tag);
    }
    private int getTag(LinearLayout layout){
        Object tag = layout.getTag();
        if (tag == null)return 1;
        return (int) tag;
    }
    private String selectTag(){
        for (int i = 0; i < 8; i++) {
            if ( b_select[i]){
                return s_select[i];
            }
        }
    return null;
    }
    private void Listener(){
        TypeListener listener = new TypeListener();
        type_1.setOnClickListener(listener);
        type_2.setOnClickListener(listener);
        type_3.setOnClickListener(listener);
        type_4.setOnClickListener(listener);
        type_5.setOnClickListener(listener);
        type_6.setOnClickListener(listener);
        type_7.setOnClickListener(listener);
        type_8.setOnClickListener(listener);
    }
    class TypeListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.type_1:
                    setTag(type_1,getTag(type_1));
                    setBG(type_1,1);
                    break;
                case R.id.type_2:
                    setTag(type_2,getTag(type_2));
                    setBG(type_2,2);
                    break;
                case R.id.type_3:
                    setTag(type_3,getTag(type_3));
                    setBG(type_3,3);
                    break;
                case R.id.type_4:
                    setTag(type_4,getTag(type_4));
                    setBG(type_4,4);
                    break;
                case R.id.type_5:
                    setTag(type_5,getTag(type_5));
                    setBG(type_5,5);
                    break;
                case R.id.type_6:
                    setTag(type_6,getTag(type_6));
                    setBG(type_6,6);
                    break;
                case R.id.type_7:
                    setTag(type_7,getTag(type_7));
                    setBG(type_7,7);
                    break;
                case R.id.type_8:
                    setTag(type_8,getTag(type_8));
                    setBG(type_8,8);
                    break;
            }
        }
    }
    public void ExitEdit(View view){
        //KillProcess.POP(LabelActivity.this);
        finish();
    }
    public void SaveMessage(View view){
        if (TotalNum > 1){
            toastUtils.ShowFail("选择标签数量不能超过一");
        }else if (TotalNum <= 0){
            toastUtils.ShowFail("选择标签数量不能少于一");
        }else {
            toastUtils.ShowSuccess("success");
            String tag = selectTag();
            Log.d(TAG,"TAG="+tag);
            SP.getInstance().PutData(LabelActivity.this,"Label",tag);
            finish();
        }
    }
}