package com.scentsnote.android.utils.extension

import com.scentsnote.android.data.vo.response.KeywordInfo

internal fun MutableList<KeywordInfo>.removeKeyword(keywordInfo: KeywordInfo) {
    val keyword = this.find {
        it.name == keywordInfo.name && it.keywordIdx == keywordInfo.keywordIdx
    }
    keyword?.checked = false
    this.remove(keyword)
}
