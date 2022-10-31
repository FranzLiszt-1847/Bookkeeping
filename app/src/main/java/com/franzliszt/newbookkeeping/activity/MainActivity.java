package com.franzliszt.newbookkeeping.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.franzliszt.newbookkeeping.R;
import com.franzliszt.newbookkeeping.adapter.OrderAdapter;
import com.franzliszt.newbookkeeping.sql.Dao;
import com.franzliszt.newbookkeeping.sql.Record;
import com.franzliszt.newbookkeeping.utils.KillProcess;
import com.franzliszt.newbookkeeping.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LinearLayout OrderNull,bottomView;
    private View view = null;
    private RecyclerView OrderRecycler;
    private TextView TotalPay,TotalIncome;
    private List<Record> recordList = new ArrayList<>();
    private Dao dao;
    private double totalPay = 0,totalIncome = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setStatusBarHide(getWindow());
        setContentView(R.layout.activity_main);
        InitView();
        getData();
        InitRecycler();
    }
    private void InitView(){
        OrderNull = findViewById(R.id.OrderNull);
        bottomView = findViewById(R.id.bottomView);
        OrderRecycler = findViewById(R.id.OrderRecycler);
        TotalPay = findViewById(R.id.TotalPay);
        TotalIncome = findViewById(R.id.TotalIncome);
        KillProcess.PUSH(MainActivity.class,MainActivity.this);
        dao = new Dao(this);
    }
    private void InitRecycler(){
        OrderRecycler.setLayoutManager(new LinearLayoutManager(this));
        OrderRecycler.setAdapter(new OrderAdapter(recordList));
    }
    /**
     * 获取RecyclerView数据源*/
    private void getData(){
        recordList = dao.QueryAll();
        if (recordList == null || recordList.size() == 0){
            IsEmpty(true);
            return;
        }
        for (int i = 0; i < recordList.size(); i++) {
            /**
             * 1为支出
             * 0为收入*/
            if (recordList.get(i).getType() == 1){
                totalPay += Double.parseDouble(recordList.get(i).getGoodsPrice());
            }else {
                totalIncome += Double.parseDouble(recordList.get(i).getGoodsPrice());
            }
        }
        IsEmpty(TotalPay,totalPay,1);
        IsEmpty(TotalIncome,totalIncome,0);

    }
    private void IsEmpty(TextView view,double price,int flag){
        if (flag == 1 && price == 0){
            view.setText("0.00");
        }else if (flag == 0 && price == 0){
            view.setText("0.00");
        }else {
            view.setText(price+"");
        }
    }
   /**
    * 判断数据库内容是否为空
    * 为空显示404界面*/
    private void IsEmpty(boolean flag){
        if (flag){
            OrderNull.setVisibility(View.VISIBLE);
            bottomView.setVisibility(View.GONE);
        }else {
            OrderNull.setVisibility(View.GONE);
            bottomView.setVisibility(View.VISIBLE);
        }
    }
    public void Exit(View view){
        KillProcess.POP(MainActivity.this);
    }

    public void Increase(View view){
        ReturnClass(IncreaseActivity.class);
    }
    private void ReturnClass(Class c){
        startActivity(new Intent(MainActivity.this,c));
    }

}