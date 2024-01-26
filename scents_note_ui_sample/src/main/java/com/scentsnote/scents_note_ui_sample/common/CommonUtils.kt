package com.scentsnote.scents_note_ui_sample.common

import android.view.ViewGroup
import androidx.core.view.children

object CommonUtils {
    fun<T> findAllViews(container : ViewGroup, klass : Class<T>) : List<T> {
        val result = ArrayList<T>()
        findViews(klass, container, result)
        return result
    }

    @Suppress("UNCHECKED_CAST")
    private fun<T> findViews(klass : Class<T>, view: ViewGroup, result: ArrayList<T>) {

        view.children.forEach {
            if (it is ViewGroup) {
                findViews(klass, it, result)
            }
            try {
                if (klass.isAssignableFrom(it::class.java)) {
                    result.add(it as T)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}