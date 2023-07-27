package com.test

import android.os.Bundle
import com.ajiang.plugin_host.R
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.mvp.IPresenter

class PluginArmActivity : BaseActivity<IPresenter>() {
    //   override fun initView(savedInstanceState: Bundle?): Int {
//     return R.layout.activity_main2
//   }
    override fun setupActivityComponent(appComponent: AppComponent) {
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main_arm
    }

    override fun initData(savedInstanceState: Bundle?) {
    }
}