package com.example.pc.sqlitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HuangRuiShu on 2016/10/27.
 */
public class SqlHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "app.db";
    public static String TABLE_NAME = "car_table";
    //    数据库的版本号(以版本数字号1开始)，如果数据库比较旧，就会用 onUpgrade(SQLiteDatabase, int, int) 方法来更新数据库，
//    如果数据库比较新，就使用 onDowngrade(SQLiteDatabase, int, int)  方法来 回退数据库
    private static int DB_VERSION = 1;

    private static String INIT_SQL = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME
            + " ("
            + " id INTEGER PRIMARY KEY, "
            + " model_id TEXT, "
            + " serial_id TEXT "
            + ");";

    public SqlHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    // 此方法在第一次使用数据库时调用生成相应的数据库表，但是此方法并不是
    // 在实例化SQLiteOpenHelper类的对象时调用，而是通过对象调用了getReadableDatabase()或getWriteableDatabase()方法时才会调用。
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(INIT_SQL);
    }


    /**
     * 当数据库需要升级时调用这个方法[在开发过程中涉及到数据库的设计存在缺陷的时候进行升级，不会损坏原来的数据]，这种实现方式会使用方法来减少表，或者增加表，或者做版本更新的需求。
     * 在这里就可以执行 SQLite Alter语句了，你可以使用 ALTER TABLE 来增加新的列插入到一张表中，你可以使用 ALTER TABLE 语句来重命名列或者移除列，或者重命名旧的表。
     * 你也可以创建新的表然后将旧表的内容填充到新表中。
     * 此方法会将事务之内的事件一起执行，如果有异常抛出，任何改变都会自动回滚操作。
     * 参数：
     * db ： 数据库
     * oldVersion ： 旧版本数据库
     * newVersion ： 新版本数据库
     * 【注意】：这里的删除等操作必须要保证新的版本必须要比旧版本的版本号要大才行。[即 Version 2.0 > Version 1.0 ] 所以这边我们不需要对其进行操作。
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "alter table car_table add sex varchar(8)";
        db.execSQL(sql);
    }
}
