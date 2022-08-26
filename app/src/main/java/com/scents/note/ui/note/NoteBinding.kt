package com.scents.note.ui.note

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scents.note.data.vo.response.KeywordInfo
import com.scents.note.util.NoteKeywordAdapter

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