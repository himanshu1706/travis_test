package com.cdn.appsusage;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by himanshurathore on 3/11/17.
 */

@DatabaseTable(tableName = "app_usage")
public class AppUsageModel {

    @DatabaseField(id = true)
    String packageName;

    @DatabaseField
    long usageTime;


    public AppUsageModel(){

    }

    public AppUsageModel(String packageName, long usageTime) {
        this.packageName = packageName;
        this.usageTime = usageTime;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public long getUsageTime() {
        return usageTime;
    }

    public void setUsageTime(long usageTime) {
        this.usageTime = usageTime;
    }
}
