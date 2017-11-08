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

public class CallHistoryAdapter extends RecyclerView.Adapter<CallHistoryAdapter.ItemHolder> {

    List<String> modelList;
    LayoutInflater inflater;
    Context mContext;

    public CallHistoryAdapter(Context context, List<String> modelList) {
        this.modelList = modelList;
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_call_history_layout, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        String str = modelList.get(position);
        holder.callDetailTV.setText(str);
    }

    @Override
    public int getItemCount() {
        return modelList != null ? modelList.size() : 0;
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.call_detail_tv)
        TextView callDetailTV;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
