package com.scentsnote.android.ui.filter

enum class FilterCategory(
    val index: Int,
    val nameText: String
) {
    Series(0, "계열"),
    Brand(1, "브랜드"),
    Keyword(2, "키워드")
}