package com.scents.note.data.vo

data class RvCircleData(
    val img:Int,
    val name:String,
    val details:String?=null,
    var visibility:Boolean=false
)