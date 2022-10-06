package com.scentsnote.android.util

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T: ViewDataBinding>(@LayoutRes val layoutRes: Int): AppCompatActivity() {
    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.lifecycleOwner = this@BaseActivity

        initView()
        initActionView()
        initLiveData()
    }

//    open fun beforeSetContentView() {}
    open fun initView() {}
    open fun initActionView() {}
    open fun initLiveData() {}
//    open fun afterOnCreate() {}

}