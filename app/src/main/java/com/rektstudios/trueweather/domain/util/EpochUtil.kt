package com.rektstudios.trueweather.domain.util

import java.util.Calendar

object EpochUtil {
    fun getDateEpochToday(): Long{
        val dateEpoch = getTimeEpochNow()
        return dateEpoch-dateEpoch%86400
    }

    fun getTimeEpochNow(): Long{
        return Calendar.getInstance().timeInMillis/1000
    }
}