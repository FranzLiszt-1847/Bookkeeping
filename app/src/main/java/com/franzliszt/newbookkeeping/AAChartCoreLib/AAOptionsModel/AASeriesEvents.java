package com.franzliszt.newbookkeeping.AAChartCoreLib.AAOptionsModel;


import com.franzliszt.newbookkeeping.AAChartCoreLib.AATools.AAJSStringPurer;

public class AASeriesEvents {
    public String legendItemClick;

    public AASeriesEvents legendItemClick(String prop) {
        String pureJSFunctionStr = "(" + prop + ")";
        pureJSFunctionStr = AAJSStringPurer.pureJavaScriptFunctionString(pureJSFunctionStr);
        legendItemClick = pureJSFunctionStr;
        return this;
    }

}
