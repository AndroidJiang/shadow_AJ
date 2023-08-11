package com.ajiang.plugin_host.plugin_view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import com.ajiang.plugin_host.R;
import com.tencent.shadow.sample.host.lib.HostAddPluginViewContainer;
import com.tencent.shadow.sample.host.lib.HostAddPluginViewContainerHolder;


public class HostAddPluginViewActivity extends Activity implements HostAddPluginViewContainer {
    private LinearLayout lnLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_host_add_plugin_view);
        lnLayout = findViewById(R.id.ln_layout);
    }

    public void loadPluginView(View view) {
        //简化逻辑，只允许点一次
        view.setEnabled(false);

        //因为当前Activity和插件都在:plugin进程，不能直接操作主进程的manager对象，所以通过一个广播调用manager。
        Intent intent = new Intent();
        intent.setPackage(getPackageName());
        intent.setAction("sample_host.manager.startPluginService");

        final int id = System.identityHashCode(this);
        HostAddPluginViewContainerHolder.instances.put(id, this);
        intent.putExtra("id", id);

        sendBroadcast(intent);
    }

    @Override
    public void addView(View view) {
        lnLayout.addView(view);
    }


}
