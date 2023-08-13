package com.ajiang.plugin_host.puremusic

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.aj.constants.Constant
import com.ajiang.plugin_host.puremusic.bridge.data.login_register.Session
import com.ajiang.plugin_host.puremusic.bridge.request.RequestLoginViewModel
import com.ajiang.plugin_host.puremusic.bridge.state.LoginViewModel
import com.ajiang.plugin_host.puremusic.ui.base.BaseActivity
import com.ajiang.plugin_host.databinding.ActivityUserLoginBinding
import com.ajiang.plugin_host.plugin_manager.PluginHelper
import com.ajiang.plugin_host.puremusic.data.bean.login_register.LoginRegisterResponse
import com.ajiang.plugin_host.R
import com.tencent.shadow.dynamic.host.EnterCallback

// 登录功能的Activity
class LoginActivity : BaseActivity() {

    var mainBinding: ActivityUserLoginBinding? = null // 当前Register的布局
    var loginViewModel: LoginViewModel? = null // ViewModel

    var requestLoginViewModel : RequestLoginViewModel? = null // TODO Reqeust ViewModel
    private val mHandler: Handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hideActionBar()

        loginViewModel = getActivityViewModelProvider(this).get(LoginViewModel::class.java) // State ViewModel初始化
        requestLoginViewModel = getActivityViewModelProvider(this).get(RequestLoginViewModel::class.java) // Request ViewModel初始化
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_login) // DataBinding初始化
        mainBinding ?.lifecycleOwner = this
        mainBinding ?.vm = loginViewModel // 绑定ViewModel与DataBinding关联
        mainBinding ?.click = ClickClass() // DataBinding关联 的点击事件
        // 登录成功 眼睛监听 成功
        requestLoginViewModel ?.registerData1 ?.observe(this, {
            loginSuccess(it !!)
        })

        // 登录失败 眼睛监听 失败
        requestLoginViewModel ?.registerData2 ?.observe(this, {
            loginFialure(it !!)
        })
    }

    // 响应的两个函数
    fun loginSuccess(registerBean: LoginRegisterResponse?) {
        //  Toast.makeText(this@LoginActivity, "登录成功😀", Toast.LENGTH_SHORT).show()
        loginViewModel?.loginState?.value = "恭喜 ${registerBean?.username} 用户，登录成功"

        // 登录成功 在跳转首页之前，需要 保存 登录的会话
        // 保存登录的临时会话信息
        mSharedViewModel.session.value = Session(true, registerBean)

        // 跳转到首页
        startActivity(Intent(this@LoginActivity,  MainActivity::class.java))
    }

    fun loginFialure(errorMsg: String?) {
        // Toast.makeText(this@LoginActivity, "登录失败 ~ 呜呜呜", Toast.LENGTH_SHORT).show()
        loginViewModel ?.loginState ?.value = "骚瑞 登录失败，错误信息是:${errorMsg}"
    }

    inner class ClickClass {

        // 点击事件，登录的函数
        fun loginAction() {
            if (loginViewModel !!.userName.value.isNullOrBlank() || loginViewModel !!.userPwd.value.isNullOrBlank()) {
                loginViewModel ?.loginState ?.value = "用户名 或 密码 为空，请你好好检查"
                return
            }

            // 非协程版本
            /*requestLoginViewModel ?.requestLogin(
                this@LoginActivity,
                loginViewModel !!.userName.value!!,
                loginViewModel !!.userPwd.value!!,
                loginViewModel !!.userPwd.value!!
            )*/

            // 协程版本
            requestLoginViewModel ?.requestLoginCoroutine( this@LoginActivity, loginViewModel !!.userName.value!!, loginViewModel !!.userPwd.value!!)
        }

        // 跳转到 注册界面
        fun startToRegister() = startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
    }

        fun startPlugin(view: View) {
//        if(!NetworkUtils.isConnected()){
//            Toast.makeText(this,"无网络",Toast.LENGTH_SHORT).show()
//            return
//        }
        PluginHelper.getInstance().singlePool.execute(Runnable {
            App.getApp()?.loadPluginManager(
                PluginHelper.getInstance().pluginManagerFile)

            /**
             * @param context context
             * @param formId  标识本次请求的来源位置，用于区分入口
             * @param bundle  参数列表, 建议在参数列表加入自己的验证
             * @param callback 用于从PluginManager实现中返回View
             */
            val bundle = Bundle() //插件 zip，这几个参数也都可以不传，直接在 PluginManager 中硬编码
            bundle.putString(
                Constant.KEY_PLUGIN_ZIP_PATH,
                PluginHelper.getInstance().pluginZipFile.getAbsolutePath()
            )
            bundle.putString(
                Constant.KEY_PLUGIN_NAME,
                Constant.PLUGIN_APP_NAME
            ) // partKey 每个插件都有自己的 partKey 用来区分多个插件，如何配置在下面讲到
            bundle.putString(
                Constant.KEY_ACTIVITY_CLASSNAME,
                "me.jessyan.mvparms.demo.mvp.ui.activity.LoginActivity"
            ) //要启动的插件的Activity页面
            bundle.putBundle(Constant.KEY_EXTRAS, Bundle()) // 要传入到插件里的参数
            App.getApp()?.getPluginManager()?.enter(
                this@LoginActivity,
                Constant.FROM_ID_START_ACTIVITY,
                bundle,
                object : EnterCallback {
                    override fun onShowLoadingView(view: View) {
                        Log.e("PluginLoad", "onShowLoadingView")
                        loading(view)
                        //这里进行加载视图
                    }

                    override fun onCloseLoadingView() {
                        Log.e("PluginLoad", "onCloseLoadingView")
                        removeLoading()
                    }

                    override fun onEnterComplete() {
                        // 启动成功
                        Log.e("PluginLoad", "onEnterComplete")
                    }
                })
        })
    }
    private fun loading(view: View) {
        mHandler.post(Runnable {
//            ll!!.removeAllViews()
//            ll!!.addView(view)
        })
    }
    private fun removeLoading() {
        mHandler.post(Runnable {
//            ll!!.removeAllViews()
        })
    }
//    fun addPluginView(view: View) {
//        val intent = Intent(this, HostAddPluginViewActivity::class.java)
//        startActivity(intent)
//    }
}