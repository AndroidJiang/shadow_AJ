package com.ajiang.plugin_host

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.test.PluginArmActivity
import com.test.PluginMain2Activity

class PluginMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun clickView(view: View) {
        var intent = Intent(this, PluginArmActivity::class.java)
        startActivity(intent)
    }
}