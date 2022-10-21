package com.franzliszt.newbookkeeping.AAChartCoreLib.AAOptionsModel;


import com.franzliszt.newbookkeeping.AAChartCoreLib.AATools.AAJSStringPurer;

public class AATooltip {
    public String backgroundColor;
    public String borderColor;
    public Float borderRadius;
    public Float borderWidth;
    public AAStyle style;
    public Boolean enabled;
    public Boolean useHTML;
    public String formatter;
    public String headerFormat;
    public String pointFormat;
    public String footerFormat;
    public Integer valueDecimals;
    public Boolean shared;
    public String valueSuffix;
    public Boolean followTouchMove;//https://api.highcharts.com.cn/highcharts#chart.panning
    public Boolean shadow;
    public Float padding;
    public String pointFormatter;
    public String positioner;
    public AADateTimeLabelFormats dateTimeLabelFormats;

    public AATooltip backgroundColor(String prop) {
        backgroundColor = prop;
        return this;
    }

    public AATooltip borderColor(String prop) {
        borderColor = prop;
        return this;
    }

    public AATooltip borderRadius(Float prop) {
        borderRadius = prop;
        return this;
    }

    public AATooltip borderWidth(Float prop) {
        borderWidth = prop;
        return this;
    }

    public AATooltip style(AAStyle prop) {
        style = prop;
        return this;
    }

    public AATooltip enabled(Boolean prop) {
        enabled = prop;
        return this;
    }

    public AATooltip useHTML(Boolean prop) {
        useHTML = prop;
        return this;
    }

    public AATooltip formatter(String prop) {
        String pureJSFunctionStr = "(" + prop + ")";
        pureJSFunctionStr = AAJSStringPurer.pureJavaScriptFunctionString(pureJSFunctionStr);
        formatter = pureJSFunctionStr;
        return this;
    }

    public AATooltip headerFormat(String prop) {
        headerFormat = prop;
        return this;
    }

    public AATooltip pointFormat(String prop) {
        pointFormat = prop;
        return this;
    }

    public AATooltip footerFormat(String prop) {
        footerFormat = prop;
        return this;
    }

    public AATooltip valueDecimals(Integer prop) {
        valueDecimals = prop;
        return this;
    }

    public AATooltip shared(Boolean prop) {
        shared = prop;
        return this;
    }

    public AATooltip valueSuffix(String prop) {
        valueSuffix = prop;
        return this;
    }

    public AATooltip followTouchMove(Boolean prop) {
        followTouchMove = prop;
        return this;
    }

    public AATooltip shadow(Boolean prop) {
        shadow = prop;
        return this;
    }

    public AATooltip padding(Float prop) {
        padding = prop;
        return this;
    }

    public AATooltip pointFormatter(String prop) {
        pointFormatter = prop;
        return this;
    }

    public AATooltip positioner(String prop) {
        positioner = prop;
        return this;
    }

    public AATooltip dateTimeLabelFormats(AADateTimeLabelFormats prop) {
        dateTimeLabelFormats = prop;
        return this;
    }

    public AATooltip() {
        enabled = true;
        shared = true;
    }


    
  
}
