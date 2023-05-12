package com.scentsnote.android.ui.note

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scentsnote.android.data.vo.response.KeywordInfo
import com.scentsnote.android.utils.NoteKeywordAdapter

object NoteBinding {
    @JvmStatic
    @BindingAdapter("setNoteKeywordList")
    fun setNoteKeywordList(recyclerView: RecyclerView, list : MutableList<KeywordInfo>?){
        if(recyclerView.adapter!=null) with(recyclerView.adapter as NoteKeywordAdapter){
            list?.let {
                setData(it)}
        }
    }
}