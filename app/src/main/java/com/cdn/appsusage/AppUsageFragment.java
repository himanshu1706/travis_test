package com.cdn.appsusage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by himanshurathore on 4/11/17.
 */

public class AppUsageFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.no_record_found_tv)
    TextView noRecordFoundTV;

    AppUsageAdapter appUsageAdapter;
    MainActivity mActivity;
    List<AppUsageModel> modelList = new ArrayList<>();
    DBHelper dbHelper;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_usage, container, false);
        ButterKnife.bind(this,view);
        dbHelper = DBHelper.getInstance(mActivity);
        modelList = dbHelper.fetchData();
        if (modelList != null && modelList.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            noRecordFoundTV.setVisibility(View.GONE);
            appUsageAdapter = new AppUsageAdapter(mActivity, modelList);
            linearLayoutManager = new LinearLayoutManager(mActivity);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(appUsageAdapter);
        } else {
            recyclerView.setVisibility(View.GONE);
            noRecordFoundTV.setVisibility(View.VISIBLE);
        }
        return view;
    }
}
