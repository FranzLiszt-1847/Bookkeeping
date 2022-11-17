package com.franzliszt.newbookkeeping.ui.record;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.franzliszt.newbookkeeping.R;
import com.franzliszt.newbookkeeping.activity.DetailedActivity;
import com.franzliszt.newbookkeeping.activity.IncreaseActivity;
import com.franzliszt.newbookkeeping.adapter.OrderAdapter;
import com.franzliszt.newbookkeeping.base.UpdateBean;
import com.franzliszt.newbookkeeping.sql.Dao;
import com.franzliszt.newbookkeeping.sql.Record;
import com.franzliszt.newbookkeeping.ui.dashboard.GeneralFragment;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.we.swipe.helper.WeSwipe;
import cn.we.swipe.helper.WeSwipeHelper;

public class RecordFragment extends Fragment {
    private View root;
    private LinearLayout OrderNull,bottomView,RecordExit,RecordAdd;
    private RecyclerView OrderRecycler;
    private TextView TotalPay,TotalIncome;
    private List<Record> recordList = new ArrayList<>();
    private OrderAdapter adapter;
    private Dao dao;
    private double totalPay = 0,totalIncome = 0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         root = inflater.inflate(R.layout.activity_main, container, false);
        InitView();
        InitRecycler();
        getData();
        Listener();
        return root;
    }
    private void InitView(){
        OrderNull = root.findViewById(R.id.OrderNull);
        bottomView = root.findViewById(R.id.bottomView);
        OrderRecycler = root.findViewById(R.id.OrderRecycler);
        TotalPay = root.findViewById(R.id.TotalPay);
        RecordExit = root.findViewById(R.id.RecordExit);
        RecordAdd = root.findViewById(R.id.RecordAdd);
        TotalIncome = root.findViewById(R.id.TotalIncome);
        dao = new Dao(getContext());
    }
    private void InitRecycler(){
        OrderRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new OrderAdapter(recordList);
        OrderRecycler.setAdapter(adapter);
        WeSwipe.attach(OrderRecycler);

        adapter.setDeleteListener(new OrderAdapter.onDeleteListener() {
            @Override
            public void onClickListener(int pos, Record bean) {
                dao.Delete(bean.getGoodsName());
                recordList.remove(pos);
                adapter.notifyDataSetChanged();
                EventBus.getDefault().postSticky(new UpdateBean(false));
            }
        });
    }
    /**
     * 获取RecyclerView数据源*/
    private void getData(){
        if (dao.QueryAll() == null){
            IsEmpty(true);
            return;
        }
        IsEmpty(false);
        recordList.addAll(dao.QueryAll());
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
        adapter.notifyDataSetChanged();
    }

    private void IsEmpty(TextView view,double price,int flag){
        if (flag == 1 && price == 0){
            view.setText("0.00");
        }else if (flag == 0 && price == 0){
            view.setText("0.00");
        }else {
            view.setText(SaveDecimal(price));
        }
    }

    /**
     * 保留一位小数*/
    private String SaveDecimal(double num){
        return new DecimalFormat("#.0").format(num);
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
    private void Listener(){
        TextListener listener = new TextListener();
        RecordAdd.setOnClickListener(listener);
        RecordExit.setOnClickListener(listener);
    }
    class TextListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.RecordAdd:
                    ReturnActivity(IncreaseActivity.class);
                    break;
                case R.id.RecordExit:
                    getActivity().finish();
                    break;
            }
        }
    }
    private void ReturnActivity(Class c){
        startActivity(new Intent(root.getContext(),c));
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEvent(UpdateBean bean){
        if (bean.getUpdate()){
            recordList.clear();
            totalPay = 0;
            totalIncome = 0;
            getData();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}