package com.cctv.ssfs.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qi on 2018/3/27.
 */

public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    /**
     * add persons
     *
     * @param beanList
     */
    public void add(List<DBRecordsBean> beanList) {
        db.beginTransaction();  //开始事务
        try {
            for (DBRecordsBean bean : beanList) {
                db.execSQL("INSERT INTO person VALUES(null, ?, ?)", new Object[]{bean.name, bean.code});
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    /**
     * 查询表中数据总条数
     * 返回表中数据条数
     */
    public Long getDataCounts() {
        Cursor cursor = db.rawQuery("select count(*)from records", null);
        cursor.moveToFirst();
        Long count = cursor.getLong(0);
        cursor.close();
        closeDB();
        return count;
    }


    /**
     * 查询数据
     *
     * @return Cursor
     */
    public List<DBRecordsBean> query() {
        Cursor cursor = db.query(DBHelper.TABLE_NAME, null, null, null,
                null, null, null);
        List<DBRecordsBean> list = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            DBRecordsBean bean = new DBRecordsBean();
            bean.name = (cursor.getString(cursor.getColumnIndex("name")));
            bean.code = (cursor.getString(cursor.getColumnIndex("code")));
            list.add(bean);
        }
        closeDB();
        return list;
    }

    /**
     * 删除
     *
     * @param bean
     */
    public void delete(DBRecordsBean bean) {
        if (bean.id >= 0) {
            db.delete(DBHelper.TABLE_NAME, "_id = ?", new String[]{bean.id + ""});
            closeDB();
        } else {
            return;
        }
    }

    /**
     * close database
     */
    public void closeDB() {
        if (db.isOpen()) {
            db.close();
        }
        if (helper != null) {
            helper.close();
        }
    }
}
