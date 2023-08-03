package com.ajiang.plugin_host.plugin_view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.aj.constants.Constant;
import com.ajiang.plugin_host.base.MyApplication;


public class MainProcessManagerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MyApplication.getApp().getPluginManager()
                .enter(context, Constant.FROM_ID_LOAD_VIEW_TO_HOST, intent.getExtras(), null);
    }
}
