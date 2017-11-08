package com.cdn.appsusage;

import android.Manifest;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by himanshurathore on 4/11/17.
 */

public class CallHistoryFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.no_record_found_tv)
    TextView noRecordFoundTV;
    List<String> callList = new ArrayList<>();
    CallHistoryAdapter callHistoryAdapter;
    MainActivity mActivity;
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

        TedPermission.with(mActivity)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        getCallDetails();
                        if (callList != null && callList.size() > 0) {
                            recyclerView.setVisibility(View.VISIBLE);
                            noRecordFoundTV.setVisibility(View.GONE);
                            callHistoryAdapter = new CallHistoryAdapter(mActivity, callList);
                            linearLayoutManager = new LinearLayoutManager(mActivity);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(callHistoryAdapter);
                        } else {
                            recyclerView.setVisibility(View.GONE);
                            noRecordFoundTV.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        Toast.makeText(mActivity, "You have rejected permission, you can't see call logs",
                                Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                  mActivity.getSupportFragmentManager().beginTransaction().remove(CallHistoryFragment.this).commit();
                            }
                        }, 1000);
                    }
                })
                .setPermissions(Manifest.permission.READ_CALL_LOG)
                .check();

        return view;
    }


    private void getCallDetails() {
       // StringBuffer sb = new StringBuffer();
        Cursor managedCursor = mActivity.managedQuery( CallLog.Calls.CONTENT_URI,null, null,null, null);
        int number = managedCursor.getColumnIndex( CallLog.Calls.NUMBER );
        int type = managedCursor.getColumnIndex( CallLog.Calls.TYPE );
        int date = managedCursor.getColumnIndex( CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex( CallLog.Calls.DURATION);
       // sb.append( "Call Details :");
        while ( managedCursor.moveToNext() ) {
            String phNumber = managedCursor.getString( number );
            String callType = managedCursor.getString( type );
            String callDate = managedCursor.getString( date );
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = managedCursor.getString( duration );
            String dir = null;
            int dircode = Integer.parseInt( callType );
            switch( dircode ) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
            }
            callList.add( "\nPhone Number:--- "+phNumber +" \nCall Type:--- "+dir+" \nCall Date:--- "+callDayTime+" \nCall duration in sec :--- "+callDuration );
        }
        managedCursor.close();
    }
}
