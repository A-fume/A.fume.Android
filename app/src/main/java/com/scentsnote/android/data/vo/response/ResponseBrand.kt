package com.scentsnote.android.data.vo.response

import androidx.recyclerview.widget.DiffUtil

data class ResponseBrand(
    val count: Int,
    val rows: MutableList<InitialBrand>
)

data class InitialBrand(
    val firstInitial: String,
    val brands: MutableList<BrandInfo>
)

data class BrandInfo(
    val brandIdx: Int,
    val name: String="",
    var check: Boolean=false,
    var clickable: Boolean=true
){
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<BrandInfo>() {
            override fun areItemsTheSame(
                oldItem: BrandInfo,
                newItem: BrandInfo
            ): Boolean =
                oldItem.brandIdx == newItem.brandIdx

            override fun areContentsTheSame(
                oldItem: BrandInfo,
                newItem: BrandInfo
            ): Boolean =
                oldItem == newItem
        }
    }
}

data class BrandTab(
    val name: String,
    var isSelected: Boolean=false
)