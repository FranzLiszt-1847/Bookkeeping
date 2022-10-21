package com.franzliszt.newbookkeeping.AAChartCoreLib.AATools;

public class AAJSStringPurer {

    public static String pureJavaScriptFunctionString(String JSStr)  {
        String pureJSStr = JSStr;
        pureJSStr = pureJSStr.replace("'", "\"");
        pureJSStr = pureJSStr.replace("\0", "");
        pureJSStr = pureJSStr.replace("\n", "");
        pureJSStr = pureJSStr.replace("\\", "\\\\");
        pureJSStr = pureJSStr.replace("\"", "\\\"");
        pureJSStr = pureJSStr.replace("\n", "\\n");
        pureJSStr = pureJSStr.replace("\r", "\\r");
        pureJSStr = pureJSStr.replace("\f", "\\f");
        pureJSStr = pureJSStr.replace("\u2028", "\\u2028");
        pureJSStr = pureJSStr.replace("\u2029","\\u2029" );
        return pureJSStr;
    }
}
