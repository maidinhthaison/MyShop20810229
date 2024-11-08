package com.ql2.myshop.utils

object StringExt {
    const val CHAR_SPLIT = "@@"
    fun splitString(productImage: String?): List<String> {
        return  productImage!!.split(CHAR_SPLIT)
    }
}