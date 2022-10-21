package com.franzliszt.newbookkeeping.utils;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class KillProcess {

    private static List<Activity> activityList = new ArrayList<>(  );
    private static Stack<Class> activityStack = new Stack<>();

    public static void PUSH(Class c,Activity activity){
        activityList.add(activity);
        /**如果大于0则代表栈内已存在，不重复添加*/
        Log.d("Stack","位置:"+activityStack.lastIndexOf(c));
        if (activityStack.lastIndexOf(c) < 0){
            activityStack.push(c);
            Log.d("Stack","已添加:"+c.getName());
            Log.d("Stack","位置:"+activityStack.lastIndexOf(c));
            return;
        }else {
            boolean result = activityStack.remove(c);
            if (result)
            {
                Log.d("Stack","delete success");
                activityStack.push(c);
                Log.d("Stack","已添加:"+c.getName()+activityStack.lastIndexOf(c));
            }
            else {
                Log.d("Stack","delete fail");
            }
        }
        Log.d("Stack","未添加:"+c.getName());
    }

    public static void POP( Activity activity){
        try {
            activityStack.pop();
            activity.startActivity(new Intent(activity,activityStack.pop()));
        }catch (EmptyStackException e){
            FinishAll();
        }
    }
    private static void FinishAll(){
        for (Activity activity : activityList) {
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
        Log.d("Stack","退出程序");
    }

}
