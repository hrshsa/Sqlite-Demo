package com.example.pc.sqlitedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new SqlHelper(this).getWritableDatabase();

    }

    /**
     * 参数一是表名称，参数二是空列的默认值，参数三是ContentValues类型的一个封装了列名称和列值的Map；
     */
    private void insert(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put("id", "2");
        cv.put("model_id", "202");
        cv.put("serial_id", "222");
        long ret = db.insert(SqlHelper.TABLE_NAME, null, cv);
        Log.e("return :", ret + "");
    }

    /**
     * 直接使用sql语句进行插入
     * String sql = "insert into SqlHelper.TABLE_NAME(id,model_id,serial_id) values ("10","100","200)"
     */
    private void insert(SQLiteDatabase db, String sql) {
        db.execSQL(sql);
    }

    /**
     * String whereClause ="id=?"     删除条件
     * String[] whereArg = new String[]{"10"};      删除条件参数
     */
    private void delete(SQLiteDatabase db, String whereClause, String[] whereArgs) {

        db.delete(SqlHelper.TABLE_NAME, whereClause, whereArgs);
    }

    /**
     * String sql  = delete from SqlHelper.TABLE_NAME where id = 1;
     */
    private void delete(SQLiteDatabase db, String sql) {
        db.execSQL(sql);
    }

    /**
     * cv.put("id","1231");
     * String whereClause = "id=?";
     * String [] whereArg = new String[]{"1"};
     */
    private void update(SQLiteDatabase db, String key, String value,
                        String whereClause, String[] whereArgs) {
        ContentValues cv = new ContentValues();
        cv.put(key, value);
        int ret = db.update(SqlHelper.TABLE_NAME, cv, whereClause, whereArgs);
        Log.e("update return: ", ret + "");
    }

    /**
     * String sql = "update ["+SqlHelper.TABLE_NAME+"] set id = 10 where carserial_id = 1";
     */
    private void update(SQLiteDatabase db, String sql) {

        db.execSQL(sql);
    }

    /**
     * ①table:表名称
     * ②columns:列名称数组
     * ③selection:条件字句，相当于where  举例 "name=?"
     * ④selectionArgs:条件字句，参数数组     条件语句的参数数组  举例 new String[]{ "dmk"}
     * ⑤groupBy:分组列
     * ⑥having:分组条件
     * ⑦orderBy:排序列
     * ⑧limit:分页查询限制
     * ⑨Cursor:返回值，相当于结果集ResultSet
     */
    private void query(SQLiteDatabase db, String[] columns, String selection, String[] selectionArgs,
                       String groupBy, String having, String orderBy, String limit) {
        Cursor cursor = null;
        try {
            cursor = db.query(SqlHelper.TABLE_NAME, columns,
                    selection, selectionArgs, groupBy, having, orderBy, limit);
            //判断游标是否为空  cursor初始坐标为-1
            if (cursor.moveToNext()) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.move(i);
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    String serialid = cursor.getString(cursor.getColumnIndex("serial_id"));
                    String modelId = cursor.getString(cursor.getColumnIndex("model_id"));
                    Log.e("id: ", id);
                    Log.e("serialid: ", serialid);
                    Log.e("modelId: ", modelId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public void click1(View view) {
        insert(db);

    }

    public void click2(View view) {
        String whereClause = "id = ?";
        String[] whereArgs = new String[]{"2"};
        delete(db, whereClause, whereArgs);
    }

    public void click3(View view) {
        update(db, "serial_id", "20", "id=?", new String[]{"2"});
    }

    public void click4(View view) {
        query(db, null, null, null, null, null, "id", null);

    }
}
