package com.scentsnote.android.utils.etc

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.scentsnote.android.BuildConfig

object Log {
    private const val TAG: String = "ScentsNote"
    private val isDebug = BuildConfig.DEBUG

    fun getInstance(): Log {
        Logger.addLogAdapter(AndroidLogAdapter())
        return this
    }

    fun d(msg: String) {
        if (isDebug)
            Logger.t(TAG).d(msg)
    }
    fun d(tag: String, msg: String) {
        if (isDebug)
            Logger.t(tag).d(msg)
    }
    fun d(tag: String, msg: String, throwable: Throwable) {
        if (isDebug)
            android.util.Log.d(tag, msg, throwable)
    }

    fun i(msg: String) {
        if (isDebug)
            Logger.t(TAG).i(msg)
    }
    fun i(tag: String, msg: String) {
        if (isDebug)
            Logger.t(tag).i(msg)
    }
    fun i(tag: String, msg: String, throwable: Throwable) {
        if (isDebug)
            android.util.Log.i(tag, msg, throwable)
    }

    fun w(msg: String) {
        if (isDebug)
            Logger.t(TAG).w(msg)
    }
    fun w(tag: String, msg: String) {
        if (isDebug)
            Logger.t(tag).w(msg)
    }
    fun w(tag: String, msg: String, throwable: Throwable) {
        if (isDebug)
            android.util.Log.w(tag, msg, throwable)
    }

    fun e(msg: String) {
        if (isDebug)
            Logger.t(TAG).e(msg)
    }
    fun e(tag: String, msg: String) {
        if (isDebug)
            Logger.t(tag).e(msg)
    }
    fun e(tag: String, msg: String, throwable: Throwable) {
        if (isDebug)
            android.util.Log.e(tag, msg, throwable)
    }

    fun v(msg: String) {
        if (isDebug)
            Logger.t(TAG).v(msg)
    }
    fun v(tag: String, msg: String) {
        if (isDebug)
            Logger.t(tag).v(msg)
    }
    fun v(tag: String, msg: String, throwable: Throwable) {
        if (isDebug)
            android.util.Log.v(tag, msg, throwable)
    }

    fun js(data: String) {
        if (isDebug)
            Logger.t(TAG).json(data)
    }
    fun js(tag: String, data: String) {
        if (isDebug)
            Logger.t(tag).json(data)
    }

    fun xml(data: String) {
        if (isDebug)
            Logger.t(TAG).xml(data)
    }
    fun xml(tag: String, data: String) {
        if (isDebug)
            Logger.t(tag).xml(data)
    }
}