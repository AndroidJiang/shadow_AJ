package com.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ajiang.plugin_host.R

abstract  class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutResID: Int = initView(savedInstanceState)
        setContentView(layoutResID)
    }
    abstract fun initView(savedInstanceState: Bundle?) : Int
}