package com.ql2.myshop.utils

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale


fun formatPriceToCurrency(price: Int?): String {
    val numberFormat : NumberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
    numberFormat.currency= Currency.getInstance("VND")
    numberFormat.minimumFractionDigits = 0
    return numberFormat.format(price)
}
fun formatIntToString(price: Int?): String{
    return "%02d".format(price)

}