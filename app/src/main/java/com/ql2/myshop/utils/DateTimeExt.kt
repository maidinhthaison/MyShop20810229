package com.ql2.myshop.utils

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun formatDateTimeServer(dateInput: String): String {
    val formatter: DateFormat = SimpleDateFormat(DATE_TIME_SERVER, Locale.getDefault())
    val date: Date = formatter.parse(dateInput) as Date
    return SimpleDateFormat(DATE_ORDER_DATETIME, Locale.getDefault()).format(date)
}

fun formatDateTimeServer2BarChart(dateInput: String): String {
    val formatter: DateFormat = SimpleDateFormat(DATE_TIME_SERVER, Locale.getDefault())
    val date: Date = formatter.parse(dateInput) as Date
    return SimpleDateFormat(DATE_BAR_CHART_DATETIME, Locale.getDefault()).format(date)
}

fun formatDateTimeServer(dateInput: String, outputFormat: String): String {
    val formatter: DateFormat = SimpleDateFormat(DATE_TIME_SERVER, Locale.getDefault())
    val date: Date = formatter.parse(dateInput) as Date
    return SimpleDateFormat(DATE_COMMON_FORMAT, Locale.getDefault()).format(date)
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

fun formatDate(date: Date, outputFormat: String): String {
    val localDate = LocalDateTime.ofInstant(date.toInstant(), TimeZone.getDefault().toZoneId())
    val outFormatter = DateTimeFormatter.ofPattern(outputFormat)
    return localDate.format(outFormatter)
}

@SuppressLint("SimpleDateFormat")
fun formatDate(outputFormat: String): String {
    val time = Calendar.getInstance().time
    val formatter = SimpleDateFormat(outputFormat)
    return formatter.format(time)
}