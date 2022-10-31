package com.franzliszt.newbookkeeping.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.franzliszt.newbookkeeping.R;
import com.franzliszt.newbookkeeping.activity.DetailedActivity;
import com.franzliszt.newbookkeeping.adapter.BarAdapter;
import com.franzliszt.newbookkeeping.adapter.RankAdapter;
import com.franzliszt.newbookkeeping.base.RankList;
import com.franzliszt.newbookkeeping.base.ViewBar;
import com.franzliszt.newbookkeeping.sql.Dao;
import com.franzliszt.newbookkeeping.sql.Record;
import com.franzliszt.newbookkeeping.utils.KillProcess;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GeneralFragment extends Fragment {
    private View root;
    private LinearLayout Exit;
    private RecyclerView PayTypeRecycler,RankRecycler;
    private TextView Save,PayItem,PaySum;
    private List<Record> recordList = new ArrayList<>();
    private List<ViewBar> barList = new ArrayList<>();
    private List<RankList> rankListList = new ArrayList<>();
    private Dao dao;
    private String[] s_select = {"日用百货","文化休闲","交通出行","生活服务","服装装扮","餐饮美食","数码电器","其他标签"};
    private double[] d_price;
    private double TotalPrice = 0;
    private WindowManager manager;
    private int width = 0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         root = inflater.inflate(R.layout.fragment_general, container, false);
        Init();
        Listener();
        getPrice();
        getData();
        getRankings();
        InitRecycler();
        return root;
    }
    private void Init(){
        Exit = root.findViewById(R.id.BtnExit);
        Save = root.findViewById(R.id.BtnSave);
        PayTypeRecycler = root.findViewById(R.id.PayTypeRecycler);
        PayItem = root.findViewById(R.id.payItem);
        PaySum = root.findViewById(R.id.paySum);
        RankRecycler = root.findViewById(R.id.RankRecycler);
        TextView EditFunc = root.findViewById(R.id.EditFunc);
        EditFunc.setText("详细概览");
        Save.setText("概览");
        dao = new Dao(root.getContext());
        recordList = dao.QueryAll();
        manager = requireActivity().getWindowManager();
        width = manager.getDefaultDisplay().getWidth();
    }
    private void InitRecycler(){
        PayTypeRecycler.setLayoutManager(new LinearLayoutManager(root.getContext()));
        PayTypeRecycler.setAdapter(new BarAdapter(barList));

        RankRecycler.setLayoutManager(new LinearLayoutManager(root.getContext()));
        RankRecycler.setAdapter(new RankAdapter(rankListList));

        PayItem.setText("共计"+recordList.size()+"笔  合计");
        PaySum.setText("¥"+SaveDecimal(TotalPrice));
    }
    /*
    * toatl:2030.99*/
    private void getData(){
        if (TotalPrice <= 0)return;
        for (int i = 0; i < d_price.length; i++) {
            if (d_price[i] == 0)continue;
            int n = (int) (d_price[i] / TotalPrice * width);
            double t = SaveDecimal(d_price[i] / TotalPrice * 100);
            barList.add(new ViewBar(s_select[i]+"   ",t+"%","¥"+d_price[i],n,10));
        }
    }
    /**
     * 保留2位小数*/
    private double SaveDecimal(double n){
        return n = ((int)(n*100))/100.0;
    }
    /**
     * 获取单个标签总价以及所有商品总价*/
    private void getPrice(){
        if (recordList.size() == 0 || recordList == null)return;
        d_price = new double[s_select.length];
        for (int i = 0; i < recordList.size(); i++) {
            for (int j = 0; j < s_select.length; j++) {
                if (recordList.get(i).getLabel().equals(s_select[j])){
                    d_price[j] += Double.parseDouble(recordList.get(i).getGoodsPrice());
                    TotalPrice += Double.parseDouble(recordList.get(i).getGoodsPrice());
                    break;
                }
            }
        }
    }
    /**
     * 选出账单支出前三甲*/
    private void getRankings(){
        if (recordList == null || recordList.size() == 0)return;
        double maxPrice = -32768,midPrice = -32768,lowPrice = -32768;
        int maxIndex = -1,midIndex = -1,lowIndex = -1;
        for (int i = 0; i < recordList.size(); i++) {
            double price = Double.parseDouble(recordList.get(i).getGoodsPrice());
            if ( price > maxPrice){
                lowPrice = midPrice;
                lowIndex = midIndex;

                midPrice = maxPrice;
                midIndex = maxIndex;

                maxPrice = price;
                maxIndex = i;
            }
            if (price < maxPrice && price > midPrice){
                lowPrice = midPrice;
                lowIndex = midIndex;

                midPrice = price;
                midIndex = i;
            }
            if (price < maxPrice && price < midPrice && price > lowPrice){
                lowPrice = price;
                lowIndex = i;
            }
        }
        int[] poi = {maxIndex,midIndex,lowIndex};
        for (int i = 0; i < 3; i++) {
            if (poi[i] == -1)continue;
            rankListList.add(new RankList(i+1,recordList.get(poi[i]).getLabel(),recordList.get(poi[i]).getGoodsName(),recordList.get(poi[i]).getGoodsPrice(),recordList.get(poi[i]).getType()));
        }
    }
    private void Listener(){
        TextListener listener = new TextListener();
        Exit.setOnClickListener(listener);
        Save.setOnClickListener(listener);
    }
    class TextListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.BtnExit:
                    KillProcess.POP(getActivity());
                    break;
                case R.id.BtnSave:
                    ReturnActivity(DetailedActivity.class);
                    break;
            }
        }
    }
 private void ReturnActivity(Class activity){
        startActivity(new Intent(getActivity(),activity));
 }
}