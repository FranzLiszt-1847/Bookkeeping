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
import com.franzliszt.newbookkeeping.sql.Dao;
import com.franzliszt.newbookkeeping.sql.Record;
import com.franzliszt.newbookkeeping.ui.dashboard.GeneralFragment;
import com.franzliszt.newbookkeeping.utils.KillProcess;

import java.util.ArrayList;
import java.util.List;

public class RecordFragment extends Fragment {
    private View root;
    private LinearLayout OrderNull,bottomView,RecordExit,RecordAdd;
    private View view = null;
    private RecyclerView OrderRecycler;
    private TextView TotalPay,TotalIncome;
    private List<Record> recordList = new ArrayList<>();
    private Dao dao;
    private double totalPay = 0,totalIncome = 0;
    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         root = inflater.inflate(R.layout.activity_main, container, false);
        InitView();
        getData();
        InitRecycler();
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
        dao = new Dao(root.getContext());
    }
    private void InitRecycler(){
        OrderRecycler.setLayoutManager(new LinearLayoutManager(root.getContext()));
        OrderRecycler.setAdapter(new OrderAdapter(recordList));
    }
    /**
     * 获取RecyclerView数据源*/
    private void getData(){
        recordList = dao.QueryAll();
        if (recordList.size() == 0 || recordList == null){
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
            view.setText(SaveDecimal(price)+"");
        }
    }
    private double SaveDecimal(double n){
        return n = ((int)(n*100))/100.0;
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
                    KillProcess.POP(getActivity());
                    break;
            }
        }
    }
    private void ReturnActivity(Class c){
        startActivity(new Intent(root.getContext(),c));
    }


}