package com.afume.afume_android.data.vo.response

data class ResponseKeyword(
    val count: Int,
    val rows: MutableList<KeywordInfo>
)
data class KeywordInfo(
    val name:String,
    val keywordIdx:Int=0,
    var checked: Boolean= false
)
