package com.scentsnote.android.utils.extension

import com.scentsnote.android.data.vo.response.BrandInfo

internal fun MutableList<BrandInfo>.removeBrand(brandInfo: BrandInfo) {
    val brand = this.find {
        it.name == brandInfo.name && it.brandIdx == brandInfo.brandIdx
    }
    brand?.check = false
    this.remove(brand)
}

internal fun MutableList<BrandInfo>.resetBrand(){
    this.forEach {
        it.check = false
    }
}