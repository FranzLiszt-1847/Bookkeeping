package com.franzliszt.newbookkeeping.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Dao {
    public static final String TAG = "DataBase";
    private Helper helper;
    private SQLiteDatabase DB;

    public Dao(Context context) {
        helper = new Helper(context);
    }

    public int Insert(Record record) {
        DB = helper.getReadableDatabase();
        if (DB.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(Helper.Row_Date, record.getDate());
            values.put(Helper.Row_Time, record.getTime());
            values.put(Helper.Row_Type, record.getType());
            values.put(Helper.Row_Label, record.getLabel());
            values.put(Helper.Row_Name, record.getGoodsName());
            values.put(Helper.Row_Price, record.getGoodsPrice());
            long RowId = DB.insert(Helper.TableName, null, values);
            if (RowId == -1){
                Log.i(TAG, "数据插入失败！");
                return 0;
            }
            else{
                Log.i(TAG, "数据插入成功！" + RowId);
                return 1;
            }
        }
        return 0;
    }

    public void Delete(int id) {
        DB = helper.getReadableDatabase();
        String selection = "ID = ?";
        String[] selectionArgs = {id + ""};
        if (DB.isOpen()) {
            int count = DB.delete(Helper.TableName, selection, selectionArgs);
            if (count > 0)
                Log.i(TAG, "删除了: " + count + "行");
            else
                Log.i(TAG, "数据未删除！");
            DB.close();
        }
    }

    public void Delete(String name) {
        DB = helper.getReadableDatabase();
        String selection = "Name = ?";
        String[] selectionArgs = {name + ""};
        if (DB.isOpen()) {
            int count = DB.delete(Helper.TableName, selection, selectionArgs);
            if (count > 0)
                Log.i(TAG, "删除了: " + count + "行");
            else
                Log.i(TAG, "数据未删除！");
            DB.close();
        }
    }


    public void Update(Record record,int id) {
        DB = helper.getReadableDatabase();
        String selection = "ID = ?";
        String[] selectionArgs = {id + ""};
        if (DB.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(Helper.Row_Date, record.getDate());
            values.put(Helper.Row_Time, record.getTime());
            values.put(Helper.Row_Type, record.getType());
            values.put(Helper.Row_Label, record.getLabel());
            values.put(Helper.Row_Name, record.getGoodsName());
            values.put(Helper.Row_Price, record.getGoodsPrice());
            int count = DB.update(Helper.TableName, values, selection, selectionArgs);
            if (count > 0) {
                Log.d(TAG, "更新了: " + count + "行");
            } else {
                Log.d(TAG, "更新失败！");
            }
            DB.close();
        }
    }

    public List<Record> QueryAll() {
        DB = helper.getReadableDatabase();  // 获得一个只读的数据库对象
        if (DB.isOpen()) {
            String[] columns = {Helper.Row_Date,Helper.Row_Time,Helper.Row_Type,Helper.Row_Label,Helper.Row_Name,Helper.Row_Price};
            String selection = null;    // 选择条件, 给null查询所有
            String[] selectionArgs = null;  // 选择条件的参数, 会把选择条件中的? 替换成数据中的值
            String groupBy = null;  // 分组语句  group by name
            String having = null;   // 过滤语句
            String orderBy = null;  // 排序
            Cursor cursor = DB.query(Helper.TableName, columns, selection, selectionArgs, groupBy, having, orderBy);
            String date;
            String time;
            int type;
            String label;
            String name;
            String price;
            if (cursor != null && cursor.getCount() > 0) {
                List<Record> list = new ArrayList<Record>();
                while (cursor.moveToNext()) {    // 向下移一位, 知道最后一位, 不可以往下移动了, 停止.
                    date = cursor.getString(cursor.getColumnIndex(Helper.Row_Date));
                    time = cursor.getString(cursor.getColumnIndex(Helper.Row_Time));
                    type = cursor.getInt(cursor.getColumnIndex(Helper.Row_Type));
                    label = cursor.getString(cursor.getColumnIndex(Helper.Row_Label));
                    name = cursor.getString(cursor.getColumnIndex(Helper.Row_Name));
                    price = cursor.getString(cursor.getColumnIndex(Helper.Row_Price));
                    list.add(new Record(date,time,type,label,name,price));
                }
                DB.close();
                return list;
            }
            if (cursor != null) {
                cursor.close();
            }
            DB.close();
        }
        return null;
    }
}
