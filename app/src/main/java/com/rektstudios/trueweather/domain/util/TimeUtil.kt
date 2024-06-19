package com.rektstudios.trueweather.domain.util

import java.util.Calendar

class TimeUtil {
    companion object{
        fun getCurrentTime() = Calendar.getInstance().timeInMillis / 1000L
    }
}