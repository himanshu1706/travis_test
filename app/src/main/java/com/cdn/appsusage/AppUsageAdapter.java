package com.cdn.appsusage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by himanshurathore on 4/11/17.
 */

public class AppUsageAdapter extends RecyclerView.Adapter<AppUsageAdapter.ItemHolder> {

    List<AppUsageModel> modelList;
    LayoutInflater inflater;
    Context mContext;

    public AppUsageAdapter(Context context, List<AppUsageModel> modelList) {
        this.modelList = modelList;
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_appusage_layout, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        AppUsageModel model = modelList.get(position);
        holder.packageNameTV.setText(model.getPackageName());
        String formattedTime = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(model.getUsageTime()),
                TimeUnit.MILLISECONDS.toMinutes(model.getUsageTime()) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(model.getUsageTime())), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(model.getUsageTime()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(model.getUsageTime())));
        holder.timeTV.setText(formattedTime);
    }

    @Override
    public int getItemCount() {
        return modelList != null ? modelList.size() : 0;
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.package_name)
        TextView packageNameTV;

        @BindView(R.id.time)
        TextView timeTV;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
