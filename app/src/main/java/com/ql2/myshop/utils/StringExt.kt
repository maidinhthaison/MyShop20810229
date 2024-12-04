package com.ql2.myshop.utils

object StringExt {
    fun splitString(productImage: String?): List<String> {
        return  productImage!!.split(CHAR_SPLIT)
    }
}