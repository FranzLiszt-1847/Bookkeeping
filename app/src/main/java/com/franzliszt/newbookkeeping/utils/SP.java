package com.franzliszt.newbookkeeping.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class SP {
    private static SP spInstant = null;
    private static final String defaultModelName = "DefaultModelName";
    private SP(){

    }
    public static SP getInstance(){
        if (spInstant == null){
            Sync();
        }
        return spInstant;
    }
    private static synchronized void Sync(){
        if (spInstant == null){
            spInstant = new SP(  );
        }
    }
    public void PutData(Context context,String key,Object value){
        PutData( context,defaultModelName,key,value );
    }
    private void PutData(Context context,String defaultModelName ,String key,Object value){
        SharedPreferences preferences = context.getSharedPreferences( defaultModelName,Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = preferences.edit();
        if (value instanceof Boolean){
            editor.putBoolean( key,(Boolean) value );
        }else if (value instanceof Integer){
            editor.putInt( key,(Integer)value );
        }else if (value instanceof Float){
            editor.putFloat( key,(Float)value );
        }else if (value instanceof Long){
            editor.putLong( key,(Long)value );
        }else if (value instanceof String){
            editor.putString( key,(String)value );
        }else{
            return;
        }
        editor.apply();
    }
    public Object GetData(Context context,String key,Object defaultValue){
        return GetData( context,defaultModelName,key,defaultValue);
    }
    private Object GetData(Context context,String defaultModelName,String key,Object defaultValue){
        SharedPreferences preferences = context.getSharedPreferences( defaultModelName,Context.MODE_PRIVATE );
        if (defaultValue instanceof Boolean){
            return preferences.getBoolean( key,(Boolean) defaultValue );
        }else if (defaultValue instanceof Integer){
            return preferences.getInt( key,(Integer) defaultValue );
        }else if (defaultValue instanceof Float){
            return preferences.getFloat( key,(Float) defaultValue );
        }else if (defaultValue instanceof Long){
            return preferences.getLong( key,(Long) defaultValue );
        }else if (defaultValue instanceof String){
            return preferences.getString( key,(String) defaultValue );
        }else{
            return null;
        }
    }
}
