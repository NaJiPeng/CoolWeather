package com.njp.android.coolweather.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * 行政地区查询类
 */

public class DistrictDao {

    private static SQLiteDatabase sDatabase;

    public static void init(Context context) {
        String path = "/data/data/" + context.getPackageName() + "/databases/" +"districts.db";
        sDatabase = SQLiteDatabase
                .openDatabase(path,null,SQLiteDatabase.OPEN_READWRITE);
    }

    public static List<String> queryProvince() {
        String sql = "SELECT DISTINCT province FROM districts";
        List<String> list = new ArrayList<>();
        Cursor c = sDatabase.rawQuery(sql, null);
        try {
            if (c.moveToFirst()) {
                do {
                    list.add(c.getString(c.getColumnIndex("province")));
                } while (c.moveToNext());
            }
        } finally {
            c.close();
        }
        return list;
    }

    public static List<String> queryCity(String province) {
        String sql = "SELECT DISTINCT city FROM districts where province=?";
        List<String> list = new ArrayList<>();
        Cursor c = sDatabase.rawQuery(sql, new String[]{province});
        try {
            if (c.moveToFirst()) {
                do {
                    list.add(c.getString(c.getColumnIndex("city")));
                } while (c.moveToNext());
            }
        } finally {
            c.close();
        }
        return list;
    }

    public static List<String> queryDistrict(String city) {
        String sql = "SELECT DISTINCT district FROM districts where city=?";
        List<String> list = new ArrayList<>();
        Cursor c = sDatabase.rawQuery(sql, new String[]{city});
        try {
            if (c.moveToFirst()) {
                do {
                    list.add(c.getString(c.getColumnIndex("district")));
                } while (c.moveToNext());
            }
        } finally {
            c.close();
        }
        return list;
    }

}
