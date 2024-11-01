package com.ql2.myshop.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

const val DATE_TIME_SERVER = "yyyy-MM-dd'T'HH:mm:ss.sss'Z'"
const val DATE_ORDER_DATETIME = "yyyy-MM-dd HH:mm"
const val DATE_ORDER_DATE = "yyyy-MM-dd"

fun formatDateTimeServer(dateInput: String): String {
    val formatter: DateFormat = SimpleDateFormat(DATE_TIME_SERVER, Locale.getDefault())
    val date: Date = formatter.parse(dateInput) as Date
    return SimpleDateFormat(DATE_ORDER_DATETIME, Locale.getDefault()).format(date)
}

fun formatDate(date: Calendar, outputFormat: String): String {
    val localDate = LocalDateTime.of(
        date.get(Calendar.YEAR),
        date.get(Calendar.MONTH) + 1,
        date.get(Calendar.DAY_OF_MONTH),
        date.get(Calendar.HOUR_OF_DAY),
        date.get(Calendar.MINUTE),
        date.get(Calendar.SECOND)
    )
    val outFormatter = DateTimeFormatter.ofPattern(outputFormat)
    return localDate.format(outFormatter)
}