package com.cdn.appsusage;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.rvalerio.fgchecker.AppChecker;

/**
 * Created by himanshurathore on 16/10/17.
 */

public class UsageTrackerService extends Service {

    private final String TAG = UsageTrackerService.class.getSimpleName();
    private final IBinder mBinder = new LocalBinder();
    private AppChecker appChecker;
    private DBHelper dbHelper;
    private Context context;
    private AppUsageModel appUsageModel;
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setNotificationOnSB();
        appChecker = new AppChecker();
        dbHelper = DBHelper.getInstance(this);
        context = this;
        appChecker.whenAny(new AppChecker.Listener() {
            @Override
            public void onForeground(String packageName) {
                Log.e(TAG, ""+packageName);
                if (packageName != null &&
                        !packageName.equalsIgnoreCase(SharePref.getInstance(context)
                        .readStringPrefs(SharePref.CURRENT_APP))){

                    String previousApp = SharePref.getInstance(context)
                            .readStringPrefs(SharePref.CURRENT_APP);
                    SharePref.getInstance(context)
                            .writeStringPrefs(SharePref.PREVIOUS_APP, previousApp);

                    appUsageModel = dbHelper.fetchDataByPackageName(previousApp);
                    if (appUsageModel != null) {
                        long usageTime;
                        if (appUsageModel.getUsageTime() > 0) {
                            usageTime = (System.currentTimeMillis()
                                    - SharePref.getInstance(context).
                                    readLongPrefs(SharePref.CURRENT_APP_START_TIME))
                                    + appUsageModel.getUsageTime();
                        } else {
                            usageTime = System.currentTimeMillis() -
                                    SharePref.getInstance(context).
                                    readLongPrefs(SharePref.CURRENT_APP_START_TIME);
                        }
                        boolean isUpdated = dbHelper.updateAppUsageTime(previousApp, usageTime);
                        Log.e("Row updated", ""+isUpdated);
                    }

                    long currentTime = System.currentTimeMillis();
                    SharePref.getInstance(context).
                            writeStringPrefs(SharePref.CURRENT_APP, packageName);
                    SharePref.getInstance(context).
                            writeLongPrefs(SharePref.CURRENT_APP_START_TIME, currentTime);
                    if (!dbHelper.isPackageNameExists(packageName)) {
                        dbHelper.insertData(new AppUsageModel(packageName, 0));
                    }
                }
            }
        }).timeout(1000)
                .start(this);

        return START_STICKY;
    }

    private void setNotificationOnSB() {
        try {

            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

            Notification notification = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(getString(R.string.notification_msg))
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .build();
            startForeground(1, notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class LocalBinder extends Binder {
        public UsageTrackerService getService() {
            return UsageTrackerService.this;
        }
    }


}
