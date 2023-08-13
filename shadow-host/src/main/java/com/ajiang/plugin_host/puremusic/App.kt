package com.ajiang.plugin_host.puremusic

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Process
import android.os.StrictMode
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.ajiang.plugin_host.plugin_manager.AndroidLogLoggerFactory
import com.ajiang.plugin_host.plugin_manager.PluginHelper
import com.ajiang.plugin_host.plugin_manager.Shadow
import com.ajiang.plugin_host.puremusic.data.repository.net.APIClient
import com.ajiang.plugin_host.puremusic.player.PlayerManager
import com.tencent.shadow.core.common.LoggerFactory
import com.tencent.shadow.dynamic.host.DynamicRuntime
import com.tencent.shadow.dynamic.host.PluginManager
import com.xiangxue.architecture.utils.Utils
import java.io.File


/**
 * 整个项目的 Application
 */
class App : MultiDexApplication(), ViewModelStoreOwner {
    companion object {
        private var sApp: App? = null
        fun getApp(): App? {
            return sApp
        }

    }
    private var mAppViewModelStore: ViewModelStore? = null
    private var mFactory: ViewModelProvider.Factory? = null
    private var mPluginManager: PluginManager? = null
    override fun onCreate() {
        super.onCreate()
        pluginOnCreate()
        Utils.init(this)
        mAppViewModelStore = ViewModelStore() // 此处 只会调用一次

        // 同学们，这里必须初始化一下，是为了保证播放音乐管理类（PlayerManager.java） 不会为null，从而不引发空指针异常
        PlayerManager.instance.init(this)
    }

    // 关键函数，只暴露 给 BaseActivity 与 BaseFragment 用的，保证共享ViewModel初始化的 单例
    // 专门给 BaseActivity 与 BaseFragment 用的
    fun getAppViewModelProvider(activity: Activity): ViewModelProvider {
        return ViewModelProvider(
            (activity.applicationContext as App),
            (activity.applicationContext as App).getAppFactory(activity) !!
        )
    }

    // AndroidViewModelFactory 工程是为了创建ViewModel，给上面的getAppViewModelProvider函数用的
    private fun getAppFactory(activity: Activity): ViewModelProvider.Factory? {
        val application = checkApplication(activity)
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        }
        return mFactory
    }

    // 监测下 Activity是否为null
    private fun checkApplication(activity: Activity): Application {
        return activity.application
            ?: throw IllegalStateException(
                "Your activity/fragment is not yet attached to "
                        + "Application. You can't request ViewModel before onCreate call."
            )
    }

    // 监测下 Activity是否为null
    private fun checkActivity(fragment: Fragment): Activity? {
        return fragment.activity
            ?: throw IllegalStateException("Can't create ViewModelProvider for detached fragment")
    }

    // TODO 暴露出去 给外界用
    // 同学们注意，此函数只给 NavHostFragment 使用
    override fun getViewModelStore(): ViewModelStore = mAppViewModelStore !!
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }


    fun pluginOnCreate() {
        sApp = this
        //        NetworkUtils.init(this);
        detectNonSdkApiUsageOnAndroidP()
        setWebViewDataDirectorySuffix()
        LoggerFactory.setILoggerFactory(AndroidLogLoggerFactory())
        if (isProcess(this, ":plugin")) {
            //在全动态架构中，Activity组件没有打包在宿主而是位于被动态加载的runtime，
            //为了防止插件crash后，系统自动恢复crash前的Activity组件，此时由于没有加载runtime而发生classNotFound异常，导致二次crash
            //因此这里恢复加载上一次的runtime
            DynamicRuntime.recoveryRuntime(this)
        }
        if (isProcess(this, getPackageName())) {
            PluginHelper.getInstance().init(this)
        }

//        HostUiLayerProvider.init(this);
    }

    private fun setWebViewDataDirectorySuffix() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            return
        }
        WebView.setDataDirectorySuffix(Application.getProcessName())
    }

    private fun detectNonSdkApiUsageOnAndroidP() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            return
        }
        val builder = StrictMode.VmPolicy.Builder()
        builder.detectNonSdkApiUsage()
        StrictMode.setVmPolicy(builder.build())
    }


    fun loadPluginManager(apk: File?) {
        if (mPluginManager == null) {
            mPluginManager = Shadow.getPluginManager(apk)
        }
    }

    fun getPluginManager(): PluginManager? {
        return mPluginManager
    }

    private fun isProcess(context: Context, processName: String): Boolean {
        var currentProcName = ""
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (processInfo in manager.runningAppProcesses) {
            if (processInfo.pid == Process.myPid()) {
                currentProcName = processInfo.processName
                break
            }
        }
        return currentProcName.endsWith(processName)
    }

}