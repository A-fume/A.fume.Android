package com.afume.afume_android.ui.note

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.data.vo.response.KeywordInfo
import com.afume.afume_android.util.NoteKeywordAdapter

object NoteBinding {
    @JvmStatic
    @BindingAdapter("setNoteKeywordList")
    fun setNoteKeywordList(recyclerView: RecyclerView, list : MutableList<KeywordInfo>?){
        if(recyclerView.adapter!=null) with(recyclerView.adapter as NoteKeywordAdapter){
            list?.let {
                setData(it)
                Log.e("명 setNoteKeywordList",data.toString())}
        }
    }
}