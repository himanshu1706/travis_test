package com.cdn.appsusage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by himanshurathore on 3/11/17.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "AppUsage.db";
    private static final int DATABASE_VERSION = 1;
    private static DBHelper databaseHelper = null;
    public SQLiteDatabase sqLiteDatabase = null;
    public Dao<AppUsageModel, String> appUsageDao = null;
    private Context mContext;


    /**
     * This constructor will be used to intialize Database
     *
     * @param context
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
        sqLiteDatabase = getWritableDatabase();
        try {
            appUsageDao = getDao(AppUsageModel.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DBHelper getInstance(Context mContext) {
        if (databaseHelper == null) {
            Context applicationContext = mContext.getApplicationContext();
            databaseHelper = new DBHelper(applicationContext);
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, AppUsageModel.class);
        } catch (SQLException e) {
            Log.e("Can't create database", "" + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Log.e("", "onUpgrade");
        try {
            TableUtils.dropTable(connectionSource, AppUsageModel.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e("Can't drop database", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public synchronized void insertData(AppUsageModel appUsageModel){
        try {
            appUsageDao.createOrUpdate(appUsageModel);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public synchronized List<AppUsageModel> fetchData(){
        try {
            return appUsageDao.queryForAll();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public synchronized AppUsageModel fetchDataByPackageName(String packageName){
        try {
            return appUsageDao.queryForId(packageName);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public synchronized boolean updateAppUsageTime(String packageName, long time){
        try {
            UpdateBuilder<AppUsageModel, String> ub = appUsageDao.updateBuilder();
            ub.updateColumnValue("usageTime",time);
            ub.where().eq("packageName", packageName);
            ub.update();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public synchronized boolean isPackageNameExists(String packageName){
        try {
            AppUsageModel appUsageModel = fetchDataByPackageName(packageName);
            if (appUsageModel != null){
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
