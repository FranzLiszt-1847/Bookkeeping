package com.franzliszt.newbookkeeping.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.franzliszt.newbookkeeping.AAChartCoreLib.AAChartCreator.AAChartModel;
import com.franzliszt.newbookkeeping.AAChartCoreLib.AAChartCreator.AAChartView;
import com.franzliszt.newbookkeeping.AAChartCoreLib.AAChartCreator.AASeriesElement;
import com.franzliszt.newbookkeeping.AAChartCoreLib.AAChartEnum.AAChartSymbolStyleType;
import com.franzliszt.newbookkeeping.AAChartCoreLib.AAChartEnum.AAChartType;
import com.franzliszt.newbookkeeping.AAChartCoreLib.AAChartEnum.AAChartZoomType;
import com.franzliszt.newbookkeeping.R;
import com.franzliszt.newbookkeeping.base.ViewBar;
import com.franzliszt.newbookkeeping.sql.Dao;
import com.franzliszt.newbookkeeping.sql.Record;
import com.franzliszt.newbookkeeping.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.PointValue;

public class DetailedActivity extends AppCompatActivity {
    private AAChartView lineChartView,mapChartView;
    private Dao dao;
    private List<Record> recordList = new ArrayList<>();
    private String[] s_select = {"日用百货","文化休闲","交通出行","生活服务","服装装扮","餐饮美食","数码电器","其他标签"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setStatusBarHide(getWindow());
        setContentView(R.layout.activity_detailed);
        InitView();
    }
    private void InitView(){
        lineChartView = findViewById(R.id.LineChartView);
        mapChartView = findViewById(R.id.mapChartView);

        dao = new Dao(this);
        recordList = dao.QueryAll();

        lineChartView.aa_drawChartWithChartModel(InitLineChart());
        mapChartView.aa_drawChartWithChartModel(InitRoseChart());

        TextView title = findViewById(R.id.EditFunc);
        title.setText("可视化概览");

        TextView save = findViewById(R.id.BtnSave);
        save.setVisibility(View.INVISIBLE);

    }
    private  AAChartModel InitLineChart() {
        return new AAChartModel()
                .chartType(AAChartType.Areaspline)
                .legendEnabled(false)
                .yAxisVisible(true)
                .markerRadius(6f)
                .markerSymbolStyle(AAChartSymbolStyleType.InnerBlank)
                .zoomType(AAChartZoomType.XY)
                .categories(s_select)
                .yAxisMin(2.0f)//Y轴数据最大值和最小值范围
                .yAxisMax(2000.0f)
                .xAxisTickInterval(2)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("合计")
                                .color("#2494F3")
                                .data( getPrice())
                })
                ;
    }

    private AAChartModel InitRoseChart() {
        return new AAChartModel()
                .yAxisTitle("cm")
                .chartType(AAChartType.Column)
                .xAxisVisible(false)//是否显示最外一层圆环
                .yAxisVisible(true)//是否显示中间的多个圆环
                .yAxisAllowDecimals(true)
                .legendEnabled(false)//隐藏图例(底部可点按的小圆点)
                .categories(getTitles())
                .dataLabelsEnabled(true)
                .polar(true)//极地化图形
                .series(new AASeriesElement[]{
                                new AASeriesElement()
                                        .name("价格")
                                        .data(getRosePrice()),
                        }
                );
    }
    /**
     * 折线图数据源x*/
    private Object[] getPrice(){
        if (recordList == null || recordList.size() == 0)return null;
        double[] d_price = new double[s_select.length];
        Object[] o_price = new Object[s_select.length];
        for (int i = 0; i < recordList.size(); i++) {
            for (int j = 0; j < s_select.length; j++) {
                if (recordList.get(i).getLabel().equals(s_select[j])){
                    Log.d("DetailedActivity",Double.parseDouble(recordList.get(i).getGoodsPrice())+"");
                    d_price[j] += Double.parseDouble(recordList.get(i).getGoodsPrice());
                    break;
                }
            }
        }
        for (int i = 0; i < s_select.length; i++) {
            o_price[i] = new Double(d_price[i]);
        }
        return o_price;
    }
    /**
     * 南丁格尔玫瑰图数据源x*/
    private Object[] getRosePrice(){
        if (recordList == null || recordList.size() == 0)return null;
        double[] d_price = new double[recordList.size()];
        Object[] o_price = new Object[recordList.size()];
        for (int i = 0; i < recordList.size(); i++) {
            d_price[i] = Double.parseDouble(recordList.get(i).getGoodsPrice());
        }
        for (int i = 0; i < recordList.size(); i++) {
            o_price[i] = new Double(d_price[i]);
        }
        return o_price;
    }
    private String[] getTitles(){
        if (recordList == null || recordList.size() == 0)return null;
        String[] title = new String[recordList.size()];
        for (int i = 0; i < recordList.size(); i++) {
            /**截取商品名称前7个字符*/
            if (recordList.get(i).getGoodsName().length() > 7 ){
                title[i] = recordList.get(i).getGoodsName().substring(0,7);
            }else {
                title[i] = recordList.get(i).getGoodsName();
            }
        }
        return title;
    }
    public void ExitEdit(View view){
       finish();
    }
}