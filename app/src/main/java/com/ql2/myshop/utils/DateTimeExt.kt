package com.ql2.myshop.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val DATE_TIME_SERVER = "yyyy-MM-dd"
const val DATE_GRID_FORMAT = "MMMM dd, yyyy"

fun formatDateServer2DateGrid(dateInput: String): String {
    val formatter: DateFormat = SimpleDateFormat(DATE_TIME_SERVER, Locale.getDefault())
    val date: Date = formatter.parse(dateInput) as Date
    return SimpleDateFormat(DATE_GRID_FORMAT, Locale.getDefault()).format(date)
}