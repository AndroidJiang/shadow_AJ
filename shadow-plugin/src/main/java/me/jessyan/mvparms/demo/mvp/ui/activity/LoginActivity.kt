package me.jessyan.mvparms.demo.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ajiang.plugin_host.R
import com.test.PluginArmActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun clickView(view: View) {
        var intent = Intent(this, PluginArmActivity::class.java)
        startActivity(intent)
    }
}