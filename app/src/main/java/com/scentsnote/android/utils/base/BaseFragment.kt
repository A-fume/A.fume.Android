package com.scentsnote.android.utils.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

abstract class BaseFragment<T: ViewDataBinding>(@LayoutRes val layoutRes: Int) : Fragment(), LifecycleObserver{
    //, BaseConstant {
    private var isRegistered = false
    private var _binding: T? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (layoutRes != 0) {
            _binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
            binding.lifecycleOwner = this@BaseFragment
            onBindData()
            return binding.root
        } else {
            throw IllegalArgumentException("layout resource cannot be null")
        }
    }

//    override fun onResume() {
//        super.onResume()
//        if (!isRegistered) {
//            kotlinBus.register(this)
//            isRegistered = true
//        }
//    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onActivityCreated(){
        activity?.lifecycle?.removeObserver(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        activity?.lifecycle?.addObserver(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        initObserver()
    }

    open fun initView() {
        //TODO init view such as adapter, linearLayoutManager
    }

    open fun initListener() {
        //TODO init action for view such as onClick
    }

    open fun initObserver(){
        //TODO init Observers
    }

    open fun onBindData() {
        //TODO binding data into layout
    }

//    override fun onDetach() {
//        super.onDetach()
//        if (isRegistered) {
//            kotlinBus.unregister(this)
//            isRegistered = false
//        }
//    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}

//interface ViewContract<T> {
//    fun getLayoutResourceId(): Int
//    fun onBindData(binding: T)
//}