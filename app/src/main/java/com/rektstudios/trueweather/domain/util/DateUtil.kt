package com.rektstudios.trueweather.domain.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DateUtil {
    companion object{
        fun getDayOfWeek(dateString:String): String{
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateString)
            val calendar = Calendar.getInstance()
            calendar.time = date!!
            val formatter= SimpleDateFormat("EEEE", Locale.getDefault())
            return formatter.format(calendar.time)
        }
        fun getFullDate(dateString:String): String{
            val date = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(dateString)
            val calendar = Calendar.getInstance()
            calendar.time = date!!
            val formatter= SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())
            return formatter.format(calendar.time)
        }
    }
}