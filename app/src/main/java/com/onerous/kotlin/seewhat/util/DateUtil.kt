package com.onerous.kotlin.seewhat.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Created by rrr on 2017/6/6.
 */

object DateUtil {

    val PATTERN_DATE = "MM月dd日"
    val PATTERN_WEEKDAY = "EEEE"
    val PATTERN = PATTERN_DATE + " " + PATTERN_WEEKDAY

    fun date2str(date: Date, format: String = PATTERN): String {
        val sdf = SimpleDateFormat(format, Locale.CHINA)
        return sdf.format(date)
    }

    fun str2date(str: String, format: String = PATTERN): Date {
        val sdf = SimpleDateFormat(format)
        val date = sdf.parse(str)
        return date
    }

    fun formatStrDate(str: String,format: String = PATTERN): String {
        val date = str2date(str,format)
        return date2str(date,format)
    }

    fun getYesterday(dateStr: String): String {
        var date = str2date(dateStr, "yyyyMMdd")
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        date = calendar.time
        return SimpleDateFormat("yyyyMMdd").format(date)
    }
}
