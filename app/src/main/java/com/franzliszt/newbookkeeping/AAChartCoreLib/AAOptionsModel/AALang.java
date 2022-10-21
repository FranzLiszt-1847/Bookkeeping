package com.franzliszt.newbookkeeping.AAChartCoreLib.AAOptionsModel;

public class AALang {
    public String resetZoom;
    public String thousandsSep;

    public AALang resetZoom(String prop) {
        resetZoom = prop;
        return this;
    }

    public AALang thousandsSep(String prop) {
        thousandsSep = prop;
        return this;
    }
}
