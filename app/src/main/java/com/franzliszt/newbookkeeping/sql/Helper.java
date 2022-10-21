package com.franzliszt.newbookkeeping.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class Helper extends SQLiteOpenHelper {
    /**数据库名称*/
    public static final String DataBase = "Bookkeeping.db";
    public static final SQLiteDatabase.CursorFactory factory = null;
    public static int version = 1;
    /**表名*/
    public static final String TableName = "recordTable";
    /**行名*/
    public static final String Row_ID = "ID";
    public static final String Row_Date = "Data";
    public static final String Row_Time = "Time";
    public static final String Row_Type = "Type";
    public static final String Row_Label = "Label";
    public static final String Row_Name = "Name";
    public static final String Row_Price = "Price";



    public Helper(@Nullable Context context) {
        super( context, DataBase, factory, version );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TableName + "( "+Row_ID+" INTEGER primary key AUTOINCREMENT, "+Row_Date+" varchar(20), "+Row_Time+" varchar(20), "+Row_Type+" INTEGER,"+Row_Label+" varchar(20),"+Row_Name+" varchar(20),"+Row_Price+" varchar(20));";
        db.execSQL( sql );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TableName);
        onCreate(db);
    }
}
