package com.rektstudios.trueweather.data.reponse.weather

import com.google.gson.annotations.SerializedName

data class ForecastData(
    @SerializedName("forecastday")
    val dailyForecastDataList: List<DailyForecastData>?
)