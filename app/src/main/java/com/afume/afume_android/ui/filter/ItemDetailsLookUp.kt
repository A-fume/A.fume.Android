package com.afume.afume_android.ui.filter

import android.view.MotionEvent
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.util.FlexboxRecyclerViewAdapter
import com.afume.afume_android.util.NoteKeywordAdapter

class ItemDetailsLookUp(private val recyclerView: RecyclerView,val type:String) : ItemDetailsLookup<Long>(){
    @Nullable
    override fun getItemDetails(@NonNull e: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(e.x,e.y)

        if(view != null){
            when(type){
                "flexbox"->{
                    val viewHolder = recyclerView.getChildViewHolder(view) as FlexboxRecyclerViewAdapter.FlexboxRecyclerViewHolder
                    return viewHolder.getItemDetails(viewHolder)
                }
                "notekeyword"->{
                    val viewHolder = recyclerView.getChildViewHolder(view) as NoteKeywordAdapter.NoteFlexboxRecyclerViewHolder
                    return viewHolder.getItemDetails(viewHolder)
                }
            }
        }
        return null
    }
}
