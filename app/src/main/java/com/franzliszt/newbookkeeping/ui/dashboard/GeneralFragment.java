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
import com.franzliszt.newbookkeeping.base.UpdateBean;
import com.franzliszt.newbookkeeping.base.ViewBar;
import com.franzliszt.newbookkeeping.sql.Dao;
import com.franzliszt.newbookkeeping.sql.Record;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GeneralFragment extends Fragment {
    private View root;
    private LinearLayout Exit;
    private RecyclerView PayTypeRecycler, RankRecycler;
    private TextView Save, PayItem, PaySum;

    private List<Record> recordList = new ArrayList<>();
    private List<ViewBar> barList = new ArrayList<>();
    private List<RankList> rankListList = new ArrayList<>();
    private BarAdapter barAdapter;
    private RankAdapter rankAdapter;


    private Dao dao;
    private String[] s_select = {"日用百货", "文化休闲", "交通出行", "生活服务", "服装装扮", "餐饮美食", "数码电器", "其他标签"};
    private double[] d_price;
    private double TotalPrice = 0;
    private WindowManager manager;
    private int width = 0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_general, container, false);
        Init();
        InitRecycler();
        loadData();
        Listener();
        return root;
    }

    private void Init() {
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

    private void InitRecycler() {
        PayTypeRecycler.setLayoutManager(new LinearLayoutManager(root.getContext()));
        barAdapter = new BarAdapter(barList);
        PayTypeRecycler.setAdapter(barAdapter);

        RankRecycler.setLayoutManager(new LinearLayoutManager(root.getContext()));
        rankAdapter = new RankAdapter(rankListList);
        RankRecycler.setAdapter(rankAdapter);
    }

    private void loadData(){
        getPrice();
        getData();
        getRankings();
        calculate();
    }

    private void calculate(){
        if (recordList == null || recordList.size() == 0) {
            PayItem.setText("共计0笔  合计");
            PaySum.setText("¥0.00");
        } else {
            PayItem.setText("共计" + recordList.size() + "笔  合计");
            PaySum.setText("¥" + SaveDecimal(TotalPrice));
        }
    }

    /*
     * toatl:2030.99*/
    private void getData() {
        if (TotalPrice <= 0) return;
        for (int i = 0; i < d_price.length; i++) {
            if (d_price[i] == 0) continue;
            int n = (int) (d_price[i] / TotalPrice * width);
            String t = SaveDecimal(d_price[i] / TotalPrice * 100) + "%";
            barList.add(new ViewBar(s_select[i] + "   ", t, "¥" + d_price[i], n, 10));
        }
       barAdapter.notifyDataSetChanged();
    }

    /**
     * 保留2位小数
     */
    private String SaveDecimal(double num){
        return new DecimalFormat("#.00").format(num);
    }

    /**
     * 获取单个标签总价以及所有商品总价
     */
    private void getPrice() {
        if (recordList == null || recordList.size() == 0) return;
        d_price = new double[s_select.length];
        for (int i = 0; i < recordList.size(); i++) {
            for (int j = 0; j < s_select.length; j++) {
                if (recordList.get(i).getLabel().equals(s_select[j])) {
                    d_price[j] += Double.parseDouble(recordList.get(i).getGoodsPrice());
                    TotalPrice += Double.parseDouble(recordList.get(i).getGoodsPrice());
                    break;
                }
            }
        }
    }

    /**
     * 选出账单支出前三甲
     */
    private void getRankings() {
        if (recordList == null || recordList.size() == 0) return;
        sort();
        int size = 0;
        if (recordList.size() < 3){
            size = recordList.size();
        }
        for (int i = 0; i < size; i++) {
            rankListList.add(new RankList(i + 1, recordList.get(i).getLabel(), recordList.get(i).getGoodsName(), recordList.get(i).getGoodsPrice(), recordList.get(i).getType()));
        }
        rankAdapter.notifyDataSetChanged();
    }

    private void sort(){
        if (recordList.size() <= 1)return;
        for (int i = 0; i < recordList.size() - 1; i++) {
            int index = i;
            for (int j = i+1; j < recordList.size(); j++) {
                if (Double.parseDouble(recordList.get(index).getGoodsPrice()) < Double.parseDouble(recordList.get(j).getGoodsPrice())){
                    index = j;
                }
            }
            if (i != index){
                Record record = recordList.get(index);
                recordList.set(index,recordList.get(i));
                recordList.set(i,record);
            }
        }
    }

    private void Listener() {
        TextListener listener = new TextListener();
        Exit.setOnClickListener(listener);
        Save.setOnClickListener(listener);
    }

    class TextListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.BtnExit:
                    getActivity().finish();
                    break;
                case R.id.BtnSave:
                    ReturnActivity(DetailedActivity.class);
                    break;
            }
        }
    }

    private void ReturnActivity(Class activity) {
        startActivity(new Intent(getActivity(), activity));
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEvent(UpdateBean bean){
        if (recordList == null){
            recordList = new ArrayList<>();
        }
        recordList.clear();
        barList.clear();
        rankListList.clear();
        TotalPrice = 0;
        recordList = dao.QueryAll();
        loadData();
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