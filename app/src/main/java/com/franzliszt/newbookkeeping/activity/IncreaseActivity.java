package com.franzliszt.newbookkeeping.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.franzliszt.newbookkeeping.R;
import com.franzliszt.newbookkeeping.sql.Dao;
import com.franzliszt.newbookkeeping.sql.Record;
import com.franzliszt.newbookkeeping.utils.DateUtils;
import com.franzliszt.newbookkeeping.utils.KillProcess;
import com.franzliszt.newbookkeeping.utils.PoPWindows;
import com.franzliszt.newbookkeeping.utils.SP;
import com.franzliszt.newbookkeeping.utils.StatusBarUtils;
import com.franzliszt.newbookkeeping.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
/**
 *  支出：1
 *  收入：0*/
public class IncreaseActivity extends AppCompatActivity {
    private Switch DateSwitch, TimeSwitch;
    private TextView SelectDate, SelectTime, TypeText, TextLabel, NameNum, PriceNum;
    private EditText GoodsName, GoodsPrice;
    private ImageView NameDelete, PriceDelete;
    private LinearLayout DateLayout, TimeLayout;
    private CalendarView mCalendarView;
    private TimePicker mTimePicker;
    private static final String TAG = "IncreaseActivity";
    private String CNDate;
    private PoPWindows poPWindows;
    private RadioGroup mGroup;
    private RadioButton mIncome, mPay;
    private ToastUtils toastUtils;
    private Dao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setStatusBarHide(getWindow());
        StatusBarUtils.setStatusBarLightMode(getWindow());
        StatusBarUtils.setStatusBarColor(getWindow(), IncreaseActivity.this, R.color.grey);
        setContentView(R.layout.activity_increase);
        InitView();
        Listener();
        getDate();
        getTime();
        Receive();
        getContent();
    }

    /**
     * 控件定义
     * PopupWindows初始化
     */
    private void InitView() {
        DateSwitch = findViewById(R.id.DateSwitch);
        TimeSwitch = findViewById(R.id.TimeSwitch);
        DateLayout = findViewById(R.id.DateLayout);
        TimeLayout = findViewById(R.id.TimeLayout);
        mCalendarView = findViewById(R.id.mCalendarView);
        mTimePicker = findViewById(R.id.mTimePicker);
        SelectDate = findViewById(R.id.SelectDate);
        SelectTime = findViewById(R.id.SelectTime);
        TypeText = findViewById(R.id.TypeText);
        TextLabel = findViewById(R.id.TextLabel);
        NameNum = findViewById(R.id.NameNum);
        PriceNum = findViewById(R.id.PriceNum);
        GoodsName = findViewById(R.id.GoodsName);
        GoodsPrice = findViewById(R.id.GoodsPrice);
        NameDelete = findViewById(R.id.NameDelete);
        PriceDelete = findViewById(R.id.PriceDelete);

        poPWindows = new PoPWindows(IncreaseActivity.this);
        poPWindows.Init(R.layout.goods_type);
        mGroup = poPWindows.getView().findViewById(R.id.mGroup);
        mIncome = poPWindows.getView().findViewById(R.id.mIncome);
        mPay = poPWindows.getView().findViewById(R.id.mPay);

        toastUtils = new ToastUtils(IncreaseActivity.this);
        dao = new Dao(IncreaseActivity.this);

        KillProcess.PUSH(IncreaseActivity.class,IncreaseActivity.this);

        SelectTime.setText(DateUtils.getCurrentTime());
    }

    /**
     * 内部类点击事件监听
     */
    private void Listener() {
        SwitchListener listener = new SwitchListener();
        DateSwitch.setOnCheckedChangeListener(listener);
        TimeSwitch.setOnCheckedChangeListener(listener);

        ImageListener imageListener = new ImageListener();
        NameDelete.setOnClickListener(imageListener);
        PriceDelete.setOnClickListener(imageListener);
    }

    /**
     * 监听用户输入的商品名称和商品价格，并显示字符长度
     */
    private void getContent() {
        GoodsName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                NameNum.setText(s.length() + "");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        GoodsPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                PriceNum.setText(s.length() + "");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 显示选择的年月日，并将选择的年月日转为星期
     */
    private void getDate() {
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                CNDate = year + "年" + month + "月" + dayOfMonth + "日";
                String date = year + "-" + month + "-" + dayOfMonth;
                Log.d(TAG, "date=" + date);
                Log.d(TAG, "CNdate=" + CNDate);
                //string日期转date日期，在转为星期
                String week = DateUtils.getWeekOfDate(DateUtils.getStringToDate(date));
                Log.d(TAG, "week=" + week);
                SelectDate.setText(CNDate + " " + week);
            }
        });
    }

    /**
     * 显示选择的时间
     */
    private void getTime() {
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                String time = hourOfDay + ":" + minute;
                SelectTime.setText(time);
                Log.d(TAG, time);
            }
        });
    }

    /**
     * PopupWindows弹窗，收入和支出单选
     */
    public void SelectType(View view) {
        poPWindows.DisplayWindows();
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.mIncome:
                        TypeText.setText(mIncome.getText());
                        break;
                    case R.id.mPay:
                        TypeText.setText(mPay.getText());
                        break;
                }
                poPWindows.Dismiss();
            }
        });
    }

    public void SelectLabel(View view) {
        startActivity(new Intent(IncreaseActivity.this, LabelActivity.class));
    }

    /**
     * SWitch点击事件监听
     */
    class SwitchListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.DateSwitch:
                    if (isChecked)
                        DateLayout.setVisibility(View.VISIBLE);
                    else
                        DateLayout.setVisibility(View.GONE);
                    break;
                case R.id.TimeSwitch:
                    if (isChecked)
                        TimeLayout.setVisibility(View.VISIBLE);
                    else
                        TimeLayout.setVisibility(View.GONE);
                    break;
            }
        }
    }

    /**
     * 一键删除
     */
    class ImageListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.NameDelete:
                    GoodsName.setText("");
                    NameNum.setText("0");
                    break;
                case R.id.PriceDelete:
                    GoodsPrice.setText("");
                    PriceNum.setText("0");
            }
        }
    }

    /**
     * 利用SharedPreferences接受来自标签页面的数据，数据由用户选择
     */
    private void Receive() {
        SP sp = SP.getInstance();
        String tag = (String) sp.GetData(IncreaseActivity.this, "Label", "");
        if (TextUtils.isEmpty(tag)) {
            Log.d(TAG, "null");
        } else {
            TextLabel.setText(tag);
            Log.d(TAG, tag);
        }
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onReceive(TextClass textClass){
//        //runOnUiThread(()->{
//            TextLabel.setText(textClass.getStr());
//       // });
//        Log.d(TAG,textClass.getStr());
//    }
//   private void RegisterEvent(){
//       EventBus.getDefault().register(this);
//       Log.d(TAG,"Registered");
//   }

    /**
     * 账单记录保存到SQLite中
     */
    public void SaveMessage(View view) {
        String date = SelectDate.getText().toString().trim();
        String time = SelectTime.getText().toString().trim();
        String type = TypeText.getText().toString();
        String label = TextLabel.getText().toString();
        String name = GoodsName.getText().toString();
        String price = GoodsPrice.getText().toString().trim();
        if (TextUtils.isEmpty(type) || type.equals("支出or收入")){
            toastUtils.ShowFail("类型错误！");
            return;
        }
        if (TextUtils.isEmpty(label) || label.equals("暂未选择")){
            toastUtils.ShowFail("标签错误!");
            return;
        }
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(price)){
            toastUtils.ShowFail("商品信息或者商品价格格式!");
            return;
        }
        int t = type.equals("支出") ? 1 : 0;
        Record record = new Record(date,time,t,label,name,price);
        int flag = dao.Insert(record);
        if (flag == 1){
            toastUtils.ShowSuccess("保存成功!");
        }else {
            toastUtils.ShowFail("保存失败!");
        }

    }
    public void ExitEdit(View view){
        KillProcess.POP(IncreaseActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}