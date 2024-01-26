package com.scentsnote.android.utils.base

import android.os.Bundle
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
        initListener()
        initObserver()
    }

//    open fun beforeSetContentView() {}
    open fun initView() {}
    open fun initListener() {}
    open fun initObserver() {}
//    open fun afterOnCreate() {}

}